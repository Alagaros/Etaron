package com.dalthow.etaron.states;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random ;

import org.apache.log4j.LogManager ;
import org.apache.log4j.Logger ;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music ;
import org.newdawn.slick.MusicListener ;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;

import com.dalthow.etaron.Run;
import com.dalthow.etaron.framework.Camera;
import com.dalthow.etaron.framework.States;
import com.dalthow.etaron.framework.WorldObject;
import com.dalthow.etaron.handler.ObjectHandler;
import com.dalthow.etaron.media.MusicResource ;
import com.dalthow.etaron.objects.Player;

/**
 * Etaron
 *
 * @author Trevi Awater
 **/

public class Game implements GameState 
{	
	// Declaration of the WorldObject's.
	public static ObjectHandler objectHandler;
	
	// Declaration of the Logger object.
	private static final Logger logger = LogManager.getLogger(Game.class);

	// Declaration of the Camera.
	public static Camera cameraObject;
	public static WorldObject cameraFocus;

	// Declaration of the fonts used in this state.
	private TrueTypeFont infoFont;

	// Declaring some other variables.
	public static final float maximumVelocity = 15F;
	public static final float gravity = 0.5F;
	
	private boolean displayInfo;
	
	private StateBasedGame stateBasedGame;
	
	private Music lastSong;
	
	
	// Default implementation for mouse.
	public void mouseClicked(int par1, int par2, int par3, int par4){}
	public void mouseDragged(int par1, int par2, int par3, int par4){}
	public void mouseMoved(int par1, int par2, int par3, int par4){}
	public void mousePressed(int par1, int par2, int par3){}
	public void mouseReleased(int par1, int par2, int par3){}
	public void mouseWheelMoved(int par1){}

	// Default implementation for general input.
	public void inputEnded(){}
	public void inputStarted(){}
	public void setInput(Input par1){}

	// Determines whether user input is allowed or not.
	public boolean isAcceptingInput() 
	{
		return true;
	}

	// Default implementation for controllers.
	public void controllerButtonPressed(int par1, int par2)
	{
		if(par2 == 1 && cameraFocus.isJumping() == false)
		{
			cameraFocus.setVelY(-Player.jumpHeight);
			cameraFocus.setJumping(true);
		}
		
		else if(par2 == 3)
		{
			objectHandler.reloadLevel();
		}
		
		else if(par2 == 5 || par2 == 6)
		{
			switchPlayer();
		}
		
		else if(par2 == 7)
		{
			displayInfo = !displayInfo;
		}
		
		else if(par2 == 8)
		{
			stateBasedGame.enterState(States.MENU_STATE.getId());
		}
	}
	
	public void controllerButtonReleased(int par1, int par2){}
	public void controllerDownPressed(int par1){}
	public void controllerDownReleased(int par1){}
	
	public void controllerLeftPressed(int par1)
	{
		cameraFocus.setVelX(-Player.walkSpeed);
	}
	
	public void controllerLeftReleased(int par1)
	{
		cameraFocus.setVelX(0);
	}
	
	public void controllerRightPressed(int par1)
	{
		cameraFocus.setVelX(Player.walkSpeed);
	}
	
	public void controllerRightReleased(int par1)
	{
		cameraFocus.setVelX(0);
	}
	
	public void controllerUpPressed(int par1){}
	public void controllerUpReleased(int par1){}
	
	// Default implementation for keyboards.
	public void keyPressed(int par1, char par2)
	{
		// Back to the main menu.
		if(par1 == Keyboard.KEY_ESCAPE)
		{
			stateBasedGame.enterState(States.MENU_STATE.getId());
		}

		// Debug toggle.
		if(par1 == Keyboard.KEY_F3)
		{
			displayInfo = !displayInfo;
		}
		
		// Player movement.
		if(par1 == Keyboard.KEY_D)
		{
			cameraFocus.setVelX(Player.walkSpeed);
		}

		if(par1 == Keyboard.KEY_A)
		{
			cameraFocus.setVelX(-Player.walkSpeed);
		}

		if(par1 == Keyboard.KEY_W && cameraFocus.isJumping() == false)
		{
			cameraFocus.setVelY(-Player.jumpHeight);
			cameraFocus.setJumping(true);
		}

		// Switching Player.
		if(par1 == Keyboard.KEY_Q)
		{
			switchPlayer();
		}
		
		// Resetting the level.
		if(par1 == Keyboard.KEY_R)
		{
			objectHandler.reloadLevel();
		}
	}
	
