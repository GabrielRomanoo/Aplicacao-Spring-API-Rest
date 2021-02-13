package br.com.empresa.mvc.mudi.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.empresa.mvc.mudi.model.Pedido;

@Repository
public class PedidoRepository {

	@PersistenceContext //esta anotacao faz com que o hibernate configure o entity manager
	private EntityManager em;
	
	public List<Pedido> recuperaTodosOsPedidos() {
		Query query = em.createQuery("SELECT p FROM Pedido p");
		return query.getResultList();
	}
}
