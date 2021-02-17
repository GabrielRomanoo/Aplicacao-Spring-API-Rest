package br.com.empresa.mvc.mudi.controller;

import javax.naming.Binding;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.empresa.mvc.mudi.dto.RequisicaoNovoPedido;
import br.com.empresa.mvc.mudi.model.Pedido;
import br.com.empresa.mvc.mudi.model.User;
import br.com.empresa.mvc.mudi.repository.PedidoRepository;
import br.com.empresa.mvc.mudi.repository.UserRepository;

@Controller
@RequestMapping("pedido")
public class PedidoController {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	//  @RequestMapping(method = RequestMethod.GET, value="formulario")
	@GetMapping("formulario")
	public String formulario(RequisicaoNovoPedido requisicao) {
		return "pedido/formulario";
	}
	
	//@RequestMapping(method = RequestMethod.POST, value="novo")
	@PostMapping("novo")
	public String novo(@Valid RequisicaoNovoPedido requisicao, BindingResult result) { //anotação valid fala pro spring validar o objeto ao lado
		if (result.hasErrors()) {
			return "pedido/formulario";
		}
		
		//a classe SecurityContextHolder nos da quem é o usuario da requisição
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		User user = userRepository.findByUsername(username);
		
		Pedido pedido = requisicao.toPedido();
		pedido.setUser(user);
		
		pedidoRepository.save(pedido);
		return "redirect:/home";
	}
	

}
