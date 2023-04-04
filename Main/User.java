package Main;

public abstract class User {
	
	private String UserID;
	private String password = "password";
	private Request[] reqHist;

	public User() {}
	
	public abstract void changePW();
	
	public abstract String getPW();
	
	public abstract String getID();
	
	public abstract void viewRequest();


}
