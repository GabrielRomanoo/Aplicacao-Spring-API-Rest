package br.com.empresa.mvc.mudi.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


import br.com.empresa.mvc.mudi.model.Oferta;

/*
 * Patter regexp
 * 
 * ^ = deve começar com...
 * \d = digito (se tiver + siginifca que pode ser varios)
 * (...)? = opcional
 * $ = acabou a string
 */
public class RequisicaoNovaOferta {
	
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	private Long pedidoId;

	//"92191239.99"
	@Pattern(regexp = "^\\d+(\\.\\d+{2})?$", message = "O campo Valor deve ser preenchido usando números") //indica a forma como este campo deve ser preenchido
	@NotNull(message = "O campo Valor é obrigatório")
	@NotBlank(message = "O campo Valor é obrigatório")
	private String valor;

	@Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}$", message = "O campo Data de Entrega deve ser prenchido como dia/mês/ano usando números")
	@NotNull(message = "O campo Valor é obrigatório")
	@NotBlank(message = "O campo Data de Entrega é obrigatório")
	private String dataDeEntrega;

	private String comentario;

	public Long getPedidoId() {
		return pedidoId;
	}

	public void setPedidoId(Long pedidoId) {
		this.pedidoId = pedidoId;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getDataDeEntrega() {
		return dataDeEntrega;
	}

	public void setDataDeEntrega(String dataDeEntrega) {
		this.dataDeEntrega = dataDeEntrega;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Oferta toOferta() {
		Oferta oferta = new Oferta();
		oferta.setComentario(comentario);
		oferta.setDataDaEntrega(LocalDate.parse(dataDeEntrega, formatter));
		oferta.setValor(new BigDecimal(valor));
		return oferta;	
	}

}
