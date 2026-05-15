package com.tienda.tienda.controller;

import com.tienda.tienda.dto.ProductoRequestDTO;
import com.tienda.tienda.dto.ProductoResponseDTO;
import com.tienda.tienda.model.Producto;
import com.tienda.tienda.repository.ProductoRepository;
import com.tienda.tienda.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("gym/tienda")
@RequiredArgsConstructor
public class ProductoController {
    private final ProductoService productoService;
    private final ProductoRepository productoRepository;

    // Listar todos los productos en la tienda
    @GetMapping("/listarproductos")
    public ResponseEntity<List<Producto>> obtenerProductos(){
        return ResponseEntity.ok(productoService.obtenerProductos());
    }

    // Buscar producto por id
    @GetMapping("/busqueda/{idProducto}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long idProducto) {
        ProductoResponseDTO dto = productoService.obtenerporId(idProducto);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/busqueda/{nombreProducto}")
    public ResponseEntity<ProductoResponseDTO> buscarPorNombre(@PathVariable String nombreProducto){
        ProductoRequestDTO dto = new ProductoRequestDTO();
        dto.setNombreproducto(nombreProducto);

        ProductoResponseDTO respuesta = productoService.obtenerporNombre(dto);
        return ResponseEntity.ok(respuesta);
    }
}
