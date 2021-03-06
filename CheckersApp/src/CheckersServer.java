import CheckersApp.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;

public class CheckersServer {
  public static void main(String args[]) {
    try{
	ORB orb = ORB.init(args, null);
	POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
        rootpoa.the_POAManager().activate();
	  
	CheckersImpl checkersImpl = new CheckersImpl();
      	checkersImpl.setORB(orb); 
		
	org.omg.CORBA.Object ref = rootpoa.servant_to_reference(checkersImpl);
      	Checkers href = CheckersHelper.narrow(ref);
	org.omg.CORBA.Object objRef =
         orb.resolve_initial_references("NameService");
		 
	//Регистрация в сервисе имён
	NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
	String name = "Checkers";
      	NameComponent path[] = ncRef.to_name( name );
		
      	ncRef.rebind(path, href);
		
      	System.out.println("CheckersServer ready and waiting ...");
		
	orb.run();
	
    	} catch (Exception e) {
        		System.err.println("ERROR: " + e);   e.printStackTrace(System.out);
     	}
	System.out.println("CheckersServer Exiting ...");
}
} 

