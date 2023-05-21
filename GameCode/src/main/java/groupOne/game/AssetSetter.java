package groupOne.game;

import java.util.Random;

/**
 * Class sets up the locations of Assets on the 32x18 tile screen
 * It has a GamePanel field, a constructor, and setup methods.
 * <p>
 * This class will be used in the GamePanel class to set up objects
 * and enemies at the start of the game, before the player is rendered.
 * Another method called closeEntrance will seal off the starting tile
 * when the player finishes collecting the rewards
 * <p>
 * Necessary to set up the game before the player sees it
*/
public class AssetSetter {

    GamePanel gp;

    // BONUS SPAWN TIME
    public int spawnTime;
    public int despawnTime;



    /**
     * Enum for types of objects that are in the game
     */
    public enum ObjectType {
        OBJ_Bonus,
        OBJ_Damage,
        OBJ_Gate,
        OBJ_Reward,
        OBJ_Door
    }

    /**
     * Constructor takes in a GamePanel parameter
     * <p>
     * The AssetSetter must know the tileSize, as well as the objects
     * being used in the GamePanel's object array
     *
     * @param  gp  a GamePanel detailing the size of the screen and types of objects
     * @see        GamePanel
    */
    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

/*     public void placeObject(int index, int map, int x, int y){
        gp.obj[map][index].x = x * gp.tileSize;
        gp.obj[map][index].y = y * gp.tileSize;
    } */

    /**
     * Places enemy at list index "index" to coordinates
     * "x" and "y"
     * 
     * @param index Index of object in list
     * @param map   Map number of object
     * @param x     X coordinate of object
     * @param y     Y coordinate of object
     */
    public void placeEnemy(int index, int map, int x, int y){
        gp.enemy[map][index].x = x * gp.tileSize;
        gp.enemy[map][index].y = y * gp.tileSize;
    }

