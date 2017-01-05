package org.handler.property.runnable;

/**
 * Hello world!
 *
 */
public class SimpleRunnable implements Runnable
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        Thread a = new Thread(new SimpleRunnable(1, "A"));
        Thread b = new Thread(new SimpleRunnable(2, "B"));
        a.start();
        b.start();
    }

    SimpleRunnable(int step,String threadName){
    	this.step = step;
    	this.threadName = threadName;
    }
    public static int i = 0;
    public int step;
    public String threadName;
	public void run() {
		// TODO Auto-generated method stub
		for(int i =0;i<1000;){
			i = i+step;
			System.out.println(threadName + ":"+(i));
		}
	}
}
