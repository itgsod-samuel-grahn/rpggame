package grahnathan.rpg.game;

import grahnathan.rpg.engine.GameEngine;
import grahnathan.rpg.engine.GameObject;
import grahnathan.rpg.game.character.Char;
import grahnathan.rpg.game.gameobject.Enemy;
import grahnathan.rpg.game.gameobject.Player;
import grahnathan.rpg.game.gameobject.Wall;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.Tile;
import org.newdawn.slick.tiled.TileSet;
import org.newdawn.slick.tiled.TiledMapPlus;

public class Game {
	
	public static final Game game = new Game();
	ArrayList<GameObject> objects;
	private Player player;
	private boolean combat = false;
	
	private Battle battle;
	
	TiledMapPlus tmp;
	
	public static void setPlayer(ArrayList<Char> playerTeam)
	{
		getInstance().player.setTeam(playerTeam);
	}
	
	public void init()
	{
		player = new Player();
		
		ArrayList<Char> enemyTeam = new ArrayList<Char>();
		enemyTeam.add(new Char(10,50));
		
		
		objects = new ArrayList<GameObject>();
		this.objects.add(player);
		
		InitTileset();
	}
	
	private void InitTileset() {
		try {
			tmp = new TiledMapPlus("/data/TestMap.tmx");
			for(int x = 0; x < tmp.getWidth(); x++)
			{
				for(int y = 0; y < tmp.getHeight(); y++)
				{
					int id = tmp.getTileId(x, y, 0);
					String type = tmp.getTileProperty(id, "type", "background");
					if(type.equalsIgnoreCase("enemy"))
					{
						ArrayList<Char> enemy = new ArrayList<Char>();
						enemy.add(new Char(10,100));
						objects.add(new Enemy(x*GameEngine.GameTileSize, y*GameEngine.GameTileSize, enemy, tmp.getTileImage(x, y, 0)));
						
					}
					if(type.equalsIgnoreCase("wall"))
					{
						objects.add(new Wall(x*GameEngine.GameTileSize, y*GameEngine.GameTileSize, tmp.getTileImage(x, y, 0)));
					}
					if(type.equalsIgnoreCase("playerspawn"))
					{
						this.player.SetPosition(x, y);
					}
						
				}
			}
			
			
			
			
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
	}

	public void handleInput(Input input)
	{
		if(!combat)
		{
			this.player.handleInput(input);
		}
		else
		{
			this.battle.handleInput(input);
		}
	}
	
	public void update()
	{
		if(!combat)
		{
			ArrayList<GameObject> toRemove = new ArrayList<GameObject>();
			for(GameObject g : objects)
			{
				if(g.getRemove())
					toRemove.add(g);
				else
					g.update();
					
			}
			for(GameObject g : toRemove)
			{
				this.objects.remove(g);
			}
		}
		if(combat)
		{
			battle.update();
		}
	}
	
	public void render(Graphics g)
	{
		if(!combat)
		{
			for(GameObject go : objects)
			{
				go.render(g);
			}
			
		}
		if(combat)
		{
			battle.render(g);
		}
		
	}
	
	public static ArrayList<GameObject> rectangleCollide(Rectangle rect)
	{
		ArrayList<GameObject> obs = new ArrayList<GameObject>();
		for(GameObject go : getInstance().objects)
		{
			if(rect.intersects(go.getRectangle()))
			{
				obs.add(go);
			}
		}
		return obs;
	}

	public static ArrayList<GameObject> rectangleCollide(int x, int y, int w, int h)
	{
		return rectangleCollide(new Rectangle(x,y,w,h));
	}
	
	public static boolean checkCollision(GameObject obj1, GameObject obj2)
	{
		return obj1.getRectangle().intersects(obj2.getRectangle());
		
	}


	public static Game getInstance() {
		return game;
	}

	public static void Combat(Enemy go) {
		Game g = getInstance();
		g.initCombat(go);
		
	}

	private void initCombat(Enemy e) {
		this.battle = new Battle(this.player, e);
		this.combat = true;
		
	}

	public static void WonBattle() 
	{
		Game g = getInstance();
		g.objects.remove(g.battle.enemy);
		g.battle = null;
		g.combat = false;
	}

	public static void LostBattle() {
		Game g = getInstance();
		g.battle = null;
		g.combat = false;
		g.init();
		
	}

	

}
