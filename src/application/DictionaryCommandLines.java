package application;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class DictionaryCommandLines {
	DictionaryManagement rs = new DictionaryManagement();
	DictionaryCommandline rs1 = new DictionaryCommandline();
	Dictionary rs3 = new Dictionary();
	public void dictionaryBasic() {
		rs3 = rs.insertFromCommandline();
		rs1.showAllWord(rs3);
	}
	public void dictionaryAdvanced() {
		rs3 = rs.insertFromFile();
		rs1.showAllWord(rs3);
	}
	public List<String> dictionarySearch(Dictionary rs){
		List<String> rs1 = new ArrayList<String>();
		try (Scanner sc = new Scanner(System.in)) {
			String s = sc.nextLine();
			for(int i=0;i<rs.words.size();++i) {
				if(rs.words.get(i).getWord_target().contains(s)) rs1.add(rs.words.get(i).getWord_target());
			}
		}
		return rs1;
		
	}
	}
