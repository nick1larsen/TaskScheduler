import java.util.LinkedList;
import java.util.Queue;

public abstract class Algorithm {

	protected int[][] processArray;
	private int time = 0;
	private double averageTurnaroundTime = 0.0;
	private double averageWaitTime = 0.0;
	protected Process[] listOfProcess;
	protected Queue<Integer> activeProcessQ = new LinkedList<>();
	private String processGantt = "|";

	Algorithm(int[][] processArray){

		this.processArray = processArray;
		//this array is an array of class Process objects. It will store the processes that will be run
		listOfProcess = new Process[processArray.length];

		//populating the listOfProcess with the process data from the CSV
		for(int i = 0; i < listOfProcess.length; i++) {
			listOfProcess[i] = new Process(processArray[i][0], processArray[i][1], processArray[i][2]);
			//activeProcessQ.add(listOfProcess[i].getProcessID());
			//System.out.println(activeProcessQ.);
		}


	}

	protected int getLengthListOfProcess(){
		return listOfProcess.length;
	}


	/**
	 * Prints the process Array
	 */
	protected void printArray() {
		for(int i = 0; i < processArray.length; i++) {
			for(int y = 0; y < processArray[i].length; y++) {
				System.out.print(processArray[i][y] + ",");
			}
			System.out.println();
		}
	}

	/**
	 * Returns the current time
	 * @return
	 */
	protected int getTime() {
		return time;
	}

	/**
	 * Adds one to the time
	 */
	protected void incrementTime(){
		time++;
	}

	/**
	 * Adds a process ID to the back of the activeProcessQ to be ready to run
	 * @param processID
	 */
	public void addToActiveProcessQ(int processID) {
		activeProcessQ.add(processID);
	}
	
	/**
	 * This method prioritizes the queue using the SJF algorithm
	 * @param processID
	 */
	public void SJFAddToActiveProcessQ(int processID){
		int tempProcessArr[] = new int[activeProcessQ.size()];
		int newProcessID = processID;
		int positionOfNewProcessID = 0;
		int spotInTempProcess = 0;
		boolean newProcessAdded = false;

		//if there is nothing in the activeProcessQ the new processID is added, otherwise this checks to see which process has the greater remaining burst
		if(activeProcessQ.size()==0) {
			activeProcessQ.add(newProcessID);
		}else {
			for(int i = 0; i < tempProcessArr.length; i++) {
				tempProcessArr[i] = activeProcessQ.poll();
			}
			


			//sets the position in listOfProcess to the one that matches the id of processID
			for(int i = 0; i < listOfProcess.length; i++) {
				if(listOfProcess[i].getProcessID() == newProcessID) {
					positionOfNewProcessID = i;
					i = listOfProcess.length;
				}
			}
			
			//if the process that was at the head of the queue has begun, it gets added back to the active process queue
			for(int i = 0; i < listOfProcess.length; i++) {
				if(tempProcessArr[0] == listOfProcess[i].getProcessID()) {
					if(listOfProcess[i].getRemainingBurst() < listOfProcess[i].getBurst()) {
						activeProcessQ.add(listOfProcess[i].getProcessID());
						spotInTempProcess++;
					}
					i = listOfProcess.length;
				}			
			}

			while (newProcessAdded == false) {

				//loop that adds the newest process to the activeProcessQ based on its remaining burst
				for(int i = 1; i < tempProcessArr.length; i++) {
					int oldQueuePosition = 0;
				
					//sets the location of the queue to the location in the listOfProcess
					for(int y = 0; y < listOfProcess.length; y++) {
						if(listOfProcess[y].getProcessID() == tempProcessArr[i]) {
							oldQueuePosition = y;
							y = listOfProcess.length;
						}
					}
					//checks whether the new process has a lower burst than this process and adds it to the queue
					if(listOfProcess[positionOfNewProcessID].getRemainingBurst() < listOfProcess[oldQueuePosition].getRemainingBurst()){
						activeProcessQ.add(listOfProcess[positionOfNewProcessID].getProcessID());
						activeProcessQ.add(listOfProcess[oldQueuePosition].getProcessID());
						newProcessAdded = true;
						i = tempProcessArr.length;
					//checks whether the new process has a larger burst than the old one and adds the old one to the queue
					} else if(listOfProcess[positionOfNewProcessID].getRemainingBurst() > listOfProcess[oldQueuePosition].getRemainingBurst()){
						activeProcessQ.add(listOfProcess[oldQueuePosition].getProcessID());
					//checks whether the bursts are equal and adds them both
					}else if(listOfProcess[positionOfNewProcessID].getRemainingBurst() == listOfProcess[oldQueuePosition].getRemainingBurst()){
						activeProcessQ.add(listOfProcess[oldQueuePosition].getProcessID());
						activeProcessQ.add(listOfProcess[positionOfNewProcessID].getProcessID());
						newProcessAdded = true;
						i = tempProcessArr.length;
						//adds the new process if it has a larger burst than all other processes
					} 


					spotInTempProcess++;
				}
				
				 if(newProcessAdded==false){
					activeProcessQ.add(listOfProcess[positionOfNewProcessID].getProcessID());
					newProcessAdded = true;
				}
			}

			for(int i = spotInTempProcess; i < tempProcessArr.length; i++) {
				activeProcessQ.add(tempProcessArr[i]);
			}
		}
	}

