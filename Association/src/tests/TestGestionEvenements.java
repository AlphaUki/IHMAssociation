package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import association.Evenement;
import association.GestionEvenements;
import java.time.Month;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests JUnit de la classe {@link association.GestionEvenements}.
 *
 * @author Josselin Scouarnec
 * @see association.GestionEvenements
 */
public class TestGestionEvenements {
  
  /**
   * Gestionnaire d'événements pour les tests.
   */
  private GestionEvenements gest;
  
  /**
   * Initialise le gestionnaire d'événement avant chaque test.
   *
   * @throws Exception ne peut pas être levée ici
   */
  @BeforeEach
  public void setUp() throws Exception {
    this.gest = new GestionEvenements();
  }
  
  /**
   * Initialisation des listes par le constructeur.
   */
  @Test
  public void testConstructeur() {
    assertTrue(this.gest.ensembleEvenements() != null);
    assertTrue(this.gest.ensembleEvenementsAvenir() != null);
    assertTrue(this.gest.ensembleEvenements().isEmpty());
    assertTrue(this.gest.ensembleEvenementsAvenir().isEmpty());
  }
  
  /**
   * Ajout d'un événement.
   */
  @Test
  public void testCreerEvenement() {
    Evenement evt = this.gest.creerEvenement("fête foraine",
        "à la foire st mich", 24, Month.SEPTEMBER, 2022, 8, 30, 12, 1000);
    assertTrue(evt != null);
    assertTrue(this.gest.ensembleEvenements().contains(evt));
    assertTrue(this.gest.ensembleEvenementsAvenir().contains(evt));
  }
  
  /**
   * Ajout d'un événement avec date et heures invalides.
   */
  @Test
  public void testCreerEvenementDateInvalide() {
    Evenement evt = this.gest.creerEvenement("fête foraine",
        "à la foire st mich", 38, Month.SEPTEMBER, 2022, 8, 30, 12, 1000);
    assertTrue(evt == null);
    assertEquals(this.gest.ensembleEvenements().size(), 0);
  }
  
  /**
   * Ajout d'un événement avec un nom invalide.
   */
  @Test
  public void testCreerEvenementNomInvalide() {
    Evenement evt = this.gest.creerEvenement("", "à la foire st mich", 24,
        Month.SEPTEMBER, 2022, 8, 30, 12, 1000);
    assertTrue(evt == null);
    assertEquals(this.gest.ensembleEvenements().size(), 0);
  }
  
  /**
   * Ajout d'un événement avec un lieu invalide.
   */
  @Test
  public void testCreerEvenementLieuInvalide() {
    Evenement evt = this.gest.creerEvenement("fête foraine", "", 24,
        Month.SEPTEMBER, 2022, 8, 30, 12, 1000);
    assertTrue(evt == null);
    assertEquals(this.gest.ensembleEvenements().size(), 0);
  }
  
  /**
   * Ajout d'un événement avec une durée invalide.
   */
  @Test
  public void testCreerEvenementDureeInvalide() {
    Evenement evt = this.gest.creerEvenement("fête foraine",
        "à la foire st mich", 24, Month.SEPTEMBER, 2022, 8, 30, -12, 1000);
    assertTrue(evt == null);
    assertEquals(this.gest.ensembleEvenements().size(), 0);
  }
  
  /**
   * Ajout d'un événement avec un nombre maximum de participants invalide.
   */
  @Test
  public void testCreerEvenementNombreMaximumParticipantsInvalide() {
    Evenement evt = this.gest.creerEvenement("fête foraine",
        "à la foire st mich", 24, Month.SEPTEMBER, 2022, 8, 30, 12, -1000);
    assertTrue(evt == null);
    assertEquals(this.gest.ensembleEvenements().size(), 0);
  }
  
  /**
   * Ajout d'un événement avec conflit avec un autre événement.
   */
  @Test
  public void testCreerEvenementConflit() {
    Evenement evt1 = this.gest.creerEvenement("fête foraine",
        "à la foire st mich", 24, Month.SEPTEMBER, 2022, 8, 30, 12, 1000);
    assertTrue(evt1 != null);
    assertTrue(this.gest.ensembleEvenements().contains(evt1));
    assertTrue(this.gest.ensembleEvenementsAvenir().contains(evt1));
    assertEquals(this.gest.ensembleEvenements().size(), 1);
    
    Evenement evt2 = this.gest.creerEvenement("brocante", "à la foire st mich",
        24, Month.SEPTEMBER, 2022, 9, 0, 10, 1000);
    assertTrue(evt2 == null);
    assertEquals(this.gest.ensembleEvenements().size(), 1);
  }
}
