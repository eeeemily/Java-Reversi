import java.util.*;

public class Main {
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        System.out.println("Choose your game: \n 1. Small 4x4 Reversi \n 2. Standard 8x8 Reversi");
        int boardChoice = scan.nextInt();
        int size;
        if (boardChoice == 1){
            size = 4;
        }else {
            size = 8;
        }
        
        // System.out.println("Choose your opponent: \n 1. An agent that use MINIMAX \n 2. An agent that use MINIMAX with Alpha-beta pruning \n 3. An agent that uses H-MINIMAX with a fixed depth cutoff and a-b pruning \n Your choice?");
        // int opponent = scan.nextInt();
        /* each choice lead to a different opponent */
        System.out.println("Do you want to play DARK (x) or LIGHT (o)?");
        // char player = scan.next().charAt(0);
        char player = 'x'; //for testing
        
        /* prints board */
        Board b = new Board(size, player);
        b.printBoard(b.gameBoard);

        scan.close();
    }
}
