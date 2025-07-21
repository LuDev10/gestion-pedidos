package com.proyecto_final.demogestion_pedidos.controller;

import com.proyecto_final.demogestion_pedidos.dto.ProductoDTO;
import com.proyecto_final.demogestion_pedidos.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

  private final ProductoService productoService;

  public ProductoController(ProductoService productoService) {
    this.productoService = productoService;
  }

  @GetMapping
  public ResponseEntity<List<ProductoDTO>> obtenerTodos() {
    return ResponseEntity.ok(productoService.obtenerTodos());
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductoDTO> obtenerPorId(@PathVariable Long id) {
    Optional<ProductoDTO> producto = productoService.obtenerPorId(id);
    return producto.map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<ProductoDTO> crearProducto(@Valid @RequestBody ProductoDTO productoDTO) {
    ProductoDTO creado = productoService.crear(productoDTO);
    return ResponseEntity.ok(creado);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ProductoDTO> actualizarProducto(@PathVariable Long id, @RequestBody ProductoDTO productoDTO) {
    Optional<ProductoDTO> actualizado = productoService.actualizar(id, productoDTO);
    return actualizado.map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
    productoService.eliminar(id);
    return ResponseEntity.noContent().build();
  }
}
