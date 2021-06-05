package ba.unsa.etf.rpr.tutorijal8;

import java.util.ArrayList;

public class UN {
    private ArrayList<Drzava> drzave;

    public UN(ArrayList<Drzava> drzave) {
        this.drzave = drzave;
    }

    public UN() {
        drzave=new ArrayList<>();
    }

    public ArrayList<Drzava> getDrzave() {
        return drzave;
    }

    public void setDrzave(ArrayList<Drzava> drzave) {
        this.drzave = drzave;
    }
}

