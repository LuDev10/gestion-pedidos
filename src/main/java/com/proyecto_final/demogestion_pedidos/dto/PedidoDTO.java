package com.proyecto_final.demogestion_pedidos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PedidoDTO {
  private Long id;
  @NotBlank(message = "El nombre del cliente no puede estar vacío")
  private String codigo;
  @NotNull(message = "La fecha es obligatoria")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate fecha;
  private List<LineaPedidoDTO> lineas = new ArrayList<>();

  private Double total;

  public PedidoDTO() {
    this.lineas = new ArrayList<>();
  }

  public PedidoDTO(Long id, String codigo, LocalDate fecha, List<LineaPedidoDTO> lineas) {
    this.id = id;
    this.codigo = codigo;
    this.fecha = fecha;
    this.lineas = lineas != null ? lineas : new ArrayList<>();
  }

  public Long getId() {
    return id;
  }

  public String getCodigo() {
    return codigo;
  }

  public LocalDate getFecha() {
    return fecha;
  }

  public List<LineaPedidoDTO> getLineas() {
    return lineas;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setCodigo(String codigo) {
    this.codigo = codigo;
  }

  public void setFecha(LocalDate fecha) {
    this.fecha = fecha;
  }

  public void setLineas(List<LineaPedidoDTO> lineas) {
    this.lineas = lineas;
  }

  public Double getTotal() {
    return total;
  }

  public void setTotal(Double total) {
    this.total = total;
  }
}