    /**
     * Places objects on the screen using the GamePanel field. These
     * objects are set up before the game enters its play state.
     * <p>
     * This class will be used in the GamePanel class to set up objects
     * at the start of the game, before other entities are rendered
     * on the screen.
     * <p>
     * The board is 25x15, x goes from 0 to 29, y goes from 0 to 14
     * The section below the board is for the UI
    */
    public void setObjects(){
        // MAP 0 IS EASY DIFFICULTY
        int mapNum = 0;
        /**
         * Setting Reward Objects (Exits) at 
         * various locations on the board.
         * <p>
         * Uses the reward01 or reward02 sprites
         */

        setObject(1, 13, ObjectType.OBJ_Reward, mapNum, 0);
        setObject(2, 2, ObjectType.OBJ_Reward, mapNum, 1);
        setObject(6, 6, ObjectType.OBJ_Reward, mapNum, 2);
        setObject(10, 7, ObjectType.OBJ_Reward, mapNum, 3);
        setObject(10, 13, ObjectType.OBJ_Reward, mapNum, 4);
        setObject(12, 2, ObjectType.OBJ_Reward, mapNum, 5);
        setObject(14, 9, ObjectType.OBJ_Reward, mapNum, 6);
        setObject(19, 3, ObjectType.OBJ_Reward, mapNum, 7);
        setObject(19, 10, ObjectType.OBJ_Reward, mapNum, 8);
        setObject(23, 7, ObjectType.OBJ_Reward, mapNum, 9);


        /**
         * Setting Gate Objects (Exits) at the bottom
         * right of the board
         * 31,16, 31,15, 31,14
         * <p>
         * Uses the gate_close sprite
         */
        setObject(24, 11, ObjectType.OBJ_Gate, mapNum, 10);
        setObject(24, 12, ObjectType.OBJ_Gate, mapNum, 11);
        setObject(24, 13, ObjectType.OBJ_Gate, mapNum, 12);


        /**
         * Setting Damage Objects (Punishments) at various locations
         * 10,11, 15,14, 19,11
         * <p>
         * Uses the damage sprite (bolts)
         */
        setObject(7, 10, ObjectType.OBJ_Damage, mapNum, 13);
        setObject(12, 5, ObjectType.OBJ_Damage, mapNum, 14);
        setObject(17, 11, ObjectType.OBJ_Damage, mapNum, 15);


        /**
         * Another Gate Object, this will be used to close the 
         * entrance after the player picks up a reward
         * Uses the gate_close sprite, like the other gates
         */
        setObject(0, 1, ObjectType.OBJ_Gate, mapNum, 20);





        // MEDIUM DIFFICULTY OBJECTS
        mapNum = 1;
        // REWARDS
        setObject(1, 13, ObjectType.OBJ_Reward, mapNum, 0);
        setObject(4, 4, ObjectType.OBJ_Reward, mapNum, 1);
        setObject(10, 2, ObjectType.OBJ_Reward, mapNum, 2);
        setObject(10, 4, ObjectType.OBJ_Reward, mapNum, 3);
        setObject(10, 9, ObjectType.OBJ_Reward, mapNum, 4);
        setObject(14, 5, ObjectType.OBJ_Reward, mapNum, 5);
        setObject(17, 3, ObjectType.OBJ_Reward, mapNum, 6);
        setObject(23, 3, ObjectType.OBJ_Reward, mapNum, 7);
        setObject(22, 6, ObjectType.OBJ_Reward, mapNum, 8);
        setObject(19, 11, ObjectType.OBJ_Reward, mapNum, 9);

        // GATES
        setObject(24, 11, ObjectType.OBJ_Gate, mapNum, 10);
        setObject(24, 12, ObjectType.OBJ_Gate, mapNum, 11);
        setObject(24, 13, ObjectType.OBJ_Gate, mapNum, 12);

        // DAMAGE
        setObject(1, 6, ObjectType.OBJ_Damage, mapNum, 13);
        setObject(5, 10, ObjectType.OBJ_Damage, mapNum, 14);
        setObject(10, 1, ObjectType.OBJ_Damage, mapNum, 15);
        setObject(16, 8, ObjectType.OBJ_Damage, mapNum, 17);
        setObject(21, 1, ObjectType.OBJ_Damage, mapNum, 18);
        setObject(21, 12, ObjectType.OBJ_Damage, mapNum, 19);
        setObject(13, 5, ObjectType.OBJ_Damage, mapNum, 21);

        // ENTRANCE GATE
        setObject(0, 1, ObjectType.OBJ_Gate, mapNum, 20);





        // HARD DIFFICULTY OBJECTS
        mapNum = 2;
        // REWARDS
        setObject(7, 1, ObjectType.OBJ_Reward, mapNum, 0);
        setObject(14, 1, ObjectType.OBJ_Reward, mapNum, 1);
        setObject(23, 1, ObjectType.OBJ_Reward, mapNum, 2);
        setObject(9, 3, ObjectType.OBJ_Reward, mapNum, 3);
        setObject(6, 4, ObjectType.OBJ_Reward, mapNum, 4);
        setObject(17, 4, ObjectType.OBJ_Reward, mapNum, 5);
        setObject(11, 6, ObjectType.OBJ_Reward, mapNum, 6);
        setObject(1, 13, ObjectType.OBJ_Reward, mapNum, 7);
        setObject(10, 13, ObjectType.OBJ_Reward, mapNum, 8);
        setObject(17, 11, ObjectType.OBJ_Reward, mapNum, 9);

        // GATES
        setObject(24, 11, ObjectType.OBJ_Gate, mapNum, 10);
        setObject(24, 12, ObjectType.OBJ_Gate, mapNum, 11);
        setObject(24, 13, ObjectType.OBJ_Gate, mapNum, 12);

        // DAMAGE
        setObject(22, 1, ObjectType.OBJ_Damage, mapNum, 13);
        setObject(6, 2, ObjectType.OBJ_Damage, mapNum, 14);
        setObject(13, 2, ObjectType.OBJ_Damage, mapNum, 15);
        setObject(6, 5, ObjectType.OBJ_Damage, mapNum, 17);
        setObject(18, 10, ObjectType.OBJ_Damage, mapNum, 18);
        setObject(2, 12, ObjectType.OBJ_Damage, mapNum, 19);
        setObject(16, 4, ObjectType.OBJ_Damage, mapNum, 21);

        // ENTRANCE GATE
        setObject(0, 1, ObjectType.OBJ_Gate, mapNum, 20);








        // EXTREME DIFFICULTY OBJECTS
        mapNum = 3;
        // REWARDS
        setObject(3, 13, ObjectType.OBJ_Reward, mapNum, 0);
        setObject(5, 5, ObjectType.OBJ_Reward, mapNum, 1);
        setObject(8, 1, ObjectType.OBJ_Reward, mapNum, 2);
        setObject(10, 9, ObjectType.OBJ_Reward, mapNum, 3);
        setObject(13, 3, ObjectType.OBJ_Reward, mapNum, 4);
        setObject(13, 11, ObjectType.OBJ_Reward, mapNum, 5);
        setObject(17, 7, ObjectType.OBJ_Reward, mapNum, 6);
        setObject(18, 9, ObjectType.OBJ_Reward, mapNum, 7);
        setObject(21, 7, ObjectType.OBJ_Reward, mapNum, 8);
        setObject(21, 13, ObjectType.OBJ_Reward, mapNum, 9);

        // GATES
        setObject(24, 11, ObjectType.OBJ_Gate, mapNum, 10);
        setObject(24, 12, ObjectType.OBJ_Gate, mapNum, 11);
        setObject(24, 13, ObjectType.OBJ_Gate, mapNum, 12);

        // DAMAGE
        setObject(2, 13, ObjectType.OBJ_Damage, mapNum, 13);
        setObject(5, 8, ObjectType.OBJ_Damage, mapNum, 14);
        setObject(7, 1, ObjectType.OBJ_Damage, mapNum, 15);
        setObject(13, 12, ObjectType.OBJ_Damage, mapNum, 17);
        setObject(17, 9, ObjectType.OBJ_Damage, mapNum, 18);
        setObject(18, 13, ObjectType.OBJ_Damage, mapNum, 19);
        setObject(23, 10, ObjectType.OBJ_Damage, mapNum, 21);

        // ENTRANCE GATE
        setObject(0, 1, ObjectType.OBJ_Gate, mapNum, 20);
    }



    
    /**
     * CloseEntrance method closes the entrance, moving a previously set Gate Object to the
     * entrance of the board
     */
    public void closeEntrance(){
        int mapNum = 0;
        gp.obj[mapNum][20].x = 0;
        gp.obj[mapNum][20].y = 2 * gp.tileSize;

        mapNum = 1;
        gp.obj[mapNum][20].x = 0;
        gp.obj[mapNum][20].y = 2 * gp.tileSize;

        mapNum = 2;
        gp.obj[mapNum][20].x = 0;
        gp.obj[mapNum][20].y = 2 * gp.tileSize;

        mapNum = 3;
        gp.obj[mapNum][20].x = 0;
        gp.obj[mapNum][20].y = 2 * gp.tileSize;
    }

