package com.tienda.tienda.controller;

import com.tienda.tienda.dto.*;
import com.tienda.tienda.model.Producto;
import com.tienda.tienda.model.Venta;
import com.tienda.tienda.repository.ProductoRepository;
import com.tienda.tienda.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @GetMapping("/busqueda/id/{idProducto}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long idProducto) {
        ProductoResponseDTO dto = productoService.obtenerporId(idProducto);
        return ResponseEntity.ok(dto);
    }

    // Buscar producto por nombre
    @GetMapping("/busqueda/nombre/{nombreProducto}")
    public ResponseEntity<ProductoResponseDTO> buscarPorNombre(@PathVariable String nombreProducto){
        ProductoRequestDTO dto = new ProductoRequestDTO();
         dto.setNombreproducto(nombreProducto);

        ProductoResponseDTO respuesta = productoService.obtenerporNombre(dto);
        return ResponseEntity.ok(respuesta);
    }

    // Borrar producto
    @DeleteMapping("/borrar/{idProducto}")
    public ResponseEntity<?> borrarUsuario(@PathVariable Long idProducto){
        if(productoService.buscarId(idProducto).isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("Mensaje","No se encontró ningun ID asociado a algún producto."));
        }

        productoService.eliminarProducto(idProducto);

        Map<String,String> respuesta = new HashMap<>();
        respuesta.put("Mensaje","Producto eliminado correctamente");
        respuesta.put("Id del prod: ",idProducto.toString());

        return ResponseEntity.ok(respuesta);
    }

    // Actualizar producto
    @PutMapping("/actualizar/{idProducto}")
    public ResponseEntity<?> actualizarProducto(@PathVariable Long idProducto, @Valid @RequestBody actualizarDTO dto){
        if(productoService.buscarId(idProducto).isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("Mensaje","No se encontró ningun ID asociado a algún producto."));
        }

        productoService.modificarProducto(idProducto, dto);

        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("Mensaje ","Producto actualizado con éxito");
        respuesta.put("IdProducto:",idProducto.toString());
        respuesta.put("Producto ",dto.getNombreproducto());

        return ResponseEntity.ok(respuesta);

    }

    @PostMapping("/anadirproducto")
    public ResponseEntity<ProductoResponseDTO> registrarUsuario(@Valid @RequestBody ProductoRequestDTO dto){
        ProductoResponseDTO nuevo = productoService.agregarProducto(dto);
        return ResponseEntity.status(201).body(nuevo);
    }

    @GetMapping("/listarpagos")
    public ResponseEntity<List<Venta>> obtenerVentas(){
        return ResponseEntity.ok(productoService.obtenerVentas());
    }

    @PostMapping("/guardarpago")
    public ResponseEntity<VentaResponseDTO> registrarVenta(@Valid @RequestBody VentaRequestDTO dto){
        VentaResponseDTO nueva = productoService.registrarVenta(dto);
        return ResponseEntity.status(201).body(nueva);
    }
}
