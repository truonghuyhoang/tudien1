package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
public class DictionaryManagement {
	public Dictionary  insertFromCommandline() {
		try (Scanner sc = new Scanner(System.in)) {
			int n = sc.nextInt();
			sc.nextLine();
			Dictionary rs = new Dictionary();
			for(int i=0;i<n;++i) {
				Word rs1 = new Word();
				String s = sc.nextLine();
				rs1.setWord_target(s);
				String s1 = sc.nextLine();
				rs1.setWord_explain(s1);
				rs.getDictionary(rs1);
			}
			return rs;
		}
	}
	public DictionaryManagement() {

	}
	public Dictionary insertFromFile() {
		Dictionary rs1 = new Dictionary();
		List<String> rs = new ArrayList<String>();
		File text = new File("dictionary.txt");
		Scanner scanner = null;
		try {
			scanner = new Scanner(text);
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();
			rs.add(line);
		}
		for(int i=0;i<rs.size();++i) {
			Word w = new Word();
			String[] rs3 = rs.get(i).split("\\t");
			w.setWord_target(rs3[0]);
			w.setWord_explain(rs3[1]);
			rs1.getDictionary(w);
		}
		return rs1;
	}

	public String dictionaryLookup(Dictionary rs,String s) {
		for(int i=0;i<rs.words.size();++i) {
			if(s.equals(rs.words.get(i).getWord_target())) return s;
		}
		return "NoLook";
	}
	public Dictionary fixDictionary(Dictionary rs) {
		try (Scanner sc = new Scanner(System.in)) {
			String s = sc.nextLine();
			String s1 = sc.nextLine();
			String s2 = sc.nextLine();
			for(int i=0;i<rs.words.size();++i) {
				if(rs.words.get(i).getWord_target().equals(s)) {
					rs.words.get(i).setWord_target(s1);
					rs.words.get(i).setWord_explain(s2);
					break;
				}
			}
		}
		return rs;

	}
	public Dictionary deleteDictionary(Dictionary rs) {
		try (Scanner sc = new Scanner(System.in)) {
			String s = sc.nextLine();
			for(int i=0;i<rs.words.size();++i) {
				if(rs.words.get(i).getWord_target().equals(s)) rs.words.remove(i);
			}
		}
		return rs;
	}
	public List<String> searchR(Dictionary rs,String word){
		List<String> temp = new ArrayList<String>();
		for(int i=0; i < rs.words.size();++i) {
			if (rs.words.get(i).getWord_target().startsWith(word)) {
				temp.add(rs.words.get(i).getWord_target());
			}
		}
		return temp;
	}
	public void dictionaryExportToFile(List<String> rs,String fileName) {
		try {
			File f = new File("C:\\Users\\Admin\\Downloads\\Dicitonary"+fileName);
			BufferedWriter fw = new BufferedWriter(new FileWriter(f));
			for(int i=0;i<rs.size();++i) {
				fw.write(rs.get(i));
				fw.write("\n");}
			fw.close();
		}catch (IOException ex) {
			System.out.println("Loi ghi file: " + ex);
		}
	}
	public List<String> List (String fileName) {
		List<String> rs = new ArrayList<String>();
		File text = new File(fileName);
		Scanner scanner = null;
		try {
			scanner = new Scanner(text);
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();
			rs.add(line);
		}
		scanner.close();
		return rs;
	}
	public void writetoField(String fileName,String Word) {
		FileWriter f;
		try {
			f = new FileWriter(fileName, true);

			f.write(Word);
			f.write("\r\n");
			f.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void insertFromMySQL(Dictionary Dict) {
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/dictionary","root","hoanglucky43");
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT * FROM `tbl_edict`");
			while(rs.next()) {
				String dtb = rs.getString("detail");
				dtb = dtb.substring(16, dtb.length());
				dtb = dtb.substring(0,dtb.length()-20);
				String[] words1 =  dtb.split("<br />");
				dtb="";
				for(String word: words1) {
					dtb=dtb+'\n'+word;

				}
				Word temp = new Word(rs.getString("word"),dtb);
				Dict.getDictionary(temp);
			}

		}catch(Exception e){ System.out.println(e);}
	}

	public void writetoDatabase(String wordTarget,String wordExplain,List<String> s) {
		int id = s.size()+1;
		try {
			String wordexplain = "<C><F><I><N><Q>@" + wordExplain + "</Q></N></I></F></C>";
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/dictionary","root","hoanglucky43");
			String query = "insert into tbl_edict (idx,word,detail) values ("+id+",'"+wordTarget+"','"+wordexplain+"') ";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.executeUpdate();
			con.close();
		}catch(Exception ex){
			System.out.println("meme : " + ex);
		}
	}
	public void mm (String wordTarget,List<String> s) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/dictionary","root","hoanglucky43");
			String query = "delete from tbl_edict where word = ? ";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1,wordTarget);
			preparedStmt.executeUpdate();
			con.close();
		}catch(Exception e) {
			System.out.println(e);
		}

	}


}
