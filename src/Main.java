import java.util.Random;

public class Main {

    public static boolean isWhiteTurn = true;

    public static void main(String[] args) {
        playChess();
    }

    public static void playChess() {
        while (true) {
            displayChessboard();

            // Example: Generate possible moves using MoveGenerator
            String possibleMoves = MoveGenerator.generatePossibleMoves();

            // Example: Choose a random move from the list
            String randomMove = chooseRandomMove(possibleMoves);

            if (randomMove.equals("x")) break;

            // Display the chosen move
            System.out.println("Chosen Move: " + randomMove);

            // Apply the chosen move to update the chessboard
            applyMove(randomMove);

            // Switch turns
            isWhiteTurn = !isWhiteTurn;
        }
        System.out.println("Checkmate");
    }

    public static void displayChessboard() {
        System.out.println();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(ChessBoard.chessBoard[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static String chooseRandomMove(String possibleMoves) {
        if (!possibleMoves.isEmpty()) {
            // Split the moves into an array of strings, each containing 5 characters
            String[] movesArray = splitMoves(possibleMoves);

            if (movesArray.length > 0) {
                Random random = new Random();
                int randomIndex = random.nextInt(movesArray.length);
                return movesArray[randomIndex];
            } else {
                return "x";
            }
        } else {
            return "x";
        }
    }

    private static String[] splitMoves(String moves) {
        int moveLength = 5;
        int numberOfMoves = moves.length() / moveLength;
        String[] movesArray = new String[numberOfMoves];

        for (int i = 0; i < numberOfMoves; i++) {
            int startIndex = i * moveLength;
            int endIndex = startIndex + moveLength;
            movesArray[i] = moves.substring(startIndex, endIndex);
        }

        return movesArray;
    }

    public static void applyMove(String move) {
        int fromRow = move.charAt(0) - '0';
        int fromCol = move.charAt(1) - '0';
        int toRow = move.charAt(2) - '0';
        int toCol = move.charAt(3) - '0';

        ChessBoard.chessBoard[toRow][toCol] = ChessBoard.chessBoard[fromRow][fromCol];
        ChessBoard.chessBoard[fromRow][fromCol] = " ";
    }
}
