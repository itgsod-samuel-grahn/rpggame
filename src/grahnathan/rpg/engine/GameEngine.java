package grahnathan.rpg.engine;

import grahnathan.rpg.game.Game;
import grahnathan.rpg.game.MainMenu;
import grahnathan.rpg.game.Menu;

import java.awt.Font;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

public class GameEngine extends BasicGame {
	
	public static TrueTypeFont gameFont;
	public static TrueTypeFont menuFont;
	
	public static GameEngine getInstance()
	{
		return instance;
	}
	public enum GameState
	{
		Menu,
		Game
	}
	
	public static TrueTypeFont getFont()
	{
		switch(getInstance().state)
		{
		case Menu:
			return GameEngine.menuFont;
		case Game:
			return GameEngine.gameFont;
		}
		return gameFont;
	}
	
	private static GameEngine instance;
	
	public static final int GameTileSize = 32;
	public static final int BattleTileSize = 64;
	public GameState state;
	private Game game;
	private Menu m;

	public GameEngine() {
		super("RPG Game");
		instance = this;
		state = GameState.Menu;
		game = Game.getInstance();
		
		m = new MainMenu();
	}
	
	public static void SetStateStatic(GameState state)
	{
		getInstance().SetState(state);
	}
	
	public void SetState(GameState state)
	{
		this.state = state;
		
		
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		g.setFont(GameEngine.getFont());
		switch(state)
		{
		case Game:
			game.render(g);
			break;
		case Menu:
			m.render(g);
			break;
				
		}
		
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		game.init();
		gameFont = new TrueTypeFont(new Font("Verdana", Font.PLAIN, 16),false);
		menuFont = new TrueTypeFont(new Font("Verdana", Font.PLAIN, 32),false);
		
		
	}

	@Override
	public void update(GameContainer gc, int arg1) throws SlickException {
		switch(state)
		{
		case Game:
			game.handleInput(gc.getInput());
			game.update();
			break;
		case Menu:
			m.handleInput(gc.getInput());
			m.update();
			break;
				
		}
	}

	public static void NewGame() {
		getInstance().m = new NewGameMenu();
		
	}
}
