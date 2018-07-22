package br.com.iago.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import br.com.iago.entity.Authentic;
import br.com.iago.entity.Tweet;
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
		ArrayList<String> arr = new ArrayList<>();
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
		Date maior = null;
		Date menor = null;
		
		for(Tweet tweet : tweets) {
			if(tweet.getPostagem().after(maior)) {
				maior = tweet.getPostagem();
			} else if(tweet.getPostagem().before(menor)) {
				menor = tweet.getPostagem();
			}
		}
		arr.add("Maiores e menores datas encontradas: ");
		arr.add("Maior data encontrada: "+maior.toString());
		arr.add("Menor data encontrada: "+menor.toString());
		return arr;
	}
	
	public ArrayList<Tweet> getTimeLine() {
		
		try {
			
			List<Status> tweetsPure = this.twitter.getHomeTimeline();
			ArrayList<Tweet> tweets = new ArrayList<>();
			for(Status tweet : tweetsPure) {
				tweets.add(
						new Tweet(
								tweet.getUser().getScreenName(), 
								tweet.getCreatedAt(), 
								tweet.getText()
						)
				);
			}
			return tweets;
		} catch (TwitterException tw) {
			
			tw.printStackTrace();
			JOptionPane.showMessageDialog(null, "Problemas ao buscar tweets.", "Opss...", JOptionPane.ERROR_MESSAGE);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Problemas ao buscar tweets.", "Opss...", JOptionPane.ERROR_MESSAGE);
		
		}
		return new ArrayList<Tweet>();
	}
	
	
	
}