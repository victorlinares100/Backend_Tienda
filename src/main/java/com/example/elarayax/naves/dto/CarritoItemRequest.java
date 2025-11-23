package com.example.elarayax.naves.dto;

import lombok.Data;

@Data
public class CarritoItemRequest {
    private Long productoId;
    private Integer cantidad;
    private Double precioUnitario;
}
