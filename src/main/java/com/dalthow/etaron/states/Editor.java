package com.dalthow.etaron.states;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.BufferedImageUtil;
import org.newdawn.slick.util.ResourceLoader;

import com.dalthow.etaron.Run;
import com.dalthow.etaron.framework.States;
import com.dalthow.etaron.framework.editor.Pixel;
import com.dalthow.etaron.media.ImageResource;
import com.dalthow.etaron.utils.DrawUtils;
import com.dalthow.etaron.utils.DrawUtils.DrawHelper;
import com.dalthow.etaron.utils.ImageUtils;

/**
 * Etaron
 *
 *
 * @author Dalthow Game Studios
 * @class Editor.java
 *
 **/

public class Editor implements GameState
{
	// Declaration of the Logger object.

	private static final Logger logger = LogManager.getLogger(Editor.class);
	
	
	// Declaring some other variables.
	
	private StateBasedGame stateBasedGame;
	private Color selectedColor;
	private int pixelSize;
	
	
	// Declaration of the font used in this state.
	
	private TrueTypeFont headerFont;
	
	
	// Declaration of the images used in this state.
	
	private Image cross, disk;
		
	
	// Declaration of the array list with Pixel's.
	
	private List<Pixel> pixels = new ArrayList<Pixel>();
	
	
	// Declaration of the rectangles used for click detection.
	
	private Rectangle saveLevel, exitEditor;
	
	
	// Declaration of the mouse position and states.
	
