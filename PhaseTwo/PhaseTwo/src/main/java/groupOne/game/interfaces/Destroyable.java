package groupOne.game.interfaces;

/**
 * Interface for destroyable objects, such as the rewards and punishments.
 * The self_destruct method causes the object to make itself null after being collected.
 */
public interface Destroyable {
    public void self_destruct();
}
