public class Bird extends Animal {
	
	// The constructor initializes all objects
	
	public Bird() {
		
		super();
		
		setSound("Tweet");
	}
	/* No need to override fly method as the default implementation
	 * works nicely with birds	 
	 public void fly(){
		
		System.out.println("I can't fly");
		
	}
	*/
}
