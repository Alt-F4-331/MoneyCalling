package com.example.moneycalling_spring.dto;

public class DiagramaRequestDTO {

    private int id;  // ID-ul diagramei
    private int userId;  // ID-ul utilizatorului (nu va mai trebui să trimitem toate datele despre utilizator)

    // Getters și Setters
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
}
