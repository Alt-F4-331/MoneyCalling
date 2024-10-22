package Domain;

public class ProfilFinanciar extends Entitate{

    private int venit;

    private String domiciliu;
    private int containerEconomii;
    private int dataSalar;

    public ProfilFinanciar(int id, int venit, String domiciliu, int containerEconomii, int dataSalar) {
        super(id);
        this.venit = venit;
        this.domiciliu = domiciliu;
        this.containerEconomii = containerEconomii;
        this.dataSalar = dataSalar;
    }

    public int getVenit() {
        return venit;
    }

    public void setVenit(int venit) {
        this.venit = venit;
    }

    public String getDomiciliu() {
        return domiciliu;
    }

    public void setDomiciliu(String domiciliu) {
        this.domiciliu = domiciliu;
    }

    public int getContainerEconomii() {
        return containerEconomii;
    }

    public void setContainerEconomii(int containerEconomii) {
        this.containerEconomii = containerEconomii;
    }

    public int getDataSalar() {
        return dataSalar;
    }

    public void setDataSalar(int dataSalar) {
        this.dataSalar = dataSalar;
    }
}
