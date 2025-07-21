package com.proyecto_final.demogestion_pedidos.controller;

import com.proyecto_final.demogestion_pedidos.dto.PedidoDTO;
import com.proyecto_final.demogestion_pedidos.service.PedidoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class WebController {

  private final PedidoService pedidoService;

  public WebController(PedidoService pedidoService) {
    this.pedidoService = pedidoService;
  }

  @GetMapping("/inicio")
  public String mostrarInicio() {
    return "inicio";
  }

  @GetMapping("/listar-pedidos")
  public String listarPedidos(Model model) {
    model.addAttribute("pedidos", pedidoService.obtenerTodos());
    return "listar-pedidos";
  }

  @GetMapping("/nuevo")
  public String mostrarFormularioNuevo(Model model) {
    model.addAttribute("pedido", new PedidoDTO());
    model.addAttribute("productos", pedidoService.obtenerProductos());
    return "nuevo-pedido";
  }

  @PostMapping("/nuevo")
  public String crearPedido(@ModelAttribute PedidoDTO pedido) {
    pedidoService.crearPedidoConLineas(pedido);
    return "redirect:/listar-pedidos";
  }

  @GetMapping("/buscar-pedido")
  public String mostrarBuscarPedido() {
    return "buscar-pedido";
  }

  @PostMapping("/buscar-pedido")
  public String buscarPedidoPorId(@RequestParam Long id, Model model) {
    return pedidoService.obtenerPedidoPorId(id)
        .map(pedido -> {
          model.addAttribute("pedido", pedido);
          model.addAttribute("total", pedido.getTotal());
          return "detalle";
        })
        .orElse("redirect:/listar-pedidos");
  }

  @GetMapping("/detalle-pedido")
  public String verDetallePedido(@RequestParam Long id, Model model) {
    Optional<PedidoDTO> pedidoOpt = pedidoService.obtenerPedidoPorId(id);
    if (pedidoOpt.isPresent()) {
      PedidoDTO pedido = pedidoOpt.get();
      model.addAttribute("pedido", pedido);
      model.addAttribute("total", pedido.getTotal());
      return "detalle";
    } else {
      model.addAttribute("error", "Pedido no encontrado");
      return "buscar-pedido";
    }
  }

  @GetMapping("/editar-pedido")
  public String mostrarSeleccionEditar() {
    return "editar-pedido";
  }

  @PostMapping("/editar-pedido")
  public String buscarPedidoParaEditar(@RequestParam Long id, Model model) {
    Optional<PedidoDTO> pedidoOptional = pedidoService.obtenerPedidoPorId(id);
    if (pedidoOptional.isPresent()) {
      model.addAttribute("pedido", pedidoOptional.get());
      model.addAttribute("productos", pedidoService.obtenerProductos());
      return "editar-pedido-formulario";
    } else {
      model.addAttribute("error", "Pedido no encontrado");
      return "editar-pedido";
    }
  }


  @PostMapping("/guardar-pedido-editado")
  public String guardarPedidoEditado(@ModelAttribute("pedido") PedidoDTO pedidoDTO, Model model) {
    pedidoService.actualizar(pedidoDTO.getId(), pedidoDTO);
    model.addAttribute("mensaje", "Pedido actualizado correctamente");
    //return "redirect:/detalle?id=" + pedidoDTO.getId();
    return "redirect:/listar-pedidos";
  }

  @PostMapping("/actualizar-pedido")
  public String actualizarPedido(@ModelAttribute("pedido") PedidoDTO pedidoDTO) {
    pedidoService.actualizar(pedidoDTO.getId(), pedidoDTO);
    return "redirect:/listar-pedidos";
  }

  @GetMapping("/eliminar-pedido")
  public String mostrarEliminarPedido() {
    return "eliminar-pedido";
  }

  @PostMapping("/eliminar-pedido")
  public String eliminarPedido(@RequestParam Long id, Model model) {
    boolean eliminado = pedidoService.eliminar(id);
    model.addAttribute("resultado", eliminado ? "Eliminado exitosamente" : "No se encontró el pedido");
    return "eliminar-pedido";
  }

  @GetMapping("/pedido/{id}")
  public String mostrarDetallePedido(@PathVariable Long id, Model model) {
    Optional<PedidoDTO> pedidoOptional = pedidoService.obtenerPedidoPorId(id);
    if (pedidoOptional.isPresent()) {
      model.addAttribute("pedido", pedidoOptional.get());
      return "detalle";
    } else {
      return "redirect:/buscar-pedido";
    }
  }
}
