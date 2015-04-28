package classes;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Font;

public class AddressBookSurface extends Application {

	private TableView<Person> tabelle = new TableView<Person>();
	private final ObservableList<Person> daten = FXCollections
			.observableArrayList(new Person("Robert", "Dziuba", "0123456789",
					"robertodziuba@gmail.com", "Sonntagstraße 10"), new Person(
					"Inga", "Schwarze", "8756473829", "inga.schwarze@web.de",
					"Tegeler Weg 107"), new Person("Tobias", "Klatt",
					"01236757575", "tobiasklatt@web.de", "Hinter dem Mond 4"));
	final HBox hbox = new HBox();
	final HBox hbox2 = new HBox();

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		primaryStage.setTitle("Cooles Adressbuch");
		// Group group = new Group();
		Scene scene = new Scene(new Group());
		primaryStage.setWidth(600);
		primaryStage.setHeight(700);
		final Label titel = new Label("Unser Adressbuch");
		titel.setFont(new Font("Arial", 20));
		tabelle.setEditable(true);
		// titel.setAlignment(Pos.CENTER);
		TableColumn vorname = new TableColumn("Vorname");
		vorname.setMinWidth(100);
		vorname.setCellValueFactory(new PropertyValueFactory<Person, String>(
				"vorname"));
		TableColumn nachname = new TableColumn("Nachname");
		nachname.setMinWidth(100);
		nachname.setCellValueFactory(new PropertyValueFactory<Person, String>(
				"nachname"));
		TableColumn telefonnummer = new TableColumn("Telefonnummer");
		telefonnummer.setMinWidth(120);
		telefonnummer
				.setCellValueFactory(new PropertyValueFactory<Person, String>(
						"telefonnummer"));
		TableColumn email = new TableColumn("E-Mail");
		email.setMinWidth(100);
		email.setCellValueFactory(new PropertyValueFactory<Person, String>(
				"mail"));
		TableColumn adresse = new TableColumn("Adresse");
		adresse.setMinWidth(100);
		adresse.setCellValueFactory(new PropertyValueFactory<Person, String>(
				"adresse"));
		tabelle.setItems(daten);
		tabelle.getColumns().addAll(vorname, nachname, telefonnummer, email,
				adresse);
		final TextField addVorname = new TextField();
		addVorname.setPromptText("Vorname");
		addVorname.setMaxWidth(vorname.getPrefWidth());
		final TextField addNachname = new TextField();
		addNachname.setPromptText("Nachname");
		addNachname.setMaxWidth(nachname.getPrefWidth());
		final TextField addTelefonnummer = new TextField();
		addTelefonnummer.setPromptText("Telefonnummer");
		addTelefonnummer.setMaxWidth(telefonnummer.getPrefWidth());
		final TextField addMail = new TextField();
		addMail.setPromptText("E-Mail");
		addMail.setMaxWidth(email.getPrefWidth());
		final TextField addAdresse = new TextField();
		addAdresse.setPromptText("Adresse");
		addAdresse.setMaxWidth(adresse.getPrefWidth());
		final Button addButton = new Button("Hinzufügen");
		addButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				daten.add(new Person(addVorname.getText(), addNachname
						.getText(), addTelefonnummer.getText(), addMail
						.getText(), addAdresse.getText()));
				addVorname.clear();
				addNachname.clear();
				addTelefonnummer.clear();
				addMail.clear();
				addAdresse.clear();
			}
		});
		final Button removeButton = new Button("Entfernen");
		final Button changeButton = new Button("Ändern");
		final Button searchButton = new Button("Suchen");
		hbox.getChildren().addAll(addVorname, addNachname, addTelefonnummer,
				addMail, addAdresse);
		hbox.setSpacing(3);
		hbox.setAlignment(Pos.CENTER);
		hbox2.getChildren().addAll(addButton, removeButton, changeButton,
				searchButton);
		hbox2.setSpacing(3);
		hbox2.setAlignment(Pos.CENTER);
		final VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		vbox.getChildren().addAll(titel, tabelle, hbox, hbox2);
		((Group) scene.getRoot()).getChildren().addAll(vbox);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static class Person {
		private SimpleStringProperty vorname;
		private SimpleStringProperty nachname;
		private SimpleStringProperty telefonnummer;
		private SimpleStringProperty email;
		private SimpleStringProperty adresse;

		private Person(String vname, String nname, String nummer, String mail,
				String adress) {
			this.vorname = new SimpleStringProperty(vname);
			this.nachname = new SimpleStringProperty(nname);
			this.telefonnummer = new SimpleStringProperty(nummer);
			this.email = new SimpleStringProperty(mail);
			this.adresse = new SimpleStringProperty(adress);
		}

		public String getVorname() {
			return vorname.get();
		}

		public void setVorname(String vname) {
			vorname.set(vname);
		}

		public String getNachname() {
			return nachname.get();
		}

		public void setNachname(String nname) {
			nachname.set(nname);
		}

		public String getTelefonnummer() {
			return telefonnummer.get();
		}

		public void setTelefonnummer(String nummer) {
			telefonnummer.set(nummer);
		}

		public String getMail() {
			return email.get();
		}

		public void setMail(String mail) {
			email.set(mail);
		}

		public String getAdresse() {
			return adresse.get();
		}

		public void setAdresse(String adress) {
			adresse.set(adress);
		}
	}
}