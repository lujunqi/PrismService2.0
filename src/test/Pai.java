package test;

public class Pai {

	public static void main(String[] args) {
		System.out.format("%s \n","x");
		double a = 0;
		boolean f = true;
		for (double i = 1; i < 9999999999d; i=i+2) {
			
			if(f) {
				a = a + 1/i;
				f= false;
			}else {
				a = a - 1/i;
				f = true;
			}
		}
		System.out.println(a*4+"=="+a);
	}

}
