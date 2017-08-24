
import CheckersApp.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;
import java.util.Properties;

class CheckersImpl extends CheckersPOA {
  private ORB orb;

  private int flag = 0, number = 0;
  private int Win = 0; // потребуется при выборе очереди хода
  private int WhiteCount = 12; // сколько осталось белых
  private int BlackCount = 12; // сколько осталось черных
  private int[] players = new int[2];
  private int store[][] = new int[8][8];
  
  
  
  public void setORB(ORB orb_val) {
    orb = orb_val; 
  }
  
  public void newStore() // обновляем доску и игроков
  {
	  if (number == 0) // проверка на законченность партии
	  {
                  WhiteCount = 12;
                  BlackCount = 12;
		  flag = 1; // потребуется при выборе очереди хода
		  for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
                                    if((i + j)%2 == 1)
                                    {
                                        if(i < 3)
                                           store[i][j] = 1; // белые
                                        if(i > 4)
                                           store[i][j] = 2; // черные
                                        if(i < 5 && i > 2)
                                           store[i][j] = 0; // пустые клетки
                                    }
                                    else
					store[i][j] = 0; // пустые клетки
				}
			}
                  
	    
		for (int i = 0; i < 2; i++)
			players[i] = 0;
		 number++;
	  }
  }
  
  public int addPlayer(int n) // добавлем игрока (0 - все хорошо, -1 - что-то не так)
  {
	if(players[0] == 0)
	{
            players[0] = n;
	}
	else 
            if(players[0] != 0 && players[1] == 0)
            {
                if (players[0] != n)
                {
                    players[1] = n;
                    return 0;
                }                        
                else 
                    return -1;
            }
            else 
                return -1;
	  
        return 0;
  }
  
  public int IsPlayers() // возврашает кол-во участвующих игроков
  {
	  if (players[0] != 0 && players[1] !=0)
		  return 2;
	  if (players[0] != 0 && players[1] == 0)
		  return 1;
	  return 0;
  }
  
  public int IsWin()
  {
	  return Win;
  }
  
  public void WinYes(int p)
  {
	  Win = p;
	  number = 0;
  }
  
  public int xod() // определяем чья очередь ходить
  {
        if ((flag + Win)%2 == 1)          
            return 1;
	else 
            return 2;	  
  }
  
  public void OtherPlayer(int player) // Передаем ход другому игроку
  {
      if(player == 1)
          flag = 2;
      else
          flag = 1;
  }
  
  public int getPick(int player, int x, int y) // Выбор шашки (1 - правильный, 0 - неправильный)
  {
	try {
                if(x > 7 || x < 0 || y > 7 || y < 0) // Проверка допустимости
                {
                    return 0;
                }
                
		if (Math.abs(store[x][y]) != player) // Проверяем принадлежит ли шашка игроку 
		{
                    return 0;
		} 
                else 
		{
                    //Проверяем можно ли куда-то сходить
                    if(x > 0 && x < 7 && y > 0 && y < 7) // для внутренные области без учета гранич
                    {
                        if((store[x+1][y+1]==0 && store[x][y] == 1) // для шашек
                                || (store[x+1][y-1]==0 && store[x][y] == 1) 
                                || (store[x-1][y+1]==0 && store[x][y] == 2)
                                || (store[x-1][y-1]==0 && store[x][y] == 2)
                                || (store[x+2][y+2]==0 && store[x][y] == 1 && Math.abs(store[x + 1][y + 1]) != player)
                                || (store[x-2][y+2]==0 && store[x][y] == 2 && Math.abs(store[x - 1][y + 1]) != player)
                                || (store[x+2][y-2]==0 && store[x][y] == 1 && Math.abs(store[x + 1][y - 1]) != player)
                                || (store[x-2][y-2]==0 && store[x][y] == 2 && Math.abs(store[x - 1][y - 1]) != player))
                        {
                            return 1;
                        }
                        else
                        {
                            if((store[x+1][y+1]==0  // для дамок
                                    || store[x+1][y-1]==0 
                                    || store[x-1][y+1]==0
                                    || store[x-1][y-1]==0
                                    || (store[x+2][y+2]==0 && Math.abs(store[x + 1][y + 1]) != player)
                                    || (store[x-2][y+2]==0 && Math.abs(store[x - 1][y + 1]) != player)
                                    || (store[x+2][y-2]==0 && Math.abs(store[x + 1][y - 1]) != player)
                                    || (store[x-2][y-2]==0 && Math.abs(store[x - 1][y - 1]) != player)))
                            {
                                return 1;
                            }
                            else
                            {
                                return 0;
                            }
                        }
                    }
                    else // для границ
                    {
                        if(x == 0 || x == 7)
                        {
                            if(x == 0)
                            {
                                if(y == 0 || y == 7)
                                {
                                    if(y == 0)
                                    {
                                        if(store[x+1][y+1]==0 || (store[x+2][y+2]==0 && Math.abs(store[x + 1][y + 1]) != player))
                                            return 1;
                                        else
                                            return 0;
                                    }
                                    else
                                    {
                                        if(store[x+1][y-1]==0 || (store[x+2][y-2]==0 && Math.abs(store[x + 1][y - 1]) != player))
                                            return 1;
                                        else
                                            return 0;
                                    }
                                }
                                else
                                {
                                    if(store[x+1][y+1]==0 || store[x+1][y-1]==0 
                                            || (store[x+2][y+2]==0 && Math.abs(store[x + 1][y + 1]) != player && y < 6)
                                            || (store[x+2][y-2]==0 && Math.abs(store[x + 1][y - 1]) != player && y > 1))
                                    {
                                        return 1;
                                    }
                                    else
                                    {
                                        return 0;
                                    }
                                }
                            }                        
                            if(x == 7)
                            {
                                if(y == 0 || y == 7)
                                {
                                    if(y == 0)
                                    {
                                        if(store[x-1][y+1]==0 || (store[x-2][y+2]==0 && Math.abs(store[x - 1][y + 1]) != player))
                                        {
                                            return 1;
                                        }
                                        else
                                        {
                                            return 0;
                                        }
                                    }
                                    else
                                    {
                                        if(store[x-1][y-1]==0 || (store[x-2][y-2]==0 && Math.abs(store[x - 1][y - 1]) != player))
                                        {
                                            return 1;
                                        }
                                        else
                                        {
                                            return 0;
                                        }
                                    }
                                }
                                else
                                {
                                    if(store[x-1][y+1]==0 || store[x-1][y-1]==0
                                            || (store[x-2][y+2]==0 && Math.abs(store[x - 1][y + 1]) != player && y < 6)
                                            || (store[x-2][y-2]==0 && Math.abs(store[x - 1][y - 1]) != player && y > 1))
                                    {
                                        return 1;
                                    }
                                    else
                                    {
                                        return 0;
                                    }
                                }
                            }
                        }
                        if(y == 0 || y == 7)
                        {
                            if(y == 0)
                            {
                                if(x == 0 || x == 7)
                                {
                                    if(x == 0)
                                    {
                                        if(store[x+1][y+1]==0 || (store[x+2][y+2]==0 && Math.abs(store[x + 1][y + 1]) != player))
                                        {
                                            return 1;
                                        }
                                        else
                                        {
                                            return 0;
                                        }
                                    }
                                    else
                                    {
                                        if(store[x-1][y+1]==0 || (store[x-2][y+2]==0 && Math.abs(store[x - 1][y + 1]) != player))
                                        {
                                            return 1;
                                        }
                                        else
                                        {
                                            return 0;
                                        }
                                    }
                                }
                                else
                                {
                                    if(store[x+1][y+1]==0 || store[x-1][y+1]==0 
                                            || (store[x+2][y+2]==0 && Math.abs(store[x + 1][y + 1]) != player && x < 6)
                                            || (store[x-2][y+2]==0 && Math.abs(store[x - 1][y + 1]) != player && x > 1))
                                    {
                                        return 1;
                                    }
                                    else
                                    {
                                        return 0;
                                    }
                                }
                            }                        
                            if(y == 7)
                            {
                                if(x == 0 || x == 7)
                                {
                                    if(x == 0)
                                    {
                                        if(store[x+1][y-1]==0 || (store[x+2][y-2]==0 && Math.abs(store[x + 1][y - 1]) != player))
                                        {
                                            return 1;
                                        }
                                        else
                                        {
                                            return 0;
                                        }
                                    }
                                    else
                                    {
                                        if(store[x-1][y-1]==0 || (store[x-2][y-2]==0 && Math.abs(store[x - 1][y - 1]) != player))
                                        {
                                            return 1;
                                        }
                                        else
                                        {
                                            return 0;
                                        }
                                    }
                                }
                                else
                                {
                                    if(store[x-1][y-1]==0 || store[x+1][y-1]==0
                                            || (store[x+2][y-2]==0 && Math.abs(store[x + 1][y - 1]) != player && x < 6)
                                            || (store[x-2][y-2]==0 && Math.abs(store[x - 1][y - 1]) != player && x > 1))
                                    {
                                        return 1;
                                    }
                                    else
                                    {
                                        return 0;
                                    }
                                }
                            }
                        }
                    }                            
		}
            } 
            catch (Exception e) 
            {
                return 0;
            }
        return 0;
  }
  
  public int getMove(int player, int x1, int y1, int x2, int y2) // проверяем можно ли сходить (1 - можно(делаем ход), 0 - нельзя)
  {
        try {
                if(x2 > 7 || x2 < 0 || y2 > 7 || y2 < 0) // допустимость
                    return 0;
                if(x2 == x1 || y1 == y2 || store[x2][y2] != 0) // нетривиальность
                            return 0;
                if(player == 1) // ходят белые
                {
                    if(store[x1][y1] > 0) // не дамка
                    {
                        if (((x1 + 1 == x2)&&((y1 - 1 == y2)||(y1 + 1 == y2))
                                ||(x1 + 2 == x2)&&((y1 - 2 == y2)||(y1 + 2 == y2)))) 
                        {
                            if(x1 + 1 == x2) // кушать нечего
                            {
                                store[x1][y1] = 0;
                                if(x2 == 7) // в дамки?
                                    store[x2][y2] = -player;
                                else
                                    store[x2][y2] = player;
                                
                                return 1;
                            }
                            else 
                            {
                                if(x1 + 1 + 1 == x2 && y1 - 1 - 1 == y2 && Math.abs(store[x1 + 1][y1 - 1]) == 2) // кушать есть чего
                                {
                                    store[x1][y1] = 0;
                                    if(x2 == 7) // в дамки?
                                        store[x2][y2] = -player;
                                    else
                                        store[x2][y2] = player;

                                    store[x1 + 1][y1 - 1] = 0; 
                                    
                                    BlackCount--; // съели
                                    return 1;
                                }
                                if(x1 + 1 + 1 == x2 && y1 + 1 + 1 == y2 && Math.abs(store[x1 + 1][y1 + 1]) == 2)
                                {
                                    store[x1][y1] = 0;
                                    if(x2 == 7) // в дамки?
                                        store[x2][y2] = -player;
                                    else
                                        store[x2][y2] = player;

                                    store[x1 + 1][y1 + 1] = 0; 

                                    BlackCount--; // съели
                                    return 1;
                                }
                            }
                        } 
                        else 
                        {                        
                            return 0;                            
                        }
                    }
                    else // для дамок
                    {
                        if ((Math.abs(x1 - x2)== Math.abs(y1 - y2))) // алкотест
                        {
                            int x_step, y_step, tmpX = x1, tmpY = y1, x_E = x1, y_E = y1;
                            int countE = 0;
                            
                            if(x2 - x1 > 0)
                                x_step = 1;
                            else
                                x_step = -1;
                            if(y2 - y1 > 0)
                                y_step = 1;
                            else
                                y_step = -1;
                            
                            tmpX +=x_step;
                            tmpY +=y_step;
                            
                            while(tmpX != x2 && tmpY != y2)
                            {                                
                                if(Math.abs(store[tmpX][tmpY]) == 2)
                                {
                                    countE++;
                                    x_E = tmpX;
                                    y_E = tmpY;
                                }
                                if(Math.abs(store[tmpX][tmpY]) == 1) // через своих не шагаем
                                    return 0;
                                
                                tmpX +=x_step;
                                tmpY +=y_step;
                            }
                            if(countE > 1) // больше одного за раз не едим
                                return 0;
                            
                            store[x2][y2] = store[x1][y1];
                            store[x1][y1] = 0;
                            store[x_E][y_E] = 0;
                            BlackCount--; // съели
                            return 1;
                        }
                    }
                    return 0;                    
                }
                else // для черных
                {
                    if(store[x1][y1]>0)
                    {
                        if (((x1 - 1 == x2)&&((y1 - 1 == y2)||(y1 + 1 == y2))
                                ||(x1 - 2 == x2)&&((y1 - 2 == y2)||(y1 + 2 == y2)))) 
                        {
                            if(x1 - 1 == x2)
                            {
                                store[x1][y1] = 0;
                                if(x2 == 0)
                                    store[x2][y2] = -player;
                                else
                                    store[x2][y2] = player;
                                
                                return 1;
                            }
                            else
                            {
                                if(x1 - 1 - 1 == x2 && y1 - 1 - 1 == y2 && Math.abs(store[x1 - 1][y1 - 1]) == 1)
                                {
                                    store[x1][y1] = 0;
                                    if(x2 == 0)
                                        store[x2][y2] = -player;
                                    else
                                        store[x2][y2] = player;

                                    store[x1 - 1][y1 - 1] = 0; 
                                    
                                    WhiteCount--;
                                    return 1;
                                }
                                if(x1 - 1 - 1 == x2 && y1 + 1 + 1 == y2 && Math.abs(store[x1 - 1][y1 + 1]) == 1)
                                {
                                    store[x1][y1] = 0;
                                    if(x2 == 0)
                                        store[x2][y2] = -player;
                                    else
                                        store[x2][y2] = player;

                                    store[x1 - 1][y1 + 1] = 0; 

                                    WhiteCount--;
                                    return 1;
                                }
                            }
                        } else 
                        {
                            return 0;                            
                        }
                    }
                    else
                    {
                        if ((Math.abs(x1 - x2)== Math.abs(y1 - y2)))
                        {
                            int x_step, y_step, tmpX = x1, tmpY = y1, x_E = x1, y_E = y1;
                            int countE = 0;
                            
                            if(x2 - x1 > 0)
                                x_step = 1;
                            else
                                x_step = -1;
                            if(y2 - y1 > 0)
                                y_step = 1;
                            else
                                y_step = -1;
                            
                            tmpX +=x_step;
                            tmpY +=y_step;
                            
                            while(tmpX != x2 && tmpY != y2)
                            {                                
                                if(Math.abs(store[tmpX][tmpY]) == 1)
                                {
                                    countE++;
                                    x_E = tmpX;
                                    y_E = tmpY;
                                }
                                if(Math.abs(store[tmpX][tmpY]) == 2)
                                    return 0;
                                
                                tmpX +=x_step;
                                tmpY +=y_step;
                            }
                            if(countE > 1)
                                return 0;
                            
                            store[x2][y2] = store[x1][y1];
                            store[x1][y1] = 0;
                            store[x_E][y_E] = 0;
                            WhiteCount--;
                            
                            return 1;
                        }
                    }
                    return 0;
                }
            } catch (Exception e) 
            {
                return 0;
            }
  }
  
  public String sayHello() {
    return "\n Welcome to Checkers! \n";
  }
  
  public String sayAboutWin(int winser) 
  {
      if(winser == 1)
      {
        return "\n White Wins \n";
      }
      return "\n Black Wins \n";
  }
 
  
  public int checkWin() {
      if(WhiteCount == 0)
          return 2;
      if(BlackCount == 0)
          return 1;
      return 0;
  }
  
  
  public int[][] getTable()
  {
    return store;
  }
  
  // implement shutdown() method
  public void shutdown() {
    orb.shutdown(false);
  }
}
