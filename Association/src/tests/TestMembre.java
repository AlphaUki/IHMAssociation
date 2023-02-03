package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import association.InformationPersonnelle;
import association.Membre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests JUnit de la classe {@link association.Membre}.
 *
 * @author Nicolas Le Bars
 * @see association.Membre
 */
public class TestMembre {
  
  /**
   * Un membre.
   */
  private Membre membre;
  
  /**
   * Instancie un membre pour les tests.
   *
   * @throws Exception not specified
   */
  @BeforeEach
  public void setUp() throws Exception {
    this.membre = new Membre();
  }
  
  /**
   * Ajout d'une information personnelle basique.
   */
  @Test
  public void testDefinirInformationPersonnnelleBasique() {
    InformationPersonnelle infoBasique =
        new InformationPersonnelle("Boba", "Fett");
    this.membre.definirInformationPersonnnelle(infoBasique);
    assertEquals(this.membre.getInformationPersonnelle().getNom(), "Boba");
    assertEquals(this.membre.getInformationPersonnelle().getPrenom(), "Fett");
  }
  
  /**
   * Ajout d'une information personnelle complet.
   */
  @Test
  public void testDefinirInformationPersonnnelleComplet() {
    InformationPersonnelle infoComplet =
        new InformationPersonnelle("Boba", "Fett", "Tatooine", 41);
    this.membre.definirInformationPersonnnelle(infoComplet);
    assertEquals(this.membre.getInformationPersonnelle().getNom(), "Boba");
    assertEquals(this.membre.getInformationPersonnelle().getPrenom(), "Fett");
    assertEquals(this.membre.getInformationPersonnelle().getAdresse(),
        "Tatooine");
    assertTrue(this.membre.getInformationPersonnelle().getAge() == 41);
  }
}
