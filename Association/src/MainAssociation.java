import java.io.IOException;

/**
 * Classe principale.
 */
public class MainAssociation {
  
  
  /**
   * Méthode principale.
   *
   * @param args not used
   */
  public static void main(String[] args) {
    System.out.println("Ca marche !");
    System.out.println("\nAppuyez sur Entrée pour terminer le programme ...");
    try {
      System.in.read();
    } catch (IOException e) {
      System.err.println("Vous avez réussi à casser le clavier : " + e);
    }
  }
  
}
