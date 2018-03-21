/*
 * 
 * Spoify Oauth...AKA welcome to hell.
 * JK... sort of... but serious <3 u spotify
 * 
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;

import java.util.*;
import weka.core.Instances;
import weka.core.converters.ArffLoader.ArffReader;
import weka.core.converters.ArffSaver;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.io.*;

import java.util.Base64;

public class SpotifyDataGrab {
    public static void main(String[] args) throws Exception {
    	URL url = new URL("https://accounts.spotify.com/api/token?grant_type=client_credentials");
    	String spotifyClientID = "A_CLIENT_ID_GOES_HERE";
    	String spotifyClientSecret = "A_CLIENT_SECRET_GOES_HERE";
        
    	HttpURLConnection con = (HttpURLConnection) url.openConnection();
    	String encoded = Base64.getEncoder().encodeToString((spotifyClientID+":"+spotifyClientSecret).getBytes(StandardCharsets.UTF_8));
    	con.setRequestProperty("Authorization", "Basic "+encoded);
    	con.setRequestMethod("POST");
    	con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            
    	
    	BufferedReader in;
    	String output = new String();
    	
    	try{
        in = new BufferedReader(
                                new InputStreamReader(
                                con.getInputStream()));
    	} catch (Exception e) {
    	
        in = new BufferedReader(
                new InputStreamReader(
                con.getErrorStream()));
    	} 
        String inputLine;
        
        while ((inputLine = in.readLine()) != null) 
        	output += inputLine;
        
        in.close();
        
        String[] array = output.replace("{", "").replace("}", "").replace("\"", "").split(",", -1);
        String[][] ohMy = new String[array.length][];
        for(int i = 0;  i < array.length; i++)
        	ohMy[i] = array[i].split(":", -1);
        
        System.out.println(ohMy[0][1]);
        
        
        //Just one song for now, I mean come on now gimme a break
        //https://developer.spotify.com/web-api/get-track/
        //https://developer.spotify.com/web-api/get-several-tracks/
        
        String trackID = "7Lf7oSEVdzZqTA0kEDSlS5";
        URL songURL = new URL("https://api.spotify.com/v1/tracks/" + trackID); 
        
    	con = (HttpURLConnection) songURL.openConnection();
    	con.setRequestProperty("Authorization", "Bearer  "+ohMy[0][1]);
    	con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        
         in = new BufferedReader(
                new InputStreamReader(
                con.getInputStream()));
        
        while ((inputLine = in.readLine()) != null) 
        	System.out.println(inputLine);
        
        in.close();
    	
    }
}
