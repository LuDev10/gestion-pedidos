package com.proyecto_final.demogestion_pedidos.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class WebControllerTest {

  @Autowired
  private MockMvc mockMvc;

  // ✅ TC001 - WebController - Carga de página principal
  @Test
  public void TC001_cargarVistaPrincipal_retorna200() throws Exception {
    mockMvc.perform(get("/"))
        .andExpect(status().isOk())
        .andExpect(view().name("index"));

    logInfo("✅ TC001 - Página principal cargada correctamente 🏠");
  }

  // ✅ TC002 - WebController - Carga de vista productos
  @Test
  public void TC002_cargarVistaProductos_retorna200() throws Exception {
    mockMvc.perform(get("/productos"))
        .andExpect(status().isOk())
        .andExpect(view().name("productos"));

    logInfo("✅ TC002 - Vista de productos cargada correctamente 📦");
  }

  // ✅ TC003 - WebController - Carga de vista pedidos
  @Test
  public void TC003_cargarVistaPedidos_retorna200() throws Exception {
    mockMvc.perform(get("/pedidos"))
        .andExpect(status().isOk())
        .andExpect(view().name("pedidos"));

    logInfo("✅ TC003 - Vista de pedidos cargada correctamente 📑");
  }

  // ✅ TC004 - WebController - Carga de vista nuevo pedido
  @Test
  public void TC004_cargarVistaNuevoPedido_retorna200() throws Exception {
    mockMvc.perform(get("/nuevo-pedido"))
        .andExpect(status().isOk())
        .andExpect(view().name("nuevo-pedido"));

    logInfo("✅ TC004 - Vista de nuevo pedido cargada correctamente 📝");
  }

  private void logInfo(String mensaje) {
    System.out.println(mensaje);
  }
}
