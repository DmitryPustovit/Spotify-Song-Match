import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CSVReader {
	
	private List<List<String>> list = new ArrayList<>();
	
	public CSVReader(String file)
	{
		try {
			Scanner scanner = new Scanner(new File(file));
	        while(scanner.hasNext()){
	        	String currentLine = scanner.nextLine().replace("/n", "");
	        	String[] values = currentLine.split(",");
	        	
	        	list.add(Arrays.asList(values));
	        }
	        
	        scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public List<List<String>> getCSVData()
	{
		return list;
	}
	
	public String[] getCSVColumnData(int col)
	{
		String[] arr = new String[list.size()];
		
		for(int i = 0; i < list.size(); i++)
			arr[i] = list.get(i).get(col);
		
		return arr;
	}
	
	public void removeRow(int row)
	{
		list.remove(row);
	}
	
	public void joinCSV(List<List<String>> csv)
	{
		list.addAll(csv);
	}
	
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