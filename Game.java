public class Game {
/* initialize state, makes a board, decides who's turn is it, call the algorithms
implement these class?
MINIMAX
MINIMAX with alpha beta..
H-MINIMAX with alpha beta..
UTILITY (decides who wins)
 */
 
    int minimax(Board b) {
        //count number of x's and o's to see if player wins at the end
        if (terminalTest(b) == true) {
            return utility(b);
        } else if (b.aiTurn == true) { // if it is the ai's turn return max
            
        } else { // human's turn return min

        }
        return 0;
    }

    boolean terminalTest(Board b) {
        // also test for if both players can do legal moves
        for (int i = 0; i<b.size; i++) { //get < size
            for (int j =0; j<b.size; j++){
                if (b.gameBoard[i][j] != '-') { // see if whole board is x's and o's
                    return false;
                }
            }
        }
        return true;
    }
 
    int utility(Board b) {
        int xCount = 0;
        int oCount = 0;
        for (int i = 0; i<b.size; i++) {
            for (int j =0; j<b.size; j++){
                if (b.gameBoard[i][j] == 'x') {
                    xCount++;
                } else {
                    oCount++;
                }
            }
        }
        if(b.getPlayer() == 'o') { // if ai is x, then if there are more x's on board, ai has won, so return 1
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
