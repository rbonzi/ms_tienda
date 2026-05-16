package com.tienda.tienda.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProducto;

    @Column(nullable = false, length = 50, unique = true)
    private String nombreProducto;

    @Column(nullable = false, length = 10)
    private Long precioProducto;

    @Column()
    private Long stockRestante;
}
