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
        return null;
    }
}
