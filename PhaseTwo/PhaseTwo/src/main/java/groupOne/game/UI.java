package groupOne.game;

import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.ArrayList;

import groupOne.game.GamePanel.GameState;

/**
 * UI draws screens and information that can be rendered in GamePanel.
 * 
 * 
*/
public class UI {

    // Drawing
    GamePanel gp;
    final Font arial_40, arial_40B, arial_60B, arial_80B;
    BufferedImage scoreImage;
    BufferedImage pausedSign;
    BufferedImage TitleBG;
    BufferedImage arrow;
    BufferedImage easyButton, mediumButton, hardButton, dwtdButton, back, quit, play;
    public boolean messageOn = false;
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    DecimalFormat dFormat = new DecimalFormat("0.00");

    /**
     * Constructor for UI takes in GamePanel to adjust dimensions 
     * and draw properly in the screen. Also sets up fonts to be
     * used, and images that will be displayed.
     * 
     * @param gp GamePanel that is being used
     */
    public UI(GamePanel gp){
        this.gp = gp;
        // Sets fonts in game
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_40B = new Font("Arial", Font.BOLD, 40);
        arial_60B = new Font("Arial", Font.BOLD, 60);
        arial_80B = new Font("Arial", Font.BOLD, 80);
        UI_Reward score = new UI_Reward(gp);
        UI_PausedSign pause = new UI_PausedSign(gp);
        UI_Button buttons = new UI_Button(gp);
        UI_TitleBackground bg = new UI_TitleBackground(gp);
        UI_MenuArrow select = new UI_MenuArrow(gp);
        TitleBG = bg.image;
        pausedSign = pause.image;
        arrow = select.image;
        scoreImage = score.image;
        // Sets buttons in game
        easyButton = buttons.easy;
        mediumButton = buttons.medium;
        hardButton = buttons.hard;
        dwtdButton = buttons.dwtd;
        back = buttons.back;
        quit = buttons.quit;
        play = buttons.play;
    }
    


    /**
     * ResetUI Method resets all of the values that change 
     * throughout the game, and are displayed such as playTime, 
     * amount of rewards remaining on the board, and whether 
     * or not the player has obtained the bonus reward.
     * <p>
     * This method will be called whenever the user moves
     * back to the title screen
     */
    public void resetUI(){
        message.clear();
        messageCounter.clear();
    }


    /**
     * Draw Method draws different screens depending on
     * the current game state.
     * <p>
     * This method will be called in the paintComponent
     * method in GamePanel, so that the screen will be 
     * rendered in the proper order.
     */
    public void draw(Graphics2D g2){

        if(gp.gameState == GameState.WIN_STATE){
            drawWinScreen(g2);
            gp.info.isReset = false;
        }

        if (gp.gameState == GameState.LOSE_STATE){
            drawLossScreen(g2);
            gp.info.isReset = false;
        }

        if(gp.gameState == GameState.TITLE_STATE){
            drawTitleScreen(g2);
            if (!gp.info.isReset) {
                gp.resetGame();
                gp.info.isReset = true;
            }
        }

        if(gp.gameState == GameState.PLAY_STATE){
            gp.info.setTitleScreenDefault(); // Set title screen back to default title screen
            drawSubWindow(gp.tileSize/6, gp.screenHeight - gp.tileSize*3, gp.screenWidth - gp.tileSize/2, gp.tileSize*3, g2); // Create a semi transparent window to display user information
            drawInventory(g2);
            drawPlayDirections(g2);
            drawTimer(g2);
            drawMessage(g2);
            gp.info.isReset = false;
        }

        if(gp.gameState == GameState.PAUSE_STATE){
            drawPauseScreen(g2);
            drawSubWindow(gp.tileSize/6, gp.screenHeight - gp.tileSize*3, gp.screenWidth - gp.tileSize/2, gp.tileSize*3, g2); // Create a semi transparent window to display user information
            drawInventory(g2);
            drawPlayDirections(g2);
            drawCurrentTime(g2);
            drawOptions(g2);
        }
    }
    