    /**
     * Set an typed object at a x,y coordnate
     * @param x X coordinate of the object to be placed
     * @param y Y coordinate of the object to be placed
     * @param type Type of the object to be placed
     * @param mapNum The ID of the map loaded
     * @param index the index of the S_object array to set the object occupying 
     */
    public void setObject(Integer x, Integer y, ObjectType type, Integer mapNum, Integer index) {
        //boolean shouldIncrement = false; // Whether to increment the s_object count; if placing by index automatically

        /*
        if (index == null) { // If index is null place by index automatically
            index = gp.S_ObjectCount;
            shouldIncrement = true;
        }
        */

        switch (type) {
            case OBJ_Gate:
                gp.obj[mapNum][index] = new OBJ_Gate(gp);
                break;
            case OBJ_Reward:
                gp.obj[mapNum][index] = new OBJ_Reward(gp, index);
                break;
            case OBJ_Bonus:
                gp.obj[mapNum][index] = new OBJ_Bonus(gp, index);
                break;
            case OBJ_Damage:
                gp.obj[mapNum][index] = new OBJ_Damage(gp, index);
                break;
            case OBJ_Door:
                gp.obj[mapNum][index] = new OBJ_Door(gp, index);
                break;
            default:
                //broken game
                break;
        }
        gp.obj[mapNum][index].x = x * gp.tileSize;
        gp.obj[mapNum][index].y = y * gp.tileSize;

        /*
        if (shouldIncrement) {
            gp.S_ObjectCount++;
        }
        */
    }



    /**
     * SetBonus method sets the Bonus object to a certain tile on the board. The bonus will be in index 16 of the array
     */
    public void setBonus(){
        int mapNum = 0;
        setObject(gp.randCol, gp.randRow, ObjectType.OBJ_Bonus, mapNum, 16);

        mapNum = 1;
        setObject(gp.randCol, gp.randRow, ObjectType.OBJ_Bonus, mapNum, 16);

        mapNum = 2;
        setObject(gp.randCol, gp.randRow, ObjectType.OBJ_Bonus, mapNum, 16);

        mapNum = 3;
        setObject(gp.randCol, gp.randRow, ObjectType.OBJ_Bonus, mapNum, 16);
    }




