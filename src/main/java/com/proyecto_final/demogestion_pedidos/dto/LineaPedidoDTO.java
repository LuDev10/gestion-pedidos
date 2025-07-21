package com.proyecto_final.demogestion_pedidos.dto;

public class LineaPedidoDTO {

  private Long id;
  private int cantidad;
  private double precioUnitario;
  private Long pedidoId;
  private String nombreProducto;
  private double subtotal;
  private Long productoId;

  public LineaPedidoDTO() {
  }

  public LineaPedidoDTO(Long productoId, int cantidad, double precioUnitario, Long pedidoId,
                        String nombreProducto, double subtotal, Long id) {
    this.productoId = productoId;
    this.cantidad = cantidad;
    this.precioUnitario = precioUnitario;
    this.pedidoId = pedidoId;
    this.nombreProducto = nombreProducto;
    this.subtotal = subtotal;
    this.id = id;
  }

  public Long getProductoId() {
    return productoId;
  }

  public void setProductoId(Long productoId) {
    this.productoId = productoId;
  }

  public int getCantidad() {
    return cantidad;
  }

  public void setCantidad(int cantidad) {
    this.cantidad = cantidad;
  }

  public double getPrecioUnitario() {
    return precioUnitario;
  }

  public void setPrecioUnitario(double precioUnitario) {
    this.precioUnitario = precioUnitario;
  }

  public Long getPedidoId() {
    return pedidoId;
  }

  public void setPedidoId(Long pedidoId) {
    this.pedidoId = pedidoId;
  }

  public String getNombreProducto() {
    return nombreProducto;
  }

  public void setNombreProducto(String nombreProducto) {
    this.nombreProducto = nombreProducto;
  }

  public double getSubtotal() {
    return subtotal;
  }

  public void setSubtotal(double subtotal) {
    this.subtotal = subtotal;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
