package com.dalthow.etaron.media;

/**
 * Etaron
 *
 * 
 * @author Dalthow Game Studios 
 * @class SoundResource.java
 * 
 **/

public enum SoundResource 
{
	// A list of all the sounds available in the game.

	COIN("assets/sounds/coin.ogg"), 
	BULLET("assets/sounds/bullet.ogg"), 
	FAILURE("assets/sounds/failure.ogg"), 
	VICTORY("assets/sounds/victory.ogg"), 
	BOING("assets/sounds/boing.ogg"),
	DOOR("assets/sounds/door.ogg"), 
	CHAIN("assets/sounds/chain.ogg"),
	VORTEX("assets/sounds/vortex.ogg");

	
	// Declaration of the path.

	private String path;

	
	// Constructor that sets the declared path variable.

	SoundResource(String path)
	{
		this.path = path;
	}

	
	// Getter.

	public String getPath()
	{
		return path;
	}
}
