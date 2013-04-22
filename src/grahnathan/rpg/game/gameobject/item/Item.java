package grahnathan.rpg.game.gameobject.item;

import grahnathan.rpg.engine.GameObject;

import org.newdawn.slick.Color;

public class Item extends GameObject
{
	
	
	public String name;	
	public Item(int x, int y, Color c, String name, Item.Type type)
	{
		super(x,y, c);
		this.name = name;
		this.type = type;
	}
}
