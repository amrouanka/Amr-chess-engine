package piecesMoves;

import engineCore.ChessBoard;
import engineCore.GameLogic;

public class King {

    public static String possibleMoves(int row, int col) {
        int originalKingPositionW = ChessBoard.kingPositionW;
        StringBuilder moves = new StringBuilder();

        for (int rowDir = -1; rowDir <= 1; rowDir += 1) {
            for (int colDir = -1; colDir <= 1; colDir += 1) {

                int newRow = row + rowDir;
                int newCol = col + colDir;
                if (!GameLogic.isValidPosition(newRow, newCol) || Character.isUpperCase(ChessBoard.chessBoard[newRow][newCol].charAt(0))) {
                    continue;
                }

                // Temporarily move the king to the new position
                ChessBoard.kingPositionW = newRow * 8 + newCol;
                moves.append(GameLogic.checkMove(row, col, newRow, newCol));

                // Restore the original king position
                ChessBoard.kingPositionW = originalKingPositionW;
            }
        }

        return moves.toString();
    }
}
