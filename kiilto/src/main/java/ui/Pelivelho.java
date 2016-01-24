package ui;

import java.util.Scanner;

/**
 *
 * @author xvixvi
 */
public class Pelivelho {

    Scanner lukija;

    public Pelivelho(Scanner sc) {
        lukija = sc;
    }

    public void alustaPeli() {
        int pm = selvitaPelaajienMaara();
    }

    public int selvitaPelaajienMaara() {
        int pm = -1;

        while (pm == -1) {
        System.out.println("Kuinka monta pelaajaa? (2-4)");
            try {
                pm = Integer.parseInt(lukija.nextLine());
                if (pm < 2 || pm > 4) {
                    throw new Exception();
                }
            } catch (Exception e) {
                pm = -1;
                System.out.println("Huonon syötteen annoit, saisinko paremman?");
                System.out.println("");
            }
        }
        return pm;
    }
}
