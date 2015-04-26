package classes;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;


public class AddressBookUi extends Application {
	
	private String oldKey = null;
	
	private Map<String, TextField> fields = new HashMap<>();
	
	private TextField nameField = new TextField(), lastnameField = new TextField(), phoneField = new TextField(), emailField = new TextField(), addressField = new TextField();
	
	private BorderPane root = new BorderPane(); 
	
	private ScrollPane contactScroll = new ScrollPane();
	
	private ListView<ContactDetails> listView = new ListView<ContactDetails>();
	
	private TextField searchfield;

	private static AddressBook book = new AddressBook();
	
	public AddressBookUi(){
		
		this.fillAddressBook();

		fields.put("Vorname",nameField);
		fields.put("Nachname",lastnameField);
		fields.put("Telefon",phoneField);
		fields.put("E-MAil",emailField);
		fields.put("Addresse",addressField);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		VBox navigation = new VBox(10);		
		
//		SearchField
		
		HBox searchbox = new HBox(10);
		
		searchfield = new TextField();
		
		Button search = new Button("Suchen");
		
		search.setOnMouseClicked((MouseEvent e) -> handleSearch(e));
		searchfield.setOnAction((ActionEvent e) -> handleKeySearch(e));
		
		searchbox.getChildren().addAll(searchfield,search);
		
		ScrollPane contactScroll = this.generateContactList(null);

//	    Neuer Eintrag
	    
		Button neu = new Button("Neuer Eintag");
		
		neu.setOnMouseClicked((MouseEvent e) -> handleNewContact(e));
		
//		Natvigation
		
		navigation.getChildren().addAll(searchbox,contactScroll,neu);
		
		navigation.setPadding(new Insets(10));
		
		root.setLeft(navigation);
		root.setCenter(new Text("Kein Kontakt ausgewählt."));
		
		Scene scene = new Scene(root, 900, 500);
		
		primaryStage.setTitle("AddressBook");
		primaryStage.setScene(scene);
        primaryStage.show();
	}
	
	private ScrollPane generateContactList(ContactDetails[] contactDetails) {
		//		ScrollContactList
		
		ObservableList<ContactDetails> data = FXCollections.observableArrayList();
		
		if(contactDetails == null){
			contactDetails = book.getAllContacts();
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
			ContactDetails[] query = book.search(keyPrefix);
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
		oldKey = new AddressBookKey(details.getVorname(),details.getNachname(),details.getTelefonnummer()).generateKey();
		this.generateForm(details);
	}
	
	private void handleNewContact(MouseEvent e) {
		oldKey = null;
		this.generateForm(null);
	}
	
	private void generateForm(ContactDetails details){
		
		VBox contactBox = new VBox();
		
		Text header = new Text();
		
		VBox contacts = new VBox();
		contacts.setPadding(new Insets(10));

		if(details != null){
			header.setText("Details zur Person");
			
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
			System.out.println(label);
			contacts.getChildren().add(this.rowBox(label,fields.get(label)));
		}

		HBox confirm = new HBox(10);
		confirm.setPadding(new Insets(10));
		confirm.setAlignment(Pos.BASELINE_CENTER);
		
		Button save = new Button("Speichern");
		Button cancel = new Button("Abrechen");
		
		save.setOnMouseClicked((MouseEvent e) -> saveContact(e));

		
		confirm.getChildren().addAll(save, cancel);
		
		header.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		
		contactBox.getChildren().addAll(header,contacts,confirm);
		
		contactBox.setPadding(new Insets(10));
		
		root.setCenter(contactBox);
	}
	
	private void saveContact(MouseEvent e){
		
		String name = nameField.getText();
		String lastname = lastnameField.getText();
		String phone = phoneField.getText();
		String email = emailField.getText();
		String address = addressField.getText();
		
		ContactDetails contact = new ContactDetails(name, lastname, phone, email, address);

		if(oldKey == null){
			try {
				book.addDetails(contact);
				this.generateContactList(null);
				oldKey = new AddressBookKey(contact.getVorname(),contact.getNachname(),contact.getTelefonnummer()).generateKey();
			} catch (Exception e2) {
				System.out.println("Fehler Aufgetreten");
			}
		}else{
			try {
				book.changeDetails(oldKey,contact);
				this.generateContactList(null);
				oldKey = new AddressBookKey(contact.getVorname(),contact.getNachname(),contact.getTelefonnummer()).generateKey();
			} catch (Exception e2) {
				System.out.println("Fehler Aufgetreten");
			}
		}
		
	}
	
	private HBox rowBox (String label, TextField rowField){
		
		HBox rowBox = new HBox(10);
		rowBox.setPadding(new Insets(10));
		
		Label rowLabel = new Label(label);
		rowLabel.setMinWidth(100);
		rowLabel.setPrefWidth(100);
		rowLabel.setMaxWidth(400);
		
		rowBox.getChildren().addAll(rowLabel,rowField);
		
		return rowBox;
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	private void fillAddressBook() {
		
		for(int i = 0; i < 25; i++){
			ContactDetails a = new ContactDetails(csRandomAlphaNumericString(5), csRandomAlphaNumericString(6),csRandomAlphaNumericString(9),csRandomAlphaNumericString(12),csRandomAlphaNumericString(25));
			book.addDetails(a);
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
