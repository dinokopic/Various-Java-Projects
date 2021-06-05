package ba.unsa.etf.rpr.tutorijal_3;

import java.util.HashMap;

public class MobilniBroj extends TelefonskiBroj {
    private String mbroj;

    public MobilniBroj(int mobilnaMreza, String broj) {
        mbroj = "0" + mobilnaMreza + "/" + broj;
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
