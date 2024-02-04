package piecesMoves;

import engineCore.ChessBoard;
import engineCore.GameLogic;

public class Pawn {

    public static String possibleMoves(int row, int col) {

        StringBuilder moves = new StringBuilder();

        // Pawn Captures
        for (int dir = -1; dir <= 1; dir += 2) {
            if (!GameLogic.isValidPosition(row-1,col+dir)) {
                continue;
            }
            if (Character.isLowerCase(ChessBoard.chessBoard[row-1][col+dir].charAt(0))) {
                if (row > 1) {
                    moves.append(GameLogic.checkMove(row, col, row-1, col+dir));
                }
                else { // Captures With Promotion
                    moves.append(GameLogic.checkPromotionMove(row, col, 0, col+dir));
                }
            }
        }

        if (GameLogic.isValidPosition(row-1, col)) {
            if (" ".equals(ChessBoard.chessBoard[row-1][col])) {
                if (row > 1) {
                    moves.append(GameLogic.checkMove(row, col, row-1, col));
                } else {
                    moves.append(GameLogic.checkPromotionMove(row, col, 0, col));
                }
            }
        }
        if (GameLogic.isValidPosition(row-2, col)) {
            if (" ".equals(ChessBoard.chessBoard[row-2][col]) && row == 6) {
                moves.append(GameLogic.checkMove(row, col, row-2, col));
            }
        }

        return moves.toString();
    }
}
