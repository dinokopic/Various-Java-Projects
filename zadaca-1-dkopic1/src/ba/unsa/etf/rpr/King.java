package ba.unsa.etf.rpr;

public class King extends ChessPiece{
    public King(String position, ChessPiece.Color color) {
        super(position, color);
    }

    @Override
    public void move(String position) throws IllegalChessMoveException, IllegalArgumentException {
        String oldPosition = this.position.toUpperCase();
        position = position.toUpperCase();
        super.move(position);
        int diffChar = position.charAt(0) - oldPosition.charAt(0);
        int diffNum = position.charAt(1) - oldPosition.charAt(1);
        if (oldPosition.compareTo(position) != 0 && (diffChar >= -1 && diffChar <= 1) && (diffNum >= -1 && diffNum <=1)) return;
        this.position = oldPosition;
        throw new IllegalChessMoveException("Illegal move");
    }
}
