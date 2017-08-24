package CheckersApp;



public final class CheckersHolder implements org.omg.CORBA.portable.Streamable
{
  public CheckersApp.Checkers value = null;

  public CheckersHolder ()
  {
  }

  public CheckersHolder (CheckersApp.Checkers initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = CheckersApp.CheckersHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    CheckersApp.CheckersHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return CheckersApp.CheckersHelper.type ();
  }

}
