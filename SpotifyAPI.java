/*
 * 
 * Spoify Oauth...AKA welcome to hell.
 * JK... sort of... but serious <3 u spotify
 * 
 */

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.io.*;
import java.util.Base64;

public class SpotifyAPI {
		
		//Hush Hush Stuff
		private String spotifyClientID = "A_CLIENT_ID_GOES_HERE";
		private String spotifyClientSecret = "A_CLIENT_SECRET_GOES_HERE";
		
		private String token;
		
		public SpotifyAPI ()
		{
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
