package groupOne.game;

import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Sets up background image of the title screen.
 */
public class UI_TitleBackground extends UI_Object{
    public UI_TitleBackground(GamePanel gp){
        super(gp);
        name = "title-bg";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/UI/Title_Background.png"));
            image = helper.scaleImage(image, gp.screenWidth, gp.screenHeight);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}