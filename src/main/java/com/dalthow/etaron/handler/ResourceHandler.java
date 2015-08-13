package com.dalthow.etaron.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.util.ResourceLoader;

import com.dalthow.etaron.media.ImageResource;
import com.dalthow.etaron.media.MusicResource;
import com.dalthow.etaron.media.SoundResource;

/**
 * Etaron
 *
 * 
 * @author Dalthow Game Studios 
 * @class ResourceHandler.java
 * 
 **/

public class ResourceHandler 
{
	// Declaration of the hash maps that hold all the resources.
	
	public Map<ImageResource.Levels, Image> levels = new HashMap<>(ImageResource.Levels.values().length);
	public Map<SoundResource, Sound> sounds = new HashMap<>(SoundResource.values().length);
	public Map<MusicResource, Music> musics = new HashMap<>(MusicResource.values().length);
	
	
	/**
     * loadSounds Loads all declared sounds.
     *
     * @return {void}
     * 
     * @throws {SlickException}
     */
	public void loadSounds() throws SlickException
	{
		for(SoundResource sound : SoundResource.values())
		{
			sounds.put(sound, new Sound(ResourceLoader.getResourceAsStream(sound.getPath()), sound.getPath()));
		}
	}
	
	
	/**
     * loadLevels Loads all declared levels.
     *
     * @return {void}
     * 
     * @throws {SlickException}
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
	}
	
	
	/**
     * get Gets an image.
     * 
     * @param  {ImageResource} image The ImageResource media object that should be obtained.
     * @param  {boolean} flipped	 If the image should be flipped or not.
     *
     * @return {void}
     * 
     * @throws {SlickException, IOException}
     */
	public Image get(ImageResource image, boolean flipped) throws SlickException, IOException
	{
		return new Image(ResourceLoader.getResourceAsStream(image.getPath()), image.getPath(), flipped);
	}

	
	/**
     * get Gets an music file.
     *
     * @param  {MusicResource} music The MusicResource media object that should be obtained.
     *
     * @return {void}
     * 
     * @throws {SlickException}
     */
	@Deprecated
	public Music get(MusicResource music) throws SlickException
	{
		return new Music(ResourceLoader.getResourceAsStream(music.getPath()), music.getPath());
	}

	
	/**
     * get Gets a sounds file.
     *
     * @param  {SoundResource} sound The SoundResource media object that should be obtained.
     *
     * @return {void}
     * 
     * @throws {SlickException}
     */
	@Deprecated
	public Sound get(SoundResource sound) throws SlickException
	{
		return new Sound(ResourceLoader.getResourceAsStream(sound.getPath()), sound.getPath());
	}
}