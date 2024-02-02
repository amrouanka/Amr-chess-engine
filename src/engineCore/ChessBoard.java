package engineCore;

public class ChessBoard {
    public static String[][] chessBoard = {
            {"r", "n", "b", "q", "k", "b", "n", "r"},
            {"p", "p", "p", "p", "p", "p", "p", "p"},
            {"-", "-", "-", "-", "-", "-", "-", "-"},
            {"-", "-", "-", "-", "-", "-", "b", "-"},
            {"-", "-", "-", "-", "-", "-", "-", "-"},
            {"-", "-", "-", "-", "K", "-", "-", "-"},
            {"P", "P", "P", "P", "P", "P", "P", "P"},
            {"R", "N", "B", "Q", "-", "B", "N", "R"}
    };
    public static int kingPositionW, kingPositionB;

    public static void updateKingsPosition() {
        kingPositionW = 0;
        kingPositionB = 0;
        while (!"K".equals(ChessBoard.chessBoard[ChessBoard.kingPositionW/8][ChessBoard.kingPositionW%8])) {ChessBoard.kingPositionW++;}
        while (!"k".equals(ChessBoard.chessBoard[ChessBoard.kingPositionB/8][ChessBoard.kingPositionB%8])) {ChessBoard.kingPositionB++;}
    }
}
