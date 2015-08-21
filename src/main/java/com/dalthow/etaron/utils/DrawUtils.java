package com.dalthow.etaron.utils;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.TrueTypeFont;

/**
 * Etaron
 *
 *
 * @author Dalthow Game Studios
 * @class ImageUtils.java
 *
 **/

public class DrawUtils
{
	/**
     * drawImageAtCenter Draws a image on the horizontal, vertical or both axis on the screen.
     *
     * @param  {Graphics} graphics           The Graphics object for the screen.
     * @param  {GameContainer} gameContainer The GameContainer for this game.
     * @param  {Image} image                 The Image that should be drawn.
     * @param  {Object} xAxis 				 Can be either a boolean or an integer, if the boolean is true it will center it on the horizontal axis. If its a integer it will be drawn at that position.
     * @param  {Object} yAxis 				 Can be either a boolean or an integer, if the boolean is true it will center it on the vertical axis. If its a integer it will be drawn at that position.
     *
     * @return {void}
     */
	public static void drawImageAtCenter(Graphics graphics, GameContainer gameContainer, Image image, Object xAxis, Object yAxis)
	{
		// Declaring the position variables.

		int xPos = 0, yPos = 0;


		// Checking the data type.

		if(xAxis instanceof Boolean && (boolean)xAxis == true)
		{
			xPos = (gameContainer.getWidth() / 2) - (image.getWidth() / 2);
		}

		else if(xAxis instanceof Integer)
		{
			xPos = (int)xAxis;
		}


		// Checking the data type.

		if(yAxis instanceof Boolean && (boolean)yAxis == true)
		{
			yPos = (gameContainer.getHeight() / 2) - (image.getHeight() / 2);
		}

		else if(yAxis instanceof Integer)
		{
			yPos = (int)yAxis;
		}


		// Drawing the image.

		graphics.drawImage(image, xPos, yPos);
	}


	/**
     * drawBackground Draws the background.
     *
     * @param  {Graphics} graphics           The Graphics object for the screen.
     * @param  {GameContainer} gameContainer The GameContainer for this game.
     * @param  {Color} color                 The Color the background should have.
     *
     * @return {void}
     */
	public static void drawBackground(Graphics graphics, GameContainer gameContainer, Color color)
	{
		graphics.setColor(color);
		graphics.fillRect(0, 0, gameContainer.getWidth(), gameContainer.getHeight());
	}


	/**
     * drawAdvancedString Draws a string of text on the screen. This method allows you to set a font, color, size and position.
     *
     * @param  {Graphics} graphics           The Graphics object for the screen.
     * @param  {GameContainer} gameContainer The GameContainer for this game.
     * @param  {String} text                 The text that should be drawn.
     * @param  {TrueTypeFont} font           The font that should be used.
     * @param  {Color} color				 The color the text should have.
     * @param  {float} size					 The size of the text.
     * @param  {Object} xAxis 				 Can be either a boolean or an integer, if the boolean is true it will center it on the horizontal axis. If its a integer it will be drawn at that position.
     * @param  {Object} yAxis 				 Can be either a boolean or an integer, if the boolean is true it will center it on the vertical axis. If its a integer it will be drawn at that position.
     *
     * @return {void}
     */
	public static void drawAdvancedString(Graphics graphics, GameContainer gameContainer, String text, TrueTypeFont font, Color color, Object xAxis, Object yAxis)
	{
		// Declaring the position variables.

		int xPos = 0, yPos = 0;


		// Checking the data type.

		if(xAxis instanceof Boolean && (boolean)xAxis == true)
		{
			xPos = (gameContainer.getWidth() / 2) - (font.getWidth(text) / 2);
		}

		else if(xAxis instanceof Integer)
		{
			xPos = (int)xAxis;
		}


		// Checking the data type.

		if(yAxis instanceof Boolean && (boolean)yAxis == true)
		{
			yPos = (gameContainer.getHeight() / 2) - (font.getHeight(text) / 2);
		}

		else if(yAxis instanceof Integer)
		{
			yPos = (int)yAxis;
		}


		// Drawing the text.

		graphics.setColor(color);
		graphics.setFont(font);
		graphics.drawString(text, xPos, yPos);
	}


	// A list of all the objects with their default color.

	public static enum DrawHelper
	{
		VOID(new Color(0, 0, 0)), 
		BLOCK(new Color(255, 255, 255)),
		PLAYER(new Color(0, 0, 255)), 
		LAVA(new Color(255, 0, 0)), 
		FLAG(new Color(0, 255, 0)), 
		COIN(new Color(255, 255, 0)), 
		ELEVATOR_UP(new Color(0, 255, 255)), 
		ELEVATOR_DOWN(new Color(255, 0, 255)), 
		DOOR(new Color(137, 69, 19)), 
		KEY(new Color(192, 192, 192)), 
		TURRET(new Color(255, 128, 0)), 
		JELLY(new Color(255, 175, 175)),
		DECOR(new Color(128, 128, 128)),
		SAND(new Color(139, 139, 0));


		// Declaration of the object's Color and description.

		private Color objectColor;


		// Constructor that fills in the declared variables.

		DrawHelper(Color objectColor)
		{
			this.objectColor = objectColor;
		}

		
		// Getter.
		
		public Color getColor()
		{
			return objectColor;
		}
	}
}