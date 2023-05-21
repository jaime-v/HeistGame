package groupOne.game;


import java.awt.event.KeyEvent;

import org.junit.Test;

import groupOne.game.AssetSetter.ObjectType;
import groupOne.game.Entity.Directions;
import groupOne.game.GamePanel.GameState;

public class PlayerTest {
    GamePanel gp = new GamePanel();
    KeyHandler keyH = new KeyHandler(gp);
    Player player = new Player(gp, keyH);
    AssetSetter asetter = new AssetSetter(gp);
    

    @Test
    public void starting_position_test() {
        //After creating the main character it should be in the spawning spot
        assert(player.x == 0);
        assert(player.y == gp.tileSize * 2);
    }

    @Test
    public void wrong_key_test() {
        //Simulate a keypress
        long current_time = System.currentTimeMillis() / 1000L;
        KeyEvent simulKeyEvent = new KeyEvent(gp, KeyEvent.KEY_PRESSED, current_time, 0, KeyEvent.VK_K, 'k');
        keyH.keyPressed(simulKeyEvent);

        player.update();

        //THIS HOLDS THE ASSUMPTION THAT THE STARTING LOCATION IS NOT BLOCKED
        assert(player.direction == Directions.DOWN); //The player should still be facing the default direction (DOWN)
        assert(player.x == 0);                 //The player should not have moved
        assert(player.y == gp.tileSize * 2);   //The player should not have moved

    }

    @Test
    public void keyDirectionTest() {
        gp.gameState = GameState.PLAY_STATE;

        //Simulate a press 
        long current_time = System.currentTimeMillis() / 1000L;
        KeyEvent simulKeyEvent = new KeyEvent(gp, KeyEvent.KEY_PRESSED, current_time, 0, KeyEvent.VK_D, 'd'); 
        keyH.keyPressed(simulKeyEvent);
        player.update();
        assert(player.direction == Directions.RIGHT); //The player should be facing up now


        assert(player.x == player.speed);                 //The player should have moved by its speed
        assert(player.y == gp.tileSize * 2);   //The player's y should not have moved
    }

    @Test
    public void GetFirstRewardTest() {
        gp.obj = new S_Entity[gp.maxMap][40];

        asetter.setObject(1, 2, ObjectType.OBJ_Reward, 0, 0);

        
        // ALL MAPS ARE SET UP WHILE PLAYER IS NOT IN PLAY STATE
        // SO SET UP GATES BEFORE ENTERING PLAY STATE MAKES TEST WORK
        asetter.setObject(99, 99, ObjectType.OBJ_Gate, 0, 20);
        asetter.setObject(99, 99, ObjectType.OBJ_Gate, 1, 20);
        asetter.setObject(99, 99, ObjectType.OBJ_Gate, 2, 20);
        asetter.setObject(99, 99, ObjectType.OBJ_Gate, 3, 20);
        //player.pickUpObject(0);

        gp.gameState = GameState.PLAY_STATE;
        long current_time = System.currentTimeMillis() / 1000L;
        KeyEvent simulKeyEvent = new KeyEvent(gp, KeyEvent.KEY_PRESSED, current_time, 0, KeyEvent.VK_D, 'd'); 
        keyH.keyPressed(simulKeyEvent);
        
        // Simulate walking into the reward
        for (int i = 0; i<12; i++) {
            player.update();
        }


        assert(player.score == 50);
        // closeEntrance() is called
        assert(gp.obj[0][20] != null);

        //collectiing the first reward also locks the starting gate
        //TODO: make a assert for that

    }

    @Test
    public void run_into_wall_test() {
        gp.gameState = GameState.PLAY_STATE;
        long current_time = System.currentTimeMillis() / 1000L;
        KeyEvent simulKeyEvent = new KeyEvent(gp, KeyEvent.KEY_PRESSED, current_time, 0, KeyEvent.VK_W, 'w'); 
        keyH.keyPressed(simulKeyEvent);
        while(player.collisionOn == false){
            player.update(); // walk up until wall is reached
        }
        int yAtWall = player.y;
        
        // Simulate walking up into wall
        for (int i = 0; i<12; i++) {
            player.update();
        }

        assert(player.y == yAtWall);
        
    }
    
    //Place an enemy right on top of the player see if it kills them
    @Test
    public void IntersectEnemyTest() {

        int currentMap = 0;
        gp.gameState = GameState.PLAY_STATE;


        gp.enemy[currentMap][0] = new Enemy_Guard(gp);
        asetter.placeEnemy(0, currentMap, 0, 2);
        gp.enemy[currentMap][0].update();


        player.update();
        


        assert(gp.gameState == GameState.LOSE_STATE);

    }
    
    // Not sure why this assertion isn't working, can hear the sound of taking damage but 
    // pickUpObject is not triggering LOSE_STATE 
    @Test
    public void playerNegativeScoreTest(){
  
        // SET DAMAGE OBJECT AT (4,2)
        asetter.setObject(4, 2, ObjectType.OBJ_Damage, 0, 13);

        // SIMULATE PLAYING THE GAME
        gp.gameState = GameState.PLAY_STATE;
        long current_time = System.currentTimeMillis() / 1000L;
        KeyEvent simulKeyEvent = new KeyEvent(gp, KeyEvent.KEY_PRESSED, current_time, 0, KeyEvent.VK_D, 'd'); 
        keyH.keyPressed(simulKeyEvent);

        // UPDATE FOR 200 TICKS
        for (int i = 0; i<200; i++) {
            player.update();
        }

        assert(player.score == -50);
        assert(gp.gameState == GameState.LOSE_STATE);
    }
    

}
