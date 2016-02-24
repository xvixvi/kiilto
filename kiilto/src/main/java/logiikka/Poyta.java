package logiikka;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.util.*;
import logiikka.omaisuusluokat.*;
import logiikka.valineluokat.*;
import ui.gui.Piirtoavustaja;

/**
 *
 * @author xvixvi
 */
public class Poyta {

    private final ArrayList<Pelaaja> pelaajat;
    private final Kasakokoelma karkkimarkkinat;
    private ArrayList<Omaisuus> omistuspakat;
    private final Random arpoja = new Random();
    private final ArrayList<Merkkihenkilo> merkkihenkilot;

    Poyta(ArrayList<Pelaaja> pelaajat) {
        this.pelaajat = pelaajat;
        karkkimarkkinat = new Kasakokoelma(pelaajat.size());
        merkkihenkilot = new ArrayList<>();
        alustaOmistuspakat();
        luoOmistukset(luoLukija("src/aputiedostoja/omistustentiedot.csv"));
        sekoitaOmistuspakat();
        luoMerkkihenkilot(luoLukija("src/aputiedostoja/merkkihenkilot.csv"));
        valitsePelinMerkkihenkilot();
    }

    private void alustaOmistuspakat() {
        omistuspakat = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            omistuspakat.add(new Omaisuus());
        }
    }

    /**
     * Yrittää luoda lukijan annetusta tiedostopolusta.
     *
     * @param filePath (suhteellinen)tiedostopolku.
     * @return Scanner lukija, joka lukee parametrina annetusta tiedostosta.
     */
    public Scanner luoLukija(String filePath) {
        Scanner lukija = null;
        try {
            File f = new File(filePath);
            lukija = new Scanner(f);

            /*
             yritys olisi kova muokata koodia siten,
             että se lukee tiedoston
             muissakin koneissa kuin alkuperäisessä,
             vaan kun ei toimi niin ei toimi...
             */
//            File file = new File(getClass().getResource("/resources/classes/aputiedostoja/filename.file").getFile());
//            lukija = new Scanner(f);
        } catch (Exception e) {
            System.out.println("lukuongelmia @ Poyta:luoLukija,\n syötteellä " + filePath + "\n" + e);
        }

        return lukija;
    }

    private void luoOmistukset(Scanner lukija) {
        for (int i = 0; i < 3; i++) {
            Omaisuus omistuspakka = omistuspakat.get(i);
            int omistustenMaara = 40;
            if (i == 1) {
                omistustenMaara = 30;
            }
            if (i == 2) {
                omistustenMaara = 20;
            }

            while (omistustenMaara > 0) {
                omistuspakka.lisaaOmistus(luoOmistus(lukija.nextLine()));
                omistustenMaara--;
            }
        }
    }

    private Omistus luoOmistus(String rivi) {
        String[] palat = rivi.split(",", 9);
        Kasakokoelma hinta = new Kasakokoelma(palat);
        return new Omistus(palat[0], Integer.parseInt(palat[1]), palat[2], hinta);
    }

    public ArrayList<Omaisuus> getOmistuspakat() {
        return omistuspakat;
    }

    /**
     * Sekoittaa omistuspakat.
     */
    public void sekoitaOmistuspakat() {
        for (Omaisuus o : omistuspakat) {
            sekoitaOmistuspakka(o);
        }
    }

    private void sekoitaOmistuspakka(Omaisuus op) {
        op.sekoita(arpoja);
    }

    private void luoMerkkihenkilot(Scanner lukija) {
        for (int i = 0; i < 10; i++) {
            Merkkihenkilo m = luoMerkkihenkilo(lukija.nextLine());
            merkkihenkilot.add(m);
        }
    }

    private Merkkihenkilo luoMerkkihenkilo(String rivi) {
        String[] palat = rivi.split(",", 9);
        int[] vaatimus = new int[6];
        for (int i = 3; i < 8; i++) {
            vaatimus[i - 2] = Integer.parseInt(palat[i]);
        }
        return new Merkkihenkilo(palat[0], vaatimus, Integer.parseInt(palat[1]));
    }

    private void valitsePelinMerkkihenkilot() {
        int henkiloita = 5;
        if (pelaajat.size() == 3) {
            henkiloita = 4;
        }
        if (pelaajat.size() == 2) {
            henkiloita = 2;
        }

        while (merkkihenkilot.size() > henkiloita) {
            int poistettava = arpoja.nextInt(merkkihenkilot.size());
            merkkihenkilot.remove(poistettava);
        }
    }

    public void vaikuttikoPelaajaMerkkihenkilon(Pelaaja vuorossaOleva) {
        ArrayList<Merkkihenkilo> vaikuttuneet = new ArrayList<>();
        for (Merkkihenkilo mh : merkkihenkilot) {
            if (mh.vaikuttuukoOmaisuudesta(vuorossaOleva.getOmaisuus())) {
                vaikuttuneet.add(mh);
            }
        }

        while (!vaikuttuneet.isEmpty()) {
            vuorossaOleva.merkkihenkiloVierailee(vaikuttuneet.remove(0));
        }
    }

    @Override
    public String toString() {
        String s = "Pöydässä pelaajat:\n";
        for (Pelaaja p : pelaajat) {
            s = s.concat(p + "\n");
        }

        s = s.concat("Merkkihenkilöt:\n");
        for (int i = 0; i < merkkihenkilot.size(); i++) {
            s = s.concat(merkkihenkilot.get(i).toString() + "\n");
        }

        for (int i = omistuspakat.size() - 1; i > -1; i--) {
            s = s.concat("Omistuspakasta " + (i + 1) + ":\n\n");
            Omaisuus oma = omistuspakat.get(i);
            s = s.concat(oma.paallimmaisetToString() + "\n");
        }
        s = s.concat("***\n");
        s = s.concat("karkkimarkkinat:\n" + karkkimarkkinat.toString() + "\n");
        s = s.concat("***\n");
        return s;
    }

    public Kasakokoelma getMarkkinat() {
        return karkkimarkkinat;
    }

    public ArrayList<String> getNakyvienNimet() {
        ArrayList<String> nimet = new ArrayList<>();
        for (Omaisuus o : omistuspakat) {
            nimet.addAll(o.getPaallimmaistenNimet());
        }
        return nimet;
    }

    /**
     *
     * @param pelaaja Joka ostaa omaisuutta pöydältä.
     * @param ostonNumero Joka ostetaan.
     * @return boolean Onnistuiko osto.
     */
    public boolean suoritaOsto(Pelaaja pelaaja, int ostonNumero) {
        Omaisuus omistuspakka;
        if (!this.getNakyvienNimet().contains("" + ostonNumero)) {
            return false;
        }

        if (ostonNumero < 41) {
            omistuspakka = omistuspakat.get(0);
        } else if (ostonNumero < 71) {
            omistuspakka = omistuspakat.get(1);
        } else {
            omistuspakka = omistuspakat.get(2);
        }

        //kysytään pelaajalta, onko hänellä varaa ostaa nimeämänsä omistus
        Omistus o = omistuspakka.getNakyvaOmistus(ostonNumero);
        if (pelaaja.onkoVaraa(o)) {
            pelaaja.osta(omistuspakka, o, karkkimarkkinat);
            return true;
        }
        return false;
    }

    /**
     *
     * @param pelaaja joka yrittää lunastaa varauksensa.
     * @param varauksenNumero varaus, jota lunastetaan.
     * @return Boolean onnistuiko yritys.
     */
    public boolean suoritaOstoVarauksista(Pelaaja pelaaja, int varauksenNumero) {
        if (!pelaaja.getVaraustenNimet().contains("" + varauksenNumero)) {
            return false;
        }

        Omistus o = pelaaja.getVaraus(varauksenNumero);
        if (pelaaja.onkoVaraa(o)) {
            return pelaaja.ostaVaraus(o, karkkimarkkinat);
        }
        return false;
    }

    /**
     *
     * @param pelaaja
     * @param varauksenNro varattavan varauksen numero (eli nimi).
     * @return boolean, onnistuiko varaaminen.
     */
    public boolean teeVaraus(Pelaaja pelaaja, int varauksenNro) {
        if (pelaaja == null) {
            return false;
        }
        if (pelaaja.getVarauksienMaara() >= 3) {
            return false;
        }
        int pakka = 0;
        if (varauksenNro > 40) {
            pakka = 1;
        }
        if (varauksenNro > 70) {
            pakka = 2;
        }
        Omaisuus omistusPakka = omistuspakat.get(pakka);

        Omistus varattava = omistusPakka.getNakyvaOmistus(varauksenNro);
        if (varattava == null) {
            return false;
        }
        if (!pelaaja.teeVaraus(varattava)) {
            return false;
        }
        omistusPakka.poistaOmistus(varattava);
        annaPelaajalleKulta(pelaaja);

        return true;
    }

    /**
     * Jos karkkimarkkinoilla on kultaa, pelaaja saa yhden kultakarkin!
     *
     * @param pelaaja Pelaaja, jolle karkki annetaan, jos sellainen löytyy.
     */
    private void annaPelaajalleKulta(Pelaaja pelaaja) {
        if (karkkimarkkinat.getKasanKoko(0) > 0) {
            karkkimarkkinat.siirraToiseenKasaan(pelaaja.getKarkit(), 0, 1);
        }
    }

    /**
     * Piirtää pöydällä olevat asiat: merkkihenkilöt, omistuspakat, näkyvillä
     * olevat omistukset ja karkkimarkkinat.
     *
     * @param graphics Grafiikat.
     * @param va VarinAsettaja, hyödyllinen kaveri kun piirretään jotain
     * karkkeihin liittyvää.
     */
    public void piirra(Graphics graphics, Piirtoavustaja va) {
        int x = 20;
        int y = 10;
        piirraMerkkihenkilot(graphics, va, x, y);
        y += 120;
        x -= 10;
        piirraOmistuspakat(graphics, x, y);
        x += 110;
        piirraNakyvatOmistukset(graphics, va, x, y);
        x += 450;
        karkkimarkkinat.piirraIsosti(graphics, va, x, y);
        y = 20;
        x += 80;
        piirraPelaajat(graphics, va, x, y);
    }

    private void piirraOmistuspakat(Graphics graphics, int x, int y) {
        for (int i = 0; i < 3; i++) {
            if (omistuspakat.get(2 - i).getKoko() > 4) {
                graphics.setColor(Color.gray);
                graphics.fill3DRect(x, y, 100, 125, true);
                graphics.setColor(Color.black);
                graphics.drawString("KIILTO", x + 33, y + 66);
                graphics.drawOval(x + 22, y + 48, 55, 25);
                y += 135;
            }
        }
    }

    private void piirraMerkkihenkilot(Graphics graphics, Piirtoavustaja va, int x, int y) {
        for (Merkkihenkilo mh : merkkihenkilot) {
            mh.piirra(graphics, va, x, y);
            x += 100;
        }
    }

    private void piirraNakyvatOmistukset(Graphics graphics, Piirtoavustaja va, int x, int y) {
        for (int i = 2; i > -1; i--) {
            omistuspakat.get(i).piirraNakyvatOmistukset(graphics, va, x, y);
            y += 135;
        }
    }

    private void piirraPelaajat(Graphics graphics, Piirtoavustaja pa, int x, int y) {
        for (Pelaaja pelaaja : pelaajat) {
            pelaaja.piirra(graphics, pa, x, y);
            y += 150;
        }
    }

    void luoTestitilanneKeskipelista() {
        for (Pelaaja pelaaja : pelaajat) {

            for (int i = 0; i < 6; i++) {
                karkkimarkkinat.siirraToiseenKasaan(pelaaja.getKarkit(), i, 2);
            }

            ArrayList<String> nakyvat = omistuspakat.get(2).getPaallimmaistenNimet();
            for (String nakyva : nakyvat) {
                teeVaraus(pelaaja, Integer.parseInt(nakyva));
            }

            nakyvat = omistuspakat.get(0).getPaallimmaistenNimet();
            for (String nakyva : nakyvat) {
                suoritaOsto(pelaaja, Integer.parseInt(nakyva));
            }
        }
    }
}
