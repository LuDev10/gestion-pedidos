package com.proyecto_final.demogestion_pedidos.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProductoDTO {
  private Long id;
  @NotBlank(message = "El nombre no puede estar vacío")
  private String nombre;

  @NotNull(message = "El precio es obligatorio")
  @Min(value = 1, message = "El precio debe ser mayor a cero")
  private Double precio;

  public ProductoDTO() {
  }

  public ProductoDTO(Long id, String nombre, double precio) {
    this.id = id;
    this.nombre = nombre;
    this.precio = precio;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public double getPrecio() {
    return precio;
  }

  public void setPrecio(double precio) {
    this.precio = precio;
  }
}
