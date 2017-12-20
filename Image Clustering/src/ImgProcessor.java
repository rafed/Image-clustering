import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ImgProcessor {

	public static BufferedImage applyTweaks(BufferedImage image, int contrast, int brightness){
		System.out.println("[*] Applying tweaks...");
		
		for(int i=0; i<image.getWidth(); i++){
			for(int j=0; j<image.getHeight(); j++){
				Color color = new Color(image.getRGB(i, j));
				
				color = changeContrast(color, contrast);
				color = changeBrightness(color, brightness);
				
				image.setRGB(i, j, color.getRGB());
			}
		}
		
		return image;
	}
	
	private static Color changeContrast(Color color, int contrast){
		
		int factor = (259 * (contrast + 255)) / (255 * (259 - contrast));
		
		int r = truncate(factor * (color.getRed()  - 128) + 128);
		int g = truncate(factor * (color.getGreen()  - 128) + 128);
		int b = truncate(factor * (color.getBlue()  - 128) + 128);
		
		return new Color(r, g, b);
	}
	
	private static Color changeBrightness(Color color, int brightness){
		
		int r = truncate(color.getRed() + brightness);
		int g = truncate(color.getGreen() + brightness);
		int b = truncate(color.getBlue() + brightness);
		
		return new Color(r, g, b);
	}
	
	public static List<Color> colorSort(List<Color> list, int HSB){
		
		Collections.sort(list, new Comparator<Color>() {
			public int compare(Color c1, Color c2){
				float[] hsb1 = Color.RGBtoHSB(c1.getRed(), c1.getGreen(), c1.getBlue(), null);
				float[] hsb2 = Color.RGBtoHSB(c2.getRed(), c2.getGreen(), c2.getBlue(), null);
				
				// comparing hues
				if(hsb1[HSB] > hsb2[HSB])
					return 1;
				else if(hsb1[HSB] < hsb2[HSB])
					return -1;
				else return 0;
			}
		});
		
		return list;
	}
	
	public static Color getColor(int c){
		if(c == 0)	return new Color(255, 51, 51);
		if(c == 1)	return new Color(0, 255, 0);
		if(c == 2)	return new Color(0, 0, 255);
		if(c == 3)	return new Color(255, 128, 0);
		if(c == 4)	return new Color(128, 255, 0);
		if(c == 5)	return new Color(0, 128, 255);
		if(c == 6)	return new Color(255, 255, 0);
		if(c == 7)	return new Color(0, 255, 128);
		if(c == 8)	return new Color(0, 255, 255);
		if(c == 9)	return new Color(127, 0, 255);
		if(c == 10)	return new Color(255, 0, 255);
		if(c == 11)	return new Color(255, 0, 127);
		if(c == 12)	return new Color(128, 128, 128);
		if(c == 13)	return new Color(255, 51, 51);
		if(c == 14)	return new Color(255, 153, 51);
		if(c == 15)	return new Color(255, 255, 51);
		if(c == 16)	return new Color(153, 255, 51);
		if(c == 17)	return new Color(51, 255, 51);
		if(c == 18)	return new Color(51, 255, 153);
		if(c == 19)	return new Color(51, 255, 255);
		if(c == 20)	return new Color(51, 153, 255);
		if(c == 21)	return new Color(51, 51, 255);
		if(c == 22)	return new Color(153, 51, 255);
		if(c == 23)	return new Color(255, 51, 255);
		if(c == 24)	return new Color(255, 51, 153);
		if(c == 25)	return new Color(160, 160, 160);
		if(c == 26)	return new Color(255, 102, 102);
		
		return Color.BLACK;
	}
	
	private static int truncate(int color){
		if(color < 0)
			return 0;
		else if(color > 255)
			return 255;
		return color;
	}
}
