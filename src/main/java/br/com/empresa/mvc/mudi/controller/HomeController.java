package br.com.empresa.mvc.mudi.controller;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.empresa.mvc.mudi.model.Pedido;

@Controller
public class HomeController {
	
	@PersistenceContext //esta anotacao faz com que o hibernate configure o entity manager
	private EntityManager em;

	@GetMapping("/home")
	public String home(Model model) {
		
		Query query = em.createQuery("SELECT p FROM Pedido p");
		List pedidos = query.getResultList();
		
		model.addAttribute("pedidos", pedidos);
		
		return "home";
	}
}

/* ANOTAÇÕES */

/* Para saber qual é a instancia certa de uma classe,
 * podemos apertar F4 ou botao direito + open Type Hierachy
 * para ver o que podemos usar depois do new */
