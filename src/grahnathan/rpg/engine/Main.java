package grahnathan.rpg.engine;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class Main {
	
	public static void main(String[] args)
	{
		GameEngine gc = new GameEngine();
		
		
		try {
			AppGameContainer app = new AppGameContainer(gc);
 
			app.setDisplayMode(1024, 768, false);
			 
			app.setVSync(true);
			app.setTargetFrameRate(60);
			
			app.start();
			
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

}
