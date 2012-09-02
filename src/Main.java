import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * This is the main class of the program, this programs gets the driving distance and time 
 * from a list of address to a certain second address. The program reads a user input 
 * file that has the "From" address in each line and then calculates the distance and time 
 * to a second user input address. The result is then saved in a user named .cvs file.
 * @author dany
 *
 */
public class Main {

	public static void main(String[] args) {

		if(args.length == 0){
			Gui.startGui(null);
		} else {
			if(args.length == 1){
				String param = args[0];
				if(param.equals("-c")){

					try {

						//User input buffer
						BufferedReader brUser = new BufferedReader(new InputStreamReader(System.in));

						//Get Input File Name
						System.out.print("Enter the 'From Here' addresses list File name: ");
						String inputFile = brUser.readLine();

						//Get Second Address
						System.out.print("Enter the 'To Here' address: ");
						String address2 = brUser.readLine();

						//Get Output File Name
						System.out.print("Enter the output file name: ");
						String outputFile = brUser.readLine() + ".csv";

						try {
							new Magic(inputFile, outputFile, address2, System.out).execute();
						} catch (Exception e){
							System.exit(1);
						}

					} catch (IOException e){
						e.printStackTrace();
					}
				} else {
					System.out.println("Invalid argument, only argument available is '-c' for Console mode.");
				}
			} else {
				System.out.println("Too many arguments!");
			}
		}
	}

}