package groupOne.game;

import java.util.ArrayList;
import java.util.Random;

import groupOne.game.interfaces.Destroyable;

/**
 * Sets up reward object images and name. Implements destroyable.
 * Also creates an ID and adds the reward to the game panel via constructor.
 */
public class OBJ_Reward extends S_Entity implements Destroyable {
    public static ArrayList<OBJ_Reward> reward_list;
    
    // Reward Object
    public OBJ_Reward(GamePanel gp, int id){
        super(gp);
        name = EntityType.Reward;
        Random random = new Random();
        int r = random.nextInt(100) + 1;
        if(r > 50){
            down1 = setup("reward01");
        }else{
            down1 = setup("reward02");
        }
        this.id = id;
    }
    @Override
    public void setAction() {
        //does nothing rewards have no AI
    }

    
}
