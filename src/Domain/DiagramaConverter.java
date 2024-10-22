package Domain;

import java.util.ArrayList;

public class DiagramaConverter implements EntitateConverter<Diagrama>{

    @Override
    public String toString(Diagrama d)
    {
        ///converteste un obiect in string
        String element = Integer.toString(d.getId());
        for(Cheltuiala c:d.getListaCheltuieli())
        {
            //pt ca e lista,trebuie adaugate elemente de pe ficere pozitie
            element=element+ ","+ c.getId()+c.getNume()+","+c.getSuma();
        }
        return element;
    }

    @Override
    public Diagrama fromString(String line)
    {
        //trecem din string in obiect
        String[] tokens=line.split(",");

        //split dupa virgula

        int id = Integer.parseInt(tokens[0]);
        int idUser=Integer.parseInt(tokens[1]);
        //primele 3 elemente sunt specifice pentru diagrama,iar restul fac parte din lista cheltuiala

        ArrayList<Cheltuiala> lista_cheltuieli = new ArrayList<>();
        int index = 2;//am inceput de la 3 pt ca acolo am ramas

        while(index < tokens.length)
        {
            //trecem in for cate 3,pentru a lua fiecare obiect de tip cheltuiala
            Cheltuiala c = new Cheltuiala(Integer.parseInt(tokens[index]) , tokens[index+1] , Integer.parseInt(tokens[index+2]));
            
            lista_cheltuieli.add(c);
            index=index+3;//pt a incepe de la urmatorul obiect Cheltuiala
        }
        //la final,in lista_cheltuieli avem toate cheltuielile pentru o diagrama

        return new Diagrama(id,idUser,lista_cheltuieli);
    }
}
