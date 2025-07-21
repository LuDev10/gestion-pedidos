package com.proyecto_final.demogestion_pedidos.model;

import jakarta.persistence.*;

@Entity
@Table(name = "lineas_pedido")
public class LineaPedido {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  private Pedido pedido;

  @ManyToOne(fetch = FetchType.LAZY)
  private Producto producto;

  private int cantidad;
  private Double subtotal;

  public LineaPedido() {
  }

  public LineaPedido(Producto producto, Pedido pedido, int cantidad, Double subtotal) {
    this.producto = producto;
    this.pedido = pedido;
    this.cantidad = cantidad;
    this.subtotal = subtotal;
  }

  public LineaPedido(Producto producto, int cantidad, Double subtotal) {
    this.producto = producto;
    this.cantidad = cantidad;
    this.subtotal = subtotal;
  }

  public Long getId() {
    return id;
  }

  public Producto getProducto() {
    return producto;
  }

  public void setProducto(Producto producto) {
    this.producto = producto;
  }

  public Pedido getPedido() {
    return pedido;
  }

  public void setPedido(Pedido pedido) {
    this.pedido = pedido;
  }

  public int getCantidad() {
    return cantidad;
  }

  public void setCantidad(int cantidad) {
    this.cantidad = cantidad;
  }

  public Double getSubtotal() {
    return subtotal;
  }

  public void setSubtotal(Double subtotal) {
    this.subtotal = subtotal;
  }
}
