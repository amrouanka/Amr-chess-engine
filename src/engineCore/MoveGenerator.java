package engineCore;

import piecesMoves.*;

public class MoveGenerator {
    public static String generatePossibleMoves() {
        StringBuilder list = new StringBuilder();
        ChessBoard.updateKingsPosition();

        for (int position = 0; position < 64; position++) {
            int row = position / 8;
            int col = position % 8;
            switch (ChessBoard.chessBoard[row][col]) {
                case "P":
                    list.append(Pawn.possibleMoves(row, col));
                    break;
                case "R":
                    list.append(Rook.possibleMoves(row, col));
                    break;
                case "N":
                    list.append(Knight.possibleMoves(row, col));
                    break;
                case "B":
                    list.append(Bishop.possibleMoves(row, col));
                    break;
                case "Q":
                    list.append(Queen.possibleMoves(row, col));
                    break;
                case "K":
                    list.append(King.possibleMoves(row, col));
                    break;
            }
        }

        return list.toString();
    }
}