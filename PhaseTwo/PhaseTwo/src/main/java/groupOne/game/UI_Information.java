package groupOne.game;

/**
 * UI Information Class that holds all game information that is displayed and interacted with.
 */
public class UI_Information {
    GamePanel gp;
    // CHECK IF GAME IS FINISHED
    public boolean gameFinished = false;

    // CREATE CURSOR FOR TITLE SCREEN
    public int commandNumber = 0;

    // CHECK REMAINING REWARDS ON BOARD
    public int rewardCount = 10;

    // CHECK IF PLAYER HAS BONUS REWARD ALREADY
    public boolean hasBonus = false;

    // CHECK TITLE SCREEN STATE (TITLE or DIFFICULTY SELECTION)
    public int titleScreenState = 0; // 0 is the first screen (TITLE), 1 is the second screen (DIFFICULTY)
    public int maxDifficulties = 4;
    public int maxPauseOptions = 3;

    // CHECK IF GATE IS UNLOCKED
    public boolean unlocked = false;

    // CHECK IF GAME IS RESET
    public boolean isReset = true; // This variable aims to stop the title screen reset lag

    // USER PLAY TIME
    double playTime = 0;

    public UI_Information(GamePanel gp){
        this.gp = gp;
    }
    
    /**
     * ResetGameInfo Method resets all of the values that change 
     * throughout the game, such as amount of  rewards remaining
     *  on the board and whether or not the player has obtained
     *  the bonus reward.
     * <p>
     * This method will be called whenever the user moves
     * back to the title screen
     */
    public void resetGameInfo(){
        playTime = 0;
        rewardCount = 10;
        hasBonus = false;
        unlocked = false;
        gp.aSetter.setSpawnTime();
    }

    // Self Explanatory method, sets title screen back to default
    public void setTitleScreenDefault(){
        titleScreenState = 0;
        commandNumber = 0; 
    }
    
}
