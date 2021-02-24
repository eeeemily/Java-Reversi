import java.util.Random;

public class Game {
    /*
     * initialize state, makes a board, decides who's turn is it, call the
     * algorithms implement these class? MINIMAX MINIMAX with alpha beta.. H-MINIMAX
     * with alpha beta.. UTILITY (decides who wins)
     */

    // int minimax(Board b) {
    // // count number of x's and o's to see if player wins at the end
    // if (terminalTest(b) == true) {
    // return utility(b);
    // } else if (b.aiTurn == true) { // if it is the ai's turn return max

    // } else { // human's turn return min

    // }
    // return 0;
    // }

    public Action temp_action;
    public char ai_turn;
    public char player_turn;

    public char aiColor;
    public char playerColor;

    public Game(char aiColor) {
        this.aiColor = aiColor;
        playerColor = (aiColor != 'x') ? 'x' : 'o';
    }

    // assuming black always move first
    public String minimax_decision(State state, char turn) { // return an action string
        temp_action = new Action();
        ai_turn = turn;

        if (ai_turn == 'x') {
            player_turn = 'o';
        } else {
            player_turn = 'x';
        }

        // if (turn == ai_turn) {
        int v = Integer.MIN_VALUE;
        String best = null;
        // a.getAction(state, max_value(state)); // gets the best action
        System.out.println("---------------STEP 1-------------------");
        // System.out.println("NUM OF ACTIONS: " + temp_action.numActions);
        for (int i = 0; i < temp_action.getNumActions(state, turn); i++) { // for each possible action
            State result_state = Result(state, temp_action.findActions(state, turn)[i]);
            System.out.println("GOT OUTTA RESULT_STATE LINE 53");
            int possible = min_value(result_state);
            System.out.println("POSSIBLE: " + possible);
            // v = Math.max(v, min_value(result_state));
            if (possible > v) {
                v = possible;
                best = temp_action.findActions(state, turn)[i];
            }
        }
        return best;
        // } else {
        // // a.getAction(state, min_value(state));
        // min_value(state);
        // }
        // System.out.println("min value" + min_value(state));
        // min_value(state);
        // return "action";
    }

    public int max_value(State state) {
        // temp_action.setState(state);
        // temp_action.setWhoseTurn(ai_turn);

        if (terminalTest(state)) {
            System.out.println("Max_Value: doing terminal test");
            return utility(state);
        }
        // System.out.println("---------------STEP 7-------------------");
        int v = Integer.MIN_VALUE;
        // state.setPlayer(ai_turn);// for RESULT function to know which piece to put
        int maxNumAction = temp_action.getNumActions(state, ai_turn);
        String[] maxActions = temp_action.findActions(state, ai_turn);
        for (int i = 0; i < maxNumAction; i++) { // for each possible action
            // System.out.println("---------------STEP 8-------------------");
            v = Math.max(v, min_value(Result(state, maxActions[i])));
        }
        return v;
    }

    public State Result(State s, String move) {
        // System.out.println("---------------STEP 2-------------------");
        State newState = s.updateState(s, ai_turn, move);
        if (s.compareState(s, newState)) {
            return s;
        } else {
            return newState;
        }
    }

    public State plResult(State s, String move) {
        // System.out.println("---------------STEP 6-------------------");
        s.updateState(s, player_turn, move);
        return s;
    }

    public int min_value(State s) {
        // temp_action.setState(s);
        // temp_action.setWhoseTurn(s.getPlayer());

        if (terminalTest(s)) {
            System.out.println("Max_Value: doing terminal test");
            return utility(s);
        }
        int v = Integer.MAX_VALUE;
        // s.setPlayer(player_turn); // for RESULT function to know which piece to put
        // System.out.println("---------------STEP 4-------------------");
        int minNumAction = temp_action.getNumActions(s, player_turn);
        String[] minActions = temp_action.findActions(s, player_turn);
        for (int i = 0; i < minNumAction; i++) { // for each possible action
            // System.out.println("---------------STEP 5-------------------");
            v = Math.min(v, max_value(plResult(s, minActions[i])));
            // v = Math.min(v, max_value(Result(s, a)));
        }
        return v;
    }

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
        // for (int i = 0; i < b.size; i++) { // get < size
        // for (int j = 0; j < b.size; j++) {
        // if (b.gameState[i][j] == '-') { // see if whole State is x's and o's
        // return true;
        // }
        // }
        // }
        if (a.hasPossibleAction(b, 'x') || a.hasPossibleAction(b, 'o'))
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
        if (player_turn == 'o') { // if ai is x, then if there are more x's on State, ai has won, so return 1
            if (xCount > oCount) {
                return 1;
            } else if (xCount < oCount) {
                return -1;
            } else {
                return 0;
            }
        } else { // else, if ai is o, we want o to have more pieces than x
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