package application;

import java.util.Arrays;
import java.util.List;

import javafx.collections.ObservableList;


/**
* Users - prosta klasa, zawierajaca informacje o uzytkownikach z roznych srodowisk
* oraz haslach
* 
* @author Karolina Bilewicz / K.Bilewicz@stud.elka.pw.edu.pl
* @version 0.9
*/
public class Users {
	private List<String> userListProdukcyjne;
	private List<String> userListTestowe;
	private List<String> userListDeweloperskie;
	
	private List<String> passListProdukcyjne;
	private List<String> passListTestowe;
	private List<String> passListDeweloperskie;
	
	/** Konstruktor Users
	 * odgornie tworzy listy uzytkownikow wraz z haslami dla nich
	 */
	Users()
	{
		userListTestowe= Arrays.asList("adam.nowak", "ewa.cudna", "jan.kowalski");
		userListProdukcyjne = Arrays.asList("janina.nowak", "robert.cudny", "renata.kowalska");
		userListDeweloperskie= Arrays.asList("tomasz.nowak", "kamila.cudna", "grzegorz.kowalski");
		
		passListTestowe= Arrays.asList("adam", "ewa", "jan");
		passListProdukcyjne= Arrays.asList("janina", "robert", "renata");
		passListDeweloperskie= Arrays.asList("tomasz", "kamila", "grzegorz");
	}
	
	/** Metoda isPasswordValid. Autentykuje uzytkownikow
	 * @param user nazwa uzytkownika
	 * @param pass haslo uzytkownika
	 * @param current aktualne srodowisko, do ktorego probuje sie zalogowac dany uzytkownik
	 * @return Zwraca prawde, jesli autentykacja uzytkownika powiodla sie.
	 */
	public Boolean isPasswordValid(String user, String pass, Environment current) {
		int index;
		if(current.getName() == "Produkcyjne" && (index = userListProdukcyjne.indexOf(user) ) != -1) {
			if(passListProdukcyjne.indexOf(pass) == index)
				return true;
		}
		else if(current.getName() == "Testowe" && ( index = userListTestowe.indexOf(user) )!= -1) {
			if(passListTestowe.indexOf(pass) == index)
				return true;
		}
		else if(current.getName() == "Deweloperskie" && ( index = userListDeweloperskie.indexOf(user) )!= -1) {
			if(passListDeweloperskie.indexOf(pass) == index) {
				return true;
			}
				
		}
		return false;
		
	}
	/** Metoda getUserList. Zwraca nazwy uzytkownikow dla danego srodowiska
	 * @param current aktualne srodowisko
	 * @return nazwy uzytkownikow
	 */
	public List<String> getUserList(Environment current) {
		if(current.getName() == "Produkcyjne")
			return userListProdukcyjne;
		else if(current.getName() == "Testowe")
			return userListTestowe;
		else if(current.getName() == "Deweloperskie")
			return userListDeweloperskie;
		else 
			return null;
	}

}
