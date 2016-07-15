package com.dalthow.etaron.handler;

import java.io.IOException;
import java.text.DecimalFormat ;
import java.text.DecimalFormatSymbols ;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager ;
import org.apache.log4j.Logger ;
import org.joda.time.DateTime ;
import org.joda.time.Interval ;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.dalthow.etaron.Run;
import com.dalthow.etaron.models.Identifier;
import com.dalthow.etaron.models.WorldObject;
import com.dalthow.etaron.media.ImageResource;
import com.dalthow.etaron.media.SoundResource;
import com.dalthow.etaron.objects.Block;
import com.dalthow.etaron.objects.Elevator;
import com.dalthow.etaron.objects.Item;
import com.dalthow.etaron.objects.Player;
import com.dalthow.etaron.objects.Sand;
import com.dalthow.etaron.objects.Turret;
import com.dalthow.etaron.states.Game;
import com.dalthow.etaron.utils.DrawUtils.DrawHelper ;

/**
 * Etaron
 *
 * @author Trevi Awater
 **/

public class ObjectHandler 
{
	// Declaration of the temporary objects, used for data sorting.
	private WorldObject temporaryObject;
	private Player temporaryPlayer;

	// Declaration of the Logger object.
	private static final Logger logger = LogManager.getLogger(ObjectHandler.class);

	// Declaration of the duration variables.
	public DateTime startTime;

	// Declaration of the level image.
	public Image currentLevel;
	
	// Declaration of the objects and players list.
	public List<WorldObject> objects = new ArrayList<>();
	public List<Player> players = new ArrayList<>();

	/**
     * Adds a WorldObjects to the objects List.
     *
     * @param object The WorldObject that should be added to the objects List.
     */
	public void addObject(WorldObject object)
	{
		objects.add(object);
	}

	/**
     * Removes a WorldObject from the objects List.
     *
     * @param object The WorldObject that should be removed from the objects List.
     */
	public void removeObject(WorldObject object)
	{
		objects.remove(object);
	}

	/**
     * Adds a Player to the players List.
     *
     * @param player The Player that should be added to the players List.
     */
	public void addPlayer(Player player)
	{
		players.add(player);
	}

	/**
     * Removes a Player to the players List.
     *
     * @param player The Player that should be removed from the players List.
     */
	public void removePlayer(Player player)
	{
		players.remove(player);
	}

