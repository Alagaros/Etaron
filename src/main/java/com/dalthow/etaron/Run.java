package com.dalthow.etaron;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.ImageIOImageData;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;

import com.dalthow.etaron.handler.States;
import com.dalthow.etaron.states.Splash;

/**
 * Etaron
 *
 *
 * @author Dalthow Game Studios 
 * @class Run.java
 *
 **/

public class Run extends StateBasedGame
{	
	// Declaration of resolution.
	
	private static int width = 960, height = 720;
	private static boolean fullscreen = false;
	
	
	// Constructor that creates the game container.
	
	public Run(String name) throws SlickException 
	{
		super(name);
		
		AppGameContainer gameContainer = new AppGameContainer(this);
		
		
		// If full-screen is enabled, then reset the width and height so it fits the screen.
		
		if(fullscreen)
		{
			width = gameContainer.getScreenWidth();
			height = gameContainer.getScreenHeight();
		}
		
		gameContainer.setDisplayMode(width, height, fullscreen);
		gameContainer.setTargetFrameRate(60);
		gameContainer.setAlwaysRender(true);
		gameContainer.setShowFPS(false);
		gameContainer.setUpdateOnlyWhenVisible(true);
		gameContainer.setVerbose(false);
		gameContainer.start();
	}

	public static void main(String args[])
	{
		System.setProperty("java.library.path", "natives/");
		 
		try 
		{
			Field fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");

			fieldSysPath.setAccessible(true);
			fieldSysPath.set(null, null);
		}
		
		catch(IllegalArgumentException | IllegalAccessException | SecurityException | NoSuchFieldException error) 
		{
			error.printStackTrace();
		}
		
		
		// Fixing input plugin for every windows version above 7.
		
		if(System.getProperty("os.name", "").trim().startsWith("Windows") && Float.parseFloat(System.getProperty("os.name", "").trim().substring(8)) > 7)
		{
			System.setProperty("jinput.useDefaultPlugin", "false");
			System.setProperty("net.java.games.input.plugins", "net.java.games.input.DirectAndRawInputEnvironmentPlugin");
		}
		
		
		// Setting the program icon.
		
		try
		{
			Display.setIcon(new ByteBuffer[]
			{ 
				new ImageIOImageData().imageToByteBuffer(ImageIO.read(ResourceLoader.getResourceAsStream("assets/images/icon-16.png")), false, false, null), 
				new ImageIOImageData().imageToByteBuffer(ImageIO.read(ResourceLoader.getResourceAsStream("assets/images/icon-32.png")), false, false, null) 
			});
		}
		
		catch(IOException error)
		{
			error.printStackTrace();
		}
		
		
		// Checking if any arguments are provided.
		
		if(args != null)
		{
			// Checking for them individually so the order doesn't matter.
			
			for(int i = 0; i < args.length; i++)
			{
				// In case not the parameters are invalid, the program still continues on its default values.
				
				try
				{
					if(args[i].startsWith("-width="))
					{
						width = Integer.parseInt(args[i].substring("-width=".length()));
					}

					else if(args[i].startsWith("-height="))
					{
						height = Integer.parseInt(args[i].substring("-heigth=".length()));
					}
					
					else if(args[i].startsWith("-fullscreen="))
					{
						fullscreen = Boolean.parseBoolean(args[i].substring("-fullscreen=".length()));
					}
				}
				
				catch(Exception error)
				{
					error.printStackTrace();
				}
			}
		}
		
		
		// Starting the game.
		
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
		addState(new Splash());
//		addState(menu);
//		addState(game);
//		addState(editor);
		
		enterState(States.SPLASH_STATE.getId());
	}
}
