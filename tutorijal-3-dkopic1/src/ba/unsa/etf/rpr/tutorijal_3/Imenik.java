package ba.unsa.etf.rpr.tutorijal_3;

import java.util.*;

public class Imenik {

    private Map<TelefonskiBroj, String> imenik;

    public Imenik () {
        imenik = new HashMap<>();
    }

    public String dajBroj(String ime) {
        for (Map.Entry<TelefonskiBroj, String> e : imenik.entrySet()) {
            if (e.getValue().equals(ime))
                return e.getKey().ispisi();
        }
        return "";
    }

    public String dajIme(TelefonskiBroj broj) {
        for (Map.Entry<TelefonskiBroj, String> e : imenik.entrySet()) {
            if (e.getKey().equals(broj))
                return e.getValue();
        }
        return "";
    }

    public void dodaj(String ime, TelefonskiBroj broj) {
        imenik.put(broj, ime);
    }

    public Set<String> izGrada(FiksniBroj.Grad grad) {
        Set<String> set = new HashSet<>();
        for (Map.Entry<TelefonskiBroj, String> e : imenik.entrySet()) {
             if (e.getKey() instanceof FiksniBroj
                     && grad.getBroj().equals(((FiksniBroj) e.getKey()).getGrad().getBroj())) {
                 set.add(e.getValue());
             }
        }
        return set;
    }

    public Set<TelefonskiBroj> izGradaBrojevi(FiksniBroj.Grad grad) {
        Set<TelefonskiBroj> set = new TreeSet<>();
        for (Map.Entry<TelefonskiBroj, String> e : imenik.entrySet()) {
            if (e.getKey() instanceof FiksniBroj
                    && grad.getBroj().equals(((FiksniBroj) e.getKey()).getGrad().getBroj())) {
                set.add(e.getKey());
            }
        }
        return set;
    }

    public String naSlovo(char s) {
        String ispis = "";
        int i = 1;
        for (Map.Entry<TelefonskiBroj, String> e : imenik.entrySet()) {
            if (e.getValue().charAt(0) == s) {
                ispis += i + ". " + e.getValue() + " - " + e.getKey().ispisi() + "\n";
                i++;
            }
        }
        return ispis;
    }
}
