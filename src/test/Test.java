package test;

import java.util.Date;

public class Test {
	private static String JAVA_SOURCE_FILE = "DynamicObject.java";
	private static String JAVA_CLASS_FILE = "DynamicObject.class";
	private static String JAVA_CLASS_NAME = "DynamicObject";

	public static void main(String[] args) throws Exception{
		Date d1=new Date(2016,1,18);
		Date d2=new Date(2012,4,2);
		int month = (d1.getYear()-d2.getYear())*12;
		month += d1.getMonth() - d2.getMonth();
		System.out.println(d1.getYear()+"==="+d2.getYear()+"==="+month/12+"=="+(month%12));
//		now.getMonth()
	}
	
	
}
