package main.ui.gui.toiminnankuuntelijat;

import main.ui.gui.Valintanapit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Kuuntelija omaisuudenvalinnan selausnapeille.
 *
 * @author xvixvi
 */
public class SelausnapinKuuntelija implements ActionListener {

    private final boolean vasen;
    private final Valintanapit valintanapit;

    /**
     * Luo kuuntelijan.
     * 
     * @param vasen selataanko vasemmalle vai oikealle.
     * @param vn isäntäolio, jonka arvoa selataan.
     */
    public SelausnapinKuuntelija(boolean vasen, Valintanapit vn) {
        this.vasen = vasen;
        valintanapit = vn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (vasen) {
            valintanapit.pienennaIndeksia();
        } else {
            valintanapit.kasvataIndeksia();
        }
    }

}
