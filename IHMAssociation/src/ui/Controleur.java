package ui;

import association.Evenement;
import association.GestionAssociation;
import association.InformationPersonnelle;
import association.Membre;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import ui.helper.DatePickerTableCell;
import ui.helper.SpinnerTableCell;

/**
 * Description d'un controleur d'une interface qui gère une
 * {@link association.GestionAssociation}.
 *
 * @author Nicolas Le Bars
 * @author Gwendolyn Mandin
 * @author Josselin Scouarnec
 */
public class Controleur {
  
  @FXML
  private MenuItem miAssociationSave;
  @FXML
  private Label lbMembersList;
  @FXML
  private TableView<Membre> tvMembersList;
  @FXML
  private TableColumn<Membre, String> tcMembersListLastName;
  @FXML
  private TableColumn<Membre, String> tcMembersListFirstName;
  @FXML
  private TableColumn<Membre, String> tcMembersListAddress;
  @FXML
  private TableColumn<Membre, Integer> tcMembersListAge;
  @FXML
  private MenuItem miMemberDelete;
  @FXML
  private MenuItem miMemberDefinePresident;
  @FXML
  private MenuItem miMemberShowEvents;
  @FXML
  private MenuItem miMemberShowFutureEvents;
  @FXML
  private MenuItem miMemberEventRegister;
  @FXML
  private MenuItem miMemberEventUnregister;
  @FXML
  private TextField tfMemberSearch;
  @FXML
  private Button btnMemberDelete;
  @FXML
  private Label lbEventList;
  @FXML
  private TableView<Evenement> tvEventList;
  @FXML
  private TableColumn<Evenement, String> tcEventListName;
  @FXML
  private TableColumn<Evenement, String> tcEventListLocation;
  @FXML
  private TableColumn<Evenement, LocalDate> tcEventListDate;
  @FXML
  private TableColumn<Evenement, Integer> tcEventListHour;
  @FXML
  private TableColumn<Evenement, Integer> tcEventListDuration;
  @FXML
  private TableColumn<Evenement, Integer> tcEventListMaxParticipants;
  @FXML
  private MenuItem miEventDelete;
  @FXML
  private MenuItem miEventShowMembers;
  @FXML
  private MenuItem miEventMemberRegister;
  @FXML
  private MenuItem miEventMemberUnregister;
  @FXML
  private TextField tfEventSearch;
  @FXML
  private Button btnEventDelete;
  @FXML
  private Label lbMessage;
  
  private static final Membre DEFAULT_MEMBRE = ((Supplier<Membre>) () -> {
    Membre membre = new Membre();
    InformationPersonnelle infos = new InformationPersonnelle("", "");
    
    membre.definirInformationPersonnnelle(infos);
    return membre;
  }).get();
  
  private static final Evenement DEFAULT_EVENEMENT =
      new Evenement("", "", LocalDate.now().atStartOfDay(), 0, -1, null);
  
  private GestionAssociation association;
  private FileChooser fileChooser;
  
  /**
   * Initialisation de l'IHM.
   */
  @FXML
  public void initialize() {
    System.out.println("Initialisation de l'interface");
    
    this.association = new GestionAssociation();
    this.fileChooser = new FileChooser();
    this.fileChooser.getExtensionFilters()
        .add(new ExtensionFilter("Serialized Association", "*.aser"));
    
    initMembersList();
    initEventList();
    
    System.out.println("Interface initialisée");
  }
  
