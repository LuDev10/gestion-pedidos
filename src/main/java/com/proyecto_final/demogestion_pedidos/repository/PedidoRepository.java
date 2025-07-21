package com.proyecto_final.demogestion_pedidos.repository;

import com.proyecto_final.demogestion_pedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
