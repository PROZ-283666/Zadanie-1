package application;

/**
* Environment - prosta klasa, zawierajaca informacje o srodowisku.
* 
* @author Karolina Bilewicz / K.Bilewicz@stud.elka.pw.edu.pl
* @version 0.9
*/
public class Environment {
	private String name;
	/** Konstruktor Environment
	 * @param n nazwa srodowiska
	 */
	Environment(String n) {
		name = n;
	}
	/** Metoda getName()
	 * @return nazwa srodowiska
	 */
	public String getName() {
		return name;
	}
	/** Metoda toString(), potrzebna, aby wyswietlac w ChoiceBox
	 * @return nazwa srodowiska
	 */
	public String toString() {
		return name;
	}
}
