package userInterface;

import engineCore.ChessBoard;
import engineCore.GameLogic;
import engineCore.MoveGenerator;
import search.AlphaBeta;

import java.awt.*;
import java.awt.event.*;
import java.util.Objects;
import javax.swing.*;

public class UserInterface extends JPanel implements MouseListener, MouseMotionListener {

    static int mouseX, mouseY, newMouseX, newMouseY;
    static int squareSize = 110;


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.BLACK);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        for (int i = 0; i < 64; i += 2) {
            g.setColor(new Color(212, 186, 150));
            g.fillRect((i % 8 + (i / 8) % 2) * squareSize, (i / 8) * squareSize, squareSize, squareSize);
            g.setColor(new Color(142, 107, 79));
            g.fillRect(((i + 1) % 8 - ((i + 1) / 8) % 2) * squareSize, ((i + 1) / 8) * squareSize, squareSize, squareSize);
        }

        Image chessPiecesImage;
        chessPiecesImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("ChessPieces.png"))).getImage();

        for (int i = 0; i < 64; i++) {
            int j = -1, k = -1;
            k = switch (ChessBoard.chessBoard[i / 8][i % 8]) {
                case "P" -> {
                    j = 5;
                    yield 0;
                }
                case "p" -> {
                    j = 5;
                    yield 1;
                }
                case "R" -> {
                    j = 2;
                    yield 0;
                }
                case "r" -> {
                    j = 2;
                    yield 1;
                }
                case "N" -> {
                    j = 4;
                    yield 0;
                }
                case "n" -> {
                    j = 4;
                    yield 1;
                }
                case "B" -> {
                    j = 3;
                    yield 0;
                }
                case "b" -> {
                    j = 3;
                    yield 1;
                }
                case "Q" -> {
                    j = 1;
                    yield 0;
                }
                case "q" -> {
                    j = 1;
                    yield 1;
                }
                case "K" -> {
                    j = 0;
                    yield 0;
                }
                case "k" -> {
                    j = 0;
                    yield 1;
                }
                default -> k;
            };
            if (j != -1) {
                g.drawImage(chessPiecesImage, (i % 8) * squareSize, (i / 8) * squareSize, (i % 8 + 1) * squareSize, (i / 8 + 1) * squareSize, j * 64, k * 64, (j + 1) * 64, (k + 1) * 64, this);
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getX() < 8 * squareSize && e.getY() < 8 * squareSize) {
            // If inside the board
            mouseX = e.getX();
            mouseY = e.getY();
            repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getX() < 8 * squareSize && e.getY() < 8 * squareSize) {
            // If inside the board
            newMouseX = e.getX();
            newMouseY = e.getY();
            if (e.getButton() == MouseEvent.BUTTON1) {
                String dragMove = getString();
                String userPossibilities = MoveGenerator.generatePossibleMoves();
                if (userPossibilities.isEmpty()) {
                    if (GameLogic.kingSafe()) {
                        System.out.println("Stalemate");
                    } else {
                        System.out.println("Computer WON by Checkmate AND you lost xD");
                    }
                    System.exit(0);
                }

                /*
                 *  if Statement below Checks if the length of the string after removing occurrences of
                 *  dragMove from userPossibilities is less than the original length of userPossibilities.
                 *  This implies that the move dragMove was present in the list of possible moves.
                 * */
                if (userPossibilities.replaceAll(dragMove, "").length() < userPossibilities.length()) {
                    ChessBoard.makeMove(dragMove);
                    repaint();
                    ChessBoard.flipBoard();

                    long startTime=System.currentTimeMillis();
                    String bestMove = AlphaBeta.alphaBeta(AlphaBeta.globalDepth, Integer.MAX_VALUE, Integer.MIN_VALUE, "", 0);
                    long endTime=System.currentTimeMillis();
                    System.out.println();
                    System.out.println("That took "+(endTime-startTime)+" milliseconds");
                    System.out.println("Moves Searched: " + AlphaBeta.c / 1000 + " Thousands moves");
                    AlphaBeta.c = 0;

                    if (bestMove.length() < 5 || "pnbqr ".indexOf(bestMove.charAt(4)) == -1) {
                        if (GameLogic.kingSafe()) {
                            System.out.println("Stalemate");
                        } else {
                            System.out.println("You WON by Checkmate");
                        }
                        System.exit(0);
                    } else {
                        ChessBoard.makeMove(Character.isLowerCase(bestMove.charAt(5)) ? bestMove.substring(0, 4) : bestMove.substring(0, 5));
                        ChessBoard.flipBoard();
                        repaint();
                    }
                }
            }
        }
    }

    private static String getString() {
        String dragMove;
        if (newMouseY / squareSize == 0 && mouseY / squareSize == 1 && "P".equals(ChessBoard.chessBoard[mouseY / squareSize][mouseX / squareSize])) {
            // Pawn promotion
            dragMove = "" + mouseY / squareSize + mouseX / squareSize + newMouseY / squareSize + newMouseX / squareSize + ChessBoard.chessBoard[newMouseY / squareSize][newMouseX / squareSize] + "Q";
        } else {
            // Regular move
            dragMove = "" + mouseY / squareSize + mouseX / squareSize + newMouseY / squareSize + newMouseX / squareSize + ChessBoard.chessBoard[newMouseY / squareSize][newMouseX / squareSize];
        }
        return dragMove;
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
