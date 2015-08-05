package com.dalthow.etaron.handler;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.dalthow.etaron.framework.Identifier;
import com.dalthow.etaron.framework.WorldObject;
import com.dalthow.etaron.objects.Block;
import com.dalthow.etaron.objects.Coin;
import com.dalthow.etaron.objects.Player;
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
     * addObject Adds a object to the objects list.
     *
     * @param  {WorldObject} object The WorldObject that should be added to the objects list.
     * 
     * @return {void}
     */
	public void addObject(WorldObject object)
	{
		objects.add(object);
	}

	
	/**
     * removeObject Removes a object from the objects list.
     *
     * @param  {WorldObject} object The WorldObject that should be removed from the objects list.
     * 
     * @return {void}
     */
	public void removeObject(WorldObject object)
	{
		objects.remove(object);
	}
	
	
	/**
     * loadLevel Loads a level based on a image checking every pixel from the top left to the bottom right.
     *
     * @param  {Image} image The Image that contains all the level data.
     * 
     * @return {void}
     */
	public void loadLevel(Image image) throws SlickException
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

				else if(red == 255 && green == 0 && blue == 0)
				{
					addObject(new Block((i * 32), (j * 32), Identifier.LAVA, false));
				}
				
				else if(red == 255 && green == 255 && blue == 0)
				{
					addObject(new Coin((i * 32), (j * 32), Identifier.COIN, false));
				}
				
				else if(red == 0 && green == 0 && blue == 255)
				{
					addObject(new Player((i * 32), (j * 32), Identifier.PLAYER, true));
				}
			}
		}
		
		for(int i = 0; i < objects.size(); i++)
		{
			WorldObject temporaryObject = objects.get(i);

			if(temporaryObject.getId() == Identifier.PLAYER)
			{
				Game.cameraFocus = objects.get(i);
				Game.cameraObject.tick(Game.cameraFocus);
			}
		}
	}
	
	
	/**
     * reloadLevel Reloads the current level.
     *
     * @return {void}
     */
	public void reloadLevel() throws SlickException
	{
		loadLevel(currentLevel);
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
		players.clear();

		for(int i = 0; i < objects.size(); i++)
		{
			// TODO: Look at this logic, it looks wanky.
			
			temporaryObject = objects.get(i);

			if(temporaryObject.getId() == Identifier.PLAYER)
			{
				players.add((Player) temporaryObject);
			}

			if(temporaryObject.getId() != Identifier.PLAYER)
			{
				temporaryObject.tick(objects);
			}

			else
			{
				for(int j = 0; j < players.size(); j++)
				{
					temporaryPlayer = players.get(j);

					if(temporaryPlayer != null && temporaryPlayer.getUpdateBounds().intersects(temporaryObject.getBounds()))
					{
						temporaryObject.tick(objects);
					}
				}
			}
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

			for(int j = 0; j < players.size(); j++)
			{
				temporaryPlayer = players.get(j);

				if(temporaryPlayer != null && temporaryPlayer.getRenderBounds().intersects(temporaryObject.getBounds()))
				{	
					temporaryObject.render(graphics);
				}
			}
		}
	}
}
