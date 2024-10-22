package Domain;

public interface EntitateConverter<T extends Entitate> {

    String toString(T Object);

    T fromString(String line);
}
