package application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class Controllerad  {
	@FXML
	TextField word_target,RCM;
	@FXML
	TextArea word_explain;
	@FXML
	Button addButton,deleteButton,backButton,resetButton;

	List<String> list = new ArrayList<String>();
	List<String> list1 = new ArrayList<String>();
	DictionaryManagement rs = new DictionaryManagement();
	Dictionary rs1 = new Dictionary();
	public void gobackSample(ActionEvent e) throws IOException {
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("Sample.fxml"));
		Parent sampleParent = loader.load();
		Scene scene = new Scene(sampleParent);
		stage.setScene(scene);
		String css = this.getClass().getResource("/application/application.css").toExternalForm();
		scene.getStylesheets().add(css);
	}

	public void getList() {
		rs.insertFromMySQL(rs1);
		for(int i=0;i<rs1.words.size();++i) {
			list.add(rs1.words.get(i).getWord_target());
			list1.add(rs1.words.get(i).getWord_explain());
		}


	}

	public boolean availbleWord(String word) {
		if(list.contains(word)) return true;
		else return false;
	}

	public void writeField() {
		rs.writetoDatabase(word_target.getText(), word_explain.getText(),list);
	}
	public void deleteList() {
		String word = word_target.getText();
		int dem = 0;
		if(availbleWord(word)) {
			for(int i=0;i<list.size();++i) {
				if(list.get(i).equals(word)) {
					dem=i;
					break;
				}
			}
		}
		list.remove(dem);
		list1.remove(dem);
	}

	public void initialize() {
		getList();

		addButton.setOnAction(e -> {
			try {
				String word = new String(word_target.getText());
				String word1 = new String(word_explain.getText());
				if(word == null || word.equals("")) {
					return;
				}
				else {
					if(availbleWord(word)==true) {
						RCM.setText("Word is availble in Dictionary");
					}
					else {
						list.add(word);
						list1.add(word1);
						writeField();
						RCM.setText("Word was added succeed");
					}
				}
			}catch(Exception ex) {
				System.out.println("Controllerad. addButton. " + ex);
			}
		});
		//deleteButton
		deleteButton.setOnAction(e ->{
			try {
				String word = new String(word_target.getText());
				if(availbleWord(word)==true) {
					deleteList();
					rs.mm(word, list);
					RCM.setText("word was deleted succeed!");
				}
				else {
					RCM.setText("Word is not avaible !");
				}


			}catch(Exception ex) {
				System.out.println("khong xoa dc tu: " + ex);
			}
		});
		//resetNutton
		resetButton.setOnAction(e ->{
			try {
				word_target.setText("");
				word_explain.setText("");
				RCM.setText("");
			}catch(Exception ex) {
				System.out.println("me me: " + ex);
			}
		});

	}


}
