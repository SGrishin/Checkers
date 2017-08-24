package CheckersApp.CheckersPackage;




abstract public class masHelper
{
  private static String  _id = "IDL:CheckersApp/Checkers/mas:1.0";

  public static void insert (org.omg.CORBA.Any a, int[][] that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static int[][] extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_long);
      __typeCode = org.omg.CORBA.ORB.init ().create_array_tc (8, __typeCode );
      __typeCode = org.omg.CORBA.ORB.init ().create_array_tc (8, __typeCode );
      __typeCode = org.omg.CORBA.ORB.init ().create_alias_tc (CheckersApp.CheckersPackage.masHelper.id (), "mas", __typeCode);
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static int[][] read (org.omg.CORBA.portable.InputStream istream)
  {
    int value[][] = null;
    value = new int[8][];
    for (int _o0 = 0;_o0 < (8); ++_o0)
    {
      value[_o0] = new int[8];
      for (int _o1 = 0;_o1 < (8); ++_o1)
      {
        value[_o0][_o1] = istream.read_long ();
      }
    }
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, int[][] value)
  {
    if (value.length != (8))
      throw new org.omg.CORBA.MARSHAL (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    for (int _i0 = 0;_i0 < (8); ++_i0)
    {
      if (value[_i0].length != (8))
        throw new org.omg.CORBA.MARSHAL (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
      for (int _i1 = 0;_i1 < (8); ++_i1)
      {
        ostream.write_long (value[_i0][_i1]);
      }
    }
  }

}
