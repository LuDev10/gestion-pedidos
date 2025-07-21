package com.proyecto_final.demogestion_pedidos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto_final.demogestion_pedidos.dto.LineaPedidoDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LineaPedidoControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  // ✅ TC001 - LineaPedidoController - Crear línea de pedido válida
  @Test
  public void TC001_crearLineaPedidoValida_retorna200() throws Exception {
    LineaPedidoDTO linea = new LineaPedidoDTO(null, 2, 2400.0, 1L, "Escoba", 1200.0, 1L);

    mockMvc.perform(post("/api/lineas-pedido")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(linea)))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Escoba")));

    logInfo("✅ TC001 - Línea de pedido creada exitosamente 🧹");
  }

  // ❌ TC002 - LineaPedidoController - Crear línea con producto inexistente
  @Test
  public void TC002_crearLineaPedidoSinProducto_retorna400() throws Exception {
    LineaPedidoDTO linea = new LineaPedidoDTO(null, 2, 2400.0, 9999L, "ProductoInexistente", 1200.0, 1L);

    mockMvc.perform(post("/api/lineas-pedido")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(linea)))
        .andExpect(status().isBadRequest());

    logInfo("⚠️ TC002 - Producto inexistente correctamente rechazado 🚫");
  }

  // ✅ TC003 - LineaPedidoController - Obtener todas las líneas de pedido
  @Test
  public void TC003_obtenerTodasLasLineas_retorna200() throws Exception {
    mockMvc.perform(get("/api/lineas-pedido"))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

    logInfo("✅ TC003 - Se obtuvieron todas las líneas de pedido 📦");
  }

  // ❌ TC004 - LineaPedidoController - Obtener línea inexistente
  @Test
  public void TC004_obtenerLineaInexistente_retorna404() throws Exception {
    mockMvc.perform(get("/api/lineas-pedido/9999"))
        .andExpect(status().isNotFound());

    logInfo("⚠️ TC004 - Línea de pedido no encontrada correctamente 🔍");
  }

  // ✅ TC005 - LineaPedidoController - Eliminar línea de pedido
  @Test
  public void TC005_eliminarLineaPedidoExistente_retorna204() throws Exception {
    LineaPedidoDTO linea = new LineaPedidoDTO(null, 1, 1000.0, 1L, "Escoba", 1000.0, 1L);
    String response = mockMvc.perform(post("/api/lineas-pedido")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(linea)))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();

    LineaPedidoDTO creada = objectMapper.readValue(response, LineaPedidoDTO.class);

    mockMvc.perform(delete("/api/lineas-pedido/" + creada.getId()))
        .andExpect(status().isNoContent());

    logInfo("✅ TC005 - Línea de pedido eliminada correctamente 🗑️ ID: " + creada.getId());
  }

  private void logInfo(String mensaje) {
    System.out.println(mensaje);
  }
}
