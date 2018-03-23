/*
 * 
 * Spoify Oauth...AKA welcome to hell.
 * JK... sort of... but serious <3 u spotify
 * 
 */

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class SpotifyAPI {
		
		//Hush Hush Stuff
		private String spotifyClientID;
		private String spotifyClientSecret;
		
		private String token;
		
		private int stepSize;
		
		public SpotifyAPI (String id, String secret)
		{
			spotifyClientID = id;
			spotifyClientSecret = secret;
			stepSize = 50;
			
			try {
				Oauth();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public void Oauth() throws IOException
		{
			URL tokenURL = new URL("https://accounts.spotify.com/api/token?grant_type=client_credentials");
			
			HttpURLConnection connection = (HttpURLConnection) tokenURL.openConnection();
	    	String encoded = Base64.getEncoder().encodeToString((spotifyClientID + ":" + spotifyClientSecret).getBytes(StandardCharsets.UTF_8));
	    	connection.setRequestProperty("Authorization", "Basic " + encoded);
	    	connection.setRequestMethod("POST");
	    	connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	    	
	    	token = ParseToken(HTTPRequest(connection));
		}
		
		public String getSongInfoByID(String ID) throws IOException
		{
			HttpURLConnection connection = (HttpURLConnection) new URL("https://api.spotify.com/v1/tracks/" + ID).openConnection();
			connection.setRequestProperty("Authorization", "Bearer  " + token);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			
			return HTTPRequest(connection);
		}
		
		public String getSongsInfoByID(String[] IDs) throws IOException
		{ 
			HttpURLConnection connection;
			String jsonReturn = new String();
			ArrayList<String []> params = new ArrayList<String []>();
			int length = IDs.length, count = 0;
			
			while(length > 0)
			{
				if(length > 50)
				{
					params.add(new String [50]);
					length -= 50;
				}
				else
				{
					params.add(new String [length]);
					length = 0;
				}
							
				for(int i = 0;  i < params.get(params.size() - 1).length; i ++)
					params.get(params.size() - 1)[i] = IDs[count++];
				
				
			}
			
			boolean first = true;
			
			for(int i = 0; i < params.size(); i ++)
			{
				connection = (HttpURLConnection) new URL("https://api.spotify.com/v1/tracks/?ids=" + 
						(Arrays.toString(params.get(i)).replace("]", "").replace("[", "").replace(" ", ""))).openConnection();
				connection.setRequestProperty("Authorization", "Bearer  " + token);
				connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				String returndata = HTTPRequest(connection);
				if(!first)
				{
					jsonReturn = jsonReturn.substring(0, jsonReturn.length() - 2)  + ",";
					returndata = returndata.substring(15, returndata.length());
				}
				jsonReturn += returndata;
				first = false;
			}
			
			return jsonReturn;
		}
		
		public String getSongsFeaturesByID(String[] IDs) throws IOException
		{ 
			HttpURLConnection connection;
			String jsonReturn = new String();
			ArrayList<String []> params = new ArrayList<String []>();
			int length = IDs.length, count = 0;
			
			while(length > 0)
			{
				if(length > 50)
				{
					params.add(new String [50]);
					length -= 50;
				}
				else
				{
					params.add(new String [length]);
					length = 0;
				}
							
				for(int i = 0;  i < params.get(params.size() - 1).length; i ++)
					params.get(params.size() - 1)[i] = IDs[count++];
					
			}
			
			boolean first = true;
			
			for(int i = 0; i < params.size(); i ++)
			{
				connection = (HttpURLConnection) new URL("https://api.spotify.com/v1/audio-features/?ids=" + 
						(Arrays.toString(params.get(i)).replace("]", "").replace("[", "").replace(" ", ""))).openConnection();
				connection.setRequestProperty("Authorization", "Bearer  " + token);
				connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				String returndata = HTTPRequest(connection);
				if(!first)
				{
					jsonReturn = jsonReturn.substring(0, jsonReturn.length() - 2)  + ",";
					returndata = returndata.substring(23, returndata.length());
				}
				jsonReturn += returndata;
				first = false;
			}
			
			return jsonReturn;
		}
		
		private String HTTPRequest(HttpURLConnection connection) throws IOException
		{
			BufferedReader data;
			String result = new String();
			try{
				data = new BufferedReader(
						new InputStreamReader(
						connection.getInputStream()));
		    	} catch (Exception e) {
		        data = new BufferedReader(
		                new InputStreamReader(
		                connection.getErrorStream()));
		        System.out.println("API REQUEST ERROR");
		    	} 
			
			 String buffer;
			
			 while ((buffer = data.readLine()) != null) 
		        	result += buffer;
		        
		     data.close();
		     
		     return result;
		}
		
		private String ParseToken (String raw)
		{
	        String[] array = raw.replace("{", "").replace("}", "").replace("\"", "").split(",", -1);
	        String[][] ohMy = new String[array.length][];
	        for(int i = 0;  i < array.length; i++)
	        	ohMy[i] = array[i].split(":", -1);
	        
	        return ohMy[0][1];
		}  
    	
}
