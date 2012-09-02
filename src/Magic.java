import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.JButton;
import javax.swing.SwingWorker;

import org.json.me.JSONArray;
import org.json.me.JSONException;
import org.json.me.JSONObject;


//Where all the Magic happens...
public class Magic extends SwingWorker{

	private String inputFile;
	private String outputFile;
	private String address2;
	private PrintStream printStream;
	private JButton myButton;
	private boolean isAllGood;

	public Magic(String inputFile, String outputFile, String address2, PrintStream printStream){ 
		super();
		this.inputFile = inputFile;
		this.outputFile = outputFile;
		this.address2 = address2;
		this.printStream = printStream;
	}

	@Override
	protected Object doInBackground() throws Exception {
		
		if (inputFile.length() == 0 || outputFile.length() == 0 || address2.length() == 0){
			printStream.println("You lelft some parameter empty! Please try again!");
			throw new Exception("Empty Parameters");
		}
		
		BufferedReader br = null;
		BufferedWriter out = null;
		
		try{

			//Set I/O
			br = new BufferedReader(new FileReader(inputFile));
			out = new BufferedWriter(new FileWriter(outputFile));

			String line;

			//Write Output file header
			String header = "From Here,To Here,Distance,Time\n";
			out.write(header);

			//Input file line loop
			int i = 0;
			while ((line=br.readLine()) != null) {
				setProgress(i);
				//Get the httpRequest with the given 2 addresses
				//Response is in JSON format
				JSONObject json = HttpRequest.getJSON(line, address2);

				//Navigate the JSON to the info we need
				JSONArray midJson;
				midJson = json.getJSONArray("rows");
				midJson = midJson.getJSONObject(0).getJSONArray("elements");
				JSONObject infoJson = midJson.getJSONObject(0);

				//If Result is OK then get result from JSON
				String outString = "";
				if(infoJson.getString("status").equalsIgnoreCase("OK")){
					String distance = infoJson.getJSONObject("distance").getString("text");
					String time = infoJson.getJSONObject("duration").getString("text");
					outString = line.replace(",", "") + "," + address2.replace(",", "") + "," + distance + "," + time;
				} else if (infoJson.getString("status").equalsIgnoreCase("OVER_QUERY_LIMIT")){
					printStream.println("That's it for today... You have reached the 2,500 queries per 24 hours limit Google has. You can try again tomorrow, or maybe from a diffrent computer (Different IP)");
					throw new Exception("Over Query Limit.");
				} else {
					outString = line.replace(",", "") + "," + address2.replace(",", "") + "," + "NO_RESULT" + "," + "NO_RESULT";
				}

				//Write result
				printStream.println(outString);
				out.write(outString + "\n");
			}

			//Close output file
			printStream.println("------------DONE------------\nResults saved in: " + outputFile);
			isAllGood = true;

		} catch (JSONException e) {
			printStream.println("Something went really bad, I got invalid response from Google.");
			throw new Exception("Invalid response from Google.");
		} catch (FileNotFoundException e) {
			printStream.println("Unable to read file... =(");
			throw new Exception("Unable to read file.");
		} catch (IOException e) {
			printStream.println("Oh, oh! Looks like someone is blocking your access to Google's Map API! Or maybe you are not connected to the internet...");
			throw new Exception("Google's Map API Blocked, No Internet");
		} finally {
			if(out != null)
				out.close();
		}

		return true;
	}

	@Override
	protected void done() {
		if(isAllGood)
			printStream.println("\nThank you!\t-Daniel Santiago, Intern");
		if(myButton != null)
			myButton.setEnabled(true);
	}
	
	public void setOKBtn(JButton button){
		myButton = button;
	}

}
