package grahnathan.rpg.game;

import grahnathan.rpg.engine.GameEngine;
import grahnathan.rpg.engine.GameEngine.GameState;

public class MainMenu extends Menu{

	public MainMenu()
	{
		super(new String[]{"New Game", "Exit"});
	}
	
	@Override
	protected void PressedItem()
	{
		if(selected == 0)
			GameEngine.NewGame();
	}
	
}
