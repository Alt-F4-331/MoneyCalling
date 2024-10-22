package Domain;

public class UtilizatorConverter implements EntitateConverter<Utilizator> {

    @Override
    public String toString(Utilizator utilizator) {
        return utilizator.getId() + ", " + utilizator.getNume() + ", " + utilizator.getPrenume() + ", " + utilizator.getParola() + ", " + utilizator.getEmail() + ", " + utilizator.getDataNasterii() + ", " + utilizator.getVenit() + ", " + utilizator.getNumarTelefon() + ", " + utilizator.getDomiciuliu() + ", " + utilizator.getContainerEconomii() + ", " + utilizator.getDataSalar();
    }

    @Override
    public Utilizator fromString(String line) {
        //impart string-ul in tokens, ma iau dupa separarea prin virgula
        String[] tokens = line.split(", ");
        //token[5] contine data, pe care trebuie sa o impart din nou, pentru a putea salva informatiile
        String[] data = tokens[5].split(".");
        //creez un obiect de tip data, cu ajutorul a ceea ce am splituit
        Data dataNasterii = new Data(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2]));
        //creez obiectul de tip utilizator
        return new Utilizator(Integer.parseInt(tokens[0]), tokens[1], tokens[2], tokens[3], tokens[4], dataNasterii, Integer.parseInt(tokens[6]), tokens[7], tokens[8], Integer.parseInt(tokens[9]), Integer.parseInt(tokens[10]));
    }
}
