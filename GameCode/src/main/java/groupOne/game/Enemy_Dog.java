package groupOne.game;

import java.util.Random;

/**
 * Enemy class for guard dogs, contains information for animations, collision, and pathing
 */
public class Enemy_Dog extends Entity{
    
    public Enemy_Dog(GamePanel gp){
        super(gp);

        direction = Directions.DOWN;

        speed = 2;

        /*
        if (gp.difficuly == Difficulty.GIGA_SWEAT_NERD) {
            speed *=3;
        }
        */


        solidArea.x = 3;
        solidArea.y = 0;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 40;
        solidArea.height = 46;

        getEnemyImage();
    }
    
    public void getEnemyImage(){
        // Dog moves up in the game window
        up1 = setup("Dog/dog_up_0");
        up2 = setup("Dog/dog_up_1");
        // Dog moves down in the game window
        down1 = setup("Dog/dog_down_0");
        down2 = setup("Dog/dog_down_1");
        // Dog moves left in the game window
        left1 = setup("Dog/dog_left_0");
        left2 = setup("Dog/dog_left_1");
        // Dog moves right in the game window
        right1 = setup("Dog/dog_right_0");
        right2 = setup("Dog/dog_right_1");
    }

    @Override
    public void setAction(){

        // If tracking player, searchPath for player position
        if(onPath == true){
            int goalCol = (gp.player.x + gp.player.solidArea.x)/gp.tileSize;
            int goalRow = (gp.player.y + gp.player.solidArea.y)/gp.tileSize;
            searchPath(goalCol, goalRow);
        }

        // Otherwise move in a random direction for 2 seconds (120 frames)
        else{
            actionLockCounter++;
            if(actionLockCounter == 120){
                Random random = new Random();
                int i = random.nextInt(100)+1;
                if(i <= 25){
                    direction = Directions.UP;
                }
                if(i > 25 && i <= 50){
                    direction = Directions.DOWN;
                }
                if(i > 50 && i <= 75){
                    direction = Directions.LEFT;
                }
                if(i > 75 && i <= 100){
                    direction = Directions.RIGHT;
                }
                actionLockCounter = 0;  

            }
        }
    }

}