	/**
     * Loads a level based on a image checking every pixel from the top left to the bottom right.
     *
     * @param level The Image that contains all the level data.
     *
	 * @throws SlickException, IOException
     */
	public void loadLevel(Image level) throws SlickException, IOException
	{
		startTime = new DateTime();
		
		clearLevel();

		int imageWidth = level.getWidth();
		int imageHeight = level.getHeight();

		currentLevel = level;

		for(int i = 0; i < imageWidth; i++)
		{
			for(int j = 0; j < imageHeight; j++)
			{
				if(level.getColor(i, j).equals(DrawHelper.BLOCK.getColor()))
				{
					addObject(new Block((i * 32), (j * 32), Identifier.BLOCK, true));
				}
				
				else if(level.getColor(i, j).equals(DrawHelper.DECOR.getColor()))
				{
					addObject(new Block((i * 32), (j * 32), Identifier.DECOR, false));
				}

				else if(level.getColor(i, j).equals(DrawHelper.LAVA.getColor()))
				{
					addObject(new Block((i * 32), (j * 32), Identifier.LAVA, false));
				}
				
				else if(level.getColor(i, j).equals(DrawHelper.COIN.getColor()))
				{
					addObject(new Item((i * 32), (j * 32), Identifier.COIN, false, Run.resourceHandler.get(ImageResource.COIN, false), Run.resourceHandler.get(SoundResource.COIN), 0, 0, true, false));
				}
				
				else if(level.getColor(i, j).equals(DrawHelper.KEY.getColor()))
				{
					addObject(new Item((i * 32), (j * 32), Identifier.KEY, false, Run.resourceHandler.get(ImageResource.KEY, false), Run.resourceHandler.get(SoundResource.CHAIN), -17, 16, false, true));
				}
				
				else if(level.getColor(i, j).equals(DrawHelper.TURRET.getColor()))
				{
					addObject(new Turret((i * 32), (j * 32 - 8), Identifier.TURRET, true));
				}
				
				else if(level.getColor(i, j).equals(DrawHelper.FLAG.getColor()))
				{
					addObject(new Block((i * 32), (j * 32), Identifier.FLAG, false));
				}
				
				else if(level.getColor(i, j).equals(DrawHelper.DOOR.getColor()))
				{
					addObject(new Block((i * 32), (j * 32), Identifier.DOOR, true));
				}
				
				else if(level.getColor(i, j).equals(DrawHelper.SAND.getColor()))
				{
					addObject(new Sand((i * 32), (j * 32), Identifier.SAND, true));
				}
				
				else if(level.getColor(i, j).equals(DrawHelper.JELLY.getColor()))
				{
					addObject(new Block((i * 32), (j * 32), Identifier.JELLY, true));
				}

				else if(level.getColor(i, j).equals(DrawHelper.ELEVATOR_DOWN.getColor()))
				{
					addObject(new Elevator((i * 32), (j * 32), 0, 8, Identifier.ELEVATOR, true));
				}
				
				else if(level.getColor(i, j).equals(DrawHelper.ELEVATOR_UP.getColor()))
				{
					addObject(new Elevator((i * 32), (j * 32), 1, 8, Identifier.ELEVATOR, true));
				}
				
				else if(level.getColor(i, j).equals(DrawHelper.PLAYER.getColor()))
				{
					addPlayer(new Player((i * 32), (j * 32), Identifier.PLAYER, true));
				}
			}
		}
		
		for(int i = 0; i < players.size(); i++)
		{
			WorldObject temporaryPlayer = players.get(i);

			if(temporaryPlayer.getId() == Identifier.PLAYER)
			{
				Game.cameraFocus = players.get(i);
			}
		}
	}

	/**
     * Reloads the current level.
     */
	public void reloadLevel()
	{
		try 
		{
			loadLevel(currentLevel);
		} 
		
		catch(IOException | SlickException error) 
		{
			logger.error(error);
		}
	}

	/**
     * clearLevel Clears the entire level so a new one can be loaded.
     */
	private void clearLevel() 
	{
		objects.clear();
		players.clear();
	}

	/**
     * Makes all the world objects tick.
     */
	public void tick()
	{
		for(int i = 0; i < objects.size(); i++)
		{
			temporaryObject = objects.get(i);
			temporaryObject.tick(objects);
		}
		
		for(int i = 0; i < players.size(); i++) 
		{
			temporaryPlayer = players.get(i);
			temporaryPlayer.tick(objects);
		}
	}

	/**
     * Makes all the world objects render.
     */
	public void render(Graphics graphics)
	{
		for(int i = 0; i < objects.size(); i++)
		{
			temporaryObject = objects.get(i);

			// Checking if this object is on the screen, if not don't draw it.
			if(temporaryPlayer != null && ((Player)Game.cameraFocus).getRenderBounds().intersects(temporaryObject.getBounds()))
			{	
				temporaryObject.render(graphics);
			}
		}

		// Rendering the players above all objects.
		for(int i = 0; i < players.size(); i++) 
		{
			temporaryPlayer = players.get(i);
			temporaryPlayer.render(graphics);
		}
	}

	/**
     * Goes to the next level.
     *
     * @return float The amount of seconds it took to complete the previous level.
     */
	public double nextLevel() throws SlickException
	{
		DecimalFormat decimalFormat = new DecimalFormat("#.###");
	    
		DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols();
	    formatSymbols.setDecimalSeparator('.');
	    decimalFormat.setDecimalFormatSymbols(formatSymbols);
		
		DateTime endTime = new DateTime();
		Interval duration = new Interval(startTime, endTime);
		
		reloadLevel();

		return Double.parseDouble(decimalFormat.format((double)duration.toDurationMillis() / 1000));
	}
}