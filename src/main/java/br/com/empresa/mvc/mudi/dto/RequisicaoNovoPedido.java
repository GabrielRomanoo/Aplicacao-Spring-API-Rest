package br.com.empresa.mvc.mudi.dto;

import javax.validation.constraints.NotBlank;

import br.com.empresa.mvc.mudi.model.Pedido;

public class RequisicaoNovoPedido {

	/* Mesmos nomes dos names dos inputs da requisicao */
	
	@NotBlank //gera essa mensagem de erro -> NotBlank.requisicaoNovoPedido.nomeProduto = mensagem default (pode ser mudada no arquivo messages.properties)
	private String nomeProduto;
	
	@NotBlank //não aceita valores nulos ou vazios
	private String urlProduto;
	
	@NotBlank
	private String urlImagem;
	private String descricao;
	
	/* Usamos a anotação @NotBlank, mas existem várias outras anotações que podem ser usadas e combinadas. Exemplos clássicos são @Email, @Min, @Max ou @Pattern.

	Por exemplo, poderíamos definir que o nome do produto deve ter no mínimo 5 e no máximo 20 caracteres */
	
	//@NotBlank @Min(5) @Max(20)
    //private String nomeProduto; 


	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public String getUrlProduto() {
		return urlProduto;
	}

	public void setUrlProduto(String urlProduto) {
		this.urlProduto = urlProduto;
	}

	public String getUrlImagem() {
		return urlImagem;
	}

	public void setUrlImagem(String urlImagem) {
		this.urlImagem = urlImagem;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public Pedido toPedido() {
		Pedido pedido = new Pedido();
		pedido.setNomeProduto(nomeProduto);
		pedido.setDescricao(descricao);
		pedido.setUrlImagem(urlImagem);
		pedido.setUrlProduto(urlProduto);
		return pedido;
	}

	

}
