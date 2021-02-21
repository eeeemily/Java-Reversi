public class Actions {

    int boardSize;
    // char [][] possibleActions;
    int[] possibleActionsI;
    int[] possibleActionsJ;
    int numActions; // posible
    boolean xTurn;
    boolean oTurn;
    char opponentColor;
    char currentColor;
    int north, west, south, east, nw, ne, sw, se; // number of opponent pieces can be flipped in each of the 8
                                                  // directions

    public Actions(State s, char whoseTurn) {
        possibleActionsI = new int[64];
        possibleActionsJ = new int[64];
        numActions = 0;
        boardSize = s.getSize();
        north = west = south = east = nw = ne = sw = se = 0;

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

        // s[i][j] goes through each cell on the board
        for (int i = 0; i < boardSize; i++) { // get < size
            for (int j = 0; j < boardSize; j++) {
                // filter 1: find the empty spot
                if (s.gameState[i][j] == '-') { // see if whole board is x's and o's
                    // filter 2: do neighbor test to see if the current spot has any of the opposite
                    // piece
                    if (neighborTest(s, i, j)) {
                        j += 1;
                        addActions(i, j);
                    }
                }
            }
        }
    }

    public void addActions(int i, int j) {
        possibleActionsI[numActions] = i;
        possibleActionsJ[numActions] = j;
        numActions += 1;
    }

    public void printActions() {
        for (int i = 0; i < numActions; i++) {
            System.out.print("The " + i + " possible action is: ");
            System.out.println(possibleActionsI[i] + possibleActionsJ[i]);
        }
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
            for (int a = (i - 2); a >= 0; a--) {
                if (state.gameState[a][j] == '-')
                    return false;
                if (state.gameState[a][j] == currentColor) {
                    north = a;
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
            // if there's a cell of the current color again, it is a legal move
            for (int a = (i + 2); a <= boardSize; a++) {
                if (state.gameState[a][j] == '-')
                    return false;
                if (state.gameState[a][j] == currentColor) {
                    south = a;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean West(State state, int i, int j) {
        // top: i[0 to i-2]; j stays
        if (j == 0 || j == 1)
            return false;

        // if the cell on top of the current one is opponent's Color, continue the test
        if (state.gameState[i][j - 1] == opponentColor) {
            // if there's a cell of the current color again, it is a legal move
            for (int a = (j - 2); a >= 0; a--) {
                if (state.gameState[i][a] == '-')
                    return false;
                if (state.gameState[i][a] == currentColor) {
                    west = a;
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
            for (int a = (j + 2); a <= boardSize; a++) {
                if (state.gameState[i][a] == '-')
                    return false;
                if (state.gameState[i][a] == currentColor) {
                    east = a;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean NW(State state, int i, int j) {
        // diagonal, i=j
        if (i != j) {
            System.out.println("NW got a problem, i=" + i + " ; j=" + j);
            return false;
        }
        //
        if (i == 0 || i == 1)
            return false;

        // if the cell on top of the current one is opponent's Color, continue the test
        if (state.gameState[i - 1][i - 1] == opponentColor) {
            // if there's a cell of the current color again, it is a legal move
            for (int a = (i - 2); a >= 0; a--) {
                if (state.gameState[a][a] == '-')
                    return false;
                if (state.gameState[a][a] == currentColor) {
                    nw = a;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean SE(State state, int i, int j) {
        // diagonal, i=j
        if (i != j) {
            System.out.println("SE got a problem, i=" + i + " ; j=" + j);
            return false;
        }
        //
        if (i == boardSize || i == boardSize - 1)
            return false;

        // if the cell on top of the current one is opponent's Color, continue the test
        if (state.gameState[j + 1][j + 1] == opponentColor) {
            // if there's a cell of the current color again, it is a legal move
            for (int a = (j + 2); a <= boardSize; a++) {
                if (state.gameState[a][a] == '-')
                    return false;
                if (state.gameState[a][a] == currentColor) {
                    ne = a;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean NE(State state, int i, int j) {
        // diagonal, i+j=boardSize
        if ((i + j) != boardSize) {
            System.out.println("NE got a problem, i+j should=boardSize; but i=" + i + " ; j=" + j);
            return false;
        }
        //
        if (i == 0 || i == 1)
            return false;

        // if the cell on top of the current one is opponent's Color, continue the test
        if (state.gameState[(i - 1)][boardSize - i + 1] == opponentColor) {
            // if there's a cell of the current color again, it is a legal move
            for (int a = (i - 2); a >= 0; a--) {
                if (state.gameState[a][boardSize - a] == '-')
                    return false;
                if (state.gameState[a][boardSize - a] == currentColor) {
                    ne = a;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean SW(State state, int i, int j) {
        // diagonal, i+j=boardSize
        if ((i + j) != boardSize) {
            System.out.println("SW got a problem, i+j should=boardSize; but i=" + i + " ; j=" + j);
            return false;
        }
        //
        if (i == boardSize || i == boardSize - 1)
            return false;

        // if the cell on top of the current one is opponent's Color, continue the test
        if (state.gameState[(i + 1)][boardSize - i - 1] == opponentColor) {
            // if there's a cell of the current color again, it is a legal move
            for (int a = (i + 2); a <= boardSize; a++) {
                if (state.gameState[a][boardSize - a] == '-')
                    return false;
                if (state.gameState[a][boardSize - a] == currentColor) {
                    sw = a;
                    return true;
                }
            }
        }
        return false;
    }

}
