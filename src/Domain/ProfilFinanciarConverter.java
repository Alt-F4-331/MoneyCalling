package Domain;

public class ProfilFinanciarConverter implements EntitateConverter<ProfilFinanciar>{

    @Override
    public String toString(ProfilFinanciar pf)
    {
        return pf.getId() + ", " +  pf.getVenit() + ", " + pf.getDomiciliu() + ", " +pf.getContainerEconomii()
                + ", " + pf.getDataSalar();
    }

    @Override
    public ProfilFinanciar fromString(String line) {

        String[] tokens = line.split(", ");
        int id = Integer.parseInt(tokens[0]);
        float venit = Float.parseFloat(tokens[1]);
        float eco = Float.parseFloat(tokens[3]);
        int dataSalar = Integer.parseInt(tokens[4]);

        return new ProfilFinanciar(id , venit , tokens[2] , eco , dataSalar);//tokens[2] e domiciliu
    }
}
