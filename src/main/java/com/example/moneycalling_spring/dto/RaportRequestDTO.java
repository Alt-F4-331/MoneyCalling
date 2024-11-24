package com.example.moneycalling_spring.dto;

import com.example.moneycalling_spring.Domain.Diagrama;
import com.example.moneycalling_spring.Domain.Raport;

public class RaportRequestDTO {

    private int id;
    private int idDiagrama; // doar ID-ul diagramei, nu întregul obiect

    public RaportRequestDTO() {}

    public RaportRequestDTO(int id, int idDiagrama) {
        this.id = id;
        this.idDiagrama = idDiagrama;
    }

    // Getters și setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdDiagrama() { return idDiagrama; }
    public void setIdDiagrama(int idDiagrama) { this.idDiagrama = idDiagrama; }

    // Static method to map from DTO to Entity
    public static Raport mapToEntity(RaportRequestDTO dto, Diagrama diagrama) {
        return new Raport(dto.getId(), diagrama);
    }

    // Static method to map from Entity to DTO
    public static RaportRequestDTO mapToDTO(Raport raport) {
        RaportRequestDTO dto = new RaportRequestDTO();
        dto.setId(raport.getId());
        dto.setIdDiagrama(raport.getDiagrama().getId());
        return dto;
    }
}

