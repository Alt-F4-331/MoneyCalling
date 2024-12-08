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



    public DiagramaRequestDTO(int id, @Valid Data data, int userId) {
        this.id = id;
        this.data = data;
    }

    // Getters și setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
