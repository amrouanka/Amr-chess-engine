package piecesMoves;

import engineCore.ChessBoard;
import engineCore.GameLogic;

public class Knight {

    public static String possibleMoves(int row, int col) {

        StringBuilder moves = new StringBuilder();
        int[][] knightMoves = {{-2, -1}, {-2, 1}, {-1, -2}, {-1, 2}, {1, -2}, {1, 2}, {2, -1}, {2, 1}};

        for (int[] move : knightMoves) {
            int newRow = row + move[0];
            int newCol = col + move[1];

            if (GameLogic.isValidPosition(newRow, newCol)) {
                if (Character.isUpperCase(ChessBoard.chessBoard[newRow][newCol].charAt(0))) {
                    continue;
                }
                moves.append(GameLogic.checkMove(row, col, newRow, newCol));
            }
        }

        return moves.toString();
    }
}
