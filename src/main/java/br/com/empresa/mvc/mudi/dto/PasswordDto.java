package br.com.empresa.mvc.mudi.dto;

public class PasswordDto {

    private String novasenha;
    
    private String novasenha2;

    private String token;

	public String getNovasenha() {
		return novasenha;
	}

	public void setNovasenha(String novasenha) {
		this.novasenha = novasenha;
	}

	public String getNovasenha2() {
		return novasenha2;
	}

	public void setNovasenha2(String novasenha2) {
		this.novasenha2 = novasenha2;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
    
}