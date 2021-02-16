public class Board {
    int size;
    char player;
    char ai;
    int score;
    char [][] gameBoard;

    public Board (int size, char player){
        this.size = size;
        this.player = player;
        
        if (player == 'x'){
            this.ai = 'o';
        }else {
            this.ai = 'x';
        }

        if (size == 4){
            /* create 4x4 bpard */
            gameBoard = new char [4][4];
            for (int i = 0; i<4; i++){
                for (int j =0; j<4; j++){
                    if (gameBoard[i][j] != 'o' || gameBoard[i][j] != 'x') {
                        gameBoard[i][j] = '-';
                    } 
                }
            }
            /* initial setup */
            gameBoard[getRow('b')][getCol(2)] = 'o';
            gameBoard[getRow('c')][getCol(3)] = 'o';
            gameBoard[getRow('c')][getCol(2)] = 'x';
            gameBoard[getRow('b')][getCol(3)] = 'x';

        }else if (size == 8){
             /* create 8x8 bpard */
            gameBoard = new char [8][8];

            for (int i = 0; i<8; i++){
                for (int j =0; j<8; j++){
                    if (gameBoard[i][j] != 'o' || gameBoard[i][j] != 'x') {
                        gameBoard[i][j] = '-';
                    } 
                }
            }
            /* initial setup */
            gameBoard[getRow('d')][getCol(4)] = 'o';
            gameBoard[getRow('e')][getCol(5)] = 'o';
            gameBoard[getRow('e')][getCol(4)] = 'x';
            gameBoard[getRow('d')][getCol(5)] = 'x'; 
        }
    }

    public int getSize(){
        return size;
    }
    public void setSize(int size){
        this.size = size;
    }

    public char getPlayer(){
        return player;
    }

    public void setPlayer(char player){
        this.player = player;
    }

    public static int getRow(char row){
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

    public static int getCol(int col){
        return col-1;
    }

    public void printBoard(char gameBoard[][]){
         /* print 4x4 board */
        if (size == 4) {
            System.out.print("  a b c d \n");
            for (int i = 0; i < 4; i++){
                System.out.print(i+1 + " ");
                for (int j =0; j < gameBoard[i].length; j++){
                    System.out.print(gameBoard[i][j] + " ");
                }
                System.out.print(i+1 + "\n");
            }
            System.out.print("  a b c d \n");
        /* print 8x8 board */
        }else if (size == 8) {
            System.out.print("  a b c d e f g h \n");
            for (int i = 0; i < 8; i++){
                System.out.print(i+1 + " ");
                for (int j =0; j < gameBoard[i].length; j++){
                    System.out.print(gameBoard[i][j] + " ");
                }
                System.out.print(i+1 + "\n");
            }
            System.out.print("  a b c d e f g h \n");
        }
    }
}
