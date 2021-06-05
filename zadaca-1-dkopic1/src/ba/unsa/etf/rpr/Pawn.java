package ba.unsa.etf.rpr;

public class Pawn extends ChessPiece {
    public Pawn(String position, Color color) {
        super(position, color);
    }

    @Override
    public void move(String position) throws IllegalChessMoveException, IllegalArgumentException {
        String oldPosition = this.position.toUpperCase();
        position = position.toUpperCase();
        super.move(position);
        //inverzni odredjuje da li je pijun crne ili bijele boje
        int inverzni = (color == Color.BLACK) ? -1 : 1;
        int diffChar = position.charAt(0) - oldPosition.charAt(0);
        int diffNum = position.charAt(1) - oldPosition.charAt(1);
        if ((oldPosition.charAt(1) == '2' && inverzni == 1 || oldPosition.charAt(1) == '7' && inverzni == -1)
                && diffNum == 2 * inverzni && diffChar == 0) return;
        if (diffNum == inverzni && diffChar >= -1 && diffChar <= 1) return;
        this.position = oldPosition;
        throw new IllegalChessMoveException("Illegal move");
    }
}
