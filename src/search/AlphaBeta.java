package search;

import engineCore.ChessBoard;
import engineCore.MoveGenerator;
import evaluation.Evaluation;

import java.util.Arrays;
import java.util.Comparator;

public class AlphaBeta {
    // Depth of the global alpha-beta search
    public static int globalDepth = 2;
    // Move length for normal moves and pawn promotions
    static int moveLength = 5;
    // Maximum move length for pawn promotions
    static int maxMoveLength = 6;

    /**
     * Perform alpha-beta pruning to find the best move and its score.
     *
     * @param depth  Depth of the search tree
     * @param beta   Beta value for pruning
     * @param alpha  Alpha value for pruning
     * @param move   Current move being considered
     * @param player Player (0 or 1) indicating Maximizer or Minimizer
     * @return String representing the best move and its score
     */
    public static String alphaBeta(int depth, int beta, int alpha, String move, int player) {
        // Generate all possible moves for the current position
        String moves = MoveGenerator.generatePossibleMoves();

        // Terminal conditions: end of depth or no possible moves
        if (depth == 0 || moves.isEmpty()) {
            // Return the evaluation score for the leaf node
            return move + (Evaluation.rating(moves.length()) * (player * 2 - 1));
        }
        moves = sortMoves(moves);

        // Toggle player between 1 and 0 (Maximizer and Minimizer)
        player = 1 - player;

        for (int i = 0; i < moves.length(); i += moveLength) {
            // Determine the actual move length and whether it's a pawn promotion
            int x = (i + maxMoveLength <= moves.length() && Character.isUpperCase(moves.charAt(i + maxMoveLength - 1)))
                    ? maxMoveLength
                    : moveLength;

            // Make the move on the chess board
            String substring = moves.substring(i, i + x);
            ChessBoard.makeMove(substring);
            ChessBoard.flipBoard();

            // Recursive call to Alpha-Beta with reduced depth
            String returnString = alphaBeta(depth - 1, beta, alpha, substring, player);
            int value = Integer.parseInt(returnString.substring(x));

            // Undo the move and flip the board back to the original state
            ChessBoard.flipBoard();
            ChessBoard.undoMove(substring);

            // Update alpha and beta based on the evaluation of the current move
            if ((player == 0 && value <= beta) || (player == 1 && value > alpha)) {
                // Update move if at the root of the search tree
                if (depth == globalDepth) {
                    move = returnString.substring(0, x);
                }

                // Update beta for Minimizer and alpha for Maximizer
                if (player == 0) {
                    beta = value;
                } else {
                    alpha = value;
                }
            }

            // Prune the search tree if alpha is greater than or equal to beta
            if (alpha >= beta) {
                // Return the move and the pruned score
                return move + (player == 0 ? beta : alpha);
            }

            // Increment i if it's a pawn promotion
            i += (x == maxMoveLength) ? maxMoveLength - 1 : 0;
        }

        // Return the best move and its score at the current state
        return move + (player == 0 ? beta : alpha);
    }

    public static String sortMoves(String moves) {
        MoveScore[] moveScores = new MoveScore[moves.length() / 5];

        // Calculate scores for each move
        for (int i = 0; i < moves.length(); i += moveLength) {

            // Determine the actual move length and whether it's a pawn promotion
            int x = (i + maxMoveLength <= moves.length() && Character.isUpperCase(moves.charAt(i + maxMoveLength - 1)))
                    ? maxMoveLength
                    : moveLength;
            String substring = moves.substring(i, i + x);

            ChessBoard.makeMove(substring);
            int score = -Evaluation.rating(-1);
            ChessBoard.undoMove(substring);
            moveScores[i / 5] = new MoveScore(substring, score);

            // Increment i if it's a pawn promotion
            i += (x == maxMoveLength) ? maxMoveLength - 1 : 0;
        }

        // Sort the moves based on scores
        Arrays.sort(moveScores, Comparator.comparingInt(MoveScore::score).reversed());

        // Build the sorted moves string
        StringBuilder sortedMoves = new StringBuilder();
        for (MoveScore moveScore : moveScores) {
            sortedMoves.append(moveScore.move());
        }

        return sortedMoves.toString();
    }

    // Helper class to store move-score pairs
    private record MoveScore(String move, int score) {
    }

}