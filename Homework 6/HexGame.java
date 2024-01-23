import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class HexGame {
    static int RIGHT = 121;
    static int LEFT = 122;
    static int TOP = 123;
    static int BOTTOM = 124;
    private static int[] getNeighbors(int item){

        int[] neighbors = new int[6];
        neighbors[0] = item - 11; // top left
        neighbors[1] = item - 10; // top right
        neighbors[2] = item - 1; // left
        neighbors[3] = item + 1; // right
        neighbors[4] = item + 10; // bottom left
        neighbors[5] = item + 11; // bottom right
        if (item <= 10) {
            neighbors[0] = TOP;
            neighbors[1] = TOP;
        }
        if (item % 11 == 10) {
            neighbors[1] = RIGHT;
            neighbors[3] = RIGHT;
        }
        if(item % 11 == 0) {
            neighbors[2] = LEFT;
            neighbors[4] = LEFT;
        }
        if (item >= 109) {
            neighbors[4] = BOTTOM;
            neighbors[5] = BOTTOM;
        }
        return neighbors;
    }

    public static void moves(String filename){
        int SIZE = 125;
        String[] ownerStorage = new String[SIZE];
        UnionFind connectivityStorage = new UnionFind(SIZE);
        Arrays.fill(ownerStorage, "NONE");
        try {
            File movesText = new File(filename);
            Scanner myReader = new Scanner(movesText);
            ownerStorage[RIGHT] = "BLUE";
            ownerStorage[LEFT] = "BLUE";
            ownerStorage[TOP] = "RED";
            ownerStorage[BOTTOM] = "RED";
            int moveCount = 0;
            while (myReader.hasNext()){
                moveCount += 1;
                int currMove = Integer.parseInt(myReader.next()) - 1;
                if (moveCount % 2 == 0){ // assign colors
                    ownerStorage[currMove] = "RED";
                } else {
                    ownerStorage[currMove] = "BLUE";
                }

                int[] neighbors = getNeighbors(currMove);
                for (int i = 0; i < neighbors.length; i++){ // for every neighbor
                    if (Objects.equals(ownerStorage[neighbors[i]], ownerStorage[currMove])){ // if the neighbor color equals the currentmove color
                        connectivityStorage.union(neighbors[i], currMove); // union them
                    }
                }
                if (connectivityStorage.find(TOP) == connectivityStorage.find(BOTTOM)){
                    System.out.println("RED HAS WON IN " + moveCount + " ATTEMPTED MOVES!! HERE'S THE BOARD:");
                    break;
                } else if (connectivityStorage.find(LEFT) == connectivityStorage.find(RIGHT)){
                    System.out.println("BLUE HAS WON IN " + moveCount + " ATTEMPTED MOVES!! HERE'S THE BOARD:");
                    break;
                }
            }

            String indent = " ";
            for (int i = 0; i < SIZE-4; i++) {  // prints out the board
                    if (Objects.equals(ownerStorage[i], "BLUE")){
                        System.out.print(ANSI_BLUE + "B" + ANSI_RESET + " ");
                    } else if (Objects.equals(ownerStorage[i], "RED")){
                        System.out.print(ANSI_RED + "R" + ANSI_RESET + " ");
                    } else {
                        System.out.print(0 + " ");
                    }
                    if (i % 11 == 10) {
                        System.out.println();
                        System.out.print(indent);
                        indent += " ";
                    }
            }
            System.out.println();
            myReader.close();
        } catch (FileNotFoundException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static final String ANSI_RED="\u001B[31m";
    public static final String ANSI_RESET="\u001B[0m";
    public static final String ANSI_BLUE="\u001B[34m";
    public static void main(String[] args) {
        moves("moves.txt");
        moves("moves2.txt");
    }
}
