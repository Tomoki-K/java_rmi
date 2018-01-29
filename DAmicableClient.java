// Client process

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.util.*;

public class DAmicableClient{

	public static void main(String args[]){
		long millis; // elapsed time
		int limit = 550000; // search range limit
		int serverCnt = args.length; // server count

		launchAmicableServer l[] = new launchAmicableServer[serverCnt];
		Thread t[] = new Thread [args.length];

		try{
			int min, max = 0;
			// start timer
			millis = System.currentTimeMillis();
			for(int i = 0; i < serverCnt; ++i){
				min = max + 1;
	            max = i == serverCnt-1 ? limit : Math.round(limit*(i+1)/serverCnt);
				// make thread for each server
				l[i] = new launchAmicableServer(args[i], min, max, millis);
				t[i] = new Thread(l[i]);
				t[i].start();
			}
		}catch(Exception e){
			System.out.println(e);
		}
	}
}

class launchAmicableServer implements Runnable{
	String address; // server address
	int min, max; // search range
	long millis; // elapsed time

	public launchAmicableServer(String name, int rangeStart, int rangeEnd, long m){
		address = name;
		min = rangeStart;
		max = rangeEnd;
		millis = m;
	}

	public void run(){
		try{
			// use AmicableService
			Amicable p = (Amicable)Naming.lookup("//"+address+"/AmicableService");
			System.out.println("Start "+ address);
			Result.collect(address, millis, p.putAmicable(min, max));
		}catch(Exception e){
			System.out.println(e);
		}
	}
}

class Result{
	static int i = 0; // replied server count
	static List<Integer[]> allRes = new ArrayList<Integer[]>();

	public static synchronized void collect(String address, long millis, List<Integer[]> res){
		System.out.println("Finish "+address);

		millis = System.currentTimeMillis() - millis;
		System.out.println(" " + (double)millis/1000 + "sec");
		
		allRes.addAll(res);
		++i;

		for (Integer[] r : allRes) {
            System.out.println('[' + String.valueOf(r[0]) + ',' + String.valueOf(r[1]) + ']');
		}
	}
}
