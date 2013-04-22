package grahnathan.rpg.engine;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

public abstract class GameObject {
	public static final int FL_REMOVE = 0;
	public static final int FL_SOLID = 1;
	public enum Type
	{
		Item,
		Enemy,
		Wall,
		Player
	}
	
	
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	
	protected Sprite spr;
	protected Type type;
	
	protected boolean[] flags = new boolean[2];
	
	public GameObject(int x, int y, Color c)
	{
		this(x,y,new Sprite(c));
	}
	
	public GameObject(int x, int y, int w, int h, Color c)
	{
		this(x,y,w,h,new Sprite(c));
	}
	
	public GameObject(int x, int y, int w, int h, Sprite s)
	{
		this(x,y,s);
		this.width = w;
		this.height = h;
	}
	
	public void SetPosition(int x, int y)
	{
		this.x = x*GameEngine.GameTileSize;
		this.y = y*GameEngine.GameTileSize;
	}
	
	public Type getType()
	{
		return this.type;
	}
	
	protected void setSolid(boolean solid)
	{
		this.flags[FL_SOLID] = solid;
	}
	
	public boolean getSolid()
	{
		return this.flags[FL_SOLID];
	}
	
	public boolean getRemove()
	{
		return this.flags[FL_REMOVE];
	}
	
	public void setRemove(boolean remove)
	{
		this.flags[FL_REMOVE] = remove;
	}
	
	public GameObject(int x, int y, Sprite spr)
	{
		this.x = x;
		this.y = y;
		this.spr = spr;
		this.width = GameEngine.GameTileSize;
		this.height = GameEngine.GameTileSize;
	}
	
	public GameObject(int x, int y, Image tileImage) {
		this(x,y,new Sprite(tileImage));
	}

	public void render(Graphics g)
	{
		spr.render(g, this.getRectangle());
	}
	
	public void update()
	{
		
	}

	public int getX() {
		return x;
	}
	public int getY()
	{
		return y;
	}
	public int getWidth()
	{
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}
	
	public Rectangle getRectangle()
	{
		return new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());
	}
	
}
