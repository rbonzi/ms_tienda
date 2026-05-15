package com.tienda.tienda.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoResponseDTO {
    private Long idproducto;
    private String nombreProducto;
    private Long precioProducto;
    private Long stockRestante;

}
