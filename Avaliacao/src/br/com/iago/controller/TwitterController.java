package br.com.iago.controller;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import javax.swing.JOptionPane;

import br.com.iago.entity.Authentic;
import br.com.iago.entity.Report;
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
		Date maior = tweets.get(0).getPostagem();
		Date menor = tweets.get(0).getPostagem();
		if(!(tweets.size() > 0)) {
			return arr;
		}
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
	
	public String gerarRelatorio(String hashtag) {
		if((hashtag.isEmpty()) || (hashtag == null)) {
			return "Hashtag não informada, impossível continuar.";
		}
		
		ArrayList<String> arr = new ArrayList<>();
		ArrayList<String> arrTweets = this.buscarQtdeTweets(hashtag);
		ArrayList<String> arrRetweets = this.buscarQtdeRetweets(hashtag);
		ArrayList<String> arrTweetsFavs = this.buscarQtdeFavoritacoes(hashtag);
		ArrayList<String> arrTweetsPUNome = this.buscarPUNome(hashtag);
		ArrayList<String> arrTweetsPUData = this.buscarPUData(hashtag);
		
		arr.add("Relatório de totalização");
		arr.add("Hashtag selecionada: "+hashtag);
		arr.add("================ Quantidade de tweets ================");
		this.transferirStrings(arrTweets, arr);
		arr.add("================ Quantidade de retweets ================");
		this.transferirStrings(arrRetweets, arr);
		arr.add("================ Quantidade de favoritações ================");
		this.transferirStrings(arrTweetsFavs, arr);
		arr.add("================ Primeiro e último nome dos tweets ================");
		this.transferirStrings(arrTweetsPUNome, arr);
		arr.add("================ Primeira e última data dos tweets ================");
		this.transferirStrings(arrTweetsPUData, arr);
		
		//selecionar o caminho para salvar o relatório
		Report report = new Report(this.montarNomeRelatorio(), this.selecionarDiretorio());
		if(report.saveReport(arr)) 
			return "Relatório gerado com sucesso. \nSalvo em: "+report.getDirectory()+"/"+report.getNome();
		return "Problemas ao gerar relatório, tente novamente.";
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
	
	public String postarTotalizacao(String hashtag) {
		//função para totalizar os resultados e realizar a postagem mencionando o professor.
		Boolean err = false;
		ArrayList<String> arrTweets = this.buscarQtdeTweets(hashtag);
		ArrayList<String> arrRetweets = this.buscarQtdeRetweets(hashtag);
		ArrayList<String> arrTweetsFavs = this.buscarQtdeFavoritacoes(hashtag);
		ArrayList<String> arrTweetsPUNome = this.buscarPUNome(hashtag);
		ArrayList<String> arrTweetsPUData = this.buscarPUData(hashtag);
		String message = "";
		for(int i = 1; i <= 5; i++) {
			message = "Olá @michelpf.\n"
					+ "Hashtag selecionada: "+hashtag+"\n";
			switch(i) {
				case 1 :
					message += this.transferirArrayParaVariavel(arrTweets);
					break;
				case 2 :
					message += this.transferirArrayParaVariavel(arrRetweets);
					break;
				case 3 :
					message += this.transferirArrayParaVariavel(arrTweetsFavs);
					break;
				case 4 :
					message += this.transferirArrayParaVariavel(arrTweetsPUNome);
					break;
				case 5 :
					message += this.transferirArrayParaVariavel(arrTweetsPUData);
					break;
				default :
					break;
			}
			if(message.length() < 280) {
				if(!this.postarTweet(message)) {
					i--;
				}
			} else {
				err = true;
				break;
			}
		}
		if(err) {
			message = "Problemas ao postar totalização.";
		} else {
			message = "Totalização postada com sucesso.";
		}
		return message;
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
	
	private void transferirStrings(ArrayList<String> arrRead, ArrayList<String> arrUpdate) {
		//transfere string de um array para outro
		arrUpdate.addAll(arrRead);
	}
	
	private String selecionarDiretorio() {
		String[] array = {"Sim", "Não"};
		if(JOptionPane.showInputDialog(
				null,
				"Gerar relatório em diretório padrão ?", 
				"Diretório de relatório",
				JOptionPane.QUESTION_MESSAGE,
				null,
				array,
				null
			).toString() == "Não"
		) {
			
			//exibir nova janela para informar diretório
			String directory = JOptionPane.showInputDialog(
					null,
					"Informe o diretório para gerar o relatório.",
					"Informe o diretório",
					JOptionPane.QUESTION_MESSAGE).toString();
			if(Files.isDirectory(Paths.get(directory), LinkOption.NOFOLLOW_LINKS)) {
				return directory;
			}
		}
		return "";
	}
	
	private String montarNomeRelatorio() {
		
		return "twitterReport_"+((new Date()).getTime());
		
	}
	
	private String transferirArrayParaVariavel(ArrayList<String> arr) {
		
		String message = "";
		
		for(String item : arr) {
			
			message += item+"\n";
			
		}
		
		return message;
		
	}
	
}