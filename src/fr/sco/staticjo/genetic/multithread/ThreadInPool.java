package fr.sco.staticjo.genetic.multithread;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.stream.IntStream;

import com.google.gson.Gson;

import fr.sco.staticjo.genetic.GeneticAlgo;
import fr.sco.staticjo.genetic.Person;
import fr.sco.staticjo.genetic.Population;
import fr.sco.staticjo.genetic.bestpath.WorldMap;
import fr.sco.staticjo.lambda.HttpLambdaConnection;
import fr.sco.staticjo.lambda.PopulationDTO;

public class ThreadInPool<P extends Person>  extends Thread {

	private BlockingQueue<Population<P>> taskQueue = null;
	private BlockingQueue<Person> responseQueue = null;
	private boolean       isStopped = false;
	private GeneticAlgo<P> algo = null;
	private HttpLambdaConnection httpCo;
	private Gson gson;
	public ThreadInPool(BlockingQueue<Population<P>> queue, BlockingQueue<Person> respQueue, GeneticAlgo<P> algo)  throws MalformedURLException, IOException{
		taskQueue = queue;
		responseQueue = respQueue;
		this.algo = algo;
		httpCo = new HttpLambdaConnection();
		gson = new Gson();
	}

	@Override
	public void run(){
		while(!isStopped()){
			try{
				Population<P> runnable = taskQueue.dequeue();
				if (runnable != null){
					
					PopulationDTO dto = new PopulationDTO(runnable);
					dto.setPointList(WorldMap.getPointList());
					String jsonDto = gson.toJson(dto);
					String result = httpCo.sendPost(jsonDto);
					
					Person child = runnable.instanciatePerson(algo.getClassPerson());
					
					String[] splitValues = result.split(",");
					IntStream.range(0, splitValues.length).forEach(e -> child.setGene(e, Long.valueOf(splitValues[e])));
//					Person child = algo.generateChild(runnable);
					responseQueue.enqueue(child);
				}
			} catch(Exception e){
				//log or otherwise report exception,
				//but keep pool thread alive.
			}
		}
	}

	public synchronized void doStop(){
		isStopped = true;
		this.interrupt(); //break pool thread out of dequeue() call.
	}

	public synchronized boolean isStopped(){
		return isStopped;
	}
}