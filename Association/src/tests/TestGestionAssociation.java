package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import association.GestionAssociation;
import java.io.IOException;
import org.junit.jupiter.api.Test;

/**
 * Tests JUnit de la classe {@link association.GestionAssociation}.
 *
 * @author Josselin Scouarnec
 * @see association.GestionAssociation
 */
public class TestGestionAssociation {
  
  /**
   * Nom du fichier de test pour la sérialisation.
   */
  private static final String FICHIER = "testSerialisation.tmp";
  
  /**
   * Association chargée identique à l'association sauvegardée.
   *
   * @throws IOException not specified
   */
  @Test
  public void testSerialisation() throws IOException {
    GestionAssociation asso1 = new GestionAssociation();
    asso1.gestionnaireEvenements();
    asso1.gestionnaireMembre();
    
    asso1.sauvegarderDonnees(FICHIER);
    
    GestionAssociation asso2 = new GestionAssociation();
    asso2.chargerDonnees(FICHIER);
    assertEquals(asso1, asso2);
  }
}
