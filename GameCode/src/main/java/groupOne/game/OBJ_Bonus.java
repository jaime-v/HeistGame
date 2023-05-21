package groupOne.game;

import groupOne.game.interfaces.Destroyable;

/**
 * Sets up bonus reward object image and name. Implements destroyable.
 * Also creates an ID and adds the bonus reward to the game panel via constructor.
 */
public class OBJ_Bonus extends S_Entity implements Destroyable {
    // Bonus Object
    public OBJ_Bonus(GamePanel gp, int id){
        super(gp);
        name = EntityType.Bonus;
        down1 = setup("bonus");
        this.id = id;
    }
    @Override
    public void setAction() {
        //does nothing bonuses have no AI
    }
}
