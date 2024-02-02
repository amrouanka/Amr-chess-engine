import engineCore.MoveGenerator;

public class Main {
    public static void main(String[] args) {
        String possibleMoves = MoveGenerator.generatePossibleMoves();
        System.out.println();
        System.out.println(possibleMoves);
    }
}