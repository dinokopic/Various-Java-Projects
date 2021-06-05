package ba.unsa.etf.rpr.tutorijal_3;

public class FiksniBroj extends TelefonskiBroj {
    private String fbroj;
    private Grad grad;

    public FiksniBroj(Grad grad, String broj) {
        this.grad = grad; fbroj = broj;
    }

    @Override
    public String ispisi() {
        return grad.getBroj() + "/" + fbroj;
    }

    @Override
    public int hashCode() {
        return this.ispisi().hashCode();
    }

    public Grad getGrad() {
        return grad;
    }

    @Override
    public int compareTo(Object o) {
        FiksniBroj drugi = (FiksniBroj) o;
        /*if (ispisi().compareTo(drugi.ispisi()) > 0) return 1;
        else return -1;*/
        return ispisi().compareTo(drugi.ispisi());
    }

    public enum Grad {
        TRAVNIK("030"),
        ORASJE("031"),
        ZENICA("032"),
        SARAJEVO("033"),
        LIVNO("034"),
        TUZLA("035"),
        MOSTAR("036"),
        BIHAC("037"),
        GORAZDE("038"),
        SIROKIBRIJEG("039");
        private String broj;
        Grad (String b) {
            broj = b;
        }
        public String getBroj() {
            return broj;
        }

    }
}
