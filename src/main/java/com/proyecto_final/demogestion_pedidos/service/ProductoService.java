package com.proyecto_final.demogestion_pedidos.service;

import com.proyecto_final.demogestion_pedidos.dto.ProductoDTO;
import com.proyecto_final.demogestion_pedidos.model.Producto;
import com.proyecto_final.demogestion_pedidos.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductoService {

  private final ProductoRepository productoRepository;

  public ProductoService(ProductoRepository productoRepository) {
    this.productoRepository = productoRepository;
  }

  public List<ProductoDTO> obtenerTodos() {
    return productoRepository.findAll().stream()
        .map(this::convertirAProductoDTO)
        .collect(Collectors.toList());
  }

  public List<ProductoDTO> obtenerTodosLosProductos() {
    return obtenerTodos();
  }

  public Optional<ProductoDTO> obtenerPorId(Long id) {
    return productoRepository.findById(id)
        .map(this::convertirAProductoDTO);
  }

  public ProductoDTO crear(ProductoDTO dto) {
    Producto producto = new Producto(null, dto.getNombre(), dto.getPrecio());
    Producto guardado = productoRepository.save(producto);
    return convertirAProductoDTO(guardado);
  }

  public Optional<ProductoDTO> actualizar(Long id, ProductoDTO dto) {
    Optional<Producto> optionalProducto = productoRepository.findById(id);
    if (optionalProducto.isPresent()) {
      Producto producto = optionalProducto.get();
      producto.setNombre(dto.getNombre());
      producto.setPrecio(dto.getPrecio());
      Producto actualizado = productoRepository.save(producto);
      return Optional.of(convertirAProductoDTO(actualizado));
    }
    return Optional.empty();
  }

  public void eliminar(Long id) {
    productoRepository.deleteById(id);
  }

  private ProductoDTO convertirAProductoDTO(Producto producto) {
    return new ProductoDTO(producto.getId(), producto.getNombre(), producto.getPrecio());
  }
}
