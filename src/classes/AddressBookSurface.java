package classes;

import java.security.SecureRandom;
import java.util.Random;

import exceptions.DetailsNotFoundException;
import exceptions.DuplicateKeyException;
import exceptions.InvalidContactException;
import exceptions.KeyIsNotInUseException;
import exceptions.ParameterStringIsEmptyException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.input.MouseEvent;

public class AddressBookSurface extends Application {
	
	private static char[] VALID_CHARACTERS =
		    "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456879".toCharArray();
	
	private TableView<ContactDetails> tabelle = new TableView<ContactDetails>();
	private final ObservableList<ContactDetails> daten = FXCollections.observableArrayList();
	
	private AddressBook book = new AddressBook();
	
	final TextField addVorname = new TextField(), addNachname = new TextField(), addTelefonnummer = new TextField(), addMail = new TextField(), addAdresse = new TextField();
	
	private String key = null; 
	
	public AddressBookSurface(){
		this.fillAddressBook();
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		primaryStage.setTitle("Cooles Adressbuch");
		
		final Label titel = new Label("Unser Adressbuch");
		titel.setFont(new Font("Arial", 20));
		
	
		/*******************************************************************
		erstelle die Tabellenkopf
		*******************************************************************/
		
		tabelle.setEditable(true);
		
		TableColumn vorname = new TableColumn("Vorname");
		vorname.setMinWidth(100);
		vorname.setCellValueFactory(new PropertyValueFactory<ContactDetails, String>(
				"vorname"));
		TableColumn nachname = new TableColumn("Nachname");
		nachname.setMinWidth(100);
		nachname.setCellValueFactory(new PropertyValueFactory<ContactDetails, String>(
				"nachname"));
		TableColumn telefonnummer = new TableColumn("Telefonnummer");
		telefonnummer.setMinWidth(120);
		telefonnummer
				.setCellValueFactory(new PropertyValueFactory<ContactDetails, String>(
						"telefonnummer"));
		TableColumn email = new TableColumn("E-Mail");
		email.setMinWidth(100);
		email.setCellValueFactory(new PropertyValueFactory<ContactDetails, String>(
				"mail"));
		TableColumn adresse = new TableColumn("Adresse");
		adresse.setMinWidth(100);
		adresse.setCellValueFactory(new PropertyValueFactory<ContactDetails, String>(
				"adresse"));
		
		tabelle.getColumns().addAll(vorname, nachname, telefonnummer, email,
				adresse);
		
		this.erstelleTabelle(null);
		
		/*******************************************************************
		erstelle das Formular
		*******************************************************************/
		
		addVorname.setPromptText("Vorname");
		addVorname.setMaxWidth(vorname.getPrefWidth());
		
		addNachname.setPromptText("Nachname");
		addNachname.setMaxWidth(nachname.getPrefWidth());

		addTelefonnummer.setPromptText("Telefonnummer");
		addTelefonnummer.setMaxWidth(telefonnummer.getPrefWidth());

		addMail.setPromptText("E-Mail");
		addMail.setMaxWidth(email.getPrefWidth());

		addAdresse.setPromptText("Adresse");
		addAdresse.setMaxWidth(adresse.getPrefWidth());
		
		final Button addButton = new Button("Hinzufügen");
		addButton.setOnAction((ActionEvent event) -> fuegeKontaktHinzuHandler(event));

		final Button removeButton = new Button("Entfernen");
		removeButton.setOnAction((ActionEvent event) -> loescheKontaktHandler(event));
		
		final Button changeButton = new Button("Ändern");
		changeButton.setOnAction((ActionEvent event) -> aendereKontaktHandler(event));
		
		final Button searchButton = new Button("Suchen");
		searchButton.setOnAction((ActionEvent event) -> sucheKontaktHandler(event));
		
		final HBox hbox = new HBox();
		final HBox hbox2 = new HBox();
		hbox.getChildren().addAll(addVorname, addNachname, addTelefonnummer,addMail, addAdresse);
		hbox.setSpacing(3);
		hbox.setAlignment(Pos.CENTER);
		hbox2.getChildren().addAll(addButton, removeButton, changeButton,searchButton);
		hbox2.setSpacing(3);
		hbox2.setAlignment(Pos.CENTER);
		
		final VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		
		
		/*******************************************************************
		alles zusammenfügen
		*******************************************************************/
		
		vbox.getChildren().addAll(titel, tabelle, hbox, hbox2);
		
		Group group = new Group();
		group.getChildren().addAll(vbox);
		
		Scene scene = new Scene(group, 600, 700);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	private void erstelleTabelle(ContactDetails[] array) {
		ObservableList<ContactDetails> daten = FXCollections.observableArrayList();
		
		ContactDetails[] allContacts = null;
		
		if(array == null){
			try {
				allContacts = book.search("");
			} catch (DetailsNotFoundException | ParameterStringIsEmptyException e1) {
				// TODO Auto-generated catch block
				System.out.println(e1.getMessage());
			}
		}else{
			allContacts = array;
		}
		
		for(ContactDetails contact : allContacts ){
			daten.add(contact);
		}
		
		tabelle.setItems(daten);
		
		// wenn wir auf ein Listenelement klicken
		tabelle.setOnMouseClicked( (MouseEvent e) -> fuelleFelderHandler(e));
	}
	
	private ContactDetails generiereDetails(){
		
		ContactDetails newContact = new ContactDetails(
				addVorname.getText(), 
				addNachname.getText(), 
				addTelefonnummer.getText(), 
				addMail.getText(), 
				addAdresse.getText());
		
		return newContact;
	}
	
	public void leereFelder(){
		addVorname.clear();
		addNachname.clear();
		addTelefonnummer.clear();
		addMail.clear();
		addAdresse.clear();
	}

	private void sucheKontaktHandler(ActionEvent event) {
		
		String name = addVorname.getText();
		String lastname = addNachname.getText(); 
		String phone = addTelefonnummer.getText(); 
		String email = addMail.getText(); 
		String address = addAdresse.getText();
		
		String keyPrefix = name;

		ContactDetails[] matched = null;
		
		try {
			matched = book.search(keyPrefix);
		} catch (ParameterStringIsEmptyException | DetailsNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
		
		this.erstelleTabelle(matched);
	}

	private void aendereKontaktHandler(ActionEvent event) {
		
		ContactDetails details = this.generiereDetails();
		
		try {
			book.changeDetails(key, details);
			key = book.generateKey(details);
			this.erstelleTabelle(null);
		} catch (DuplicateKeyException | InvalidContactException
				| KeyIsNotInUseException | ParameterStringIsEmptyException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}

	private void loescheKontaktHandler(ActionEvent event) {
		try {
			book.removeDetails(key);
			key = null;
			this.leereFelder();
			this.erstelleTabelle(null);
		} catch (KeyIsNotInUseException | ParameterStringIsEmptyException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}

	private void fuegeKontaktHinzuHandler(ActionEvent e){
		
		ContactDetails newContact = this.generiereDetails();

		try {
			book.addDetails(newContact);
			
			daten.add(newContact);
			this.leereFelder();
			this.erstelleTabelle(null);
		} catch (DuplicateKeyException | InvalidContactException
				| ParameterStringIsEmptyException e1) {
			// TODO Auto-generated catch block
			System.out.println(e1.getMessage());
		}
	}
	
	private void fuelleFelderHandler(MouseEvent e){
		
		ContactDetails details = tabelle.getSelectionModel().getSelectedItem();
		
		addVorname.setText(details.getVorname());
		addNachname.setText(details.getNachname());
		addTelefonnummer.setText(details.getTelefonnummer());
		addMail.setText(details.getMail());
		addAdresse.setText(details.getAdresse());
		
		try {
			key = book.generateKey(details);
		} catch (ParameterStringIsEmptyException e1) {
			// TODO Auto-generated catch block
			System.out.println(e1.getMessage());
		}
	}

	public void fillAddressBook(){
		
		for(int i = 0; i < 100; i++){
			ContactDetails a = new ContactDetails(
					this.csRandomAlphaNumericString(5), 
					this.csRandomAlphaNumericString(6),
					this.csRandomAlphaNumericString(9),
					this.csRandomAlphaNumericString(12),
					this.csRandomAlphaNumericString(25)
					);	
			try {
				book.addDetails(a);
			} catch (DuplicateKeyException | InvalidContactException
					| ParameterStringIsEmptyException e) {
				System.out.println(e.getMessage());
			}
		}

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