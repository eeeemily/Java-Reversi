import java.util.Random;
public class Game {
    /*
     * initialize state, makes a board, decides who's turn is it, call the
     * algorithms implement these class? MINIMAX MINIMAX with alpha beta.. H-MINIMAX
     * with alpha beta.. UTILITY (decides who wins)
     */
    // public Game(int choice, char player){
    // if (choice == 1){ //minimax
    // //call minimax
    // }
    // }
    // int minimax(Board b) {
    // //count number of x's and o's to see if player wins at the end
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
    // assuming black always move first
    public String minimax_decision(State state, char turn) { //return an action string
        Action a = new Action(state, turn);
        // System.out.println(a.numFlips);
        // a.printActions();
        temp_action = a;
        ai_turn = turn;

        if (ai_turn == 'x'){
            player_turn = 'o';
        }else {
            player_turn = 'x';
        }

        if (turn == ai_turn) {
            int v = Integer.MIN_VALUE;
            String best = null;
            // a.getAction(state, max_value(state)); // gets the best action
            for (int i = 0; i< temp_action.numActions; i++){ //for each possible action 
                System.out.println("**********STEP222222222");
                State result_state = Result(state, temp_action.possibleActionsString[i]);
                
                int possible = min_value(result_state);

                // v = Math.max(v, min_value(result_state));
                if (possible > v){
                    v = possible;
                    best = temp_action.possibleActionsString[i];
                }
            } 
            return best;
        } else {
            // a.getAction(state, min_value(state));
            min_value(state);
        }
        // System.out.println("min value" + min_value(state));
        // min_value(state);
        return "action";
    }

    public int max_value(State state){
        temp_action.setState(state);
        temp_action.setWhoseTurn(ai_turn);

        System.out.println("**********STEP1111111111");
            if (terminalTest(state)) {
                return utility(state);
            }
            int v = Integer.MIN_VALUE;
            state.setPlayer(ai_turn);// for RESULT function to know which piece to put
            for (int i = 0; i< temp_action.numActions; i++){ //for each possible action 
                System.out.println("**********STEP222222222");
                v = Math.max(v, min_value(Result(state, temp_action.possibleActionsString[i])));
            } 
            return v;
    }
    
    public State Result(State s, String move) {
        // s.updateState(s, s.getPlayer(), move);
        System.out.println("**********STEP3333333");
        return s;
    }

    // public State plResult(State s, String move) {
    //     s.updateState(s, s.getPlayer(), move);
    //     System.out.println("**********Hello222222");
    //     return s;
    // }
    public int min_value(State s){
        temp_action.setState(s);
        temp_action.setWhoseTurn(s.getPlayer());

        if (terminalTest(s)) {
            return utility(s);
        }
        int v = Integer.MAX_VALUE;
        s.setPlayer(player_turn); // for RESULT function to know which piece to put
        for (int i = 0; i< temp_action.numActions; i++){ //for each possible action 
            System.out.println("**********STEP4444444444");
            v = Math.min(v, max_value(Result(s, temp_action.possibleActionsString[i])));
            // v = Math.min(v, max_value(Result(s, a)));
        }
        return v;
    }

    public void RandomAgent(State s, char turn) {
        Action move = new Action(s, turn);
        Random rand = new Random();
        if(move.numActions == 0){
            System.out.println("No moves.");
        }else {
            int rand_move = rand.nextInt(move.numActions);
            s.updateState(s, turn, move.possibleActionsString[rand_move]);
        }
    }

    boolean terminalTest(State b) {
        // also test for if both players can do legal moves
        for (int i = 0; i < b.size; i++) { // get < size
            for (int j = 0; j < b.size; j++) {
                if (b.gameState[i][j] != '-') { // see if whole State is x's and o's
                    return false;
                }
            }
        }
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
