package classes;

import interfaces.AddressBookInterface;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import exceptions.DetailsNotFoundException;
import exceptions.DuplicateKeyException;
import exceptions.InvalidContactException;
import exceptions.KeyIsNotInUseException;
import exceptions.ParameterStringIsEmptyException;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Duration;


public class AddressBookUi extends Application {
	
	private String key = null;
	
	private Map<String, TextField> fields = new HashMap<>();
	
	private TextField nameField = new TextField(), lastnameField = new TextField(), phoneField = new TextField(), emailField = new TextField(), addressField = new TextField();
	
	private BorderPane root = new BorderPane(); 
	
	private StackPane center = new StackPane();
	
	private Text error = new Text(); 
	
	private ScrollPane contactScroll = new ScrollPane();
	
	private ListView<ContactDetails> listView = new ListView<ContactDetails>();
	
	private TextField searchfield;

	private static AddressBookInterface book = new AddressBook();
	
	public AddressBookUi(){
		
		this.fillAddressBook();

		fields.put("Vorname",nameField);
		fields.put("Nachname",lastnameField);
		fields.put("Telefon",phoneField);
		fields.put("E-Mail",emailField);
		fields.put("Addresse",addressField);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		this.generateNavigation();
		
		this.generateContent(null,false);
		
		Scene scene = new Scene(root, 900, 500);
		
		primaryStage.setTitle("AddressBook");
		primaryStage.setScene(scene);
        primaryStage.show();
	}
	
	private void generateContent(Node data,boolean remove) {
		
		if(remove)
			center.getChildren().clear();
		
		center.getChildren().add(error);
		error.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
		error.setFill(Color.RED);
		
		if(data != null){
			center.getChildren().add(data);
		}else{
			center.getChildren().add(new Text("Kein Kontakt ausgewählt."));
		}
		
		root.setCenter(center);
	}

	private void generateNavigation(){
				
//		SearchField
		
		HBox searchbox = new HBox(10);
		
		searchfield = new TextField();
		
		Button search = new Button("Suchen");
		
		search.setOnMouseClicked((MouseEvent e) -> handleSearch(e));
		searchfield.setOnAction((ActionEvent e) -> handleKeySearch(e));
		
		searchbox.getChildren().addAll(searchfield,search);
		
//		ContactList
		
		ScrollPane contactScroll = this.generateContactList(null);

//	    Neuer Kontakt
	    
		Button neu = new Button("Neuer Eintag");
		
		neu.setOnMouseClicked((MouseEvent e) -> handleNewContact(e));
		
//		Natvigation
		
		VBox navigation = new VBox(10);	
		
		navigation.getChildren().addAll(searchbox,contactScroll,neu);
		
		navigation.setPadding(new Insets(10));
		
		root.setLeft(navigation);
		
	}
	
