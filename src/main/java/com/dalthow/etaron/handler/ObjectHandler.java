package com.dalthow.etaron.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.dalthow.etaron.Run;
import com.dalthow.etaron.framework.Identifier;
import com.dalthow.etaron.framework.WorldObject;
import com.dalthow.etaron.media.ImageResource;
import com.dalthow.etaron.media.SoundResource;
import com.dalthow.etaron.objects.Block;
import com.dalthow.etaron.objects.Item;
import com.dalthow.etaron.objects.Player;
import com.dalthow.etaron.objects.Turret;
import com.dalthow.etaron.states.Game;

/**
 * Etaron
 *
 *
 * @author Dalthow Game Studios 
 * @class ObjectHandler.java
 *
 **/

public class ObjectHandler 
{
	// Declaration of the temporary objects, used for data sorting.

	private WorldObject temporaryObject;
	private Player temporaryPlayer;
	
	
	// Declaration of the level image.
	
	private Image currentLevel;

	
	// Declaration of the objects and players list.
	
	public List<WorldObject> objects = new ArrayList<>();
	public List<Player> players = new ArrayList<>();
	
	
	/**
     * addObject Adds a WorldObjects to the objects List.
     *
     * @param  {WorldObject} object The WorldObject that should be added to the objects List.
     * 
     * @return {void}
     */
	public void addObject(WorldObject object)
	{
		objects.add(object);
	}

	
	/**
     * removeObject Removes a WorldObject from the objects List.
     *
     * @param  {WorldObject} object The WorldObject that should be removed from the objects List.
     * 
     * @return {void}
     */
	public void removeObject(WorldObject object)
	{
		objects.remove(object);
	}
	
	
	/**
     * addPlayer Adds a Player to the players List.
     *
     * @param  {Player} player The Player that should be added to the players List.
     * 
     * @return {void}
     */
	public void addPlayer(Player player)
	{
		players.add(player);
	}

	
	/**
     * removePlayer Removes a Player to the players List.
     *
     * @param  {Player} player The Player that should be removed from the players List.
     * 
     * @return {void}
     */
	public void removePlayer(Player player)
	{
		players.remove(player);
	}
	
	
	/**
     * loadLevel Loads a level based on a image checking every pixel from the top left to the bottom right.
     *
     * @param  {Image} image The Image that contains all the level data.
     * 
     * @return {void}
     * 
	 * @throws {SlickException, IOException} 
     */
	public void loadLevel(Image image) throws SlickException, IOException
	{
		clearLevel();

		int imageWidth = image.getWidth();
		int imageHeight = image.getHeight();

		currentLevel = image;

		for(int i = 0; i < imageWidth; i++)
		{
			for(int j = 0; j < imageHeight; j++)
			{
				int red = image.getColor(i, j).getRed();
				int blue = image.getColor(i, j).getBlue();
				int green = image.getColor(i, j).getGreen();

				if(red == 255 && green == 255 && blue == 255)
				{
					addObject(new Block((i * 32), (j * 32), Identifier.BLOCK, true));
				}
				
				if(red == 128 && green == 128 && blue == 128)
				{
					addObject(new Block((i * 32), (j * 32), Identifier.DECOR, false));
				}

				else if(red == 255 && green == 0 && blue == 0)
				{
					addObject(new Block((i * 32), (j * 32), Identifier.LAVA, false));
				}
				
				else if(red == 255 && green == 255 && blue == 0)
				{
					addObject(new Item((i * 32), (j * 32), Identifier.COIN, false, Run.resourceHandler.get(ImageResource.COIN, false), Run.resourceHandler.get(SoundResource.COIN), 0, 0, true, false));
				}
				
				else if(red == 192 && green == 192 && blue == 192)
				{
					addObject(new Item((i * 32), (j * 32), Identifier.KEY, false, Run.resourceHandler.get(ImageResource.KEY, false), Run.resourceHandler.get(SoundResource.CHAIN), -17, 16, false, true));
				}
				
				else if(red == 255 && green == 128 && blue == 0)
				{
					addObject(new Turret((i * 32), (j * 32 - 8), Identifier.TURRET, true));
				}
				
				else if(red == 0 && green == 255 && blue == 0)
				{
					addObject(new Block((i * 32), (j * 32), Identifier.FLAG, false));
				}
				
				else if(red == 139 && green == 69 && blue == 19)
				{
					addObject(new Block((i * 32), (j * 32), Identifier.DOOR, true));
				}
				
				else if(red == 255 && green == 175 && blue == 175)
				{
					addObject(new Block((i * 32), (j * 32), Identifier.JELLY, true));
				}
				
				else if(red == 0 && green == 0 && blue == 255)
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
     * reloadLevel Reloads the current level.
     *
     * @return {void}
     * 
     * @throws {SlickException}
     */
	public void reloadLevel() throws SlickException
	{
		try 
		{
			loadLevel(currentLevel);
		} 
		
		catch(IOException error) 
		{
			error.printStackTrace();
		}
	}

	
	/**
     * clearLevel Clears the entire level so a new one can be loaded.
     *
     * @return {void}
     */
	private void clearLevel() 
	{
		objects.clear();
		players.clear();
	}


	/**
     * tick Makes all the world objects tick.
     *
     * @return {void}
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
     * render Makes all the world objects render.
     *
     * @return {void}
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
}