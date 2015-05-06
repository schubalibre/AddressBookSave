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
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.input.MouseEvent;

public class AddressBookSurface extends Application {
	
	//DefaultNamen, damit wir Zufallskontakte entwickeln können
	private static char[] VALID_CHARACTERS =
		    "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456879".toCharArray();
	
	//Tabelle hat intern eine ObservableArrayList
	private TableView<ContactDetails> tabelle = new TableView<ContactDetails>();
	
	//neues Addressbuch
	private AddressBook book = new AddressBook();
	
	//Felder kreieren
	final Label labelvorname = new Label("Vorname:");
	final Label labelnachname = new Label("Nachname:");
	final Label labeltelefonnummer = new Label("Telefonnummer:");
	final Label labelemail = new Label("E-Mail:");
	final Label labeladresse = new Label("Adresse:");
	
	final HBox hbox5 = new HBox();
	
	final TextField addVorname = new TextField(), addNachname = new TextField(), addTelefonnummer = new TextField(), addMail = new TextField(), addAdresse = new TextField();
	
	//Speichern den Key: alles
	private String key = null; 
	
	//Konstruktor, der Methode fillA. aufruft
	public AddressBookSurface(){
		this.fillAddressBook();
	}
	
	//Main Methode, die das Programm startet
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		primaryStage.setTitle("Cooles Adressbuch");
		
		final Label titel = new Label("Unser Adressbuch");
		titel.setFont(new Font("Arial", 20));
		
		labelvorname.setFont(new Font("Arial", 12));
		labelnachname.setFont(new Font("Arial", 12));
		labeltelefonnummer.setFont(new Font("Arial", 12));
		labelemail.setFont(new Font("Arial", 12));
		labeladresse.setFont(new Font("Arial", 12));
	
		/*******************************************************************
		erstelle die Tabellenkopf
		*******************************************************************/
		
		tabelle.setEditable(true);
		
		TableColumn vorname = new TableColumn("Vorname");
		vorname.setMinWidth(150);
		vorname.setCellValueFactory(new PropertyValueFactory<ContactDetails, String>(
				"vorname"));
		TableColumn nachname = new TableColumn("Nachname");
		nachname.setMinWidth(150);
		nachname.setCellValueFactory(new PropertyValueFactory<ContactDetails, String>(
				"nachname"));
		TableColumn telefonnummer = new TableColumn("Telefonnummer");
		telefonnummer.setMinWidth(150);
		telefonnummer
				.setCellValueFactory(new PropertyValueFactory<ContactDetails, String>(
						"telefonnummer"));
		TableColumn email = new TableColumn("E-Mail");
		email.setMinWidth(150);
		email.setCellValueFactory(new PropertyValueFactory<ContactDetails, String>(
				"mail"));
		TableColumn adresse = new TableColumn("Adresse");
		adresse.setMinWidth(150);
		adresse.setCellValueFactory(new PropertyValueFactory<ContactDetails, String>(
				"adresse"));
		
		tabelle.getColumns().addAll(vorname, nachname, telefonnummer, email,
				adresse);
		
		//Attribut null
		this.erstelleTabelle(null);
		
		/*******************************************************************
		erstelle das Formular, Eingabefelder und Knöpfe
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
		
		final Button leereFelder = new Button("Felder leeren");
		leereFelder.setOnAction((ActionEvent event) -> leereFelderHandler(event));
		
		final Button zurueckButton = new Button("Zurück zum Adressbuch");
		zurueckButton.setOnAction((ActionEvent event) -> zurueckButtonHandler(event));
		
		final HBox hbox = new HBox();
		final HBox hbox2 = new HBox();
		
		final HBox hbox3 = new HBox();
		final HBox hbox4 = new HBox();

		hbox.getChildren().addAll(labelvorname, addVorname, labelnachname, addNachname, labeltelefonnummer, addTelefonnummer, labelemail, addMail, labeladresse, addAdresse);
		hbox.setSpacing(10);
		hbox.setAlignment(Pos.CENTER);
		hbox2.getChildren().addAll(addButton, removeButton, changeButton, searchButton, leereFelder, zurueckButton);
		hbox2.setSpacing(10);
		hbox2.setAlignment(Pos.CENTER);
		hbox3.setMinHeight(30);
		hbox4.setMinHeight(30);
		
		final VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		
		
		/*******************************************************************
		alles zusammenfügen
		*******************************************************************/
		
		vbox.getChildren().addAll(titel, tabelle, hbox3, hbox, hbox4, hbox2, hbox5);
		
		Group group = new Group();
		group.getChildren().addAll(vbox);
		
		Scene scene = new Scene(group, 800, 700);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	private void zurueckButtonHandler(ActionEvent event) {
		errorNachricht(null);
		erstelleTabelle(null);
	}

	private void leereFelderHandler(ActionEvent event) {
		key=null;
		errorNachricht(null);
		leereFelder();
	}

	private void errorNachricht(String message){
		
		hbox5.getChildren().clear();
		final Label errorlabel = new Label(message);
		errorlabel.setFont(new Font("Arial", 16));
		errorlabel.setTextFill(Color.RED);
		
		hbox5.getChildren().addAll(errorlabel);
		hbox5.setAlignment(Pos.CENTER);
	}
	
