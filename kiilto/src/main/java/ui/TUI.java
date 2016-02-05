package ui;

import java.util.*;
import logiikka.*;
import logiikka.valineluokat.*;

/**
 *
 * @author xvixvi
 */
public class TUI {

    Scanner lukija;
    Pelivelho pv;

    public TUI(Scanner sc) {
        lukija = sc;
        
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

    public ArrayList<String> selvitaPelaajienNimet(int pm) {
        ArrayList<String> nimet = new ArrayList<>();
        for (int i = 0; i < pm; i++) {
            System.out.println("Anna pelaaja nro" +(i+1) +" nimi:");
            String nimi = lukija.nextLine();
            if (nimi.isEmpty())  break;
            
            nimet.add(nimi);
        }
        return nimet;
    }

    public int selvitaVoittoonTarvittavaValta() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int pelaajanToimi() {
        int v = 0;
        while (v < 1 || v > 3) {
            try {
                System.out.println("Mitä haluat tehdä? Valitse kokonaisluku 1-3:");
                System.out.println("  1. nosta nallekarkkeja markkinoilta ('1')");
                System.out.println("  2. osta omaisuutta ('2')");
                System.out.println("  3. varaa omaisuutta ('3')");
                v = Integer.parseInt(lukija.nextLine());
                if (v<1 || v>3) throw new IllegalArgumentException();
            } catch (Exception e) {
                v = 0;
                System.out.println("Koitappa uudestaan.");
            }
        }
        return v;
    }

    public int[] mitaKarkkejaNostetaan(Kasakokoelma markkinat) {
        int[] nosto = new int[5];
        
        while (true) {
            System.out.println("Mitä nallekarkkeja haluaisit? ('o' -ohjeet)");
            String syote = lukija.nextLine();
            if (syote.equalsIgnoreCase("o")) {
                tulostaKarkkienNostamisOhjeet();
            } else {
                try {
                    nosto = tarkistaKarrkienNostamisSyote(syote, markkinat);
                    break;
                } catch (Exception e) {
                    System.out.println("Syötteesi oli huono.\n" + e.getMessage()+"\n");
                }
            }
        }
        return nosto;
    }
    
    private int[] tarkistaKarrkienNostamisSyote(String syote, Kasakokoelma markkinat) throws Exception {
        int[] nosto = new int[5];
        int summa = 0;
        boolean sama = false;
        
        String[] maarat = syote.split(",", 5);
                    
        for (int i = 0; i < 5; i++) {
            int maara = Integer.parseInt(maarat[i].trim());
            if (maara<0 || maara>2) throw new IllegalArgumentException("luvut voivat olla 0,1 tai 2.");
            if (maara == 2) sama = true;
            summa += maara;
            if (summa > 3) throw new IllegalArgumentException("eipäs rohmuta!");
            if (sama && summa > 2) throw new IllegalArgumentException("eipäs rohmuta!");
            if (markkinat.getKasanKoko(i+1)< maara) throw new IllegalArgumentException("liian vähän karkkeja markkinoilla!");
            if (maara == 2 && markkinat.getKasanKoko(i+1) < 4) throw new IllegalArgumentException("kasassa on liian vähän karkkeja kahden ottamiseen!");
            //syöte on ollu tähän numeroon mennessä hyväksyttävä
            nosto[i] = maara;
        }
        if (sama && summa!=2) throw new IllegalArgumentException();
        if (!sama && summa !=3) throw new IllegalArgumentException();
        
        return nosto;
    }
     
    private void tulostaKarkkienNostamisOhjeet() {
        System.out.println("\nValitse nostettavat nallekarkit antamalla syöte\n"
                + "  'x1,x2,x3,x4,x5'\n"
                + "missä x1 on valkoisten, x2 sinisten, x3 vihreiden,\n"
                + "x4 punaisten ja x5 mustien nallekarkkien määrä\n"
                + "kokonaisluvulla kuvattuna.");
        System.out.println("  Voit nostaa joko kolmea eriväristä nallekarkkia\n"
                + "yhden kutakin väriä (yhteensä 3 karkkia), tai vaihtoehtoisesti\n"
                + "kaksi samanväristä nallekarkkia (yhteensä 2 karkkia).\n"
                + "Jos nostat 2kpl samanvärisiä, tulee markkinoilla olla\n"
                + "vähintään 4kpl kyseistä väriä, jotta siirto on laillinen.");
        System.out.println("\n'm' tulostaa markkinatilanteen.\n");
    }
        
}
