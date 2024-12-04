package com.example.moneycalling_spring.dto;

import com.example.moneycalling_spring.Domain.Cheltuiala;
import com.example.moneycalling_spring.Domain.Data;

import java.util.Map;

public class DiagramaRequestDTO {

    private int id;  // ID-ul diagramei

    private Data data; // data la care a fost creata diagrama
    private int userId;  // ID-ul utilizatorului (nu va mai trebui să trimitem toate datele despre utilizator)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }



}



