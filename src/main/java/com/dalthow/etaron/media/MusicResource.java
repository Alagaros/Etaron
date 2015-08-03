package com.dalthow.etaron.media;

/**
 * Etaron
 *
 * 
 * @author Dalthow Game Studios 
 * @class MusicResource.java
 * 
 **/

public enum MusicResource 
{
	// A list of all the music available in the game.

	STARTING_OUT_SMALL("assets/music/starting out small.ogg", 0), 
	KEEP_ON_JUMPING("assets/music/keep on jumping.ogg", 1), 
	NEW_MYSTERIES("assets/music/new mysteries.ogg", 2);

	
	// Declaration of the path and id.

	private String path;
	private int id;

	
	// Constructor that sets the declared variables.

	MusicResource(String path, int id)
	{
		this.path = path;
		this.id = id;
	}

	
	// Getters.

	public String getPath()
	{
		return path;
	}

	public int getId()
	{
		return id;
	}
	
	
	// Returns a type with the path based on an id.
	
	public static MusicResource getMusicById(int id)
	{
		for(MusicResource music : MusicResource.values())
		{
			if(music.id == id)
			{
				return music;
			}
		}
		
		return null;
	}
}
