
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Parser parser = new Parser("( 2[m] * 2[m] )");
		Expression exp = parser.compile();
		System.out.println(exp.evaluate());
	}

}