    /**
     * AddMessage method, takes in a String of text that will be
     * displayed to the user and disappear after a certain amount
     * of time.
     * <p>
     * This method will be called when player interacts with
     * most objects and some tiles in PLAY_STATE.
     * 
     * @param text String of text to be shown to the user.
     */
    public void addMessage(String text){
        message.add(text);
        messageCounter.add(0);
    }


    // WIN
    /**
     * DrawWinScreen Method, used to draw out the win screen with 2D graphics.
     * Displays the player's final score, finish time, and instructions to 
     * move to the next screen.
     * <p>
     * Will be called when the game state is WIN_STATE.
     * 
     * @param g2 Java 2D Graphics 
     */
    private void drawWinScreen(Graphics2D g2){
        drawShade(g2);

        String winText, finalScoreText, finalTimeText, continueText;
        g2.setFont(arial_60B);
        g2.setColor(Color.yellow);

        winText = "You escaped!";
        int x = getXforCenteredText(g2, winText);
        int y = gp.screenHeight/2 - (gp.tileSize*3);
        g2.drawString(winText, x, y);

        g2.setFont(arial_40B);
        g2.setColor(Color.white);
        finalScoreText = "Final Score: " + gp.player.score;
        x = getXforCenteredText(g2, finalScoreText) - 5*gp.tileSize;
        y = gp.screenHeight/2 - gp.tileSize;
        g2.drawString(finalScoreText, x, y);

        finalTimeText = "Final Time: " + dFormat.format(gp.info.playTime);
        x = getXforCenteredText(g2, finalTimeText) + 5*gp.tileSize;
        g2.drawString(finalTimeText, x, y);
        
        continueText = "Press Enter to continue";
        x = getXforCenteredText(g2, continueText);
        y += 4*gp.tileSize;
        g2.drawString(continueText, x, y);
    }



    // LOSE
    /**
     * DrawLossScreen Method, used to draw out the loss screen with 2D graphics.
     * Displays the player's final score, finish time, and instructions to 
     * move to the next screen.
     * <p>
     * Will be called when the game state is LOSE_STATE.
     * 
     * @param g2 Java 2D Graphics 
     */
    private void drawLossScreen(Graphics2D g2){
        drawShade(g2);
        
        String text;
        g2.setFont(arial_60B);
        g2.setColor(Color.red);

        text = "GAME OVER";
        int x = getXforCenteredText(g2, text);
        int y = gp.screenHeight/2 - (gp.tileSize*3);
        g2.drawString(text, x, y);

        g2.setFont(arial_40B);
        g2.setColor(Color.white);
        text = "Final Score: " + gp.player.score;
        x = getXforCenteredText(g2, text) - 5*gp.tileSize;
        y = gp.screenHeight/2 - gp.tileSize;
        g2.drawString(text, x, y);

        text = "Final Time: " + dFormat.format(gp.info.playTime);
        x = getXforCenteredText(g2, text) + 5*gp.tileSize;
        g2.drawString(text, x, y);
        
        text = "Press Enter to continue";
        x = getXforCenteredText(g2, text);
        y += 4*gp.tileSize;
        g2.drawString(text, x, y);
    }




    // PLAYING
    /**
     * DrawInventory Method, used to draw out player's current score with 2D graphics.
     * <p>
     * Will be called when the game state is PLAY_STATE.
     * 
     * @param g2 Java 2D Graphics 
     */
    private void drawInventory(Graphics2D g2){
        g2.setFont(arial_40B);
        g2.setColor(Color.white);
        g2.drawImage(scoreImage, gp.tileSize, gp.screenHeight - gp.tileSize*2 + 8, gp.tileSize, gp.tileSize, null);
        g2.drawString("x "+ gp.player.score, gp.tileSize*2 + 4, gp.screenHeight - gp.tileSize - 4);
    }

