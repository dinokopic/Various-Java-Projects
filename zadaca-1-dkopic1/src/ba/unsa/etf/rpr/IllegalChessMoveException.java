package ba.unsa.etf.rpr;

public class IllegalChessMoveException extends Exception {
    private String message;
    public IllegalChessMoveException(String position) {
        this.message = position;
    }

    @Override
    public String toString() {
        return message;
    }
}
