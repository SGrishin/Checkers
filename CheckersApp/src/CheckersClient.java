import CheckersApp.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;

import java.util.Scanner;

public class CheckersClient
{
  static Checkers CheckersImpl;
  
  public static void main(String args[])
    {
		Scanner myScan = new Scanner(System.in);
                int[] playPick = new int[2];
		int[] playMove = new int[2];
		int table[][];
		int player = 0; 
		int f = 1, win = 0, h = 0, num = 0;
    try{
	ORB orb = ORB.init(args, null);
	org.omg.CORBA.Object objRef =  orb.resolve_initial_references("NameService");
	NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
	String name = "Checkers";
	
	//Получение объектной ссылки
        CheckersImpl = CheckersHelper.narrow(ncRef.resolve_str(name));
		
        System.out.println("Obtained a handle on server object: " + CheckersImpl);
		
	System.out.println(CheckersImpl.sayHello());
		
	CheckersImpl.newStore();
		
	table = new int[8][8];
		
	//Добавление игрока
        player = 1;
	while (f != 0)
	{
            f = CheckersImpl.addPlayer(player);
            if (f != 0)
                player = 2;
	}
                
	if (player == 1) System.out.printf("You are white.\n");
        if (player == 2) System.out.printf("You are black.\n");
		
	num = CheckersImpl.IsPlayers();
	if (num == 1)
            System.out.printf("Please wait until you join another player... \n");
        while(num != 2)
        {
            num = CheckersImpl.IsPlayers();
        }
        System.out.printf("The time has come... \n");                
		
	while (win == 0)
	{
            //Узнать, не победил ли другой игрок
            win = CheckersImpl.IsWin();
					
            //Узнать, чей ход
            h = CheckersImpl.xod();
						
            if (h == player && win == 0)
            {	
                table = CheckersImpl.getTable();

                // First Line                                                                
                System.out.printf(" # ");
                for (int i = 1; i <=8; i++)
                {									
                    System.out.printf(" %d ", (i - 1) % 10);									
                }   
                System.out.printf(" # ");                                                              
                System.out.printf("\n");
                                                                
                // Desk
                for (int i = 0; i < 8; i++)
                {
                    for (int j = 0; j < 8; j++)
                    {
                        if (j == 0) 
                        {
                            System.out.printf(" %d ", i % 10);
                        }
                        if (table[i][j] == 0) 
                        {
                            System.out.printf(" _ ");
                        } 
                        else 
                        {
                            if(table[i][j] == 1)
                                System.out.printf( " w ");
                            if(table[i][j] == -1)
                                System.out.printf( " W ");
                            if(table[i][j] == 2)
                                System.out.printf( " b ");
                            if(table[i][j] == -2)
                                System.out.printf( " B ");
                        }
                        if (j == 8 - 1) 
                        {
                            System.out.printf(" %d\n", i % 10);
                        }
                    }
                }
		// Last Line
		System.out.printf(" # ");
		for (int i = 1; i <=8; i++) 
                {									
                    System.out.printf(" %d ", (i - 1) % 10);									
		}
                System.out.printf(" # ");                                                                
                System.out.printf("\n");
								
		f = 0;
		while (f != 1)
		{
                    //Выбрать шашку
                    System.out.printf("(Player %d)  Choose your checker (0 to %d)\n", player,7);
                    System.out.printf("x: ");
                    playPick[0] = myScan.nextInt();
                    System.out.printf("y: ");
                    playPick[1] = myScan.nextInt();
                    f = CheckersImpl.getPick(player, playPick[0], playPick[1]);
                    if (f != 1)
                    {
                        System.out.println("Incorrect pick. Repeat.");
                    }
                }
                f = 0;
		while (f != 1)
		{
                    //Сделать ход
                    System.out.printf("(Player %d)  Choose your move (0 to %d)\n", player,7);
                    System.out.printf("x: ");
                    playMove[0] = myScan.nextInt();
                    System.out.printf("y: ");
                    playMove[1] = myScan.nextInt();
                    f = CheckersImpl.getMove(player, playPick[0], playPick[1], playMove[0], playMove[1]);
                    if (f != 1) System.out.println("Incorrect move. Repeat.");
		}
		if (f == 1) System.out.printf("OK. Wait until the other player moves. \n");		
							
                //Проверить победу
		win = CheckersImpl.checkWin();

		if (win == player)
		{										
                    //Оповестить другого игрока о победе
                    CheckersImpl.WinYes(player);
                }
		else
		{
                    //Отдать ход другому игроку
                    CheckersImpl.OtherPlayer(player);
		}
            }
						
	}
		
	System.out.println(CheckersImpl.sayAboutWin(win));
		
	} 
    catch (Exception e) {  System.out.println("ERROR : " + e) ;  e.printStackTrace(System.out);  }
    }
}

