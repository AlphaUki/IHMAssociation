package ui.helper;

import javafx.application.Platform;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.util.Callback;

/**
 * {@link Spinner} pour une {@link TableCell}.
 *
 * @author Nicolas Le Bars
 * 
 * @param <S> Le type générique de la TableView
 * @param <T> Le type de toutes les valeurs qui peuvent être itérées dans le
 *        Spinner. Les types courants sont Integer et String.
 */
public class SpinnerTableCell<S, T> extends TableCell<S, T> {
  
  /**
   * Fournit un {@link Spinner} qui permet de modifier le contenu de la cellule
   * lorsque la cellule est double-cliquée, ou lorsque
   * {@link javafx.scene.control.TableView#edit(int, javafx.scene.control.TableColumn)}
   * est appelé.
   *
   * @param <S> Le type du générique TableView
   * @param <T> Le type de toutes les valeurs qui peuvent être itérées dans le
   *        Spinner. Les types courants sont Integer et String
   * @param min La valeur entière minimale autorisée pour le Spinner
   * @param max La valeur entière maximale autorisée pour le Spinner
   * @param initial La valeur du Spinner lors de sa première instanciation, doit
   *        être comprise dans les limites des arguments min et max, ou sinon la
   *        valeur min sera utilisée
   * @param step Le montant à incrémenter ou décrémenter par étape
   * @return Un {@link Callback} qui peut être inséré dans le
   *         {@link TableColumn#cellFactoryProperty()} d'une colonne de tableau
   */
  public static <S, T> Callback<TableColumn<S, T>, TableCell<S, T>> forTableColumn(
      int min, int max, int initial, int step) {
    return list -> new SpinnerTableCell<S, T>(min, max, initial, step);
  }
  
  /**
   * Fournit un {@link Spinner} qui permet de modifier le contenu de la cellule
   * lorsque la cellule est double-cliquée, ou lorsque
   * {@link javafx.scene.control.TableView#edit(int, javafx.scene.control.TableColumn)}
   * est appelé.
   *
   * @param <S> Le type du générique TableView
   * @param <T> Le type de toutes les valeurs qui peuvent être itérées dans le
   *        Spinner. Les types courants sont Integer et String
   * @param min La valeur entière minimale autorisée pour le Spinner
   * @param max La valeur entière maximale autorisée pour le Spinner
   * @param initial La valeur du Spinner lors de sa première instanciation, doit
   *        être comprise dans les limites des arguments min et max, ou sinon la
   *        valeur min sera utilisée
   * @param step Le montant à incrémenter ou décrémenter par étape
   * @param formatter Le TextFormatter
   * @return Un {@link Callback} qui peut être inséré dans le
   *         {@link TableColumn#cellFactoryProperty()} d'une colonne de tableau
   */
  public static <S, T> Callback<TableColumn<S, T>, TableCell<S, T>> forTableColumn(
      int min, int max, int initial, int step, TextFormatter<?> formatter) {
    return list -> new SpinnerTableCell<S, T>(min, max, initial, step,
        formatter);
  }
  
  private Spinner<T> spinner;
  private TextFormatter<?> textFormatter;
  
  /**
   * Créer un SpinnerTableCell avec un TextFormatter.
   *
   * @param min La valeur minimale
   * @param max La valeur maximale
   * @param initial La valeur initiale
   * @param step Le pas
   * @param formatter Le TextFormatter
   */
  public SpinnerTableCell(int min, int max, int initial, int step,
      TextFormatter<?> formatter) {
    this.getStyleClass().add("spinner-table-cell");
    this.spinner = new Spinner<>(min, max, initial, step);
    this.textFormatter = formatter;
    this.spinner.getEditor().setTextFormatter(this.textFormatter);
  }
  
  /**
   * Créer un SpinnerTableCell sans TextFormatter.
   *
   * @param min La valeur minimale
   * @param max La valeur maximale
   * @param initial La valeur initiale
   * @param step Le pas
   */
  public SpinnerTableCell(int min, int max, int initial, int step) {
    this(min, max, initial, step, null);
  }
  
  @Override
  public void startEdit() {
    super.startEdit();
    if (!isEditing()) {
      return;
    }
    
    if (this.spinner == null) {
      this.spinner = new Spinner<>();
      this.spinner.getEditor().setTextFormatter(this.textFormatter);
    }
    
    this.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        Platform.runLater(() -> {
          commitEdit(this.spinner.getValue());
        });
      }
    });
    
    this.spinner.setEditable(true);
    this.spinner.getValueFactory().setValue(this.getItem());
    this.setText(null);
    this.setGraphic(this.spinner);
    this.spinner.getEditor().selectAll();
    this.spinner.requestFocus();
  }
  
  @Override
  public void cancelEdit() {
    super.cancelEdit();
    this.setText(this.getItem().toString());
    this.setGraphic(null);
  }
  
  @Override
  public void updateItem(T item, boolean empty) {
    super.updateItem(item, empty);
    
    if (empty) {
      this.setText(null);
      this.setGraphic(null);
    } else if (this.isEditing()) {
      this.setText(null);
      this.setGraphic(this.spinner);
    } else {
      this.setText(this.getItem().toString());
      this.setGraphic(null);
    }
  }
}
