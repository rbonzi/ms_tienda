package com.tienda.tienda.service;

import com.tienda.tienda.dto.ProductoRequestDTO;
import com.tienda.tienda.dto.ProductoResponseDTO;
import com.tienda.tienda.dto.actualizarDTO;
import com.tienda.tienda.model.Producto;
import com.tienda.tienda.repository.ProductoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductoService {
    private final ProductoRepository productoRepository;

    private ProductoResponseDTO mapToDto(Producto producto){
        return new ProductoResponseDTO(
                producto.getIdProducto(),
                producto.getNombreProducto(),
                producto.getPrecioProducto(),
                producto.getStockRestante()
        );
    }

    // Listar todos los productos
    public List<Producto> obtenerProductos(){
        return productoRepository.findAll();
    }

    // Buscar productos por id
    public ProductoResponseDTO obtenerporId(Long idproducto){
        Producto productoEncontrado = productoRepository.findById(idproducto)
                .orElseThrow(() -> new RuntimeException("No existe un producto con ese id"));

    ProductoResponseDTO dto = new ProductoResponseDTO();
    dto.setIdproducto(productoEncontrado.getIdProducto());
    dto.setNombreProducto(productoEncontrado.getNombreProducto());
    dto.setPrecioProducto(productoEncontrado.getPrecioProducto());

    return dto;
    }

    // Buscar productos por nombre
    public ProductoResponseDTO obtenerporNombre(ProductoRequestDTO dto){
        if(!productoRepository.existByNombreProducto(dto.getNombreproducto())){
            throw new RuntimeException("No existe un producto con ese nombre");
        }

        Producto productoNuevo = new Producto();
        productoNuevo.setNombreProducto(dto.getNombreproducto());
        productoNuevo.setPrecioProducto(dto.getPrecioproducto());


        ProductoResponseDTO respuesta = new ProductoResponseDTO();
        respuesta.setIdproducto(productoNuevo.getIdProducto());
        respuesta.setNombreProducto(productoNuevo.getNombreProducto());
        respuesta.setPrecioProducto(productoNuevo.getPrecioProducto());

        return respuesta;
    }

    // Eliminar producto con id
    @Transactional
    public void eliminarProducto(Long idProducto){
        productoRepository.deleteById(idProducto);
    }

    // Modificar productos
    public Optional<ProductoResponseDTO> modificarProducto(Long idProducto, @Valid actualizarDTO dto){
        return productoRepository.findById(idProducto).map(existe ->{
           existe.setPrecioProducto(dto.getPrecioproducto());
           existe.setNombreProducto(dto.getNombreproducto());

           if(dto.getStockRestante() != null){
               existe.setStockRestante(dto.getStockRestante());
           }

           return mapToDto(productoRepository.save(existe));
        });
    }


}
