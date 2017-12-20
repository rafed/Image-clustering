import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Main {

	final static int hue = 0;
	final static int saturation = 1;
	final static int brightness = 2;
	
	final static int equal = 0;
	final static int random = 1;
	
	final static int averaged = 0;
	final static int colored = 1;
	
	public static void main(String[] args) throws Exception{

		/*** Tweak here to give different effects!!! ****/
		int numOfClusters = 10; 		// should not be > 27, if cluster is colored
		int numOfKmeansIteration = 5; 	// number of times to be averaged
		int type = averaged;			//(averaged/colored)
		int comparisonOn = hue; 		// (hue/brightness/saturation)
		int contrast = 0;
		int brightness = 0;
		/*****************************************/
		
		File input = new File("/path/to/input.jpg");
		BufferedImage image = ImageIO.read(input);	
		
		File output = new File("/path/to/output.jpg");
		
		Cluster cluster = new Cluster(numOfClusters, comparisonOn);
		cluster.distributeColors(image, equal);
		cluster.runKMeans(numOfKmeansIteration);
		image = cluster.applyClustering(image, type); 
		
		image = ImgProcessor.applyTweaks(image, contrast, brightness);
		
		
		System.out.println("[*] Writing image to disk.");
		try {
			ImageIO.write(image, "jpg", output);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("[*] Done.");
	}
	
}
