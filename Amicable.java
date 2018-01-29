// implementation of remote service interface

import java.rmi.Remote;
import java.rmi.RemoteException ;
import java.util.List;

// Amicable interface
public interface Amicable extends Remote{
	List<Integer[]> putAmicable(int min, int max) throws RemoteException;
}
