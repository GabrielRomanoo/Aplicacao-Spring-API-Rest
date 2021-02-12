package br.com.empresa.mvc.mudi.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.empresa.mvc.mudi.model.Pedido;

@Controller
public class HomeController {

	@GetMapping("/home")
	public String home(Model model) {
		Pedido pedido = new Pedido();
		pedido.setNomeProduto("Iphone 11");
		pedido.setUrlImagem("https://images-na.ssl-images-amazon.com/images/I/71iO2R%2BCLjL._AC_SL1500_.jpg");
		pedido.setUrlProduto("https://www.amazon.com.br/Celular-Apple-iPhone-64gb-Tela/dp/B07XS2ZR1K");
		pedido.setDescricao("um iphone");
		
		List<Pedido> pedidos = Arrays.asList(pedido);
		
		model.addAttribute("pedidos", pedidos);
		
		return "home";
	}
}
