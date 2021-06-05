package ba.unsa.etf.rpr;
import java.util.Map;
import java.util.Scanner;

public class Program {

    private static void checkInput(String input) throws IllegalChessMoveException {
        if (input.charAt(0) == 'K' || input.charAt(0) == 'Q' || input.charAt(0) == 'R' || input.charAt(0) == 'B' ||
                input.charAt(0) == 'N')
            input = input.substring(1);
        if (input.length() != 2 || input.charAt(0) < 'a' || input.charAt(0) > 'h' || input.charAt(1) < '1'
                || input.charAt(1) > '8')
            throw new IllegalChessMoveException(input);
    }

    public static void main(String[] args) {
        Board board = new Board();
        ChessPiece.Color pieceColor = ChessPiece.Color.WHITE;
        Scanner input = new Scanner(System.in);
        for (;;) {
            if (pieceColor == ChessPiece.Color.WHITE && board.isCheck(pieceColor)
                    || pieceColor == ChessPiece.Color.BLACK && board.isCheck(pieceColor))
                System.out.println("CHECK");
            if (pieceColor == ChessPiece.Color.WHITE) System.out.println("White move: ");
            else System.out.println("Black move: ");
            String position = null;
            for(;;) {
                try {
                    position = input.nextLine();
                    if (position.compareTo("X") == 0 || position.compareTo("x") == 0) return;
                    checkInput(position);
                    if (position.charAt(0) == 'K') {
                        board.move(King.class, pieceColor, position.substring(1));
                    } else if (position.charAt(0) == 'Q') {
                        board.move(Queen.class, pieceColor, position.substring(1));

                    } else if (position.charAt(0) == 'B') {
                        board.move(Bishop.class, pieceColor, position.substring(1));

                    } else if (position.charAt(0) == 'R') {
                        board.move(Rook.class, pieceColor, position.substring(1));

                    } else if (position.charAt(0) == 'N') {
                        board.move(Knight.class, pieceColor, position.substring(1));
                    } else {
                        board.move(Pawn.class, pieceColor, position);
                    }
                    break;
                } catch (IllegalChessMoveException | IllegalArgumentException ignore) {
                    System.out.println("Illegal move");
                }
            }
            if (pieceColor == ChessPiece.Color.WHITE)
                pieceColor = ChessPiece.Color.BLACK;
            else pieceColor = ChessPiece.Color.WHITE;
        }
    }
}
