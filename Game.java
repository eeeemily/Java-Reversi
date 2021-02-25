import java.util.Random;

public class Game {

    public Action temp_action;
    public char ai_turn;
    public char player_turn;

    // assuming black always move first
    public String minimax_decision(Action a, State state, char turn) { // return an action string
        temp_action = a;
        ai_turn = turn;

        int v = Integer.MIN_VALUE;
        String best = null; // GET THE BEST ACTION

        String current;
        for (int i = 0; i < temp_action.numActions; i++) { // for each possible action
            current = temp_action.findActions(state, turn)[i];
            State result_state = Result(state, temp_action.findActions(state, turn)[i]);
            // System.out.println("Temp action initially" + temp_action.findActions(state,
            // turn)[i]);

            // System.out.println("STEP 33333333333");
            int possible = min_value(result_state);
            // System.out.println("POSSIBLE: " + possible);
            // v = Math.max(v, min_value(result_state));
            if (possible > v) {
                v = possible;
                // System.out.println("Tem action inside for loop: " + current);
                best = current;
            }
        }
        // }
        return best;

        // System.out.println("min value" + min_value(state));
        // min_value(state);
    }

    public int max_value(State state) {
        // temp_action.setState(state);
        // temp_action.setWhoseTurn(ai_turn);

        if (terminalTest(state)) {
            return utility(state);
        }
        int v = Integer.MIN_VALUE;
        // state.setPlayer(ai_turn);// for RESULT function to know which piece to put
        for (int i = 0; i < temp_action.numActions; i++) { // for each possible action
            // System.out.println("---------------STEP 8-------------------");

            // System.out.println("possible Actions: " + temp_action.findActions(state,
            // ai_turn)[i]);
            v = Math.max(v, min_value(Result(state, temp_action.findActions(state, ai_turn)[i])));
        }
        return v;
    }

    public State Result(State s, String move) {
        // System.out.println("---------------STEP 2-------------------");
        // s.printState(s.gameState);
        s.updateState(s, ai_turn, move);
        return s;
    }

    public int min_value(State s) {
        // temp_action.setState(s);
        // temp_action.setWhoseTurn(s.getPlayer());

        if (terminalTest(s)) {
            return utility(s);
        }
        int v = Integer.MAX_VALUE;
        // s.setPlayer(player_turn); // for RESULT function to know which piece to put
        // System.out.println("---------------STEP 4-------------------");
        for (int i = 0; i < temp_action.numActions; i++) { // for each possible action

            // System.out.println("---------------STEP 5-------------------");
            // System.out.println("possible Actions: " + temp_action.findActions(s,
            // player_turn)[i]);
            v = Math.min(v, max_value(Result(s, temp_action.findActions(s, ai_turn)[i])));
            // v = Math.min(v, max_value(Result(s, a)));
        }
        return v;
    }

    // public String ALPHA-BETA
    public String RandomAgent(Action move, State s, char turn) {
        System.out.println("--------------RandomAgent Running--------------");
        Random rand = new Random();
        if (move.numActions == 0) {
            return null;
        } else {
            int rand_move = rand.nextInt(move.numActions);
            return move.possibleActionsString[rand_move];
        }
    }

    boolean terminalTest(State b) {
        Action a = new Action();
        // also test for if both players can do legal moves
        if (a.hasPossibleAction(b, 'x') == true || a.hasPossibleAction(b, 'o') == true)
            return false;

        return true;

    }

    int utility(State b) {
        int xCount = 0;
        int oCount = 0;
        for (int i = 0; i < b.size; i++) {
            for (int j = 0; j < b.size; j++) {
                if (b.gameState[i][j] == 'x') {
                    xCount++;
                } else {
                    oCount++;
                }
            }
        }
        if (b.getPlayer() == 'o') { // if ai is x, then if there are more x's on State, ai has won, so return 1
            if (xCount > oCount) {
                return 1;
            } else if (xCount < oCount) {
                return -1;
            } else {
                return 0;
            }
        } else {
            if (oCount > xCount) {
                return 1;
            } else if (oCount < xCount) {
                return -1;
            } else {
                return 0;
            }
        }
    }
}