    /**
     * DrawInventory Method, used to draw out player's current play time.
     * The playTime is displayed in seconds with two decimal places.
     * 
     * @param g2 Java 2D Graphics 
     */
    private void drawTimer(Graphics2D g2){
        g2.setFont(arial_40B);
        g2.setColor(Color.white);
        gp.info.playTime += (double)1/gp.FPS;
        g2.drawString("Time: " + dFormat.format(gp.info.playTime), gp.tileSize*7, gp.screenHeight - gp.tileSize*2 + 20);
        g2.drawString("Remaining Rewards: " + gp.info.rewardCount, gp.tileSize*5, gp.screenHeight - gp.tileSize/2);
    }

    /**
     * DrawPlayDirections Method, used to draw out player controls
     * and display them to the user.
     * <p>
     * Will be called when the game state is PLAY_STATE.
     * 
     * @param g2 Java 2D Graphics 
     */
    private void drawPlayDirections(Graphics2D g2){
        g2.setFont(arial_40B);
        g2.setColor(Color.white);
        g2.drawString("USE WASD!", gp.tileSize*16, gp.screenHeight - gp.tileSize*2 + 20);
        g2.drawString("P TO PAUSE!", gp.tileSize*16, gp.screenHeight - gp.tileSize/2);
    }


    /**
     * DrawMessage method, draws text from message list
     * and removes the message from both lists after a 
     * certain amount of time.
     * <p>
     * This method will be called when player interacts with
     * most objects and some tiles in PLAY_STATE.
     * 
     * @param g2 Java 2D graphics
     */
    private void drawMessage(Graphics2D g2){
        int messageX = gp.tileSize;
        int messageY = gp.tileSize*2;
        g2.setFont(arial_40);
        g2.setColor(new Color(240, 240, 240));

        for(int i = 0; i < message.size(); i++){
            if(message.get(i) != null){
                g2.setColor(new Color(0, 0, 0));
                g2.drawString(message.get(i), messageX+2, messageY+2);
                g2.setColor(new Color(240, 240, 240));
                g2.drawString(message.get(i), messageX, messageY);

                int counter = messageCounter.get(i) + 1;
                messageCounter.set(i, counter);
                messageY += 50;

                if(messageCounter.get(i) > 180){
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }
    }
    
    // PAUSE
    private void drawPauseScreen(Graphics2D g2){
        drawShade(g2);
        int x = gp.screenWidth/2 - 282;
        int y = gp.screenHeight/2 - 380;
        g2.drawImage(pausedSign, x, y, null);
    }
    /**
     * Draw options window in pause screen
     * 
     * @param g2 Java 2D Graphics
     */
    private void drawOptions(Graphics2D g2){
        int frameX = gp.screenWidth/4;
        int frameY = gp.screenHeight/4;
        int frameWidth = gp.screenWidth/2;
        int frameHeight = gp.screenHeight/2 + gp.tileSize;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight, g2);

        options_top(g2, frameX, frameY, frameWidth, frameHeight);

        gp.keyH.enter = false;
    }

    /**
     * Draw options text and bars in pause screen
     * 
     * @param g2 Java 2D Graphics
     */
    private void options_top(Graphics2D g2, int frameX, int frameY, int frameWidth, int frameHeight){
        int textX;
        int textY;
        g2.setFont(arial_60B);
        g2.setColor(new Color(255, 255, 255));

        String text = "OPTIONS";
        textX = getXforCenteredText(g2, text);
        textY = frameY + gp.tileSize + gp.tileSize/2;
        g2.drawString(text, textX, textY);

        g2.setFont(arial_40);
        textX = frameX + gp.tileSize;
        textY += gp.tileSize*2;
        g2.drawString("Music", textX, textY);
        if(gp.info.commandNumber == 0){
            g2.drawString(" >", textX - gp.tileSize, textY);
        }

        textY += gp.tileSize*2;
        g2.drawString("Sound Effects", textX, textY);
        if(gp.info.commandNumber == 1){
            g2.drawString(" >", textX - gp.tileSize, textY);
        }

        textY += gp.tileSize*2;
        g2.drawString("Exit Game", textX, textY);
        if(gp.info.commandNumber == 2){
            g2.drawString(" >", textX - gp.tileSize, textY);
            if(gp.keyH.enter == true){
                gp.gameState = GameState.TITLE_STATE;
            }
        }
        
        text = "Back";
        textX = getXforCenteredText(g2, text);
        textY += gp.tileSize*2;
        g2.drawString(text, textX, textY);
        if(gp.info.commandNumber == 3){
            g2.drawString(" >", textX - gp.tileSize, textY);
            if(gp.keyH.enter == true){
                gp.gameState = GameState.PLAY_STATE;
            }
        }

        textX = frameX + frameWidth - gp.tileSize * 4 ;
        textY = frameY + gp.tileSize * 3;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX - 48, textY, 120, 24);
        int volumeWidth = 24 * gp.music.volumeScale;
        g2.fillRect(textX - 48, textY, volumeWidth, 24);;

        textY += gp.tileSize*2;
        g2.drawRect(textX - 48, textY, 120, 24);
        int SEWidth = 24 * gp.SE.volumeScale;
        g2.fillRect(textX - 48, textY, SEWidth, 24);;

    }
    
