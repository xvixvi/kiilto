package ui.gui;

import java.awt.*;
import javax.swing.*;
import ui.gui.toiminnankuuntelijat.UusintanapinKuuntelija;

/**
 *
 * @author xvixvi
 */
public class Loppuikkuna implements Runnable {

    private final String voittaja;
    private final String voittovalta;
    private final String kierros;
    private Kayttoliittyma kayttoliittyma;
    private final JFrame ruutu;

    public Loppuikkuna(String voittaja, String voittovalta, String kierros, Kayttoliittyma kayttis) {
        this.voittaja = voittaja;
        this.voittovalta = voittovalta;
        this.kierros = kierros;
        kayttoliittyma = kayttis;
        ruutu = new JFrame();
    }

    @Override
    public void run() {
        ruutu.setLayout(new GridLayout(0, 1));
        ruutu.setPreferredSize(new Dimension(450, 300));
        ruutu.setLocation(500, 200);
        
        luoKomponentit();

        ruutu.pack();
        ruutu.setVisible(true);
    }

    private void luoKomponentit() {
        JLabel tekstikentta1 = new JLabel("Pelin voitti pelaaja " + voittaja + " kierroksella " + kierros + "!");
        JLabel tekstikentta2 = new JLabel("Tuomari lähettää erityiskiitoksensa voittajalle:");
        JLabel tekstikentta3 = new JLabel("'Näiden lahjusten turvin ensi perjantaina minä tarjoan!'");
        
        tekstikentta1.setHorizontalAlignment(JLabel.CENTER);
        tekstikentta2.setHorizontalAlignment(JLabel.CENTER);
        tekstikentta3.setHorizontalAlignment(JLabel.CENTER);
        
        JButton uudestaan = new JButton("Haluan revanssin!");
        uudestaan.addActionListener(new UusintanapinKuuntelija(kayttoliittyma, this));
        JButton lopeta = new JButton("Ei ikinä enää, lopeta, LOPETA!");
        lopeta.addActionListener(new UusintanapinKuuntelija(kayttoliittyma, this));

        ruutu.add(tekstikentta1);
        ruutu.add(tekstikentta2);
        ruutu.add(tekstikentta3);
        ruutu.add(uudestaan);
        ruutu.add(lopeta);
    }

    public void tuhoa() {
        ruutu.setVisible(false);
        ruutu.dispose();
    }
}