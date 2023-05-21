package groupOne.game;
import org.junit.Test;

import groupOne.game.AssetSetter.ObjectType;
import groupOne.game.GamePanel.GameState;
import java.awt.event.KeyEvent;

public class EnemyTest {
    GamePanel gp = new GamePanel();
    KeyHandler keyH = new KeyHandler(gp);
    AssetSetter asetter = new AssetSetter(gp);

    Player player = new Player(gp, keyH);
    Enemy_Guard guard = new Enemy_Guard(gp);
    Enemy_Dog dog = new Enemy_Dog(gp);
    OBJ_Damage trap1 = new OBJ_Damage(gp,13);
    OBJ_Damage trap2 = new OBJ_Damage(gp,14);

    /* Game should have at least 2 types of enemies, with
       multiple instances of each type */ 
    @Test
    public void enemyNotNULL(){
        assert(guard != null);
        assert(dog != null);
        assert(trap1 != null);
        assert(trap2 != null);
    }

    /* Penalties are placed on cells on the board */ 
    @Test
    public void trapIsPlaced(){
        gp.obj = new S_Entity[gp.maxMap][40];
        asetter.setObject(10, 11, ObjectType.OBJ_Damage, 0, 13);
        // x and y values stored in Integer object type for comparison
        Integer trapX = gp.obj[0][13].x;
        Integer trapY = gp.obj[0][13].y;

        assert(trapX != null);
        assert(trapY != null);
    }

    /* Enemy moves towards a direction that makes them closest to
       the current position of the player */ 
    @Test
    public void movingToPlayer(){
        gp.enemy[0][2] = dog;
        asetter.placeEnemy(2, 0, 21, 4);
        gp.gameState = GameState.PLAY_STATE;

        // Compare initial absolute distance, compare again after some time 
        Integer initialDistanceX = Math.abs(player.x - dog.x);
        Integer initialDistanceY = Math.abs(player.y - dog.y);

        long current_time = System.currentTimeMillis() / 1000L;
        KeyEvent simulKeyEvent = new KeyEvent(gp, KeyEvent.KEY_PRESSED, current_time, 0, KeyEvent.VK_D, 'd'); 
        keyH.keyPressed(simulKeyEvent);

        for (int i = 0; i<12; i++) {
            player.update();
        }

        Integer finalDistanceX = Math.abs(player.x - dog.x);
        Integer finalDistanceY = Math.abs(player.y - dog.y);
        
        // Distance between enemy and player should be shorter after some time
        assert(finalDistanceX < initialDistanceX || finalDistanceY < initialDistanceY);
    }

    @Test
    public void enemyNoPickupTest(){
        gp.enemy[0][0] = guard;
        asetter.placeEnemy(0, 0, 4, 2);
        asetter.setObject(4, 2, ObjectType.OBJ_Reward, 0, 0);
        gp.gameState = GameState.PLAY_STATE;

        long current_time = System.currentTimeMillis() / 1000L;
        KeyEvent simulKeyEvent = new KeyEvent(gp, KeyEvent.KEY_PRESSED, current_time, 0, KeyEvent.VK_D, 'd'); 
        keyH.keyPressed(simulKeyEvent);

        for (int i = 0; i<4; i++) {
            player.update();
        }
        assert(gp.obj[0][0] != null);
    }
}
