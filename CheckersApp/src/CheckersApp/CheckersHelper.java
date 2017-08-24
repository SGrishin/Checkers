package CheckersApp;




abstract public class CheckersHelper
{
  private static String  _id = "IDL:CheckersApp/Checkers:1.0";

  public static void insert (org.omg.CORBA.Any a, CheckersApp.Checkers that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static CheckersApp.Checkers extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (CheckersApp.CheckersHelper.id (), "Checkers");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static CheckersApp.Checkers read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_CheckersStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, CheckersApp.Checkers value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static CheckersApp.Checkers narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof CheckersApp.Checkers)
      return (CheckersApp.Checkers)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      CheckersApp._CheckersStub stub = new CheckersApp._CheckersStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static CheckersApp.Checkers unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof CheckersApp.Checkers)
      return (CheckersApp.Checkers)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      CheckersApp._CheckersStub stub = new CheckersApp._CheckersStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
