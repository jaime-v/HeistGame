package groupOne.game;

import org.junit.Test;


/**
 * All tests that are done on the test map
 * Check Player movement, Player collision, 
 * Tile placement, 
 */
public class MapTest {
    
    GamePanel gp = new GamePanel();
    KeyHandler keyH = new KeyHandler(gp);
    TileManager tileM = new TileManager(gp);
    Player player = new Player(gp, keyH);

    @Test
    public void testTilePlacement(){
        for(int i = 0; i < gp.maxScreenCol; i++){
            for(int j = 0; j < gp.maxScreenRow; j++){
                assert(tileM.mapTileNum[0][i][j] != 8);
            }
        }
    }

    @Test
    public void countWalls(){
        int wallCount = 0;
        for(int i = 0; i < gp.maxScreenCol; i++){
            for(int j = 0; j < gp.maxScreenRow; j++){
                if(tileM.mapTileNum[0][i][j] == 15){
                    wallCount++;
                }
            }
        }
        assert(wallCount > 0);
    }
}
