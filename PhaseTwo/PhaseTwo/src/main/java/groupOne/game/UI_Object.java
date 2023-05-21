package groupOne.game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;

/**
 * Abstract class for objects in the UI. Sets up for:
 * Rewards, Doors, Damage Tiles, Bonus Reward,
 * Contains various fields for implementing
 * collision, animation, interaction, and
 * position.
 * 
 * 
 * 
*/
public abstract class UI_Object {
    public BufferedImage image;
    protected String name;
    public boolean collision = false;
    private int x, y;
    public Rectangle solidArea = new Rectangle(0,0,48,48); // solid area is the collision detection area, can change it up if you want
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;
    Helper helper = new Helper();
    GamePanel gp;

    /**
     * Constructor takes in GamePanel, used 
     * to specify position on the screen
     * 
    */
    public UI_Object(GamePanel gp){
        this.gp = gp;
    }

    /**
     * Draw method so that any object 
     * can be drawn in the GamePanel
     * 
    */
    // public void draw(Graphics2D g2){

    //     g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
           
    // }
}
