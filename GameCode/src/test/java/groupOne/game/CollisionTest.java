package groupOne.game;
import java.awt.event.KeyEvent;

import org.junit.Test;

import groupOne.game.Entity.Directions;
import groupOne.game.GamePanel.GameState;

public class CollisionTest {
    
    GamePanel gp = new GamePanel();
    KeyHandler keyH = new KeyHandler(gp);
    TileManager tileM = new TileManager(gp);
    Player player = new Player(gp, keyH);
    CollisionChecker checker = new CollisionChecker(gp);
    

    @Test
    public void downCollisionSim(){
        // Simulate Play state
        gp.gameState = GameState.PLAY_STATE;


        // Set player inside a tile to test for collision
        player.x = 4 * gp.tileSize;
        player.y = 4 * gp.tileSize;


        // Simulate a press 
        long current_time = System.currentTimeMillis() / 1000L;
        KeyEvent simulKeyEvent = new KeyEvent(gp, KeyEvent.KEY_PRESSED, current_time, 0, KeyEvent.VK_S, 's'); 
        keyH.keyPressed(simulKeyEvent);


        // Attempt to update position
        
        for(int i = 0; i < 20; i++){
            player.update();
        }
        


        assert(player.direction == Directions.DOWN);    // The player should be facing down now
        assert(player.collisionOn == true);             // Player collision should be on
        assert(player.x == 4 * gp.tileSize);            // The player should not have moved
        assert(player.y == 4 * gp.tileSize);   

    }

    @Test
    public void upCollisionSim(){
        // Simulate Play state
        gp.gameState = GameState.PLAY_STATE;


        // Set player inside a tile to test for collision
        player.x = 4 * gp.tileSize;
        player.y = 4 * gp.tileSize;


        // Simulate a press 
        long current_time = System.currentTimeMillis() / 1000L;
        KeyEvent simulKeyEvent = new KeyEvent(gp, KeyEvent.KEY_PRESSED, current_time, 0, KeyEvent.VK_W, 'w'); 
        keyH.keyPressed(simulKeyEvent);


        // Attempt to update position

        for(int i = 0; i < 20; i++){
            player.update();
        }
       


        assert(player.direction == Directions.UP);      // The player should be facing up now
        assert(player.collisionOn == true);             // Player collision should be on
        assert(player.x == 4 * gp.tileSize);            // The player should not have moved
        assert(player.y == 4 * gp.tileSize);   

    }
    
    @Test
    public void leftCollisionSim(){
        // Simulate Play state
        gp.gameState = GameState.PLAY_STATE;


        // Set player inside a tile to test for collision
        player.x = 4 * gp.tileSize;
        player.y = 4 * gp.tileSize;


        // Simulate a press 
        long current_time = System.currentTimeMillis() / 1000L;
        KeyEvent simulKeyEvent = new KeyEvent(gp, KeyEvent.KEY_PRESSED, current_time, 0, KeyEvent.VK_A, 'a'); 
        keyH.keyPressed(simulKeyEvent);


        // Attempt to update position
       
        for(int i = 0; i < 20; i++){
            player.update();
        }
      


        assert(player.direction == Directions.LEFT);    // The player should be facing left now
        assert(player.collisionOn == true);             // Player collision should be on
        assert(player.x == 4 * gp.tileSize);            // The player should not have moved
        assert(player.y == 4 * gp.tileSize);   

    }
    
    @Test
    public void rightCollisionSim(){
        // Simulate Play state
        gp.gameState = GameState.PLAY_STATE;


        // Set player inside a tile to test for collision
        player.x = 4 * gp.tileSize;
        player.y = 4 * gp.tileSize;


        // Simulate a press 
        long current_time = System.currentTimeMillis() / 1000L;
        KeyEvent simulKeyEvent = new KeyEvent(gp, KeyEvent.KEY_PRESSED, current_time, 0, KeyEvent.VK_D, 'd'); 
        keyH.keyPressed(simulKeyEvent);


        // Attempt to update position

        for(int i = 0; i < 20; i++){
            player.update();
        }



        assert(player.direction == Directions.RIGHT);   // The player should be facing right now
        assert(player.collisionOn == true);             // Player collision should be on
        assert(player.x == 4 * gp.tileSize);            // The player should not have moved
        assert(player.y == 4 * gp.tileSize);   

    }
}
