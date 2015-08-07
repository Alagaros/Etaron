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

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.dalthow.etaron.framework.States;
import com.dalthow.etaron.handler.ResourceHandler;
import com.dalthow.etaron.network.TransferData;
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
	
	
	// Declaration of the ResourceHandler.
	
	public static ResourceHandler resourceHandler;
	
	
	// Declaration for the authentication variables.
	
	private static String email;
	private transient static String password;
	
	public static String accessToken;
	
	public static String version = "0.2.0.0"; // TODO: Get this from the Maven pom.xml.
	
	
	// Constructor that creates the game container.
	
	public Run(String name) throws SlickException 
	{
		super(name);
		
		AppGameContainer gameContainer = new AppGameContainer(this);
		
		
		// Tries to log the user in if he entered credentials.
		
		if(email != null && password != null)
		{
			accessToken = attemptLogin(email, password);
		}
		
				
		// If full-screen is enabled, then reset the width and height so it fits the screen.
		
		if(fullScreen)
		{
			width = gameContainer.getScreenWidth();
			height = gameContainer.getScreenHeight();
		}
		
		resourceHandler = new ResourceHandler();
		
		gameContainer.setDisplayMode(width, height, fullScreen);
		gameContainer.setTargetFrameRate(60);
		gameContainer.setAlwaysRender(true);
		gameContainer.setShowFPS(false);
		gameContainer.setUpdateOnlyWhenVisible(true);
		gameContainer.setVerbose(false);
		gameContainer.setVSync(vSync);
		gameContainer.start();
	}
	
	
	// First method to get called.
	
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
		
		
		// Fixing input plug-in for every windows version above 7.
		
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
						fullScreen = Boolean.parseBoolean(args[i].substring("-fullscreen=".length()));
					}
					
					else if(args[i].startsWith("-vsync="))
					{
						vSync = Boolean.parseBoolean(args[i].substring("-vsync=".length()));
					}
					
					else if(args[i].startsWith("-email="))
					{
						email = args[i].substring("-email=".length());
					}
					
					else if(args[i].startsWith("-password="))
					{
						password = args[i].substring("-password=".length());
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
			new Run("Etaron");
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
		addState(new Menu());
		addState(new Game());
		
		enterState(States.SPLASH_STATE.getId());
	}
	
	/**
     * attemptLogin Tries to log the user in and obtain an access token.
     *
     * @param  {String} email    The users email.
     * @param  {String} password The users password.
     * 
     * @return {String}			  The access token.		
     */
	private String attemptLogin(String email, String password)
	{
		BasicNameValuePair[] authenticationData = new BasicNameValuePair[3];
		authenticationData[0] = new BasicNameValuePair("email", email);
		authenticationData[1] = new BasicNameValuePair("password", password);
		authenticationData[2] = new BasicNameValuePair("getToken", "true");
		
		System.out.println(TransferData.postData("http://datayma.dalthow.com/share/scripts/php/authentication.php", authenticationData));
		
		return null;
	}
}
