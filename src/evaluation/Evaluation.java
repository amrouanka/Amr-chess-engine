package evaluation;

import engineCore.ChessBoard;
import engineCore.GameLogic;
import engineCore.MoveGenerator;


public class Evaluation {

    // Piece values
    private static final int PAWN_VALUE = 100;
    private static final int KNIGHT_VALUE = 325;
    private static final int BISHOP_VALUE = 350;
    private static final int ROOK_VALUE = 500;
    private static final int QUEEN_VALUE = 975;

    private static final int[][] DFCT = {  // Distance From Centre Table
            {6, 5, 4, 3, 3, 4, 5, 6},
            {5, 4, 3, 2, 2, 3, 4, 5},
            {4, 3, 2, 1, 1, 2, 3, 4},
            {3, 2, 1, 0, 0, 1, 2, 3},
            {3, 2, 1, 0, 0, 1, 2, 3},
            {4, 3, 2, 1, 1, 2, 3, 4},
            {5, 4, 3, 2, 2, 3, 4, 5},
            {6, 5, 4, 3, 3, 4, 5, 6}
    };

    public static int rating(int moves) {
        int counter = 0;

        if (moves == 0) {
            if (GameLogic.kingSafe()) {
                return 0;
            } else {
                return Integer.MAX_VALUE;
            }
        }

        counter += rateMaterial();
        ChessBoard.flipBoard();

        String possibleMoves = MoveGenerator.generatePossibleMoves();
        if (possibleMoves.isEmpty()) {
            if (GameLogic.kingSafe()) {
                ChessBoard.flipBoard();
                return 0;
            } else {
                ChessBoard.flipBoard();
                return Integer.MIN_VALUE;
            }
        }

        counter -= possibleMoves.length() / 5;
        counter -= rateMaterial();
        ChessBoard.flipBoard();

        return (counter * -1);
    }


    public static int rateMaterial() {
        int[] knightAdj = { -20, -16, -12, -8, -4,  0,  4,  8 };
        int[] rookAdj = { 15,  12,   9,  6,  3,  0, -3, -6 };

        int counter = 0;
        int bishopCounter = 0;
        int knightCounter = 0;
        int rookCounter = 0;
        int pawnCounter = -1; /* so When there is 1 pawn pawnCounter == 0, then 0 == first Idx */

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                String square = ChessBoard.chessBoard[i][j];

                switch (square) {
                    case "P" -> {
                        counter += PAWN_VALUE - DFCT[i][j];
                        pawnCounter++;
                    }
                    case "N" -> {
                        counter += KNIGHT_VALUE - DFCT[i][j] * 2;
                        knightCounter++;
                    }
                    case "B" -> {
                        counter += BISHOP_VALUE - DFCT[i][j];
                        bishopCounter++;
                    }
                    case "R" -> {
                        counter += ROOK_VALUE + DFCT[i][j];
                        rookCounter++;
                    }
                    case "Q" -> counter += QUEEN_VALUE;
                }
            }
        }

        if (knightCounter == 2 && rookCounter == 1 && bishopCounter == 0) counter -= 30; /* 2 knights against 1 rook */

        if (pawnCounter != -1) {
            counter += (knightAdj[pawnCounter] * knightCounter) + (rookAdj[pawnCounter] * rookCounter);
        }

        return counter;
    }
}
