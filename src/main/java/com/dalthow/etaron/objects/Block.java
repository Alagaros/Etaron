package com.dalthow.etaron.objects;

import java.awt.Rectangle;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.dalthow.etaron.models.Identifier;
import com.dalthow.etaron.models.WorldObject;
import com.dalthow.etaron.states.Game;
import com.dalthow.etaron.utils.DrawUtils ;

/**
 * Etaron
 *
 * @author Trevi Awater
 **/

public class Block extends WorldObject
{
	// Constructor that sets the variables for the WorldObject.
	public Block(float xPos, float yPos, Identifier id, boolean isSolid)
	{
		super(xPos, yPos, id, isSolid);
	}

	// Default WorldObject methods.
	@Override
	public void tick(List<WorldObject> objectList)
	{
		if(id == Identifier.DOOR)
		{
			// Making sure the user can touch the block.
			Rectangle touchBounds = getBounds();
			touchBounds.grow(1, 1);

			Player temporaryPlayer = (Player)Game.cameraFocus;

			// Checking if the player has a key, if so delete it and open the door.
			if(temporaryPlayer.getBounds().intersects(touchBounds))
				if(temporaryPlayer.hasItem(Identifier.KEY, true))
					Game.objectHandler.objects.remove(this);
		}

		else if(id == Identifier.JELLY)
		{
			Rectangle touchBounds = getBounds();
			touchBounds.grow(0, 1);

			for(int i = 0; i < Game.objectHandler.players.size(); i++)
			{
				Player temporaryPlayer = Game.objectHandler.players.get(i);

				if(temporaryPlayer.getBoundsTop().intersects(touchBounds))
					temporaryPlayer.setVelY((float)(Player.jumpHeight * 1.75));

				else if(temporaryPlayer.getBoundsBottom().intersects(touchBounds))
					temporaryPlayer.setVelY((float)(-Player.jumpHeight * 1.75));
			}
		}
	}

	@Override
	public void render(Graphics graphics)
	{
		// Switching the Block's color based on the Identifier.
		Color blockColor = new Color(255, 255, 255);

		if(id == Identifier.LAVA)
			blockColor = DrawUtils.DrawHelper.LAVA.getColor();

		else if(id == Identifier.FLAG)
			blockColor = DrawUtils.DrawHelper.FLAG.getColor();

		else if(id == Identifier.DOOR)
			blockColor = DrawUtils.DrawHelper.DOOR.getColor();

		else if(id == Identifier.JELLY)
			blockColor = DrawUtils.DrawHelper.JELLY.getColor();

		else if(id == Identifier.DECOR)
			blockColor = DrawUtils.DrawHelper.DECOR.getColor();

		// Drawing the Block.
		graphics.setColor(blockColor);
		graphics.fillRect(getBounds().x, getBounds().y, getBounds().width, getBounds().height);
	}

	@Override
	public Rectangle getBounds()
	{
		return new Rectangle((int)xPos, (int)yPos, 32, 32);
	}
}