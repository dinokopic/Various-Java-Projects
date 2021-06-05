package ba.unsa.etf.rpr.zadaca2;

public class Administrator extends Korisnik {
    public Administrator(String ime, String prezime, String email, String username, String password) {
        super(ime, prezime, email, username, password);
    }

    @Override
    public boolean checkPassword() {
        boolean hasSpecial = false;
        for (char c : getPassword().toCharArray()) {
            if ((c < 'A' || c > 'Z') && (c < 'a' || c > 'z') && (c < '0' || c > '9')) {
                hasSpecial = true;
                break;
            }
        }
        return super.checkPassword() && hasSpecial;
    }
}
