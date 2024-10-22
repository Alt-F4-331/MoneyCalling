package Domain;

public class Utilizator extends Entitate {
    private String nume;
    private String prenume;
    private String parola;
    private String email;
    private Data dataNasterii;
    private int venit;
    private String numarTelefon;
    private String domiciliu;
    private int containerEconomii;
    private int dataSalar;

    public Utilizator(int id, String nume, String prenume, String parola, String email, Data dataNasterii, int venit, String numarTelefon, String domiciliu, int containerEconomii, int dataSalar) {
        super(id);
        this.nume = nume;
        this.prenume = prenume;
        this.parola = parola;
        this.email = email;
        this.dataNasterii = dataNasterii;
        this.venit = venit;
        this.numarTelefon = numarTelefon;
        this.domiciliu = domiciliu;
        this.containerEconomii = containerEconomii;
        this.dataSalar = dataSalar;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Data getDataNasterii() {
        return dataNasterii;
    }

    public void setDataNasterii(Data dataNasterii) {
        this.dataNasterii = dataNasterii;
    }

    public int getVenit() {
        return venit;
    }

    public void setVenit(int venit) {
        this.venit = venit;
    }

    public String getNumarTelefon() {
        return numarTelefon;
    }

    public void setNumarTelefon(String numarTelefon) {
        this.numarTelefon = numarTelefon;
    }

    public String getDomiciuliu() {
        return domiciliu;
    }

    public void setDomiciuliu(String domiciuliu) {
        this.domiciliu = domiciuliu;
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