	/**
	 * This method takes in the ID of the process that just arrived and adds it to the activeProcessQ based on its burst time
	 * @param processID
	 */
	public void STCFAddToActiveProcessQ(int processID) {
		int tempProcessArr[] = new int[activeProcessQ.size()];
		int newProcessID = processID;
		int positionOfNewProcessID = 0;
		int spotInTempProcess = 0;
		boolean newProcessAdded = false;



		//if there is nothing in the activeProcessQ the new processID is added, otherwise this checks to see which process has the greater remaining burst
		if(activeProcessQ.size()==0) {
			activeProcessQ.add(newProcessID);
		}else {
			for(int i = 0; i < tempProcessArr.length; i++) {
				tempProcessArr[i] = activeProcessQ.poll();
			}

			//sets the position in listOfProcess to the one that matches the id of processID
			for(int i = 0; i < listOfProcess.length; i++) {
				if(listOfProcess[i].getProcessID() == newProcessID) {
					positionOfNewProcessID = i;
					i = listOfProcess.length;
				}
			}

			while (newProcessAdded == false) {

				//loop that adds the newest process to the activeProcessQ based on its remaining burst
				for(int i = 0; i < tempProcessArr.length; i++) {
					int oldQueuePosition = 0;
				
					//sets the location of the queue to the location in the listOfProcess
					for(int y = 0; y < listOfProcess.length; y++) {
						if(listOfProcess[y].getProcessID() == tempProcessArr[i]) {
							oldQueuePosition = y;
							y = listOfProcess.length;
						}
					}
					//checks whether the new process has a lower burst than this process and adds it to the queue
					if(listOfProcess[positionOfNewProcessID].getRemainingBurst() < listOfProcess[oldQueuePosition].getRemainingBurst()){
						activeProcessQ.add(listOfProcess[positionOfNewProcessID].getProcessID());
						activeProcessQ.add(listOfProcess[oldQueuePosition].getProcessID());
						newProcessAdded = true;
						i = tempProcessArr.length;
					//checks whether the new process has a larger burst than the old one and adds the old one to the queue
					} else if(listOfProcess[positionOfNewProcessID].getRemainingBurst() > listOfProcess[oldQueuePosition].getRemainingBurst()){
						activeProcessQ.add(listOfProcess[oldQueuePosition].getProcessID());
					//checks whether the bursts are equal and adds them both
					}else if(listOfProcess[positionOfNewProcessID].getRemainingBurst() == listOfProcess[oldQueuePosition].getRemainingBurst()){
						activeProcessQ.add(listOfProcess[oldQueuePosition].getProcessID());
						activeProcessQ.add(listOfProcess[positionOfNewProcessID].getProcessID());
						newProcessAdded = true;
						i = tempProcessArr.length;
						//adds the new process if it has a larger burst than all other processes
					} 


					spotInTempProcess++;
				}
				
				 if(newProcessAdded==false){
					activeProcessQ.add(listOfProcess[positionOfNewProcessID].getProcessID());
					newProcessAdded = true;
				}

				
			}

			for(int i = spotInTempProcess; i < tempProcessArr.length; i++) {
				activeProcessQ.add(tempProcessArr[i]);
			}
		}

	}

	/**
	 * Returns the head of the activeProcessQ without removing it
	 * @return
	 */
	public int peekActiveProcessQ(){
		if(activeProcessQ.peek()==null) {
			return 999999;
		}else {
			return activeProcessQ.peek();
		}
	}

	/**
	 * Returns the head of the activeProcessQ and removes it.
	 * @return
	 */
	public int pollActiveProcessQ() {
		return activeProcessQ.poll();
	}

	/**
	 * Adds the process that just ran to the processGantt String
	 * @param process
	 */
	public void addToProcessGantt(Integer process) {
		processGantt = processGantt + process + "|";
		System.out.println(processGantt);
	}

	/**
	 * Calculates the average wait time
	 */
	public void calcAverageWaitTime(){
		double totalWaitTime = 0.0;
		for(int i = 0; i < listOfProcess.length; i++) {
			totalWaitTime = totalWaitTime + listOfProcess[i].getWaitingTime();
		}
		averageWaitTime = totalWaitTime / listOfProcess.length;
		System.out.println(averageWaitTime + " is averageWaitTime");
	}

	public void calcAverageTurnaroundTime() {
		double totalTurnaroundTime = 0.0;
		for(int i = 0; i < listOfProcess.length; i++) {
			totalTurnaroundTime = totalTurnaroundTime + listOfProcess[i].getTurnaroundTime();
		}
		averageTurnaroundTime = totalTurnaroundTime / listOfProcess.length;
		System.out.println(averageTurnaroundTime + " is average Turnaroundtime");
	}



}//Algorithm

