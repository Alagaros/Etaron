package com.dalthow.etaron.utils;

import java.io.File;

/**
 * Etaron
 *
 *
 * @author Dalthow Game Studios 
 * @class FileUtils.java
 *
 **/

public class FileUtils
{
	/**	
	 * getFileExtension Gets a file extension from a File object.
	 * 
	 * @param  {File} file The file we need the extension from.
	 * 
	 * @return {String}	   The file extension.
	 */
	public static String getFileExtension(File file) 
	{
        String fileName = file.getName();
      
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
    	{
    		return fileName.substring(fileName.lastIndexOf(".")+1);
    	}
        
        else return "";
    }
}
