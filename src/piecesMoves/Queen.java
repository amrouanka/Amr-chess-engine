package piecesMoves;

import engineCore.ChessBoard;
import engineCore.GameLogic;

public class Queen {

    public static String possibleMoves(int row, int col) {

        StringBuilder moves = new StringBuilder();

        for (int rowDir = -1; rowDir <= 1; rowDir++) {
            for (int colDir = -1; colDir <= 1; colDir++) {
                if (rowDir == 0 && colDir == 0) {
                    continue; // Skip the current position
                }

                int newRow = row + rowDir;
                int newCol = col + colDir;

                // Check if the new position is within the board boundaries
                while (GameLogic.isValidPosition(newRow, newCol)) {

                    if (" ".equals(ChessBoard.chessBoard[newRow][newCol])) {
                        moves.append(GameLogic.checkMove(row, col, newRow, newCol));
                    }
                    else if (Character.isLowerCase(ChessBoard.chessBoard[newRow][newCol].charAt(0))) {
                        moves.append(GameLogic.checkMove(row, col, newRow, newCol));
                        break; // Stop further exploration when capturing an opponent piece
                    }
                    else {
                        break; // Stop further exploration if a friendly piece is encountered
                    }

                    newRow += rowDir;
                    newCol += colDir;
                }
            }
        }

        return moves.toString();
    }
}
