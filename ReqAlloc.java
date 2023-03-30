package OOP;

public class ReqAlloc extends Request {
	
	  enum RegisterType {Register, Deregister}

	  private String cood;
	  private String sup;
	  private RegisterType type;
	  
	  public String getCood() {
	    return cood;
	  }
	  
	  public void setCood(String cood) {
	    this.cood = cood;
	  }
	  
	  public String getSup() {
	    return sup;
	  }
	  
	  public void setSup(String sup) {
	    this.sup = sup;
	  }
	  
	  public RegisterType getType() {
	    return type;
	  }
	  
	  public void setType(RegisterType type) {
	    this.type = type;
	  }

}
