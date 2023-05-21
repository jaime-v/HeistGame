package groupOne.game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Helper Class that will be used to 
 * assist with rendering graphics
 * and scaling images.
 * 
 * 
*/
public class Helper {
    /**
     * Returns an image of preferred tile size 
     * that can then be painted on the screen 
     * <p>
     * this improves rendering time, as the image
     * will already be scaled before the screen is
     * updated again
     * 
     * @param original  the original image that will be scaled
     * @param width     the width that the image will be scaled to
     * @param height    the height that the image will be scaled to
     * 
    */
    public BufferedImage scaleImage(BufferedImage original, int width, int height){

        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(original, 0, 0, width, height, null);
        g2.dispose();

        return scaledImage;
    }
}
