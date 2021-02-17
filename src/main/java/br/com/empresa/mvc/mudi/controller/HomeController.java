package br.com.empresa.mvc.mudi.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.empresa.mvc.mudi.model.Pedido;
import br.com.empresa.mvc.mudi.model.StatusPedido;
import br.com.empresa.mvc.mudi.repository.PedidoRepository;

@Controller
@RequestMapping("/home")
public class HomeController {
	
	@Autowired
	private PedidoRepository pedidoRepository;

	@GetMapping
	public String home(Model model, Principal principal) { //objeto principal pegamos dados do usuario
		List<Pedido> pedidos = pedidoRepository.findAllByUser(principal.getName());
		model.addAttribute("pedidos", pedidos);
		return "home";
	}
	
	@GetMapping("/{satus}")
	public String porStatus(@PathVariable("satus") String status, Model model) { //o spring vai injetar no parametro status uma variavel que vem do path chamada status 
//		List<Pedido> pedidos = pedidoRepository.findByStatus(StatusPedido.valueOf(status.toUpperCase()));
		List<Pedido> pedidos = pedidoRepository.findByStatusAndUser(StatusPedido.valueOf(status.toUpperCase()), SecurityContextHolder.getContext().getAuthentication().getName());
		model.addAttribute("pedidos", pedidos);
		model.addAttribute("status", status);
		return "home";
	}
	
	//se ouver algum erro, entra no metodo abaixo
	@ExceptionHandler(IllegalArgumentException.class)
	public String onError() {
		return "redirect:/home";
	}
	
//	@GetMapping("/home")
//	public ModelAndView home() {
//	    List<Pedido> pedidos = pedidoRepository.findAll();
//	    ModelAndView mv = new ModelAndView("home");
//	    mv.addObject("pedidos", pedidos);
//	    return mv; 
//	}   
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
