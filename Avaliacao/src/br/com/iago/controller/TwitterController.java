package br.com.iago.controller;

import java.util.ArrayList;

import br.com.iago.entity.Authentic;
import twitter4j.Twitter;
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
	
	public ArrayList<Integer> buscarQtdeTweets() {
		return new ArrayList<Integer>();
	}
}
