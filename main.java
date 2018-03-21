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
	
	 public static void main(String[] args) throws Exception 
	 {
		 String id = "ID_GOES_HERE";
		 String secret = "SECRET_GOES_HERE";
		 
		 CSVReader trainingSet = new CSVReader(
				 "rating.csv");
		 trainingSet.removeRow(0);
		 String [] songIDs = trainingSet.getCSVColumnData(2);
		 
		 SpotifyAPI spot = new SpotifyAPI(id, secret); 
		 System.out.println(spot.getSongsInfoByID(songIDs));
	 }
}
