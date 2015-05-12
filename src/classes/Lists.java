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
import javafx.stage.Stage;

public class Lists extends Application {
	
	ListView<ContactDetails> liste = new ListView<>();
	TableView<ContactDetails> tabelle = new TableView<>();
	TreeView<String> baum = new TreeView<>();
	AddressBook buch = new AddressBook();
	
	public Lists() {
		// wir füllen unser AdressBuch
		fuelleBuch();
	}
	
	//DefaultNamen, damit wir Zufallskontakte entwickeln können
	private static char[] VALID_CHARACTERS =
		    "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456879".toCharArray();

	@Override
	public void start(Stage primaryStage) {
		
		primaryStage.setTitle("Cooles Listen");
		// wir erstellen eine Liste
		this.erstelleListe();
		// wir erstellen eine Tabelle
		// Das TableView dient zur Darstellung und Editierung von Daten in tabellarischer Form. 
		// Das TableView ist Verwandt mit dem ListView und gehört zu den etwas komplexeren Komponenten. 
		this.erstelleTabelle();
		// wir erstellen einen Baum
		this.erstelleBaum();
		
		
		Group group = new Group();
		
		group.getChildren().addAll(new HBox(liste,tabelle,baum));
		
		Scene scene = new Scene(group, 1200, 500);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
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
	
	private void erstelleListe() {
		try {
			// hole alle Kontakte
			ContactDetails[] personen = buch.search("");
			// wir definieren ein FXCollections.observableArrayList, welches die darzustellenden Daten enthält
			ObservableList<ContactDetails> namen = FXCollections.observableArrayList();
			for(ContactDetails person : personen){
				namen.add(person);
			}
			
			liste.setItems(namen);
			
			liste.setCellFactory((listItem) -> {
			    return new ListCell<ContactDetails>() {
			        @Override
			        protected void updateItem(ContactDetails item, boolean empty) {
			            super.updateItem(item, empty);
			            if (item == null || empty) {
			                setText(null);
			            } else {
			                setText(item.getNachname() + ", " + item.getVorname());
			            }
			        }
			    };
		    });
			
		} catch (ParameterStringIsEmptyException | DetailsNotFoundException e) { e.getMessage();	}
		
		
	}

	@SuppressWarnings("unchecked")
	private void erstelleTabelle() {
		
        //table.setEditable(true);
		
		TableColumn<ContactDetails, String> vorname =  new TableColumn<ContactDetails, String>("Vorname");
		vorname.setCellValueFactory(new PropertyValueFactory<ContactDetails, String>("vorname"));
		
		TableColumn<ContactDetails, String> nachname =  new TableColumn<ContactDetails, String>("Nachname");
		nachname.setCellValueFactory(new PropertyValueFactory<ContactDetails, String>("nachname"));
		
		TableColumn<ContactDetails, String> telefon =  new TableColumn<ContactDetails, String>("Telefon");
		telefon.setCellValueFactory(new PropertyValueFactory<ContactDetails, String>("telefonnummer"));
		
		TableColumn<ContactDetails, String> email =  new TableColumn<ContactDetails, String>("E-Mail");
		email.setCellValueFactory(new PropertyValueFactory<ContactDetails, String>("mail"));
		
		TableColumn<ContactDetails, String> adresse =  new TableColumn<ContactDetails, String>("Adresse");
		adresse.setCellValueFactory(new PropertyValueFactory<ContactDetails, String>("adresse"));
		
        
        tabelle.getColumns().addAll(
        		vorname, 
        		nachname, 
        		telefon,
        		email,
        		adresse
        		);

		try {
			// als erstes holen wir alle Kontakte
			ContactDetails[] personen = buch.search("");
			// wir definieren ein FXCollections.observableArrayList, welches die darzustellenden Daten enthält
			ObservableList<ContactDetails> namen = FXCollections.observableArrayList();
			for(ContactDetails person : personen){
				namen.add(person);
			}
			tabelle.setItems(namen);
			
		} catch (ParameterStringIsEmptyException | DetailsNotFoundException e) { e.getMessage();	}
		
	}

	@SuppressWarnings("unchecked")
	private void erstelleBaum() {
		try {
			ContactDetails[] personen = buch.search("");
		
			TreeItem<String> rootItem = new TreeItem<String> ("Adressbuch");
	        rootItem.setExpanded(true);
	        
	        for(ContactDetails person : personen){
	        	TreeItem<String> item = new TreeItem<String> (person.getVorname() + " " + person.getNachname()); 
	        	
	        	TreeItem<String> nameItem = new TreeItem<String> ("Vorname: " + person.getVorname());
	        	TreeItem<String> nachnameItem = new TreeItem<String> ("Nachname: " + person.getNachname());
	        	TreeItem<String> telefonItem = new TreeItem<String> ("Telefon: " + person.getTelefonnummer());
	        	TreeItem<String> emailItem = new TreeItem<String> ("E-Mail: " + person.getMail());
	        	TreeItem<String> adressItem = new TreeItem<String> ("Adresse: " + person.getAdresse());
	        	
	        	
	        	item.getChildren().addAll(
	        			nameItem,
	        			nachnameItem,
	        			telefonItem,
	        			emailItem,
	        			adressItem);
	            rootItem.getChildren().add(item);
			}
       
	        baum = new TreeView<String> (rootItem);
			
		} catch (ParameterStringIsEmptyException | DetailsNotFoundException e) { e.getMessage();	}
	}


	public static void main(String[] args) {
		launch(args);
	}
	
	private String csRandomAlphaNumericString(int numChars) {
		SecureRandom srand = new SecureRandom();
	    Random rand = new Random();
	    char[] buff = new char[numChars];

	    for (int i = 0; i < numChars; ++i) {
	      // reseed rand once you've used up all available entropy bits
	      if ((i % 10) == 0) {
	          rand.setSeed(srand.nextLong()); // 64 bits of random!
	      }
	      buff[i] = VALID_CHARACTERS[rand.nextInt(VALID_CHARACTERS.length)];
	    }
	    return new String(buff);
	}
}
