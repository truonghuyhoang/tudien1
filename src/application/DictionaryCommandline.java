package application;


public class DictionaryCommandline {
	
	public void showAllWord(Dictionary rs ) {
		System.out.println("No" + " | English" + "\t| Vietnamese");
		for(int i=0;i<rs.words.size();++i) {
			System.out.println(i +"  |" +rs.words.get(i).getWord_target() + "\t\t  |"+ rs.words.get(i).getWord_explain() );
		}
	}
	
}

