package com.mycompany.app;

//Start Time Sun Jun 29 10:43:46 PDT 2014
// java -cp target/fizz-buzz-1.0-SNAPSHOT.jar com.mycompany.app.FizzBuzz 90 23 26 13 18 41 69 79 55 5 2 52 34 10 63 3 29 47 74 85 92 22 42 50 22 60 5 89 6 49 77 91 96 84 42 74 47 60 25 88 41 67 33 22 44 67 60 37 90 82 22 59 89 5 26 37 92 73 64 48 80 14 84 84 54 28 82 27 11 89 29 95 74 100 91 100 62 61 21 50 4 40 12 56 42 33 55 33 19 43 41 7 88 23 7 89 59 8 50 91
//Maven Googled
//End Time Sun Jun 29 11:11:20 PDT 2014
public class FizzBuzz {

	public static void main(String [] args){
		
		if (args.length < 100){
			System.out.println("Specify Numbers for FizzBuzz");
			return;
		} 
		
		for(int i=0;i<args.length;i++){
			//Initialize Input and Output
			String output="NONE";
			String sInput = args[i];
			try{
				int input= Integer.parseInt(sInput);
				if(input % 28 == 0){
					output="FIZZBUZZ";
				} else if(input % 4 == 0){
					output="FIZZ";
				} else if (input % 7 == 0){
					output="BUZZ";
				} 
			}catch (Exception e){}
			
			System.out.println(sInput + " " + output);
				
		}
	}	
}