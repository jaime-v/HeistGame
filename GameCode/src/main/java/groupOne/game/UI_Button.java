package groupOne.game;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

/**
 * Sets up all buttons featured in the UI, such as difficulty buttons
 *  back, and play.
 */
public class UI_Button extends UI_Object{
    public BufferedImage easy, medium, hard, back, quit, play, dwtd, arrow;
    public UI_Button(GamePanel gp){
        super(gp);
        name = "Button";
        getButton();
        
    }
    
    public BufferedImage setup(String imagePath){
        Helper helper = new Helper();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/" + imagePath + ".png"));
            image = helper.scaleImage(image, 270, 78);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }
    
    private void getButton(){
        easy = setup("UI/button/button_EASY");
        medium = setup("UI/button/button_MEDIUM");
        hard = setup("UI/button/button_HARD");
        dwtd = setup("UI/button/button_DWTD");
        back = setup("UI/button/button_BACK");
        quit = setup("UI/button/button_QUIT");
        play = setup("UI/button/button_PLAY");
    }
}