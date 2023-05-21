package groupOne.game;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import groupOne.game.GamePanel.GameState;

/**
 * Abstract Class for the entities that
 * will be created: Player and Enemy
 * Contains various fields for position, speed, 
 * animations, direction, and collision detection
 * <p>
 * Necessary to create different kinds of entities
 * without rewriting code several times over.
*/
public abstract class Entity {

    GamePanel gp; // GamePanel that entity will be in

    // ENTITY POSITION AND MOVEMENT
    public Integer x, y; // Coordinates in pixels
    public Integer speed; // Pixels moved per frame

    
    public Directions direction = Directions.DOWN;

    /**
     * Enum for directions that the entity is facing, used for determining used sprites
     */
    public enum Directions {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    // SPRITE ANIMATION
    public int spriteCounter = 0;
    public int spriteNumber = 1;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;


    // ENTITY COLLISION
    public Rectangle solidArea = new Rectangle(0,0,48,48);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;

    // ENTITY PATHING
    public int actionLockCounter = 0;
    public boolean onPath = true;

    // CHECK COLLISION
    public boolean enemyContact;

    // Constructor to add entity to GamePanel
    public Entity(GamePanel gp){
        this.gp = gp;
    }

    /**
     * Returns a scaled image of the given input image. 
     * Utilizing the helper class to scale up the 
     * sprite before rendering.
     * <p>
     * 
     * @param imagePath the name of the image
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

    // Action that each entity wil take
    public abstract void setAction();


    public void update(){
        setAction();
        checkCollision();

        if(enemyContact == true){
            gp.playSE(SoundNames.DAMAGED.get_id());
            gp.gameState = GameState.LOSE_STATE;
        }

        if(collisionOn == false){
            switch(direction){
                case UP:
                    this.y -= speed;
                    break;
                case DOWN:
                    this.y += speed;
                    break;
                case LEFT:
                    this.x -= speed;
                    break;
                case RIGHT:
                    this.x += speed;
                    break;
            }
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

        // Condensed version but not obvious
        //spriteNumber = (spriteCounter%16 == 0) ? (-spriteNumber+3) : spriteNumber;

    }

    
    /**
     * void method that draws the player sprite
     * with updates information. This method 
     * will be called in the GamePanel, so that
     * the game components are rendered in the
     * proper order.
    */
    public void draw(Graphics2D g2){

        BufferedImage image = null;

        switch(direction){
         case UP:
             if(spriteNumber == 1){
                 image = up1;
             }
             if(spriteNumber == 2){
                 image = up2;
             }
             break;
         case DOWN:
             if(spriteNumber == 1){
                 image = down1;
             }
             if(spriteNumber == 2){
                 image = down2;
             }
             break;
         case LEFT:
             if(spriteNumber == 1){
                 image = left1;
             }
             if(spriteNumber == 2){
                 image = left2;
             }
             break;
         case RIGHT:
             if(spriteNumber == 1){
                 image = right1;
             }
             if(spriteNumber == 2){
                 image = right2;
             }
             break;
        }

        g2.drawImage(image, this.x, this.y, null);
           

    }

    public void searchPath(int goalCol, int goalRow){

        int startCol = (this.x + solidArea.x)/gp.tileSize;
        int startRow = (this.y + solidArea.y)/gp.tileSize;

        gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow);
        if (gp.pFinder.search() == true){
            // Next X and Y
            int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
            int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;

            // Entity's solidArea Positions
            int enLeftX = this.x + solidArea.x;
            int enRightX = this.x + solidArea.x + solidArea.width;
            int enTopY = this.y + solidArea.y;
            int enBottomY = this.y + solidArea.y + solidArea.height;

            // Compare positions and decide directions
            if(enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize){
                direction = Directions.UP;
            }
            else if(enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize){
                direction = Directions.DOWN;
            }
            else if(enTopY >= nextY && enBottomY < nextY + gp.tileSize){
                // Left or Right
                if(enLeftX > nextX){
                    direction = Directions.LEFT;
                }
                if(enLeftX < nextX){
                    direction = Directions.RIGHT;
                }
            }
            else if(enTopY > nextY && enLeftX > nextX){
                // Up or Left
                direction = Directions.UP;
                checkCollision();
                if(collisionOn == true){
                    direction = Directions.LEFT;
                }
            }
            else if(enTopY > nextY && enLeftX < nextX){
                // Up or Right
                direction = Directions.UP;
                checkCollision();
                if(collisionOn == true){
                    direction = Directions.RIGHT;
                }
            }
            else if(enTopY < nextY && enLeftX > nextX){
                // Down or Left
                direction = Directions.DOWN;
                checkCollision();
                if(collisionOn == true){
                    direction = Directions.LEFT;
                }
            }
            else if(enTopY < nextY && enLeftX < nextX){
                // Down or Right
                direction = Directions.DOWN;
                checkCollision();
                if(collisionOn == true){
                    direction = Directions.RIGHT;
                }
            }
        }
    }
    
    private void checkCollision(){
        collisionOn = false;
        gp.checker.checkTile(this);
        gp.checker.checkEntity(this, gp.enemy);
        gp.checker.checkObject(this, false);
        enemyContact = gp.checker.checkPlayer(this);


    }
}
