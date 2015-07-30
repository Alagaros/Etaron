/**
 * Etaron
 *
 *
 * @Author Dalthow Game Studios 
 * @Class Run.java
 *
 **/

package com.dalthow.etaron;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.dalthow.etaron.handler.States;
import com.dalthow.etaron.states.Editor;
import com.dalthow.etaron.states.Game;
import com.dalthow.etaron.states.Menu;
import com.dalthow.etaron.states.Splash;

public class Run extends StateBasedGame
{
	// Declaring game states.
	
	private Splash splash;
	private Menu menu;
	private Game game;
	private Editor editor;
	
	
	// Constructor that creates the game container.
	
	public Run(String name) throws SlickException 
	{
		super(name);
		
		AppGameContainer container = new AppGameContainer(this);

		container.setDisplayMode(960, 720, false);
		container.setTargetFrameRate(60);
		container.setAlwaysRender(true);
		container.setShowFPS(false);
		container.setUpdateOnlyWhenVisible(true);
		container.setVerbose(false);
		container.start();
	}

	public static void main(String args[])
	{
		// Fixing input plugin for every windows version above 7
		
		if(System.getProperty("os.name", "").trim().startsWith("Windows") && Float.parseFloat(System.getProperty("os.name", "").trim().substring(8)) > 7)
		{
			System.setProperty("jinput.useDefaultPlugin", "false");
			System.setProperty("net.java.games.input.plugins", "net.java.games.input.DirectAndRawInputEnvironmentPlugin");
		}
		
		try 
		{
			new Run("Etaron"); // TODO Create some configuration file, so we can put the version number and resolution in there.
		} 
		
		catch(SlickException error) 
		{
			error.printStackTrace();
		}
	}

	
	// Adding the states to the game container.
	
	@Override
	public void initStatesList(GameContainer gameContainer) throws SlickException 
	{
		addState(splash);
//		addState(menu);
//		addState(game);
//		addState(editor);
		
		enterState(States.SPLASH_STATE.getId());
	}
}
