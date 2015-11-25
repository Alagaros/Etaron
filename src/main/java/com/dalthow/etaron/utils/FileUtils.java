package com.dalthow.etaron.utils;

import java.io.File;

/**
 * Etaron
 *
 * @author Trevi Awater
 **/

public class FileUtils
{
	/**	
	 * Gets a file extension from a File object.
	 * 
	 * @param file The file we need the extension from.
	 * 
	 * @return String The file extension.
	 */
	public static String getFileExtension(File file) 
	{
        String fileName = file.getName();
      
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
    	{
    		return fileName.substring(fileName.lastIndexOf(".")+1);
    	}
        
        else return null;
	}
}
