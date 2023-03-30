package OOP;

public abstract class Request { //actually why abstract? what are some unimplemented methods
	
	  private boolean approvalStatus;
	  private int requestDate;
	  
	  public boolean getApprovalStatus() {
	    return approvalStatus;
	  }

	  public void setApprovalStatus(boolean approvalStatus) {
	    this.approvalStatus = approvalStatus;
	  }

	  public double getRequestDate() {
	    return requestDate;
	  }

	  public void getRequestDate(int requestDate) {
	    this.requestDate = requestDate;
	  }
}
