package com.dalthow.etaron.states;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File ;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager ;
import org.apache.log4j.Logger ;
import org.lwjgl.input.Keyboard ;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;

import com.dalthow.etaron.Run;
import com.dalthow.etaron.framework.Score ;
import com.dalthow.etaron.framework.States;
import com.dalthow.etaron.media.ImageResource;
import com.dalthow.etaron.utils.DrawUtils;
import com.dalthow.etaron.utils.FileUtils ;
import com.dalthow.etaron.utils.ImageUtils ;

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
	
	private Image header, arrowLeft, arrowRight, plus;
	
	
	// Declaration of the Logger object.

	private static final Logger logger = LogManager.getLogger(Menu.class);
		
	
	// Declaration of the rectangles used for click detection.
	
	private Rectangle pageBack, pageNext, newLevel;
	private Rectangle levels[];
	
	
	// Declaration of all the levels and scores.
	
	public static List<Image> easyLevelPage = new ArrayList<Image>();
	public static List<Image> mediumLevelPage = new ArrayList<Image>();
	public static List<Image> hardLevelPage = new ArrayList<Image>();
	public static List<Image> customLevelPage = new ArrayList<Image>();
	
	public static List[] allPages = {easyLevelPage, mediumLevelPage, hardLevelPage, customLevelPage};
	
	public static List<Score> scores = new ArrayList<Score>();
	
	
	// Declaration of the fonts used in this state.
	
	private TrueTypeFont difficultyFont, infoFont;
	
	
	// Declaration of the mouse position and states.
	
	private int xMouse, yMouse;
	private boolean mouseDown;
	private Rectangle mousePixel;
	
	
	// Declaring some other variables.
	
	private String[] difficulties = {"Easy", "Medium", "Hard", "Custom"};
	private Color[] difficultyColors = {new Color(0, 255, 0), new Color(255, 255, 0), new Color(255, 0, 0), new Color(0, 255, 255)};
	private int page;

	
	// Default implementation for mouse.
	
	public void mouseClicked(int par1, int par2, int par3, int par4){}
	
	public void mouseDragged(int par1, int par2, int par3, int par4)
	{
		// Setting the mouse position equal to the declared class variables.
		
		xMouse = par3;
		yMouse = par4;
	}
	
	public void mouseMoved(int par1, int par2, int par3, int par4)
	{
		// Setting the mouse position equal to the declared class variables.
		
		xMouse = par3;
		yMouse = par4;
	}
	
	public void mousePressed(int par1, int par2, int par3)
	{
		mouseDown = true;
	}
	
	public void mouseReleased(int par1, int par2, int par3)
	{
		mouseDown = false;
	}
	
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
	
	public void keyPressed(int par1, char par2)
	{
		if(par1 == Keyboard.KEY_F5)
		{
			try
			{
				loadCustomLevels();
			}
			
			catch(SlickException error)
			{
				logger.error(error);
			}
		}
	}
	
	public void keyReleased(int par1, char par2){}
	

	// Lets the state know which id it has.

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
			
			header = Run.resourceHandler.get(ImageResource.HEADER, false);
			arrowLeft = Run.resourceHandler.get(ImageResource.ARROW, false);
			arrowRight = Run.resourceHandler.get(ImageResource.ARROW, false);
			plus = Run.resourceHandler.get(ImageResource.PLUS, false);
			
			
			// Loading fonts.
			
			InputStream inputStream = ResourceLoader.getResourceAsStream("assets/fonts/bit-bold.ttf");
			Font bitBold = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			
			difficultyFont = new TrueTypeFont(bitBold.deriveFont(32F), false);
			infoFont = new TrueTypeFont(bitBold.deriveFont(10F), false);
		}
		
		catch(IOException | FontFormatException error) 
		{
			logger.error(error);
		}
		
		
		// Adding filters to the images.
		
		arrowRight.setFilter(Image.FILTER_NEAREST);
		arrowLeft.setFilter(Image.FILTER_NEAREST);
		plus.setFilter(Image.FILTER_NEAREST);
		
		
		// Rotating the images.
		
		arrowRight.rotate(180);
		
		
		// Loading the levels.

		loadLevelImages();
		loadCustomLevels();
		
		
		// Filling in some variables.
		
		page = 0;

		
		// Filling in the button dimensions.
		
		pageBack = new Rectangle(75, gameContainer.getHeight() / 2 - 40, 40, 80);
		pageNext = new Rectangle(gameContainer.getWidth() - 115, gameContainer.getHeight() / 2 - 40, 40, 80);
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
		// General layout.
		
		DrawUtils.drawBackground(graphics, gameContainer, new Color(20, 20, 20));
		DrawUtils.drawImageAtCenter(graphics, gameContainer, header, true, 50);
		
		graphics.setColor(new Color(255, 128, 0));
		graphics.fillRect(-300, 0, 325, gameContainer.getHeight());

		graphics.setColor(new Color(185, 0, 255));
		graphics.fillRect(25, 0, 10, gameContainer.getHeight());


		// Information.
		
		DrawUtils.drawAdvancedString(graphics, gameContainer, "VERSION:" + " " + Run.version, infoFont, new Color(255, 255, 255), gameContainer.getWidth() - infoFont.getWidth("VERSION:" + " " + Run.version) - 2, gameContainer.getHeight() - infoFont.getHeight() - 1);
		DrawUtils.drawAdvancedString(graphics, gameContainer, difficulties[page], difficultyFont, difficultyColors[page], true, 140);
				
		
		// Page navigation.

		graphics.setColor(new Color(255, 255, 255));
		
		arrowLeft.draw(75, gameContainer.getHeight() / 2 - 40, 5);
		arrowRight.draw(gameContainer.getWidth() - 83, gameContainer.getHeight() / 2 + 24, 5);
		
		if(mousePixel.intersects(pageBack))
		{
			graphics.fillRect(75, gameContainer.getHeight() / 2 + 45, 40, 5);
		}

		if(mousePixel.intersects(pageNext))
		{
			graphics.fillRect(gameContainer.getWidth() - 115, gameContainer.getHeight() / 2 + 45, 40, 5);
		}
		
		
		// New level button.
		
		if(newLevel != null && page == 3)
		{
			plus.draw(newLevel.getCenterX() - (plus.getWidth() * 4 / 2), newLevel.getCenterY() - (plus.getHeight() * 4 / 2), 4);
		}
		
		
		// The levels.
		
		drawLevels(allPages[page], graphics, gameContainer);
	}
	
	
	// Gets called every frame.

	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException 
	{
		handleMouse(stateBasedGame);
	}
	
	
	/**
     * handleMouse Checks if something should happen when the mouse is moved.
     * 
     * @param  {StateBasedGame} stateBasedGame Used so we can switch between game states.
     *
     * @return {void}
     * 
     * @throws {SlickException}
     */
	private void handleMouse(StateBasedGame stateBasedGame) throws SlickException
	{
		mousePixel = new Rectangle(xMouse, yMouse, 1, 1);

		if(mouseDown)
		{
			// Going back a level page.
			
			if(mousePixel.intersects(pageBack) && page > 0)
			{
				page--;
			}
			
			
			// Going to the next level page.
			
			else if(mousePixel.intersects(pageNext) && page < allPages.length - 1)
			{
				page++;
			}
			
			
			// Going to the Editor state.
			
			else if(newLevel != null && page == 3 && mousePixel.intersects(newLevel))
			{
				stateBasedGame.enterState(States.EDITOR_STATE.getId());
			}
			
			
			// Starting a level.
			
			for(int i = 0; i < levels.length; i++) 
			{
				if(mousePixel.intersects(levels[i]))
				{
					try 
					{
						Game.objectHandler.loadLevel((Image)allPages[page].get(i));
					} 
					
					catch(IOException error)
					{
						logger.error(error);
					}
					
					stateBasedGame.enterState(States.GAME_STATE.getId());
				}
			}
			
			mouseDown = false;
		}
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
	}
	
	
	private void loadCustomLevels() throws SlickException
	{
		customLevelPage.clear();
		
		File folder;
		
		folder = new File(System.getenv("Appdata") + Run.customLevelLocation);

		
		// Checking if the folder already exists. If not create it.
		
		if(!folder.exists())
		{
			folder.mkdirs();
			
			logger.info("Custom level folder not found, creating one.");
		}
		
		else
		{
			File[] listOfFiles = folder.listFiles();

			for(int i = 0; i < listOfFiles.length; i++)
			{
				// Checking if the File is a file and not a file folder.
				
				if(listOfFiles[i].isFile())
				{
					// Checking if the File is a png.
					
					if(FileUtils.getFileExtension(listOfFiles[i]).matches("png"))
					{
						Image customLevel = new Image(folder.getPath() + "/" + listOfFiles[i].getName());
						
						// Checking if the level is valid.
						
						if(ImageUtils.isValidLevel(customLevel))
						{
							customLevelPage.add(customLevel);
						}
					}
				}
			}
			
			logger.info("Finished loading " + customLevelPage.size() + " custom levels.");
		}
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
		levels = new Rectangle[list.size()];
		
		int col = 0;
		int row = 0;

		for(int i = 0; i < list.size(); i++)
		{
			list.get(i).setFilter(Image.FILTER_NEAREST);
			list.get(i).draw((gameContainer.getWidth() - 578) / 2 + (col * 150), 200 + (row * 150), 2);
			
			levels[i] = new Rectangle((gameContainer.getWidth() - 578) / 2 + (col * 150), 200 + (row * 150), 128, 128);
			
			Score score = getHighestScoreForLevel(i + 1 + (page * 12));
			
			if(score != null)
			{
				DrawUtils.drawAdvancedString(graphics, gameContainer, "Coins: " + score.getCoins(), infoFont, new Color(255, 255, 255), (gameContainer.getWidth() - 570) / 2 + (col * 150), 332 + (row * 150));
				DrawUtils.drawAdvancedString(graphics, gameContainer, score.getDuration() + "'s", infoFont, new Color(255, 255, 255), (gameContainer.getWidth() - 376 - infoFont.getWidth(score.getDuration() + "'s")) / 2 + (col * 150), 332 + (row * 150));
			}
			
			else
			{
				DrawUtils.drawAdvancedString(graphics, gameContainer, "?", infoFont, new Color(255, 255, 255), (gameContainer.getWidth() - 456) / 2 + (col * 150), 332 + (row * 150));
			}
			
			col++;

			
			// Going to the next row.
			
			if(col == 4)
			{
				col = 0;
				row++;
			}
		}
		
		
		// Figuring out where to draw the plus button for a new custom level.
		
		if(page == 3)
		{
			newLevel = new Rectangle((gameContainer.getWidth() - 578) / 2 + (col * 150), 200 + (row * 150), 128, 128);
		}
	}
	
	
	/**
     * getHighestScoreForLevel Checks what the highest achieved score is for a certain level.
     *
     * @param  {int} level The level we should check for.
     * 
     * @return {Score}
     */
	private Score getHighestScoreForLevel(int level)
	{
		Score highestScore = null;
		
		
		// Looping trough all the scores.
		
		for(int i = 0; i < scores.size(); i++)
		{
			if(scores.get(i).getLevel() == level)
			{
				// If there is no "highestScore" specified yet.
				
				if(highestScore == null)
				{
					highestScore = scores.get(i); 
				}
				
				
				// If there is a score with a higher coin count.
				
				else if(scores.get(i).getCoins() > highestScore.getCoins())
				{
					highestScore = scores.get(i); 
				}
				
				
				// If the coin count is equal but the duration faster.
				
				else if(scores.get(i).getCoins() == highestScore.getCoins())
				{	
					if(scores.get(i).getDuration() < highestScore.getDuration())
					{
						highestScore = scores.get(i);
					}
				}
			}
		}
		
		return highestScore;
	}
}