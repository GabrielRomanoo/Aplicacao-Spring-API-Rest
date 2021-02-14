package br.com.empresa.mvc.mudi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.empresa.mvc.mudi.dto.RequisicaoNovoPedido;
import br.com.empresa.mvc.mudi.model.Pedido;
import br.com.empresa.mvc.mudi.repository.PedidoRepository;

@Controller
@RequestMapping("pedido")
public class PedidoController {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	//  @RequestMapping(method = RequestMethod.GET, value="formulario")
	@GetMapping("formulario")
	public String formulario() {
		return "pedido/formulario";
	}
	
	//@RequestMapping(method = RequestMethod.POST, value="novo")
	@PostMapping("novo")
	public String novo(RequisicaoNovoPedido requisicao) {
		Pedido pedido = requisicao.toPedido();
		pedidoRepository.save(pedido);
		return "pedido/formulario";
	}
	

}
