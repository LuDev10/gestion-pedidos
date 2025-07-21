package com.proyecto_final.demogestion_pedidos.config;

import com.proyecto_final.demogestion_pedidos.model.LineaPedido;
import com.proyecto_final.demogestion_pedidos.model.Pedido;
import com.proyecto_final.demogestion_pedidos.model.Producto;
import com.proyecto_final.demogestion_pedidos.repository.LineaPedidoRepository;
import com.proyecto_final.demogestion_pedidos.repository.PedidoRepository;
import com.proyecto_final.demogestion_pedidos.repository.ProductoRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Component
public class DataLoader {
  private final PedidoRepository pedidoRepository;
  private final ProductoRepository productoRepository;
  private final LineaPedidoRepository lineaPedidoRepository;

  public DataLoader(PedidoRepository pedidoRepository, ProductoRepository productoRepository, LineaPedidoRepository lineaPedidoRepository) {
    this.pedidoRepository = pedidoRepository;
    this.productoRepository = productoRepository;
    this.lineaPedidoRepository = lineaPedidoRepository;
  }

  @PostConstruct
  public void cargarDatos() {
    List<Producto> productos = Arrays.asList(
        new Producto(null, "Escoba", 1200.0),
        new Producto(null, "Detergente", 850.0),
        new Producto(null, "Desinfectante", 1300.0),
        new Producto(null, "Guantes", 500.0),
        new Producto(null, "Esponjas", 300.0),
        new Producto(null, "Trapeador", 1500.0),
        new Producto(null, "Jabón líquido", 950.0),
        new Producto(null, "Cloro", 1100.0),
        new Producto(null, "Paño de cocina", 700.0),
        new Producto(null, "Desengrasante", 1400.0)
    );

    productoRepository.saveAll(productos);

    List<String> clientes = Arrays.asList("Luis", "Andrea", "Carlos", "Lucía", "Marcos", "Camila", "Sofía", "Pedro", "Elena", "Nicolás");
    Random rand = new Random();

    for (int i = 0; i < 10; i++) {
      Pedido pedido = new Pedido();
      pedido.setCodigo(clientes.get(i));
      pedido.setFecha(LocalDate.now().minusDays(i));
      pedido.setLineasPedido(new ArrayList<>());

      pedido = pedidoRepository.save(pedido);

      int cantidadProductos = rand.nextInt(3) + 1;
      Set<Integer> indicesUsados = new HashSet<>();
      double totalPedido = 0.0;

      for (int j = 0; j < cantidadProductos; j++) {
        int index;
        do {
          index = rand.nextInt(productos.size());
        } while (indicesUsados.contains(index));
        indicesUsados.add(index);

        Producto producto = productos.get(index);
        int cantidad = rand.nextInt(3) + 1;
        double subtotal = cantidad * producto.getPrecio();
        totalPedido += subtotal;

        LineaPedido linea = new LineaPedido(producto, pedido, cantidad, subtotal);
        lineaPedidoRepository.save(linea);
        pedido.getLineasPedido().add(linea);
      }

      pedido.setTotal(totalPedido);
      pedidoRepository.save(pedido);
    }
  }
}
