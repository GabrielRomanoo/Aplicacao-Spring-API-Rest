package br.com.empresa.mvc.mudi.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.empresa.mvc.mudi.dto.RequisicaoNovaOferta;
import br.com.empresa.mvc.mudi.model.Oferta;
import br.com.empresa.mvc.mudi.model.Pedido;
import br.com.empresa.mvc.mudi.repository.PedidoRepository;

@RestController
@RequestMapping("/api/ofertas")
public class OfertasRest {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@PostMapping
	public Oferta criaOferta(@RequestBody RequisicaoNovaOferta requisicao) {
		
		Optional<Pedido> pedidoBuscado = pedidoRepository.findById(requisicao.getPedidoId());
		if (!pedidoBuscado.isPresent()) {
			System.out.println("DEU RUIM");
			return null;
		}
		
		Pedido pedido = pedidoBuscado.get();
		
		Oferta novaOferta = requisicao.toOferta();
		novaOferta.setPedido(pedido);
		
		pedido.getOfertas().add(novaOferta);
		
		pedidoRepository.save(pedido);
		
		return novaOferta;
	}

}
