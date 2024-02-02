package engineCore;

public class GameLogic {

    public static boolean isValidPosition(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    public static boolean kingSafe() {
        // Check diagonals (bishop/queen)
        for (int i = -1; i <= 1; i += 2) {
            for (int j = -1; j <= 1; j += 2) {
                int temp = 1;
                int newRow = ChessBoard.kingPositionW / 8 + temp * i;
                int newCol = ChessBoard.kingPositionW % 8 + temp * j;

                while (GameLogic.isValidPosition(newRow, newCol) &&
                        " ".equals(ChessBoard.chessBoard[newRow][newCol])) {
                    temp++;
                    newRow = ChessBoard.kingPositionW / 8 + temp * i;
                    newCol = ChessBoard.kingPositionW % 8 + temp * j;
                }

                if (GameLogic.isValidPosition(newRow, newCol) &&
                        ("b".equals(ChessBoard.chessBoard[newRow][newCol]) || "q".equals(ChessBoard.chessBoard[newRow][newCol]))) {
                    return false;
                }
            }
        }

        // Check rows and columns (rook/queen)
        for (int i = -1; i <= 1; i += 2) {
            int temp = 1;
            int newRow = ChessBoard.kingPositionW / 8;
            int newCol = ChessBoard.kingPositionW % 8 + temp * i;

            while (GameLogic.isValidPosition(newRow, newCol) &&
                    " ".equals(ChessBoard.chessBoard[newRow][newCol])) {
                temp++;
                newCol = ChessBoard.kingPositionW % 8 + temp * i;
            }

            if (GameLogic.isValidPosition(newRow, newCol) &&
                    ("r".equals(ChessBoard.chessBoard[newRow][newCol]) || "q".equals(ChessBoard.chessBoard[newRow][newCol]))) {
                return false;
            }

            temp = 1;
            newRow = ChessBoard.kingPositionW / 8 + temp * i;
            newCol = ChessBoard.kingPositionW % 8;

            while (GameLogic.isValidPosition(newRow, newCol) &&
                    " ".equals(ChessBoard.chessBoard[newRow][newCol])) {
                temp++;
                newRow = ChessBoard.kingPositionW / 8 + temp * i;
            }

            if (GameLogic.isValidPosition(newRow, newCol) &&
                    ("r".equals(ChessBoard.chessBoard[newRow][newCol]) || "q".equals(ChessBoard.chessBoard[newRow][newCol]))) {
                return false;
            }
        }

        // Check knight positions
        for (int i = -1; i <= 1; i += 2) {
            for (int j = -1; j <= 1; j += 2) {
                int newRow, newCol;
                if (GameLogic.isValidPosition(newRow = ChessBoard.kingPositionW / 8 + i, newCol = ChessBoard.kingPositionW % 8 + j * 2) &&
                        "k".equals(ChessBoard.chessBoard[newRow][newCol])) {
                    return false;
                }
                if (GameLogic.isValidPosition(newRow = ChessBoard.kingPositionW / 8 + i * 2, newCol = ChessBoard.kingPositionW % 8 + j) &&
                        "k".equals(ChessBoard.chessBoard[newRow][newCol])) {
                    return false;
                }
            }
        }

        // Check pawn positions
        if (ChessBoard.kingPositionW >= 16) {
            for (int i = -1; i <= 1; i += 2) {
                int newRow, newCol;
                if (GameLogic.isValidPosition(newRow = ChessBoard.kingPositionW / 8 - 1, newCol = ChessBoard.kingPositionW % 8 + i) &&
                        "p".equals(ChessBoard.chessBoard[newRow][newCol])) {
                    return false;
                }
            }
        }

        // Check adjacent king positions
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newRow, newCol;
                if ((i != 0 || j != 0) && GameLogic.isValidPosition(newRow = ChessBoard.kingPositionW / 8 + i, newCol = ChessBoard.kingPositionW % 8 + j) &&
                        "a".equals(ChessBoard.chessBoard[newRow][newCol])) {
                    return false;
                }
            }
        }

        return true;
    }


    // Checks and records a potential move, then reverts the board to the original state
    public static String checkMove(int fromRow, int fromCol, int toRow, int toCol) {
        String oldPiece = ChessBoard.chessBoard[toRow][toCol];
        ChessBoard.chessBoard[toRow][toCol] = ChessBoard.chessBoard[fromRow][fromCol];
        ChessBoard.chessBoard[fromRow][fromCol] = " ";
        String move;
        if (kingSafe()) {
            move = fromRow + "" + fromCol + toRow + toCol + oldPiece;
        } else {
            move = "";
        }
        ChessBoard.chessBoard[fromRow][fromCol] = ChessBoard.chessBoard[toRow][toCol];
        ChessBoard.chessBoard[toRow][toCol] = oldPiece;
        return move;
    }
    public static String checkPromotionMove(int fromRow, int fromCol, int toRow, int toCol) {
        StringBuilder move = new StringBuilder();
        String oldPiece = ChessBoard.chessBoard[toRow][toCol];
        ChessBoard.chessBoard[toRow][toCol] = ChessBoard.chessBoard[fromRow][fromCol];
        ChessBoard.chessBoard[fromRow][fromCol] = " ";
        if (kingSafe()) {
            move.append(fromRow).append(fromCol).append(toRow).append(toCol).append(oldPiece).append("Q");
            move.append(fromRow).append(fromCol).append(toRow).append(toCol).append(oldPiece).append("R");
            move.append(fromRow).append(fromCol).append(toRow).append(toCol).append(oldPiece).append("N");
            move.append(fromRow).append(fromCol).append(toRow).append(toCol).append(oldPiece).append("B");
        }
        ChessBoard.chessBoard[fromRow][fromCol] = ChessBoard.chessBoard[toRow][toCol];
        ChessBoard.chessBoard[toRow][toCol] = oldPiece;
        return move.toString();
    }
}
