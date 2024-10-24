package Domain;

public class UtilizatorConverter implements EntitateConverter<Utilizator> {

    @Override
    public String toString(Utilizator utilizator) {


        return utilizator.getId() + ", " + utilizator.getNume() + ", " + utilizator.getPrenume() + ", " + utilizator.getParola() + ", " + utilizator.getEmail() + ", " + utilizator.getDataNasterii().toString() +  ", " + utilizator.getNumarTelefon()  +
                "," + utilizator.getProfil();
    }

    @Override
    public Utilizator fromString(String line) {
        //impart string-ul in tokens, ma iau dupa separarea prin virgula
        String[] tokens = line.split(", ");
        //token[5] contine data, pe care trebuie sa o impart din nou, pentru a putea salva informatiile
        //creez un obiect de tip data, cu ajutorul a ceea ce am splituit
        Data dataNasterii = new Data(Integer.parseInt(tokens[5]), Integer.parseInt(tokens[6]), Integer.parseInt(tokens[7]));
        //creez obiectul de tip utilizator
        return new Utilizator(Integer.parseInt(tokens[0]), tokens[1], tokens[2], tokens[3], tokens[4], dataNasterii,  tokens[8], Integer.parseInt(tokens[9]));
    }
}
