public class Action {
    State state;
    int boardSize;
    // char [][] possibleActions;
    // int[] possibleActionsI; // can be removed?
    // int[] possibleActionsJ;
    int numActions; // posible
    String[] possibleActionsString; // Emily: maybe we should stick to this String array?

    // int[] numFlips;
    String[] flippable;
    int flippablePieces;

    boolean xTurn;
    boolean oTurn;
    char whoseTurn;
    char opponentColor;
    char currentColor;
    int count;

    public Action() {
        possibleActionsString = new String[64];
        numActions = 0;

        // // for func flip
        flippable = new String[1000]; // put all flippeable pieces in a array
        flippablePieces = 0; // count how many pieces can be flipped
    }

    public String[] findActions(State state, char curPlayer) {
        possibleActionsString = new String[64];
        numActions = 0;
        boardSize = state.getSize();
        currentColor = curPlayer;
        opponentColor = (curPlayer == 'x') ? 'o' : 'x';
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                // filter 1: find the empty spot
                if (state.gameState[i][j] == '-') { // see if whole board is x's and o's
                    // filter 2: do neighbor test to see if the current spot has any of the opposite
                    String move = convertToMove(i, j);
                    if (neighborTest(state, move)) {
                        addActions(move);
                    }
                }
            }
        }
        return possibleActionsString;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public char getWhoseTurn() {
        return whoseTurn;
    }

    public void setWhoseTurn(char whoseTurn) {
        this.whoseTurn = whoseTurn;
    }

    public void addActions(String action) {
        // possibleActionsI[numActions] = i;
        // possibleActionsJ[numActions] = j;
        possibleActionsString[numActions] = action;
        numActions += 1;
    }

    public void printActions(State s, char curPlayer) {
        findActions(s, curPlayer);
        System.out.print("Current player: " + currentColor);
        System.out.println(" has the following legal moves: " + numActions);
        for (int i = 0; i < numActions; i++) {
            System.out.print("The " + (i + 1) + " possible action is: ");
            System.out.println(possibleActionsString[i]);
        }
    }

    public boolean isPossibleAction(State s, char curPlayer, String move) {
        findActions(s, curPlayer);
        for (int i = 0; i < numActions; i++) {
            if ((possibleActionsString[i]).equals(move)) {
                System.out.println(move + " is a possible move at current state for Player " + curPlayer);
                return true;
            }
        }
        System.out.println(move + " is a NOT possible move at current state for Player " + curPlayer);
        return false;
    }

    public boolean hasPossibleAction(State s, char curPlayer) {
        findActions(s, curPlayer);
        return (numActions == 0) ? false : true; // if numActions=0, return false; else, there's possible moves
    }

    public boolean noPossibleAction(State s, char curPlayer) {
        findActions(s, curPlayer);
        return (numActions == 0) ? true : false; // if numActions=0, return true; else, there's no possible moves
    }

    public int[] convertToBoard(String a) {
        int[] index = new int[2];
        index[0] = Character.getNumericValue(a.charAt(1)) - 1;
        index[1] = Character.getNumericValue(a.charAt(0)) - 10;
        // System.out.println("CONVERTING TO BOARD: e.g. a3->2,0");
        // System.out.println(a + " -> " + index[0] + "," + index[1]);
        return index;
    }

    public String convertToMove(int i, int j) {
        return "" + State.getCharRow(j) + (i + 1);
    }

    public State addMove(State s, String move, char CurPlayer) {
        int[] m = convertToBoard(move);
        s.gameState[m[0]][m[1]] = CurPlayer;
        System.out.println("adding a Move: " + move);
        return s;
    }

    public State flip(State s, String move, char curPlayer) {
        boardSize = s.getSize();
        s = addMove(s, move, curPlayer);
        // for func flip

        currentColor = curPlayer;
        opponentColor = (curPlayer == 'x') ? 'o' : 'x';

        System.out.println("******** Flip Function***********");
        neighborTest(s, move);
        System.out.println("fiona test here: ");
        for (int a = 0; a < flippablePieces; a++) {
            System.out.print(flippable[a]);
        }
        for (int a = 0; a < flippablePieces; a++) {
            String flips = flippable[a];
            int[] convertIJ = convertToBoard(flips);
            s.gameState[convertIJ[0]][convertIJ[1]] = currentColor;
            System.out.println("flipping: " + flippable[a]);
        }
        s.printState(s.gameState);
        System.out.println("flippablePieces is crazy: " + flippablePieces);
        System.out.println("******** End of Flip Function***********");
        return s;
    }

    public boolean neighborTest(State state, String move) {
        /*
         * if any of the 8 directions pass the test, then it is a legal move NW | N | NE
         * W | * | E SW | S | SE
         */
        flippable = new String[64]; // put all flippeable pieces in a array
        flippablePieces = 0; // count how many pieces can be flipped
        North(state, move);
        South(state, move);
        West(state, move);
        East(state, move);
        NW(state, move);
        NE(state, move);
        SW(state, move);
        SE(state, move);
        return North(state, move) || South(state, move) || West(state, move) || East(state, move) || NW(state, move)
        || NE(state, move) || SW(state, move) || SE(state, move);        
    }

    public boolean North(State state, String move) {
        int[] ij = convertToBoard(move);
        int i = ij[0];
        int j = ij[1];
        // top: i[0 to i-2]; j stays
        if (i == 0 || i == 1)
            return false;

        // if the cell on top of the current one is opponent's Color, continue the test
        if (state.gameState[(i - 1)][j] == opponentColor) {
            // if there's a cell of the current color again, it is a legal move
            flippable[flippablePieces] = convertToMove((i - 1), j);
            int tempFlip = 1;
            flippablePieces += tempFlip;
            for (int a = (i - 2); a >= 0; a--) {
                if (a < 0 || j < 0) {
                    flippablePieces -= tempFlip;
                    return false;
                } else if (state.gameState[a][j] == opponentColor) {
                    flippable[flippablePieces] = convertToMove(a, j);
                    flippablePieces += 1;
                    tempFlip += 1;
                } else if (state.gameState[a][j] == currentColor) {
                    return true;
                } else if (state.gameState[a][j] == '-') {
                    flippablePieces -= tempFlip;
                    return false;
                } else {
                    flippablePieces -= tempFlip;
                    return false;
                }
            }
        }
        return false;
    }

    public boolean South(State state, String move) {
        int[] ij = convertToBoard(move);
        int i = ij[0];
        int j = ij[1];
        if (i == boardSize - 1 || i == boardSize - 2)
            return false;

        if (state.gameState[(i + 1)][j] == opponentColor) {
            flippable[flippablePieces] = convertToMove(i + 1, j);
            int tempFlip = 1;
            flippablePieces += tempFlip;
            for (int a = (i + 2); a < boardSize; a++) {
                if (a >= boardSize || j >= boardSize) {
                    flippablePieces -= tempFlip;
                    return false;
                } else if (state.gameState[a][j] == opponentColor) {
                    flippable[flippablePieces] = convertToMove(a, j);
                    flippablePieces += 1;
                    tempFlip += 1;
                } else if (state.gameState[a][j] == currentColor) {
                    return true;
                } else if (state.gameState[a][j] == '-') {
                    flippablePieces -= tempFlip;
                    return false;
                } else {
                    flippablePieces -= tempFlip;
                    return false;
                }
            }
        }
        return false;
    }

    public boolean West(State state, String move) {
        int[] ij = convertToBoard(move);
        int i = ij[0];
        int j = ij[1];
        if (j == 0 || j == 1)
            return false;

        if (state.gameState[i][j - 1] == opponentColor) {
            flippable[flippablePieces] = convertToMove(i, j - 1);
            int tempFlip = 1;
            flippablePieces += tempFlip;
            for (int a = (j - 2); a > 0; a--) {
                if (i < 0 || a < 0) {
                    flippablePieces -= tempFlip;
                    return false;
                } else if (state.gameState[i][a] == opponentColor) {
                    flippable[flippablePieces] = convertToMove(i, a);
                    flippablePieces += 1;
                    tempFlip += 1;
                } else if (state.gameState[i][a] == currentColor) {
                    return true;
                } else if (state.gameState[i][a] == '-') {
                    flippablePieces -= tempFlip;
                    return false;
                } else {
                    flippablePieces -= tempFlip;
                    return false;
                }
            }
        }
        return false;
    }

    public boolean East(State state, String move) {
        int[] ij = convertToBoard(move);
        int i = ij[0];
        int j = ij[1];
        if (j == boardSize - 1 || j == boardSize - 2)
            return false;

        if (state.gameState[(i)][j + 1] == opponentColor) {
            flippable[flippablePieces] = convertToMove(i, j + 1);
            int tempFlip = 1;
            flippablePieces += tempFlip;
            for (int a = (j + 2); a < boardSize; a++) {
                if (i >= boardSize || a >= boardSize) {
                    flippablePieces -= tempFlip;
                    return false;
                } else if (state.gameState[i][a] == opponentColor) {
                    flippable[flippablePieces] = convertToMove(i, a);
                    flippablePieces += 1;
                    tempFlip += 1;
                } else if (state.gameState[i][a] == currentColor) {
                    return true;
                } else if (state.gameState[i][a] == '-') {
                    flippablePieces -= tempFlip;
                    return false;
                } else {
                    flippablePieces -= tempFlip;
                    return false;
                }
            }
        }
        return false;
    }

    public boolean NW(State state, String move) {
        int[] ij = convertToBoard(move);
        int i = ij[0];
        int j = ij[1];
        if (i == 0 || i == 1 || j == 0 || j == 1)
            return false;

        if (state.gameState[i - 1][j - 1] == opponentColor) {
            flippable[flippablePieces] = convertToMove(i - 1, j - 1);
            int tempFlip = 1;
            flippablePieces += tempFlip;
            int b = j - 2;

            for (int a = (i - 2); a >= 0; a--) {
                if (a < 0 || b < 0) {
                    flippablePieces -= tempFlip;
                    return false;
                } else if (state.gameState[a][b] == opponentColor) {
                    flippable[flippablePieces] = convertToMove(a, b);
                    flippablePieces += 1;
                    tempFlip += 1;
                } else if (state.gameState[a][b] == currentColor) {
                    return true;
                } else if (state.gameState[a][b] == '-') {
                    flippablePieces -= tempFlip;
                    return false;
                } else {
                    flippablePieces -= tempFlip;
                    return false;
                }
                b--;
            }
        }
        return false;
    }

    public boolean SE(State state, String move) {
        int[] ij = convertToBoard(move);
        int i = ij[0];
        int j = ij[1];
        if (i == boardSize - 1 || i == boardSize - 2 || j == boardSize - 1 || j == boardSize - 2)
            return false;
        if (state.gameState[i + 1][j + 1] == opponentColor) {
            flippable[flippablePieces] = convertToMove(i + 1, j + 1);
            int tempFlip = 1;
            flippablePieces += tempFlip;
            int b = j + 2;
            for (int a = (i + 2); a < boardSize; a++) {
                if (a >= boardSize || b >= boardSize) {
                    flippablePieces -= tempFlip;
                    return false;
                } else if (state.gameState[a][b] == opponentColor) {
                    flippable[flippablePieces] = convertToMove(a, b);
                    flippablePieces += 1;
                    tempFlip += 1;
                } else if (state.gameState[a][b] == currentColor) {
                    return true;
                } else if (state.gameState[a][b] == '-') {
                    flippablePieces -= tempFlip;
                    return false;
                } else {
                    flippablePieces -= tempFlip;
                    return false;
                }
                b++;
            }
        }
        return false;
    }

    public boolean NE(State state, String move) {
        int[] ij = convertToBoard(move);
        int i = ij[0];
        int j = ij[1];
        if (i == 0 || i == 1 || j == (boardSize - 1) || j == (boardSize - 2))
            return false;
        if (state.gameState[i - 1][j + 1] == opponentColor) {
            flippable[flippablePieces] = convertToMove(i - 1, j + 1);
            int tempFlip = 1;
            flippablePieces += tempFlip;
            int b = j + 2;
            for (int a = (i - 2); a > 0; a--) {
                if (a < 0 || b >= boardSize) {
                    flippablePieces -= tempFlip;
                    return false;
                } else if (state.gameState[a][b] == opponentColor) {
                    flippable[flippablePieces] = convertToMove(a, b);
                    flippablePieces += 1;
                    tempFlip += 1;
                } else if (state.gameState[a][b] == currentColor) {
                    return true;
                } else if (state.gameState[a][b] == '-') {
                    flippablePieces -= tempFlip;
                    return false;
                } else {
                    flippablePieces -= tempFlip;
                    return false;
                }
                b++;

            }
        }
        return false;
    }

    public boolean SW(State state, String move) {
        int[] ij = convertToBoard(move);
        int i = ij[0];
        int j = ij[1];
        if (i == (boardSize - 1) || i == (boardSize - 2) || j == 0 || j == 1)
            return false;

        if (state.gameState[(i + 1)][j - 1] == opponentColor) {
            flippable[flippablePieces] = convertToMove(i + 1, j - 1);
            int tempFlip = 1;
            flippablePieces += tempFlip;
            int b = j - 2;
            for (int a = (i + 2); a < boardSize; a++) {
                if (a >= boardSize || b < 0) {
                    flippablePieces -= tempFlip;
                    return false;
                } else if (state.gameState[a][b] == opponentColor) {
                    flippable[flippablePieces] = convertToMove(a, b);
                    flippablePieces += 1;
                    tempFlip += 1;
                } else if (state.gameState[a][b] == currentColor) {
                    return true;
                } else if (state.gameState[a][b] == '-') {
                    flippablePieces -= tempFlip;
                    return false;
                } else {
                    flippablePieces -= tempFlip;
                    return false;
                }
                b--;

            }
        }
        return false;
    }
}