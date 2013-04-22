package grahnathan.rpg.engine;

import java.util.ArrayList;

import grahnathan.rpg.engine.GameEngine.GameState;
import grahnathan.rpg.game.Game;
import grahnathan.rpg.game.Menu;
import grahnathan.rpg.game.character.Char;
import grahnathan.rpg.game.gameobject.Player;

public class NewGameMenu extends Menu {

	public NewGameMenu() {
		super(new String[]{"Start already.."});
	}
	
	@Override
	protected void PressedItem()
	{
		if(selected == 0)
		{
			ArrayList<Char> team = new ArrayList<Char>();
			team.add(new Char(10,100));
			team.add(new Char(10,100));
			team.add(new Char(10,100));
			team.add(new Char(10,100));
			Game.setPlayer(team);
			GameEngine.SetStateStatic(GameState.Game);
		}
	}

}
