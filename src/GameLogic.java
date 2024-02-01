public class GameLogic {

    public static boolean isValidPosition(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    // Checks if the square is occupied by an opponent's piece
    public static boolean isOpponent(int row, int col) {
        if (isValidPosition(row, col)) {
            if (Main.isWhiteTurn) {
                return Character.isLowerCase(ChessBoard.chessBoard[row][col].charAt(0));
            } else {
                return Character.isUpperCase(ChessBoard.chessBoard[row][col].charAt(0));
            }
        }

        return false;
    }

    // Checks if the square is empty
    public static boolean isEmpty(int row, int col) {
        return " ".equals(ChessBoard.chessBoard[row][col]);
    }


    public static boolean kingSafe(boolean isWhite) {
        int kingRow = isWhite ? ChessBoard.kingPositionW / 8 : ChessBoard.kingPositionB / 8;
        int kingCol = isWhite ? ChessBoard.kingPositionW % 8 : ChessBoard.kingPositionB % 8;

        // Check diagonal attacks
        for (int j = -1; j <= 1; j += 2) {
            for (int k = -1; k <= 1; k += 2) {
                int tempKingRow = kingRow;
                int tempKingCol = kingCol;

                while (isValidPosition(tempKingRow, tempKingCol)) {
                    String piece = ChessBoard.chessBoard[tempKingRow][tempKingCol];

                    if ((isWhite && ("b".equals(piece) || "q".equals(piece))) || (!isWhite && ("B".equals(piece) || "Q".equals(piece)))) {
                        return false;
                    }
                    if ((!isWhite && ("b".equals(piece) || "q".equals(piece))) || (isWhite && isFriendlyPiece(tempKingRow, tempKingCol))) {
                        break;
                    }

                    tempKingRow += j;
                    tempKingCol += k;
                }
            }
        }

        // Check horizontal and vertical attacks
        for (int j = -1; j <= 1; j++) {
            for (int k = -1; k <= 1; k++) {
                if ((j == 0 && k != 0) || (j != 0 && k == 0)) {
                    int tempKingRow = kingRow + j;
                    int tempKingCol = kingCol + k;

                    while (isValidPosition(tempKingRow, tempKingCol)) {
                        String piece = ChessBoard.chessBoard[tempKingRow][tempKingCol];

                        if ((isWhite && ("r".equals(piece) || "q".equals(piece))) || (!isWhite && ("R".equals(piece) || "Q".equals(piece)))) {
                            return false;
                        }
                        if ((!isWhite && ("r".equals(piece) || "q".equals(piece))) || (isWhite && isFriendlyPiece(tempKingRow, tempKingCol))) {
                            break;
                        }

                        tempKingRow += j;
                        tempKingCol += k;
                    }
                }
            }
        }

        // Check for knight attacks
        int[][] knightOffsets = {{-2, -1}, {-2, 1}, {-1, -2}, {-1, 2}, {1, -2}, {1, 2}, {2, -1}, {2, 1}};

        for (int[] offset : knightOffsets) {
            int tempKingRow = kingRow + offset[0];
            int tempKingCol = kingCol + offset[1];

            if (isValidPosition(tempKingRow, tempKingCol)) {
                String piece = ChessBoard.chessBoard[tempKingRow][tempKingCol];

                if ((isWhite && "n".equals(piece)) || (!isWhite && "N".equals(piece))) {
                    return false;
                }
            }
        }

        // Check for pawn attacks
        int[][] pawnOffsets = isWhite ? new int[][]{{1, -1}, {1, 1}} : new int[][]{{-1, -1}, {-1, 1}};

        for (int[] offset : pawnOffsets) {
            int tempKingRow = kingRow + offset[0];
            int tempKingCol = kingCol + offset[1];

            if (isValidPosition(tempKingRow, tempKingCol) && "pP".contains(ChessBoard.chessBoard[tempKingRow][tempKingCol])) {
                return false;
            }
        }

        return true;
    }


    // Checks and records a potential move, then reverts the board to the original state
    public static String checkMove(int fromRow, int fromCol, int toRow, int toCol) {
        String oldPiece = ChessBoard.chessBoard[toRow][toCol];
        ChessBoard.chessBoard[toRow][toCol] = ChessBoard.chessBoard[fromRow][fromCol];
        ChessBoard.chessBoard[fromRow][fromCol] = " ";

        // "" is to convert int to string implicitly
        String move = fromRow + "" + fromCol + toRow + toCol + oldPiece;

        ChessBoard.chessBoard[fromRow][fromCol] = ChessBoard.chessBoard[toRow][toCol];
        ChessBoard.chessBoard[toRow][toCol] = oldPiece;

        return move;
    }

    public static boolean isFriendlyPiece(int row, int col) {
        char piece = ChessBoard.chessBoard[row][col].charAt(0);
        boolean isWhitePiece = Character.isUpperCase(piece);

        return (Main.isWhiteTurn && isWhitePiece) || (!Main.isWhiteTurn && !isWhitePiece);
    }
}
