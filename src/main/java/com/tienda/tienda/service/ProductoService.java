package com.tienda.tienda.service;

import com.tienda.tienda.dto.*;
import com.tienda.tienda.model.Producto;
import com.tienda.tienda.model.Venta;
import com.tienda.tienda.repository.ProductoRepository;
import com.tienda.tienda.repository.VentaRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductoService {
    @Autowired
    private final ProductoRepository productoRepository;
    private final VentaRepository ventaRepository;

    private ProductoResponseDTO mapToDto(Producto producto) {
        return new ProductoResponseDTO(
                producto.getIdProducto(),
                producto.getNombreProducto(),
                producto.getPrecioProducto(),
                producto.getStockRestante()
        );
    }

    // Listar todos los productos
    public List<Producto> obtenerProductos() {
        return productoRepository.findAll();
    }

    // Buscar productos por id
    public ProductoResponseDTO obtenerporId(Long idproducto) {
        Producto productoEncontrado = productoRepository.findById(idproducto)
                .orElseThrow(() -> new RuntimeException("No existe un producto con ese id"));

        ProductoResponseDTO respuesta = new ProductoResponseDTO();
        respuesta.setIdproducto(productoEncontrado.getIdProducto());
        respuesta.setNombreProducto(productoEncontrado.getNombreProducto());
        respuesta.setPrecioProducto(productoEncontrado.getPrecioProducto());
        respuesta.setStockRestante(productoEncontrado.getStockRestante());

        return respuesta;

    }

    // Buscar productos por nombre
    public ProductoResponseDTO obtenerporNombre(ProductoRequestDTO dto) {
        Producto productoEncontrado = productoRepository.findByNombreProductoIgnoreCase(dto.getNombreproducto())
                .orElseThrow(() -> new RuntimeException("No existe un producto con ese nombre"));

        ProductoResponseDTO respuesta = new ProductoResponseDTO();
        respuesta.setIdproducto(productoEncontrado.getIdProducto());
        respuesta.setNombreProducto(productoEncontrado.getNombreProducto());
        respuesta.setPrecioProducto(productoEncontrado.getPrecioProducto());
        respuesta.setStockRestante(productoEncontrado.getStockRestante());

        return respuesta;
    }

    // Buscar para borrar
    public Optional<ProductoResponseDTO> buscarId(Long idProducto){
        return productoRepository.findById(idProducto).map(this::mapToDto);
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

           if(dto.getStockrestante() != null){
               if(dto.getStockrestante() < 0) {
                   throw new RuntimeException("El stock no puede ser un numero negativo");
               }
               existe.setStockRestante(dto.getStockrestante());
           }

           return mapToDto(productoRepository.save(existe));
        });
    }

    // Añadir productos
    public ProductoResponseDTO agregarProducto(ProductoRequestDTO dto){
        if(productoRepository.existsByNombreProducto(dto.getNombreproducto())){
            throw new RuntimeException("ERROR: Ya existe un producto con el nombre '" + dto.getNombreproducto()+"'");
        }

        Producto productoNuevo = new Producto();
        productoNuevo.setNombreProducto(dto.getNombreproducto());
        productoNuevo.setPrecioProducto(dto.getPrecioproducto());
        productoNuevo.setStockRestante(dto.getStockrestante());

        Producto Productoguardado = productoRepository.save(productoNuevo);

        ProductoResponseDTO respuesta = new ProductoResponseDTO();
        respuesta.setIdproducto(Productoguardado.getIdProducto());
        respuesta.setNombreProducto(Productoguardado.getNombreProducto());
        respuesta.setPrecioProducto(Productoguardado.getPrecioProducto());
        respuesta.setStockRestante(Productoguardado.getStockRestante());
        return respuesta;
    }

    public VentaResponseDTO registrarVenta(VentaRequestDTO dto){
        if(!productoRepository.existsByNombreProducto(dto.getNombreproducto())){
            throw new RuntimeException("ERROR: Ya existe un producto con el nombre '"+dto.getNombreproducto()+"'");
        }

        Venta ventanueva = new Venta();
        ventanueva.setNombreProducto(dto.getNombreproducto());
        ventanueva.setPrecioProducto(dto.getPrecioproducto());

        Venta ventaguardada = ventaRepository.save(ventanueva);

        VentaResponseDTO respuesta = new VentaResponseDTO();
        respuesta.setIdventa(ventanueva.getIdVenta());
        respuesta.setNombreProducto(ventanueva.getNombreProducto());
        respuesta.setPrecioProducto(ventanueva.getPrecioProducto());

        return respuesta;
    }

    public List<Venta> obtenerVentas() {
        return ventaRepository.findAll();
    }
}
