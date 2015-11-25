package com.dalthow.etaron.states;

import java.io.IOException;

import org.apache.log4j.LogManager ;
import org.apache.log4j.Logger ;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import com.dalthow.etaron.Run;
import com.dalthow.etaron.framework.States;
import com.dalthow.etaron.media.ImageResource;
import com.dalthow.etaron.utils.DrawUtils;

/**
 * Etaron
 *
 * @author Trevi Awater
 **/

public class Splash implements GameState 
{
	// Declaration of the logo image.
	private Image logo;

	// Declaration of a variable used to check how long the user is in the Splash state.
	private int ticksInSplash;

	// Declaration of the Logger object.
	private static final Logger logger = LogManager.getLogger(Splash.class);

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
		return false;
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
	public void keyPressed(int par1, char par2){}
	public void keyReleased(int par1, char par2){}

	// Lets the state know which id it has.
	public int getID() 
	{
		return States.SPLASH_STATE.getId();
	}

	// Gets called when the state is created.
	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException
	{
		try 
		{
			// Loading images.
			logo = Run.resourceHandler.get(ImageResource.LOGO, false);
		}
		
		catch(IOException error) 
		{
			logger.error(error);
		}
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
		DrawUtils.drawBackground(graphics, gameContainer, new Color(255, 255, 255));
		DrawUtils.drawImageAtCenter(graphics, gameContainer, logo, true, true);
	}

	// Gets called every frame.
	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException 
	{
		ticksInSplash += delta;
		
		if(ticksInSplash > 1000)
		{
			stateBasedGame.enterState(States.MENU_STATE.getId());
		}
	}
}