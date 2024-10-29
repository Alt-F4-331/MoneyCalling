package com.example.moneycalling_spring.Domain;

public class UtilizatorConverter implements EntitateConverter<Utilizator> {

    @Override
    public String toString(Utilizator utilizator) {


        return utilizator.getId() + ", " + utilizator.getNume() + ", " + utilizator.getPrenume() + ", " + utilizator.getParola() + ", " + utilizator.getEmail() + ", " + utilizator.getDataNasterii().toString()
                + ", "+ utilizator.getSex() + ", " + utilizator.getNumarTelefon()  + ", " + utilizator.getProfil().toString();
    }

    @Override
    public Utilizator fromString(String line) {
        //impart string-ul in tokens, ma iau dupa separarea prin virgula
        String[] tokens = line.split(", ");
        //token[5] contine data, pe care trebuie sa o impart din nou, pentru a putea salva informatiile
        //creez un obiect de tip data, cu ajutorul a ceea ce am splituit
        String[] data = tokens[5].split("\\.");
        Data dataNasterii = new Data(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2]));
        //creez un obiect ProfilFinanciar
        ProfilFinanciar profilFinanciar= new ProfilFinanciar(Integer.parseInt(tokens[8]),Float.parseFloat(tokens[9]), tokens[10] ,Float.parseFloat(tokens[11]),Integer.parseInt(tokens[12]));
        //creez obiectul de tip utilizator
        return new Utilizator(Integer.parseInt(tokens[0]), tokens[1], tokens[2], tokens[3], tokens[4], dataNasterii,tokens[6],  tokens[7], profilFinanciar);
    }
}
