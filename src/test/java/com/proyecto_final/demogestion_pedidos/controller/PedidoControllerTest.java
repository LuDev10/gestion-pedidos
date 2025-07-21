package com.proyecto_final.demogestion_pedidos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto_final.demogestion_pedidos.dto.LineaPedidoDTO;
import com.proyecto_final.demogestion_pedidos.dto.PedidoDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PedidoControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  // ✅ TC001 - PedidoController - Crear pedido exitosamente
  @Test
  public void TC001_crearPedidoConLineas_retorna201YDatosCorrectos() throws Exception {
    LineaPedidoDTO linea = new LineaPedidoDTO(1L, 2, 2400.0, 1L, "Escoba", 1200.0, 1L);
    PedidoDTO pedido = new PedidoDTO(null, "PED-TEST-001", LocalDate.now(), Collections.singletonList(linea));

    String json = objectMapper.writeValueAsString(pedido);

    mockMvc.perform(post("/api/pedidos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(status().isCreated())
        .andExpect(content().string(containsString("PED-TEST-001")));

    logInfo("✅ TC001 - Pedido creado exitosamente con código PED-TEST-001 🎉");
  }

  // ❌ TC002 - PedidoController - Crear pedido con datos inválidos
  @Test
  public void TC002_crearPedidoInvalido_retorna400() throws Exception {
    PedidoDTO pedido = new PedidoDTO(null, "", null, Collections.emptyList());
    String json = objectMapper.writeValueAsString(pedido);

    mockMvc.perform(post("/api/pedidos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(status().isBadRequest());

    logInfo("⚠️ TC002 - Validación correcta para pedido inválido 🛑");
  }

  // ✅ TC003 - PedidoController - Obtener todos los pedidos
  @Test
  public void TC003_obtenerTodosLosPedidos_retorna200() throws Exception {
    mockMvc.perform(get("/api/pedidos"))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

    logInfo("✅ TC003 - Se obtuvo la lista de pedidos correctamente 📦");
  }

  // ❌ TC004 - PedidoController - Obtener pedido inexistente
  @Test
  public void TC004_obtenerPedidoPorIdInexistente_retorna404() throws Exception {
    mockMvc.perform(get("/api/pedidos/9999"))
        .andExpect(status().isNotFound());

    logInfo("⚠️ TC004 - Pedido no encontrado, código 404 correcto 🔍");
  }

  // ✅ TC005 - PedidoController - Eliminar pedido por ID
  @Test
  public void TC005_eliminarPedidoPorId_retorna204() throws Exception {
    LineaPedidoDTO linea = new LineaPedidoDTO(2L, 1, 1500.0, 6L, "Trapeador", 1500.0, 1L);
    PedidoDTO pedido = new PedidoDTO(null, "PED-DELETE-001", LocalDate.now(), Collections.singletonList(linea));

    String json = objectMapper.writeValueAsString(pedido);

    String response = mockMvc.perform(post("/api/pedidos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(status().isCreated())
        .andReturn().getResponse().getContentAsString();

    PedidoDTO pedidoCreado = objectMapper.readValue(response, PedidoDTO.class);

    mockMvc.perform(delete("/api/pedidos/" + pedidoCreado.getId()))
        .andExpect(status().isNoContent());

    logInfo("✅ TC005 - Pedido eliminado correctamente 🗑️ ID: " + pedidoCreado.getId());
  }

  private void logInfo(String message) {
    System.out.println(message);
  }
}
