public class Exercise2 extends Thread
{
    public static void main(String[] args)
    {

        Exercise2 thread0,thread1, thread2, thread3,thread4;
        thread0 = new Exercise2();
        thread1 = new Exercise2();
        thread2 = new Exercise2();
        thread3 = new Exercise2();
        thread4 = new Exercise2();
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
        double randomnum=Math.random();
        for (int i=0; i<3; i++)
        {
            try{
                index= Integer.parseInt(getName().substring(getName().length()-1));
                if(randomnum>=0.5) {
                    System.out.println(StarWarChars[index] + "  Throw LightSaber!");
                    pause = (int) (Math.random() * 3000);
                    //System.out.println("Sleep time for "+ StarWarChars[index] + ":" +pause + " millisecs");
                    sleep(pause);
                } else{
                    System.out.println("                      "+StarWarChars[index] + "   Catch LightSaber!");
                    pause = (int) (Math.random() * 3000);
                    sleep(pause);
                }
            }
            catch (InterruptedException e)
            {
                System.out.println(e);
            }
        }
    }
}
