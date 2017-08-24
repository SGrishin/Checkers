package CheckersApp;




public abstract class CheckersPOA extends org.omg.PortableServer.Servant
 implements CheckersApp.CheckersOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("newStore", new java.lang.Integer (0));
    _methods.put ("addPlayer", new java.lang.Integer (1));
    _methods.put ("IsPlayers", new java.lang.Integer (2));
    _methods.put ("IsWin", new java.lang.Integer (3));
    _methods.put ("WinYes", new java.lang.Integer (4));
    _methods.put ("xod", new java.lang.Integer (5));
    _methods.put ("OtherPlayer", new java.lang.Integer (6));
    _methods.put ("getPick", new java.lang.Integer (7));
    _methods.put ("getMove", new java.lang.Integer (8));
    _methods.put ("sayHello", new java.lang.Integer (9));
    _methods.put ("sayAboutWin", new java.lang.Integer (10));
    _methods.put ("checkWin", new java.lang.Integer (11));
    _methods.put ("getTable", new java.lang.Integer (12));
    _methods.put ("shutdown", new java.lang.Integer (13));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // CheckersApp/Checkers/newStore
       {
         this.newStore ();
         out = $rh.createReply();
         break;
       }

       case 1:  // CheckersApp/Checkers/addPlayer
       {
         int n = in.read_long ();
         int $result = (int)0;
         $result = this.addPlayer (n);
         out = $rh.createReply();
         out.write_long ($result);
         break;
       }

       case 2:  // CheckersApp/Checkers/IsPlayers
       {
         int $result = (int)0;
         $result = this.IsPlayers ();
         out = $rh.createReply();
         out.write_long ($result);
         break;
       }

       case 3:  // CheckersApp/Checkers/IsWin
       {
         int $result = (int)0;
         $result = this.IsWin ();
         out = $rh.createReply();
         out.write_long ($result);
         break;
       }

       case 4:  // CheckersApp/Checkers/WinYes
       {
         int p = in.read_long ();
         this.WinYes (p);
         out = $rh.createReply();
         break;
       }

       case 5:  // CheckersApp/Checkers/xod
       {
         int $result = (int)0;
         $result = this.xod ();
         out = $rh.createReply();
         out.write_long ($result);
         break;
       }

       case 6:  // CheckersApp/Checkers/OtherPlayer
       {
         int player = in.read_long ();
         this.OtherPlayer (player);
         out = $rh.createReply();
         break;
       }

       case 7:  // CheckersApp/Checkers/getPick
       {
         int player = in.read_long ();
         int x = in.read_long ();
         int y = in.read_long ();
         int $result = (int)0;
         $result = this.getPick (player, x, y);
         out = $rh.createReply();
         out.write_long ($result);
         break;
       }

       case 8:  // CheckersApp/Checkers/getMove
       {
         int player = in.read_long ();
         int x1 = in.read_long ();
         int y1 = in.read_long ();
         int x2 = in.read_long ();
         int y2 = in.read_long ();
         int $result = (int)0;
         $result = this.getMove (player, x1, y1, x2, y2);
         out = $rh.createReply();
         out.write_long ($result);
         break;
       }

       case 9:  // CheckersApp/Checkers/sayHello
       {
         String $result = null;
         $result = this.sayHello ();
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 10:  // CheckersApp/Checkers/sayAboutWin
       {
         int winser = in.read_long ();
         String $result = null;
         $result = this.sayAboutWin (winser);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 11:  // CheckersApp/Checkers/checkWin
       {
         int $result = (int)0;
         $result = this.checkWin ();
         out = $rh.createReply();
         out.write_long ($result);
         break;
       }

       case 12:  // CheckersApp/Checkers/getTable
       {
         int $result[][] = null;
         $result = this.getTable ();
         out = $rh.createReply();
         CheckersApp.CheckersPackage.masHelper.write (out, $result);
         break;
       }

       case 13:  // CheckersApp/Checkers/shutdown
       {
         this.shutdown ();
         out = $rh.createReply();
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:CheckersApp/Checkers:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public Checkers _this() 
  {
    return CheckersHelper.narrow(
    super._this_object());
  }

  public Checkers _this(org.omg.CORBA.ORB orb) 
  {
    return CheckersHelper.narrow(
    super._this_object(orb));
  }


} // class CheckersPOA
