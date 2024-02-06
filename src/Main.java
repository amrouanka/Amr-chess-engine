import engineCore.ChessBoard;
import engineCore.GameLogic;
import search.AlphaBeta;
import userInterface.UserInterface;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        startGame();
    }

    private static void startGame() {
        JFrame f = new JFrame("Amr Chess Engine");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        UserInterface ui = new UserInterface();
        f.add(ui);
        f.setSize(900, 920);
        f.setVisible(true);
        f.setLocationRelativeTo(null);

        Object[] options = {"Black", "White"};
        ChessBoard.humanAsWhite = JOptionPane.showOptionDialog(null,
                "Who should play as white?", "ABC Options",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[1]);

        if (ChessBoard.humanAsWhite == 0) {
            playComputerMove(f);
        }
    }

    private static void playComputerMove(JFrame frame) {
        String bestMove = AlphaBeta.alphaBeta(AlphaBeta.globalDepth, Integer.MAX_VALUE, Integer.MIN_VALUE, "", 0);
        ChessBoard.makeMove(Character.isLowerCase(bestMove.charAt(5)) ? bestMove.substring(0, 4) : bestMove.substring(0, 5));
        ChessBoard.flipBoard();
        frame.repaint();
    }
}
