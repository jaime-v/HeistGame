package groupOne.game;

import java.awt.event.KeyEvent;

import org.junit.Test;
import groupOne.game.AssetSetter.ObjectType;
import groupOne.game.GamePanel.GameState;

public class RewardTest {
    GamePanel gp = new GamePanel();
    KeyHandler keyH = new KeyHandler(gp);
    TileManager tileM = new TileManager(gp);
    Player player = new Player(gp, keyH);
    AssetSetter aSetter = new AssetSetter(gp);
    UI_Information info = new UI_Information(gp);
    
    @Test
    public void allBaseRewards(){
        player.x = 1*gp.tileSize; // set player position to (1,2) to avoid conflict with gate in test map

        // set gates for each map
        aSetter.setObject(0, 2, ObjectType.OBJ_Gate, 0, 20);
        aSetter.setObject(0, 2, ObjectType.OBJ_Gate, 1, 20);
        aSetter.setObject(0, 2, ObjectType.OBJ_Gate, 2, 20);
        aSetter.setObject(0, 2, ObjectType.OBJ_Gate, 3, 20);

        // set rewards in a straight line
        aSetter.setObject(2, 2, ObjectType.OBJ_Reward, 0, 0);
        aSetter.setObject(3, 2, ObjectType.OBJ_Reward, 0, 1);
        aSetter.setObject(4, 2, ObjectType.OBJ_Reward, 0, 2);
        aSetter.setObject(5, 2, ObjectType.OBJ_Reward, 0, 3);
        aSetter.setObject(6, 2, ObjectType.OBJ_Reward, 0, 4);
        aSetter.setObject(7, 2, ObjectType.OBJ_Reward, 0, 5);
        aSetter.setObject(8, 2, ObjectType.OBJ_Reward, 0, 6);
        aSetter.setObject(9, 2, ObjectType.OBJ_Reward, 0, 7);
        aSetter.setObject(10, 2, ObjectType.OBJ_Reward, 0, 8);
        aSetter.setObject(11, 2, ObjectType.OBJ_Reward, 0, 9);


        gp.gameState = GameState.PLAY_STATE;
        long current_time = System.currentTimeMillis() / 1000L;

        // Walk to the right edge of map, collect all rewards on the way
        KeyEvent simulKeyEvent = new KeyEvent(gp, KeyEvent.KEY_PRESSED, current_time, 0, KeyEvent.VK_D, 'd'); 
        keyH.keyPressed(simulKeyEvent);
        for (int i=0; i<250; i++){
            player.update();
        }
        // 50 score change * 10 rewards = 500
        assert(player.score == 500);

        // Walk down to bottom-right corner of map
        simulKeyEvent = new KeyEvent(gp, KeyEvent.KEY_PRESSED, current_time, 0, KeyEvent.VK_S, 's'); 
        keyH.keyPressed(simulKeyEvent);

        for (int i=0; i<150; i++){
            player.update();
        }

        // Walk through exit and trigger win screen
        simulKeyEvent = new KeyEvent(gp, KeyEvent.KEY_PRESSED, current_time, 0, KeyEvent.VK_D, 'd'); 
        keyH.keyPressed(simulKeyEvent);
        for (int i=0;i<50;i++){
            player.update();
        }

        assert(gp.gameState == GameState.WIN_STATE);
        
    }

    @Test
    public void allRewards(){
        player.x = 1*gp.tileSize;
        // set up gates for each map
        aSetter.setObject(0, 2, ObjectType.OBJ_Gate, 0, 20);
        aSetter.setObject(0, 2, ObjectType.OBJ_Gate, 1, 20);
        aSetter.setObject(0, 2, ObjectType.OBJ_Gate, 2, 20);
        aSetter.setObject(0, 2, ObjectType.OBJ_Gate, 3, 20);

        //set up rewards similarly to other test case, but with a bonus at the end
        aSetter.setObject(2, 2, ObjectType.OBJ_Reward, 0, 0);
        aSetter.setObject(3, 2, ObjectType.OBJ_Reward, 0, 1);
        aSetter.setObject(4, 2, ObjectType.OBJ_Reward, 0, 2);
        aSetter.setObject(5, 2, ObjectType.OBJ_Reward, 0, 3);
        aSetter.setObject(6, 2, ObjectType.OBJ_Reward, 0, 4);
        aSetter.setObject(7, 2, ObjectType.OBJ_Reward, 0, 5);
        aSetter.setObject(8, 2, ObjectType.OBJ_Reward, 0, 6);
        aSetter.setObject(9, 2, ObjectType.OBJ_Reward, 0, 7);
        aSetter.setObject(10, 2, ObjectType.OBJ_Reward, 0, 8);
        aSetter.setObject(11, 2, ObjectType.OBJ_Reward, 0, 9);
        aSetter.setObject(12, 2, ObjectType.OBJ_Bonus, 0, 16);


        gp.gameState = GameState.PLAY_STATE;
        long current_time = System.currentTimeMillis() / 1000L;

        // Walk to the right edge of map, collect all rewards on the way
        KeyEvent simulKeyEvent = new KeyEvent(gp, KeyEvent.KEY_PRESSED, current_time, 0, KeyEvent.VK_D, 'd'); 
        keyH.keyPressed(simulKeyEvent);
        for (int i=0; i<250; i++){
            player.update();
        }
        // Player score should be 500*2 = 1000, but anything more than 500 meets requirements
        assert(player.score > 500);

        // Walk down to bottom-right corner of map
        simulKeyEvent = new KeyEvent(gp, KeyEvent.KEY_PRESSED, current_time, 0, KeyEvent.VK_S, 's'); 
        keyH.keyPressed(simulKeyEvent);

        for (int i=0; i<150; i++){
            player.update();
        }

        // Walk through exit and trigger win screen
        simulKeyEvent = new KeyEvent(gp, KeyEvent.KEY_PRESSED, current_time, 0, KeyEvent.VK_D, 'd'); 
        keyH.keyPressed(simulKeyEvent);
        for (int i=0;i<50;i++){
            player.update();
        }

        assert(gp.gameState == GameState.WIN_STATE);
        
    }

    @Test
    public void bonusDissapears(){
        gp.gameState = GameState.PLAY_STATE;
        gp.aSetter.setSpawnTime();
        long current_time = System.currentTimeMillis() / 1000L;
        aSetter.setObject(4, 2, ObjectType.OBJ_Bonus, 0, 16);
        KeyEvent simulKeyEvent = new KeyEvent(gp, KeyEvent.KEY_PRESSED, current_time, 0, KeyEvent.VK_D, 'd'); 
        keyH.keyPressed(simulKeyEvent);
        // Walk down into a wall until despawnTime is reached
        while(gp.info.playTime <= gp.aSetter.despawnTime){
            gp.info.playTime += (double)player.speed*1/60;
            player.update();
        }
        // bonus should be null after
        assert(gp.obj[0][16] == null);
    }
}
