package groupOne.game;

import org.junit.Test;

public class GamePanelTest {

    GamePanel gp = new GamePanel();
    AssetSetter asetter = new AssetSetter(gp);
    int currentMap = 0;

    public Boolean checkVariables(){
        return(gp.screenWidth >= gp.maxScreenCol && gp.screenHeight >= gp.maxScreenRow && gp.FPS>0 && currentMap >= 0);
    }
    @Test
    public void EnemyPlacementTest() {
        assert(checkVariables());
        gp.enemy = new Entity[gp.maxMap][10];
        gp.enemy[currentMap][0] = new Enemy_Guard(gp);
        asetter.placeEnemy(0, currentMap, 2, 2);
        assert(checkVariables());

        //Not finished yet
    }


        
    
}
