package groupOne.game;

import java.util.ArrayList;

/**
 * Pathfinder class for enemies to track the player on the board using the A* search algorithm
 */
public class Pathfinder {
    GamePanel gp;
    Node[][] node;
    ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> pathList = new ArrayList<>();
    Node startNode, goalNode, currentNode;
    boolean goalReached = false;
    int step = 0;

    public Pathfinder(GamePanel gp){
        this.gp = gp;
        instantiateNodes();
    }

    // Create node for every tile on the map
    public void instantiateNodes(){
        node = new Node[gp.maxScreenCol][gp.maxScreenRow];

        int col = 0;
        int row = 0;

        while(col < gp.maxScreenCol && row < gp.maxScreenRow){
            node[col][row] = new Node(col, row);

            col++;
            if(col == gp.maxScreenCol){
                col = 0;
                row++;
            }
        }
    }

    // During the game, we are calling the algorithm repeatedly
    // So we need to reset our results 
    public void resetNodes(){

        int col = 0;
        int row = 0;

        while(col < gp.maxScreenCol && row < gp.maxScreenRow){
            node[col][row].open = false;
            node[col][row].checked = false;
            node[col][row].solid = false;

            col++;
            if(col == gp.maxScreenCol){
                col = 0;
                row++;
            }
        }

        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }

    public void setNodes(int startCol, int startRow, int goalCol, int goalRow){
        resetNodes();

        // Set Start and Goal nodes
        startNode = node[startCol][startRow];
        currentNode = startNode;
        goalNode = node[goalCol][goalRow];
        openList.add(currentNode);

        int col = 0;
        int row = 0;

        while(col < gp.maxScreenCol && row < gp.maxScreenRow){
            // SET SOLID NODE
            // CHECK TILES
            int tileNum = gp.tileM.mapTileNum[gp.currentMap][col][row];
            if(gp.tileM.tile[tileNum].collision == true){
                node[col][row].solid = true;
            }

            // SET COST
            getCost(node[col][row]);

            col++;
            if(col == gp.maxScreenCol){
                col = 0;
                row++;
            }

        }
    }

    public void getCost(Node node){
        // G Cost
        int xDist = Math.abs(node.col - startNode.col);
        int yDist = Math.abs(node.row - startNode.row);
        node.gCost = xDist + yDist;

        // H Cost
        xDist = Math.abs(node.col - goalNode.col);
        yDist = Math.abs(node.row - goalNode.row);
        node.hCost = xDist + yDist;

        // F Cost
        node.fCost = node.gCost + node.hCost;
    }

    public boolean search(){
        while(goalReached == false && step < 500){
            int col = currentNode.col;
            int row = currentNode.row;

            // Check current node
            currentNode.checked = true;
            openList.remove(currentNode);

            // Open the up node
            if(row - 1 >= 0){
                openNode(node[col][row-1]);
            }

            // Open the left node
            if(col - 1 >= 0){
                openNode(node[col-1][row]);
            }

            // Open the down node
            if(row + 1 < gp.maxScreenRow){
                openNode(node[col][row+1]);
            }

            // Open the right node
            if(col + 1 < gp.maxScreenCol){
                openNode(node[col+1][row]);
            }

            // Find the best node
            int bestNodeIndex = 0;
            int bestNodefCost = 999;
            for(int i = 0; i < openList.size(); i++){

                // Check if this node's F Cost is better
                if(openList.get(i).fCost < bestNodefCost){
                    bestNodeIndex = i;
                    bestNodefCost = openList.get(i).fCost;
                }

                // If F cost is equal, check G cost
                else if(openList.get(i).fCost == bestNodefCost){
                    if(openList.get(i).gCost < openList.get(bestNodeIndex).gCost){
                        bestNodeIndex = i;
                    }
                }
            }

            // If there is no node in the openList, end the loop
            if(openList.size() == 0){
                break;
            }

            // After the loop, openList[bestNodeIndex] is the next step ( = currentNode)
            currentNode = openList.get(bestNodeIndex);

            if(currentNode == goalNode){
                goalReached = true;
                trackThePath();
            }
            step++;
        }

        return goalReached;
    }

    public void openNode(Node node){
        if(node.open == false && node.checked == false && node.solid == false){
            node.open = true;
            node.parent = currentNode;
            openList.add(node);
        }
    }

    public void trackThePath(){
        Node current = goalNode;

        while(current != startNode){
            pathList.add(0, current);
            current = current.parent;
        }
    }
}
