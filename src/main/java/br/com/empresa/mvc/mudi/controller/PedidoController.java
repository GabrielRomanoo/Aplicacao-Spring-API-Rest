package br.com.empresa.mvc.mudi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("pedido")
public class PedidoController {
	
	@GetMapping("formulario")
	public String formulario() {
		return "pedido/formulario";
	}
	
	@GetMapping("novo")
	public String novo(Model model) {
		model.getAttribute("nomeProduto");
		return "pedido/formulario";
	}
	

}
