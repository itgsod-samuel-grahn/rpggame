package grahnathan.rpg.engine;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.opengl.Texture;

public class Sprite {
	Color c;
	Image texture;
	
	public Sprite(Color c)
	{
		this.c = c;
		this.texture = null;
	}
	
	public Sprite(Image t)
	{
		this.c = null;
		this.texture = t;
	}
	
	public void render(Graphics g, Rectangle rect)
	{
		if(c != null)
		{
				g.setColor(c);
				g.fill(rect);
		}
		if(texture != null)
		{
			g.drawImage(texture, rect.getX(), rect.getY());
		}
		
	}
}
