package grahnathan.rpg.game.gameobject;

import grahnathan.rpg.engine.GameEngine;
import grahnathan.rpg.engine.GameObject;
import grahnathan.rpg.game.character.Char;
import grahnathan.rpg.game.character.Char.Allegiance;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

public class Enemy extends GameObject
{
	private ArrayList<Char> team;

	public Enemy(int x, int y, ArrayList<Char> enemyTeam)
	{
		super(x,y,new Color(255,1,1));
		this.type = Type.Enemy;
		this.height = GameEngine.GameTileSize;
		this.width = GameEngine.GameTileSize;
		this.team = enemyTeam;
		for(Char c: team)
		{
			c.allegiance = Allegiance.Enemy;
		}
	}
	
	public Enemy(int x, int y, ArrayList<Char> enemyTeam, Image image)
	{
		super(x,y,image);
		this.type = Type.Enemy;
		this.height = GameEngine.GameTileSize;
		this.width = GameEngine.GameTileSize;
		this.team = enemyTeam;
		for(Char c: team)
		{
			c.allegiance = Allegiance.Enemy;
		}
		
	
	}
	
	public ArrayList<Char> getTeam()
	{
		return team;
		
	}

}
