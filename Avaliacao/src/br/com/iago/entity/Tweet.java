package br.com.iago.entity;

import java.util.Date;

public class Tweet implements Comparable<Tweet> {
	
	private String autor;
	private Date postagem;
	private String mensagem;
	private Integer qtdeFavoritos;
	private Integer qtdeRetweets;

	public Tweet(String autor, Date postagem, String mensagem, Integer favoritos, Integer retweets) {
		this.autor = autor;
		this.postagem = postagem;
		this.mensagem = mensagem;
		this.qtdeFavoritos = favoritos;
		this.qtdeRetweets = retweets;
	}
	
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
	public Integer getQtdeFavoritos() {
		return qtdeFavoritos;
	}
	public void setQtdeFavoritos(Integer qtdeFavoritos) {
		this.qtdeFavoritos = qtdeFavoritos;
	}
	public Integer getQtdeRetweets() {
		return qtdeRetweets;
	}
	public void setQtdeRetweets(Integer qtdeRetweets) {
		this.qtdeRetweets = qtdeRetweets;
	}
	public String toString() {
		return "Autor: "+this.getAutor()+" \n"
		+"Postagem: "+this.getPostagem()+" \n"
		+"Mensagem: "+this.getMensagem();
	}

	@Override
	public int compareTo(Tweet tweet) {
		
		if(this.getPostagem().before(tweet.getPostagem()))
			return 1;
		return 0;
		
	}
}
