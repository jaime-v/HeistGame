package groupOne.game;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/**
 * Sound class sets up audio for the game.
 * Storing all required audio filepaths 
 * into an easily accessible array.
 * 
*/
public class Sound {
    Clip clip; // Audio clip
    URL soundURL[] = new URL[30]; // Can store up to 30 sounds, indexed from 0-29
    FloatControl fc;
    int volumeScale = 3;
    float volume;

    /**
     * Constructor
     * Stores all audio filepaths in an array. 
     * Can be called using different indices.
     * 
     * 
     * 
    */
    public Sound(){
        soundURL[0] = getClass().getResource("/mainTheme.wav");
        soundURL[1] = getClass().getResource("/coin.wav");
        soundURL[2] = getClass().getResource("/powerup.wav");
        soundURL[3] = getClass().getResource("/dooropen.wav");
        soundURL[4] = getClass().getResource("/win.wav");
        soundURL[5] = getClass().getResource("/receivedamage.wav");
    }

    /**
     * Set audio file to be played next. File 
     * is specified based on the input index.
     * <p>
     * Audio file must be in the soundURL array.
     * @param i  index of audio clip requested
    */
    public void setFile(int i){
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
            checkVolume();
        } catch (Exception e) {
        }
    }

    public void play(){
        clip.start(); // Self-explanatory, plays audio clip
    }

    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY); // Self-explanatory, loops audio clip
    }

    public void stop(){
        clip.stop(); // Self-explanatory, stops audio clip
    }

    public void checkVolume(){
        switch(volumeScale){
            case 0: volume = -80f; break;
            case 1: volume = -20f; break;
            case 2: volume = -12f; break;
            case 3: volume = -5f; break;
            case 4: volume = 1f; break;
            case 5: volume = 6f; break;
        }
        fc.setValue(volume);
    }
}
