package com.dalthow.etaron.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * Etaron
 *
 *
 * @author Dalthow Game Studios 
 * @class NetworkUtil.java
 *
 **/

public class NetworkUtil
{
	/**
     * postData Makes a http post request to a specific page with some values.
     *
     * @param  {String} url                  The web-page we should post to.
     * @param  {BasicNameValuePair[]} values The variables that should be posted with the request.
     *
     * @return {String}						 The response pages.
     */
	public static String postData(String url,  BasicNameValuePair[] values)
	{
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		for(int i = 0; i < values.length; i++) 
		{
			params.add(values[i]);
		}
		
		try 
		{
			httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		    HttpResponse response = httpClient.execute(httpPost);
		    HttpEntity responseEntity = response.getEntity();

		    if(responseEntity != null) 
		    {
		        return EntityUtils.toString(responseEntity); 
		    }
		} 
		
		catch(ClientProtocolException error) 
		{
		    error.printStackTrace();
		} 
		
		catch(IOException error) 
		{
		   System.out.println("Could not access the requested resource on the server.");
		}
		
		return null;
	}
}