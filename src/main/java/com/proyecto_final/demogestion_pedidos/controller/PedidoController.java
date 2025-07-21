package com.proyecto_final.demogestion_pedidos.controller;

import com.proyecto_final.demogestion_pedidos.dto.PedidoDTO;
import com.proyecto_final.demogestion_pedidos.service.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

  private final PedidoService pedidoService;

  public PedidoController(PedidoService pedidoService) {
    this.pedidoService = pedidoService;
  }

  @GetMapping
  public List<PedidoDTO> obtenerTodos() {
    return pedidoService.obtenerTodos();
  }

  @GetMapping("/{id}")
  public ResponseEntity<PedidoDTO> obtenerPorId(@PathVariable Long id) {
    return pedidoService.obtenerPedidoPorId(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<PedidoDTO> crear(@RequestBody PedidoDTO dto) {
    PedidoDTO creado = pedidoService.crearPedidoConLineas(dto);
    return ResponseEntity.ok(creado);
  }

  @PutMapping("/{id}")
  public ResponseEntity<PedidoDTO> actualizar(@PathVariable Long id, @RequestBody PedidoDTO dto) {
    PedidoDTO actualizado = pedidoService.actualizar(id, dto);
    return ResponseEntity.ok(actualizado);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> eliminar(@PathVariable Long id) {
    if (pedidoService.eliminar(id)) {
      return ResponseEntity.ok().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
