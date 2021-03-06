package tiralabra.tietorakenteet.apurakenteet;

import java.util.Iterator;

/**
 * Created by vili on 23.8.2017.
 */
public class Lista<E> {

    private Object[] dataelementti;
    private int viimIndeksi = -1; //-1 -> tyhjä lista
    private int kursori = 0;

    public Lista() {
        this.dataelementti = new Object[10];
    }

    public boolean onTyhja() {
        return viimIndeksi < 0;
    }

    public int koko() {
        return viimIndeksi + 1;
    }

    public void lisaa(E asia) {
        if (!onkoTilaa(viimIndeksi + 1)) {
            tuplaaPituus();
        }
        dataelementti[++viimIndeksi] = asia;
    }

    public E haeViimeinen() {
        return (E) dataelementti[viimIndeksi];
    }

    public E haeEnsimmainen() {
        return (E) dataelementti[0];
    }

    /**
     *
     * @param indeksi josta haetaan
     * @return objekti jos on mitä antaa, null jos ei ole
     */
    public E haeIndeksista(int indeksi) {
        if (onkoIndeksiListalla(indeksi)) {
            return (E) dataelementti[indeksi];
        } else
            return null;
    }

    /**
     * Tuplaa listan käyttämän dataelementin pituuden. Eli jos listalla on 8 oliota ja dataelementin pituus on 10,
     * niin tämän metodin suorituksen jälkeen elementin pituus on 20, ei 16.
     */
    protected void tuplaaPituus() {
        Object[] uusiTaulukko = new Object[2 * dataelementti.length];
        dataelementti = kopioiUuteen(uusiTaulukko, dataelementti);
    }

    protected boolean onkoTilaa(int pituus) {
        return dataelementti.length > pituus;
    }

    /**
     * Kopioi annetun tyypin otuksia taulukosta toiseen.
     * @param minne taulukko, johon kopioidaan
     * @param mista taulukko, josta kopioidaan
     * @return taulukko, mihin kopioitiin
     */
    protected static Object[] kopioiUuteen(Object[] minne, Object[] mista) {
        for (int i = 0; i < mista.length; i++) {
            minne[i] = mista[i];
        }
        return minne;
    }

    /**
     *
     * @param indeksi jota tarkastellaan
     * @return boolean onko indeksi listan alun ja lopun välissä (alku ja loppu mukaanluettuina)
     */
    protected boolean onkoIndeksiListalla(int indeksi) {
        return indeksi >= 0 && indeksi <= viimIndeksi && (indeksi != 0 || dataelementti[0] != null);
    }

    protected int getElementinKoko() {
        return dataelementti.length;
    }
}
