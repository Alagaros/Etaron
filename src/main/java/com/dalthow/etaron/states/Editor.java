package com.dalthow.etaron.states;

import java.awt.image.BufferedImage ;
import java.io.File ;
import java.io.IOException ;
import java.text.DateFormat ;
import java.text.SimpleDateFormat ;
import java.util.ArrayList;
import java.util.Calendar ;
import java.util.List;

import javax.imageio.ImageIO ;
import javax.swing.JOptionPane ;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image ;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle ;
import org.newdawn.slick.opengl.Texture ;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.BufferedImageUtil ;

import com.dalthow.etaron.Run ;
import com.dalthow.etaron.framework.States;
import com.dalthow.etaron.framework.editor.Pixel;
import com.dalthow.etaron.media.ImageResource ;
import com.dalthow.etaron.utils.DrawUtils;
import com.dalthow.etaron.utils.ImageUtils ;

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
	
	
	// Declaration of the array list with Pixel's.
	
	private List<Pixel> pixels = new ArrayList<Pixel>();
	
	
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
		
		
		// Setting the Pixel size.
		
		pixelSize = 10;
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
		saveLevel();
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
		
		
		// Drawing the mouse pixel.
		
		graphics.setColor(selectedColor);
		graphics.fillRect(mousePixel.getX() - (pixelSize / 2), mousePixel.getY() - (pixelSize / 2), pixelSize, pixelSize);	
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
			for(int i = 0; i < pixels.size(); i++)
			{
				Rectangle pixelWrapper = new Rectangle(40 + (pixels.get(i).getX() * 10), (Run.height / 2 - (pixelSize * 64 / 2)) + pixels.get(i).getY() * pixelSize, pixelSize, pixelSize);

				
				// Setting the Pixel's color to the selected color.
				
				if(mousePixel.intersects(pixelWrapper))
				{
					pixels.get(i).setColor(selectedColor);
				}
			}
		}
	}
	
	
	private void loadLevel(Image level)
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
	
	private void resetEditor() throws SlickException, IOException
	{
		selectedColor = new Color(255, 255, 255);
		
		
		// Loading in a blank level.
		
		loadLevel(Run.resourceHandler.get(ImageResource.BLANK_LEVEL, false));
	}
	
	private void saveLevel()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		Calendar calendar = Calendar.getInstance();
		String timeStamp = dateFormat.format(calendar.getTime());
		
		BufferedImage image = new BufferedImage(64, 64, BufferedImage.TYPE_INT_RGB);

		java.awt.Graphics awtGraphics = image.getGraphics();

		for(int i = 0; i < pixels.size(); i++)
		{
			awtGraphics.setColor(new java.awt.Color(pixels.get(i).getColor().getRed(), pixels.get(i).getColor().getGreen(), pixels.get(i).getColor().getBlue()));
			awtGraphics.fillRect(pixels.get(i).getX(), pixels.get(i).getY(), 1, 1);
		}
		
		try
		{
			Texture texture = BufferedImageUtil.getTexture("", image);
			Image slickImage = new Image(texture.getImageWidth(), texture.getImageHeight());
			slickImage.setTexture(texture);
			slickImage.setName(timeStamp + ".png");
			
			if(ImageUtils.isValidLevel(slickImage))
			{
				ImageIO.write(image, "PNG", new File(System.getenv("Appdata") + Run.customLevelLocation + timeStamp + ".png"));
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