	private ScrollPane generateContactList(ContactDetails[] contactDetails) {
//		ScrollContactList
		
		ObservableList<ContactDetails> data = FXCollections.observableArrayList();
		
		if(contactDetails == null){

				try {
					contactDetails = book.getAllContacts();
				} catch (DetailsNotFoundException | ParameterStringIsEmptyException e1) {
					this.generateErrorModal(e1.getMessage());
				}
		}
	    
		for(ContactDetails contact : contactDetails){
			 data.add(contact);
		}
		
		listView.setItems(data);
		
		listView.setCellFactory((comboBox) -> {
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

		
		listView.setOnMouseClicked((MouseEvent e) -> handleContact(e));
		
	    contactScroll.setContent(listView);
		
		return contactScroll;
	}
	
	private void handleKeySearch(ActionEvent e){
		this.contactSearch();
	}
	
	private void handleSearch(MouseEvent e){
		this.contactSearch();
	}
	
	private void contactSearch(){
		String keyPrefix = searchfield.getText();
		
		if(keyPrefix.length() > 0){
			ContactDetails[] query = null;
			try {
				query = book.search(keyPrefix);
			} catch (ParameterStringIsEmptyException | DetailsNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(query.length > 0){
				this.generateContactList(query);
			}else{
				ListView<Text> list = new ListView<Text>();
				ObservableList<Text> data = FXCollections.observableArrayList();
				data.add(new Text("Leider nichts gefunden"));
				list.setItems(data);
				contactScroll.setContent(list);
			}
		}else{
			this.generateContactList(null);
		}
	}

	private void handleContact(MouseEvent e) {
		ContactDetails details = listView.getSelectionModel().getSelectedItem();
		try {
			key = book.generateKey(details);
		} catch (ParameterStringIsEmptyException e1) {
			this.generateErrorModal(e1.getMessage());
		}
		this.generateForm(details);
	}
	
	private void handleNewContact(MouseEvent e) {
		key = null;
		this.generateForm(null);
	}
	
	private void generateForm(ContactDetails details){
		
		VBox contactBox = new VBox();
		contactBox.setPadding(new Insets(45,10,10,20));
	

		Text header = new Text();
		header.setFont(Font.font("Verdana", FontWeight.BOLD, 25));

		
		VBox contacts = new VBox();
		contacts.setPadding(new Insets(90,10,10,0));

		if(details != null){
			header.setText("Details zu " + details.getVorname() + " " + details.getNachname());
			
			nameField.setText(details.getVorname());
			lastnameField.setText(details.getNachname());
			phoneField.setText(details.getTelefonnummer());
			emailField.setText(details.getMail());
			addressField.setText(details.getAdresse());

		}else{
			header.setText("Neue Person");
			
			nameField.setText(null);
			lastnameField.setText(null);
			phoneField.setText(null);
			emailField.setText(null);
			addressField.setText(null);
		}

		for(String label : fields.keySet()){
			contacts.getChildren().add(this.createRowBox(label,fields.get(label)));
		}

		HBox confirm = new HBox(10);
		confirm.setPadding(new Insets(10));
		confirm.setAlignment(Pos.BASELINE_CENTER);
		
		Button save = new Button("Speichern");
		save.setOnMouseClicked((MouseEvent e) -> saveContact(e));
	
		Button delete = new Button("Löschen");
		delete.setOnMouseClicked((MouseEvent e) -> deleteContact(e));
		
		confirm.getChildren().addAll(save, delete);
		

		contactBox.getChildren().addAll(header,contacts,confirm);
		
		FadeTransition ft = new FadeTransition(Duration.millis(500), contactBox);
		ft.setFromValue(0.0);
		ft.setToValue(1.0);
		ft.play();
		
		this.generateContent(contactBox,true);
	}

	private Object deleteContact(MouseEvent e) {
		if(key == null){
			nameField.setText(null);
			lastnameField.setText(null);
			phoneField.setText(null);
			emailField.setText(null);
			addressField.setText(null);
		}else{
			try {
				book.removeDetails(key);
			} catch (KeyIsNotInUseException | ParameterStringIsEmptyException e1) {
				// TODO Auto-generated catch block
				this.generateErrorModal(e1.getMessage());
			}
			key = null;
			this.generateContactList(null);
			this.generateContent(null, true);
		}
		return null;
	}

	private HBox createRowBox(String label, TextField rowField){
		
		HBox rowBox = new HBox();
		rowBox.setPadding(new Insets(10,10,10,0));
		
		Label rowLabel = new Label(label);
		rowLabel.setMinWidth(100);
		
		rowField.setMinWidth(400);
		rowBox.getChildren().addAll(rowLabel,rowField);
		
		return rowBox;
	}
	
	private void saveContact(MouseEvent e){
		
		String name = nameField.getText();
		String lastname = lastnameField.getText();
		String phone = phoneField.getText();
		String email = emailField.getText();
		String address = addressField.getText();
		
		ContactDetails contact = new ContactDetails(name, lastname, phone, email, address);

		if(key == null){

			try {
				book.addDetails(contact);
				key = book.generateKey(contact);
				this.generateContactList(null);
				this.generateForm(contact);
			} catch (DuplicateKeyException | InvalidContactException | ParameterStringIsEmptyException e1) {
				// TODO Auto-generated catch block
				this.generateErrorModal(e1.getMessage());
			}
		}else{

			try {
				book.changeDetails(key,contact);
				key = book.generateKey(contact);
				this.generateContactList(null);
				this.generateForm(contact);
			} catch (DuplicateKeyException | InvalidContactException | KeyIsNotInUseException | ParameterStringIsEmptyException e1) {
				// TODO Auto-generated catch block
				this.generateErrorModal(e1.getMessage());
			}
		}
	}
	
	private void generateErrorModal(String message) {
		error.setText(message);
	}
	
	private void fillAddressBook() {
		
		for(int i = 0; i < 100; i++){
			ContactDetails a = new ContactDetails(
					csRandomAlphaNumericString(5), 
					csRandomAlphaNumericString(6),
					csRandomAlphaNumericString(9),
					csRandomAlphaNumericString(12),
					csRandomAlphaNumericString(25)
					);	
			try {
				book.addDetails(a);
			} catch (DuplicateKeyException | InvalidContactException
					| ParameterStringIsEmptyException e) {
				this.generateErrorModal(e.getMessage());
			}
		}
	}

	private static char[] VALID_CHARACTERS =
		    "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456879".toCharArray();

	// cs = cryptographically secure
	public static String csRandomAlphaNumericString(int numChars) {
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
