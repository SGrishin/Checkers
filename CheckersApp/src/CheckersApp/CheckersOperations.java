package CheckersApp;




public interface CheckersOperations 
{
  void newStore ();
  int addPlayer (int n);
  int IsPlayers ();
  int IsWin ();
  void WinYes (int p);
  int xod ();
  void OtherPlayer (int player);
  int getPick (int player, int x, int y);
  int getMove (int player, int x1, int y1, int x2, int y2);
  String sayHello ();
  String sayAboutWin (int winser);
  int checkWin ();
  int[][] getTable ();
  void shutdown ();
} // interface CheckersOperations