    /**
     * RemoveBonus method sets the Bonus object to null, destroying it
     * This method will be called if the player fails
     * to collect the bonus reward
     */
    public void removeBonus(){
        int mapNum = 0;
        gp.obj[mapNum][16] = null;
        mapNum = 1;
        gp.obj[mapNum][16] = null;
        mapNum = 2;
        gp.obj[mapNum][16] = null;
        mapNum = 3;
        gp.obj[mapNum][16] = null;
    }



    
    /**
     * OpenGate method switches out the original Gate objects
     * with Door objects, which use the gate_open sprite
     */
    public void openGate(){
        int mapNum = 0;
        
        setObject(24, 11, ObjectType.OBJ_Door, mapNum, 10);
        setObject(24, 12, ObjectType.OBJ_Door, mapNum, 11);
        setObject(24, 13, ObjectType.OBJ_Door, mapNum, 12);

        mapNum = 1;
        
        setObject(24, 11, ObjectType.OBJ_Door, mapNum, 10);
        setObject(24, 12, ObjectType.OBJ_Door, mapNum, 11);
        setObject(24, 13, ObjectType.OBJ_Door, mapNum, 12);

        mapNum = 2;
        
        setObject(24, 11, ObjectType.OBJ_Door, mapNum, 10);
        setObject(24, 12, ObjectType.OBJ_Door, mapNum, 11);
        setObject(24, 13, ObjectType.OBJ_Door, mapNum, 12);

        mapNum = 3;
        
        setObject(24, 11, ObjectType.OBJ_Door, mapNum, 10);
        setObject(24, 12, ObjectType.OBJ_Door, mapNum, 11);
        setObject(24, 13, ObjectType.OBJ_Door, mapNum, 12);

    }




    
    /**
     * SetEnemy method creates enemies on the board
     * at various locations
     * <p>
     */
    public void setEnemy(){
        // EASY MAP ENEMIES
        int mapNum = 0;
        gp.enemy[mapNum][0] = new Enemy_Guard(gp);
        gp.enemy[mapNum][1] = new Enemy_Guard(gp);
        gp.enemy[mapNum][2] = new Enemy_Guard(gp);
        gp.enemy[mapNum][3] = new Enemy_Dog(gp);
        
        placeEnemy(0, mapNum, 10, 2);
        placeEnemy(1, mapNum, 10, 3);
        placeEnemy(2, mapNum, 19, 4);
        placeEnemy(3, mapNum, 19, 11);
        
        // MEDIUM MAP ENEMIES
        mapNum = 1;
        gp.enemy[mapNum][0] = new Enemy_Guard(gp);
        gp.enemy[mapNum][1] = new Enemy_Guard(gp);
        gp.enemy[mapNum][2] = new Enemy_Dog(gp);
        gp.enemy[mapNum][3] = new Enemy_Dog(gp);
        placeEnemy(0, mapNum, 8, 7);
        placeEnemy(1, mapNum, 14, 13);
        placeEnemy(2, mapNum, 23, 1);
        placeEnemy(3, mapNum, 23, 7);

        // HARD MAP ENEMIES
        mapNum = 2;
        gp.enemy[mapNum][0] = new Enemy_Guard(gp);
        gp.enemy[mapNum][1] = new Enemy_Dog(gp);
        gp.enemy[mapNum][2] = new Enemy_Dog(gp);
        gp.enemy[mapNum][3] = new Enemy_Dog(gp);
        placeEnemy(0, mapNum, 3, 13);
        placeEnemy(1, mapNum, 15, 7);
        placeEnemy(2, mapNum, 18, 13);
        placeEnemy(3, mapNum, 23, 2);

        // EXTREME MAP ENEMIES
        mapNum = 3;
        gp.enemy[mapNum][0] = new Enemy_Guard(gp);
        gp.enemy[mapNum][1] = new Enemy_Dog(gp);
        gp.enemy[mapNum][2] = new Enemy_Dog(gp);
        gp.enemy[mapNum][3] = new Enemy_Guard(gp);
        gp.enemy[mapNum][4] = new Enemy_Dog(gp);
        placeEnemy(0, mapNum, 12, 3);
        placeEnemy(1, mapNum, 23, 1);
        placeEnemy(2, mapNum, 1, 13);
        placeEnemy(3, mapNum, 11, 11);
        placeEnemy(4, mapNum, 23, 13);

    }

    /**
     * Random spawn timer for the bonus reward, spawns between 8 and 15 seconds
     */
    public void setSpawnTime(){
        Random random = new Random();
        spawnTime = random.nextInt(8,15);
        despawnTime = spawnTime + 20;
    }


}