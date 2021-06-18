public class Zoo {
	
	public static void main(String[] args) {
		
		Animal sparky = new Dog();
		Animal tweety = new Bird();
		
		System.out.print("Dog: ");
		sparky.fly();
		
		System.out.print("Bird: ");
		tweety.fly();
	}
	
}
