public class Action {
    State currentState;
    int boardSize;
    // char [][] possibleActions;
    int[] possibleActionsI; // can be removed?
    int[] possibleActionsJ;
    String[] possibleActionsString; // Emily: maybe we should stick to this String array?
    int[] numFlips;
    String[] flippable;
    int flippablePieces;
    int numActions; // posible
    boolean xTurn;
    boolean oTurn;
    char opponentColor;
    char currentColor;
    int north, west, south, east, nw, ne, sw, se; // number of opponent pieces can be flipped in each of the 8
                                                  // directions; actually, might just loop through each cell agin and
                                                  // substract from current state; nvm, lemme still do this way

    public Action(State s, char whoseTurn) {
        currentState = s;
        possibleActionsI = new int[64];
        possibleActionsJ = new int[64];
        possibleActionsString = new String[64];
        numActions = 0;
        numFlips = new int[64]; // how many pieces can one move flip
        boardSize = s.getSize();
        north = west = south = east = nw = ne = sw = se = 0;

        // // for func flip
        flippable = new String[64]; // put all flippeable pieces in a array
        flippablePieces = 0; // count how many pieces can be flipped

        // see who's making a move at the given state s
        if (whoseTurn == 'x') {
            xTurn = true;
            oTurn = false;
            opponentColor = 'o';
            currentColor = 'x';
        } else if (whoseTurn == 'o') {
            xTurn = false;
            oTurn = true;
            opponentColor = 'x';
            currentColor = 'o';
        } else {
            System.out.println("illegeal input: who's turn is it?? " + whoseTurn);
        }

        // public Action getAction(State s, int value){
        // }

        // s[i][j] goes through each cell on the board
        for (int i = 0; i < boardSize; i++) { // get < size
            for (int j = 0; j < boardSize; j++) {
                north = west = south = east = nw = ne = sw = se = 0; // reset
                // filter 1: find the empty spot
                if (s.gameState[i][j] == '-') { // see if whole board is x's and o's
                    // filter 2: do neighbor test to see if the current spot has any of the opposite
                    if (neighborTest(s, i, j)) {
                        numFlips[numActions] = north + south + west + east + nw + ne + sw + se;
                        addActions(i, j);
                    }
                }
            }
        }
    }

    public void addActions(int i, int j) {
        possibleActionsI[numActions] = i;
        possibleActionsJ[numActions] = j;
        possibleActionsString[numActions] = "" + State.getCharRow(i) + (j + 1);
        numActions += 1;
    }

    public void printActions() {
        System.out.println("Current player: " + currentColor);
        System.out.println("Total Legal Moves: " + numActions);
        for (int i = 0; i < numActions; i++) {
            System.out.print("The " + (i + 1) + " possible action is: ");
            // System.out.print(State.getCharRow(possibleActionsI[i]));
            // System.out.println((possibleActionsJ[i] + 1));
            System.out.print(possibleActionsString[i]);
            System.out.println(";   Number of Pieces can be flipped in this move: " + numFlips[i]);

        }
    }

