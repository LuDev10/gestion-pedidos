package com.proyecto_final.demogestion_pedidos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto_final.demogestion_pedidos.dto.ProductoDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductoControllerTest {

  private static final org.slf4j.Logger log = getLogger(ProductoControllerTest.class);

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void TC001_crearProductoValido_retorna201YDatosCorrectos() throws Exception {
    ProductoDTO nuevoProducto = new ProductoDTO(null, "Cepillo", 999.99);

    mockMvc.perform(post("/api/productos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(nuevoProducto)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.nombre").value("Cepillo"))
        .andExpect(jsonPath("$.precio").value(999.99));

    log.info("✅ TC001 - Producto creado correctamente 🧼");
  }

  @Test
  public void TC002_crearProductoInvalidoSinNombre_retorna400() throws Exception {
    ProductoDTO productoInvalido = new ProductoDTO(null, "", 500.0);

    mockMvc.perform(post("/api/productos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(productoInvalido)))
        .andExpect(status().isBadRequest());

    log.info("✅ TC002 - Validación negativa por nombre vacío 🛑");
  }

  @Test
  public void TC003_obtenerTodosLosProductos_retorna200YListado() throws Exception {
    mockMvc.perform(get("/api/productos"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Escoba"))); // Suponiendo que ya existe

    log.info("✅ TC003 - Lista de productos obtenida correctamente 📦");
  }

  @Test
  public void TC004_obtenerProductoPorIdExistente_retorna200() throws Exception {
    Long idExistente = 1L;

    mockMvc.perform(get("/api/productos/" + idExistente))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(idExistente));

    log.info("✅ TC004 - Producto obtenido por ID 🆔");
  }

  @Test
  public void TC005_eliminarProductoPorIdExistente_retorna204() throws Exception {
    ProductoDTO nuevo = new ProductoDTO(null, "Pala", 800.00);

    String response = mockMvc.perform(post("/api/productos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(nuevo)))
        .andReturn()
        .getResponse()
        .getContentAsString();

    ProductoDTO creado = objectMapper.readValue(response, ProductoDTO.class);

    mockMvc.perform(delete("/api/productos/" + creado.getId()))
        .andExpect(status().isNoContent());

    log.info("✅ TC005 - Producto eliminado correctamente 🗑️");
  }
}
