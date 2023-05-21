package groupOne.game;

import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Sets up paused sign image for the pause menu.
 */
public class UI_PausedSign extends UI_Object{
    public UI_PausedSign(GamePanel gp){
        super(gp);
        name = "Paused";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/UI/PAUSED.png"));
            image = helper.scaleImage(image, 564, 156);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}