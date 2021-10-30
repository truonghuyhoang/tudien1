package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class SampleController  {

	@FXML
	AnchorPane root;

	@FXML
	TextField wordSearch, wordName;

	@FXML
	ListView<String> wordRcm;

	@FXML
	Button wordSound,historyButton;

	@FXML
	TextArea wordMeaning;

	@FXML
	Button add,search;

	Dictionary lists = new Dictionary();
	Dictionary lists1 = new Dictionary();
	List<String> history = new ArrayList<String>();

	public void initialize() {
		// initialization
		getRecommendList();
		System.out.print(lists.words.size());
		// properties

		// events
		wordSearch.setOnAction(e -> {
			try {
				String wordTarget = wordSearch.getText();
				boolean isCorrect = false;
				if (wordTarget != null && !wordTarget.contentEquals("")) {
					for (Word w: lists1.words) {
						if (w.getWord_target().equals(wordTarget)) {
							wordName.setText(wordTarget);
							wordMeaning.setText(w.getWord_explain());
							addtoHistory(wordTarget);
							isCorrect = true;
							break;
						}
					}
				}
				if (! isCorrect) {
					wordMeaning.setText("Wrong Word!!");
				}
			} catch (Exception ex) {
				System.out.println("wordSearch. onAction. " + ex);
			}
		});

		wordSearch.textProperty().addListener((obs, oldText, newText) -> {
			try {
				// System.out.println("text changed from "+ oldText + " to " + newText);
				actionListview(newText);
			} catch (Exception ex) {
				System.out.println("wordSearch. changeWord. " + ex);
			}
		});

		wordRcm.setOnMouseClicked(e -> {
			String wordTarget = wordRcm.getSelectionModel().getSelectedItem();
			if (wordTarget == null) {
				return;
			}
			for (Word w: lists1.words) {
				if (w.getWord_target().equals(wordTarget)) {
					wordName.setText(wordTarget);
					wordMeaning.setText(w.getWord_explain());
					addtoHistory(wordTarget);
					break;
				}
			}
		});

		add.setOnAction(e -> {
			try {
				showAddanddelete(e);
			} catch (Exception ex) {
				System.out.println("addButton. "+ ex);
			}
		});
		//
		search.setOnAction(e ->{
			try {
				String wordTarget = wordSearch.getText();
				boolean isCorrect = false;
				if (wordTarget != null && !wordTarget.contentEquals("")) {
					for (Word w: lists1.words) {
						if (w.getWord_target().equals(wordTarget)) {
							wordName.setText(wordTarget);
							wordMeaning.setText(w.getWord_explain());
							isCorrect = true;
							break;

						}
					}
				}
				else {
					wordRcm.getItems().clear();
					wordName.setText("");
					wordMeaning.setText(null);
					isCorrect = true;
				}
				if (! isCorrect) {

					wordMeaning.setText("Wrong Word!!");
				}
			} catch (Exception ex) {
				System.out.println("wordSearch. onAction. " + ex);
			}
		});
		historyButton.setOnAction(e -> {
			try {
				if(history.size()==0) wordMeaning.setText("Your history is clear !");
				else {
					String word ="";
					for(int i=0;i<history.size();++i) {
						word = word + '\n'+history.get(i);
					}
					wordMeaning.setText(word);
				}
			}catch (Exception ex) {
				System.out.print("asdf " +ex);
			}
		});
//	}
	}

	public void getRecommendList() {
		DictionaryManagement rs = new DictionaryManagement();
		rs.insertFromMySQL(lists);

	}

	public void actionListview(String word) {
		lists1.words.clear();
		for(int i=0;i<lists.words.size();++i) {
			if(lists.words.get(i).getWord_target().startsWith(word)) {
				lists1.words.add(lists.words.get(i));
			}
		}
		wordRcm.getItems().clear();
		for(int i=0;i<lists1.words.size();++i) {
			wordRcm.getItems().add(lists1.words.get(i).getWord_target());
		}

	}

	public void showAddanddelete(ActionEvent e) throws IOException {
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("addanddelete.fxml"));
		Parent sampleParent = loader.load();
		Scene scene = new Scene(sampleParent);
		stage.setScene(scene);
	}

	public void addtoHistory(String word) {
		history.add(word);
	}

}