    // check if the input(string like a4) is a possible move
    public boolean isPossibleAction(String s) {
        for (int i = 0; i < numActions; i++) {
            if (State.getCharRow(possibleActionsI[i]) == (s.charAt(0))) {
                if ((possibleActionsJ[i] + 1) == Character.getNumericValue(s.charAt(1))) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasPossibleAction(State s, char curPlayer) {
        Action a = new Action(s, curPlayer);
        return (a.numActions == 0) ? false : true; // if numActions=0, return false; else, there's possible moves
    }

    public boolean noPossibleAction(State s, char curPlayer) {
        Action a = new Action(s, curPlayer);
        return (a.numActions == 0) ? true : false; // if numActions=0, return true; else, there's no possible moves
    }
    // public Action getAction(State state, int value){

    // }

    public State flip(State s, String move, char curPlayer) {
        int i = State.getCol(Character.getNumericValue(move.charAt(1)));
        int j = State.getRow(move.charAt(0));
        // for func flip
        flippable = new String[64]; // put all flippeable pieces in a array
        flippablePieces = 0; // count how many pieces can be flipped
        currentColor = curPlayer;
        opponentColor = (curPlayer == 'x') ? 'o' : 'x';
        System.out.println("\n********\n Flip Function");
        System.out.println("Original State: ");
        s.printState(s.gameState);
        neighborTest(s, i, j);
        for (int a = 0; a < flippablePieces; a++) {
            String flips = flippable[a];
            s.gameState[State.getCol(Character.getNumericValue(flips.charAt(1)))][State
                    .getRow(flips.charAt(0))] = currentColor;
        }
        System.out.println("Flipped State: ");
        s.printState(s.gameState);
        return s;
    }

    public boolean neighborTest(State state, int i, int j) {
        /*
         * if any of the 8 directions pass the test, then it is a legal move NW | N | NE
         * W | * | E SW | S | SE
         */
        return North(state, i, j) || South(state, i, j) || West(state, i, j) || East(state, i, j) || NW(state, i, j)
                || NE(state, i, j) || SW(state, i, j) || SE(state, i, j);
    }

    // whose turn

    public boolean North(State state, int i, int j) {
        // top: i[0 to i-2]; j stays
        if (i == 0 || i == 1)
            return false;

        // if the cell on top of the current one is opponent's Color, continue the test
        if (state.gameState[(i - 1)][j] == opponentColor) {
            // if there's a cell of the current color again, it is a legal move
            flippable[flippablePieces] = "" + State.getCharRow(i - 1) + (j + 1);
            flippablePieces += 1;
            for (int a = (i - 2); a >= 0; a--) {
                if (state.gameState[a][j] == '-') {
                    flippablePieces -= 1;
                    return false;
                }
                if (state.gameState[a][j] == currentColor) {
                    north += 1;
                    flippable[flippablePieces] = "" + State.getCharRow(a) + (j + 1);
                    flippablePieces += 1;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean South(State state, int i, int j) {
        // top: i[i+2 to boarderSize]?; j stays
        if (i == boardSize || i == boardSize - 1)
            return false;

        // if the cell on top of the current one is opponent's Color, continue the test
        if (state.gameState[(i + 1)][j] == opponentColor) {
            flippable[flippablePieces] = "" + State.getCharRow(i + 1) + (j + 1);
            flippablePieces += 1;
            // if there's a cell of the current color again, it is a legal move
            for (int a = (i + 2); a <= boardSize; a++) {
                if (state.gameState[a][j] == '-') {
                    flippablePieces -= 1;
                    return false;
                }
                if (state.gameState[a][j] == currentColor) {
                    south += 1;
                    flippable[flippablePieces] = "" + State.getCharRow(a) + (j + 1);
                    flippablePieces += 1;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean West(State state, int i, int j) {
        // top: i stays; j[0 to i-2]
        if (j == 0 || j == 1)
            return false;

        // if the cell on top of the current one is opponent's Color, continue the test
        if (state.gameState[i][j - 1] == opponentColor) {
            // if there's a cell of the current color again, it is a legal move
            flippable[flippablePieces] = "" + State.getCharRow(i) + (j);
            flippablePieces += 1;
            for (int a = (j - 2); a >= 0; a--) {
                if (state.gameState[i][a] == '-') {
                    flippablePieces -= 1;
                    return false;
                }
                if (state.gameState[i][a] == currentColor) {
                    flippable[flippablePieces] = "" + State.getCharRow(i) + (a + 1);
                    flippablePieces += 1;
                    west += 1;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean East(State state, int i, int j) {
        // top: i; j [j+2 to boarderSize]
        if (j == boardSize || j == boardSize - 1)
            return false;

        // if the cell on top of the current one is opponent's Color, continue the test
        if (state.gameState[(i)][j + 1] == opponentColor) {
            // if there's a cell of the current color again, it is a legal move
            flippable[flippablePieces] = "" + State.getCharRow(i) + (j + 2);
            flippablePieces += 1;
            for (int a = (j + 2); a <= boardSize; a++) {
                if (state.gameState[i][a] == '-') {
                    flippablePieces -= 1;
                    return false;
                }
                if (state.gameState[i][a] == currentColor) {
                    flippable[flippablePieces] = "" + State.getCharRow(i) + (a + 1);
                    flippablePieces += 1;
                    east += 1;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean NW(State state, int i, int j) {
        // diagonal, i=j; otherwise, the spot is not at the diagonal position
        if (i != j) {
            return false;
        }
        //
        if (i == 0 || i == 1)
            return false;

        // if the cell on top of the current one is opponent's Color, continue the test
        if (state.gameState[i - 1][i - 1] == opponentColor) {
            flippable[flippablePieces] = "" + State.getCharRow(i - 1) + (i);
            flippablePieces += 1;
            // if there's a cell of the current color again, it is a legal move
            for (int a = (i - 2); a >= 0; a--) {
                if (state.gameState[a][a] == '-') {
                    flippablePieces -= 1;
                    return false;
                }
                if (state.gameState[a][a] == currentColor) {
                    flippable[flippablePieces] = "" + State.getCharRow(a) + (a + 1);
                    flippablePieces += 1;
                    nw += 1;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean SE(State state, int i, int j) {
        // diagonal, i=j; otherwise, the spot is not at the diagonal position
        if (i != j) {
            return false;
        }
        //
        if (i == boardSize || i == boardSize - 1)
            return false;

        // if the cell on top of the current one is opponent's Color, continue the test
        if (state.gameState[j + 1][j + 1] == opponentColor) {
            flippable[flippablePieces] = "" + State.getCharRow(j + 1) + (j + 2);
            flippablePieces += 1;
            // if there's a cell of the current color again, it is a legal move
            for (int a = (j + 2); a <= boardSize; a++) {
                if (state.gameState[a][a] == '-') {
                    flippablePieces -= 1;
                    return false;
                }
                if (state.gameState[a][a] == currentColor) {
                    flippable[flippablePieces] = "" + State.getCharRow(a) + (a + 1);
                    flippablePieces += 1;
                    ne += 1;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean NE(State state, int i, int j) {
        // diagonal, i+j=boardSize, otherwise, the spot is not at the diagonal position
        if ((i + j + 1) != boardSize) {
            return false;
        }
        //
        if (i == 0 || i == 1)
            return false;

        // if the cell on top of the current one is opponent's Color, continue the test
        if (state.gameState[(i - 1)][boardSize - i + 1] == opponentColor) {
            flippable[flippablePieces] = "" + State.getCharRow(i - 1) + (boardSize - i + 2);
            flippablePieces += 1;
            // if there's a cell of the current color again, it is a legal move
            for (int a = (i - 2); a >= 0; a--) {
                if (state.gameState[a][boardSize - a] == '-') {
                    flippablePieces -= 1;
                    return false;
                }
                if (state.gameState[a][boardSize - a] == currentColor) {
                    flippable[flippablePieces] = "" + State.getCharRow(a) + (boardSize - a + 1);
                    flippablePieces += 1;
                    ne += 1;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean SW(State state, int i, int j) {
        // diagonal, i+j=boardSize; otherwise, the spot is not at the diagonal position
        if ((i + j + 1) != boardSize) {

            return false;
        }
        //
        if (i == boardSize || i == boardSize - 1)
            return false;

        // if the cell on top of the current one is opponent's Color, continue the test
        if (state.gameState[(i + 1)][boardSize - i - 1] == opponentColor) {
            flippable[flippablePieces] = "" + State.getCharRow(i + 1) + (boardSize - i);
            flippablePieces += 1;
            // if there's a cell of the current color again, it is a legal move
            for (int a = (i + 2); a <= boardSize; a++) {
                if (state.gameState[a][boardSize - a] == '-') {
                    flippablePieces -= 1;
                    return false;
                }
                if (state.gameState[a][boardSize - a] == currentColor) {
                    flippable[flippablePieces] = "" + State.getCharRow(a) + (boardSize - a + 1);
                    flippablePieces += 1;
                    sw += 1;
                    return true;
                }
            }
        }
        return false;
    }

}
