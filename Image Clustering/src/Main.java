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
		int numOfClusters = 3; 			// should not be > 27, if cluster is colored
		int numOfKmeansIteration = 30; 	// number of times to be averaged
		int comparisonOn = brightness; 	// (hue/brightness/saturation)
		int contrast = 0;
		int brightness = 0;
		/*****************************************/
		
		File input = new File("path to your input file.jpg");
		BufferedImage image = ImageIO.read(input);	
		
		File output = new File("path to your output file");
		
		Cluster cluster = new Cluster(numOfClusters, comparisonOn);
		cluster.distributeColors(image, random);
		cluster.runKMeans(numOfKmeansIteration);
		image = cluster.applyClustering(image, colored); //(averaged/colored)
		
		image = ImgProcessor.applyTweaks(image, contrast, brightness);
		
		try {
			ImageIO.write(image, "jpg", output);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("[*] Image written to disk.");
	}
	
}