	private int xMouse, yMouse;
	private boolean mouseDown;
	private Rectangle mousePixel;
	
	
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
		if(par1 == Keyboard.KEY_ESCAPE)
		{
			stateBasedGame.enterState(States.MENU_STATE.getId());
		}
	}
	
	public void keyReleased(int par1, char par2){}
	

	// Lets the state know which id it has.

	public int getID() 
	{
		return States.EDITOR_STATE.getId();
	}

	
	// Gets called when the state is created.
	
	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException 
	{
		// Setting the global StateBasedGame to the local one so we can use it in other methods.
		
		this.stateBasedGame = stateBasedGame;
		
		
		try 
		{
			// Loading font.
			
			InputStream inputStream = ResourceLoader.getResourceAsStream("assets/fonts/bit-bold.ttf");
			Font bitBold = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			
			headerFont = new TrueTypeFont(bitBold.deriveFont(32F), false);
			
			
			// Loading images.
			
			cross = Run.resourceHandler.get(ImageResource.CROSS, false);
			disk = Run.resourceHandler.get(ImageResource.DISK, false);
		}
		
		catch(IOException | FontFormatException error) 
		{
			logger.error(error);
		}
		
		
		// Adding filters to the images.
		
		cross.setFilter(Image.FILTER_NEAREST);
		disk.setFilter(Image.FILTER_NEAREST);
		
		
		// Setting the Pixel size.
		
		pixelSize = 10;
		
		
		// Filling in the button dimensions.
		
		saveLevel = new Rectangle(gameContainer.getWidth() - 86 - disk.getWidth() * 4, gameContainer.getHeight() - 40 - disk.getHeight() * 4, 32, 36);
		exitEditor = new Rectangle(gameContainer.getWidth() - 40 - cross.getWidth() * 4, gameContainer.getHeight() - 40 - cross.getHeight() * 4, 32, 32);
	}
	
	
	// Gets called when the player enters this state.

	public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException 
	{
		try
		{
			resetEditor();
		}
		
		catch(IOException error)
		{
			error.printStackTrace();
		}
	}
	
	
	// Gets called when the player leaves this state.

	public void leave(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException 
	{
		
	}

	
	// Gets called every frame.

	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException 
	{
		DrawUtils.drawBackground(graphics, gameContainer, new Color(20, 20, 20));
		
		
		// Drawing all the Pixel's on the screen.
		
		for(int i = 0; i < pixels.size(); i++)
		{
			graphics.setColor(pixels.get(i).getColor());
			graphics.fillRect(pixels.get(i).getX() * pixelSize + 40, (gameContainer.getHeight() / 2 - (pixelSize * 64 / 2)) + pixels.get(i).getY() * pixelSize, pixelSize, pixelSize);
		}
		
		
		// Drawing the color palette.
		
		int col = 0;
		int row = 0;
		
		for(int i = 0; i < DrawHelper.values().length; i++)
		{
			graphics.setColor(DrawHelper.values()[i].getColor());
			graphics.fillRect(gameContainer.getWidth() - 72 - (col * 48), 86 + (row * 48), 32, 32);

			col++;

			if(col == 4)
			{
				col = 0;
				row++;
			}
		}
		
		graphics.setColor(new Color(255, 255, 255));
		graphics.setFont(headerFont);
		graphics.drawString("Palette", gameContainer.getWidth() - headerFont.getWidth("Palette") - 39, 40);
		
		
		// Drawing the mouse pixel.
		
		graphics.setColor(selectedColor);
		graphics.fillRect(mousePixel.getX() - (pixelSize / 2), mousePixel.getY() - (pixelSize / 2), pixelSize, pixelSize);
		
		
		// Drawing the close and save button.
		
		cross.draw(gameContainer.getWidth() - 40 - cross.getWidth() * 4, gameContainer.getHeight() - 40 - cross.getHeight() * 4, 4);
		disk.draw(gameContainer.getWidth() - 86 - disk.getWidth() * 4, gameContainer.getHeight() - 40 - disk.getHeight() * 4, 4);
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
			// Looping trough all the pixels in the level.
			
			for(int i = 0; i < pixels.size(); i++)
			{
				Rectangle pixelWrapper = new Rectangle(pixels.get(i).getX() * pixelSize + 40, (Run.height / 2 - (pixelSize * 64 / 2)) + pixels.get(i).getY() * pixelSize, pixelSize, pixelSize);

				
				// Setting the Pixel's color to the selected color.
				
				if(mousePixel.intersects(pixelWrapper))
				{
					pixels.get(i).setColor(selectedColor);
				}
			}
			
			
			// Drawing all the object color's so the user can pick one to draw with.
			
			int col = 0;
			int row = 0;
			
			for(int i = 0; i < DrawHelper.values().length; i++)
			{
				Rectangle paletWrapper = new Rectangle(Run.width - 72 - (col * 48), 86 + (row * 48), 32, 32);

				if(mousePixel.intersects(paletWrapper))
				{
					selectedColor = new Color(DrawHelper.values()[i].getColor());
				}

				col++;

				if(col == 4)
				{
					col = 0;
					row++;
				}
			}
			
			
			// Used to return to the Menu state.
			
			if(mousePixel.intersects(exitEditor))
			{
				stateBasedGame.enterState(States.MENU_STATE.getId());
				
				mouseDown = false;
			}
			
			
			// Saves the level to the disk if its valid.
			
			else if(mousePixel.intersects(saveLevel))
			{
				saveLevel();
				
				mouseDown = false;
			}
		}
	}
	
	
	/**
     * loadLevelInEditor Unpacks a level Image into the pixels array.
     * 
     * @param  {Image} level The level we should tear appart.
     *
     * @return {void}
     */
	private void loadLevelInEditor(Image level)
	{
		for(int i = 0; i < level.getWidth(); i++)
		{
			for(int j = 0; j < level.getHeight(); j++)
			{
				Pixel pixel = new Pixel(i, j);
				pixel.setColor(level.getColor(i, j));
				
				pixels.add(pixel);
			}
		}
	}
	
	
	/**
     * resetEditor Resets the level editor.
     * 
     * @return {void}
     */
	private void resetEditor() throws SlickException, IOException
	{
		selectedColor = new Color(255, 255, 255);
		
		
		// Loading in a blank level.
		
		loadLevelInEditor(Run.resourceHandler.get(ImageResource.BLANK_LEVEL, false));
	}
	
	
	/**
     * saveLevel Generates a Image from the pixels array. Then saves it to the disk after that it returns to the Menu state.
     * 
     * @return {void}
     */
	private void saveLevel()
	{
		// Generating a time stamp for the level.
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		Calendar calendar = Calendar.getInstance();
		String timeStamp = dateFormat.format(calendar.getTime());
		
		
		// Creating a new BufferedImage that is 64 by 64.
		
		BufferedImage image = new BufferedImage(64, 64, BufferedImage.TYPE_INT_RGB);

		java.awt.Graphics awtGraphics = image.getGraphics();

		
		// Looping trough the pixels and adding them to the image.
		
		for(int i = 0; i < pixels.size(); i++)
		{
			awtGraphics.setColor(new java.awt.Color(pixels.get(i).getColor().getRed(), pixels.get(i).getColor().getGreen(), pixels.get(i).getColor().getBlue()));
			awtGraphics.fillRect(pixels.get(i).getX(), pixels.get(i).getY(), 1, 1);
		}
		
		try
		{
			// Converting the awt image to slick.
			
			Texture texture = BufferedImageUtil.getTexture("", image);
			Image slickImage = new Image(texture.getImageWidth(), texture.getImageHeight());
			slickImage.setTexture(texture);
			slickImage.setName(timeStamp + ".png");
			
			if(ImageUtils.isValidLevel(slickImage))
			{
				// Writing the image to the disk.
				
				ImageIO.write(image, "PNG", new File(System.getenv("Appdata") + Run.customLevelLocation + timeStamp + ".png"));
				
				
				// Adding the image to the Menu state.
				
				Menu.customLevelPage.add(slickImage);
				stateBasedGame.enterState(States.MENU_STATE.getId());
			}
			
			else
			{
				JOptionPane.showMessageDialog(null, "You need to add at least one player and one flag to be able to save the level.", "Level requirement error", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		catch(IOException | SlickException error)
		{
			logger.error(error);
		}
	}
}