package classes;

import java.security.SecureRandom;
import java.util.Random;

import exceptions.DetailsNotFoundException;
import exceptions.DuplicateKeyException;
import exceptions.InvalidContactException;
import exceptions.ParameterStringIsEmptyException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Lists extends Application {
	
	/*
	 * Views!
	 * JavaFX stellt uns 3 verschiedene "Views" zur Verfügung um Daten horizontal oder vertikal darzustellen. 
	 * Dabei kann der User Elemente auswählen oder mit ihnen interagieren. 
	 * Eine View hat einen generischen Typen und kann somit den Typen der Daten repräsentieren und mit ihnen arbeiten.
	 * 
 	 * */
	
	// instantiiere ListView, TableView, TreeView und unser AddressBook
	ListView<ContactDetails> listeView = new ListView<>();
	TableView<ContactDetails> tabelleView = new TableView<>();
	TreeView<String> baumView;
	AddressBook buch = new AddressBook();
	
	//DefaultNamen, damit wir Zufallskontakte entwickeln können
	private static char[] VALID_CHARACTERS =
			    "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456879".toCharArray();
	
	public Lists() {
		// wir füllen unser AdressBuch
		fuelleBuch();
	}
	
	public Lists(AddressBook buch) {
		// wir füllen unser AdressBuch
		this.buch = buch;
	}

	@Override
	public void start(Stage primaryStage) {
		
		primaryStage.setTitle("Cooles Listen");
		// wir erstellen eine Liste
		// Die ListView dient zur einfachen vertikalen Darstellung von Daten. 
		this.erstelleListe();
		// wir erstellen eine Tabelle
		// Die TableView dient zur Darstellung und Editierung von Daten in tabellarischer Form. 
		// Sie ist Verwandt mit dem ListView und gehört zu den etwas komplexeren Komponenten. 
		this.erstelleTabelle();
		// wir erstellen einen Baum
		// Die TreeView dient zur vertikalen Darstellung von Baumstrukturen (Versachtelungen). 
		this.erstelleBaum();
		
		
		// wir generieren unsere FX Fenster
		Group group = new Group();
		
		group.getChildren().addAll(new HBox(
				new VBox(hLabel("Liste"),listeView),
				new VBox(hLabel("Tabelle"),tabelleView),
				new VBox(hLabel("Baum"),baumView)
				)
		);
		
		Scene scene = new Scene(group, 1200, 500);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	private Label hLabel(String str) {
		Label label = new Label(str);
	    label.setFont(new Font("Arial", 30));
	    
		return label;
	}
	
	/**
	 * fülle das Addressbook mit Defaultwerten
	 */
	private void fuelleBuch(){

		for(int i = 0; i < 500; i++){
			
			
			
			
			ContactDetails person = new ContactDetails(
					this.csRandomAlphaNumericString(5), 
					this.csRandomAlphaNumericString(6),
					this.csRandomAlphaNumericString(9),
					this.csRandomAlphaNumericString(12),
					this.csRandomAlphaNumericString(25)
					);	
			
			
			try {
				buch.addDetails(person);
			} catch (DuplicateKeyException | InvalidContactException | ParameterStringIsEmptyException e) {
				e.getMessage();
			}
			
			
			
			
		}
	}
	
	/**
	 * Eine ListView kann selbst keine Daten aufnehmen und wir müssen unsere Kontakte über eine besondere ArrayList (observableArrayList)
	 * hinzufügen. Eine observableArrayList ermöglicht uns auch mit eventHandler zu arbeiten.... hier wird das nicht benutzt.
	 * WICHTIG: die Daten, die angezeigt werden sollen, werden nicht als Strings übergeben sondern, für eine höhere Flexibilität, als Objekte.
	 * Dies ermöglicht uns, direkt mit den Objekten zu arbeiten. Damit die ListView "weiß", welcher der Daten anzuzeigen sind,
	 * müssen wir dies mit Hilfe von setCellFactory in einer inneren Funktion angeben. 
	 */
	
	private void erstelleListe() {
		try {
			// hole alle Kontakte
			ContactDetails[] personen = buch.search("");
			
			// wir definieren ein FXCollections.observableArrayList, welches die darzustellenden Daten enthält
			ObservableList<ContactDetails> namensListe = FXCollections.observableArrayList();
			
			// wir fügen jeden einzelnen Kontakt (Person) in unsere observableArrayList
			for(ContactDetails person : personen){
				namensListe.add(person);
			}
			
			// wir fügen unsere Daten der observableArrayList in unsere Liste hinzu
			listeView.setItems(namensListe);
			
			
			// wir editieren die Form, wie wir die Daten in unserer Liste anzeigen wollen
			// diese Methode ist aus dem Oracle Handbuch für ListViews
			listeView.setCellFactory((listItem) -> {
				// setCellFactory verlangt ein neues Objekt ListCell
			    return new ListCell<ContactDetails>() {
			    	// wir überschreiben die Methode updateItem
			        @Override
			        protected void updateItem(ContactDetails item, boolean empty) {
			            super.updateItem(item, empty);
			            // Wenn unserer ContactDetails leer ist .....
			            if (item == null || empty) {
			                setText(null);
			            } else { // und wenn nicht geben wir Nachname und Vorname aus.
			                setText(item.getNachname() + ", " + item.getVorname());
			            }
			        }
			    };
		    });
			
		} catch (ParameterStringIsEmptyException | DetailsNotFoundException e) { e.getMessage();	}
		
		
	}

	/**
	 * Die TableView ist Verwandt mit dem ListView und gehört zu den etwas komplexeren Komponenten. Dank JavaFX wird werden uns 
	 * Eigenschaften wie z.B. Sortierung der der Spalten, Layout etc. von Java abgenommen. Auch bei der TableView sollte 
	 * direkt mit den Objekten gearbeitet werden. Dazu muss auch hier wieder über setCellValueFactory angegeben werden, welche Attribute
	 * unseres Objektes angezeigt werden. Auch hier werden die Daten erst in einer observableArrayList gespeicht und dann der TableView übergeben.
	 */
	
	private void erstelleTabelle() {
		
		try {
			// als erstes holen wir alle Kontakte
			ContactDetails[] personen = buch.search("");
			// wir definieren ein FXCollections.observableArrayList, welches die darzustellenden Daten enthält
			ObservableList<ContactDetails> namen = FXCollections.observableArrayList();
			for(ContactDetails person : personen){
				namen.add(person);
			}
			// wir fügen unsere Daten der observableArrayList in unsere Tabelle hinzu
			tabelleView.setItems(namen);
			
		} catch (ParameterStringIsEmptyException | DetailsNotFoundException e) { e.getMessage();	}

		// Deklaration der Tabellenspalte mit dem Namen
		TableColumn<ContactDetails, String> vorname =  new TableColumn<ContactDetails, String>("Vorname");
		// hier sagen wir der Tabelle, dass es das Attribut vorname aus unseren Objekt nehmen soll. 
		// Dabei wird intern auf die getter Methoden zurückgegriffen
		vorname.setCellValueFactory(new PropertyValueFactory<ContactDetails, String>("vorname")); // intern -> contactDetails.getVorname();
		
		
		// analog zu wie oben beschrieben
		TableColumn<ContactDetails, String> nachname =  new TableColumn<ContactDetails, String>("Nachname");
		nachname.setCellValueFactory(new PropertyValueFactory<ContactDetails, String>("nachname"));
		
		TableColumn<ContactDetails, String> telefon =  new TableColumn<ContactDetails, String>("Telefon");
		telefon.setCellValueFactory(new PropertyValueFactory<ContactDetails, String>("telefonnummer"));
		
		TableColumn<ContactDetails, String> email =  new TableColumn<ContactDetails, String>("E-Mail");
		email.setCellValueFactory(new PropertyValueFactory<ContactDetails, String>("mail"));
		
		TableColumn<ContactDetails, String> adresse =  new TableColumn<ContactDetails, String>("Adresse");
		adresse.setCellValueFactory(new PropertyValueFactory<ContactDetails, String>("adresse"));
		
        // wir fügen unseren Tabellenspalten in unsere Tabelle ein
        tabelleView.getColumns().addAll(
        		vorname, 
        		nachname, 
        		telefon,
        		email,
        		adresse
        		);

	}

	/**
	 * Treeviews ermöglichen uns Daten in einer verschachtelte Baumstruktur anzuzeigen, wie wir es z.B. aus dem Dateimanager kennen
	 * Dabei wird nicht mehr mit einer Liste (observableArrayList) gearbeitet sondern mit Knoten. Jedem Knoten kann, nach Benennung ein weiterer Kinds-Knoten 
	 * übergeben werden. Somit kann eine Komplexe Baumstruktur erreicht werden. Der Wurzel-Knoten ist immer der oberste.   
	 */
	private void erstelleBaum() {
		// wir erstellen unseren Wurzel-Knoten mit dem Namen "Adressbuch"
		TreeItem<String> rootItem = new TreeItem<String> ("Adressbuch");
		// es soll "ausgeklappt" starten
        rootItem.setExpanded(true);

		try {

			// als erstes holen wir alle Kontakte
			ContactDetails[] personen = buch.search("");
	        for(ContactDetails person : personen){
	        	// wir arbeiten hier nicht mehr mit einer observableArrayList sondern direkt mit den Tree-Knoten
	        	TreeItem<String> item = new TreeItem<String> ("Kontakt: " + person.getVorname() + " " + person.getNachname()); 
	        	
	        	TreeItem<String> nameItem = new TreeItem<String> ("Vorname: " + person.getVorname());
	        	TreeItem<String> nachnameItem = new TreeItem<String> ("Nachname: " + person.getNachname());
	        	TreeItem<String> telefonItem = new TreeItem<String> ("Telefon: " + person.getTelefonnummer());
	        	TreeItem<String> emailItem = new TreeItem<String> ("E-Mail: " + person.getMail());
	        	TreeItem<String> adressItem = new TreeItem<String> ("Adresse: " + person.getAdresse());

	        	// jeder Kind-Knoten wird seinem Eltern-Knoten zugewiesen
	        	item.getChildren().addAll(
	        			nameItem,
	        			nachnameItem,
	        			telefonItem,
	        			emailItem,
	        			adressItem);
	        	// und schlussendlich an unseren Wurzel-Knoten übergeben
	            rootItem.getChildren().add(item);
			}
		} catch (ParameterStringIsEmptyException | DetailsNotFoundException e) { e.getMessage();	}
	
		// der Wurzel-Knoten kommt nun in die Treeview 
        baumView = new TreeView<String> (rootItem);
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	private String csRandomAlphaNumericString(int numChars) {
		// bei der Generierung von Zufallszahlen gibt es wie bei Pflanzen einen Samen, der zu Nachkommen führt. 
		// Aus diesem Startwert ermittelt der Zufallszahlengenerator anschließend die folgenden Zahlen durch lineare Kongruenzen.
		// Dadurch sind die Zahlen nicht wirklich zufällig, sondern gehorchen einem mathematischen Verfahren. 
		// Kryptografisch bessere Zufallszahlen liefert die Klasse java.security.SecureRandom, die eine Unterklasse von Random ist.
		SecureRandom srand = new SecureRandom();
		
	    Random rand = new Random();
	    // wir intantiieren uns Array vom Typ char mit der länge der Der Zeichen die unser Default-Wort haben soll
	    char[] buff = new char[numChars];

	    // nun durchlaufen wir eine for-Schleife für jedes Zeichen 
	    for (int i = 0; i < numChars; ++i) {
	      // damit auch bei Zeichenketten länger als 10 Zeichen der Zufall gewährleistet wird setzen wir einen neuen Samen mit Hilfe unseres 
	      // SecureRandom Super Algorithmus....
	      if ((i % 10) == 0) {
	    	  // and here is where the magic happens....
	          rand.setSeed(srand.nextLong()); // 64 bits of random!
	      }
	      // wir kreieren in unseren Array buff ein zufälliges Zeichen mit Hilfe der von Random und unseren Zeichen Array VALID_CHARACTERS.
	      buff[i] = VALID_CHARACTERS[rand.nextInt(VALID_CHARACTERS.length)];
	    }
	    return new String(buff);
	}
}
