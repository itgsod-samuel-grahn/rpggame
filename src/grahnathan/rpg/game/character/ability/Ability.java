package grahnathan.rpg.game.character.ability;

import grahnathan.rpg.game.character.Char;

public abstract class Ability 
{
	private int cooldown;
	private int turnsSinceLastUse;
	public Target targetType;
	
	enum Target{
		Enemy,
		Ally,
		Area,
		Self
	}

	public Ability(int cooldown, Target targetType)
	{
		this.cooldown = cooldown;
		this.turnsSinceLastUse = 0;
		this.targetType = targetType;
	}
	
	public void OnTurnStart()
	{
		this.turnsSinceLastUse++;
	}
	
	public boolean Available()
	{
		return turnsSinceLastUse >= cooldown;
	}
	
	public abstract void Use();
	public abstract void Use(Char c);
	public abstract void Use(int x, int y);
}
