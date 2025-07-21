package com.proyecto_final.demogestion_pedidos.controller;

import com.proyecto_final.demogestion_pedidos.dto.LineaPedidoDTO;
import com.proyecto_final.demogestion_pedidos.service.LineaPedidoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lineas-pedido")
public class LineaPedidoController {
  private final LineaPedidoService lineaPedidoService;

  public LineaPedidoController(LineaPedidoService lineaPedidoService) {
    this.lineaPedidoService = lineaPedidoService;
  }

  @GetMapping
  public ResponseEntity<List<LineaPedidoDTO>> getAll() {
    return ResponseEntity.ok(lineaPedidoService.obtenerTodas());
  }

  @GetMapping("/{id}")
  public ResponseEntity<LineaPedidoDTO> getById(@PathVariable Long id) {
    return ResponseEntity.ok(lineaPedidoService.obtenerPorId(id));
  }

  @GetMapping("/pedido/{pedidoId}")
  public ResponseEntity<List<LineaPedidoDTO>> getByPedidoId(@PathVariable Long pedidoId) {
    return ResponseEntity.ok(lineaPedidoService.obtenerPorPedido(pedidoId));
  }

  @PostMapping
  public ResponseEntity<Void> create(@Valid @RequestBody LineaPedidoDTO dto) {
    lineaPedidoService.crear(dto);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody LineaPedidoDTO dto) {
    try {
      lineaPedidoService.actualizar(id, dto);
      return ResponseEntity.noContent().build();
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    lineaPedidoService.eliminar(id);
    return ResponseEntity.noContent().build();
  }
}
