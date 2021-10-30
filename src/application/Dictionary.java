package application;

import java.util.ArrayList;
import java.util.List;
public class Dictionary {
	  List<Word> words = new ArrayList<Word>();
	Dictionary(){
		
	}
	public void getDictionary(Word rs){
		words.add(rs);
	}
	
}

