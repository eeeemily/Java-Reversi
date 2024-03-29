import java.util.ArrayList;

public class Action {
    State state;
    int boardSize;
    ArrayList<String> FlipAL;
    ArrayList<String> flipN, flipS, flipE, flipW, flipNW, flipNE, flipSW, flipSE;
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
    // int north, west, south, east, nw, ne, sw, se; // number of opponent pieces
    // can be flipped in each of the 8
    // directions; actually, might just loop through each cell agin and
    // substract from current state; nvm, lemme still do this way

    public Action() {
        // maybe i don't need to parse in these confusing argument
        // public Action(State state, char whoseTurn) {
        // this.state = state;
        // this.whoseTurn = whoseTurn;

        // possibleActionsI = new int[64];
        // possibleActionsJ = new int[64];
        possibleActionsString = new String[64];
        numActions = 0;
        flipN = new ArrayList<String>();
        flipS = new ArrayList<String>();
        flipE = new ArrayList<String>();
        flipW = new ArrayList<String>();
        flipNW = new ArrayList<String>();
        flipNE = new ArrayList<String>();
        flipSW = new ArrayList<String>();
        flipSE = new ArrayList<String>();
        // numFlips = new int[64]; // how many pieces can one move flip
        // boardSize = state.getSize();
        // north = west = south = east = nw = ne = sw = se = 0;

        // // for func flip
        flippable = new String[1000]; // put all flippeable pieces in a array
        flippablePieces = 0; // count how many pieces can be flipped

        // see who's making a move at the given state s
        // if (whoseTurn == 'x') {
        // xTurn = true;
        // oTurn = false;
        // opponentColor = 'o';
        // currentColor = 'x';
        // } else if (whoseTurn == 'o') {
        // xTurn = false;
        // oTurn = true;
        // opponentColor = 'x';
        // currentColor = 'o';
        // } else {
        // System.out.println("illegeal input: who's turn is it?? " + whoseTurn);
        // }

        // public String getAction(State s, int value){
        // return "Best Action";
        // }

    }

    public State cloneBoard(State target) {
        State clone = new State(target.getSize(), target.getPlayer());
        for (int i = 0; i < target.getSize(); i++) {
            for (int j = 0; j < target.getSize(); j++) {
                clone.gameState[i][j] = target.gameState[i][j];
            }
        }
        System.out.println("Cloning:");
        clone.printState(clone.gameState);
        return clone;

    }

