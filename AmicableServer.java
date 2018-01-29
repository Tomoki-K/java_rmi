// サーバプロセス実装のクラスファイル

import java.rmi.Naming;

public class AmicableServer{

	public AmicableServer(){
		try{
			Amicable p = new AmicableImpl() ;
			Naming.rebind("//localhost/AmicableService",p) ;
		} catch(Exception e) {
			e.printStackTrace() ;
		}
	}

	public static void main(String args[]){
		new AmicableServer() ;
	}
}
