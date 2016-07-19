//ImageRunner.java

import imagereader.Runner;

public class ImageRunner {

	 public static void main(String[] args){
	        ImageIOImplement imageioer = new ImageIOImplement();  
	        ImageProcessor processor = new ImageProcessor();  
	        Runner.run(imageioer, processor);  
	    } 

}

