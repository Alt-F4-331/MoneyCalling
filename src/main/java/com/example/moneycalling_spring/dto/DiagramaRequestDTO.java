package com.example.moneycalling_spring.dto;

import com.example.moneycalling_spring.Domain.Data;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class DiagramaRequestDTO {

    @Min(value = 1, message = "ID-ul diagramei trebuie să fie un număr pozitiv.")
    private int id;  // ID-ul diagramei

    @NotNull(message = "Data nu poate fi nulă.")
    private Data data; // data la care a fost creata diagrama

    @Min(value = 1, message = "User ID-ul trebuie să fie un număr pozitiv.")
    private int userId;  // ID-ul utilizatorului


    public DiagramaRequestDTO(int id, @Valid Data data, int userId) {
        this.id = id;
        this.data = data;
        this.userId = userId;
    }

    // Getters și setters
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
