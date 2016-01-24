
package logiikka;

/**
 *
 * @author xvixvi
 */
public class Nallekarkkikasa {
    Vari vari;
    int koko;
    
    public Nallekarkkikasa(int vari) {
        this.koko = 0;
        this.vari = Vari.values()[vari];
    }
    
    public Nallekarkkikasa(int vari, int koko) {
        this.koko = koko;
        this.vari = Vari.values()[vari];
    }
    
    public Vari getVari() {
        return this.vari;
    }
    
    public int getKoko() {
        return this.koko;
    }
    
    public void setKoko(int uk) {
        this.koko = uk;
    }
    
    public void kasvataYhdella() {
        this.koko += 1;
    }
    
    public void pienennaYhdella() {
        this.koko -= 1;
    }
    
    public boolean onTyhja() {
        return this.koko < 1;
    }
}