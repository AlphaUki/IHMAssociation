package ui.helper;

import java.time.LocalDate;
import javafx.application.Platform;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.util.Callback;

/**
 * {@link DatePicker} pour une {@link TableCell}.
 *
 * @author Nicolas Le Bars
 * 
 * @param <S> Le type générique de la TableView
 */
public class DatePickerTableCell<S> extends TableCell<S, LocalDate> {
  
  /**
   * Fournit un {@link DatePicker} qui permet de modifier le contenu de la
   * cellule lorsque la cellule est double-cliquée, ou lorsque
   * {@link javafx.scene.control.TableView#edit(int, javafx.scene.control.TableColumn)}
   * est appelé. Cette méthode ne fonctionnera que sur les instances de
   * {@link TableColumn} qui sont de type LocalDate.
   *
   * @param <S> Le type du générique TableView
   * @return Un {@link Callback} qui peut être inséré dans le
   *         {@link TableColumn#cellFactoryProperty()} d'une colonne de tableau.
   */
  public static <S> Callback<TableColumn<S, LocalDate>, TableCell<S, LocalDate>> forTableColumn() {
    return list -> new DatePickerTableCell<S>();
  }
  
  /**
   * Fournit un {@link DatePicker} qui permet de modifier le contenu de la
   * cellule lorsque la cellule est double-cliquée, ou lorsque
   * {@link javafx.scene.control.TableView#edit(int, javafx.scene.control.TableColumn)}
   * est appelé. Cette méthode ne fonctionnera que sur les instances de
   * {@link TableColumn} qui sont de type LocalDate.
   *
   * @param <S> Le type du générique TableView
   * @param formatter Le TextFormatter
   * @return Un {@link Callback} qui peut être inséré dans le
   *         {@link TableColumn#cellFactoryProperty()} d'une colonne de tableau.
   */
  public static <S> Callback<TableColumn<S, LocalDate>, TableCell<S, LocalDate>> forTableColumn(
      TextFormatter<?> formatter) {
    return list -> new DatePickerTableCell<S>();
  }
  
  private DatePicker datePicker;
  private TextFormatter<?> textFormatter;
  
  /**
   * Crée un DatePickerTableCell avec un TextFormatter. Voir la méthode statique
   * {@link DatePickerTableCell forTableColumn}.
   *
   * @param formatter Le TextFormatter
   */
  public DatePickerTableCell(TextFormatter<?> formatter) {
    this.getStyleClass().add("date-time-picker-table-cell");
    this.datePicker = new DatePicker();
    this.textFormatter = formatter;
    this.datePicker.getEditor().setTextFormatter(this.textFormatter);
  }
  
  /**
   * Crée un DatePickerTableCell par défaut avec un TextFormatter null. Voir la
   * méthode statique {@link DatePickerTableCell forTableColumn}.
   */
  public DatePickerTableCell() {
    this(null);
  }
  
  @Override
  public void startEdit() {
    super.startEdit();
    if (!isEditing()) {
      return;
    }
    
    if (this.datePicker == null) {
      this.datePicker = new DatePicker();
      this.datePicker.getEditor().setTextFormatter(this.textFormatter);
    }
    
    this.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        Platform.runLater(() -> {
          commitEdit(this.datePicker.getValue());
        });
      }
    });
    
    this.datePicker.setEditable(true);
    this.datePicker.setValue(this.getItem());
    this.setText(null);
    this.setGraphic(this.datePicker);
    this.datePicker.getEditor().selectAll();
    this.datePicker.requestFocus();
  }
  
  @Override
  public void cancelEdit() {
    super.cancelEdit();
    this.setText(this.getItem().toString());
    this.setGraphic(null);
  }
  
  @Override
  public void updateItem(LocalDate item, boolean empty) {
    super.updateItem(item, empty);
    
    if (empty) {
      this.setText(null);
      this.setGraphic(null);
    } else if (this.isEditing()) {
      this.setText(null);
      this.setGraphic(this.datePicker);
    } else {
      this.setText(this.getItem().toString());
      this.setGraphic(null);
    }
  }
}
