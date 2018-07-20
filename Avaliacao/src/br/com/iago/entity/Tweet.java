package br.com.iago.entity;

import java.util.Date;

public class Tweet {
	
	private String autor;
	private Date postagem;
	private String mensagem;
	
	
	
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public Date getPostagem() {
		return postagem;
	}
	public void setPostagem(Date postagem) {
		this.postagem = postagem;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public String toString() {
		return "Autor: "+this.getAutor()+" \n"
		+"Postagem: "+this.getPostagem()+" \n"
		+"Mensagem: "+this.getMensagem();
	}
}
