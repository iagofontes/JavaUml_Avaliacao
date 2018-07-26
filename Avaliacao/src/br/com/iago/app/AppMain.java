package br.com.iago.app;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import br.com.iago.controller.TwitterController;

public class AppMain {

	public static TwitterController twitterController;
	public static String displayOption = "1 - Quantidade de tweets por dia \n"
			+ "2 - Quantidade de retweets por dia \n"
			+ "3 - Quantidade de favoritações por dia \n"
			+ "4 - Exibir autor do primeiro e último tweet \n"
			+ "5 - Exibir a data mais recente e a mais antiga \n"
			+ "6 - Postar tweet \n"
			+ "7 - Sair \n";
	public static ArrayList<String> hashtags = new ArrayList<>();
	
	public static void main(String[] args) {
		
		twitterController = new TwitterController();
		popularHashtags();
		
		while(true) {
			try {
				
				selectMenuOption(
						twitterController, 
						Integer.parseInt(
								JOptionPane.showInputDialog(
										displayOption
								).toString())
				);
				
			} catch (NullPointerException npex) {
				
				System.exit(0);
				
			} catch (NumberFormatException nfex) {
				
				nfex.printStackTrace();
				JOptionPane.showMessageDialog(
						null, 
						"Erro ao verificar opção selecionada.", 
						"Problemas", 
						JOptionPane.ERROR_MESSAGE);
				
			} catch (Exception e) {
				
				e.printStackTrace();
				JOptionPane.showMessageDialog(
						null, 
						"Problemas encontrados, tente novamente.", 
						"Problemas não reconhecidos", 
						JOptionPane.ERROR_MESSAGE);
				
			}
		}
		
	}
	
	public static void selectMenuOption(TwitterController twitterCtrl, Integer option) {
		switch(option) {
			case 1:
				
				apresentarListaStrings(
						twitterCtrl.buscarQtdeTweets(
								selecionarHashtag()
						),
						"Totalização dos tweets",
						"Nenhum tweet encontrado para totalização.");
				break;
			
			case 2:
				
				apresentarListaStrings(
						twitterCtrl.buscarQtdeRetweets(
								selecionarHashtag()
						),
						"Totalização dos retweets",
						"Nenhum retweet encontrado para totalização.");				
				break;
				
			case 3:
				
				apresentarListaStrings(
						twitterCtrl.buscarQtdeFavoritacoes(
								selecionarHashtag()
						),
						"Totalização dos favoritações",
						"Nenhum favoritação encontrada para totalização.");				
				break;
				
			case 4:
				
				apresentarListaStrings(
						twitterCtrl.buscarPUNome(),
						"Nomes da timeline",
						"Nenhum tweet encontrado.");				
				break;
				
			case 5:
				
				apresentarListaStrings(
						twitterCtrl.buscarPUData(),
						"Datas da timeline",
						"Nenhum tweet encontrado.");				
				break;
				
			case 6:

				atualizarStatus(twitterCtrl);
				break;
				
			case 7:
				
				System.exit(0);
				break;
				
			default:
				JOptionPane.showMessageDialog(
						null, 
						"Opção não encontrada, tente novamente.", 
						"Opss...", 
						JOptionPane.INFORMATION_MESSAGE
				);
				break;
				
		}
	}
	
	public static void apresentarListaStrings(ArrayList<String> arr, String jTitle, String eMessage) {
		//apresenta os nomes de datas dos primeiros e últimos tweets
		String message = "";
		if(arr.size() > 0) {
			for(String descricao : arr) {
				message += descricao+" \n";
			}
		} else {
			message = eMessage;
		}
		JOptionPane.showMessageDialog(null, message, jTitle, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static String selecionarHashtag() {
		return (String) JOptionPane.showInputDialog(
				null,
				"Selecione uma das hashtags abaixo:", 
				"Hashtags", 
				JOptionPane.QUESTION_MESSAGE,
				null,
				hashtags.toArray(),
				null
				);
	}
	
	public static void popularHashtags() {
		hashtags.add("#java");
		hashtags.add("#jvm");
		hashtags.add("#javaone");
		hashtags.add("#openjdk");
		hashtags.add("#java7");
		hashtags.add("#java8");
		hashtags.add("#java9");
	}
	
	public static void atualizarStatus(TwitterController twitterCtrl) {
		
		try {
			
			JOptionPane.showMessageDialog(
					null, 
					twitterCtrl.atualizarStatus(
						JOptionPane.showInputDialog(
								null,
								"Informe o texto para publicação:", 
								"Texto para status",
								JOptionPane.QUESTION_MESSAGE,
								null,
								null,
								null
							).toString()
					), 
					"Atualizar Status", 
					JOptionPane.INFORMATION_MESSAGE);
			
		} catch (NullPointerException npe) {
			
			npe.printStackTrace();
			JOptionPane.showMessageDialog(null, "Text inválido.", "Texto para stauts", JOptionPane.ERROR_MESSAGE);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
	}
}
