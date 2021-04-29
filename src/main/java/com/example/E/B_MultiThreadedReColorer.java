package com.example.E;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class B_MultiThreadedReColorer {

	public static final String SOURCE_FILE = "C:\\Users\\SophieGavrila\\Documents\\Threads\\Threads\\src\\main\\java\\com\\example\\E\\flowers.jpg";
	public static final String DESTINATION_FILE = "C:\\Users\\SophieGavrila\\Documents\\Threads\\Threads\\out\\flowers.jpg";

	public static void main(String[] args) throws IOException { 
		

		BufferedImage originalImage = ImageIO.read(new File(SOURCE_FILE));
		
		BufferedImage resultImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);
		
		/*=======================================s
		 * == To run the program & record time ==
		 * ======================================
		 */
		
		int numOfThreads = 3;
		long startTime = System.currentTimeMillis();
		recolorPixelMultiThreaded(originalImage, resultImage, numOfThreads);
		long endTime = System.currentTimeMillis();
		
		
		long duration = endTime - startTime;
		
		File outputFile = new File(DESTINATION_FILE);
		ImageIO.write(resultImage, "jpg", outputFile);
		
		System.out.println("It took " + duration + " milliseconds to complete the operation using recolorSingleThread().");
	}
	
	/*
	 * ========================================
	 * === Taking a multi-threaded approach ===
	 * ========================================
	 * Previously, we recolored the entire pic
	 * in one thread.  Now we will delegate 
	 * several different threads in charge of
	 * recoloring only their chunk of the file.
	 * ========================================
	 */
	public static void recolorPixelMultiThreaded(BufferedImage originalImage, BufferedImage resultImage, int numberOfThreads) {
		
		List<Thread> threads = new ArrayList<>();
        int width = originalImage.getWidth();
        int height = originalImage.getHeight() / numberOfThreads; // this is how we delegate Latency 
		
        for(int i = 0; i < numberOfThreads ; i++) {
            final int threadMultiplier = i; // This will be passed into the Runnable to tell it
            								// which portion of the image it needs to process 

            /*
             *  In each iteration of the loop, we create a new thread  
             *  with a new runnable task to be executed by that particular
             *  thread.
             */
           
            Thread thread = new Thread(() -> {
                int xOrigin = 0 ;
                int yOrigin = height * threadMultiplier; // this calculates the top corner from which the recolor
                										// image method can be called.
                									
                								// left corner  // top corner
                recolorImage(originalImage, resultImage, xOrigin, yOrigin, width, height);
            });

            threads.add(thread);
        }

        for(Thread thread : threads) {
            thread.start();
        }

        for(Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
            }
        }
	}
	
	
	
	public static void recolorPixelSingleThreaded(BufferedImage originalImage, BufferedImage resultImage, int x, int y) {

		int rgb = originalImage.getRGB(x, y);
		
		int red = getRed(rgb);
		int green = getGreen(rgb);
		int blue = getBlue(rgb);
		
		int newRed;
		int newGreen;
		int newBlue;
		
		if(isShadeOfGrey(red, blue, green)) {
	        newRed = Math.min(255, red + 10);
            newGreen = Math.max(0, green - 80);
            newBlue = Math.max(0, blue - 20);     // here we're just manipulating a purple color.
			
		} else { 			// if the pixel is NOT part of a white flower....
			newRed = red;
			newGreen = green;
			newBlue = blue;
		}
		

		int newRGB = createRGBFromColors(newRed, newGreen, newBlue);
		
		setRGB(resultImage, x, y, newRGB);
	
		
	}
	
	public static void setRGB(BufferedImage image, int x, int y, int rgb) {
		
		image.getRaster().setDataElements(x, y, image.getColorModel().getDataElements(rgb, null));
		
	}
	
    public static void recolorSingleThreaded(BufferedImage originalImage, BufferedImage resultImage) {
        recolorImage(originalImage, resultImage, 0, 0, originalImage.getWidth(), originalImage.getHeight());
    }
	
	public static void recolorImage(BufferedImage originalImage, BufferedImage resultImage, int leftCorner, int topCorner, int width, int height) {
		
		for(int x = leftCorner; x < leftCorner + width && x < originalImage.getWidth(); x ++) {
			
			for(int y = topCorner ; y < topCorner + height && y < originalImage.getHeight() ; y++) {
                recolorPixelSingleThreaded(originalImage, resultImage, x , y);
            }
		}
	}
	
	public static boolean isShadeOfGrey(int red, int green, int blue) {

		return Math.abs(red - green) < 30 && Math.abs(red - blue) < 30 && Math.abs(green - blue) < 30;
	}

	public static int createRGBFromColors(int red, int green, int blue) {
		int rgb = 0;

		rgb |= blue;
		rgb |= green << 8;
		rgb |= red << 16;

		rgb |= 0xFF000000; 

		return rgb;
	}


	public static int getRed(int rgb) {
		return (rgb & 0x00FF0000) >> 16;
	}

	public static int getGreen(int rgb) {
		return (rgb & 0x0000FF00) >> 8; 
	}

	public static int getBlue(int rgb) {
		return (rgb & 0x000000FF);
	}

}
