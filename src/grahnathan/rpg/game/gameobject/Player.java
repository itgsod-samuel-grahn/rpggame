package grahnathan.rpg.game.gameobject;

import grahnathan.rpg.engine.GameEngine;
import grahnathan.rpg.engine.GameObject;
import grahnathan.rpg.game.Game;
import grahnathan.rpg.game.character.Char;
import grahnathan.rpg.game.character.Char.Allegiance;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Input;

public class Player extends GameObject{
	private int speed;
	private int moveAmountX;
	private int moveAmountY;
	private ArrayList<Char> team;

	public Player(ArrayList<Char> playerTeam)
	{
		
		super(32,32,new Color(255,255,255));
		this.type = Type.Player;
		this.speed = 4;
		this.height = GameEngine.GameTileSize;
		this.width = GameEngine.GameTileSize;
		this.team = playerTeam;
		
		for(Char c: team)
		{
			c.allegiance = Allegiance.Player;
		}
		
	}

	public Player() {
		super(32,32,new Color(255,255,255));
		this.type = Type.Player;
		this.speed = 4;
		this.height = GameEngine.GameTileSize;
		this.width = GameEngine.GameTileSize;
		
		
	}
	
	public void setTeam(ArrayList<Char> team)
	{
		this.team = team;
		for(Char c: team)
		{
			c.allegiance = Allegiance.Player;
		}
	}

	public void handleInput(Input input) {
		moveAmountX = 0;
		moveAmountY = 0;
		
		if(input.isKeyDown(Input.KEY_W))
			move(0,-1);
		if(input.isKeyDown(Input.KEY_S))
			move(0,1);
		if(input.isKeyDown(Input.KEY_A))
			move(-1,0);
		if(input.isKeyDown(Input.KEY_D))
			move(1,0);
	}
	

	
	public void update()
	{
		int newX = x + moveAmountX;
		int newY = y + moveAmountY;
		
		ArrayList<GameObject> objects = Game.rectangleCollide(newX, newY, this.getWidth(), this.getHeight());
		ArrayList<GameObject> items = new ArrayList<GameObject>();
		
		boolean move = true;
		
		for(GameObject go : objects)
		{
			if(go.getType() == Type.Item)
				items.add(go);
			if(go.getSolid())
				move = false;
			if(go.getType() == Type.Enemy)
				Game.Combat((Enemy)go);
		}
		
		if(move)
		{
			this.x = newX;
			this.y = newY;
		}
		
	}

	private void move(int x, int y) {
		moveAmountX += x*speed;
		moveAmountY += y*speed;
		
	}

	public ArrayList<Char> getTeam() {
		return this.team;
	}

}
