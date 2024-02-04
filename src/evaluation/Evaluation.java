package evaluation;

import engineCore.ChessBoard;
import engineCore.GameLogic;
import engineCore.MoveGenerator;


public class Evaluation {

    // Piece values
    private static final int PAWN_VALUE = 100;
    private static final int KNIGHT_VALUE = 325;
    private static final int BISHOP_VALUE = 325;
    private static final int ROOK_VALUE = 500;
    private static final int QUEEN_VALUE = 975;

    // Piece Square Tables
    private static final int[][] pawnTable ={
            { 0,  0,  0,  0,  0,  0,  0,  0},
            {50, 50, 50, 50, 50, 50, 50, 50},
            {10, 10, 20, 30, 30, 20, 10, 10},
            { 5,  5, 10, 25, 25, 10,  5,  5},
            { 0,  0,  0, 20, 20,  0,  0,  0},
            { 5, -5,-10,  0,  0,-10, -5,  5},
            { 5, 10, 10,-20,-20, 10, 10,  5},
            { 0,  0,  0,  0,  0,  0,  0,  0}};
    private static final int[][] rookTable ={
            { 0,  0,  0,  0,  0,  0,  0,  0},
            { 5, 10, 10, 10, 10, 10, 10,  5},
            {-5,  0,  0,  0,  0,  0,  0, -5},
            {-5,  0,  0,  0,  0,  0,  0, -5},
            {-5,  0,  0,  0,  0,  0,  0, -5},
            {-5,  0,  0,  0,  0,  0,  0, -5},
            {-5,  0,  0,  0,  0,  0,  0, -5},
            { 0,  0,  0,  5,  5,  0,  0,  0}};
    private static final int[][] knightTable ={
            {-50,-40,-30,-30,-30,-30,-40,-50},
            {-40,-20,  0,  0,  0,  0,-20,-40},
            {-30,  0, 10, 15, 15, 10,  0,-30},
            {-30,  5, 15, 20, 20, 15,  5,-30},
            {-30,  0, 15, 20, 20, 15,  0,-30},
            {-30,  5, 10, 15, 15, 10,  5,-30},
            {-40,-20,  0,  5,  5,  0,-20,-40},
            {-50,-40,-30,-30,-30,-30,-40,-50}};
    private static final int[][] bishopTable ={
            {-20,-10,-10,-10,-10,-10,-10,-20},
            {-10,  0,  0,  0,  0,  0,  0,-10},
            {-10,  0,  5, 10, 10,  5,  0,-10},
            {-10,  5,  5, 10, 10,  5,  5,-10},
            {-10,  0, 10, 10, 10, 10,  0,-10},
            {-10, 10, 10, 10, 10, 10, 10,-10},
            {-10,  5,  0,  0,  0,  0,  5,-10},
            {-20,-10,-10,-10,-10,-10,-10,-20}};
    private static final int[][] queenTable ={
            {-20,-10,-10, -5, -5,-10,-10,-20},
            {-10,  0,  0,  0,  0,  0,  0,-10},
            {-10,  0,  5,  5,  5,  5,  0,-10},
            { -5,  0,  5,  5,  5,  5,  0, -5},
            {  0,  0,  5,  5,  5,  5,  0, -5},
            {-10,  5,  5,  5,  5,  5,  0,-10},
            {-10,  0,  5,  0,  0,  0,  0,-10},
            {-20,-10,-10, -5, -5,-10,-10,-20}};

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
                return Integer.MIN_VALUE;
            }
        }

        counter += rateMaterial();
        ChessBoard.flipBoard();

        if (MoveGenerator.generatePossibleMoves().isEmpty()) {
            if (GameLogic.kingSafe()) {
                ChessBoard.flipBoard();
                return 0;
            } else {
                ChessBoard.flipBoard();
                return Integer.MAX_VALUE;
            }
        }

        counter -= MoveGenerator.generatePossibleMoves().length() * 2;
        counter -= rateMaterial();
        ChessBoard.flipBoard();

        ChessBoard.updateKingsPosition();

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
                        counter += PAWN_VALUE + pawnTable[i][j] - DFCT[i][j] * 2;
                        pawnCounter++;
                    }
                    case "N" -> {
                        counter += KNIGHT_VALUE + knightTable[i][j] - DFCT[i][j] * 3;
                        knightCounter++;
                    }
                    case "B" -> {
                        counter += bishopTable[i][j];
                        bishopCounter++;
                    }
                    case "R" -> {
                        counter += ROOK_VALUE + rookTable[i][j];
                        rookCounter++;
                    }
                    case "Q" -> counter += QUEEN_VALUE + queenTable[i][j] - DFCT[i][j] * 3;
                }
            }
        }

        if (knightCounter == 2 && rookCounter == 1 && bishopCounter == 0) counter -= 100; /* 2 knights against 1 rook */

        if (pawnCounter != -1) {
            counter += (knightAdj[pawnCounter] * knightCounter) + (rookAdj[pawnCounter] * rookCounter);
        }

        // bishop pair +50
        counter += (bishopCounter >= 2) ? (375 * bishopCounter) : (BISHOP_VALUE * bishopCounter);

        return counter;
    }
}
