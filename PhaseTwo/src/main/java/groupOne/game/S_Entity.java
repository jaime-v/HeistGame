package groupOne.game;

/**
 * Abstract Class for stationary entities such as Doors, Rewards, etc. to extend. 
 * Contains fields for collision, array index and a method for destructable entities to implement
 */
public abstract class S_Entity extends Entity{
    public int id;
    public EntityType name;

    /**
     * Enum for identifying object types, used to replace strings and improve readability.
     */
    public enum EntityType {
        Bonus,
        Damage,
        Door,
        Gate,
        Laser,
        Reward
    }
    public boolean collision = false;

    public S_Entity(GamePanel gp){
        super(gp);
    }

    public void self_destruct() {
        // TODO Auto-generated method stub
        gp.obj[gp.currentMap][id] = null;
    }
    
}
