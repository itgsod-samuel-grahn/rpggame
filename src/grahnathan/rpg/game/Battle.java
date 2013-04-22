package grahnathan.rpg.game;

import grahnathan.rpg.engine.GameEngine;
import grahnathan.rpg.game.character.Char;
import grahnathan.rpg.game.character.Char.Allegiance;
import grahnathan.rpg.game.gameobject.Enemy;
import grahnathan.rpg.game.gameobject.Player;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class Battle {
	public static final int MapSizeY = 8;
	public static final int MapSizeX = 16;
	private Player player;
	public Enemy enemy;
	
	private Char selected;
	private int pointer = 0;
	
	Char[][] map;
	private Char[] speedArray;
	
	int mouseTileX;
	int mouseTileY;
	
	String log = "";
	
	
	public Battle(Player p, Enemy e)
	{
		this.player = p;
		this.enemy = e;
		map = new Char[MapSizeX][MapSizeY];
		for(int i = 0; i < player.getTeam().size(); i++)
		{
			map[0][i] = player.getTeam().get(i);
		}
		for(int i = 0; i < enemy.getTeam().size(); i++)
		{
			map[MapSizeX-1][i] = enemy.getTeam().get(i);
		}
		speedArray = new Char[enemy.getTeam().size() + player.getTeam().size()];
		for(int i = 0; i< player.getTeam().size(); i++)
		{
			speedArray[i] = player.getTeam().get(i);
		}
		for(int i = 0; i< enemy.getTeam().size(); i++)
		{
			speedArray[player.getTeam().size() + i] = enemy.getTeam().get(i);
		}
	}
	
	public ArrayList<Char> GetAvailableTargets(int range, int[] curPos)
	{
		ArrayList<Char> chars = new ArrayList<Char>();
		for(int x = 0; x < MapSizeX; x++)
		{
			for(int y = 0; y < MapSizeY; y++)
			{
				Char c = map[x][y];
				if(c != null && c != selected)
				{
					if(isInRange(range, curPos[0], curPos[1], x, y))
					{
						chars.add(c);
					}
				}
					
				
			}
		}
		return chars;
		
	}
	
	public void update()
	{
		
		
		
		for(int x = 0; x < MapSizeX; x++)
		{
			for(int y = 0; y < MapSizeY; y++)
			{
				Char c = map[x][y];
				if(c != null && c.curHealth <= 0)
				{
					map[x][y] = null;
					player.getTeam().remove(c);
					enemy.getTeam().remove(c);
					Char[] newArr = new Char[enemy.getTeam().size() + player.getTeam().size()];
					int offset = 0;
					for(int i = 0; i < speedArray.length; i++)
					{
						if(c == speedArray[i])
							offset = -1;
						else
							newArr[i + offset] = speedArray[i];
					}
					speedArray = newArr;
				}
			}
		}
		
		if(enemy.getTeam().size() == 0)
			Game.WonBattle();
		if(player.getTeam().size() == 0)
			Game.LostBattle();
		
		if(selected != null)
		{
			if(selected.getMoves() == 2) selected = null;
		}
		if(selected == null)
		{
			pointer %= speedArray.length;
			selected = speedArray[pointer];
			selected.NewTurn();
			pointer++;
			
		}
	}
	
	public void Log(String s)
	{
		log += s;
		log += "\n";
	}
	
	public void render(Graphics g)
	{
		g.drawString(log, 100, 32*8);
		
		Char c;
		Color color = new Color(255,0,0);
		
		
		
		for(int x = 0; x < MapSizeX; x++)
		{
			for(int y = 0; y < MapSizeY; y++)
			{
				c = map[x][y];
				int red = 255;
				int green = 255;
				int blue = 255;
				
				if(c != null)
				{
					
					if(c.allegiance == Allegiance.Player)
						red = 0;
					if(c.allegiance == Allegiance.Enemy)
						blue = 0;
					if(c != selected)
						green = 0;
					
					color = new Color(red,green,blue);
					
					c.render(g, x, y, color);
				}
				else
				{
					int[] pos = getPos(selected);
					
					if(x == mouseTileX && y == mouseTileY)
						blue = 0;
					if(isInRange(selected.getMovement(),x,y,pos[0],pos[1]))
						green = 0;
					color = new Color(red, green, blue);
					g.setColor(color);
					g.drawRect(x*GameEngine.BattleTileSize, y*GameEngine.BattleTileSize, GameEngine.BattleTileSize -1, GameEngine.BattleTileSize-1);
				}
			}
		}
	}

	private int[] getPos(Char lookfor) {
		int curX = -1;
		int curY = -1;
		
		xloop:
		for(int x = 0; x < MapSizeX; x++)
		{
			for(int y = 0; y < MapSizeY; y++)
			{
				Char c = map[x][y];
				if(c == lookfor)
				{
					curX = x;
					curY = y;
					
					break xloop;
				}
			}
		}
		int[] array = {curX, curY};
		return array;
		
	}

	public void handleInput(Input input)
	{
		if(isPlayerTurn())
			playerInput(input);
		else
			AIInput();
	}
	
	private void AIInput() {
		ArrayList<Char> targets = GetAvailableTargets(selected.getAttackRange(), getPos(selected));
		if(targets.size() == 0)
			AIMove();
		else
		{
			Char bestTarget = null;
			for(Char c : targets)
			{
				if(bestTarget == null ||  c.curHealth < bestTarget.curHealth)
				{
					bestTarget = c;
				}
			}AIAttack(bestTarget);
		}
		
	}

	private void AIAttack(Char bestTarget) {
		bestTarget.curHealth -= selected.damage;
		selected.MakeMove();
		
	}

	private void AIMove() {
		ArrayList<int[]> list =  GetReachableWithAdjacent(selected.getMovement(), getPos(selected));
		int minDist = -1;
		int[] targetPos = null;
		for(int[] arr : list)
		{
			int dist = getDistance(getPos(selected), arr);
			if(minDist == -1 || dist < minDist)
			{
				minDist = dist;
				targetPos = arr;
			}
		}
		
		if(targetPos != null)
			Move(selected, targetPos[0], targetPos[1]);
		else
		{
			list = GetReachableWithAdjacent(9999999, getPos(selected));
			int mindist = -1;
			int[] pos = null;
			for(int[] arr : list)
			{
				int dist = getDistance(getPos(selected), arr);
				if(minDist == -1 || dist < minDist)
				{
					minDist = dist;
					pos = arr;
				}
			}
			
			if(pos != null)
			{
				double angle = Math.atan2((getPos(selected)[0] - pos[0]),(getPos(selected)[1]-pos[1]));
				
				int x = (int)(selected.getMovement() * Math.sin(angle));
				int y = (int)(selected.getMovement() * Math.cos(angle));
				Log("X: " + x + ", Y: " + y);
				Log("Move: " + Move(selected, getPos(selected)[0] - x, getPos(selected)[1] - y));
				
			}
		}
		
		selected.MakeMove();
		
	}
	
	private int getDistance(int[] pos, int[] target) {
		// TODO Auto-generated method stub
		return getDistance(pos[0], pos[1], target[0], target[1]);
	}

	private ArrayList<int[]> GetReachableWithAdjacent(int range, int[] start)
	{
		ArrayList<int[]> list = new ArrayList<int[]>();
		
		for(int x = 0; x < MapSizeX; x++)
		{
			for(int y = 0; y < MapSizeY; y++)
			{
				Char c = map[x][y];
				if(c == null && isInRange(range, start[0], start[1], x, y) && GetAvailableTargets(selected.getAttackRange(), new int[]{x,y}).size() != 0)
				{
					list.add(new int[]{x,y});
				}
				
			
			}
		}
		
		return list;
	}

	private void playerInput(Input input) {
		this.mouseTileX = input.getMouseX()/GameEngine.BattleTileSize;
		this.mouseTileY = input.getMouseY()/GameEngine.BattleTileSize;
		
		if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON))
		{
			if(mouseTileX < MapSizeX && mouseTileY < MapSizeY)
			{
				if(Move(selected, mouseTileX, mouseTileY))
					selected.MakeMove();
				else if(Attack(selected, mouseTileX, mouseTileY))
					selected.MakeMove();
			}
		}
		
	}

	public boolean isPlayerTurn()
	{
		for(Char c : player.getTeam())
		{
			if(c == selected)
				return true;
		}
		return false;
	}

	private boolean Attack(Char toattack, int targetX, int targetY) {
		Char target = map[targetX][targetY];
		if(target == null) return false;
		
		int curX = 0;
		int curY = 0;
		
		xloop:
		for(int x = 0; x < MapSizeX; x++)
		{
			for(int y = 0; y < MapSizeY; y++)
			{
				Char c = map[x][y];
				if(c == toattack)
				{
					curX = x;
					curY = y;
					break xloop;
				}
			}
		}
		
		if(!isInRange(toattack.getAttackRange(), curX, curY, targetX, targetY))
			return false;
		if(target == toattack) return false;
		
		target.curHealth -= toattack.damage;
		return true;
	}

	private boolean isInRange(int range, int curX, int curY, int targetX, int targetY) {
		
		if(getDistance(curX, curY, targetX, targetY) <= range) return true;
		return false;
	}
	
	private int getDistance(int x1, int y1, int x2, int y2)
	{
		int dx = x1-x2;
		int dy = y1-y2;
		return (int)Math.sqrt((dx*dx) + (dy*dy));
	}

	private boolean Move(Char tomove, int targetX, int targetY) {
		Char c;
		int curX = 0;
		int curY = 0;
		if(targetX < 0 || targetX > MapSizeX) 
			return false;
		if(targetY < 0 || targetY > MapSizeY) 
			return false;
		if(map[targetX][targetY] != null)
			return false;
		
		xloop:
		for(int x = 0; x < MapSizeX; x++)
		{
			for(int y = 0; y < MapSizeY; y++)
			{
				c = map[x][y];
				if(c == tomove)
				{
					curX = x;
					curY = y;
					break xloop;
				}
			}
		}
		if(!isInRange(tomove.getMovement(), curX, curY, targetX, targetY))
			return false;
		
		map[targetX][targetY] = tomove;
		map[curX][curY] = null;
		return true;
		
	}

}
