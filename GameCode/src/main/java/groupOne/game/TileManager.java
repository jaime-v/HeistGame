package groupOne.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * TileManager constructs the map
 * for GamePanel to use. Stores
 * various tiles, scales them with
 * helper class, and draws out a map
 * that is created with a .txt file.
 * 
*/
public class TileManager {
    GamePanel gp;
    Tile[] tile;
    int mapTileNum[][][];
    boolean drawPath = false;

    // Constructor
    public TileManager(GamePanel gp){
        this.gp = gp;

        tile = new Tile[40];
        mapTileNum = new int[gp.maxMap][gp.maxScreenCol][gp.maxScreenRow];

        getTileImage();
        loadMap("/map01.txt",0);
        loadMap("/map02.txt",1);
        loadMap("/map03.txt",2);
        loadMap("/map04.txt",3);
    }

    public void draw(Graphics2D g2){
        int col = 0, row = 0, x = 0, y = 0;
        while(col < gp.maxScreenCol && row < gp.maxScreenRow){

            int tileNum = mapTileNum[gp.currentMap][col][row];

            g2.drawImage(tile[tileNum].image, x, y, null);
            col++;
            x += gp.tileSize;

            if(col == gp.maxScreenCol){
                col = 0;
                x = 0;
                row++;
                y += gp.tileSize;
            }
        }

        // Draws path of an enemy moving toward player if drawPath is true
        if(drawPath == true){
            g2.setColor(new Color(255,0,0,70));

            for(int i = 0; i < gp.pFinder.pathList.size(); i++){
                int drawX = gp.pFinder.pathList.get(i).col * gp.tileSize;
                int drawY = gp.pFinder.pathList.get(i).row * gp.tileSize;
                g2.fillRect(drawX, drawY, gp.tileSize, gp.tileSize);
            }
        }
    }

    
    // Read .txt file from mapData
    // Store the numbers in a 3D array
    // Draw method will call on this 3D array "mapTileNum" to draw each individual tile
    private void loadMap(String filePath, int map){
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0, row = 0;

            while(col < gp.maxScreenCol && row < gp.maxScreenRow){
                String line = br.readLine();

                while(col < gp.maxScreenCol){
                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[map][col][row] = num;

                    col++;
                }

                if(col == gp.maxScreenCol){
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (Exception e) {
        }
    }


    // Load Tile Images with this method
    private void getTileImage(){
        setup(10, "Floors/floor_1", false);
        setup(11, "Floors/floor_2", false);
        setup(12, "Floors/floor_3", false);
        setup(13, "Floors/floor_4", false);
        setup(14, "Floors/floor_BASIC", false);

        setup(15, "wall", true);

        setup(20, "Borders/border_BOT", true);
        setup(21, "Borders/border_BOTL", true);
        setup(22, "Borders/border_BOTR", true);
        setup(23, "Borders/border_SIDE", true);
        setup(24, "Borders/border_TOP", true);
        setup(25, "Borders/border_TOPL", true);
        setup(26, "Borders/border_TOPR", true);

    }

    // Set up tiles to be used, reduces amount of lines copy-pasted 
    private void setup(int index, String imagePath, boolean collision){
        Helper helper = new Helper();
        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/" + imagePath + ".png"));
            tile[index].image = helper.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
