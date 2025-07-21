package com.proyecto_final.demogestion_pedidos.service;

import com.proyecto_final.demogestion_pedidos.dto.LineaPedidoDTO;
import com.proyecto_final.demogestion_pedidos.model.LineaPedido;
import com.proyecto_final.demogestion_pedidos.model.Pedido;
import com.proyecto_final.demogestion_pedidos.model.Producto;
import com.proyecto_final.demogestion_pedidos.repository.LineaPedidoRepository;
import com.proyecto_final.demogestion_pedidos.repository.PedidoRepository;
import com.proyecto_final.demogestion_pedidos.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LineaPedidoService {

  private final LineaPedidoRepository lineaPedidoRepository;
  private final PedidoRepository pedidoRepository;
  private final ProductoRepository productoRepository;

  public LineaPedidoService(LineaPedidoRepository lineaPedidoRepository,
                            PedidoRepository pedidoRepository,
                            ProductoRepository productoRepository) {
    this.lineaPedidoRepository = lineaPedidoRepository;
    this.pedidoRepository = pedidoRepository;
    this.productoRepository = productoRepository;
  }

  public void crear(LineaPedidoDTO dto) {
    agregarLinea(dto);
  }

  public void agregarLinea(LineaPedidoDTO dto) {
    Optional<Pedido> pedidoOpt = pedidoRepository.findById(dto.getPedidoId());
    Optional<Producto> productoOpt = productoRepository.findById(dto.getProductoId());

    if (pedidoOpt.isPresent() && productoOpt.isPresent()) {
      Pedido pedido = pedidoOpt.get();
      Producto producto = productoOpt.get();
      double subtotal = dto.getCantidad() * producto.getPrecio();

      LineaPedido linea = new LineaPedido(producto, pedido, dto.getCantidad(), subtotal);
      lineaPedidoRepository.save(linea);
    } else {
      throw new IllegalArgumentException("Pedido o Producto no encontrado");
    }
  }

  public void actualizar(Long id, LineaPedidoDTO dto) {
    LineaPedido existente = lineaPedidoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Línea de pedido no encontrada"));

    Producto producto = productoRepository.findById(dto.getProductoId())
        .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

    Pedido pedido = pedidoRepository.findById(dto.getPedidoId())
        .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado"));

    existente.setCantidad(dto.getCantidad());
    existente.setSubtotal(dto.getCantidad() * producto.getPrecio());
    existente.setProducto(producto);
    existente.setPedido(pedido);

    lineaPedidoRepository.save(existente);
  }

  public List<LineaPedidoDTO> obtenerTodas() {
    return lineaPedidoRepository.findAll().stream()
        .map(linea -> new LineaPedidoDTO(
            linea.getId(),
            linea.getCantidad(),
            linea.getSubtotal(),
            linea.getProducto().getId(),
            linea.getProducto().getNombre(),
            linea.getProducto().getPrecio(),
            linea.getPedido().getId()
        ))
        .collect(Collectors.toList());
  }

  public List<LineaPedidoDTO> obtenerPorPedido(Long pedidoId) {
    List<LineaPedido> lineas = lineaPedidoRepository.findByPedidoId(pedidoId);
    return lineas.stream()
        .map(linea -> new LineaPedidoDTO(
            linea.getId(),
            linea.getCantidad(),
            linea.getSubtotal(),
            linea.getProducto().getId(),
            linea.getProducto().getNombre(),
            linea.getProducto().getPrecio(),
            linea.getPedido().getId()
        ))
        .collect(Collectors.toList());
  }

  public LineaPedidoDTO obtenerPorId(Long id) {
    LineaPedido linea = lineaPedidoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Línea de pedido no encontrada"));
    return new LineaPedidoDTO(
        linea.getId(),
        linea.getCantidad(),
        linea.getSubtotal(),
        linea.getProducto().getId(),
        linea.getProducto().getNombre(),
        linea.getProducto().getPrecio(),
        linea.getPedido().getId()
    );
  }

  public void eliminar(Long id) {
    lineaPedidoRepository.deleteById(id);
  }
}
