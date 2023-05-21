package groupOne.game;

import groupOne.game.interfaces.Destroyable;

/**
 * Sets up door object image, name, and collision. Implements destroyable.
 * Also creates an ID and adds the door to the game panel via constructor.
 */
public class OBJ_Door extends S_Entity implements Destroyable {
    // Door Object
    public OBJ_Door(GamePanel gp, int id){
        super(gp);
        name = EntityType.Door;
        down1 = setup("Gate/gate_open");
        collision = true;
        this.id = id;
    }
    @Override
    public void setAction() {
        //does nothing doors have no AI
    }
    
}
