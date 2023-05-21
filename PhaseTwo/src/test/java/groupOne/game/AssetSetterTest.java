package groupOne.game;

import java.awt.RenderingHints.Key;

import org.junit.Test;

import groupOne.game.AssetSetter.ObjectType;

public class AssetSetterTest {
    GamePanel gp = new GamePanel();
    AssetSetter object = new AssetSetter(gp);
    KeyHandler keyH = new KeyHandler(gp);
    Player player = new Player(gp, keyH);

    public Boolean variableCheck(){
        object.setSpawnTime();
        return(gp != null && object != null && keyH != null && player != null && object.despawnTime == object.spawnTime+20);
    }

    @Test
    public void testifplacement() {
        assert(variableCheck()); // precondition
        
        object.setObject(2, 2, ObjectType.OBJ_Reward, 1, 0);
        assert(gp.obj[1][0] != null); //object is set
        assert(gp.obj[1][0].name != null); // ObjectType assigned

        assert(variableCheck()); // postcondition

    }

    

    //testdds

}