package wheel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.*;
import java.util.Scanner; 


public class Main {
    public static void main(String[] args) {
        int rad = 1024;
        Scanner in = new Scanner(System.in);
        int x = in.nextInt(); 
        int y = in.nextInt(); 
        int r = 0;
        int g = 0;
        int bl = 0;
        BufferedImage img = new BufferedImage(rad, rad, BufferedImage.TYPE_INT_RGB);

        // Center Point (MIDDLE, MIDDLE)
        int centerX = img.getWidth() / 2;
        int centerY = img.getHeight() / 2;
        int radius = (img.getWidth() / 2) * (img.getWidth() / 2);

        // Red Source is (RIGHT, MIDDLE)
        int redX = img.getWidth()/8*4;
        int redY = img.getHeight() / 8;
        int redRad = img.getWidth() * img.getWidth();

        // Green Source is (LEFT, MIDDLE)
        int greenX = img.getWidth()/8*7;
        int greenY = img.getHeight() / 8*6;
        int greenRad = img.getWidth() * img.getWidth();

        // Blue Source is (MIDDLE, BOTTOM)
        int blueX = img.getWidth() / 8*2;
        int blueY = img.getHeight()/8*7;
        int blueRad = img.getWidth() * img.getWidth();

        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
            	
                int a = i - centerX;
                int b = j - centerY;

                //System.out.println(a);
                int distance = a * a + b * b;
                if (distance < radius) {
//                    System.out.println("Loop in");
                    int rdx = i - redX;
                    int rdy = j - redY;
                    int redDist = (rdx * rdx + rdy * rdy);
                    int redVal = (int) (255 - ((redDist / (float) redRad) * 256));

                    int gdx = i - greenX;
                    int gdy = j - greenY;
                    int greenDist = (gdx * gdx + gdy * gdy);
                    int greenVal = (int) (255 - ((greenDist / (float) greenRad) * 256));

                    int bdx = i - blueX;
                    int bdy = j - blueY;
                    int blueDist = (bdx * bdx + bdy * bdy);
                    int blueVal = (int) (255 - ((blueDist / (float) blueRad) * 256));

//                    System.out.println(redVal);
                    if (a==x && b ==y) {
                    	r = redVal;
                    	g = greenVal;
                    	bl = blueVal;
                    }
                    Color c = new Color(redVal%256, greenVal, blueVal);

                    float hsbVals[] = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), null);

                    Color highlight = Color.getHSBColor(hsbVals[0], hsbVals[1], 1);

                    img.setRGB(i, j, RGBtoHEX(highlight));
                } else {
                    img.setRGB(i, j, 0x000000);
                }
            }
        }

        try {
            ImageIO.write(img, "png", new File("wheel.png"));
            System.out.println(r);
            System.out.println(g);
            System.out.println(bl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int RGBtoHEX(Color color) {
        String hex = Integer.toHexString(color.getRGB() & 0xffffff);
        if (hex.length() < 6) {
            if (hex.length() == 5)
                hex = "0" + hex;
            if (hex.length() == 4)
                hex = "00" + hex;
            if (hex.length() == 3)
                hex = "000" + hex;
        }
        hex = "#" + hex;
        return Integer.decode(hex);
    }
}
