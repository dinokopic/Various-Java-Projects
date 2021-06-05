package ba.unsa.etf.rpr;

import java.util.Map;
import java.util.TreeMap;

public class Board {
    private Map<String, ChessPiece> board;

    public Board() {
        board = new TreeMap<>();
        for (char i = 'A'; i < 'I'; i++) {
            String c = String.valueOf(i);
            board.put(c + 2, new Pawn(c + 2, ChessPiece.Color.WHITE));
            board.put(c + 7, new Pawn(c + 7, ChessPiece.Color.BLACK));
        }

        board.put("A1", new Rook("A1", ChessPiece.Color.WHITE));
        board.put("H1", new Rook("H1", ChessPiece.Color.WHITE));
        board.put("A8", new Rook("A8", ChessPiece.Color.BLACK));
        board.put("H8", new Rook("H8", ChessPiece.Color.BLACK));

        board.put("B1", new Knight("B1", ChessPiece.Color.WHITE));
        board.put("G1", new Knight("G1", ChessPiece.Color.WHITE));
        board.put("B8", new Knight("B8", ChessPiece.Color.BLACK));
        board.put("G8", new Knight("G8", ChessPiece.Color.BLACK));

        board.put("C1", new Bishop("C1", ChessPiece.Color.WHITE));
        board.put("F1", new Bishop("F1", ChessPiece.Color.WHITE));
        board.put("C8", new Bishop("C8", ChessPiece.Color.BLACK));
        board.put("F8", new Bishop("F8", ChessPiece.Color.BLACK));

        board.put("D1", new Queen("D1", ChessPiece.Color.WHITE));
        board.put("D8", new Queen("D8", ChessPiece.Color.BLACK));

        board.put("E1", new King("E1", ChessPiece.Color.WHITE));
        board.put("E8", new King("E8", ChessPiece.Color.BLACK));

        for (int i = 3; i < 7; i++) {
            for (char j ='A'; j < 'I'; j++) {
                board.put("" + j + i, null);
            }
        }
    }

    private int compare (int a, int b) {
        if (a < b) return -1;
        else if (a > b) return 1;
        else return 0;
    }

    private void moveIfAble(String position1, String position2) throws IllegalArgumentException, IllegalChessMoveException {
        if (!board.containsKey(position1)) throw new IllegalArgumentException("Illegal argument");
        ChessPiece piece = board.get(position1);
        piece.move(position2);
        try {
            //udaljenost pozicija po horizontali odnosno vertikali
            int diffChar = position2.charAt(0) - position1.charAt(0), diffNum = position2.charAt(1) - position1.charAt(1);
            //u kojem se smjeru kretati (1 - naprijed, -1 - nazad, 0 - ne kretati se tim pravcem
            int horMove = compare(diffChar, 0), verMove = compare(diffNum, 0);
            if (piece.getClass() != Knight.class) {
                String movePosition = "" + (char) (position1.charAt(0) + horMove) + (char) (position1.charAt(1) + verMove);
                while (movePosition.compareTo(position2) != 0) {
                    if (board.get(movePosition) != null) throw new IllegalChessMoveException("Illegal move");
                    movePosition = "" + (char) (movePosition.charAt(0) + horMove) + (char) (movePosition.charAt(1) + verMove);
                }
            }
            if (board.get(position2) != null && (board.get(position2).getColor() == piece.getColor()
                    || piece.getClass() == Pawn.class && horMove == 0))
                throw new IllegalChessMoveException("Illegal move");
            //provjera da li se pijun kretao ukoso bez da je "pojeo" protivnicku figuru
            if (board.get(position2) == null && piece.getClass() == Pawn.class && horMove != 0)
                throw new IllegalChessMoveException("Illegal move");
        }
        catch (IllegalChessMoveException e) {
            //pijun se ne moze kretati nazad, pa je potrebno kreirati novog
            if (piece.getClass() == Pawn.class)
                board.put(position1, new Pawn(position1, piece.getColor()));
            else piece.move(position1);
            throw e;
        }
    }

    public void move(String position1, String position2) throws IllegalArgumentException, IllegalChessMoveException {
        position1 = position1.toUpperCase();
        position2 = position2.toUpperCase();
        ChessPiece piece1 = board.get(position1);
        moveIfAble(position1, position2);
        ChessPiece piece2 = board.get(position2);
        try {
            board.put(position2, piece1);
            board.put(position1, null);
            if (isCheck(piece1.getColor())) throw new IllegalChessMoveException("Illegal move");
        }
        catch (IllegalChessMoveException e) {
            board.put(position1, piece1);
            board.put(position2, piece2);
            if (piece1.getClass() == Pawn.class)
                board.put(position1, new Pawn(position1, piece1.getColor()));
            else piece1.move(position1);
            throw e;
        }
    }

    private String kingPositionOfColor(ChessPiece.Color color) {
        for (Map.Entry<String, ChessPiece> e : board.entrySet()) {
            if (e.getValue() != null && e.getValue().getClass() == King.class && e.getValue().getColor() == color)
                return e.getKey();
        }
        return null;
    }

    public boolean isCheck(ChessPiece.Color color) {
        String kingPosition = kingPositionOfColor(color);
        if (kingPosition == null) return false;
        for (Map.Entry<String, ChessPiece> e : board.entrySet()) {
            if (e.getValue() != null && e.getValue().getColor() != color) {
                String piecePosition = e.getKey();
                ChessPiece piece = e.getValue();
                try {
                    //ako figura moze bez problema doci na kraljevu poziciju, sah je
                    moveIfAble(piecePosition, kingPosition);
                } catch (IllegalChessMoveException ignore) { continue; }
                //try-catch blok stavljen samo jer metoda move moze (ali nece) baciti izuzetak
                try {
                    if (piece.getClass() == Pawn.class) board.put(piecePosition, new Pawn(piecePosition, piece.getColor()));
                    else piece.move(piecePosition);
                } catch (IllegalChessMoveException ignored) {}
                return true;
            }
        }
        return false;
    }

    public void move(Class type, ChessPiece.Color color, String position) throws IllegalChessMoveException {
        for (Map.Entry<String, ChessPiece> e : board.entrySet()) {
            if (e.getValue() != null && e.getValue().getClass() == type && e.getValue().getColor() == color) {
                try {
                    move(e.getKey(), position);
                    return;
                }
                catch (IllegalChessMoveException ignored) {
                }
            }
        }
        throw new IllegalChessMoveException("Illegal move");
    }

}
