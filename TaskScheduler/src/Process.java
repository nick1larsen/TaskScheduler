
public class Process {
	
	private int processID, arrivalTime, burst;
	private double arrTime;
	private int remainingBurst;
	private int endTime, waitingTime, turnaroundTime;
	private Integer startTime;
	
	
	Process(int processID, int arrivalTime, int burst){
		this.processID = processID;
		this.arrivalTime = arrivalTime;
		this.burst = burst;	
		remainingBurst=burst;
	}
	
	
	/**
	 * Reduces the remainging burst by one 
	 */
	public void decreaseBurst(){
		remainingBurst = remainingBurst - 1;
	}
	
	
	/**
	 * Returns processID
	 * @return
	 */
	public int getProcessID() {
		return processID;
	}
	
	/**
	 * Returns arrivalTime
	 * @return
	 */
	public int getArrivalTime() {
		return arrivalTime;
	}
	
	/**
	 * Returns remaining burst
	 * @return
	 */
	public int getRemainingBurst() {
		return remainingBurst;
	}
	
	/**
	 * Returns burst
	 * @return
	 */
	public int getBurst() {
		return burst;
	}
	
	public void setStartTime(int time) {
	//	System.out.println("start time was " + startTime);
		//System.out.println("does startTime really == null? " + startTime==null);
        if(startTime == null) {
       // 	System.out.println("in here");
        	startTime = time;
        }
	//	System.out.println("start time is now " + startTime);
	}
	
	/**
	 * Calculates the waiting time for a process
	 * @param startTime
	 */
	public void calcWaitingTime() {
		waitingTime = turnaroundTime - burst;
		System.out.println("waitingTime is " + waitingTime);
	}

	/**
	 * Returns waitingTime
	 * @return
	 */
	public int getWaitingTime() {
		return waitingTime;
	}
	
	/**
	 * Calculates the turnaroundTime for a process
	 * @param endTime
	 */
	public void calcTurnaroundTime(int endTime) {
		this.endTime = endTime;
		turnaroundTime = endTime - startTime;
		System.out.println("turnaround time is " + turnaroundTime);
	}
	
	/**
	 * Returns turnaroundTime
	 * @return
	 */
	public int getTurnaroundTime() {
		return turnaroundTime;
	}
}
