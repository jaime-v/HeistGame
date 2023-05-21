package groupOne.game;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import groupOne.game.GamePanel.GameState;
import groupOne.game.KeyHandler.KeyLastPressedStates;

/**
 * Player class stores information about
 * player character that can be drawn on
 * GamePanel. Information about the
 * player is also updated as the user 
 * plays the game.
 * <p>
 * This class extends Entity which has many
 * fields that implement animation, object
 * interaction, collision detection, 
 * player movement direction, and position
 * 
 * @see Entity
*/
public class Player extends Entity {
    KeyHandler keyH;
    int score = 0;
    int collisionMessageDelay = 0;

    /**
     * Constructor that modifies player collision
     * detection area, as well as positioning the
     * player on the GamePanel, and handling key
     * input.
    */
    public Player(GamePanel gp, KeyHandler keyH){
        super(gp);
        this.keyH = keyH;


        solidArea = new Rectangle();

        solidArea.x = 20;
        solidArea.y = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 8;
        solidArea.height = 8;
        
        setDefaultValues();
        getPlayerImage();
    }

    /**
     * void method that resets player
     * position, speed, score and 
     * movement direction.
     * <p>
     * This will be called whenever
     * the game is meant to be reset.
    */
    public void setDefaultValues(){
        x = 0;
        y = gp.tileSize * 2;
        speed = 5;
        score = 0;
        direction = Directions.DOWN;
    }
    
    @Override
    public void setAction() {
        //does nothing
    }
    
    
    /**
     * Returns a scaled image of a given sprite.
     * Utilizes the helper class to scale the
     * image before it is rendered into the 
     * GamePanel.
     * <p>
     * This reduces rendering time
    */
    public BufferedImage setup(String imagePath){
        Helper helper = new Helper();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/" + imagePath + ".png"));
            image = helper.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }


    /**
     * void method that updates player information,
     * checks for collision and object interaction,
     * animates the character sprite, and changes
     * game state when the player exits the screen.
    */
    public void update(){
        try {
            
        if(keyH.up == true || keyH.down == true || keyH.left == true || keyH.right == true ){
            if(keyH.up == true && keyH.lastPressed == KeyLastPressedStates.UP || keyH.up == true && keyH.down == false && keyH.left == false && keyH.right == false){
                direction = Directions.UP;
            }
            else if(keyH.down == true && keyH.lastPressed == KeyLastPressedStates.DOWN || keyH.up == false && keyH.down == true && keyH.left == false && keyH.right == false){
                direction = Directions.DOWN;
            }
            else if(keyH.left == true && keyH.lastPressed == KeyLastPressedStates.LEFT || keyH.up == false && keyH.down == false && keyH.left == true && keyH.right == false){
                direction = Directions.LEFT;
            }
            else if(keyH.right == true && keyH.lastPressed == KeyLastPressedStates.RIGHT || keyH.up == false && keyH.down == false && keyH.left == false && keyH.right == true){
                direction = Directions.RIGHT;
            }
    
            collisionOn = false;
            gp.checker.checkTile(this);

            int objIndex = gp.checker.checkObject(this, true);
            pickUpObject(objIndex);

            int enemyIndex = gp.checker.checkEntity(this, gp.enemy);
            interactEnemy(enemyIndex);
    
            if(collisionOn == false){
                switch(direction){
                    case UP:
                        y -= speed;
                        break;
                    case DOWN:
                        y += speed;
                        break;
                    case LEFT:
                        x -= speed;
                        break;
                    case RIGHT:
                        x += speed;
                        break;
                }
            }
        }

        if(collisionMessageDelay < 60){
            collisionMessageDelay++;
        }
        
        spriteCounter++;
        if(spriteCounter > 16){
            if(spriteNumber == 1){
                spriteNumber = 2;
            }
            else if(spriteNumber == 2){
                spriteNumber = 1;
            }
            spriteCounter = 0;
        }
      
    
        } catch (ArrayIndexOutOfBoundsException e) {
            if(gp.info.rewardCount > 0){
                gp.gameState = GameState.LOSE_STATE;
                gp.playSE(SoundNames.DAMAGED.get_id());
            }
            else{
                gp.gameState = GameState.WIN_STATE;
                gp.playSE(SoundNames.WIN.get_id());
            }
        }
    }

    
    
    /**
     * void method that stores player images
     * depending on the direction of movement.
     * Images are stored so that they can be
     * drawn in the GamePanel.
    */
    private void getPlayerImage(){
        up1 = setup("player_up_0");
        up2 = setup("player_up_1");
        down1 = setup("player_down_0");
        down2 = setup("player_down_1");
        left1 = setup("player_left_0");
        left2 = setup("player_left_1");
        right1 = setup("player_right_0");
        right2 = setup("player_right_1");
    }

    
    /**
     * void method that checks for object interaction.
     * Various UI messages are displayed depending on
     * the object that was interacted with. Information
     * regarding the player is also updated.
    */
    private void pickUpObject(int i){
        if(i != 999){
            S_Entity object = gp.obj[gp.currentMap][i];
            switch(object.name){

                case Reward:
                gp.playSE(SoundNames.COIN.get_id());
                    score += 50;
                    object.self_destruct();
                    gp.info.rewardCount--;
                    gp.ui.addMessage("+50 EZ MONEY!");
                    gp.aSetter.closeEntrance();
                    break;

                case Door:
                    if(gp.info.rewardCount == 0){
                        gp.playSE(SoundNames.DOOROPEN.get_id());
                        object.self_destruct();
                        gp.ui.addMessage("The Door is unlocked!");
                    }
                    else{
                        gp.ui.addMessage("You need to collect more rewards!");
                    }
                    break;

                case Bonus:
                    object.self_destruct();
                    if(score <= 200){
                        score += 200;
                        gp.ui.addMessage("You grabbed the diamond! +200 !!");
                    }
                    else{
                        score = score * 2;
                        gp.ui.addMessage("You doubled your score!");
                    }
                    gp.info.hasBonus = true;
                    gp.playSE(SoundNames.POWERUP.get_id());
                    break;

                case Damage:
                    gp.playSE(SoundNames.DAMAGED.get_id());
                    object.self_destruct();
                    score -= 50;
                    if(speed > 1){
                        speed--;
                    }
                    gp.ui.addMessage("You took some damage! You lost some points!");
                    if(score < 0){
                        gp.gameState = GameState.LOSE_STATE;
                    }
                    break;
                
                case Gate:
                    if(collisionMessageDelay == 60){
                        gp.ui.addMessage("You can't go through here");
                        collisionMessageDelay = 0;
                    }
                    else{
                        collisionMessageDelay++;
                    }
                        break;

                case Laser:
                    //nothing for now
                    break;
            }
        }
    }

    private void interactEnemy(int i){
        if(i != 999){
            gp.playSE(SoundNames.DAMAGED.get_id());
            gp.gameState = GameState.LOSE_STATE;
        }
    }

}