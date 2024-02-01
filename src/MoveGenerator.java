public class MoveGenerator {
    public static String generatePossibleMoves() {
        StringBuilder list = new StringBuilder();

        for (int i = 0; i < 64; i++) {
            String piece = ChessBoard.chessBoard[i / 8][i % 8];

            switch (piece) {
                case "P":
                    if (Main.isWhiteTurn) {
                        list.append(possibleP(i));
                    }
                    break;
                case "p":
                    if (!Main.isWhiteTurn) {
                        list.append(possibleP(i));
                    }
                    break;
                case "R":
                    if (Main.isWhiteTurn) {
                        list.append(possibleR(i));
                    }
                    break;
                case "r":
                    if (!Main.isWhiteTurn) {
                        list.append(possibleR(i));
                    }
                    break;
                case "N":
                    if (Main.isWhiteTurn) {
                        list.append(possibleN(i));
                    }
                    break;
                case "n":
                    if (!Main.isWhiteTurn) {
                        list.append(possibleN(i));
                    }
                    break;
                case "B":
                    if (Main.isWhiteTurn) {
                        list.append(possibleB(i));
                    }
                    break;
                case "b":
                    if (!Main.isWhiteTurn) {
                        list.append(possibleB(i));
                    }
                    break;
                case "Q":
                    if (Main.isWhiteTurn) {
                        list.append(possibleQ(i));
                    }
                    break;
                case "q":
                    if (!Main.isWhiteTurn) {
                        list.append(possibleQ(i));
                    }
                    break;
                case "K":
                    if (Main.isWhiteTurn) {
                        list.append(possibleK(i));
                    }
                    break;
                case "k":
                    if (!Main.isWhiteTurn) {
                        list.append(possibleK(i));
                    }
                    break;
            }
        }
        return list.toString();
    }

    public static String possibleP(int i) {
        int r = i / 8, c = i % 8;
        StringBuilder list = new StringBuilder();

        // Pawn direction depends on whether it's a white or black pawn
        int direction = (Character.isUpperCase(ChessBoard.chessBoard[r][c].charAt(0))) ? -1 : 1;

        // Check forward movement
        if (GameLogic.isEmpty(r + direction, c) && GameLogic.kingSafe(Main.isWhiteTurn)) {
            list.append(GameLogic.checkMove(r, c, r + direction, c));

            // Check double move for the first pawn move
            if ((r == 1 && direction == 1) || (r == 6 && direction == -1)) {
                int doubleMoveRow = r + 2 * direction;
                if (GameLogic.isEmpty(doubleMoveRow, c) && GameLogic.kingSafe(Main.isWhiteTurn)) {
                    list.append(GameLogic.checkMove(r, c, doubleMoveRow, c));
                }
            }
        }

        // Check capturing moves diagonally
        for (int j = -1; j <= 1; j += 2) {
            int newRow = r + direction;
            int newCol = c + j;

            if (GameLogic.isOpponent(newRow, newCol) && GameLogic.kingSafe(Main.isWhiteTurn)) {
                list.append(GameLogic.checkMove(r, c, newRow, newCol));
            }
        }

        // TODO: Implement en passant here

        return list.toString();
    }

    public static String possibleR(int i) {
        int r = i / 8, c = i % 8;
        StringBuilder list = new StringBuilder();

        // Check horizontal and vertical movements
        for (int j = -1; j <= 1; j++) {
            for (int k = -1; k <= 1; k++) {
                if ((j == 0 && k != 0) || (j != 0 && k == 0)) {
                    int newRow = r + j;
                    int newCol = c + k;

                    // Check if the new position is valid before entering the loop
                    while (GameLogic.isValidPosition(newRow, newCol) && GameLogic.kingSafe(Main.isWhiteTurn)) {

                        if (GameLogic.isEmpty(newRow, newCol)) {
                            list.append(GameLogic.checkMove(r, c, newRow, newCol));

                        } else if (GameLogic.isOpponent(newRow, newCol)) {
                            list.append(GameLogic.checkMove(r, c, newRow, newCol));
                            break; // Stop checking in this direction after capturing an opponent piece
                        } else {
                            break;
                        }

                        newRow += j;
                        newCol += k;
                    }
                }
            }
        }

        return list.toString();
    }

    public static String possibleN(int i) {
        int r = i / 8, c = i % 8;
        StringBuilder list = new StringBuilder();

        int[][] moves = {{-2, -1}, {-2, 1}, {-1, -2}, {-1, 2}, {1, -2}, {1, 2}, {2, -1}, {2, 1}};

        for (int[] move : moves) {
            int newRow = r + move[0];
            int newCol = c + move[1];

            if (GameLogic.kingSafe(Main.isWhiteTurn) && GameLogic.isValidPosition(newRow, newCol) && ((GameLogic.isEmpty(newRow, newCol)) || (GameLogic.isOpponent(newRow, newCol)))) {
                list.append(GameLogic.checkMove(r, c, newRow, newCol));
            }
        }

        return list.toString();
        /*
         * This implementation checks the possible knight moves
         * using a predefined set of relative positions.
         * The method iterates through these positions, checks
         * if the resulting position is valid, and if it's
         * empty or occupied by an opponent. The GameLogic.isValidPosition
         * and isEmptyOrOpponent helper methods are used for clarity and code reusability.
         */
    }

    public static String possibleB(int i) {
        int r = i / 8, c = i % 8;
        StringBuilder list = new StringBuilder();

        // Check diagonal movements
        for (int j = -1; j <= 1; j += 2) {
            for (int k = -1; k <= 1; k += 2) {
                int newRow = r + j;
                int newCol = c + k;

                while (GameLogic.isValidPosition(newRow, newCol) && ((GameLogic.isEmpty(newRow, newCol)) || (GameLogic.isOpponent(newRow, newCol)))) {

                    if (GameLogic.kingSafe(Main.isWhiteTurn)) {
                        list.append(GameLogic.checkMove(r, c, newRow, newCol));
                    }

                    newRow += j;
                    newCol += k;
                }
            }
        }

        return list.toString();
    }

    public static String possibleQ(int i) {
        int r = i / 8, c = i % 8;
        StringBuilder list = new StringBuilder();

        // Check horizontal and vertical movements
        for (int j = -1; j <= 1; j++) {
            for (int k = -1; k <= 1; k++) {
                if (j == 0 && k == 0) {
                    continue; // Skip the current position
                }

                int newRow = r + j;
                int newCol = c + k;

                while (GameLogic.isValidPosition(newRow, newCol) && ((GameLogic.isEmpty(newRow, newCol)) || (GameLogic.isOpponent(newRow, newCol)))) {

                    if (GameLogic.kingSafe(Main.isWhiteTurn)) {
                        list.append(GameLogic.checkMove(r, c, newRow, newCol));
                    }

                    newRow += j;
                    newCol += k;
                }
            }
        }

        return list.toString();
    }

    public static String possibleK(int i) {
        int originalKingPositionW = ChessBoard.kingPositionW;
        int originalKingPositionB = ChessBoard.kingPositionB;
        int r = i / 8, c = i % 8;
        StringBuilder list = new StringBuilder();

        for (int j = -1; j <= 1; j++) {
            for (int k = -1; k <= 1; k++) {
                if (j == 0 && k == 0) {
                    continue; // Skip the current position
                }

                int newRow = r + j;
                int newCol = c + k;

                // Temporarily move the king to the new position
                if (Main.isWhiteTurn) {
                    ChessBoard.kingPositionW = newRow * 8 + newCol;
                } else {
                    ChessBoard.kingPositionB = newRow * 8 + newCol;
                }

                if (GameLogic.kingSafe(Main.isWhiteTurn) && GameLogic.isValidPosition(newRow, newCol) && ((GameLogic.isEmpty(newRow, newCol)) || (GameLogic.isOpponent(newRow, newCol)))) {
                    // e.g., 3435p, p: enemy pawn
                    list.append(GameLogic.checkMove(r, c, newRow, newCol));
                }

                // Restore the original king position
                ChessBoard.kingPositionW = originalKingPositionW;
                ChessBoard.kingPositionB = originalKingPositionB;
            }
        }

        return list.toString();
    }

}
