package com.dalthow.etaron;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.ImageIOImageData;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;

import com.dalthow.etaron.framework.Score;
import com.dalthow.etaron.framework.States;
import com.dalthow.etaron.handler.ResourceHandler;
import com.dalthow.etaron.states.Editor ;
import com.dalthow.etaron.states.Game;
import com.dalthow.etaron.states.Menu;
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
	public static int width = 960, height = 720;
	private static boolean fullScreen = false, vSync = false;

	// The location of the custom level folder.
	public static final String customLevelLocation = "/Dalthow/Games/Etaron/Custom Levels/";

	// Declaration of the Logger object.
	private static final Logger logger = LogManager.getLogger(Run.class);

	// Declaration of the ResourceHandler.
	public static ResourceHandler resourceHandler;

	public static String version = "0.2.0.0"; // TODO: Get this from the Maven pom.xml.


	// Constructor that creates the game container.
	public Run(String name) throws SlickException
	{
		super(name);

		AppGameContainer gameContainer = new AppGameContainer(this);

		// If full-screen is enabled, then reset the width and height so it fits the screen.
		if(fullScreen)
		{
			width = gameContainer.getScreenWidth();
			height = gameContainer.getScreenHeight();
		}

		resourceHandler = new ResourceHandler();
		resourceHandler.loadAudio();

		gameContainer.setDisplayMode(width, height, fullScreen);
		gameContainer.setTargetFrameRate(60);
		gameContainer.setAlwaysRender(true);
		gameContainer.setShowFPS(false);
		gameContainer.setUpdateOnlyWhenVisible(true);
		gameContainer.setVerbose(false);
		gameContainer.setVSync(vSync);
		gameContainer.start();
	}


	// Gets called whenever the window is closed.
	@Override
    public boolean closeRequested() 
	{
       logger.info("Stopping Etaron.");
       
       return true;
    }

	// Adding the states to the game container.
	@Override
	public void initStatesList(GameContainer gameContainer) throws SlickException
	{
		addState(new Splash());
		addState(new Menu());
		addState(new Game());
		addState(new Editor());

		enterState(States.SPLASH_STATE.getId());
	}


	// First method to get called.
	public static void main(String args[])
	{
		//System.setProperty("java.library.path", "natives/");

		try
		{
			Field fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");

			fieldSysPath.setAccessible(true);
			fieldSysPath.set(null, null);
		}

		catch(IllegalArgumentException | IllegalAccessException | SecurityException | NoSuchFieldException error)
		{
			logger.error(error);
		}

		// Fixing input plug-in for every windows version above 7.
		if(System.getProperty("os.name", "").trim().startsWith("Windows") && Float.parseFloat(System.getProperty("os.name", "").trim().substring(8)) > 7)
		{
			System.setProperty("jinput.useDefaultPlugin", "false");
			System.setProperty("net.java.games.input.plugins", "net.java.games.input.DirectAndRawInputEnvironmentPlugin");
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
						fullScreen = Boolean.parseBoolean(args[i].substring("-fullscreen=".length()));
					}

					else if(args[i].startsWith("-vsync="))
					{
						vSync = Boolean.parseBoolean(args[i].substring("-vsync=".length()));
					}
				}

				catch(Exception error)
				{
					logger.error(error);
				}
			}
		}

		// Starting the game.
		try
		{
			new Run("Etaron");
		}

		catch(SlickException error)
		{
			logger.error(error);
		}
	}
}
