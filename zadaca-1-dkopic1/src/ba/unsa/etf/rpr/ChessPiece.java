package ba.unsa.etf.rpr;

public abstract class ChessPiece {
    protected String position;
    protected Color color;

    private void positionTest(String position) throws IllegalArgumentException {
        position = position.toUpperCase();
        if (position.length() != 2 || position.charAt(0) < 'A' || position.charAt(0) > 'H' ||
                position.charAt(1) < '1' || position.charAt(1) > '8')
            throw new IllegalArgumentException(position);
    }

    public ChessPiece(String position, Color color) {
        positionTest(position);
        this.position = position.toUpperCase();
        this.color = color;
    }

    public static enum Color{
        BLACK, WHITE
    };
    public String getPosition() {
        return position;
    }
    public Color getColor() {
        return color;
    }

    public void move(String position) throws IllegalChessMoveException, IllegalArgumentException {
        positionTest(position);
        this.position = position;
    }

}
