import java.io.IOException;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;

import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.LongSerializationPolicy;

import weka.core.Instances;
import weka.core.converters.ArffLoader.ArffReader;
import weka.core.converters.ArffSaver;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
 
public class main {
	
	 public static void main(String[] args) throws Exception 
	 { 
		 //Sooo.. I completly forgot that WEKA, you know the main core of this project yeah that weka, weka CAN READ CSVs....
		 //ALL. THAT. WORK...
		 
		 String id = "ID_GOES_HERE";
		 String secret = "SECRET_GOES_HERE";
		 
		 CSVReader trainingSet = new CSVReader(
				 "rating.csv");
		 trainingSet.removeRow(0);
		 String [] songIDs = trainingSet.getCSVColumnData(2);
		 
		 SpotifyAPI spot = new SpotifyAPI(id, secret); 
		 String spotifyResult = spot.getSongsFeaturesByID(songIDs);
		 
		 Gson gson = new GsonBuilder().setLenient().setLongSerializationPolicy( LongSerializationPolicy.STRING ).create();
		 JsonElement jelem = gson.fromJson(spotifyResult, JsonElement.class);
		 JsonArray dataArr = jelem.getAsJsonObject().get("audio_features").getAsJsonArray();
		 
		 List<List<String>> formattedData = new ArrayList<>();
		 String[] colHeads = {"danceability", "energy", "key", "loudness", "mode", "speechiness", "acousticness",
				 "instrumentalness", "liveness", "valence", "tempo", "duration_ms", "time_signature"};

		 List<String> nullList = new ArrayList<>();
		 
		 for(int i = 0; i < 12; i++)
			 nullList.add("null");
		 
		 for(int i = 0; i < dataArr.size(); i++)
		 { 
			 List<String> arr = new ArrayList();
			 
			 try
			 {
				 for(int j = 0; j < colHeads.length; j++)
				 {
					 arr.add((dataArr.get(i).getAsJsonObject().get(colHeads[j]).getAsString()));
				 }
				 
				 formattedData.add(arr);
			 }
			 catch(Exception e){
				 System.out.println("Null");
				 formattedData.add(nullList);
			 }
		 }
		 
		 trainingSet.joinCSV(formattedData);
		
		 trainingSet.saveCSV(
				 "ratingEdited.csv");
		 
	 }
}