	public void keyReleased(int par1, char par2)
	{
		// Stopping player movement.
		if(par1 == Keyboard.KEY_D)
		{
			cameraFocus.setVelX(0);
		}

		if(par1 == Keyboard.KEY_A)
		{
			cameraFocus.setVelX(0);
		}
	}

	// Lets the state know which id it has.
	public int getID() 
	{
		return States.GAME_STATE.getId();
	}

	// Gets called when the state is created.
	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException 
	{
		try 
		{
			// Loading fonts.
			InputStream inputStream = ResourceLoader.getResourceAsStream("assets/fonts/bit-bold.ttf");
			Font bitBold = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			
			infoFont = new TrueTypeFont(bitBold.deriveFont(10F), false);
		}
		
		catch(IOException | FontFormatException error) 
		{
			logger.error(error);
		}
		
		
		// Creating undefined objects.
		objectHandler = new ObjectHandler();
		cameraObject = new Camera(0, 0, gameContainer);
		
		
		// Setting the global StateBasedGame to the local one so we can use it in other methods.
		this.stateBasedGame = stateBasedGame;
	}
	
	// Gets called when the player enters this state.
	public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException 
	{
		playRandomMusic();
	}
	
	// Gets called when the player leaves this state.
	public void leave(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException 
	{
		lastSong.pause();
	}

	// Gets called every frame.
	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException 
	{
		graphics.translate(cameraObject.getPosX(), cameraObject.getPosY());
		
		objectHandler.render(graphics);
		
		graphics.translate(-cameraObject.getPosX(), -cameraObject.getPosY());
		
		if(displayInfo)
		{
			graphics.setColor(new Color(0, 0, 0));
			graphics.fillRect(0, 0, 133, 98);

			graphics.setColor(new Color(255, 255, 255));
			graphics.setFont(infoFont);

			graphics.drawString("VERSION:" + " " + Run.version, 15, 15);
			graphics.drawString("FPS:" + " " + gameContainer.getFPS(), 15, 30);

			graphics.drawString("X:" + " " + (int)cameraObject.getPosX(), 15, 60);
			graphics.drawString("Y:" + " " + (int)cameraObject.getPosY(), 15, 75);
		}
	}

	// Gets called every frame.
	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException 
	{
		objectHandler.tick();
		cameraObject.tick(cameraFocus);
	}


	/**
	 * Sets the camera focus on the next player in the List.
	 */
	private void switchPlayer() {
		int currentPlayer = objectHandler.players.indexOf(cameraFocus);

		if (objectHandler.players.size() > 1) {
			if (currentPlayer < objectHandler.players.size() - 1) {
				currentPlayer++;
			} else {
				currentPlayer = 0;
			}

			cameraFocus.setVelX(0);
			cameraFocus = objectHandler.players.get(currentPlayer);
		}
	}

	/**
	 * Gets a random Music file.
	 *
	 * @return Music
	 */
	private Music getRandomMusic()
	{
		Random random = new Random();

		// Getting a random song based on how many are currently loaded.
		Music randomSong = Run.resourceHandler.music.get(MusicResource.getMusicById(random.nextInt(MusicResource.values().length)));

		// Like Steve Jobs said, "We are making it less random to make it feel more random.".
		if(lastSong == randomSong)
		{
			return getRandomMusic();
		}
		
		if(randomSong != null)
		{
			lastSong = randomSong;
			lastSong.play();
		}
		
		return randomSong;
	}

	/**
	 * Keeps on playing random Music files.
	 */
	private void playRandomMusic()
	{
		getRandomMusic().addListener(new MusicListener()
		{
			@Override
			public void musicEnded(Music oldMusic)
			{
				playRandomMusic();
			}

			@Override
			public void musicSwapped(Music oldMusic, Music newMusic) {}
		});
	}
}