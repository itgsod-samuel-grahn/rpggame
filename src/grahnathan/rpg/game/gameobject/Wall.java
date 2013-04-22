package grahnathan.rpg.game.gameobject;

import grahnathan.rpg.engine.GameEngine;
import grahnathan.rpg.engine.GameObject;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

public class Wall extends GameObject
{
	public Wall(int x, int y, int w, int h)
	{
		super(x,y,w,h,new Color(255,0,255));
		this.setSolid(true);
	}

	public Wall(int x, int y) {
		this(x,y,GameEngine.GameTileSize, GameEngine.GameTileSize);
	}

	public Wall(int x, int y, Image tileImage) {
		super(x, y,tileImage);
		this.setSolid(true);
	}
}
