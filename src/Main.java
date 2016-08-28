
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Parser parser = new Parser("test( 1 + 2 ) * 3");
		Expression exp = parser.compile();
		System.out.println(exp.evaluate());
	}

}
