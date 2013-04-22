package grahnathan.rpg.game;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class Menu {
	
	private String[] items;
	protected int selected;
	
	public Menu(String[] items)
	{
		this.items = items;
		selected = 0;
	}
	
	public void update()
	{
		
	}
	public void handleInput(Input input)
	{
		if(input.isKeyPressed(Input.KEY_W))
			selected--;
		if(input.isKeyPressed(Input.KEY_S))
			selected++;
		if(selected < 0) selected = this.items.length-1;
		selected %= this.items.length;
		
		if(input.isKeyPressed(Input.KEY_ENTER))
			PressedItem();
	}
	protected void PressedItem() {
		
	}

	public void render(Graphics g)
	{
		
		for(int i = 0; i < items.length; i++)
		{
			
			if(i == selected) g.setColor(new Color(255,255,255));
			else g.setColor(new Color(100,100,100));
			g.drawString(items[i], 256, 256 + i*32);
		}
	}

}
