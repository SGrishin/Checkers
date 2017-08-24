package CheckersApp.CheckersPackage;




public final class masHolder implements org.omg.CORBA.portable.Streamable
{
  public int value[][] = null;

  public masHolder ()
  {
  }

  public masHolder (int[][] initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = CheckersApp.CheckersPackage.masHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    CheckersApp.CheckersPackage.masHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return CheckersApp.CheckersPackage.masHelper.type ();
  }

}
