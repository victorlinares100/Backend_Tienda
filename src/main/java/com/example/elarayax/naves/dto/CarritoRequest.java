package com.example.elarayax.naves.dto;

import java.util.List;
import lombok.Data;

@Data
public class CarritoRequest {
    private Long userId;
    private Long metodoPagoId;  
    private Long estadoId;     
    private List<CarritoItemRequest> items;
}
