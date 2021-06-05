package ba.unsa.etf.rpr;

public class Bishop extends ChessPiece {
    public Bishop(String position, Color color) {
        super(position, color);
    }

    @Override
    public void move(String position) throws IllegalChessMoveException, IllegalArgumentException {
        String oldPosition = this.position.toUpperCase();
        position = position.toUpperCase();
        super.move(position);
        int diffChar = position.charAt(0) - oldPosition.charAt(0);
        int diffNum = position.charAt(1) - oldPosition.charAt(1);
        if (oldPosition.compareTo(position) != 0 && Math.abs(diffChar) == Math.abs(diffNum)) return;
        this.position = oldPosition;
        throw new IllegalChessMoveException("Illegal move");
    }
}
