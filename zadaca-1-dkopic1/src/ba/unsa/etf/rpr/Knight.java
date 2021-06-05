package ba.unsa.etf.rpr;
import java.lang.Math;

public class Knight extends ChessPiece {
    public Knight(String position, Color color) {
        super(position, color);
    }

    @Override
    public void move(String position) throws IllegalChessMoveException, IllegalArgumentException {
        String oldPosition = this.position.toUpperCase();
        position = position.toUpperCase();
        super.move(position);
        int diff = position.charAt(0) - oldPosition.charAt(0);
        int diff1 = position.charAt(1) - oldPosition.charAt(1);
        if ((Math.abs(diff) == 2 && Math.abs(diff1) == 1) || (Math.abs(diff) == 1 && Math.abs(diff1) == 2)) return;
        this.position = oldPosition;
        throw new IllegalChessMoveException("Illegal move");
    }
}
