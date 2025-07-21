package com.proyecto_final.demogestion_pedidos.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
public class Pedido {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String codigo;
  private LocalDate fecha;

  @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  private List<LineaPedido> lineasPedido = new ArrayList<>();

  private Double total;

  public Pedido() {}

  public Pedido(Long id, String codigo, LocalDate fecha, List<LineaPedido> lineasPedido) {
    this.id = id;
    this.codigo = codigo;
    this.fecha = fecha;
    this.lineasPedido = lineasPedido;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCodigo() {
    return codigo;
  }

  public void setCodigo(String codigo) {
    this.codigo = codigo;
  }

  public LocalDate getFecha() {
    return fecha;
  }

  public void setFecha(LocalDate fecha) {
    this.fecha = fecha;
  }

  public List<LineaPedido> getLineasPedido() {
    return lineasPedido;
  }

  public void setLineasPedido(List<LineaPedido> lineasPedido) {
    this.lineasPedido = lineasPedido;
  }

  public Double getTotal() {
    return total;
  }

  public void setTotal(Double total) {
    this.total = total;
  }

  public void addLinea(LineaPedido linea) {
    linea.setPedido(this);
    this.lineasPedido.add(linea);
  }

  public void setLineas(List<LineaPedido> lineas) {
    this.setLineasPedido(lineas);
  }

  public List<LineaPedido> getLineas() {
    return this.getLineasPedido();
  }
}
