package br.com.iago.app;

import javax.swing.JOptionPane;

import br.com.iago.controller.TwitterController;

public class AppMain {

	public static TwitterController twitterController;
	public static String displayOption = "1 - Quantidade de tweets por dia \n"
			+ "2 - Quantidade de retweets por dia \n"
			+ "3 - Quantidade de favoritações por dia \n"
			+ "4 - Exibir autor do primeiro e último tweet \n"
			+ "5 - Exibir a data mais recente e a mais antiga \n"
			+ "6 - Sair \n";
	
	public static void main(String[] args) {
		twitterController = new TwitterController();
		
		while(true) {
			try {
				selectMenuOption(twitterController, 
						Integer.parseInt(
								JOptionPane.showInputDialog(
										displayOption
								)
						)
				);
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
				
				JOptionPane.showMessageDialog(null, "opcao1", "Oi", JOptionPane.INFORMATION_MESSAGE);
				break;
				
			case 6:
				
				System.exit(0);
				
		}
	}

}
