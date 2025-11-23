package com.example.elarayax.naves.dto;

import lombok.Data;
import java.util.List;

@Data
public class CarritoRequest {
    private Long usuarioId;   // ✔ CORRECTO
    private Long metodoPagoId;
    private Long estadoId;
    private List<CarritoItemRequest> items;
}

