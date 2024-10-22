package Domain;

/*
 Pentru user am putea sa adaugam inca o clasa numita provioriu "ProfilFinanciar" care sa aiba: venit, dataSalar, containerEconomii
                                                               "User" sa ramana doar cu: nume, prenume, dataNasterii, numarTelefon, domiciliu
 */

public class Utilizator extends Entitate {
    private String nume;
    private String prenume;
    private String parola;
    private String email;
    private Data dataNasterii;

    private String numarTelefon;

    private int idProfil;

    public Utilizator(int id, String nume, String prenume, String parola, String email, Data dataNasterii, String numarTelefon ,int idProfil) {
        super(id);
        this.nume = nume;
        this.prenume = prenume;
        this.parola = parola;
        this.email = email;
        this.dataNasterii = dataNasterii;
        this.numarTelefon = numarTelefon;
        this.idProfil=idProfil;

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


    public int getProfil() {
        return idProfil;
    }

    public void setProfil(int profil) {
        this.idProfil = profil;
    }

    public String getNumarTelefon() {
        return numarTelefon;
    }

    public void setNumarTelefon(String numarTelefon) {
        this.numarTelefon = numarTelefon;
    }


}
