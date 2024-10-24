package Domain;

public class ProfilFinanciarConverter implements EntitateConverter<ProfilFinanciar>{

    @Override
    public String toString(ProfilFinanciar pf)
    {
        return Integer.toString(pf.getId()) + "," +  Integer.toString(pf.getVenit()) + "," + pf.getDomiciliu() + "," + Integer.toString(pf.getContainerEconomii())
                + "," + Integer.toString(pf.getDataSalar());
    }

    @Override
    public ProfilFinanciar fromString(String line) {

        String[] tokens = line.split(",");
        int id = Integer.parseInt(tokens[0]);
        int venit = Integer.parseInt(tokens[1]);
        int eco = Integer.parseInt(tokens[3]);
        int dataSalar = Integer.parseInt(tokens[4]);


        return new ProfilFinanciar(id , venit , tokens[2] , eco , dataSalar);//tokens[2] e domiciliu
    }
}
