package groupOne.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import javax.swing.JPanel;

/**
 * Class for the Game Window, sets up the thread that
 * runs the game, and brings all components together
 * <p>
 * Necessary for the player to see and play the game
*/
public class GamePanel extends JPanel implements Runnable{
    
    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3; // Tiles will be scaled by 3 to create 48x48 tiles
    final int tileSize = originalTileSize * scale; // 48x48 tile
    final int maxScreenCol = 25; // Max number of column tiles
    final int maxScreenRow = 15; // Max number of row tiles
    final int screenWidth = tileSize * maxScreenCol; 
    final int screenHeight = tileSize * maxScreenRow + 3*tileSize; 

    // MAPS
    final int maxMap = 10;
    int currentMap = 0;

    final int FPS = 60;

    // SYSTEM
    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound SE = new Sound();
    public CollisionChecker checker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public UI_Information info = new UI_Information(this);
    public Pathfinder pFinder = new Pathfinder(this);
    Thread gameThread;
    
    // BONUS REWARD SPAWN INFORMATION
    boolean bonusInPlace = false;
    public int randRow = 12;
    public int randCol = 11;




    /**
     * Enum for difficulties in the game, for better readability
     */
    public enum Difficulty {
        EASY,
        MEDIUM,
        HARD,
        GIGA_SWEAT_NERD,
    }
    Difficulty difficulty = Difficulty.EASY;

    // ENTITIES AND OBJECTS
    public Player player = new Player(this, keyH);
    public S_Entity obj[][] = new S_Entity[maxMap][40];
    public Entity enemy[][] = new Entity[maxMap][10];
    ArrayList<Entity> entityList = new ArrayList<>();

    // GAME STATE
    /**
     * Enum for game states, such as the title screen or playing screen
     */
    public enum GameState {
        TITLE_STATE,
        PLAY_STATE,
        PAUSE_STATE,
        WIN_STATE,
        LOSE_STATE
    }
    public GameState gameState = GameState.TITLE_STATE;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight)); 
        this.setBackground(Color.BLACK); 
        this.setDoubleBuffered(true); // Buffer strategy for optimized rendering
        this.addKeyListener(keyH); // GamePanel can listen for key input
        this.setFocusable(true); // GamePanel can receive key inputs when it is focused, this allows the panel to be focusable
    }

    public void setupGame(){
        aSetter.setObjects(); // Set objects
        aSetter.setEnemy();
        aSetter.setSpawnTime();

        playMusic(SoundNames.MAINTHEME.get_id()); // Start Music


        gameState = GameState.TITLE_STATE; // Load in title
    }

    public void resetGame(){
        aSetter.setObjects(); // Set objects again
        aSetter.setEnemy();
        aSetter.removeBonus();
        player.setDefaultValues();
        ui.resetUI();
        info.resetGameInfo();
        bonusInPlace = false;

    }
    

    public synchronized void startGameThread(){
        gameThread = new Thread(this, "Game");
        gameThread.start();
    }

    public void run(){
        final double drawInterval = 1000000000/FPS; // 0.01666666 seconds
        double nextDrawTime = System.nanoTime() + drawInterval; // Update every 0.0166666666666 seconds

        while(gameThread != null){

            // TWO THINGS
            // UPDATE information such as character position 
            update();
            // RENDER the screen with updated information
            // repaint is how you call the paintComponent method
            repaint();

            try{
                double remainingTime = nextDrawTime - System.nanoTime(); // time remaining until next draw time
                remainingTime = remainingTime/1000000; // convert to milliseconds
                if(remainingTime < 0){
                    remainingTime = 0; // in the rare case that the computer does take the entire drawing interval to draw, thread will not sleep
                }
                Thread.sleep((long) remainingTime); // Thread.sleep only takes long values, and milliseconds

                nextDrawTime += drawInterval; // calculate next draw time
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    private void update(){
        if(gameState == GameState.PLAY_STATE){

            // Bonus Collection window
            while(bonusInPlace == false){
                setBonusSpawnLocation();
            }
            if(info.playTime > aSetter.spawnTime && info.playTime < aSetter.despawnTime && info.hasBonus == false){
                aSetter.setBonus();
            }
            else if(info.playTime > aSetter.despawnTime){
                aSetter.removeBonus();
            }

            // Check if gate is unlocked
            if(info.rewardCount == 0 && info.unlocked == false){
                aSetter.openGate();
                info.unlocked = true;
            }

            // Update player information
            player.update();

            // Update enemy information
            for(int i = 0; i < enemy[1].length; i++){
                if(enemy[currentMap][i] != null){
                    enemy[currentMap][i].update();
                }
            }
        }
    }

    // Method to draw things in JPanel
    public void paintComponent(Graphics g){
        super.paintComponent(g); 

        Graphics2D g2 = (Graphics2D)g;

        if(gameState == GameState.TITLE_STATE){
            ui.draw(g2);
        }
        else{
            tileM.draw(g2);
            
            entityList.add(player);
            for(int i = 0; i < enemy[1].length; i++){
                if(enemy[currentMap][i] != null){
                    entityList.add(enemy[currentMap][i]);
                }
            }

            for(int i = 0; i < obj[1].length; i++){
                if(obj[currentMap][i] != null){
                    entityList.add(obj[currentMap][i]);
                }
            }

            Collections.sort(entityList, new Comparator<Entity>() {
                public int compare(Entity e1, Entity e2){
                    int result = Integer.compare(e1.y, e2.y);
                    return result;
                }
            });
            
            for(int i = 0; i < entityList.size(); i++){
                entityList.get(i).draw(g2);
            }
            entityList.clear();
    
    
            ui.draw(g2);
            g2.dispose();
        }

    }


    /**
     * Random spawn location for the bonus reward, 
     * spawns on a tile without collision. With 
     * coordinates X and Y. X being between 2 and 
     * 22 and Y between 2 and 14
     */
    private void setBonusSpawnLocation(){
        Random random = new Random();
        int x = random.nextInt(2,23);
        int y = random.nextInt(2,15);
        int randTile = tileM.mapTileNum[currentMap][x][y];
        while(tileM.tile[randTile].collision == true){
            x = random.nextInt(2,23);
            y = random.nextInt(2,15);
            randTile = tileM.mapTileNum[currentMap][x][y];
        }
        bonusInPlace = true; // ACCESSED ONLY WHEN TILE HAS NO COLLISION
        randCol = x;
        randRow = y;
    }

    private void playMusic(int i){
        music.setFile(i);
        music.play();
        music.loop();
    }

    // Method to stop music, in case we want to
    private void stopMusic(){
        music.stop();
    }
    
    public void playSE(int i){
        SE.setFile(i);
        SE.play();
    }
}
