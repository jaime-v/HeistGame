package groupOne.game;

import java.awt.event.KeyEvent;

import org.junit.Test;

import groupOne.game.Entity.Directions;
import groupOne.game.GamePanel.GameState;

public class MovementTest {
    
    GamePanel gp = new GamePanel();
    KeyHandler keyH = new KeyHandler(gp);
    TileManager tileM = new TileManager(gp);
    Player player = new Player(gp, keyH);

    
    @Test
    public void movePlayerDown(){
        gp.gameState = GameState.PLAY_STATE;

        //Simulate a press 
        long current_time = System.currentTimeMillis() / 1000L;
        KeyEvent simulKeyEvent = new KeyEvent(gp, KeyEvent.KEY_PRESSED, current_time, 0, KeyEvent.VK_S, 's'); 
        keyH.keyPressed(simulKeyEvent);
        player.update();

        assert(player.direction == Directions.DOWN);            // The player should be facing down now
        assert(player.x == 0);                                  // The player's x should not have moved
        assert(player.y == gp.tileSize * 2 + player.speed);     // The player's y should have moved by its speed
    }

    @Test
    public void movePlayerUp(){
        gp.gameState = GameState.PLAY_STATE;

        //Simulate a press 
        long current_time = System.currentTimeMillis() / 1000L;
        KeyEvent simulKeyEvent = new KeyEvent(gp, KeyEvent.KEY_PRESSED, current_time, 0, KeyEvent.VK_W, 'w'); 
        keyH.keyPressed(simulKeyEvent);
        player.update();

        assert(player.direction == Directions.UP);              // The player should be facing up now
        assert(player.x == 0);                                  // The player's x should not have moved
        assert(player.y == gp.tileSize * 2 - player.speed);     // The player's y should have moved by its speed
    }

    @Test
    public void movePlayerRight(){
        gp.gameState = GameState.PLAY_STATE;

        //Simulate a press 
        long current_time = System.currentTimeMillis() / 1000L;
        KeyEvent simulKeyEvent = new KeyEvent(gp, KeyEvent.KEY_PRESSED, current_time, 0, KeyEvent.VK_D, 'd'); 
        keyH.keyPressed(simulKeyEvent);
        player.update();

        assert(player.direction == Directions.RIGHT);       // The player should be facing right now
        assert(player.x == player.speed);                   // The player's should have moved by its speed
        assert(player.y == gp.tileSize * 2);                // The player's y should not have moved
    }

    @Test
    public void movePlayerLeft(){
        gp.gameState = GameState.PLAY_STATE;

        //Simulate a press 
        long current_time = System.currentTimeMillis() / 1000L;
        KeyEvent simulKeyEvent = new KeyEvent(gp, KeyEvent.KEY_PRESSED, current_time, 0, KeyEvent.VK_A, 'a'); 
        keyH.keyPressed(simulKeyEvent);
        player.update();

        assert(player.direction == Directions.LEFT);       // The player should be facing left now
        assert(player.x == -player.speed);                 // The player's should have moved by its speed
        assert(player.y == gp.tileSize * 2);               // The player's y should not have moved
    }

}
