package groupOne.game;

/**
 * Class checks for collision between game entities,
 * objects, and tiles.
 * <p>
 * Necessary for the player to interact with objects, tiles
*/
public class CollisionChecker {

    GamePanel gp;

    /**
     * CollisionChecker class requires the positions 
     * of the game elements and their solid areas
     * @param gp
     */
    public CollisionChecker(GamePanel gp){
        this.gp = gp;
    }

    /**
     * Checks if entity will collide with a solid tile (tile with collision = true)
     * @param entity
     */
    public void checkTile(Entity entity){

        
        // Get the solidArea of entity
        final int entityLeftWorldX = entity.x + entity.solidArea.x;
        final int entityRightWorldX = entity.x + entity.solidArea.x + entity.solidArea.width;
        final int entityTopWorldY = entity.y + entity.solidArea.y;
        final int entityBottomWorldY = entity.y + entity.solidArea.y + entity.solidArea.height;




        // Get the solidArea of entity in terms of columns and rows
        int entityLeftCol = entityLeftWorldX/gp.tileSize;
        int entityRightCol = entityRightWorldX/gp.tileSize;
        int entityTopRow = entityTopWorldY/gp.tileSize;
        int entityBottomRow = entityBottomWorldY/gp.tileSize;




        // Create two tileNum variables, which will check if the specific tile has collision
        int tileNum1, tileNum2;




        switch(entity.direction){
            /**
             * If entity is moving up, check if the tiles above have collision on
             * _____________________________________________________
             *                  |                 |
             *                  |                 |
             *                  |                 |
             *   tileNum1       |     tileNum2    |   
             * _________________|_________________|_________________
             *                 _|__________       | 
             *                | | Player  |       |
             *                |_|_________|       |  
             *                  |                 |           
             * _________________|_________________|_________________
             *                  |                 |
             *                  |                 |
             *                  |                 |
             *                  |                 |
             * _________________|_________________|_________________
             */
            case UP:
                entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize; // The row entity would reach if they moved in that direction
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow]; // Get the tile that is immediately above entity's row and in line with entity's left column
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow]; // Get the tile that is immediately above entity's row and in line with entity's right column
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
                    entity.collisionOn = true;
                }
                break;



            /**
             * If entity is moving down, check if the tiles below have collision on
             * _____________________________________________________
             *                  |                 |
             *                  |                 |
             *                  |                 |
             *                  |                 |
             * _________________|_________________|_________________
             *                  |                 |
             *                 _|__________       | 
             *                | | Player  |       |
             *                |_|_________|       |      
             * _________________|_________________|_________________
             *                  |                 |
             *                  |                 |
             *   tileNum1       |     tileNum2    |
             *                  |                 |
             * _________________|_________________|_________________
             */
            case DOWN:
                entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
                    entity.collisionOn = true;
                }
                break;




            /**
             * If entity is moving left, check if the tiles to the left have collision on
             * _____________________________________________________
             *                  |                 |
             *                  |                 |
             *                  |                 |
             *                  |                 |
             * _________________|_________________|_________________
             *                  |                 |
             *                  |                 |
             *   tileNum1       |  ___________    |
             * _________________|_|  Player  |____|_________________
             *                  | |__________|    |
             *                  |                 |
             *                  |                 |
             *    tileNum2      |                 |
             * _________________|_________________|_________________
             */
            case LEFT:
                entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
                    entity.collisionOn = true;
                }
                break;


                
            /**
             * If entity is moving right, check if the tiles to the right have collision on
             * _____________________________________________________
             *                  |                 |
             *                  |                 |
             *                  |                 |
             *                  |                 |
             * _________________|_________________|_________________
             *                  |                 |
             *                  |                 |
             *                  |     ___________ |   tileNum1 
             * _________________|____|  Player  | |_________________
             *                  |    |__________| |
             *                  |                 |
             *                  |                 |
             *                  |                 |   tileNum2 
             * _________________|_________________|_________________
             */
            case RIGHT:
                entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
                    entity.collisionOn = true;
                }
                break;
        }
    }
    
    /**
     * Returns the index of the object that is touched by the entity.
     * This method checks if the collision area of any entity that intersects
     * the collision area of any object. If the entity is the player,
     * the index of the object is returned so that the player
     * can interact with it. Otherwise, 999 is returned which belongs
     * to no object.
     * 
     * <p>
     * 
     * This is necessary because enemies must be able to go through
     * objects, but player must be able to interact with those same
     * objects.
     * 
     * @param entity    The entity that collides with the object
     * @param player    Boolean stating if the player is the entity colliding with the object
     * @return          index of object that is collided with
     */
    public int checkObject(Entity entity, boolean player){
        int index = 999;

        for(int i = 0; i < gp.obj[1].length; i++){
            if(gp.obj[gp.currentMap][i] != null){
                entity.solidArea.x = entity.x + entity.solidArea.x;
                entity.solidArea.y = entity.y + entity.solidArea.y;

                gp.obj[gp.currentMap][i].solidArea.x = gp.obj[gp.currentMap][i].x + gp.obj[gp.currentMap][i].solidArea.x;
                gp.obj[gp.currentMap][i].solidArea.y = gp.obj[gp.currentMap][i].y + gp.obj[gp.currentMap][i].solidArea.y;

                switch(entity.direction){
                    case UP: entity.solidArea.y -= entity.speed; break;
                    case DOWN: entity.solidArea.y += entity.speed; break;
                    case LEFT: entity.solidArea.x -= entity.speed; break;
                    case RIGHT: entity.solidArea.x += entity.speed; break;
                }
                if(entity.solidArea.intersects(gp.obj[gp.currentMap][i].solidArea)){
                    if(gp.obj[gp.currentMap][i].collision == true){
                        entity.collisionOn = true;
                    }
                    if(player == true){
                        index = i;
                    }
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.obj[gp.currentMap][i].solidArea.x = gp.obj[gp.currentMap][i].solidAreaDefaultX;
                gp.obj[gp.currentMap][i].solidArea.y = gp.obj[gp.currentMap][i].solidAreaDefaultY;
            }
        }

        return index;
    }

    /**
     * Returns the index of the entity that is touched by the entity.
     * This method checks if the collision area of any entity that intersects
     * the collision area of any other entity. If the entity is not the target,
     * the index of the object is returned so that the entity can interact 
     * with the target. Otherwise, 999 is returned which belongs to no entity.
     * 
     * <p>
     * This is necessary so that enemies cannot phase through other enemies,
     * but will collide with the player.
     * 
     * @param entity    The entity that collides with the target entity
     * @param target    The entity that is being collided with
     * @return          index of entity that is collided with
     */
    public int checkEntity(Entity entity, Entity[][] target){
        int index = 999;

        for(int i = 0; i < target[1].length; i++){
            if(target[gp.currentMap][i] != null){
                entity.solidArea.x = entity.x + entity.solidArea.x;
                entity.solidArea.y = entity.y + entity.solidArea.y;

                target[gp.currentMap][i].solidArea.x = target[gp.currentMap][i].x + target[gp.currentMap][i].solidArea.x;
                target[gp.currentMap][i].solidArea.y = target[gp.currentMap][i].y + target[gp.currentMap][i].solidArea.y;

                switch(entity.direction){
                    case UP: entity.solidArea.y -= entity.speed; break;
                    case DOWN: entity.solidArea.y += entity.speed; break;
                    case LEFT: entity.solidArea.x -= entity.speed; break;
                    case RIGHT: entity.solidArea.x += entity.speed; break;
                }
                
                if(entity.solidArea.intersects(target[gp.currentMap][i].solidArea)){
                    if(target[gp.currentMap][i] != entity){
                        entity.collisionOn = true;
                        index = i;
                    }
                }
                
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                target[gp.currentMap][i].solidArea.x = target[gp.currentMap][i].solidAreaDefaultX;
                target[gp.currentMap][i].solidArea.y = target[gp.currentMap][i].solidAreaDefaultY;
            }
        }

        return index;
    }

    /**
     * returns a boolean indicating whether or not 
     * there is collision between an enemy and a player
     * when the player is not moving.
     * 
     * 
     * @param entity    the entity that is moving toward the player
     * @return          boolean representing if there is collision with player
     */
    public boolean checkPlayer(Entity entity){

        boolean contact = false;
        
        entity.solidArea.x = entity.x + entity.solidArea.x;
        entity.solidArea.y = entity.y + entity.solidArea.y;

        gp.player.solidArea.x = gp.player.x + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.y + gp.player.solidArea.y;

        switch(entity.direction){
            case UP:
                entity.solidArea.y -= entity.speed;
                if(entity.solidArea.intersects(gp.player.solidArea)){
                    entity.collisionOn = true;
                    contact = true;
                }
                break;
            case DOWN:
                entity.solidArea.y += entity.speed;
                if(entity.solidArea.intersects(gp.player.solidArea)){
                    entity.collisionOn = true;
                    contact = true;
                }
                break;
            case LEFT:
                entity.solidArea.x -= entity.speed;
                if(entity.solidArea.intersects(gp.player.solidArea)){
                    entity.collisionOn = true;
                    contact = true;
                }
                break;
            case RIGHT:
                entity.solidArea.x += entity.speed;
                if(entity.solidArea.intersects(gp.player.solidArea)){
                    entity.collisionOn = true;
                    contact = true;
                }
                break;
        }
        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        return contact;
    }
}
