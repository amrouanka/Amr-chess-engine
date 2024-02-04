package engineCore;

public class ChessBoard {
    public static String[][] chessBoard = {
            {"r", "n", "b", "q", "k", "b", "n", "r"},
            {"p", "p", "p", "p", "p", "p", "p", "p"},
            {" ", " ", " ", " ", " ", " ", " ", " "},
            {" ", " ", " ", " ", " ", " ", " ", " "},
            {" ", " ", " ", " ", " ", " ", " ", " "},
            {" ", " ", " ", " ", " ", " ", " ", " "},
            {"P", "P", "P", "P", "P", "P", "P", "P"},
            {"R", "N", "B", "Q", "K", "B", "N", "R"}
    };
    public static int kingPositionW, kingPositionB;
    public static int humanAsWhite = -1;  // 1=human as white, 0=human as black

    public static void updateKingsPosition() {
        kingPositionW = 0;
        kingPositionB = 0;
        while (!"K".equals(ChessBoard.chessBoard[ChessBoard.kingPositionW / 8][ChessBoard.kingPositionW % 8])) {
            ChessBoard.kingPositionW++;
        }
        while (!"k".equals(ChessBoard.chessBoard[ChessBoard.kingPositionB / 8][ChessBoard.kingPositionB % 8])) {
            ChessBoard.kingPositionB++;
        }
    }

    public static void makeMove(String move) {
        int fromRow = move.charAt(0) - '0';
        int fromCol = move.charAt(1) - '0';
        int toRow = move.charAt(2) - '0';
        int toCol = move.charAt(3) - '0';

        if (move.length() == 6) {
            chessBoard[toRow][toCol] = Character.toString(move.charAt(5));
        } else {
            if ("K".equals(chessBoard[toRow][toCol])) {
                kingPositionW = 8 * toRow + toCol;
            }
            chessBoard[toRow][toCol] = chessBoard[fromRow][fromCol];
        }

        chessBoard[fromRow][fromCol] = " ";
    }

    public static void undoMove(String move) {
        int fromRow = move.charAt(0) - '0';
        int fromCol = move.charAt(1) - '0';
        int toRow = move.charAt(2) - '0';
        int toCol = move.charAt(3) - '0';

        if (move.length() == 6) {
            chessBoard[fromRow][fromCol] = Character.isUpperCase(move.charAt(5)) ? "P" : "p";
        } else {
            if ("K".equals(chessBoard[fromRow][fromCol])) {
                kingPositionW = 8 * fromRow + fromCol;
            }
            chessBoard[fromRow][fromCol] = chessBoard[toRow][toCol];
        }
        chessBoard[toRow][toCol] = Character.toString(move.charAt(4));
    }

    public static void flipBoard() {
        for (int i = 0; i < 32; i++) {

            int row = i / 8, col = i % 8;

            // Swap pieces and convert case
            String currentPiece = chessBoard[row][col];
            String temp = Character.isUpperCase(currentPiece.charAt(0))
                    ? currentPiece.toLowerCase()
                    : currentPiece.toUpperCase();

            chessBoard[row][col] = Character.isUpperCase(chessBoard[7 - row][7 - col].charAt(0))
                    ? chessBoard[7 - row][7 - col].toLowerCase()
                    : chessBoard[7 - row][7 - col].toUpperCase();

            chessBoard[7 - row][7 - col] = temp;
        }

        // Swap king positions
        int tempKingPosB = kingPositionB;
        kingPositionB = 63 - kingPositionW;
        kingPositionW = 63 - tempKingPosB;
    }
}
