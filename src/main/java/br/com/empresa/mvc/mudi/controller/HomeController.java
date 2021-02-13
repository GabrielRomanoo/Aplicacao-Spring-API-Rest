package br.com.empresa.mvc.mudi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.empresa.mvc.mudi.model.Pedido;
import br.com.empresa.mvc.mudi.repository.PedidoRepository;

@Controller
public class HomeController {
	
	@Autowired
	private PedidoRepository repository;

	@GetMapping("/home")
	public String home(Model model) {
		
		List<Pedido> pedidos = repository.recuperaTodosOsPedidos();
		
		model.addAttribute("pedidos", pedidos);
		
		return "home";
	}
}

/* ANOTAÇÕES */

/* Utilizamos o @AutoWired para indicar ao Spring que o 
 * objeto anotado é um componente ou Bean dele e que 
 * queremos que ele nos dê uma instância por meio 
 * do recurso de injeção de dependência.
 * 
 * E não basta só usar @Autowired, também é preciso anotar a 
 * classe em si para o Spring encontrar. Para tal, existem 
 * anotações como @Controller, @Repository e @Service
 */

/* Para saber qual é a instancia certa de uma classe,
 * podemos apertar F4 ou botao direito + open Type Hierachy
 * para ver o que podemos usar depois do new */
