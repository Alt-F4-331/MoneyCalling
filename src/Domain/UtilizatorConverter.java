package Domain;

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
        Data dataNasterii = new Data(Integer.parseInt(tokens[5]), Integer.parseInt(tokens[6]), Integer.parseInt(tokens[7]));
        //creez un obiect ProfilFinanciar
        ProfilFinanciar profilFinanciar= new ProfilFinanciar(Integer.parseInt(tokens[10]),Integer.parseInt(tokens[11]), tokens[12] ,Integer.parseInt(tokens[13]),Integer.parseInt(tokens[14]));
        //creez obiectul de tip utilizator
        return new Utilizator(Integer.parseInt(tokens[0]), tokens[1], tokens[2], tokens[3], tokens[4], dataNasterii,tokens[8],  tokens[9], profilFinanciar);
    }
}
