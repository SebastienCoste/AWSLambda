package fr.sco.staticjo.genetic.multithread;

import java.util.ArrayList;
import java.util.List;

import fr.sco.staticjo.genetic.GeneticAlgo;
import fr.sco.staticjo.genetic.Person;
import fr.sco.staticjo.genetic.Population;

public class ThreadPool<P extends Person> {

    private BlockingQueue<Population<P>> taskQueue = null;
    private BlockingQueue<Person> responseQueue = null;
    private List<PoolThread<P>> threads = new ArrayList<PoolThread<P>>();
    private boolean isStopped = false;

    public ThreadPool(int noOfThreads, int maxNoOfTasks, GeneticAlgo<P> algo){
        taskQueue = new BlockingQueue<Population<P>>(maxNoOfTasks);
        responseQueue = new BlockingQueue<Person>(maxNoOfTasks);

        for(int i=0; i<noOfThreads; i++){
            threads.add(new PoolThread<P>(taskQueue, responseQueue, algo));
        }
        for(PoolThread<P> thread : threads){
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
        for(PoolThread<P> thread : threads){
           thread.doStop();
        }
    }
    
    public Person getResult() throws InterruptedException{
			return responseQueue.dequeue();
    }

}
