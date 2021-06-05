package ba.unsa.etf.rpr.tutorijal8;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Tutorijal {

    public static void main (String[] args) {
        ArrayList<Grad> gradovi = ucitajGradove();
        for (Grad g : gradovi) {
            System.out.println(g.getNaziv() + " " + Arrays.toString(g.getTemperature()) + " " + g.getBrojStanovnika());
        }
    }

    public static ArrayList<Grad> ucitajGradove() {
        ArrayList<Grad> gradovi = new ArrayList<>();
        try {
            Scanner datoteka = new Scanner(new File("mjerenja.txt"));
            while (datoteka.hasNextLine()) {
                String[] sadrzaj = datoteka.nextLine().split(",");
                Grad grad = new Grad();
                double[] temps = Stream.of(sadrzaj)
                        .skip(1)
                        .mapToDouble(Double::parseDouble)
                        .limit(1001)
                        .toArray();
                grad.setNaziv(sadrzaj[0]);
                grad.setTemperature(temps);
                grad.setBrojStanovnika(0);
                gradovi.add(grad);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return gradovi;
    }

}