  /**
   * Initialise les propriétés de la liste des membres.
   */
  protected void initMembersList() {
    System.out.println("Initialisation du système de gestion des membres");
    this.tvMembersList.getSelectionModel().setCellSelectionEnabled(true);
    
    this.tvMembersList.setRowFactory(tv -> new TableRow<Membre>() {
      @Override
      protected void updateItem(Membre membre, boolean empty) {
        super.updateItem(membre, empty);
        
        Membre president =
            (Membre) association.gestionnaireMembre().president();
        
        if (membre == null) {
          setStyle("");
        } else if (president != null && membre.equals(president)) {
          setStyle("-fx-background-color: khaki;");
        } else {
          setStyle("");
        }
      }
    });
    
    this.tcMembersListLastName
        .setCellValueFactory(cell -> new SimpleStringProperty(
            cell.getValue().getInformationPersonnelle().getNom()));
    this.tcMembersListFirstName
        .setCellValueFactory(cell -> new SimpleStringProperty(
            cell.getValue().getInformationPersonnelle().getPrenom()));
    this.tcMembersListAddress
        .setCellValueFactory(cell -> new SimpleStringProperty(
            cell.getValue().getInformationPersonnelle().getAdresse()));
    this.tcMembersListAge
        .setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(
            cell.getValue().getInformationPersonnelle().getAge()));
    
    this.tcMembersListLastName
        .setCellFactory(TextFieldTableCell.forTableColumn());
    this.tcMembersListFirstName
        .setCellFactory(TextFieldTableCell.forTableColumn());
    this.tcMembersListAddress
        .setCellFactory(TextFieldTableCell.forTableColumn());
    // TODO ajouter un TextFormatter pour n'autoriser que des chiffres
    this.tcMembersListAge
        .setCellFactory(SpinnerTableCell.forTableColumn(0, 200, 0, 1));
    
    this.tcMembersListLastName.setOnEditCommit(cell -> {
      // éditable si et seulement si les données sont invalides
      if (cell.getNewValue() != null) {
        String value = cell.getNewValue().trim();
        
        if (!value.isEmpty() && !value
            .equals(DEFAULT_MEMBRE.getInformationPersonnelle().getNom())) {
          Membre membre = this.tvMembersList.getItems()
              .get(cell.getTablePosition().getRow());
          InformationPersonnelle membreInfo =
              membre.getInformationPersonnelle();
          
          membre.definirInformationPersonnnelle(
              new InformationPersonnelle(value, membreInfo.getPrenom(),
                  membreInfo.getAdresse(), membreInfo.getAge()));
          
          if (!membreInfo.getPrenom().isEmpty()) {
            if (this.association.gestionnaireMembre().ajouterMembre(membre)) {
              // contourne le problème de onClickMembersList qui n'est pas
              // déclenché lorsqu'une cellule est éditée
              this.tcMembersListLastName.setEditable(false);
              this.miAssociationSave.setDisable(false);
              this.lbMessage.setText(
                  "Le membre a été ajouté ! Vous ne pouvez plus changer son Nom ou son Prénom.");
            } else {
              membre.definirInformationPersonnnelle(
                  new InformationPersonnelle("", membreInfo.getPrenom(),
                      membreInfo.getAdresse(), membreInfo.getAge()));
            }
          }
        }
        
        this.tvMembersList.refresh();
      }
    });
    
    this.tcMembersListFirstName.setOnEditCommit(cell -> {
      // éditable si et seulement si les données sont invalides
      if (cell.getNewValue() != null) {
        String value = cell.getNewValue().trim();
        
        if (!value.isEmpty() && !value
            .equals(DEFAULT_MEMBRE.getInformationPersonnelle().getPrenom())) {
          Membre membre = this.tvMembersList.getItems()
              .get(cell.getTablePosition().getRow());
          InformationPersonnelle membreInfo =
              membre.getInformationPersonnelle();
          
          membre.definirInformationPersonnnelle(
              new InformationPersonnelle(membreInfo.getNom(), value,
                  membreInfo.getAdresse(), membreInfo.getAge()));
          
          if (!membreInfo.getNom().isEmpty()) {
            if (this.association.gestionnaireMembre().ajouterMembre(membre)) {
              // contourne le problème de onClickMembersList qui n'est pas
              // déclenché lorsqu'une cellule est éditée
              this.tcMembersListFirstName.setEditable(false);
              this.miAssociationSave.setDisable(false);
              this.lbMessage.setText(
                  "Le membre a été ajouté ! Vous ne pouvez plus changer son Nom ou son Prénom.");
            } else {
              membre.definirInformationPersonnnelle(
                  new InformationPersonnelle(membreInfo.getNom(), "",
                      membreInfo.getAdresse(), membreInfo.getAge()));
            }
          }
        }
        
        this.tvMembersList.refresh();
      }
    });
    