    /**
     * DrawCurrentTime method draws out the play time from when the player pressed pause.
     * The play time is displayed in seconds with two decimal places.
     * 
     * @param g2 Java 2D Graphics 
     */
    private void drawCurrentTime(Graphics2D g2){
        g2.setFont(arial_40B);
        g2.setColor(Color.white);
        g2.drawString("Time: " + dFormat.format(gp.info.playTime), gp.tileSize*7, gp.screenHeight - gp.tileSize*2 + 20);
        g2.drawString("Remaining Rewards: " + gp.info.rewardCount, gp.tileSize*5, gp.screenHeight - gp.tileSize/2);
    }

    // TITLE
    private void drawTitleScreen(Graphics2D g2){
        /*
        Difficulty buttons are (45x13)*6 = 270x78 pixels
        Centered around middle of screenWidth
        Draw images w/ offset -(1/2)buttonWidth, -(1/2)buttonHeight
        */
        int buttonWidth = 270;
        int buttonHeight = 78;
        int arrowHeight = 52;
        if(gp.info.titleScreenState == 0){
            // SET FONT SIZE AND BACKGROUND
            g2.setFont(arial_80B);
            g2.drawImage(TitleBG, 0,0, null);
    
            // TITLE TEXT POSITION
            String titleText = "HEIST GAME";
            int x = getXforCenteredText(g2, titleText);
            int y = gp.tileSize*4 + gp.tileSize/4;
    
            // DISPLAY SHADOW
            g2.setColor(Color.black);
            g2.drawString(titleText, x+5, y+5);

            // DISPLAY TITLE
            g2.setColor(Color.white);
            g2.drawString(titleText, x, y);
    
            // IMAGE OF CHARACTER
            Enemy_Guard guard = new Enemy_Guard(gp);
            Enemy_Dog dog = new Enemy_Dog(gp);
            int xTemp = gp.screenWidth/2 - gp.tileSize*4;
            y += gp.tileSize*6+gp.tileSize/8;
            g2.drawImage(guard.down1, xTemp, y, gp.tileSize * 2, gp.tileSize * 2, null);
            g2.drawImage(dog.left1, gp.screenWidth/2+gp.tileSize*2, y+15, gp.tileSize * 2, gp.tileSize * 2, null);
            // MENU
            x = gp.screenWidth/2;
            g2.setFont(arial_40);

            //PLAY
            y += gp.tileSize*3;
            g2.drawImage(play, x-buttonWidth/2, y-buttonHeight/2, null);
            if(gp.info.commandNumber == 0){
                g2.drawImage(arrow, x-gp.tileSize*4, y-arrowHeight/2, null);
                //g2.drawString(">>", x-gp.tileSize*5, y+gp.tileSize/2);
            }
            
            // QUIT
            y += gp.tileSize*2;
            g2.drawImage(quit, x-buttonWidth/2, y-buttonHeight/2, null);
            if(gp.info.commandNumber == 1){
                g2.drawImage(arrow, x-gp.tileSize*4, y-arrowHeight/2, null);
            }
        }
        else if(gp.info.titleScreenState == 1){
            g2.drawImage(TitleBG, 0,0, null);

            g2.setFont(arial_60B);
            g2.setColor(Color.white);
            String text = "Choose Difficulty";
            int x = getXforCenteredText(g2, text);
            int y = gp.tileSize*4 + gp.tileSize/4;
            g2.setColor(Color.black);
            g2.drawString(text, x+3, y+3);
            g2.setColor(Color.white);
            g2.drawString(text, x, y);

            x = gp.screenWidth/2;
  
            y += gp.tileSize*2;

            g2.drawImage(easyButton, x-buttonWidth/2, y-buttonHeight/2, null);
            if(gp.info.commandNumber == 0){
                g2.drawImage(arrow, x-gp.tileSize*4, y-arrowHeight/2, null);
            }
            
            // MEDIUM BUTTON
            y += gp.tileSize*2;
            g2.drawImage(mediumButton,  x-buttonWidth/2, y-buttonHeight/2, null);
            if(gp.info.commandNumber == 1){
                g2.drawImage(arrow, x-gp.tileSize*4, y-arrowHeight/2, null);
            }

            //HARD BUTTON
            // x = getXforCenteredText(g2, text);
            y += gp.tileSize*2;
            g2.drawImage(hardButton,  x-buttonWidth/2, y-buttonHeight/2, null);
            if(gp.info.commandNumber == 2){
                g2.drawImage(arrow, x-gp.tileSize*4, y-arrowHeight/2, null);
            }

            // text = "Dumb Ways to Die";
            //xTemp = getXforCenteredText(g2, text);
            y += gp.tileSize*2;
            g2.drawImage(dwtdButton,  x-buttonWidth/2, y-buttonHeight/2, null);
            if(gp.info.commandNumber == 3){
                g2.drawImage(arrow, x-gp.tileSize*4, y-arrowHeight/2, null);
            }

            y += gp.tileSize*2;
            g2.drawImage(back,  x-buttonWidth/2, y-buttonHeight/2, null);
            if(gp.info.commandNumber == 4){
                g2.drawImage(arrow, x-gp.tileSize*4, y-arrowHeight/2, null);
            }
        }
    }

    /**
     * Void method that draws a semi transparent subwindow
     * at the given "x" and "y" coordinates, that is "width" wide
     * and "height" tall
     * @param x         x coordinate for subwindow
     * @param y         y coordinate for subwindow
     * @param width     width of subwindow
     * @param height    height of subwindow
     * @param g2        Java 2D Graphics
     */
    private void drawSubWindow(int x, int y, int width, int height, Graphics2D g2){
        g2.setColor(new Color(0,0,0,150));
        g2.fillRoundRect(x, y, width, height, 35, 35);

        g2.setColor(new Color(255, 255, 255, 150));
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
    }




    /**
     * Returns the X coordinate that will center the input text
     * Helper method for centering different strings and 
     * different sizes of text
     * 
     * @param g2    Java 2D Graphics
     * @param text  Text that will be centered
     * @return      X coordinate that centers the given String of text
     */
    private int getXforCenteredText(Graphics2D g2, String text){
        int textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - textLength/2;
        return x;
    }

    /**
     * Shades the entire screen during pause, lose, and win game states
     * 
     * @param g2 Java 2D Graphics
     */
    private void drawShade(Graphics2D g2){
        g2.setColor(new Color(0,0,0,150));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
    }

}

