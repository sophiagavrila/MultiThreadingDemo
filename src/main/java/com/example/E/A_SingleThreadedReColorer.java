package com.example.E;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class A_SingleThreadedReColorer {

	/*
	 * Step 6. Add flowers.jpg to src/main/resources and add as constant variable
	 */

	public static final String SOURCE_FILE = "C:\\Users\\SophieGavrila\\Documents\\Threads\\Threads\\src\\main\\java\\com\\example\\E\\flowers.jpg";
	public static final String DESTINATION_FILE = "C:\\Users\\SophieGavrila\\Documents\\Threads\\Threads\\out\\flowers.jpg";

	public static void main(String[] args) throws IOException { // make sure you add throws declaration
		
		/*
		 * Step 7.
		 * Read original image into a buffered image object, using img.IO.read
		 * BufferedImage class represents all image data such as pixels, color space, and dimensions + methods
		 * to manipulate those pixels.
		 */
		BufferedImage originalImage = ImageIO.read(new File(SOURCE_FILE));
		
		/*
		 * Step 8.
		 * Create the output for the BufferedIMage where we will send the manipulated form.
		 * In the constructor we set the width, height, and color space which is in 
		 */
		BufferedImage resultImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);
		
		/*=======================================
		 * == To run the program & record time ==
		 * ======================================
		 */
		long startTime = System.currentTimeMillis();
		recolorSingleThreaded(originalImage, resultImage);
		long endTime = System.currentTimeMillis();
		
		
		long duration = endTime - startTime;
		
		File outputFile = new File(DESTINATION_FILE);
		ImageIO.write(resultImage, "jpg", outputFile);
		
		System.out.println("It took " + duration + " milliseconds to complete the operation using recolorSingleThread().");
	}
	
	/*
	 * Step 9.
	 * The recolor pixel method will take in the original img, result img,
	 * and the x, y coordinates of the pixel to recolor.
	 */
	public static void recolorPixelSingleThreaded(BufferedImage originalImage, BufferedImage resultImage, int x, int y) {
		// First get the rgb value of the partiuclar pixel in the image at those coordinates.
		int rgb = originalImage.getRGB(x, y);
		
		/*
		 *  then brek the rgb value of the pixel into individual red, blue, green components
		 *  by calling the first 3 helper methods we created (steps 1 - 3)
		 */
		
		int red = getRed(rgb);
		int green = getGreen(rgb);
		int blue = getBlue(rgb);
		
		/*
		 * Then, declare the new variables that will hold the new color values
		 * in the result image
		 */
		int newRed;
		int newGreen;
		int newBlue;
		
		/*
		 * ========================================================
		 * ==== This is the code that creates the purple color ====
		 * =========================================================
		 */
		if(isShadeOfGrey(red, blue, green)) {
	        newRed = Math.min(255, red + 10);
            newGreen = Math.max(0, green - 80);
            newBlue = Math.max(0, blue - 20);     // here we're just manipulating a purple color.
			
		} else { // if the pixel is NOT part of a white flower....
			newRed = red;
			newGreen = green;
			newBlue = blue;
		}
		
		/*
		 * Step 10.
		 *  Now we combine those new color values into a single RGB value.
		 *  
		 */
		int newRGB = createRGBFromColors(newRed, newGreen, newBlue);
		
		/*
		 * ==========================================================
		 * == STEP (12) after createing setRGB() (step 11) ====
		 * ==========================================================
		 */
		
		setRGB(resultImage, x, y, newRGB);
		
		/*
		 * Create the last method below to iterate through the 
		 * entire image and apply recoloring. 
		 */
		
	}
	
	/*
	 * Step 11.
	 * Assign the newly created RGB value into the the resultImage BufferedImage object.
	 * 
	 * @param new image, x & y coordinates of the pixel, new RGB value.
	 */
	public static void setRGB(BufferedImage image, int x, int y, int rgb) {
		/*
		 * First we grab the Raster. Raster graphics are bitmaps. 
		 * A bitmap is a grid of individual pixels that collectively compose an image. 
		 */
		image.getRaster().setDataElements(x, y, image.getColorModel().getDataElements(rgb, null));
		
		/*==============================================================
		 * ======= Then go back to above method and call setRGB();======
		 * =============================================================
		 */
	}
	
	
	/*
	 * FINAL STEP 13.
	 * Wrapper method for SingleThreaded solution calls the recolorimage starting from 
	 * the left top corner.
	 */
    public static void recolorSingleThreaded(BufferedImage originalImage, BufferedImage resultImage) {
        recolorImage(originalImage, resultImage, 0, 0, originalImage.getWidth(), originalImage.getHeight());
    }
	
	/*
	 * Step 12. 
	 * Iterates through all pixels on image and recolrs each one
	 */
	public static void recolorImage(BufferedImage originalImage, BufferedImage resultImage, int leftCorner, int topCorner, int width, int height) {
		
		// Loop through all the x values between left corner and end of the row
		for(int x = leftCorner; x < leftCorner + width && x < originalImage.getWidth(); x ++) {

			// for every x value, iterate over all the y values from top corner, to the bottom.
			for(int y = topCorner ; y < topCorner + height && y < originalImage.getHeight() ; y++) {
                recolorPixelSingleThreaded(originalImage, resultImage, x , y);
            }
		}
	}
	

	/*
	 * Step 5. This method is the isShadeofGrey method which takes in color values
	 * and determines wehther the pixels are a particular shade of grey.
	 */
	public static boolean isShadeOfGrey(int red, int green, int blue) {
		// logic is checking if all color components have similar color intensity.
		// if the color is a perfect mix of green, red, and blue, then we know it a
		// shade of grey.

		return Math.abs(red - green) < 30 && Math.abs(red - blue) < 30 && Math.abs(green - blue) < 30;
	}

	/*
	 * Step 4. This method changes the rgb value within the image by taking the
	 * indiviudal values of Red, Gren, Blue and creating a compound value to apply
	 * to the pixel.
	 */
	public static int createRGBFromColors(int red, int green, int blue) {
		int rgb = 0;

		rgb |= blue;
		rgb |= green << 8;
		rgb |= red << 16;

		rgb |= 0xFF000000; // sets it to opaque.
						   // the constant has FF which is 255 in hexadecimal
						   // in the leftmost byte a zeroes in the rest of the bytes.

		return rgb;
	}

	/*
	 * The below methods grab the RGB value of the pixels within the image.
	 */

	// Step 3.
	public static int getRed(int rgb) {
		return (rgb & 0x00FF0000) >> 16; // shift value 2 bytes (16 bits) to the right.
	}

	// Step 2.
	public static int getGreen(int rgb) {
		return (rgb & 0x0000FF00) >> 8; // since the green component is the 2nd byte from the right,
										// we need to shift the value 1 byte (or 8 bits) to the right.
	}

	/*
	 * Step 1. getBlue method takes an RGB value and extracts only the blue value
	 * out of that pixel -- it does this by applying a bit mask on the pixel, making
	 * all components 0 except for the right most byte (FF) which is the blue
	 * component.
	 */
	public static int getBlue(int rgb) {
		return (rgb & 0x000000FF);
	}

}
