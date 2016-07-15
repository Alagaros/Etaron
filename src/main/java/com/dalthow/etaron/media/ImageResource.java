package com.dalthow.etaron.media;

/**
 * Etaron
 *
 * @author Trevi Awater
 **/

public enum ImageResource
{
	// A list of all the textures available in the game that aren't levels.
	LOGO("assets/images/logo.png"), HEADER("assets/images/header.png"), ARROW("assets/images/arrow.png"),
	KEY("assets/images/items/key.png"), COIN("assets/images/items/coin.png"), DISK("assets/images/disk.png"),
	CROSS("assets/images/cross.png"), BLANK_LEVEL("assets/images/blank-level.png"), PLUS("assets/images/plus.png");

	// Declaration of the path.
	private String path;

	// Constructor that sets the declared path variable.
	ImageResource(String path)
	{
		this.path = path;
	}

	// Getter.
	public String getPath()
	{
		return path;
	}

	// A list of all the levels in the game.
	public enum Levels
	{
		// Easy levels.
		LEVEL_TUTORIAL("assets/levels/easy/tutorial.png", 1), LEVEL_THE_CLIMB("assets/levels/easy/the-climb.png", 2),
		LEVEL_CAVEMAN("assets/levels/easy/caveman.png", 3), LEVEL_THE_FALL("assets/levels/easy/the-fall.png", 4),
		LEVEL_INVADERS("assets/levels/easy/invaders.png", 5), LEVEL_FLAPPY("assets/levels/easy/flappy.png", 6),
		LEVEL_RESCUE("assets/levels/easy/rescue.png", 7),

		// Medium levels.
		LEVEL_UNDER_FIRE("assets/levels/medium/under-fire.png", 13), LEVEL_SOUNDWAVE("assets/levels/medium/soundwave.png", 14),

		// Hard levels.
		LEVEL_GET_WREKT("assets/levels/hard/get-wrekt.png", 25);

		// Declaration of the path and level variable.
		private String path;
		private int level;

		// Constructor that sets the declared variables.
		Levels(String path, int level)
		{
			this.setPath(path);
			this.setLevel(level);
		}

		// Getters.
		public String getPath()
		{
			return path;
		}
		public int getLevel()
		{
			return level;
		}

		// Setters.
		public void setPath(String path)
		{
			this.path = path;
		}
		public void setLevel(int level)
		{
			this.level = level;
		}

		/**
	     * Gets a Levels enum based on the path..
	     *
	     * @param path The Image it's path.
	     * 
	     * @return Levels
	     */
		public static Levels findByPath(String path)
		{
		    for(Levels levels : values())
				if(levels.getPath().equals(path))
					return levels;
		    
		    return null;
		}
	}
}