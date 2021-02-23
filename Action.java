public class Action {
    State state;
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
    char whoseTurn;
    char opponentColor;
    char currentColor;
    int north, west, south, east, nw, ne, sw, se; // number of opponent pieces can be flipped in each of the 8
                                                  // directions; actually, might just loop through each cell agin and
                                                  // substract from current state; nvm, lemme still do this way

    public Action(State state, char whoseTurn) {
        this.state = state;
        this.whoseTurn = whoseTurn;

        possibleActionsI = new int[64];
        possibleActionsJ = new int[64];
        possibleActionsString = new String[64];
        numActions = 0;
        numFlips = new int[64]; // how many pieces can one move flip
        boardSize = state.getSize();
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

        // public String getAction(State s, int value){
        // return "Best Action";
        // }

        // s[i][j] goes through each cell on the board
        for (int i = 0; i < boardSize; i++) { // get < size
            for (int j = 0; j < boardSize; j++) {
                north = west = south = east = nw = ne = sw = se = 0; // reset
                // filter 1: find the empty spot
                if (state.gameState[i][j] == '-') { // see if whole board is x's and o's
                    // filter 2: do neighbor test to see if the current spot has any of the opposite
                    if (neighborTest(state, i, j)) {
                        numFlips[numActions] = north + south + west + east + nw + ne + sw + se;
                        addActions(i, j);
                    }
                }
            }
        }
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
            System.out.println(possibleActionsString[i]);
            // System.out.println("; Number of Pieces can be flipped in this move: " +
            // numFlips[i]);

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
    public int[] convert(String a) {
        int[] index = new int[2];
        // System.out.println(index[0]); //0
        // System.out.println(index[1]); //0
        // a.charAt 1 is the column
        index[0] = Character.getNumericValue(a.charAt(1)) - 1;
        // a.charAt 0 is the Row
        index[1] = Character.getNumericValue(a.charAt(0)) - 10;

        // System.out.println(Character.getNumericValue('g'));
        return index;
    }

    public State flip(State s, String move, char curPlayer) {
        boardSize = s.getSize();
        System.out.println("move is " + move);
        int[] arrayMove = convert(move);
        int i = arrayMove[0];
        int j = arrayMove[1];
        System.out.println("Flip:??  int i: " + i + " ; int: " + j);

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
            int[] convertIJ = convert(flips);
            s.gameState[convertIJ[0]][convertIJ[1]] = currentColor;
            System.out.println("flipping: " + flippable[a]);
            // s.gameState[State.getRow(flips.charAt(1))][State
            // .getCol(Character.getNumericValue(flips.charAt(0)))] = currentColor;
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
            flippable[flippablePieces] = "" + State.getCharRow(j) + (i);
            int tempFlip = 1;
            flippablePieces += tempFlip;
            for (int a = (i - 2); a >= 0; a--) {
                if (state.gameState[a][j] == '-') {
                    flippablePieces -= tempFlip;
                    return false;
                } else if (state.gameState[a][j] == opponentColor) {
                    north += 1; // useless?
                    flippable[flippablePieces] = "" + State.getCharRow(j) + (a + 1);
                    flippablePieces += 1;
                    tempFlip += 1;
                } else if (state.gameState[a][j] == currentColor) {
                    System.out.println("North has Flippable pieces: " + tempFlip);
                    return true;
                } else {
                    System.out.println("North reaches the boarder");
                    flippablePieces -= tempFlip;
                    return false;
                }
            }
        }
        return false;
    }

    public boolean South(State state, int i, int j) {
        // top: i[i+2 to boarderSize]?; j stays
        if (i == boardSize - 1 || i == boardSize - 2)
            return false;

        // if the cell on top of the current one is opponent's Color, continue the test
        if (state.gameState[(i + 1)][j] == opponentColor) {
            flippable[flippablePieces] = "" + State.getCharRow(j) + (i + 2);
            int tempFlip = 1;
            flippablePieces += tempFlip;
            // if there's a cell of the current color again, it is a legal move
            for (int a = (i + 2); a < boardSize; a++) {
                if (state.gameState[a][j] == '-') {
                    flippablePieces -= tempFlip;
                    return false;
                } else if (state.gameState[a][j] == opponentColor) {
                    flippable[flippablePieces] = "" + State.getCharRow(j) + (a + 1);
                    flippablePieces += 1;
                    tempFlip += 1;
                } else if (state.gameState[a][j] == currentColor) {
                    System.out.println("South has Flippable pieces: " + tempFlip);
                    return true;
                } else {
                    System.out.println("South reaches the boarder");
                    flippablePieces -= tempFlip;
                    return false;
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
            flippable[flippablePieces] = "" + State.getCharRow(j - 1) + (i + 1);
            int tempFlip = 1;
            flippablePieces += tempFlip;
            for (int a = (j - 2); a >= 0; a--) {
                if (state.gameState[i][a] == '-') {
                    flippablePieces -= tempFlip;
                    return false;
                } else if (state.gameState[i][a] == opponentColor) {
                    flippable[flippablePieces] = "" + State.getCharRow(a) + (i + 1);
                    flippablePieces += 1;
                    tempFlip += 1;
                } else if (state.gameState[i][a] == currentColor) {
                    System.out.println("West has Flippable pieces: " + tempFlip);
                    return true;
                } else {
                    System.out.println("West reaches the boarder");
                    flippablePieces -= tempFlip;
                    return false;
                }
            }
        }
        return false;
    }

    public boolean East(State state, int i, int j) {
        // top: i; j [j+2 to boarderSize]
        if (j == boardSize - 1 || j == boardSize - 2)
            return false;

        // if the cell on top of the current one is opponent's Color, continue the test
        if (state.gameState[(i)][j + 1] == opponentColor) {
            // if there's a cell of the current color again, it is a legal move
            flippable[flippablePieces] = "" + State.getCharRow(j + 1) + (i + 2);
            int tempFlip = 1;
            flippablePieces += tempFlip;
            for (int a = (j + 2); a < boardSize; a++) {
                if (state.gameState[i][a] == '-') {
                    flippablePieces -= tempFlip;
                    return false;
                } else if (state.gameState[i][a] == opponentColor) {
                    flippable[flippablePieces] = "" + State.getCharRow(a) + (i + 1);
                    flippablePieces += 1;
                    tempFlip += 1;
                } else if (state.gameState[i][a] == currentColor) {
                    System.out.println("East has Flippable pieces: " + tempFlip);
                    return true;
                } else {
                    System.out.println("East reaches the boarder");
                    flippablePieces -= tempFlip;
                    return false;
                }
            }
        }
        return false;
    }

    public boolean NW(State state, int i, int j) {
        // diagonal, i=j; otherwise, the spot is not at the diagonal position
        if (i == 0 || i == 1 || j == 0 || j == 1)
            return false;

        // if the cell on top of the current one is opponent's Color, continue the test
        if (state.gameState[i - 1][j - 1] == opponentColor) {
            flippable[flippablePieces] = "" + State.getCharRow(j - 1) + (i);
            int tempFlip = 1;
            flippablePieces += tempFlip;
            // if there's a cell of the current color again, it is a legal move
            int b = j - 2;
            for (int a = (i - 2); a >= 0; a--) {
                if (state.gameState[a][b] == '-') {
                    flippablePieces -= tempFlip;
                    return false;
                } else if (state.gameState[a][b] == opponentColor) {
                    flippable[flippablePieces] = "" + State.getCharRow(b) + (a + 1);
                    flippablePieces += 1;
                    tempFlip += 1;
                } else if (state.gameState[a][b] == currentColor) {
                    System.out.println("NW has Flippable pieces: " + tempFlip);
                    return true;
                } else {
                    System.out.println("NW got a problem");
                    flippablePieces -= tempFlip;
                    return false;
                }
                b--;
            }
        }
        return false;
    }

    public boolean SE(State state, int i, int j) {
        if (i == boardSize - 1 || i == boardSize - 2 || j == boardSize - 1 || j == boardSize - 2)
            return false;

        // if the cell on top of the current one is opponent's Color, continue the test
        if (state.gameState[i + 1][j + 1] == opponentColor) {
            flippable[flippablePieces] = "" + State.getCharRow(j + 1) + (i + 1);
            int tempFlip = 1;
            flippablePieces += tempFlip;
            // if there's a cell of the current color again, it is a legal move
            int b = j + 2;
            for (int a = (i + 2); a < boardSize; a++) {
                if (state.gameState[a][b] == '-') {
                    flippablePieces -= tempFlip;
                    return false;
                } else if (state.gameState[a][b] == opponentColor) {
                    flippable[flippablePieces] = "" + State.getCharRow(b) + (a + 1);
                    flippablePieces += 1;
                    tempFlip += 1;
                } else if (state.gameState[a][b] == currentColor) {
                    System.out.println("NW has Flippable pieces: " + tempFlip);
                    return true;
                } else {
                    System.out.println("SE reaches the boarder, without finding curCOlor");
                    flippablePieces -= tempFlip;
                    return false;
                }
                b++;
            }
        }
        return false;
    }

    public boolean NE(State state, int i, int j) {

        if (i == 0 || i == 1 || j == (boardSize - 1) || j == (boardSize - 2))
            return false;

        // if the cell on top of the current one is opponent's Color, continue the test
        if (state.gameState[i - 1][j + 1] == opponentColor) {
            flippable[flippablePieces] = "" + State.getCharRow(j + 1) + (i);
            int tempFlip = 1;
            flippablePieces += tempFlip;
            // if there's a cell of the current color again, it is a legal move
            int b = j + 2;
            for (int a = (i - 2); a >= 0; a--) {
                if (state.gameState[a][b] == '-') {
                    flippablePieces -= tempFlip;
                    return false;
                } else if (state.gameState[a][b] == opponentColor) {
                    flippable[flippablePieces] = "" + State.getCharRow(b) + (a + 1);
                    flippablePieces += 1;
                    tempFlip += 1;
                } else if (state.gameState[a][b] == currentColor) {
                    System.out.println("NW has Flippable pieces: " + tempFlip);
                    return true;
                } else {
                    System.out.println("SE reaches the boarder, without finding curCOlor");
                    flippablePieces -= tempFlip;
                    return false;
                }
                b++;

            }
        }
        return false;
    }

    public boolean SW(State state, int i, int j) {
        // i=6,7 j=0,1
        if (i == (boardSize - 1) || i == (boardSize - 2) || j == 0 || j == 1)
            return false;

        // if the cell on top of the current one is opponent's Color, continue the test
        if (state.gameState[(i + 1)][j - 1] == opponentColor) {
            flippable[flippablePieces] = "" + State.getCharRow(j - 1) + (i + 2);
            int tempFlip = 1;
            flippablePieces += tempFlip;
            // if there's a cell of the current color again, it is a legal move
            int b = j - 2;
            for (int a = (i + 2); a < boardSize; a++) {
                if (state.gameState[a][b] == '-') {
                    flippablePieces -= 1;
                    return false;
                } else if (state.gameState[a][b] == opponentColor) {
                    flippable[flippablePieces] = "" + State.getCharRow(b) + (a + 1);
                    flippablePieces += 1;
                    tempFlip += 1;
                } else if (state.gameState[a][b] == currentColor) {
                    System.out.println("NW has Flippable pieces: " + tempFlip);
                    return true;
                } else {
                    System.out.println("SE reaches the boarder, without finding curCOlor");
                    flippablePieces -= tempFlip;
                    return false;
                }
                b--;

            }
        }
        return false;
    }

}
