package com.dalthow.etaron.states;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;

import com.dalthow.etaron.Run;
import com.dalthow.etaron.framework.States;
import com.dalthow.etaron.media.ImageResource;
import com.dalthow.etaron.utils.DrawUtils;

/**
 * Etaron
 *
 *
 * @author Dalthow Game Studios 
 * @class Menu.java
 *
 **/

public class Menu implements GameState 
{
	// Declaration of the images used in this state.
	
	private Image header;
	
	
	// Declaration of all the levels.
	
	public static List<Image> easyLevelPage = new ArrayList<Image>();
	public static List<Image> mediumLevelPage = new ArrayList<Image>();
	public static List<Image> hardLevelPage = new ArrayList<Image>();
	public static List<Image> customLevelPage = new ArrayList<Image>();
	
	
	// Declaration of the fonts used in this state.
	
	private Font bitbold;
	
	
	// Declaration of the mouse position.
	
	private int xMouse, yMouse;
	private Rectangle mousePixel;
	
	
	// Declaring some other variables.
	
	private String[] difficulties = {"Easy", "Medium", "Hard", "Custom"};
	private Color[] difficultyColors = {new Color(0, 255, 0), new Color(255, 255, 0), new Color(255, 0, 0), new Color(0, 255, 255)};
	private int page;
	
	
	// Default implementation for mouse.
	
	public void mouseClicked(int par1, int par2, int par3, int par4){}
	public void mouseDragged(int par1, int par2, int par3, int par4){}
	
	public void mouseMoved(int par1, int par2, int par3, int par4)
	{
		// Setting the mouse position equal to the declared class variables.
		
		xMouse = par3;
		yMouse = par4;
	}
	
	public void mousePressed(int par1, int par2, int par3){}
	public void mouseReleased(int par1, int par2, int par3){}
	public void mouseWheelMoved(int par1){}
	
	
	// Default implementation for general input.
	
	public void inputEnded(){}
	public void inputStarted(){}
	public void setInput(Input par1){}
	
	
	// Determines whether user input is allowed or not.
	
	public boolean isAcceptingInput() 
	{
		return true;
	}
	
	
	// Default implementation for controllers.
	
	public void controllerButtonPressed(int par1, int par2){}
	public void controllerButtonReleased(int par1, int par2){}
	public void controllerDownPressed(int par1){}
	public void controllerDownReleased(int par1){}
	public void controllerLeftPressed(int par1){}
	public void controllerLeftReleased(int par1){}
	public void controllerRightPressed(int par1){}
	public void controllerRightReleased(int par1){}
	public void controllerUpPressed(int par1){}
	public void controllerUpReleased(int par1){}
	
	
	// Default implementation for keyboards.
	
	public void keyPressed(int par1, char par2){}
	public void keyReleased(int par1, char par2){}
	

	// Lets the state know which Id it has.

	public int getID() 
	{
		return States.MENU_STATE.getId();
	}

	
	// Gets called when the state is created.
	
	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException 
	{
		try 
		{
			// Loading images.
			
			header = Run.resourceHandler.get(ImageResource.HEADER);
			
			
			// Loading fonts.
			
			InputStream inputStream = ResourceLoader.getResourceAsStream("assets/fonts/bit-bold.ttf");
			bitbold = Font.createFont(Font.TRUETYPE_FONT, inputStream);
		}
		
		catch(IOException | FontFormatException error) 
		{
			error.printStackTrace();
		}
		
		loadLevelImages();
		page = 0;
	}
	
	
	// Gets called when the player enters this state.

	public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException 
	{

	}
	
	
	// Gets called when the player leaves this state.

	public void leave(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException 
	{
		
	}

	
	// Gets called every frame.

	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException 
	{
		DrawUtils.drawBackground(graphics, gameContainer, new Color(20, 20, 20));
		DrawUtils.drawImageAtCenter(graphics, gameContainer, header, true, 50);
		DrawUtils.drawAdvancedString(graphics, gameContainer, difficulties[page], bitbold, difficultyColors[page], 32F, true, 140);
		
		graphics.setColor(new Color(255, 128, 0));
		graphics.fillRect(-300, 0, 325, gameContainer.getHeight());

		graphics.setColor(new Color(185, 0, 255));
		graphics.fillRect(25, 0, 10, gameContainer.getHeight());
		
		drawLevels(easyLevelPage, graphics, gameContainer);
	}
	
	
	// Gets called every frame.

	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException 
	{
		mousePixel = new Rectangle(xMouse, yMouse, 1, 1);
	}
	
	
	/**
     * loadLevelImages Loads all levels into their appropriate list.
     *
     * @return {void}
     */
	private void loadLevelImages() throws SlickException
	{
		Run.resourceHandler.loadLevels();

		
		// Looping trough all levels.
		
		for(ImageResource.Levels image : ImageResource.Levels.values())
		{
			if(image.getPath().contains("levels/easy"))
			{
				easyLevelPage.add(Run.resourceHandler.levels.get(image));
			}

			else if(image.getPath().contains("levels/medium"))
			{
				mediumLevelPage.add(Run.resourceHandler.levels.get(image));
			}

			else if(image.getPath().contains("levels/hard"))
			{
				hardLevelPage.add(Run.resourceHandler.levels.get(image));
			}
		}
		
		//addCustomLevels();
	}
	
	
	/**
     * drawLevels Draws the level images from a specific page on the screen.
     *
     * @param  {List<Image>} list            The List with levels.
     * @param  {Graphics} graphics           The Graphics object for the screen.
     * @param  {GameContainer} gameContainer The GameContainer for this game.
     *
     * @return {void}
     */
	private void drawLevels(List<Image> list, Graphics graphics, GameContainer gameContainer)
	{
		int col = 0;
		int row = 0;

		for(int i = 0; i < list.size(); i++)
		{
			list.get(i).setFilter(Image.FILTER_NEAREST);
			list.get(i).draw((gameContainer.getWidth() - 578) / 2 + (col * 150), 200 + (row * 150), 2);

			col++;
			
		
			// Going to the next row.
			
			if(col == 4)
			{
				col = 0;
				row++;
			}
		}
	}
}