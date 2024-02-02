package userInterface;

import engineCore.ChessBoard;

import java.awt.*;
import java.awt.event.*;
import java.util.Objects;
import javax.swing.*;

public class UserInterface extends JPanel implements MouseListener, MouseMotionListener {
    static int x = 0, y = 0;
    static int squareSize = 90;

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
                g.drawImage(chessPiecesImage, (i % 8) * squareSize, (i / 8) * squareSize,
                        (i % 8 + 1) * squareSize, (i / 8 + 1) * squareSize, j * 64, k * 64, (j + 1) * 64, (k + 1) * 64, this);
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        x = e.getX();
        y = e.getY();
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {
        x = e.getX();
        y = e.getY();
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
