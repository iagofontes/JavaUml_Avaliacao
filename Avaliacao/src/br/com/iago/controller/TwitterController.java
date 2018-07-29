package br.com.iago.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import br.com.iago.entity.Authentic;
import br.com.iago.entity.Tweet;
import br.com.iago.utils.Logger;
import br.com.iago.utils.Util;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class TwitterController {
	
	private Twitter twitter;
	
	public TwitterController() {
		
		this.twitter = this.criarObjetoTwitter();
		
	}
	
	private Twitter criarObjetoTwitter() {
		
		AccessToken accessToken = new AccessToken(Authentic.token, Authentic.tokenSecret);
		Twitter twitter = TwitterFactory.getSingleton();
		twitter.setOAuthConsumer(Authentic.consumerKey, Authentic.consumerSecret);
		twitter.setOAuthAccessToken(accessToken);
		return twitter;
		
	}
	
	public ArrayList<String> buscarQtdeTweets(String hashtag) {
		
		//busca a quantidade de tweets de acordo com uma hashtag específica
		Date hoje = this.montarDataParaFiltrar();

		ArrayList<Tweet> tweets = this.buscarTweets(
				Util.formatDateToString(this.buscarUltimaSemana(hoje)), 
				Util.formatDateToString(hoje), 
				hashtag
			);

		ArrayList<String> arr = new ArrayList<>();
		
		if(tweets.size() < 1) {
			return new ArrayList<String>();
		}

		Collections.sort(tweets);
		int qt = 0;
		Date date = tweets.get(0).getPostagem();
		arr.add("Quantidade de tweets por dia");
		
		for(Tweet tweet : tweets) {
			
			if(Util.formatDateToString(date).equals(Util.formatDateToString(tweet.getPostagem()))) {

				//se a data do tweet for igual a atual, só incremento.
				qt++;
				
			} else {

				//se a data do tweet não for igual, seto 1 no total 
				//atualizo a data e adiciono as informações no array
				arr.add(this.montarDescricaoTotalTweets(date, qt));
				qt=1;
				date = tweet.getPostagem();

			}

		}
		
		return arr;
		
	}
	
	public ArrayList<String> buscarQtdeRetweets(String hashtag) {
		
		//busca a quantidade de retweets de acordo com uma hashtag específica
		Date hoje = this.montarDataParaFiltrar();

		ArrayList<Tweet> tweets = this.buscarTweets(
				Util.formatDateToString(this.buscarUltimaSemana(hoje)), 
				Util.formatDateToString(hoje), 
				hashtag
			);

		ArrayList<String> arr = new ArrayList<>();

		if(tweets.size() < 1) {
			return arr;
		}
		
		Collections.sort(tweets);
		int qt = 0;
		Date date = tweets.get(0).getPostagem();
		arr.add("Quantidade de retweets por dia");
		
		for(Tweet tweet : tweets) {
			
			if(Util.formatDateToString(date).equals(Util.formatDateToString(tweet.getPostagem()))) {

				//se a data do tweet for igual a atual, só incremento.
				qt += tweet.getQtdeRetweets();
				
			} else {

				//se a data do tweet não for igual, seto 1 no total 
				//atualizo a data e adiciono as informações no array
				arr.add(this.montarDescricaoTotalTweets(date, qt));
				qt = tweet.getQtdeRetweets();
				date = tweet.getPostagem();

			}

		}
		
		return arr;
		
	}
	
	public ArrayList<String> buscarQtdeFavoritacoes(String hashtag) {
		
		//busca a quantidade de favoritações de acordo com uma hashtag específica
		Date hoje = this.montarDataParaFiltrar();

		ArrayList<String> arr = new ArrayList<>();
		
		ArrayList<Tweet> tweets = this.buscarTweets(
				Util.formatDateToString(this.buscarUltimaSemana(hoje)), 
				Util.formatDateToString(hoje), 
				hashtag
			);
		
		if(tweets.size() < 1) {
			return arr;
		}
		
		Collections.sort(tweets);
		int qt = 0;
		Date date = tweets.get(0).getPostagem();
		arr.add("Quantidade de favoritações por dia");
		
		for(Tweet tweet : tweets) {
			
			if(Util.formatDateToString(date).equals(Util.formatDateToString(tweet.getPostagem()))) {

				//se a data do tweet for igual a atual, só incremento.
				qt += tweet.getQtdeFavoritos();
				
			} else {

				//se a data do tweet não for igual, seto 1 no total 
				//atualizo a data e adiciono as informações no array
				arr.add(this.montarDescricaoTotalTweets(date, qt));
				qt = tweet.getQtdeFavoritos();
				date = tweet.getPostagem();

			}

		}

		return arr;
		
	}
	
	public ArrayList<String> buscarPUNome(String hashtag) {
		
		//busca o primeiro e último nome dos usuários dos tweets selecionados
		Date hoje = this.montarDataParaFiltrar();

		ArrayList<String> arr = new ArrayList<>();

		ArrayList<Tweet> tweets = this.buscarTweets(
				Util.formatDateToString(this.buscarUltimaSemana(hoje)), 
				Util.formatDateToString(hoje), 
				hashtag
			);
		
		if(tweets.size() < 1) {
			return arr;
		}
		
		Collections.sort(tweets);

		arr.add("Primeiro e último nome dos tweets encontrados: ");

		if(!tweets.get(0).getAutor().isEmpty()) {
			arr.add("Primeiro: "+tweets.get(0).getAutor().toString());
		}
		
		if(!tweets.get(tweets.size()-1).getAutor().isEmpty()) {
			arr.add("Último: "+tweets.get(tweets.size()-1).getAutor().toString());
		}

		return arr;
		
	}
	
	public ArrayList<String> buscarPUData(String hashtag) {
		
		//busca a quantidade de favoritações de acordo com uma hashtag específica
		Date hoje = this.montarDataParaFiltrar();
		
		ArrayList<String> arr = new ArrayList<>();
		
		ArrayList<Tweet> tweets = this.buscarTweets(
				Util.formatDateToString(this.buscarUltimaSemana(hoje)), 
				Util.formatDateToString(hoje), 
				hashtag
			);
		
		if(!(tweets.size() > 0)) {
			return arr;
		}
		
		Logger.saveLog(1, "teste", new Date());

		Date maior = tweets.get(0).getPostagem();
		Date menor = tweets.get(0).getPostagem();
		
		for(Tweet tweet : tweets) {
			if(tweet.getPostagem().after(maior)) {
				maior = tweet.getPostagem();
			}
			if(tweet.getPostagem().before(menor)) {
				menor = tweet.getPostagem();
			}
		}
		
		arr.add("Maiores e menores datas encontradas: ");
		arr.add("Maior data encontrada: "+Util.formatDateToBr(maior));
		arr.add("Menor data encontrada: "+Util.formatDateToBr(menor));
		return arr;
		
	}
	
	public String atualizarStatus(String texto) {
		
		String message = "Problemas ao atualizar status.";
		
		if(!texto.isEmpty()) {
			
			if(this.postarTweet(texto)) {
				
				message = "Tweet postado com sucesso.";
				
			}
			
		}
		
		return message;
	}
	
	private ArrayList<Tweet> getTimeLine() {
		
		try {
			
			List<Status> tweetsPure = this.twitter.getHomeTimeline();
			ArrayList<Tweet> tweets = new ArrayList<>();
			for(Status tweet : tweetsPure) {
				tweets.add(
						new Tweet(
								tweet.getUser().getScreenName(), 
								tweet.getCreatedAt(), 
								tweet.getText(),
								tweet.getFavoriteCount(),
								tweet.getRetweetCount()
						)
				);
			}
			
			return tweets;
				
		} catch (Exception e) {
			
			e.printStackTrace();
			Logger.saveLog(1, e.getMessage(), new Date());
		
		}
		
		return new ArrayList<Tweet>();
		
	}
	
	private Boolean postarTweet(String texto) {
		
		try {
			
			this.twitter.updateStatus(texto);
			return true;
			
		} catch (TwitterException te) {
			
			te.printStackTrace();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
		return false;
		
	}
	
	private ArrayList<Tweet> buscarTweets(String inicio, String fim, String hashtag) {
		
		//buscar os tweets de acordo com as datas e  
		//as hashtags informadas
		ArrayList<Tweet> tweets = new ArrayList<>();
		Query twitterQuery = new Query();
		
		try {
		
			if(!hashtag.isEmpty()) {			
				twitterQuery.setQuery(hashtag);
			}
			if(!inicio.isEmpty()) {
				twitterQuery.setSince(inicio);
			}
			if(!fim.isEmpty()) {
				twitterQuery.setUntil(fim);
			}
			
			QueryResult qResult = this.twitter.search(twitterQuery);
			
			while(qResult.hasNext()) {
				
				twitterQuery = qResult.nextQuery();
				
				for(Status tweet : qResult.getTweets()) {
					
					tweets.add(
							new Tweet(
									tweet.getUser().getScreenName(), 
									tweet.getCreatedAt(), 
									tweet.getText(),
									tweet.getFavoriteCount(),
									tweet.getRetweetCount()
							)
					);
					
				}
				
				qResult = this.twitter.search(twitterQuery);
				
			}

		} catch (Exception e) {
			
			e.printStackTrace();
			Logger.saveLog(1, e.getMessage(), new Date());
		}
		
		return tweets;
		
	}
	
	private String montarDescricaoTotalTweets(Date date, Integer total) {

		return Util.formatDateToBr(date)+" = "+total;
		
	}
	
	private Date buscarUltimaSemana(Date date) {

		//retornar a data de uma semana atrás, de acordo com a data passada como parâmetro
		Calendar cInst = Calendar.getInstance();
		cInst.setTime(date);
		cInst.add(Calendar.DATE, -7);
		return cInst.getTime();
		
	}

	private Date montarDataParaFiltrar() {
		
		//retorna a data para a filtragem da listagem
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, +1);
		return cal.getTime();
		
	}

	private String buscarNomePorDia(Integer dia) {
		switch(dia) {
			case 0 : 
				return "Domingo";
			case 1 : 
				return "Segunda-Feira";
			case 2 :
				return "Terça-Feira";
			case 3 :
				return "Quarta-Feira";
			case 4 :
				return "Quinta-Feira";
			case 5 :
				return "Sexta-Feira";
			case 6 :
				return "Sábado";
			default :
				return "Não reconhecido";
		}
	}
	
}