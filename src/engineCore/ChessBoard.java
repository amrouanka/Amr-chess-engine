package engineCore;

import java.util.Arrays;

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

    public static void updateKingsPosition() {
        kingPositionW = 0;
        kingPositionB = 0;
        while (!"K".equals(ChessBoard.chessBoard[ChessBoard.kingPositionW/8][ChessBoard.kingPositionW%8])) {ChessBoard.kingPositionW++;}
        while (!"k".equals(ChessBoard.chessBoard[ChessBoard.kingPositionB/8][ChessBoard.kingPositionB%8])) {ChessBoard.kingPositionB++;}
    }

    public static void makeMove(String move) {

        if (move.length() == 6) {
            chessBoard[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))] = Character.toString(move.charAt(5));
        } else {
            chessBoard[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))] = chessBoard[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))];
        }

        chessBoard[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))] = " ";
    }

    public static void undoMove(String move) {

        if (move.length() == 6) {
            chessBoard[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))] = Character.isUpperCase(move.charAt(5)) ? "P" : "p";
        } else {
            chessBoard[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))] = chessBoard[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))];
        }
        chessBoard[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))] = Character.toString(move.charAt(4));
    }

    public static void displayBoard() {
        for (int i = 0; i < 8; i++) {
            System.out.println(Arrays.toString(chessBoard[i]));
        }
    }

    public static void flipBoard() {

    }
}



















