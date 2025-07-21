package com.proyecto_final.demogestion_pedidos.repository;

import com.proyecto_final.demogestion_pedidos.model.LineaPedido;
import com.proyecto_final.demogestion_pedidos.model.Pedido;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface LineaPedidoRepository extends JpaRepository<LineaPedido, Long> {

  @Modifying
  @Transactional
  @Query("DELETE FROM LineaPedido lp WHERE lp.pedido.id = :pedidoId")
  void deleteAllByPedidoId(@Param("pedidoId") Long pedidoId);

  List<LineaPedido> findByPedidoId(Long pedidoId);

  List<LineaPedido> findByPedido(Pedido pedido);
}
