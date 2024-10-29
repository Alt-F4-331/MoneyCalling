package com.example.moneycalling_spring.Domain;

public class RaportConverter implements EntitateConverter<Raport>{
    @Override
    public String toString(Raport r){
        return r.getId() + ", " + r.getIdDiagrama();
        //converteste un obiect de tip RAPORT in fisier text
    }
    //pentru fisier text
    @Override
    public Raport fromString(String line) {
        String[] tokens = line.split(", ");
        return new Raport(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]));
        //converteste dintr-un fisier text un obiect de tip RAPORT ezpz
    }
}
