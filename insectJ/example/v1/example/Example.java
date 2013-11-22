package example;

public class Example {

	public void go(String[] args) {
		if (args.length != 1) {
			System.err.println("Usage: java " + this.getClass().getName() + " <int value>");
			System.exit(1);
		}
		int i = Integer.parseInt(args[0]);
		if (i > 100) {
			System.out.println("Error: i is too large");
		}
		else {
			System.out.println("ok");			
		}
	}
}
