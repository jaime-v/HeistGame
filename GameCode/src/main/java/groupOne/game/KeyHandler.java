package groupOne.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import groupOne.game.GamePanel.Difficulty;
import groupOne.game.GamePanel.GameState;


/**
 * KeyHandler class manages keyboard input, 
 * allowing user to navigate the various 
 * game screens in GamePanel
 * 
 * 
 */
public class KeyHandler implements KeyListener {

    public boolean up, down, left, right, enter;

    /**
     * Enum for the last pressed key, used to assist with movement and allow the player to control the character better
     */
    public enum KeyLastPressedStates {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }
    public KeyLastPressedStates lastPressed;


    GamePanel gp;
    int command;

    public KeyHandler(GamePanel gp){
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Unimplemented, we don't need it
    }

    /**
     * keyPressed method that performs different actions
     * based on the current game state
     * 
     * 
     */
    @Override
    public void keyPressed(KeyEvent event) {

        // TITLE
        if(gp.gameState == GameState.TITLE_STATE){
            titleState(event);
        }

        // PLAY
        else if(gp.gameState == GameState.PLAY_STATE){
            playState(event);
        }

        // PAUSE
        else if(gp.gameState == GameState.PAUSE_STATE){
            pauseState(event);
        }

        // WIN
        else if(gp.gameState == GameState.WIN_STATE){
            winState(event);
        }

        // LOSE
        else if(gp.gameState == GameState.LOSE_STATE){
            loseState(event);
        }
    }

    // IN TITLE SCREEN
    private void titleState(KeyEvent keyEvent){

        // TitleScreenState 0 is default title screen
        if(gp.info.titleScreenState == 0){

            // Move commandNumber and UI Cursor up
            if(keyEvent.getKeyCode() == KeyEvent.VK_W){
                gp.info.commandNumber--;
                if(gp.info.commandNumber < 0){
                    gp.info.commandNumber = 1;
                }
            }
    
            // Move commandNumber and UI Cursor down
            if(keyEvent.getKeyCode() == KeyEvent.VK_S){
                gp.info.commandNumber++;
                if(gp.info.commandNumber > 1){
                    gp.info.commandNumber = 0;
                }
            }

            // Select an option via Enter key
            if(keyEvent.getKeyCode() == KeyEvent.VK_ENTER){
                command = gp.info.commandNumber;
                switch (command){

                    case 0: // Play option, takes user to difficulty selection
                        gp.info.titleScreenState = 1;
                        break;

                    case 1: // Quit option, stops game thread and exits
                        gp.gameThread = null;
                        System.exit(0);
                }
            }
        }

        // TitleScreenState 1 is difficulty selection screen
        else if(gp.info.titleScreenState == 1){

            // Move commandNumber and UI Cursor up
            if(keyEvent.getKeyCode() == KeyEvent.VK_W){
                gp.info.commandNumber--;
                if(gp.info.commandNumber < 0){
                    gp.info.commandNumber = gp.info.maxDifficulties;
                }
            }
    
            // Move commandNumber and UI Cursor down
            if(keyEvent.getKeyCode() == KeyEvent.VK_S){
                gp.info.commandNumber++;
                if(gp.info.commandNumber > gp.info.maxDifficulties){
                    gp.info.commandNumber = 0;
                }
            }

            // Select an option via Enter key
            if(keyEvent.getKeyCode() == KeyEvent.VK_ENTER){
                command = gp.info.commandNumber;
                switch (command){

                    // EASY
                    case 0: // Easy difficulty selected, change current map and change play state
                        gp.currentMap = 0;
                        gp.difficulty = Difficulty.EASY;
                        gp.gameState = GameState.PLAY_STATE;
                        break;

                    // MEDIUM
                    case 1: // Medium difficulty selected, change current map and change play state
                        gp.currentMap = 1;
                        gp.difficulty = Difficulty.MEDIUM;
                        gp.gameState = GameState.PLAY_STATE;
                        break;

                    // HARD
                    case 2: // Hard difficulty selected, change current map and change play state
                        gp.currentMap = 2;
                        gp.difficulty = Difficulty.HARD;
                        gp.gameState = GameState.PLAY_STATE;
                        break;

                    // ABSOLUTE GIGA SWEAT NERD
                    case 3: // Extreme difficulty selected, change current map and change play state
                        gp.currentMap = 3;
                        gp.difficulty = Difficulty.GIGA_SWEAT_NERD;
                        gp.gameState = GameState.PLAY_STATE;
                        break;

                    // BACK
                    case 4: // Back option selected, takes user back to default title screen
                        gp.info.titleScreenState = 0;
                        break;
                }
            }
        }
    }

