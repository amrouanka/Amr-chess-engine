import engineCore.ChessBoard;
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
        f.setSize(755, 760);
        f.setVisible(true);
        f.setLocationRelativeTo(null);

        Object[] options = {"Computer", "Human"};
        ChessBoard.humanAsWhite = JOptionPane.showOptionDialog(null,
                "Who should play as white?", "ABC Options",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[1]);

        if (ChessBoard.humanAsWhite == 0) {
            playComputerMove(f);
        }
    }

    private static void playComputerMove(JFrame frame) {
        try {
            ChessBoard.makeMove(AlphaBeta.alphaBeta(AlphaBeta.globalDepth, 1000000, -1000000, "", 0).substring(0, 5));
            ChessBoard.flipBoard();
            frame.repaint();
        } catch (IndexOutOfBoundsException exception) {
            System.out.println("Checkmate");
        }
    }
}
