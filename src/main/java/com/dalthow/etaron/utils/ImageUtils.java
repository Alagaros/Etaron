package com.dalthow.etaron.utils;

import org.newdawn.slick.Image ;

/**
 * Etaron
 *
 *
 * @author Dalthow Game Studios 
 * @class ImageUtils.java
 *
 **/

public class ImageUtils
{
	/**	
	 * isValidLevel Checks if a level Image has a player and exit.
	 * 
	 * @param  {Image} level The level that should be checked.
	 * 
	 * @return {boolean}
	 */
	public static boolean isValidLevel(Image level)
	{
		// Checking if the level has the correct size.
		
		if(level.getWidth() == 64 && level.getHeight() == 64)
		{
			// Declaration of the flags.
			
			boolean hasPlayer = false, hasExit = false;
			
			
			// Looping trough the horizontal pixels.
			
			for(int i = 0; i < level.getWidth(); i++)
			{
				// Looping trough the vertical pixels.
				
				for(int j = 0; j < level.getHeight(); j++)
				{
					// Checking pixel color.
					
					int red = level.getColor(i, j).getRed();
					int blue = level.getColor(i, j).getBlue();
					int green = level.getColor(i, j).getGreen();

					if(red == 0 && green == 0 && blue == 255)
					{
						hasPlayer = true;
					}

					else if(red == 0 && green == 255 && blue == 0)
					{
						hasExit = true;
					}
				}
			}
			
			return (hasPlayer && hasExit);
		}
		
		return false;
	}
}
