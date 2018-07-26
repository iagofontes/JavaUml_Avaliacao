package br.com.iago.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import br.com.iago.entity.Authentic;
import br.com.iago.entity.Tweet;
import br.com.iago.utils.Logger;
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
		Date hoje = new Date();
		ArrayList<Tweet> tweets = this.buscarTweets(
				this.formatDateToString(this.buscarUltimaSemana(hoje)), 
				this.formatDateToString(hoje), 
				hashtag
			);
		
		
		ArrayList<String> arr = new ArrayList<>();
		
		for(Tweet tweet : tweets) {
			
			
		}
		
		arr.add("Quantidade de tweets por dia");
		arr.add("Quarta-Feira = 10");
		arr.add("Quinta-Feira = 2");
		return arr;
		
	}
	
	public ArrayList<String> buscarQtdeRetweets(String hashtag) {
		
		//busca a quantidade de retweets de acordo com uma hashtag específica
		ArrayList<String> arr = new ArrayList<>();
		arr.add("Quantidade de retweets por dia");
		arr.add("Quarta-Feira = 10");
		arr.add("Quinta-Feira = 2");
		return arr;
		
	}
	
	public ArrayList<String> buscarQtdeFavoritacoes(String hashtag) {
		
		//busca a quantidade de favoritações de acordo com uma hashtag específica
		ArrayList<String> arr = new ArrayList<>();
		arr.add("Quantidade de favoritações por dia");
		arr.add("Quarta-Feira = 10");
		arr.add("Quinta-Feira = 2");
		return arr;
		
	}
	
	public ArrayList<String> buscarPUNome() {
		
		//busca a quantidade de favoritações de acordo com uma hashtag específica
		ArrayList<String> arr = new ArrayList<>();
		arr.add("Quantidade de favoritações por dia");
		arr.add("Quarta-Feira = 10");
		arr.add("Quinta-Feira = 2");
		return arr;
		
	}
	
	public ArrayList<String> buscarPUData() {
		
		//busca a quantidade de favoritações de acordo com uma hashtag específica
		ArrayList<String> arr = new ArrayList<>();
		ArrayList<Tweet> tweets = this.getTimeLine();
		if(!(tweets.size() > 0)) {
			return arr;
		}

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
		arr.add("Maior data encontrada: "+maior.toString());
		arr.add("Menor data encontrada: "+menor.toString());
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
	
	private Date buscarUltimaSemana(Date date) {

		//retornar a data de uma semana atrás, de acordo com a data passada como parâmetro
		Calendar cInst = Calendar.getInstance();
		cInst.setTime(date);
		cInst.add(Calendar.DATE, -7);
		return cInst.getTime();
		
	}
	
	private String formatDateToString(Date date) {
		
		SimpleDateFormat fd = new SimpleDateFormat("yyyy-MM-dd");
		return fd.format(date);
		
	}
	
	
	
}