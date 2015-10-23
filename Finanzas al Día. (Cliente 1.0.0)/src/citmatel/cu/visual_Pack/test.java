package citmatel.cu.visual_Pack;

import java.util.Random;

import citmatel.cu.class_Pack.Utils;



public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*Random random=new Random();
		int var= random.nextInt();
		System.out.println(var);*/
		
		String var="none";
		int hashCode = Math.abs(var.hashCode());
		System.out.println(Utils.fillUntil4Digits(hashCode % 10000));
		

	}

}
