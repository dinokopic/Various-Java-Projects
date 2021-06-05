package ba.unsa.etf.rpr.tutorijal_3;

public class MedunarodniBroj extends TelefonskiBroj {
    private String mbroj;
    public MedunarodniBroj(String drzava, String broj) {
        mbroj = drzava + broj;
    }

    @Override
    public String ispisi() {
        return mbroj;
    }

    @Override
    public int hashCode() {
        return mbroj.hashCode();
    }

    @Override
    public int compareTo(Object o) {
        return mbroj.compareTo(o.toString());
    }
}
