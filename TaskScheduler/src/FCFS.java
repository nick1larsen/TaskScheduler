import java.util.Queue;

/**
 * 
 * This class simulates the First Come First Served of Task scheduling. It takes in an array of process information and 
 * creates a new instance of the Process class. It then prioritizes the processes in the FCFS method
 * @author Nick
 *
 */
public class FCFS extends Algorithm {



	/**
	 * Constructor for FCFS class
	 * @param processArray
	 */
	public FCFS(int[][] processArray) {
		super(processArray);	
		System.out.println("Now executing the FCFS algorithm");
	}

	/**
	 * This method runs the first come first serve algorithm
	 */
	public void runFCFS() {
		int processArrayLocation = 0;
		boolean stillRunning = true;

		//this while loop will continue as long as there are active or waiting processes
		while(stillRunning == true) {

			System.out.println("\nThe current time is " + super.getTime());
			//this for loop checks to see if any processes have arrived at this time
			for(int i = 0; i < super.listOfProcess.length; i++) {
				if(super.listOfProcess[i].getArrivalTime() == super.getTime()) {
					System.out.println("Process ID " + super.listOfProcess[i].getProcessID() + " has arrived");
					super.addToActiveProcessQ(super.listOfProcess[i].getProcessID());
				}
			}

			//this sets the value of processArray location to the location for the process id that is running
			for(int i = 0; i<super.listOfProcess.length; i++) {
				if(super.peekActiveProcessQ() == super.listOfProcess[i].getProcessID()) {
					processArrayLocation = i;
					//System.out.println("this is the process ID" + super.listOfProcess[i].getProcessID() + " and i is" + i);

				}
			} 


			//this condition checks to see if there are any processes left in the queue. if there are none it terminates the initial while loop
			if(super.peekActiveProcessQ()==999999) {
				boolean additionalProcesses;
				
				//this for loop checks to see if there are any processes that have not made it to the listOfProcess queue
				for(int i = 0; i < super.getLengthListOfProcess(); i++) {
					if(super.listOfProcess[i].getRemainingBurst() != 0) {
						stillRunning = true;
						i = super.getLengthListOfProcess();
						System.out.println("Nothing is running");
						super.addToProcessGantt(null);
						super.incrementTime();
					} else {
						stillRunning = false;
					}
				}

				//this states which process is running, and reduces the remaining burst by 1
			} else if (super.listOfProcess[processArrayLocation].getRemainingBurst() != 0) {
				System.out.println("Process ID " + super.activeProcessQ.peek() + " is running");
				
				//this sets the start time, however the method does not allow the start time to change
				super.listOfProcess[processArrayLocation].setStartTime(super.getTime());

				super.listOfProcess[processArrayLocation].decreaseBurst();
				System.out.println("It has a reminaing burst of " + super.listOfProcess[processArrayLocation].getRemainingBurst());
				super.addToProcessGantt(super.listOfProcess[processArrayLocation].getProcessID());
				super.incrementTime();
				//this condition checks to see if the current active process has any burst left, if not it removes it from the queue
				if(super.listOfProcess[processArrayLocation].getRemainingBurst() == 0) {
					System.out.println("Process ID " + super.activeProcessQ.poll() + " has no remaining burst and is being deactivated");
					super.listOfProcess[processArrayLocation].calcTurnaroundTime(super.getTime());
					super.listOfProcess[processArrayLocation].calcWaitingTime();
				}
			}



		}
		super.calcAverageWaitTime();
		super.calcAverageTurnaroundTime();
	}

}//FCFS
