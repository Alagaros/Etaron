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
import com.dalthow.etaron.states.Game;
import com.dalthow.etaron.states.Menu;
import com.dalthow.etaron.states.Splash;
import com.dalthow.etaron.utils.LogUtils ;
import com.dalthow.etaron.utils.NetworkUtils;

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


	// Declaration of the Logger object.

	private static final Logger logger = LogManager.getLogger(Run.class);


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


		// Getting scores and sending some client information.

		if(isLoggedIn())
		{
			JSONArray scores = getScores();

			for(int i = 0; i < scores.length(); i++)
			{
				try
				{
					JSONObject temporaryObject = scores.getJSONObject(i);

					Menu.scores.add(new Score(temporaryObject.getInt("coins"), temporaryObject.getInt("level"), temporaryObject.getDouble("duration")));
				}

				catch(JSONException error)
				{
					logger.error(error);
				}
			}

			sendSystemInfo();
		}


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
			logger.error(error);
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
			{new ImageIOImageData().imageToByteBuffer(ImageIO.read(ResourceLoader.getResourceAsStream("assets/images/icon-16.png")), false, false, null), new ImageIOImageData().imageToByteBuffer(ImageIO.read(ResourceLoader.getResourceAsStream("assets/images/icon-32.png")), false, false, null)});
		}

		catch(IOException error)
		{
			logger.error(error);
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
	 * @param {String} email 	The users email.
	 * @param {String} password The users password.
	 * 
	 * @return {String} The access token.
	 */
	private String attemptLogin(String email, String password)
	{
		BasicNameValuePair[] authenticationData = new BasicNameValuePair[3];
		authenticationData[0] = new BasicNameValuePair("email", email);
		authenticationData[1] = new BasicNameValuePair("password", password);
		authenticationData[2] = new BasicNameValuePair("type", "token");

		try
		{
			JSONObject response = new JSONObject(NetworkUtils.postData("http://www.dalthow.com/share/scripts/php/authentication.php", authenticationData));

			if(response.getString("status").matches("success"))
			{
				logger.info("Authentication succesfull, obtained token.");
				
				return response.getString("token");
			}

			else
			{
				logger.warn("Could not log in server responded with: " + LogUtils.formatServerMessage(response.getString("status")));
			}
		}

		catch(JSONException error)
		{
			logger.error(error);
		}

		return null;
	}


	/**
	 * getScores Tries to get the previous scores from a user.
	 *
	 * @return {String} The json String.
	 */
	private JSONArray getScores()
	{
		BasicNameValuePair[] scoresData = new BasicNameValuePair[1];
		scoresData[0] = new BasicNameValuePair("accessToken", accessToken);

		try
		{
			JSONObject response = new JSONObject(NetworkUtils.postData("http://www.dalthow.com/share/games/etaron/get-scores.php", scoresData));

			if(response.getString("status").matches("success"))
			{
				logger.info("Obtained player scores.");
				
				return response.getJSONArray("scores");
			}

			else
			{
				logger.warn("Could not obtain scores server responded with: " + LogUtils.formatServerMessage(response.getString("status")));
			}
		}

		catch(JSONException error)
		{
			logger.error(error);
		}

		return null;
	}

	
	/**
	 * sendSystemInfo Sends some system platform information to the server. This is used for additional tweaks later.
	 *
	 * @return {void}
	 */
	private boolean sendSystemInfo()
	{
		BasicNameValuePair[] clientData = new BasicNameValuePair[4];
		clientData[0] = new BasicNameValuePair("accessToken", accessToken);
		clientData[1] = new BasicNameValuePair("java", System.getProperty("java.version"));
		clientData[2] = new BasicNameValuePair("os", System.getProperty("os.name"));
		clientData[3] = new BasicNameValuePair("arch", System.getProperty("os.arch"));

		try
		{
			JSONObject response = new JSONObject(NetworkUtils.postData("http://www.dalthow.com/share/scripts/php/upload-system-info.php", clientData));

			if(response.getString("status").matches("success"))
			{
				logger.info("Succesfully send system info to server.");
				
				return true;
			}

			else
			{
				logger.warn("Could not send system info: " + LogUtils.formatServerMessage(response.getString("status")));
			}
		}

		catch(JSONException error)
		{
			logger.error(error);
		}

		return false;
	}
	
	
	/**
	 * isLoggedIn Checks if the access token is empty.
	 *
	 * @return {boolean} If the user is logged in or not.
	 */
	public static boolean isLoggedIn()
	{
		return accessToken != null;
	}
}
