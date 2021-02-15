package br.com.empresa.mvc.mudi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.empresa.mvc.mudi.model.Pedido;
import br.com.empresa.mvc.mudi.model.StatusPedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>{

	List<Pedido> findByStatus(StatusPedido status);

//	public List<Pedido> findAll(); //a interface JpaRepository ja tem o findAll implementado
}
