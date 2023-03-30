package OOP;

public class ReqChangeSup extends Request {
	
	  private String projID;
	  private String replaceSupID;
	  private String studentID;
	  
	  public String getProjID() {
	    return projID;
	  }
	  public void setProjID(String projID) {
	    this.projID = projID;
	  }
	  public String getReplaceSupID() {
	    return replaceSupID;
	  }
	  public void setReplaceSupID(String replaceSupID) {
	    this.replaceSupID = replaceSupID;
	  }
	  public String getStudentID() {
	    return studentID;
	  }
	  public void setStudentID(String studentID) {
	    this.studentID = studentID;
	  }
}
