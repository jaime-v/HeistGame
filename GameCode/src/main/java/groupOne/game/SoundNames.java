package groupOne.game;

/**
 * Enum for sound names so that the code is more understandable.
 */
public enum SoundNames {
    MAINTHEME(0),
    COIN(1),
    POWERUP(2),
    DOOROPEN(3),
    WIN(4),
    DAMAGED(5);

    private int soundId;

    SoundNames (int soundId) {
        this.soundId = soundId;
    }

    public int get_id() {
        return soundId;
    }

}
