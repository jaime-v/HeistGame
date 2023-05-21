package groupOne.game;

import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * Sets up reward image for the UI, used to display the player's score in picture form.
 */
public class UI_Reward extends UI_Object{

    public UI_Reward(GamePanel gp){
        super(gp);
        name = "UI-Reward";
        try {
            Random random = new Random();
            int r = random.nextInt(100)+1;
            if(r > 50){
                image = ImageIO.read(getClass().getResourceAsStream("/reward01.png"));
            }
            else{
                image = ImageIO.read(getClass().getResourceAsStream("/reward02.png"));
            }
            helper.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}