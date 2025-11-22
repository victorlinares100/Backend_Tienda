package com.example.elarayax.naves.dto;

import lombok.Data;

@Data
public class ProductoDTO {
    private String nombreProducto;
    private Double precioProducto;
    private Integer stock;
    private String categoria;
    private String marca;
    private String talla;
    private String imagenUrl;
}
