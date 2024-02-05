package search;

import engineCore.ChessBoard;
import engineCore.MoveGenerator;
import evaluation.Evaluation;


public class AlphaBeta {
    public static int c = 0;

    // Depth of the global alpha-beta search
    public static int globalDepth = 4;
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
        moves = orderMoves(moves);

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
            c++;
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
            i += (x == maxMoveLength) ? 1 : 0;
        }

        // Return the best move and its score at the current state
        return move + (player == 0 ? beta : alpha);
    }

    /**
     * Order the moves based on a simple evaluation of their potential outcomes.
     * This method aims to prioritize promising moves to improve alpha-beta pruning.
     *
     * @param moves Unordered string of moves
     * @return String representing ordered moves
     */
    public static String orderMoves(String moves) {
        // Count the number of pawn promotions and store them into temp which will be the most promising moves
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < moves.length(); i += moveLength) {
            if (i + maxMoveLength <= moves.length() && Character.isUpperCase(moves.charAt(i + maxMoveLength - 1))) {
                temp.append(moves, i, i + maxMoveLength);
                moves = moves.replace(moves.substring(i, i + maxMoveLength), "");
                i -= 5;
            }
        }

        // Determine the size of the array for storing move scores
        int arraySize = moves.length() / moveLength;
        int[] scores = new int[arraySize];

        // Evaluate each move and store the scores
        for (int i = 0; i < moves.length(); i += moveLength) {
            String substring = moves.substring(i, i + moveLength);
            ChessBoard.makeMove(substring);
            scores[i / moveLength] = Evaluation.rating(-1) * -1;  // Reverse the score for opponent's perspective
            ChessBoard.undoMove(substring);
        }

        // Build a new ordered list by selecting moves with higher scores
        StringBuilder orderedListA = new StringBuilder();
        String orderedListB = moves;
        for (int i = 0; i < Math.min(6, arraySize); i++) {
            int max = Integer.MIN_VALUE, maxLocation = 0;
            for (int j = 0; j < arraySize; j++) {
                if (scores[j] > max) {
                    max = scores[j];
                    maxLocation = j;
                }
            }
            scores[maxLocation] = Integer.MIN_VALUE;
            orderedListA.append(moves, maxLocation * moveLength, maxLocation * moveLength + moveLength);
            orderedListB = orderedListB.replace(moves.substring(maxLocation * moveLength, maxLocation * moveLength + moveLength), "");
        }

        return temp.toString() + orderedListA + orderedListB;
    }
}




