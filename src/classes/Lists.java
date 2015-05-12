package classes;

import java.security.SecureRandom;
import java.util.Random;

import com.sun.javafx.geom.transform.CanTransformVec3d;

import exceptions.DetailsNotFoundException;
import exceptions.DuplicateKeyException;
import exceptions.InvalidContactException;
import exceptions.ParameterStringIsEmptyException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Lists extends Application {
	
	ListView<ContactDetails> liste = new ListView<>();
	TableView<ContactDetails> tabelle = new TableView<>();
	TreeView<String> baum = new TreeView<>();
	AddressBook buch = new AddressBook();
	
	public Lists() {
		fuelleBuch();
	}
	
	//DefaultNamen, damit wir Zufallskontakte entwickeln können
	private static char[] VALID_CHARACTERS =
		    "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456879".toCharArray();

	@Override
	public void start(Stage primaryStage) {
		
		primaryStage.setTitle("Cooles Listen");
		
		this.fuelleListe();
		this.fuelleTabelle();
		this.fuelleBaum();
		
		
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
	
	private void fuelleListe() {
		try {
			ContactDetails[] personen = buch.search("");
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

	private void fuelleTabelle() {
		
        //table.setEditable(true);
		
		TableColumn vorname =  new TableColumn("Vorname");
		vorname.setCellValueFactory(
                new PropertyValueFactory<ContactDetails, String>("vorname"));
		
		TableColumn nachname =  new TableColumn("Nachname");
		nachname.setCellValueFactory(
                new PropertyValueFactory<ContactDetails, String>("nachname"));
		
		TableColumn telefon =  new TableColumn("Telefon");
		telefon.setCellValueFactory(
                new PropertyValueFactory<ContactDetails, String>("telefonnummer"));
		
		TableColumn email =  new TableColumn("E-Mail");
		email.setCellValueFactory(
                new PropertyValueFactory<ContactDetails, String>("mail"));
		
		TableColumn adresse =  new TableColumn("Adresse");
		adresse.setCellValueFactory(
                new PropertyValueFactory<ContactDetails, String>("adresse"));
		
        
        tabelle.getColumns().addAll(
        		vorname, 
        		nachname, 
        		telefon,
        		email,
        		adresse
        		);

		try {
			ContactDetails[] personen = buch.search("");
			ObservableList<ContactDetails> namen = FXCollections.observableArrayList();
			for(ContactDetails person : personen){
				namen.add(person);
			}
			tabelle.setItems(namen);
			
		} catch (ParameterStringIsEmptyException | DetailsNotFoundException e) { e.getMessage();	}
		
	}

	private void fuelleBaum() {
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
