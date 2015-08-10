package com.dalthow.etaron.states;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
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
import com.dalthow.etaron.objects.Player;

/**
 * Etaron
 *
 *
 * @author Dalthow Game Studios 
 * @class Game.java
 *
 **/

public class Game implements GameState 
{	
	// Declaration of the WorldObject's.
	
	public static ObjectHandler objectHandler;

	
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
	
	public void controllerButtonPressed(int par1, int par2){}
	public void controllerButtonReleased(int par1, int par2){}
	public void controllerDownPressed(int par1){}
	public void controllerDownReleased(int par1){}
	public void controllerLeftPressed(int par1){}
	public void controllerLeftReleased(int par1){}
	public void controllerRightPressed(int par1){}
	public void controllerRightReleased(int par1){}
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
			int currentPlayer = objectHandler.players.indexOf(cameraFocus);
			
			if(objectHandler.players.size() > 1)
			{
				if(currentPlayer < objectHandler.players.size() - 1)
				{
					currentPlayer++;
				}

				else
				{
					currentPlayer = 0;
				}

				cameraFocus.setVelX(0);
				cameraFocus = objectHandler.players.get(currentPlayer);
			}
		}
		
		
		// Resetting the level.
		
		if(par1 == Keyboard.KEY_R)
		{
			try 
			{
				objectHandler.reloadLevel();
			} 
			
			catch(SlickException error) 
			{
				error.printStackTrace();
			}
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
			error.printStackTrace();
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
	}
	
	
	// Gets called when the player leaves this state.

	public void leave(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException 
	{
		
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
}