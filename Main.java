import java.util.*;

public class Main {
    static char player;
    static char aiColor;
    static int boardChoice;
    static int size;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Choose your game: \n 1. Small 4x4 Reversi \n 2. Standard 8x8 Reversi");
        // int boardChoice = scan.nextInt();
        boardChoice = 1; // for testing

        if (boardChoice == 1) {
            size = 4;
        } else {
            size = 8;
        }

        // System.out.println("Choose your opponent: \n 1. An agent that use MINIMAX \n
        // 2. An agent that use MINIMAX with Alpha-beta pruning \n 3. An agent that uses
        // H-MINIMAX with a fixed depth cutoff and a-b pruning \n Your choice?");
        // int opponent = scan.nextInt();
        /* each choice lead to a different opponent */
        System.out.println("Do you want to play DARK (x) or LIGHT (o)?");
        // char player = scan.next().charAt(0);
        char turn = 'x';
        player = 'x'; // for testing
        aiColor = (player != 'x') ? 'x' : 'o';

        /* prints board */
        State b = new State(size, player);
        b.printState(b.gameState);

        Action move = new Action(); // get board info?
        // Action move = new Action(b, b.ai);
        Game g = new Game(aiColor); // initialize game

        // if ((b.ai == 'x') && (move.numActions != 0)) { // AI MOVES FIRST
        // // g.minimax_decision(b);
        // } else { // PLAYER MOVES FIRST
        while (move.hasPossibleAction(b, turn) == true) {
            System.out.println("-----------------------------");
            System.out.print(player + " move: (? for help): ");
            String location;
            if (turn == player) { // if it's player's turn
                location = scan.next();
                if (location.equals("?"))
                    move.printActions(b, player);
                while (!move.isPossibleAction(b, player, location)) {
                    System.out.println("please enter one of the possible moves: ");
                    // System.out.println("move is possible?: " + move.isPossibleAction(location));
                    move.printActions(b, player);
                    location = scan.next();
                }
            } else {
                // location = g.RandomAgent(move, b, 'o');
                location = g.minimax_decision(b, turn);
                if (location == null) {
                    System.out.println("No possible moves for " + player);
                }
            }
            b = b.updateState(b, player, location);
            turn = (turn == 'x') ? 'o' : 'x';
        }
        scan.close();

    }
    // }

}
