// implements remote service

import java.rmi.RemoteException ;
import java.rmi.server.UnicastRemoteObject ;
import java.lang.Math ;
import java.util.List;
import java.util.ArrayList;

public class AmicableImpl extends UnicastRemoteObject implements Amicable{
	public AmicableImpl() throws RemoteException{
		super();
	}

	public List<Integer[]> putAmicable(int min, int max){
		long millis = System.currentTimeMillis();
        List<Integer[]> res = new ArrayList<Integer[]>();
        List<Integer> discovered = new ArrayList<Integer>();

        for (int i = min; i <= max; i++) {
            int AmiPair = discovered.contains(i) ? 0 : getAmiPair(i);
            if( AmiPair != 0) {
                res.add(new Integer[]{i, AmiPair});
                discovered.add(AmiPair);
            }
        }
		return res ;
	}

	private static int getAmiPair(int n) {
        int dSum = divisorSum(n);
        if (n == dSum || n % 2 != dSum % 2) return 0;
        return n == divisorSum(dSum) ? dSum : 0;
    }

    private static int divisorSum(int n) {
        int sum = 0;
        for (int d = 1; d <= n/2; d++) {
            if (n % d == 0) sum += d;
        }
        return sum;
    }
}