    // IN PLAY SCREEN
    private void playState(KeyEvent keyEvent){

        switch (keyEvent.getKeyCode()) {
            // Character moves up when user presses W key
            case KeyEvent.VK_W:
                up = true;
                lastPressed = KeyLastPressedStates.UP;
                break;

            // Character moves left when user presses A key
            case KeyEvent.VK_A:
                left = true;
                lastPressed = KeyLastPressedStates.LEFT;
                break;

            // Character moves down when user presses S key
            case KeyEvent.VK_S:
                down = true;
                lastPressed = KeyLastPressedStates.DOWN;
                break;
            
            // Character moves right when user presses D key
            case KeyEvent.VK_D:
                right = true;
                lastPressed = KeyLastPressedStates.RIGHT;
                break;

            // When P key is pressed, user pauses the game
            case  KeyEvent.VK_P:
                gp.gameState = GameState.PAUSE_STATE;
                break;
        }
    }

    // IN PAUSE SCREEN
    private void pauseState(KeyEvent keyEvent){
        
        // When P key is pressed, user unpauses the game
        if(keyEvent.getKeyCode() == KeyEvent.VK_P){
            gp.gameState = GameState.PLAY_STATE;
        }

        // Select an option via Enter key
        if(keyEvent.getKeyCode() == KeyEvent.VK_ENTER){
            enter = true;
        }

        // Move commandNumber and UI Cursor up
        if(keyEvent.getKeyCode() == KeyEvent.VK_W){
            gp.info.commandNumber--;
            if(gp.info.commandNumber < 0){
                gp.info.commandNumber = gp.info.maxPauseOptions;
            }
        }

        // Move commandNumber and UI Cursor down
        if(keyEvent.getKeyCode() == KeyEvent.VK_S){
            gp.info.commandNumber++;
            if(gp.info.commandNumber > gp.info.maxPauseOptions){
                gp.info.commandNumber = 0;
            }
        }

        // If A key is pressed, check if cursor is on music or sfx option and reduce volume if it is not at its lowest
        if(keyEvent.getKeyCode() == KeyEvent.VK_A){
                if(gp.info.commandNumber == 0 && gp.music.volumeScale > 0){
                    gp.music.volumeScale--;
                    gp.music.checkVolume();
                }
                if(gp.info.commandNumber == 1 && gp.SE.volumeScale > 0){
                    gp.SE.volumeScale--;
                }
            }
        
        // If D key is pressed, check if cursor is on music or sfx option and increase volume if it is not at its highest
        if(keyEvent.getKeyCode() == KeyEvent.VK_D){
                if(gp.info.commandNumber == 0 && gp.music.volumeScale < 5){
                    gp.music.volumeScale++;
                    gp.music.checkVolume();
                }
                if(gp.info.commandNumber == 1 && gp.SE.volumeScale < 5){
                    gp.SE.volumeScale++;
                }
            }
        
    }

    // IN WIN SCREEN
    private void winState(KeyEvent keyEvent){
        // Pressing enter takes user back to title screen
        if(keyEvent.getKeyCode() == KeyEvent.VK_ENTER){
            gp.gameState = GameState.TITLE_STATE;
        }
    }

    // IN LOSE SCREEN
    private void loseState(KeyEvent keyEvent){
        // Pressing enter takes user back to title screen
        if(keyEvent.getKeyCode() == KeyEvent.VK_ENTER){
            gp.gameState = GameState.TITLE_STATE;
        }
    }

    
    /**
     * keyReleased method detects when 
     * select key is no longer being 
     * pressed
     * 
     */
    public void keyReleased(KeyEvent keyEvent) {

        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_W:
                up = false;
                break;
            
            case KeyEvent.VK_A:
                left = false;
                break;

            case KeyEvent.VK_S:
                down = false;
                break;

            case KeyEvent.VK_D:
                right = false;
                break;

            case KeyEvent.VK_ENTER:
                enter = false;
                break;
        }
    }
    
}
