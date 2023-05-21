package groupOne.game;

import javax.swing.JFrame;
/**
 * Main Game class, this will run the game
 * 
 * mvn package
 * java -cp target/GameCode-1.0-SNAPSHOT.jar groupOne.game.HeistGame
 * 
 */
public class HeistGame {

    public static JFrame window;
    public static void main(String[] args){

        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Heist Game");
        window.setUndecorated(true);

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startGameThread();
    }
}
