package com.example.elarayax.naves.dto;

import lombok.Data;

@Data
public class ProductoDTO {
    private String nombreProducto;
    private Double precioProducto;
    private Integer stock;
    private Long categoriaId;
    private Long marcaId;
    private Long tallaId;
    private String imagenUrl; 
}


