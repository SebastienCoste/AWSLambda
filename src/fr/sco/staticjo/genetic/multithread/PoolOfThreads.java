package fr.sco.staticjo.genetic.multithread;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import fr.sco.staticjo.genetic.GeneticAlgo;
import fr.sco.staticjo.genetic.Person;
import fr.sco.staticjo.genetic.Population;

public class PoolOfThreads<P extends Person> {

    private BlockingQueue<Population<P>> taskQueue = null;
    private BlockingQueue<Person> responseQueue = null;
    private List<ThreadInPool<P>> threads = new ArrayList<ThreadInPool<P>>();
    private boolean isStopped = false;

    public PoolOfThreads(int noOfThreads, int maxNoOfTasks, GeneticAlgo<P> algo) throws MalformedURLException, IOException{
        taskQueue = new BlockingQueue<Population<P>>(maxNoOfTasks);
        responseQueue = new BlockingQueue<Person>(maxNoOfTasks);

        for(int i=0; i<noOfThreads; i++){
            threads.add(new ThreadInPool<P>(taskQueue, responseQueue, algo));
        }
        for(ThreadInPool<P> thread : threads){
            thread.start();
        }
    }

    public synchronized void  execute(Population<P> task) throws InterruptedException{
        if(this.isStopped) throw
            new IllegalStateException("ThreadPool is stopped");

        this.taskQueue.enqueue(task);
    }

    public synchronized void stop(){
        this.isStopped = true;
        for(ThreadInPool<P> thread : threads){
           thread.doStop();
        }
    }
    
    public Person getResult() throws InterruptedException{
			return responseQueue.dequeue();
    }

}
