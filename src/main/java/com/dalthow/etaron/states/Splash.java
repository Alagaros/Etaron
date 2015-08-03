package com.dalthow.etaron.states;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;

import com.dalthow.etaron.handler.States;

/**
 * Etaron
 *
 *
 * @author Dalthow Game Studios 
 * @class Splash.java
 *
 **/

public class Splash implements GameState 
{
	// Declaration of the logo image.
	
	private Image logo;
	
	
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
	

	// Lets the state know which Id it has.

	public int getID() 
	{
		return States.SPLASH_STATE.getId();
	}

	
	// Gets called when the state is created.
	
	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException 
	{
		logo = new Image(ResourceLoader.getResourceAsStream("assets/images/logo.png"), "Logo", false);
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
		graphics.setColor(new Color(255, 255, 255));
		graphics.fillRect(0, 0, gameContainer.getWidth(), gameContainer.getHeight());
		graphics.drawImage(logo, (gameContainer.getWidth() / 2) - (logo.getWidth() / 2), (gameContainer.getHeight() / 2) - (logo.getHeight() / 2));
	}
	
	
	// Gets called every frame.

	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException 
	{
		
	}
}