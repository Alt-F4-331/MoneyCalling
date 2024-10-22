package Domain;

public class Data {
    private int zi;
    private int luna;
    private int an;

    public Data (int zi, int luna, int an) {
        this.zi = zi;
        this.luna = luna;
        this.an = an;
    }

    public int getZi() {
        return this.zi;
    }

    public int getLuna() {
        return this.luna;
    }

    public int getAn() {
        return this.an;
    }

    public void setZi(int zi) {
        this.zi = zi;
    }

    public void setLuna(int luna) {
        this.luna = luna;
    }

    public void setAn(int an) {
        this.an = an;
    }
}
