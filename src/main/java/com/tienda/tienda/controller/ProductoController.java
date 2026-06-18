package com.tienda.tienda.controller;

import com.tienda.tienda.dto.*;
import com.tienda.tienda.model.Producto;
import com.tienda.tienda.repository.ProductoRepository;
import com.tienda.tienda.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Listar productos", description = "Obtiene una lista de todos los productos en catalogo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Productos listados correctamente"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    public ResponseEntity<List<Producto>> obtenerProductos(){
        return ResponseEntity.ok(productoService.obtenerProductos());
    }

    // Buscar producto por id
    @GetMapping("/busqueda/id/{idProducto}")
    @Operation(summary = "Buscar un producto", description = "Utilizado para buscar un producto por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos incorrectos"),
            @ApiResponse(responseCode = "404", description = "Error al buscar el producto"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    public ResponseEntity<?> obtenerPorId(@PathVariable Long idProducto) {
        ProductoResponseDTO dto = productoService.obtenerporId(idProducto);
        return ResponseEntity.ok(dto);
    }

    // Buscar producto por nombre
    @GetMapping("/busqueda/nombre/{nombreProducto}")
    @Operation(summary = "Buscar un producto", description = "Utilizado para buscar un producto por su NOMBRE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos incorrectos"),
            @ApiResponse(responseCode = "404", description = "Error al buscar el producto"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    public ResponseEntity<ProductoResponseDTO> buscarPorNombre(@PathVariable String nombreProducto){
        ProductoRequestDTO dto = new ProductoRequestDTO();
         dto.setNombreproducto(nombreProducto);

        ProductoResponseDTO respuesta = productoService.obtenerporNombre(dto);
        return ResponseEntity.ok(respuesta);
    }

    // Borrar producto
    @DeleteMapping("/borrar/{idProducto}")
    @Operation(summary = "Eliminar un producto", description = "Utilizado para eliminar un producto por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto eliminado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos incorrectos"),
            @ApiResponse(responseCode = "404", description = "Error al eliminar el producto"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
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
    @Operation(summary = "Actualizar un producto", description = "Utilizado para actualizar un producto por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto modificado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos incorrectos"),
            @ApiResponse(responseCode = "404", description = "Error al modificar el producto"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
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
    @Operation(summary = "Añadir un producto", description = "Utilizado para añadir un producto al catalogo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto añadida correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos incorrectos"),
            @ApiResponse(responseCode = "404", description = "Error al añadir el producto"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    public ResponseEntity<ProductoResponseDTO> registrarUsuario(@Valid @RequestBody ProductoRequestDTO dto){
        ProductoResponseDTO nuevo = productoService.agregarProducto(dto);
        return ResponseEntity.status(201).body(nuevo);
    }
}
