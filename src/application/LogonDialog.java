package application;

import java.util.Optional;

import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

/**
* LogonDialog - klasa, ktora tworzy okienko logowania, kiedy tworzony jest obiekt tej klasy
* 
* @author Karolina Bilewicz / K.Bilewicz@stud.elka.pw.edu.pl
* @version 0.9
*/
public class LogonDialog {
	
	private ChoiceBox<Environment> environmentChoiceBox;
	private ComboBox<String> userIdComboBox;
	private PasswordField passField;
	private ButtonType logOnButton;
	private Dialog<Pair<Environment, String>> dialog;
	
	private Label env;
	private Label userLabel;
	private Label passLabel;
	private GridPane grid;
	private ButtonType cancelButton;
	/** Konstruktor
	 * Tworzy okno logowania ze wszystkimi potrzebnymi obiektami.
	 * Lista wyboru srodowiska jest na bazie ChoiceBox.
	 * Lista wyboru uzytkownika jest na bazie ComboBox.
	 * Pole wpisywania hasla na bazie PasswordField.
	 * Przyciski- Logon i Anuluj -  na bazie ButtonType.
	 * Zarzadca ukladu - GridPane.
	 * 
	 * Dodatkowo, lista wyboru srodowiska, lista wyborow uzytkownika
	 * i Pole wpisywania hasla maja dodane listenery, aby moc sprawdzic, czy
	 * przycisk Logon powinien byc juz aktywowany oraz czy lista uzytkownikow powinna sie
	 * zmienic, kiedy srodowisko zostalo zmienione.
	 * 
	 * Korzysta tez z metody na obiekcie klasy Dialog - setResultConverter,
	 * aby przekonwertowac ButtonType, kliknietego przez uzytkownika w Pair<Environment, String>.
	 * Sprawdzane jest, czy zostala wybrana opcja ok oraz czy haslo jest prawidlowe 
	 * dla danego uzytkownika w danym srodowisku.
	 * 
	 *
	 * @param windowTitle ustawia tytul okna logowania
	 * @param headerTitle ustawia tytul naglowka logowania
	 * 
	 */
	LogonDialog(String windowTitle, String headerTitle) {
		Users user = new Users();
		Environment test = new Environment("Testowe");
		Environment prod = new Environment("Produkcyjne");
		Environment dev = new Environment("Deweloperskie");
		
		dialog = new Dialog<>();
		dialog.setTitle(windowTitle);
		dialog.setHeaderText(headerTitle);
		ImageView loginImage = new ImageView(this.getClass().getResource("login.png").toString());
		loginImage.setPreserveRatio(true);
		loginImage.setFitHeight(100);
		dialog.setGraphic(loginImage);
			
		grid = new GridPane();
		
		env = new Label("Srodowisko: ");
		userLabel = new Label("Uzytkownik: ");
		passLabel = new Label("Haslo: ");
		
		
	    logOnButton = new ButtonType("Logon", ButtonData.OK_DONE);
	    cancelButton = new ButtonType("Anuluj", ButtonData.CANCEL_CLOSE);
	    
	    environmentChoiceBox = new ChoiceBox<Environment>();
		environmentChoiceBox.getItems().addAll(prod, test, dev);
	    environmentChoiceBox.setValue(prod);
	    
	    userIdComboBox = new ComboBox<String>();
	    userIdComboBox.getItems().addAll(user.getUserList(prod));
	    userIdComboBox.setEditable(true);    
	    
	    passField = new PasswordField();
	    passField.setPromptText("Haslo");
	    
		
		grid.add(env, 1, 1);
		grid.add(environmentChoiceBox, 2, 1);
		grid.add(userLabel, 1, 2);
		grid.add(userIdComboBox, 2, 2);
		grid.add(passLabel, 1, 3);
		grid.add(passField, 2, 3);
	    
	    dialog.getDialogPane().getButtonTypes().addAll(logOnButton, cancelButton);
	    dialog.getDialogPane().lookupButton(logOnButton).setDisable(true);
	    
	    environmentChoiceBox.valueProperty().addListener(
	    		(observable, oldVal, newVal) -> changeUserList(user, oldVal));
	    
	    userIdComboBox.valueProperty().addListener(
	    		(observable, oldVal, newVal) -> changeButton());
	    
	    passField.textProperty().addListener(
	    		(observable, oldVal, newVal) -> changeButton());
	    
	    
	    dialog.setResultConverter(dialogButton -> {
	    	if(dialogButton == logOnButton) {
	    		if(user.isPasswordValid(userIdComboBox.getValue(), passField.getText(),environmentChoiceBox.getValue()))
	    			return new Pair<Environment, String>(environmentChoiceBox.getValue(), userIdComboBox.getValue());
	    	}
	    	return null;
	    });	
		
		dialog.getDialogPane().setContent(grid);		
		
	}
	
	
	/** Metoda changeUserList. Zmienia liste uzytkownikow przy zmianach srodowiska.
	 *
	 * @param user obiekt, zawierajacy informacje o uzytkownikach dla danych srodowisk
	 * @param oldVal stara wartosc srodowiska
	 * 
	 */
	private void changeUserList(Users user, Environment oldVal) {
		Environment env = environmentChoiceBox.getValue();
		userIdComboBox.getItems().removeAll(user.getUserList(oldVal));
    	userIdComboBox.getItems().addAll(user.getUserList(env));
    	changeButton();
	}
	
	/* Metoda changeButton.
	 * Sprawdza, czy wszystkie pola, wymagana do aktywacji przycisku Logon, zostaly zapelnione.
	 */
	private void changeButton() {
		if(userIdComboBox.getValue() == null || environmentChoiceBox.getValue() == null
				|| passField.getText().trim().isEmpty()) {
			dialog.getDialogPane().lookupButton(logOnButton).setDisable(true);
		}
		else
			dialog.getDialogPane().lookupButton(logOnButton).setDisable(false);
	}
	
	/** Metoda showAndWait.
	 * 
	 * @return zwraca pare z wybranym srodowiskiem oraz uzytkownikiem, ktory sie do niego poprawnie zalogowal, a jesli niepoprawnie to null
	 */
	public Optional<Pair<Environment, String>> showAndWait() {
		return dialog.showAndWait();
	}
}
