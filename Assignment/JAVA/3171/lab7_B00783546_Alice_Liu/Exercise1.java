//Creates three threads, runs them concurrently but each thread is paused for a random
//time between 0 and 3 seconds
public class Exercise1 extends Thread
{
    public static void main(String[] args)
    {

        Exercise1 thread0,thread1, thread2, thread3,thread4;
        thread0 = new Exercise1();
        thread1 = new Exercise1();
        thread2 = new Exercise1();
        thread3 = new Exercise1();
        thread4 = new Exercise1();
        thread0.start();
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
    }
    public void run()
    {
        String[] StarWarChars={"Han Solo", "Darth Vader", "Luke Skywalker", "Chewbacca", "BB-8"};
        int pause,index;
        for (int i=0; i<5; i++)
        {
            try{
                index= Integer.parseInt(getName().substring(getName().length()-1));
                System.out.println(StarWarChars[index] + " being executed");
                pause = (int)(Math.random()*3000);

                System.out.println("Sleep time for "+ StarWarChars[index] + ":" +
                        pause + " millisecs");
                sleep(pause);
            }
            catch (InterruptedException e)
            {
                System.out.println(e);
            }
        }
    }
}
