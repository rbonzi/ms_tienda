package com.tienda.tienda.repository;

import com.tienda.tienda.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    Optional<Producto> findByNombreProductoIgnoreCase(String nombreProducto);

    boolean existsByNombreProducto(String nombreProducto);
}
