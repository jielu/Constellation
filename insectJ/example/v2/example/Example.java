package example;

public class Example {

	public static void main(String[] args) {
		Example ex = new Example();
		ex.go(args);
	}

	public void go(String[] args) {
		if (args.length != 1) {
			System.err.println("Usage: java " + this.getClass().getName() + " <int values>");
			System.exit(1);
		}
		int i = Integer.parseInt(args[0]);
		if (i > 100) {
			System.out.println("Error: i should be no larger than 100");
		}
		else {
			System.out.println("ok");			
		}
	}
}
