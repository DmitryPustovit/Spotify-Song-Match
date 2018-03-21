import java.io.IOException;
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

public class main {
    //Just one song for now, I mean come on now gimme a break
    //https://developer.spotify.com/web-api/get-track/
    //https://developer.spotify.com/web-api/get-several-tracks/
	
	 public static void main(String[] args) throws Exception 
	 {
		 //Hey Look it's only 3 lines of code, I for sure didn't write a class for the API /s
		 SpotifyAPI spot = new SpotifyAPI(); 
		 String demoSongID = "2EoIt9vdgFRNW03u5IvFsQ";
		 System.out.println(spot.getSongInfoByID(demoSongID));
	 }
}
