package groupOne.game;

import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Sets up menu arrow for the UI, acts as a cursor so that 
 * the player knows what they are selecting in the menu.
 */
public class UI_MenuArrow extends UI_Object{
    public UI_MenuArrow(GamePanel gp){
        super(gp);
        name = "Selection Arrow";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/UI/selectionArrow.png"));
            image = helper.scaleImage(image, 32, 52);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}