package application;

import java.util.Optional;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.util.Pair;
/**
* Moja glowna klasa, nazwana LogOnWindow.
* Zbudowana na podstawie wskazowek projektowych.
* Generuje okno logowania za pomoca klasy LogonDialog oraz alert, zarowno w razie powodzenia,
* kiedy to informuje o wybranym srodowisku oraz o tym, jaki uzytkownik sie zalogowal,
* jak i nie, kiedy to informuje o bledzie logowania.
* 
* @author Karolina Bilewicz / K.Bilewicz@stud.elka.pw.edu.pl
* @version 0.9
*/
public class LogOnWindow extends Application {
	/** Metoda start. Glowny punkt wejsciowy aplikacji JavaFX. 
	 * Jest wolana, kiedy metoda init skonczy sie wykonywac i po tym, kiedy system jest gotowy na to,
	 * aby aplikacja zaczela dzialac.
	 * Tworzy ona obiekt klasy LogonDialog i wywoluje na nim metode showAndWait.
	 *
	 * @param primaryStage mozna ustawic scene aplikacji na primaryStage.
	 * @exception Exception gdy bedzie wyjatek do obsluzenia
	 * 
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		Optional<Pair<application.Environment, String>> result = (new LogonDialog("Logowanie",
				"Logowanie do systemu STYLEman")).showAndWait();
		
		if (result.isPresent()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Poprawnie zalogowano do systemu");
			alert.setHeaderText(null);
			Pair<application.Environment, String> p = result.get();
			
			String message = "Wybrane srodowisko: " + p.getKey().toString() + 
					". Zalogowano jako " + p.getValue();
			alert.setContentText(message);

			alert.showAndWait();
		}
		else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Niepoprawne dane");
			alert.setHeaderText(null);
			alert.setContentText("Logowanie nieudane");
			alert.showAndWait();
		}
		
	}
	
	/** Metoda main. 
	 *
	 * @param args argumenty wiersza polecen
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