    public String[] findActions(State state, char curPlayer) {
        // System.out.println("debugging: findActions?");
        possibleActionsString = new String[64];
        numActions = 0;
        boardSize = state.getSize();
        currentColor = curPlayer;
        opponentColor = (curPlayer == 'x') ? 'o' : 'x';
        // s[i][j] goes through each cell on the board
        for (int i = 0; i < boardSize; i++) { // get < size
            for (int j = 0; j < boardSize; j++) {
                // north = west = south = east = nw = ne = sw = se = 0; // reset
                // filter 1: find the empty spot
                if (state.gameState[i][j] == '-') { // see if whole board is x's and o's
                    // filter 2: do neighbor test to see if the current spot has any of the opposite
                    String move = convertToMove(i, j);
                    if (neighborTest(state, move)) {
                        // numFlips[numActions] = north + south + west + east + nw + ne + sw + se;
                        // System.out.println("debugging: should add move");
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
            // System.out.print(State.getCharRow(possibleActionsI[i]));
            // System.out.println((possibleActionsJ[i] + 1));
            System.out.println(possibleActionsString[i]);
            // System.out.println("; Number of Pieces can be flipped in this move: " +
            // numFlips[i]);

        }
    }

    // check if the input(string like a4) is a possible move
    public boolean isPossibleAction(State s, char curPlayer, String move) {
        findActions(s, curPlayer);
        for (int i = 0; i < numActions; i++) {
            if ((possibleActionsString[i]).equals(move)) {
                // System.out.println(move + " is a possible move at current state for Player "
                // + curPlayer);
                return true;
            }
        }
        // System.out.println(move + " is a NOT possible move at current state for
        // Player " + curPlayer);
        return false;
    }

    public int getNumActions(State state, char curPlayer) {
        return findActions(state, curPlayer).length;
    }

    public boolean hasPossibleAction(State s, char curPlayer) {
        // System.out.println("debugging: has possible action?");
        // System.out.println(curPlayer);
        // s.printState(s.gameState);
        findActions(s, curPlayer);
        // System.out.println(possibleActionsString[0]);
        return (numActions == 0) ? false : true; // if numActions=0, return false; else, there's possible moves
    }

    public boolean noPossibleAction(State s, char curPlayer) {
        findActions(s, curPlayer);
        return (numActions == 0) ? true : false; // if numActions=0, return true; else, there's no possible moves
    }
    // public Action getAction(State state, int value){

    // }
    public int[] convertToBoard(String a) {
        int[] index = new int[2];
        index[0] = Character.getNumericValue(a.charAt(1)) - 1;
        index[1] = Character.getNumericValue(a.charAt(0)) - 10;
        // System.out.println("CONVERTING TO BOARD: e.g. a3->2,0");
        // System.out.println(a + " -> " + index[0] + "," + index[1]);
        return index;
    }

    public String convertToMove(int i, int j) {
        // System.out.println("CONVERTING TO Move: e.g. 2,0->a3");
        // System.out.println(i + "," + j + " -> " + State.getCharRow(j) + (i + 1));
        return "" + State.getCharRow(j) + (i + 1);
    }

    public State addMove(State s, String move, char CurPlayer) {
        int[] m = convertToBoard(move);
        s.gameState[m[0]][m[1]] = CurPlayer;
        // System.out.println("adding a Move: " + move);
        return s;
    }

    public State flip(State s, String move, char curPlayer) {
        boardSize = s.getSize();
        s = addMove(s, move, curPlayer);
        // for func flip
        FlipAL = new ArrayList<String>();
        currentColor = curPlayer;
        opponentColor = (curPlayer == 'x') ? 'o' : 'x';

        // System.out.println("******** Flip Function***********");
        // System.out.println("Original State + move: ");
        // s.printState(s.gameState);

        // neighborTest(s, move);
        // int count = 0;
        // for (int a = 0; a < flippablePieces; a++) { // count++;
        // String flips = flippable[a];
        // int[] convertIJ = convertToBoard(flips);
        // s.gameState[convertIJ[0]][convertIJ[1]] = currentColor;
        // System.out.println("flipping: " + flippable[a]);
        // //
        // s.gameState[State.getRow(flips.charAt(1))][State.getCol(Character.getNumericValue(flips.charAt(0)))]
        // // = currentColor;
        // }

        flipN = new ArrayList<String>();
        flipS = new ArrayList<String>();
        flipE = new ArrayList<String>();
        flipW = new ArrayList<String>();
        flipNW = new ArrayList<String>();
        flipNE = new ArrayList<String>();
        flipSW = new ArrayList<String>();
        flipSE = new ArrayList<String>();

        North(s, move);
        South(s, move);
        West(s, move);
        East(s, move);
        NW(s, move);
        NE(s, move);
        SW(s, move);
        SE(s, move);

        if (flipN.size() > 0 && North(s, move)) {
            for (String i : flipN) {
                int[] convertIJ = convertToBoard(i);
                s.gameState[convertIJ[0]][convertIJ[1]] = currentColor;
            }
        }
        if (flipS.size() > 0 && South(s, move)) {
            for (String i : flipS) {
                int[] convertIJ = convertToBoard(i);
                s.gameState[convertIJ[0]][convertIJ[1]] = currentColor;
            }
        }
        if (flipE.size() > 0 && East(s, move)) {
            for (String i : flipE) {
                int[] convertIJ = convertToBoard(i);
                s.gameState[convertIJ[0]][convertIJ[1]] = currentColor;
            }
        }
        if (flipW.size() > 0 && West(s, move)) {
            for (String i : flipW) {
                int[] convertIJ = convertToBoard(i);
                s.gameState[convertIJ[0]][convertIJ[1]] = currentColor;
            }
        }
        if (flipNE.size() > 0 && NE(s, move)) {
            for (String i : flipNE) {
                int[] convertIJ = convertToBoard(i);
                s.gameState[convertIJ[0]][convertIJ[1]] = currentColor;
            }
        }
        if (flipNW.size() > 0 && NW(s, move)) {
            for (String i : flipNW) {
                int[] convertIJ = convertToBoard(i);
                s.gameState[convertIJ[0]][convertIJ[1]] = currentColor;
            }
        }
        if (flipSE.size() > 0 && SE(s, move)) {
            for (String i : flipSE) {
                int[] convertIJ = convertToBoard(i);
                s.gameState[convertIJ[0]][convertIJ[1]] = currentColor;
            }
        }
        if (flipSW.size() > 0 && SW(s, move)) {
            for (String i : flipSW) {
                int[] convertIJ = convertToBoard(i);
                s.gameState[convertIJ[0]][convertIJ[1]] = currentColor;
            }
        }
        // System.out.println("count:" + count);
        // System.out.println("Flipped State: ");
        // s.printState(s.gameState);
        // System.out.println("flippablePieces: " + flippablePieces);
        // System.out.println("******** End of Flip Function***********");
        return s;
    }

    public boolean neighborTest(State state, String move) {
        /*
         * if any of the 8 directions pass the test, then it is a legal move NW | N | NE
         * W | * | E SW | S | SE
         */
        flippable = new String[64]; // put all flippeable pieces in a array
        flippablePieces = 0; // count how many pieces can be flipped

        return North(state, move) || South(state, move) || West(state, move) || East(state, move) || NW(state, move)
                || NE(state, move) || SW(state, move) || SE(state, move);
    }

    // whose turn

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
            flipN.add(convertToMove((i - 1), j));
            for (int a = (i - 2); a >= 0; a--) {
                if (a < 0 || j < 0) {
                    // System.out.println("a<0 or b<0::: a: " + a + " b: " + b);
                    flippablePieces -= tempFlip;
                    return false;
                } else if (state.gameState[a][j] == opponentColor) {
                    // north += 1; // useless?
                    flippable[flippablePieces] = convertToMove(a, j);
                    flippablePieces += 1;
                    tempFlip += 1;
                    flipN.add(convertToMove(a, j));
                } else if (state.gameState[a][j] == currentColor) {
                    // System.out.println("-------North has Flippable pieces: " + tempFlip);
                    return true;
                } else if (state.gameState[a][j] == '-') {
                    flippablePieces -= tempFlip;
                    return false;
                } else {
                    // System.out.println("North reaches the boarder");
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
        // top: i[i+2 to boarderSize]?; j stays
        if (i == boardSize - 1 || i == boardSize - 2)
            return false;

        // if the cell on top of the current one is opponent's Color, continue the test
        if (state.gameState[(i + 1)][j] == opponentColor) {
            flippable[flippablePieces] = convertToMove(i + 1, j);
            int tempFlip = 1;
            flippablePieces += tempFlip;
            flipS.add(convertToMove(i + 1, j));
            // if there's a cell of the current color again, it is a legal move
            for (int a = (i + 2); a < boardSize; a++) {
                if (a >= boardSize || j >= boardSize) {
                    flippablePieces -= tempFlip;
                    return false;
                } else if (state.gameState[a][j] == opponentColor) {
                    // System.out.println("debugging: flippablePieces: " + flippablePieces);
                    flippable[flippablePieces] = convertToMove(a, j);
                    flippablePieces += 1;
                    tempFlip += 1;
                    flipS.add(convertToMove(a, j));
                } else if (state.gameState[a][j] == currentColor) {
                    // System.out.println("-------South has Flippable pieces: " + tempFlip); //
                    // PRINTS
                    // FLIPABLE PIECES
                    return true;
                } else if (state.gameState[a][j] == '-') {
                    flippablePieces -= tempFlip;
                    return false;
                } else {
                    // System.out.println("South reaches the boarder");
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
        // top: i stays; j[0 to i-2]
        if (j == 0 || j == 1)
            return false;

        // if the cell on top of the current one is opponent's Color, continue the test
        if (state.gameState[i][j - 1] == opponentColor) {
            // if there's a cell of the current color again, it is a legal move
            flippable[flippablePieces] = convertToMove(i, j - 1);
            int tempFlip = 1;
            flippablePieces += tempFlip;
            flipW.add(convertToMove(i, j - 1));
            for (int a = (j - 2); a >= 0; a--) {
                if (i < 0 || a < 0) {
                    flippablePieces -= tempFlip;
                    return false;
                } else if (state.gameState[i][a] == opponentColor) {
                    flippable[flippablePieces] = convertToMove(i, a);
                    flippablePieces += 1;
                    tempFlip += 1;
                    flipW.add(convertToMove(i, a));
                } else if (state.gameState[i][a] == currentColor) {
                    // System.out.println("-------West has Flippable pieces: " + tempFlip);
                    return true;
                } else if (state.gameState[i][a] == '-') {
                    flippablePieces -= tempFlip;
                    return false;
                } else {
                    // System.out.println("West reaches the boarder");
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
        // top: i; j [j+2 to boarderSize]
        if (j == boardSize - 1 || j == boardSize - 2)
            return false;

        // if the cell on top of the current one is opponent's Color, continue the test
        if (state.gameState[(i)][j + 1] == opponentColor) {
            // if there's a cell of the current color again, it is a legal move
            flippable[flippablePieces] = convertToMove(i, j + 1);
            int tempFlip = 1;
            flippablePieces += tempFlip;
            flipE.add(convertToMove(i, j + 1));
            for (int a = (j + 2); a < boardSize; a++) {
                if (i >= boardSize || a >= boardSize) {
                    flippablePieces -= tempFlip;
                    return false;
                } else if (state.gameState[i][a] == opponentColor) {
                    flippable[flippablePieces] = convertToMove(i, a);
                    flippablePieces += 1;
                    tempFlip += 1;
                    flipE.add(convertToMove(i, a));
                } else if (state.gameState[i][a] == currentColor) {
                    // System.out.println("-------East has Flippable pieces: " + tempFlip);
                    return true;
                } else if (state.gameState[i][a] == '-') {
                    flippablePieces -= tempFlip;
                    return false;
                } else {
                    // System.out.println("East reaches the boarder");
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
        // diagonal, i=j; otherwise, the spot is not at the diagonal position
        if (i == 0 || i == 1 || j == 0 || j == 1)
            return false;

        // if the cell on top of the current one is opponent's Color, continue the test
        if (state.gameState[i - 1][j - 1] == opponentColor) {
            // System.out.println("debugging: " + flippablePieces);
            flippable[flippablePieces] = convertToMove(i - 1, j - 1);
            int tempFlip = 1;
            flippablePieces += tempFlip;
            flipNW.add(convertToMove(i - 1, j - 1));
            // if there's a cell of the current color again, it is a legal move
            int b = j - 2;
            // if((i-2)==0){
            // System.out.println("NW out of index?");
            // return false;
            // }

            for (int a = (i - 2); a >= 0; a--) {
                if (a < 0 || b < 0) {
                    flippablePieces -= tempFlip;
                    return false;
                } else if (state.gameState[a][b] == opponentColor) {
                    flippable[flippablePieces] = convertToMove(a, b);
                    flippablePieces += 1;
                    tempFlip += 1;
                    flipNW.add(convertToMove(a, b));
                } else if (state.gameState[a][b] == currentColor) {
                    // System.out.println("-------NW has Flippable pieces: " + tempFlip);
                    return true;
                } else if (state.gameState[a][b] == '-') {
                    flippablePieces -= tempFlip;
                    return false;
                } else {
                    // System.out.println("NW got a problem");
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

        // if the cell on top of the current one is opponent's Color, continue the test
        if (state.gameState[i + 1][j + 1] == opponentColor) {
            flippable[flippablePieces] = convertToMove(i + 1, j + 1);
            int tempFlip = 1;
            flippablePieces += tempFlip;
            flipSE.add("" + convertToMove(i + 1, j + 1));
            // if there's a cell of the current color again, it is a legal move
            int b = j + 2;
            for (int a = (i + 2); a < boardSize; a++) {

                if (a >= boardSize || b >= boardSize) {
                    flippablePieces -= tempFlip;
                    return false;
                } else if (state.gameState[a][b] == opponentColor) {
                    flippable[flippablePieces] = convertToMove(a, b);
                    flippablePieces += 1;
                    tempFlip += 1;
                    flipSE.add(convertToMove(a, b));
                } else if (state.gameState[a][b] == currentColor) {
                    // System.out.println("-------NW has Flippable pieces: " + tempFlip);
                    return true;
                } else if (state.gameState[a][b] == '-') {
                    flippablePieces -= tempFlip;
                    return false;
                } else {
                    // System.out.println("SE reaches the boarder, without finding curCOlor");
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

        // if the cell on top of the current one is opponent's Color, continue the test
        if (state.gameState[i - 1][j + 1] == opponentColor) {
            flippable[flippablePieces] = convertToMove(i - 1, j + 1);
            int tempFlip = 1;
            flippablePieces += tempFlip;
            flipNE.add(convertToMove(i - 1, j + 1));
            // if there's a cell of the current color again, it is a legal move
            int b = j + 2;
            for (int a = (i - 2); a >= 0; a--) {
                if (a < 0 || b >= boardSize) {
                    flippablePieces -= tempFlip;
                    return false;
                } else if (state.gameState[a][b] == opponentColor) {
                    flippable[flippablePieces] = convertToMove(a, b);
                    flippablePieces += 1;
                    tempFlip += 1;
                    flipNE.add(convertToMove(a, b));
                } else if (state.gameState[a][b] == currentColor) {
                    // System.out.println("-------NW has Flippable pieces: " + tempFlip);
                    return true;
                } else if (state.gameState[a][b] == '-') {
                    flippablePieces -= tempFlip;
                    return false;
                } else {
                    // System.out.println("SE reaches the boarder, without finding curCOlor");
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
        // i=6,7 j=0,1
        if (i == (boardSize - 1) || i == (boardSize - 2) || j == 0 || j == 1)
            return false;

        // if the cell on top of the current one is opponent's Color, continue the test
        if (state.gameState[(i + 1)][j - 1] == opponentColor) {
            flippable[flippablePieces] = convertToMove(i + 1, j - 1);
            int tempFlip = 1;
            flippablePieces += tempFlip;
            flipSW.add(convertToMove(i + 1, j - 1));
            // if there's a cell of the current color again, it is a legal move
            int b = j - 2;
            for (int a = (i + 2); a < boardSize; a++) {
                if (a >= boardSize || b < 0) {
                    System.out.println("a<0 or b<0::: a: " + a + " b: " + b);
                    flippablePieces -= tempFlip;
                    return false;
                } else if (state.gameState[a][b] == opponentColor) {
                    flippable[flippablePieces] = convertToMove(a, b);
                    flippablePieces += 1;
                    tempFlip += 1;
                    flipSW.add(convertToMove(a, b));
                } else if (state.gameState[a][b] == currentColor) {
                    // System.out.println("-------NW has Flippable pieces: " + tempFlip);
                    return true;
                } else if (state.gameState[a][b] == '-') {
                    flippablePieces -= tempFlip;
                    return false;
                } else {
                    // System.out.println("SE reaches the boarder, without finding curCOlor");
                    flippablePieces -= tempFlip;
                    return false;
                }
                b--;

            }
        }
        return false;
    }

}