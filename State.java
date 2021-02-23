public class State {
    int size;
    static char player;
    char ai;
    char[][] gameState;
    boolean aiTurn; // true = ai's turn, false = human's turn

    public State(int size, char player) { // Emily: maybe we don't need to take in player? we will just parse in value?
        this.size = size;
        State.player = player;

        if (player == 'x') {
            this.ai = 'o';
            aiTurn = false;
        } else {
            this.ai = 'x';
            aiTurn = true;
        }

        if (size == 4) {
            /* create 4x4 board */
            gameState = new char[4][4];
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (gameState[i][j] != 'o' || gameState[i][j] != 'x') {
                        gameState[i][j] = '-';
                    }
                }
            }
            /* initial setup */
            gameState[getRow('b')][getCol(2)] = 'o';
            gameState[getRow('c')][getCol(3)] = 'o';
            gameState[getRow('c')][getCol(2)] = 'x';
            gameState[getRow('b')][getCol(3)] = 'x';

        } else if (size == 8) {
            /* create 8x8 board */
            gameState = new char[8][8];

            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (gameState[i][j] != 'o' || gameState[i][j] != 'x') {
                        gameState[i][j] = '-';
                    }
                }
            }
            /* initial state */
            gameState[getRow('d')][getCol(4)] = 'o';
            gameState[getRow('e')][getCol(5)] = 'o';
            gameState[getRow('e')][getCol(4)] = 'x';
            gameState[getRow('d')][getCol(5)] = 'x';
        }
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public char getPlayer() {
        return player;
    }

    public void setPlayer(char player) { // will never need to set player
        State.player = player;
    }

    public static char getCharRow(int row) {
        switch (row) {
            case 0:
                return 'a';
            case 1:
                return 'b';
            case 2:
                return 'c';
            case 3:
                return 'd';
            case 4:
                return 'e';
            case 5:
                return 'f';
            case 6:
                return 'g';
            case 7:
                return 'h';
            default:
                return 100;
        }
    }

    public static int getRow(char row) {
        switch (row) {
            case 'a':
                return 0;
            case 'b':
                return 1;
            case 'c':
                return 2;
            case 'd':
                return 3;
            case 'e':
                return 4;
            case 'f':
                return 5;
            case 'g':
                return 6;
            case 'h':
                return 7;
            default:
                return 100;
        }
    }

    public static int getCol(int col) {
        return col - 1;
    }

    public void printState(char gameState[][]) {
        /* print 4x4 board */
        if (size == 4) {
            System.out.print("  a b c d \n");
            for (int i = 0; i < 4; i++) {
                System.out.print(i + 1 + " ");
                for (int j = 0; j < gameState[i].length; j++) {
                    System.out.print(gameState[i][j] + " ");
                }
                System.out.print(i + 1 + "\n");
            }
            System.out.print("  a b c d \n");
            /* print 8x8 board */
        } else if (size == 8) {
            System.out.print("  a b c d e f g h \n");
            for (int i = 0; i < 8; i++) {
                System.out.print(i + 1 + " ");
                for (int j = 0; j < gameState[i].length; j++) {
                    System.out.print(gameState[i][j] + " ");
                }
                System.out.print(i + 1 + "\n");
            }
            System.out.print("  a b c d e f g h \n");
        }
    }

    public char[][] getGameState() {
        return gameState;
    }

    public State updateState(State s, char curPlayer, String move) {
        Action a = new Action(s, curPlayer);

        gameState[getRow(move.charAt(0))][getCol(Character.getNumericValue(move.charAt(1)))] = curPlayer;
        printState(gameState);
        // s = a.flip(s, move, curPlayer);
        return s;
    }
}
