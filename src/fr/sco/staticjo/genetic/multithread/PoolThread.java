package fr.sco.staticjo.genetic.multithread;

import fr.sco.staticjo.genetic.GeneticAlgo;
import fr.sco.staticjo.genetic.Person;
import fr.sco.staticjo.genetic.Population;

public class PoolThread<P extends Person>  extends Thread {

	private BlockingQueue<Population<P>> taskQueue = null;
	private BlockingQueue<Person> responseQueue = null;
	private boolean       isStopped = false;
	private GeneticAlgo<P> algo = null;

	public PoolThread(BlockingQueue<Population<P>> queue, BlockingQueue<Person> respQueue, GeneticAlgo<P> algo){
		taskQueue = queue;
		responseQueue = respQueue;
		this.algo = algo;
	}

	@Override
	public void run(){
		while(!isStopped()){
			try{
				Population<P> runnable = taskQueue.dequeue();
				if (runnable != null){
					Person child = algo.generateChild(runnable);
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