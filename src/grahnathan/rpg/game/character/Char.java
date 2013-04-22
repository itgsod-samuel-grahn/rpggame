package grahnathan.rpg.game.character;

import grahnathan.rpg.engine.GameEngine;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class Char {
	public enum Allegiance
	{
		Enemy,
		Player
	}
	
	public int damage;
	public int maxHealth;
	public int curHealth;
	public Allegiance allegiance;
	private int movesMade;
	public int attRange = 1;
	
	public Char(int damage, int maxHealth)
	{
		this.damage = damage;
		this.maxHealth = maxHealth;
		this.curHealth = maxHealth;
		movesMade = 0;
	}
	
	public void render(Graphics g, int tileX, int tileY, Color c)
	{
		g.setColor(c);
		g.fillRect(tileX * GameEngine.BattleTileSize, tileY*GameEngine.BattleTileSize, GameEngine.BattleTileSize-1, GameEngine.BattleTileSize-1);
		g.setColor(new Color(255 - c.getRed(), 255 - c.getGreen(), 255 - c.getBlue()));
		g.drawString(curHealth + "/" + maxHealth, tileX *GameEngine.BattleTileSize, tileY * GameEngine.BattleTileSize);
	}
	
	public void NewTurn()
	{
		movesMade = 0;
	}
	
	public int getMoves()
	{
		return movesMade;
	}
	
	public void MakeMove()
	{
		movesMade++;
	}

	public int getMovement() {
		return 3;
	}
	
	public int getAttackRange()
	{
		return attRange;
	}
	
	
}
