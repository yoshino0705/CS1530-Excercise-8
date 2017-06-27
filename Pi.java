import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
public class Pi{
	public AtomicLong pin;
	public long iterations;
	public long actualIterations;
	
	public Pi(){
		pin = new AtomicLong(0);
		pin.set(0);
	}
	
	public static void main(String args[]){
		if(args.length < 2){
			System.out.println("Too few arguments!");
			System.exit(1);
		
		}
		
		Pi pi = new Pi();
		
		long threads = 0;
		
		try{
			threads = Long.parseLong(args[0]);
			//System.out.println(threads);
			pi.actualIterations = Long.parseLong(args[1]);
			//System.out.println(Long.parseLong(args[1]));
			pi.iterations = pi.actualIterations/threads;
			
		}catch(Exception e){
			System.out.println("Invalid arguments!");
			System.exit(2);
			
		}
		
		
		
		
		Thread[] ts = new Thread[((int) threads)];
		
		for (int j = 0; j < (int) threads; j++) {
		    
		    ts[j] = new Thread(() -> { 
		    	pi.calculate((int)pi.iterations);
		    });
		}

		
		
		try {
		    
		    for (Thread t : ts) 
		    	t.start();
		    
		    for (Thread t : ts) 
		    	t.join();
		    
		} catch (InterruptedException iex) { }
		
		System.out.println("Total = " + pi.actualIterations);
		System.out.println("Inside = " + pi.pin.get());
		System.out.println("Ratio = " + pi.pin.get()*1.0/pi.actualIterations);
		System.out.println("Pi = " + 4*pi.pin.get()*1.0/pi.actualIterations);
	}
	
	public void calculate(int numOfIter){
		for(int i = 0; i < numOfIter; i++){
			double x, y;						

			x = ThreadLocalRandom.current().nextDouble(0, 1);
			y = ThreadLocalRandom.current().nextDouble(0, 1);
			
			if((x*x + y*y) < 1){
				this.pin.addAndGet(1);
			}
			
		}
		
		
	}

	
}
