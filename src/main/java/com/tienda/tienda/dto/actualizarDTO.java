package com.tienda.tienda.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class actualizarDTO {
    @NotBlank(message = "El producto debe tener un nombre")
    private String nombreproducto;

    @NotNull(message = "El producto debe tener un precio")
    private Long precioproducto;

    private Long stockRestante;
}
