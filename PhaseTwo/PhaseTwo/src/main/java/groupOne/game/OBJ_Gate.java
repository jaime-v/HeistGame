package groupOne.game;

/**
 * Sets up gate object image and collision.
 * Adds the gate to the game panel via constructor.
 */
public class OBJ_Gate extends S_Entity{
    // Gate Object
    public OBJ_Gate(GamePanel gp){
        super(gp);
        name = EntityType.Gate;
        down1 = setup("Gate/gate_close");
        collision = true;
    }
    @Override
    public void setAction() {
        //does nothing gates have no AI
    }
    
}
