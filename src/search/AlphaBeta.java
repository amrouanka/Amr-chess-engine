package search;

import engineCore.ChessBoard;
import engineCore.MoveGenerator;
import evaluation.Evaluation;

public class AlphaBeta {
    static int globalDepth = 4;

    public static String alphaBeta(int depth, int beta, int alpha, String move, int player) {
        // Generate all possible moves for the current position
        String moves = MoveGenerator.generatePossibleMoves();

        // Terminal conditions: end of depth or no possible moves
        if (depth == 0 || moves.isEmpty()) {
            // Return the evaluation score for the leaf node
            return move + (Evaluation.rating() * (player * 2 - 1));
        }

        // Toggle player between 1 and 0 (Maximizer and Minimizer)
        player = 1 - player;

        // Loop through each possible move
        for (int i = 0; i < moves.length(); i += 5) {
            // Make the move on the chess board
            ChessBoard.makeMove(moves.substring(i, i + 5));
            ChessBoard.flipBoard();

            // Recursive call to Alpha-Beta with reduced depth
            String returnString = alphaBeta(depth - 1, beta, alpha, moves.substring(i, i + 5), player);
            int value = Integer.parseInt(returnString.substring(5));

            // Undo the move and flip the board back to the original state
            ChessBoard.flipBoard();
            ChessBoard.undoMove(moves.substring(i, i + 5));

            // Update alpha and beta based on the evaluation of the current move
            if ((player == 0 && value <= beta) || (player == 1 && value > alpha)) {
                // Update move if at the root of the search tree
                if (depth == globalDepth) {
                    move = returnString.substring(0, 5);
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
        }

        // Return the best move and its score at the current state
        return move + (player == 0 ? beta : alpha);
    }
}
