package groupOne.game;

import groupOne.game.interfaces.Destroyable;

/**
 * Sets up damage object image and name. Implements destroyable.
 * Also creates an ID and adds the damage object to the game panel via constructor.
 */
public class OBJ_Damage extends S_Entity implements Destroyable {
    // Damage Object
    public OBJ_Damage(GamePanel gp, int id){
        super(gp);
        name = EntityType.Damage;
        down1 = setup("damage");
        this.id = id;
    }
    @Override
    public void setAction() {
        //does nothing hazards have no AI
    }
}
