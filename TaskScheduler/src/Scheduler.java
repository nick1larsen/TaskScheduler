import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
 * 
 * FILL THS IN!!!!
 * 
 * 
 * 
 */
public class Scheduler {

	public static void main(String[] args) throws FileNotFoundException {



		int numberOfLines = 0;


		//This try statement opens up the CSV, determines how many lines there are in the file, sets up the processArray
		//with that many lines and then populates the processArray with the data from the CSV
		try {
			Scanner scan = new Scanner(new File("C:\\Users\\Nick\\Desktop\\input.csv"));

			while(scan.hasNextLine()) {
				numberOfLines++;
				scan.nextLine();
			}

			scan.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found. Terminating.");
		}

		//This array of process data is populated from the CSV
		int[][] processArray = new int[numberOfLines][3];

		try {
			Scanner scan = new Scanner(new File("C:\\Users\\Nick\\Desktop\\input.csv"));
			//scan2.useDelimiter(",");


			for(int i = 0; i < processArray.length; i++) {
				String line = scan.nextLine();
				for(int y = 0; y < processArray[i].length; y++) {
					int entry;
					if(line.contains(",")) {
						entry = Integer.parseInt(line.substring(0, line.indexOf(",")));
						line = line.substring(line.indexOf(",")+1);
					} else {
						entry = Integer.parseInt(line);
					}
					processArray[i][y] = entry;
				}

			}
			scan.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found. Terminating.");
		}


	//	FCFS fcfs = new FCFS(processArray);
	//	fcfs.runFCFS();

		SJF sjf = new SJF(processArray);
		sjf.runSJF();
		
	//	STCF stcf = new STCF(processArray);
	//	stcf.runSTCF();
		

	}//main

}//Scheduler
