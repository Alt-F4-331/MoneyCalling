package com.example.moneycalling_spring.Domain;

public interface EntitateConverter<T extends Entitate> {

    String toString(T Object);

    T fromString(String line);
}
