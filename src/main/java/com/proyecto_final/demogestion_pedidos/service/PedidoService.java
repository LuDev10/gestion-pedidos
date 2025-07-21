package com.proyecto_final.demogestion_pedidos.service;

import com.proyecto_final.demogestion_pedidos.dto.LineaPedidoDTO;
import com.proyecto_final.demogestion_pedidos.dto.PedidoDTO;
import com.proyecto_final.demogestion_pedidos.model.LineaPedido;
import com.proyecto_final.demogestion_pedidos.model.Pedido;
import com.proyecto_final.demogestion_pedidos.model.Producto;
import com.proyecto_final.demogestion_pedidos.repository.LineaPedidoRepository;
import com.proyecto_final.demogestion_pedidos.repository.PedidoRepository;
import com.proyecto_final.demogestion_pedidos.repository.ProductoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidoService {

  private final PedidoRepository pedidoRepository;
  private final ProductoRepository productoRepository;
  private final LineaPedidoRepository lineaPedidoRepository;

  public PedidoService(PedidoRepository pedidoRepository,
                       ProductoRepository productoRepository,
                       LineaPedidoRepository lineaPedidoRepository) {
    this.pedidoRepository = pedidoRepository;
    this.productoRepository = productoRepository;
    this.lineaPedidoRepository = lineaPedidoRepository;
  }

  public List<PedidoDTO> obtenerTodos() {
    List<Pedido> pedidos = pedidoRepository.findAll();
    List<PedidoDTO> dtos = new ArrayList<>();
    for (Pedido pedido : pedidos) {
      dtos.add(convertirAPedidoDTO(pedido));
    }
    return dtos;
  }

  public Optional<PedidoDTO> obtenerPedidoPorId(Long id) {
    Optional<Pedido> pedidoOpt = pedidoRepository.findById(id);

    if (pedidoOpt.isPresent()) {
      pedidoOpt.get().getLineasPedido().size();
      return Optional.of(convertirAPedidoDTO(pedidoOpt.get()));
    }
    return Optional.empty();
  }

  public PedidoDTO crearPedidoConLineas(PedidoDTO pedidoDTO) {
    Pedido pedido = new Pedido();
    pedido.setCodigo(pedidoDTO.getCodigo());
    pedido.setFecha(pedidoDTO.getFecha());

    List<LineaPedido> lineas = new ArrayList<>();
    double total = 0.0;

    for (LineaPedidoDTO lineaDTO : pedidoDTO.getLineas()) {
      Producto producto = productoRepository.findById(lineaDTO.getProductoId()).orElseThrow();
      double subtotal = producto.getPrecio() * lineaDTO.getCantidad();
      total += subtotal;

      LineaPedido linea = new LineaPedido(producto, lineaDTO.getCantidad(), subtotal);
      linea.setPedido(pedido);
      lineas.add(linea);
    }

    pedido.setTotal(total);
    pedido.setLineasPedido(lineas);
    Pedido pedidoGuardado = pedidoRepository.save(pedido);
    lineaPedidoRepository.saveAll(lineas);

    return convertirAPedidoDTO(pedidoGuardado);
  }

  @Transactional
  public PedidoDTO actualizar(Long id, PedidoDTO dto) {
    Pedido pedidoExistente = pedidoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

    pedidoExistente.setCodigo(dto.getCodigo());
    pedidoExistente.setFecha(dto.getFecha());

    List<LineaPedido> nuevasLineas = dto.getLineas().stream().map(lineaDTO -> {
      Producto producto = productoRepository.findById(lineaDTO.getProductoId())
          .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
      LineaPedido linea = new LineaPedido();
      linea.setProducto(producto);
      linea.setCantidad(lineaDTO.getCantidad());
      linea.setSubtotal(producto.getPrecio() * lineaDTO.getCantidad());
      linea.setPedido(pedidoExistente);
      return linea;
    }).toList();

    // Limpiamos la lista existente y agregamos las nuevas
    pedidoExistente.getLineasPedido().clear();
    pedidoExistente.getLineasPedido().addAll(nuevasLineas);

    // Actualizamos el total
    double total = nuevasLineas.stream().mapToDouble(LineaPedido::getSubtotal).sum();
    pedidoExistente.setTotal(total);

    pedidoRepository.save(pedidoExistente);
    return mapToDTO(pedidoExistente);
  }


  public boolean eliminar(Long id) {
    if (pedidoRepository.existsById(id)) {
      pedidoRepository.deleteById(id);
      return true;
    }
    return false;
  }

  private PedidoDTO convertirAPedidoDTO(Pedido pedido) {
    PedidoDTO dto = new PedidoDTO();
    dto.setId(pedido.getId());
    dto.setCodigo(pedido.getCodigo());
    dto.setFecha(pedido.getFecha());
    dto.setTotal(pedido.getTotal());

    List<LineaPedido> lineas = lineaPedidoRepository.findByPedido(pedido);
    List<LineaPedidoDTO> lineasDTO = new ArrayList<>();

    for (LineaPedido linea : lineas) {
      LineaPedidoDTO lineaDTO = new LineaPedidoDTO();
      lineaDTO.setProductoId(linea.getProducto().getId());
      lineaDTO.setNombreProducto(linea.getProducto().getNombre());
      lineaDTO.setCantidad(linea.getCantidad());
      lineaDTO.setSubtotal(linea.getSubtotal());
      lineasDTO.add(lineaDTO);
    }

    dto.setLineas(lineasDTO);
    return dto;
  }

  public List<LineaPedidoDTO> getLineasPedido(Long pedidoId) {
    Optional<Pedido> pedidoOpt = pedidoRepository.findById(pedidoId);
    if (pedidoOpt.isPresent()) {
      Pedido pedido = pedidoOpt.get();
      return convertirAPedidoDTO(pedido).getLineas();
    }
    return new ArrayList<>();
  }

  public List<Producto> obtenerProductos() {
    return productoRepository.findAll();
  }

  private PedidoDTO mapToDTO(Pedido pedido) {
    PedidoDTO dto = new PedidoDTO();
    dto.setId(pedido.getId());
    dto.setCodigo(pedido.getCodigo());
    dto.setFecha(pedido.getFecha());
    dto.setTotal(pedido.getTotal());

    List<LineaPedidoDTO> lineasDTO = pedido.getLineas().stream().map(linea -> {
      LineaPedidoDTO l = new LineaPedidoDTO();
      l.setId(linea.getId());
      l.setProductoId(linea.getProducto().getId());
      l.setNombreProducto(linea.getProducto().getNombre());
      l.setCantidad(linea.getCantidad());
      l.setSubtotal(linea.getSubtotal());
      return l;
    }).toList();

    dto.setLineas(lineasDTO);
    return dto;
  }
}
