package br.com.iago.entity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

import br.com.iago.utils.Logger;

public class Report {

	private String nome;
	private String directory;
	
	public Report(String nome, String directory) {
		
		this.nome = nome;
		this.directory = directory;
		
	}
	
	public Boolean saveReport(ArrayList<String> messages) {
		
		if(this.directory.isEmpty()) {
			
			this.directory = this.getProjectPath();
			
		}
		
		if(this.checkDirectory(this.directory)) {
			
			try {
				
				File f = new File(this.directory+"/"+this.nome+".txt");
				PrintWriter pw = new PrintWriter(new FileWriter(f));
				pw.write(this.createReportByArray(messages));
				pw.close();
				return true;
				
			} catch (IOException ioEx) {
				
				ioEx.printStackTrace();
				Logger.saveLog(1, ioEx.getMessage(), new Date());
				JOptionPane.showMessageDialog(
					null, 
					"Problemas ao gravar arquivo.", 
					"Problemas", 
					JOptionPane.ERROR_MESSAGE
				);
				
			} catch (Exception ex) {
				
				ex.printStackTrace();
				Logger.saveLog(1, ex.getMessage(), new Date());
				
			}
		}

		return false;

	}
	
	public Boolean checkDirectory(String directory) {

		try {
		
			Path path = Paths.get(directory);
			if(Files.exists(path, LinkOption.NOFOLLOW_LINKS)) {
				
				return true;
				
			}
			
		} catch (SecurityException secEx) {
			
			secEx.printStackTrace();
			Logger.saveLog(1, secEx.getMessage(), new Date());
			JOptionPane.showMessageDialog(
					null, 
					"Permissão negada ao acessar diretório.", 
					"Permissão negada", 
					JOptionPane.ERROR_MESSAGE);
			
		} catch (InvalidPathException inPaEx) {

			inPaEx.printStackTrace();
			Logger.saveLog(1, inPaEx.getMessage(), new Date());
			JOptionPane.showMessageDialog(
					null, 
					"Problemas ao localizar diretório informado.", 
					"Diretório não localizado.", 
					JOptionPane.ERROR_MESSAGE);
			
		} catch (Exception ex) {
			
			ex.printStackTrace();
			Logger.saveLog(1, ex.getMessage(), new Date());
			
		}
		
		return false;

	}
	
	private String createReportByArray(ArrayList<String> arrStrings) {
		
		String reportString = "";
		
		for(String item : arrStrings) {
			
			reportString += item.concat("\n");
			
		}
		
		return reportString;
		
	}
	
	private String getProjectPath() {
		
		return System.getProperty("user.dir");
		
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDirectory() {
		return directory;
	}
	public void setDirectory(String direct) {
		this.directory = direct;
	}
}
