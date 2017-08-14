import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

public class Cluster {

	int numOfCluster;
	ArrayList<Color>[] cluster;
	Color centroid[], newCentroid[];

	int comparisonOn;
	
	public Cluster(int numOfCluster, int comparisonOn){
		this.numOfCluster = numOfCluster;		
		cluster = new ArrayList[numOfCluster];
		
		for(int i=0; i<numOfCluster; i++){
			cluster[i] = new ArrayList<Color>();
		}
		
		this.comparisonOn = comparisonOn;
		
		centroid = new Color[numOfCluster];
		newCentroid = new Color[numOfCluster];
	}
	
	public void distributeColors(BufferedImage image, int choice){
		if(choice == 0){
			distributeColorsEqually(image);
		}
		else if(choice == 1){
			distributeColorsRandomly(image);
		}
		
		calculateCentroid();
	}
	
	private void distributeColorsEqually(BufferedImage image){
		
		List<Color> allColors = new ArrayList<Color>();
		
		for(int i=0; i<image.getWidth(); i++){
			for(int j=0; j<image.getHeight(); j++){
				Color color = new Color(image.getRGB(i, j));
				allColors.add(color);
			}
		}
		
		System.out.println("[*] Grouping colors...");
		allColors = ImgProcessor.colorSort(allColors, comparisonOn);
		
		System.out.println("[*] Distributing colors in groups...");
		int pixelsInAGroup = allColors.size() / numOfCluster;
		
		int colorIndex = 0;
		for(int i=0; i<numOfCluster; i++){
			for(int j=0; j<pixelsInAGroup; j++){
				cluster[i].add(allColors.get(colorIndex++));
			}
		}
	}	
	
	private void distributeColorsRandomly(BufferedImage image){
		Random random = new Random();
		
		for(int i=0; i<image.getWidth(); i++){
			for(int j=0; j<image.getHeight(); j++){
				int pos = random.nextInt(numOfCluster);
				
				Color color = new Color(image.getRGB(i, j));
				cluster[pos].add(color);
			}
		}
	}
	
	public void runKMeans(int n){
		
		System.out.println("Running K-means");
		
		for(int i=0; i<n; i++){
			System.out.println("Mean iteration: " + (i+1));
			
			centroid = newCentroid;
			
			ArrayList<Color>[] tempCluster = new ArrayList[numOfCluster];
			for(int j=0; j<numOfCluster; j++){
				tempCluster[j] = new ArrayList<Color>();
			}
			
			for(List<Color> list: cluster){
				for(Color color: list){
					int bestFit = findBestGroup(color);
					tempCluster[bestFit].add(color);
				}
			}
			
			cluster = tempCluster;
			
			calculateCentroid();
		}
	}
	
	public BufferedImage applyClustering(BufferedImage image, int type){
		for(int i=0; i<image.getWidth(); i++){
			for(int j=0; j<image.getHeight(); j++){
				
				Color color = new Color(image.getRGB(i, j));
				int pos = findBestGroup(color);
				
				Color selectedGroupColor = null;
				
				if(type == 0){
					selectedGroupColor = centroid[pos];
				}
				else if(type == 1){
					selectedGroupColor = ImgProcessor.getColor(pos);
				}
				
				image.setRGB(i, j, selectedGroupColor.getRGB());
			}
		}
		
		return image;
	}
	
	private int findBestGroup(Color color){
		int bestFItInCluster = 999999;
		double leastDiff = 999999;
		
		for(int i=0; i<numOfCluster; i++){
			double diff = calculateDistance(color, centroid[i]); 
			
			if(diff < leastDiff){
				leastDiff = diff;
				bestFItInCluster = i;
			}
		}
		
		return bestFItInCluster;
	}
	
	
	private static double calculateDistance(Color c1, Color c2){
		double result = 0;
		
		result += Math.pow(c1.getRed() - c2.getRed(), 2);
		result += Math.pow(c1.getGreen() - c2.getGreen(), 2);
		result += Math.pow(c1.getBlue() - c2.getBlue(), 2);
		
		return Math.sqrt(result);
	}
	
	
	private void calculateCentroid(){
		newCentroid = new Color[numOfCluster];
		
		for(int i=0; i<numOfCluster; i++){
			
			int r=0, g=0, b=0;			
			
			/* here I'm taking the average of the colors, the 
			 * formula for averaging colors is a bit different than you might think */
			
			for(Color color: cluster[i]){
				r += Math.pow(color.getRed(), 2);
				g += Math.pow(color.getGreen(), 2);
				b += Math.pow(color.getBlue(), 2);
			}
			
			int count = cluster[i].size();
			
			if(count != 0){
				r /= count;
				g /= count;
				b /= count;
			}
			
			r = (int) Math.sqrt(r);
			g = (int) Math.sqrt(g);
			b = (int) Math.sqrt(b);
			
			newCentroid[i] = new Color(r, g, b);
		}
	}
	
	
}
