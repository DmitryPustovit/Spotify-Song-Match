import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CSVReader {
	
	private List<List<String>> list;
	
	/**
	 * Default Constructor
	 */
	public CSVReader()
	{
		list = new ArrayList<>();
	}
	
	/**
	 * Constructor that automatically open a inputed CSV file
	 * EX. CSVReader aCSV = new CSVReader("myFile.csv");
	 *
	 * @param A CSV file
	 */
	public CSVReader(String file)
	{
		list = new ArrayList<>();
		openFile(file);
	}
	
	/**
	 * Sets the active open file of the CSV reader to the inputed CSV file
	 * 
	 * @param A CSV file
	 */
	public void openFile(String file)
	{
		try {
			Scanner scanner = new Scanner(new File(file));
	        while(scanner.hasNext()){
	        	String currentLine = scanner.nextLine().replace("/n", "");
	        	String[] values = currentLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
	        	
	        	list.add(Arrays.asList(values));
	        }
	        
	        scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns open CSV file as a two dimetional list
	 *
	 * @return CSV file as 2d list
	 */
	public List<List<String>> getCSVData()
	{
		return list;
	}
	
	/**
	 * Returns all the items of a specified column in the opened CSV
	 *
	 * @param int - column number 
	 * @return Column data as a String array
	 */
	public String[] getCSVColumnData(int col)
	{
		String[] arr = new String[list.size()];
		
		for(int i = 0; i < list.size(); i++)
			arr[i] = list.get(i).get(col);
		
		return arr;
	}
	
	/**
	 * Removes a row from list with the stored CSV, does not overwrite the original CSV
	 *
	 * @param row number 
	 */
	public void removeRow(int row)
	{
		list.remove(row);
	}
	
	/**
	 * Joins two open CSVs (lists) together
	 * This method will probably be rewritten and/or removed
	 *
	 * @param the other CSV
	 */
	public void joinCSV(List<List<String>> csv)
	{
		//I think there are built in methods to get this to work but...
		//I am honestly just too tired to do that now
		List<List<String>> output = new ArrayList<List<String>>();
		
		for(int i = 0; i < list.size(); i++)
		{
			output.add(new ArrayList<>());
			
			for (int j = 0; j < list.get(i).size(); j++)
				output.get(i).add(list.get(i).get(j));
			
			for (int j = 0; j < csv.get(i).size(); j++)
				output.get(i).add(csv.get(i).get(j));
		}
		
		list = output;
	}
	
	/**
	 * Saved current store CSV data as a CSV
	 *
	 * @param File name and/or path
	 */
	public void saveCSV(String file)
	{
		try {
			PrintWriter pw = new PrintWriter(new File(file));
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < list.size(); i++)
			{
				for(int j = 0; j < list.get(i).size(); j++)
				{
					sb.append(list.get(i).get(j));
					if(j != list.get(i).size() -1)
						sb.append(',');
				}
				
				sb.append('\n');
			}
			pw.write(sb.toString());
	        pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}