    this.tcMembersListAddress.setOnEditCommit(cell -> {
      if (cell.getNewValue() != null) {
        String value = cell.getNewValue().trim();
        
        if (!value.isEmpty() && !value.equals(cell.getOldValue())) {
          this.tvMembersList.getItems().get(cell.getTablePosition().getRow())
              .getInformationPersonnelle().setAdresse(value);
          this.miAssociationSave.setDisable(false);
        }
        
        this.tvMembersList.refresh();
      }
    });
    
    this.tcMembersListAge.setOnEditCommit(cell -> {
      if (cell.getNewValue() != null) {
        int value = cell.getNewValue();
        
        if (value != cell.getOldValue()) {
          this.tvMembersList.getItems().get(cell.getTablePosition().getRow())
              .getInformationPersonnelle().setAge(value);
          this.tvMembersList.refresh();
          this.miAssociationSave.setDisable(false);
        }
      }
    });
  }
  
  /**
   * Initialise les propriétés de la liste des événements.
   */
  private void initEventList() {
    System.out.println("Initialisation du système de gestion des événements");
    this.tvEventList.getSelectionModel().setCellSelectionEnabled(true);
    
    this.tcEventListName.setCellValueFactory(
        cell -> new SimpleStringProperty(cell.getValue().getNom()));
    this.tcEventListLocation.setCellValueFactory(
        cell -> new SimpleStringProperty(cell.getValue().getLieu()));
    this.tcEventListDate.setCellValueFactory(cell -> new SimpleObjectProperty<>(
        cell.getValue().getDate().toLocalDate()));
    this.tcEventListHour
        .setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(
            cell.getValue().getDate().getHour()));
    this.tcEventListDuration.setCellValueFactory(
        cell -> new ReadOnlyObjectWrapper<>(cell.getValue().getDuree()));
    this.tcEventListMaxParticipants
        .setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(
            cell.getValue().getNbParticipantsMax()));
    
    this.tcEventListName.setCellFactory(TextFieldTableCell.forTableColumn());
    this.tcEventListLocation
        .setCellFactory(TextFieldTableCell.forTableColumn());
    // TODO ajouter un TextFormatter pour n'autoriser qu'un format de date
    this.tcEventListDate.setCellFactory(DatePickerTableCell.forTableColumn());
    // TODO ajouter un TextFormatter pour n'autoriser que des chiffres
    this.tcEventListHour
        .setCellFactory(SpinnerTableCell.forTableColumn(0, 23, 0, 1));
    // TODO ajouter un TextFormatter pour n'autoriser que des chiffres
    this.tcEventListDuration.setCellFactory(
        SpinnerTableCell.forTableColumn(1, Integer.MAX_VALUE, 1, 1));
    // TODO ajouter un TextFormatter pour n'autoriser que des chiffres
    this.tcEventListMaxParticipants.setCellFactory(
        SpinnerTableCell.forTableColumn(0, Integer.MAX_VALUE, 0, 1));
    
    BiConsumer<Evenement, Integer> tryCreateEvent = (evt, row) -> {
      Evenement evenement = this.association.gestionnaireEvenements()
          .creerEvenement(evt.getNom(), evt.getLieu(),
              evt.getDate().getDayOfMonth(), evt.getDate().getMonth(),
              evt.getDate().getYear(), evt.getDate().getHour(), 0,
              evt.getDuree(), evt.getNbParticipantsMax());
      
      if (evenement != null) {
        this.tvEventList.getItems().set(row, evenement);
        this.miAssociationSave.setDisable(false);
        this.lbMessage.setText("L'événement a été ajouté !");
      }
    };
    
    this.tcEventListName.setOnEditCommit(cell -> {
      if (cell.getNewValue() != null) {
        String value = cell.getNewValue().trim();
        
        if (!value.isEmpty() && !value.equals(cell.getOldValue())) {
          Evenement evenement =
              this.tvEventList.getItems().get(cell.getTablePosition().getRow());
          
          evenement.setNom(value);
          
          if (!cell.getOldValue().equals(DEFAULT_EVENEMENT.getNom())) {
            tryCreateEvent.accept(evenement, cell.getTablePosition().getRow());
          }
        }
        
        this.tvEventList.refresh();
      }
    });
    
    this.tcEventListLocation.setOnEditCommit(cell -> {
      if (cell.getNewValue() != null) {
        String value = cell.getNewValue().trim();
        
        if (!value.isEmpty() && !value.equals(cell.getOldValue())) {
          Evenement evenement =
              this.tvEventList.getItems().get(cell.getTablePosition().getRow());
          
          evenement.setLieu(value);
          
          if (!cell.getOldValue().equals(DEFAULT_EVENEMENT.getLieu())) {
            tryCreateEvent.accept(evenement, cell.getTablePosition().getRow());
          }
        }
        
        this.tvEventList.refresh();
      }
    });
    
    this.tcEventListDate.setOnEditCommit(cell -> {
      if (cell.getNewValue() != null) {
        LocalDate value = cell.getNewValue();
        
        if (value != cell.getOldValue()) {
          Evenement evenement =
              this.tvEventList.getItems().get(cell.getTablePosition().getRow());
          
          evenement
              .setDate(value.atTime(cell.getRowValue().getDate().getHour(), 0));
          
          if (cell.getOldValue() != DEFAULT_EVENEMENT.getDate().toLocalDate()) {
            tryCreateEvent.accept(evenement, cell.getTablePosition().getRow());
          }
        }
        
        this.tvEventList.refresh();
      }
    });
    
    this.tcEventListHour.setOnEditCommit(cell -> {
      if (cell.getNewValue() != null) {
        int value = cell.getNewValue();
        
        if (value != cell.getOldValue()) {
          Evenement evenement =
              this.tvEventList.getItems().get(cell.getTablePosition().getRow());
          
          evenement.setDate(cell.getRowValue().getDate().withHour(value));
          
          if (cell.getOldValue() != DEFAULT_EVENEMENT.getDate().getHour()) {
            tryCreateEvent.accept(evenement, cell.getTablePosition().getRow());
          }
        }
        
        this.tvEventList.refresh();
      }
    });
    
    this.tcEventListDuration.setOnEditCommit(cell -> {
      if (cell.getNewValue() != null) {
        int value = cell.getNewValue();
        
        if (value != cell.getOldValue()) {
          Evenement evenement =
              this.tvEventList.getItems().get(cell.getTablePosition().getRow());
          
          evenement.setDuree(value);
          
          if (cell.getOldValue() != DEFAULT_EVENEMENT.getDuree()) {
            tryCreateEvent.accept(evenement, cell.getTablePosition().getRow());
          }
        }
        
        this.tvEventList.refresh();
      }
    });
    
    this.tcEventListMaxParticipants.setOnEditCommit(cell -> {
      if (cell.getNewValue() != null) {
        int value = cell.getNewValue();
        
        if (value != cell.getOldValue()) {
          Evenement evenement =
              this.tvEventList.getItems().get(cell.getTablePosition().getRow());
          
          evenement.setNbParticipantsMax(value);
          
          if (cell.getOldValue() != DEFAULT_EVENEMENT.getNbParticipantsMax()) {
            tryCreateEvent.accept(evenement, cell.getTablePosition().getRow());
          }
        }
        
        this.tvEventList.refresh();
      }
    });
  }
  
  /**
   * Vide les informations de l'association.
   *
   * @param event (on click)
   */
  @FXML
  public void onActionAssociationNew(ActionEvent event) {
    this.tvMembersList.getSelectionModel().select(-1);
    this.tvEventList.getSelectionModel().select(-1);
    this.tvMembersList.getItems().clear();
    this.tvEventList.getItems().clear();
    this.tvMembersList.refresh();
    this.tvEventList.refresh();
    this.association.gestionnaireEvenements().ensembleEvenements().stream()
        .toList().forEach(evt -> this.association.gestionnaireEvenements()
            .supprimerEvenement(evt));
    this.association.gestionnaireMembre().ensembleMembres().stream().toList()
        .forEach(
            mem -> this.association.gestionnaireMembre().supprimerMembre(mem));
    this.miAssociationSave.setDisable(true);
    this.lbMessage
        .setText("Les informations de l'association ont été supprimées");
  }
  
  /**
   * Charge les informations d'une association à partir d'un fichier.
   *
   * @param event (on click)
   */
  @FXML
  public void onActionAssociationLoad(ActionEvent event) {
    Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup()
        .getOwnerWindow();
    File chosenFile = this.fileChooser.showOpenDialog(stage);
    
    try {
      if (chosenFile != null) {
        this.association.chargerDonnees(chosenFile.getAbsolutePath());
        this.tvMembersList.edit(-1, null);
        this.tvMembersList.setItems(FXCollections.observableList(
            this.association.gestionnaireMembre().ensembleMembres().stream()
                .map(mem -> (Membre) mem).collect(Collectors.toList())));
        this.tvEventList.edit(-1, null);
        this.tvEventList.setItems(FXCollections
            .observableList(this.association.gestionnaireEvenements()
                .ensembleEvenements().stream().collect(Collectors.toList())));
        this.lbMembersList
            .setText("Les informations de l'association ont été chargées");
      } else {
        this.lbMembersList.setText("Aucun fichier de sélectionné...");
      }
    } catch (IOException e) {
      this.lbMembersList.setText("Une erreur est survenue !");
      e.printStackTrace();
    }
  }
  
  /**
   * Sauvegarde les informations de l'association dans un fichier.
   *
   * @param event (on click)
   */
  @FXML
  public void onActionAssociationSave(ActionEvent event) {
    File chosenFile =
        this.fileChooser.showSaveDialog((Stage) ((MenuItem) event.getSource())
            .getParentPopup().getOwnerWindow());
    
    try {
      if (chosenFile != null) {
        this.association.sauvegarderDonnees(chosenFile.getAbsolutePath());
        this.miAssociationSave.setDisable(true);
        this.lbMessage
            .setText("Les informations de l'association ont été sauvegardées");
      } else {
        this.lbMessage.setText("Aucun fichier de sélectionné...");
      }
    } catch (IOException e) {
      this.lbMembersList.setText("Une erreur est survenue !");
      e.printStackTrace();
    }
  }
  
  /**
   * Charge les informations d'une association à partir d'un fichier.
   *
   * @param event (on click)
   */
  @FXML
  public void onActionExit(ActionEvent event) {
    Platform.exit();
    System.exit(0);
  }
  
  /**
   * Affiche les informations à propos de l'application.
   *
   * @param event (on click)
   */
  @FXML
  public void onActionAbout(ActionEvent event) {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("À propos");
    alert.setHeaderText(null);
    alert.setContentText("Application de gestion d'association.");
    alert.showAndWait();
  }
  
  /**
   * Gère la visibilité et l'activation de certains boutons ou menus pour la
   * liste des membres.
   *
   * @param event (on click)
   */
  @FXML
  public void onClickMembersList(MouseEvent event) {
    Membre membre = this.tvMembersList.getSelectionModel().getSelectedItem();
    boolean memberIsNull = membre == null;
    boolean memberIsValid = !(memberIsNull
        || membre.getInformationPersonnelle().getNom()
            .equals(DEFAULT_MEMBRE.getInformationPersonnelle().getNom())
        || membre.getInformationPersonnelle().getPrenom()
            .equals(DEFAULT_MEMBRE.getInformationPersonnelle().getPrenom()));
    
    this.miMemberDelete.setDisable(memberIsNull);
    this.miMemberDefinePresident.setDisable(!memberIsValid);
    this.miMemberShowEvents.setDisable(
        !(memberIsValid && membre.ensembleEvenements().size() != 0));
    this.miMemberShowFutureEvents.setDisable(
        !(memberIsValid && membre.ensembleEvenementsAvenir().size() != 0));
    
    Evenement evenement =
        this.tvEventList.getSelectionModel().getSelectedItem();
    boolean eventIsValid = !(evenement == null
        || evenement.getNom().equals(DEFAULT_EVENEMENT.getNom())
        || evenement.getLieu().equals(DEFAULT_EVENEMENT.getLieu())
        || evenement.getDuree() == DEFAULT_EVENEMENT.getDuree()
        || evenement.getNbParticipantsMax() == DEFAULT_EVENEMENT
            .getNbParticipantsMax());
    boolean bothAreValid = memberIsValid && eventIsValid;
    boolean memberIsRegistered =
        bothAreValid && evenement.getParticipants().contains(membre);
    
    this.miMemberEventRegister
        .setDisable(!(bothAreValid && !memberIsRegistered));
    this.miMemberEventRegister
        .setVisible(!(bothAreValid && memberIsRegistered));
    this.miMemberEventUnregister
        .setVisible(!this.miMemberEventRegister.isVisible());
    this.btnMemberDelete.setDisable(memberIsNull);
    
    this.tcMembersListLastName.setEditable(!memberIsValid);
    this.tcMembersListFirstName.setEditable(!memberIsValid);
  }
  
  /**
   * Ajoute un nouveau membre à la liste. Si les informations du membre sont
   * valides, il est ajouté à l'association.
   *
   * @param event (on click)
   */
  @FXML
  public void onClickMemberNew(Event event) {
    Membre membre = DEFAULT_MEMBRE.clone();
    int row = this.tvMembersList.getItems().size();
    
    this.tvMembersList.getItems().add(membre);
    this.tvMembersList.getSelectionModel().select(row);
    this.tvMembersList.layout();
    this.tcMembersListLastName.setEditable(true);
    this.tvMembersList.edit(row, this.tcMembersListLastName);
    this.lbMessage.setText(
        "Un nouveau membre est éditable, mais il ne sera pas ajouté à l'association tant qu'il "
        + "n'est pas valide");
    
    // FIXME
    this.btnMemberDelete.setDisable(false);
  }
  
  /**
   * Retire dans la liste, le membre séléctionné. Retire dans l'association, le
   * membre séléctionné si il est présent.
   *
   * @param event (on click)
   */
  @FXML
  public void onClickMemberDelete(Event event) {
    int row = this.tvMembersList.getSelectionModel().getSelectedIndex();
    Membre membre = this.tvMembersList.getItems().get(row);
    
    this.association.gestionnaireEvenements().ensembleEvenements().stream()
        .forEach(evt -> this.association.gestionnaireEvenements()
            .annulerEvenement(evt, membre));
    this.tvMembersList.getItems().remove(row);
    this.miAssociationSave.setDisable(false);
    this.lbMessage.setText("Le membre a été retiré de l'association");
    
    // FIXME
    if (this.tvMembersList.getItems().size() == 0) {
      this.btnMemberDelete.setDisable(true);
    }
  }
  
  /**
   * Définis le nouveau président de l'association.
   *
   * @param event (on click)
   */
  @FXML
  public void actionMemberDefinePresident(ActionEvent event) {
    Membre membre = this.tvMembersList.getSelectionModel().getSelectedItem();
    this.association.gestionnaireMembre().designerPresident(membre);
    this.tvMembersList.refresh();
    this.lbMessage.setText("Un nouveau président à été défini");
  }
  
  /**
   * Affiche les événements du membre séléctionné.
   *
   * @param event (on click)
   */
  @FXML
  public void actionMemberShowEvents(ActionEvent event) {
    Membre membre = this.tvMembersList.getSelectionModel().getSelectedItem();
    
    this.lbEventList.setText(
        "les événements de " + membre.getInformationPersonnelle().getNom() + " "
            + membre.getInformationPersonnelle().getPrenom());
    this.tvEventList.setItems(FXCollections.observableList(
        membre.ensembleEvenements().stream().collect(Collectors.toList())));
    this.lbMessage.setText("Les événements du membre ont été affichés");
  }
  
  /**
   * Affiche les événéments futurs du membre séléctionné.
   *
   * @param event (on click)
   */
  @FXML
  public void actionMemberShowFutureEvents(ActionEvent event) {
    Membre membre = this.tvMembersList.getSelectionModel().getSelectedItem();
    
    this.lbEventList.setText("les événements futurs de "
        + membre.getInformationPersonnelle().getNom() + " "
        + membre.getInformationPersonnelle().getPrenom());
    this.tvEventList.setItems(FXCollections
        .observableList(membre.ensembleEvenementsAvenir().stream().toList()));
    this.lbMessage.setText("Les événements du membre ont été affichés");
  }
  
  /**
   * Affiche les membres de l'association.
   *
   * @param event (on click)
   */
  @FXML
  public void actionMembersListReset(ActionEvent event) {
    this.lbMembersList.setText("les membres de l'asscociation");
    this.tvMembersList.setItems(FXCollections
        .observableList(this.association.gestionnaireMembre().ensembleMembres()
            .stream().map(mem -> (Membre) mem).collect(Collectors.toList())));
    this.lbMessage.setText("La liste des membres a été réinitialisée");
  }
  
  /**
   * Filtre la liste des membres.
   *
   * @param event (on click)
   */
  @FXML
  public void onKeyReleasedMemberSearch(KeyEvent event) {
    // TODO
  }
  
  /**
   * Gère la visibilité et l'activation de certains boutons ou menus pour la
   * liste des événements.
   *
   * @param event (on click)
   */
  @FXML
  public void onClickEventList(MouseEvent event) {
    Membre membre = this.tvMembersList.getSelectionModel().getSelectedItem();
    Evenement evenement =
        this.tvEventList.getSelectionModel().getSelectedItem();
    boolean memberIsValid = !(membre == null
        || membre.getInformationPersonnelle().getNom()
            .equals(DEFAULT_MEMBRE.getInformationPersonnelle().getNom())
        || membre.getInformationPersonnelle().getPrenom()
            .equals(DEFAULT_MEMBRE.getInformationPersonnelle().getPrenom()));
    boolean eventIsNull = evenement == null;
    boolean eventIsValid =
        !(eventIsNull || evenement.getNom().equals(DEFAULT_EVENEMENT.getNom())
            || evenement.getLieu().equals(DEFAULT_EVENEMENT.getLieu())
            || evenement.getDuree() == DEFAULT_EVENEMENT.getDuree()
            || evenement.getNbParticipantsMax() == DEFAULT_EVENEMENT
                .getNbParticipantsMax());
    
    this.miEventDelete.setDisable(eventIsNull);
    this.miEventShowMembers
        .setDisable(!(eventIsValid && evenement.getParticipants().size() != 0));
    
    boolean bothAreValid = memberIsValid && eventIsValid;
    boolean memberIsRegistered =
        bothAreValid && evenement.getParticipants().contains(membre);
    
    this.miEventMemberRegister
        .setDisable(!(bothAreValid && !memberIsRegistered));
    this.miEventMemberRegister
        .setVisible(!(bothAreValid && memberIsRegistered));
    this.miEventMemberUnregister
        .setVisible(!this.miEventMemberRegister.isVisible());
    this.btnEventDelete.setDisable(eventIsNull);
  }
  
  /**
   * Ajoute un nouveau événement à la liste. Si les informations de l'événement
   * sont valides, il est ajouté à l'association.
   *
   * @param event (on click)
   */
  @FXML
  public void onClickEventNew(Event event) {
    Evenement evenement = DEFAULT_EVENEMENT.clone();
    int row = this.tvEventList.getItems().size();
    
    this.tvEventList.getItems().add(evenement);
    this.tvEventList.getSelectionModel().select(row);
    this.tvEventList.layout();
    this.tvEventList.edit(row, this.tcEventListName);
    this.lbMessage.setText(
        "Un nouvel événement est éditable, mais il ne sera pas ajouté à l'association tant qu'il "
        + "n'est pas valide");
    
    // FIXME
    this.btnEventDelete.setDisable(false);
  }
  
  /**
   * Retire dans la liste, l'événement séléctionné. Retire dans l'association,
   * l'événement séléctionné si il est présent.
   *
   * @param event (on click)
   */
  @FXML
  public void onClickEventDelete(Event event) {
    int row = this.tvEventList.getSelectionModel().getSelectedIndex();
    Evenement evenement = this.tvEventList.getItems().get(row);
    
    this.association.gestionnaireEvenements().supprimerEvenement(evenement);
    this.tvEventList.getItems().remove(row);
    this.miAssociationSave.setDisable(false);
    this.lbMessage.setText("L'événement a été retiré de l'association");
    
    // FIXME
    if (this.tvEventList.getItems().size() == 0) {
      this.btnEventDelete.setDisable(true);
    }
  }
  
  /**
   * Affiche les membres qui participent à l'événement.
   *
   * @param event (on click)
   */
  @FXML
  public void actionEventShowMembers(ActionEvent event) {
    Evenement evenement =
        this.tvEventList.getSelectionModel().getSelectedItem();
    
    this.lbMembersList
        .setText("les participants de l'événement " + evenement.getNom());
    this.tvMembersList
        .setItems(FXCollections.observableList(evenement.getParticipants()
            .stream().map(mem -> (Membre) mem).collect(Collectors.toList())));
    this.lbMessage.setText("Les participants à l'événement ont été affichés");
  }
  
  /**
   * Affiche les événements de l'association.
   *
   * @param event (on click)
   */
  @FXML
  public void actionEventListReset(ActionEvent event) {
    this.lbEventList.setText("les événements de l'association");
    this.tvEventList.setItems(
        FXCollections.observableList(this.association.gestionnaireEvenements()
            .ensembleEvenements().stream().collect(Collectors.toList())));
    this.lbMessage.setText("La liste des événements a été réinitialisée");
  }
  
  /**
   * Filtre la liste des événements.
   *
   * @param event (on click)
   */
  @FXML
  public void onKeyReleasedEventSearch(KeyEvent event) {
    
  }
  
  /**
   * Inscrit un membre séléctionné à un événement séléctionné.
   *
   * @param event (on click)
   */
  @FXML
  public void actionEventMemberRegister(ActionEvent event) {
    Membre membre = this.tvMembersList.getSelectionModel().getSelectedItem();
    Evenement evenement =
        this.tvEventList.getSelectionModel().getSelectedItem();
    
    this.association.gestionnaireEvenements().inscriptionEvenement(evenement,
        membre);
    this.miAssociationSave.setDisable(false);
    this.lbMessage.setText("Le membre a été inscrit à l'événement");
  }
  
  /**
   * Désinscrit un membre séléctionné d'un événement séléctionné.
   *
   * @param event (on click)
   */
  @FXML
  public void actionEventMemberUnregister(ActionEvent event) {
    Membre membre = this.tvMembersList.getSelectionModel().getSelectedItem();
    Evenement evenement =
        this.tvEventList.getSelectionModel().getSelectedItem();
    
    this.association.gestionnaireEvenements().annulerEvenement(evenement,
        membre);
    this.miAssociationSave.setDisable(false);
    this.lbMessage.setText("Le membre a été désinscrit de l'événement");
  }
}