	//Bekommt ein Array von ContactDetails, wenn ich suche, rufe ich diese Methode auf und übergebe die Werte, die ich gefunden habe. Wenn die Suche leer ist, soll man mir alles zeigen. Tabelle guckt, ob im Array etwas drin ist, wenn nicht, golt sich die Methode automatisch alle Kontakte und packt sie in die Tabelle. Wenn das Array nicht leer ist, dann nehme ich das was im Array ist (die, die ich bei der Suche gefunden habe). Array mit gefundenen Kontakten wird der Tabelle übergeben.
	private void erstelleTabelle(ContactDetails[] array) {
		ObservableList<ContactDetails> daten = FXCollections.observableArrayList();
		
		ContactDetails[] allContacts = null;
		
		if(array == null){
			try {
				allContacts = book.search(null);
			} catch (DetailsNotFoundException | ParameterStringIsEmptyException e1) {
				// TODO Auto-generated catch block
				errorNachricht(e1.getMessage());
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
	
	//addVorname sind Felder von oben, speichert den Text rein
	//Beim Anklicken, bekommt man unten in den Feldern alles reingeschrieben, ausgelagerte Methode
	private ContactDetails generiereDetails(){
		
		ContactDetails newContact = new ContactDetails(
				addVorname.getText(), 
				addNachname.getText(),
				addTelefonnummer.getText(), 
				addMail.getText(),
				addAdresse.getText()
				);
		
		return newContact;
	}
	
	public void leereFelder(){
		
		errorNachricht(null);
		addVorname.clear();
		addNachname.clear();
		addTelefonnummer.clear();
		addMail.clear();
		addAdresse.clear();
	}

	// Holt sich aus allen Feldern unten die Daten die vom User eingetragen wurden, packt sie in den keyPrefix und dann sucht er die mit book.search -> EXC falls key leer ist.. 
	private void sucheKontaktHandler(ActionEvent event) {
		
		errorNachricht(null);
		String name = addVorname.getText();
		String lastname = addNachname.getText(); 
		String phone = addTelefonnummer.getText(); 
		String email = addMail.getText(); 
		String address = addAdresse.getText();
		
		//sucht nach diesen Variablen
		String keyPrefix = name+" "+lastname+" "+phone+" "+email+" "+address;

		ContactDetails[] matched = null;
		
		//versuche zurückgegeben
		//gefundene Sachen werden übergeben
		try {
			matched = book.search(keyPrefix);
		} catch (ParameterStringIsEmptyException | DetailsNotFoundException e) {
			errorNachricht(e.getMessage());	
		}
		
		//matched sind die gefundenen, neue Tabelle damit erstellen
		this.erstelleTabelle(matched);
	}

	
	// bekommt den key des Kontaktes bevor er verändert wurde, details sind die neuen veränderten Daten
	// Tabelle neu laden
	private void aendereKontaktHandler(ActionEvent event) {
		
		errorNachricht(null);
		
		ContactDetails details = this.generiereDetails();
		try {
			book.changeDetails(key, details);
			key = book.generateKey(details);
			this.leereFelder();
			this.erstelleTabelle(null);
		} catch (DuplicateKeyException | InvalidContactException| KeyIsNotInUseException | ParameterStringIsEmptyException e) {
			errorNachricht(e.getMessage());
		}
	}

	//key sind alle unsere Kontaktdaten, danach wieder auf null, reset.
	//Leere Felder und lade Tabelle neu
	//Syso Exceptions. 
	private void loescheKontaktHandler(ActionEvent event) {
		
		errorNachricht(null);
		
		try {
			book.removeDetails(key);
			key = null;
			this.leereFelder();
			this.erstelleTabelle(null);
		} catch (KeyIsNotInUseException | ParameterStringIsEmptyException e) {
			errorNachricht(e.getMessage());
		}
	}

	//Versuch neue Kontakt hinzuzufügen, muss in den try catch wegen EXC
	//wenn Kontakt kreiert wurde, dann in die Tabelle, leereFelder, dann Tabelle neu einlesen
	private void fuegeKontaktHinzuHandler(ActionEvent e){
		
		errorNachricht(null);
		
		ContactDetails newContact = this.generiereDetails();
		try {
			book.addDetails(newContact);
			this.leereFelder();
			this.erstelleTabelle(null);
		} catch (DuplicateKeyException | InvalidContactException| ParameterStringIsEmptyException e1) {
			errorNachricht(e1.getMessage());
		}
	}
	
	//Methode, wenn Tabelle Namen anklicken, dass unten in den Feldern angezeigt wird
	//Holt sich Daten von der Zeile in der Tabelle die man anklickt, gibt ganzen Kontakt zurück
	//Hole Felder und setze dort die Daten ein
	//Key Variable von oben: damit man weiß, damit es kein neuer Kontakt, sondern ein alter ist
	//Wenn jemand auf Knopf drückt weiß ich, dass dies mein key ist
	
	private void fuelleFelderHandler(MouseEvent e){
		
		errorNachricht(null);
		ContactDetails details = tabelle.getSelectionModel().getSelectedItem();
		
		addVorname.setText(details.getVorname());
		addNachname.setText(details.getNachname());
		addTelefonnummer.setText(details.getTelefonnummer());
		addMail.setText(details.getMail());
		addAdresse.setText(details.getAdresse());
		
		try {
			key = book.generateKey(details);
		} catch (ParameterStringIsEmptyException e1) {
			errorNachricht(e1.getMessage());
		}
	}
	
	//Wir erstellen 100 Kontakte, per Zufall werden die 100 Kontakte mit den Chars gefüllt
	//Dann füllen wir sie unserem Adressbuch hinzu
	public void fillAddressBook(){
		
		for(int i = 0; i < 5; i++){
			ContactDetails a = new ContactDetails(
					this.csRandomAlphaNumericString(5), 
					this.csRandomAlphaNumericString(6),
					this.csRandomAlphaNumericString(9),
					this.csRandomAlphaNumericString(12),
					this.csRandomAlphaNumericString(25)
					);	
			try {
				book.addDetails(a);
			} catch (DuplicateKeyException | InvalidContactException | ParameterStringIsEmptyException e) {
				errorNachricht(e.getMessage());
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