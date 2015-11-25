package com.dalthow.etaron.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.util.ResourceLoader;

import com.dalthow.etaron.Run ;
import com.dalthow.etaron.media.ImageResource;
import com.dalthow.etaron.media.MusicResource;
import com.dalthow.etaron.media.SoundResource;

/**
 * Etaron
 *
 * @author Trevi Awater
 **/

public class ResourceHandler
{
	// Declaration of the Logger object.
	private static final Logger logger = LogManager.getLogger(ResourceHandler.class);

	// Declaration of the hash maps that hold all the resources.
	public Map<ImageResource.Levels, Image> levels = new HashMap<>(ImageResource.Levels.values().length);
	public Map<SoundResource, Sound> sounds = new HashMap<>(SoundResource.values().length);
	public Map<MusicResource, Music> music = new HashMap<>(MusicResource.values().length);


	/**
	 * Loads all declared sounds.
	 *
	 * @throws SlickException
	 */
	private void loadSounds() throws SlickException
	{
		for(SoundResource sound : SoundResource.values())
		{
			sounds.put(sound, new Sound(ResourceLoader.getResourceAsStream(sound.getPath()), sound.getPath()));
		}

		logger.info("Finished loading " + sounds.size() + " sounds.");
	}
	
	/**
	 * Loads all declared music.
	 *
	 * @throws SlickException
	 */
	private void loadMusic() throws SlickException
	{
		for(MusicResource song : MusicResource.values())
		{
			music.put(song, new Music(ResourceLoader.getResourceAsStream(song.getPath()), song.getPath()));
		}
		
		logger.info("Finished loading " + music.size() + " songs.");
	}

	/**
	 * Loads all declared levels.
	 *
	 * @throws SlickException
	 */
	public void loadLevels() throws SlickException
	{
		for(ImageResource.Levels imageResource : ImageResource.Levels.values())
		{
			if(imageResource.name().startsWith("LEVEL_"))
			{
				Image image = new Image(ResourceLoader.getResourceAsStream(imageResource.getPath()), imageResource.name(), false);
				image.setName(imageResource.getPath());

				levels.put(imageResource, image);
			}
		}

		logger.info("Finished loading " + levels.size() + " levels.");
	}

	/**
	 * Gets an image.
	 * 
	 * @param image The ImageResource media object that should be obtained.
	 * @param flipped If the image should be flipped or not.
	 *
	 * @return Image
	 * 
	 * @throws SlickException, IOException
	 */
	public Image get(ImageResource image, boolean flipped) throws SlickException, IOException
	{
		return new Image(ResourceLoader.getResourceAsStream(image.getPath()), image.getPath(), flipped);
	}

	/**
	 * Gets an music file.
	 *
	 * @param music The MusicResource media object that should be obtained.
	 *
	 * @return Music
	 * 
	 * @throws SlickException
	 */
	@Deprecated
	public Music get(MusicResource music) throws SlickException
	{
		return new Music(ResourceLoader.getResourceAsStream(music.getPath()), music.getPath());
	}

	/**
	 * Gets a sounds file.
	 *
	 * @param sound The SoundResource media object that should be obtained.
	 *
	 * @return Sound
	 * 
	 * @throws SlickException
	 */
	@Deprecated
	public Sound get(SoundResource sound) throws SlickException
	{
		return new Sound(ResourceLoader.getResourceAsStream(sound.getPath()), sound.getPath());
	}

	/**
	 * Loads both the SoundResource's and the MusicResource's on a separate Thread.
	 *
	 * @throws SlickException
	 */
	public void loadAudio()
	{
		new Thread()
		{
			public void run()
			{
				try
				{
					Run.resourceHandler.loadSounds();
					Run.resourceHandler.loadMusic();

					// Stopping the Thread.
					interrupt();
				}
				
				catch(SlickException error)
				{
					logger.error(error);
				}
			}
		}.start();
	}
}