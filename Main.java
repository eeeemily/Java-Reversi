import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Choose your game: \n 1. Small 4x4 Reversi \n 2. Standard 8x8 Reversi");
        // int boardChoice = scan.nextInt();
        int boardChoice = 1; // for testing
        int size;
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
        char player = 'x'; // for testing

        /* prints board */
        State b = new State(size, player);
        b.printState(b.gameState);

        Action move = new Action(b, player); // initialize player action
        // Action move = new Action(b, b.ai);
        Game g = new Game(); // initialize game

        if ((b.ai == 'x') && (move.numActions != 0)) {
            // g.minimax_decision(b);
        } else {
            System.out.println("Your move (? for help): ");
            String location = scan.next();

            if (location.equals("?")) {
                move.printActions();
            } else {
                // 1. x
                // System.out.println("location is " + move.isPossibleAction(location));
                // while (!move.isPossibleAction(location)) {
                // System.out.println("please enter one of the possible moves: ");
                // move.printActions();
                // }
                // b = b.updateState(b, 'x', location);

                // // 2.o
                // System.out.println("o move: ");
                // move = new Action(b, 'o');
                // while (location.length() != 2) {
                // System.out.println("illegal Entry, please enter a location on board: (e.g.
                // a2)");
                // }
                // while (!move.isPossibleAction(location)) {
                // System.out.println("please enter one of the possible moves: ");
                // System.out.println(location);
                // System.out.println(move.isPossibleAction(location));
                // move.printActions();
                // location = scan.next();
                // }
                // b = b.updateState(b, 'o', location);

                // // 3.x
                // System.out.println("x move: ");
                // move = new Action(b, 'x');
                // location = scan.next();
                // while (!move.isPossibleAction(location)) {
                // System.out.println("please enter one of the possible moves: ");
                // move.printActions();
                // location = scan.next();
                // }
                // b = b.updateState(b, 'x', location);

                // // 4.o
                // System.out.println("o move: ");
                // move = new Action(b, 'o');
                // location = scan.next();
                // // while (!move.isPossibleAction(location)) {
                // System.out.println("please enter one of the possible moves: ");
                // move.printActions();
                // location = scan.next();
                // // }
                // b = b.updateState(b, 'o', location);
                b = b.updateState(b, 'x', location);
                g.minimax_decision(b, 'o'); // 'o' is AI's role

            }
        }

        scan.close();
    }
}
