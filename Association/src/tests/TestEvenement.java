package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import association.Evenement;
import association.InformationPersonnelle;
import association.InterMembre;
import association.Membre;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests JUnit de la classe {@link association.Evenement}.
 *
 * @author Nicolas Le Bars
 * @see association.Evenement
 */
public class TestEvenement {
  
  /**
   * Un événement avec tous les paramètres.
   */
  private Evenement evenement1;
  
  /**
   * Un événement sans avoir à donner un LocalDateTime.
   */
  private Evenement evenement2;
  
  /**
   * Instancie un événement avec tous les paramètres et un événement sans avoir
   * à donner un LocalDateTime.
   *
   * @throws Exception not specified
   */
  @BeforeEach
  public void setUp() throws Exception {
    this.evenement1 =
        new Evenement("La Boumba", "335 Route De Rosporden, 29000 Quimper",
            LocalDateTime.of(2022, Month.SEPTEMBER, 24, 14, 30), 6, 320,
            new HashSet<>());
    this.evenement2 = new Evenement("El Tri Mujer",
        "8 Avenue Georges Clemenceau, 29200 Brest", 25, Month.OCTOBER, 2022, 18,
        20, 5, 150);
  }
  
  /**
   * Modification du nom de l'événement : avec tous les paramètres.
   */
  @Test
  public void testSetNom1() {
    String nom = "Kimperfest";
    this.evenement1.setNom(nom);
    assertEquals(this.evenement1.getNom(), nom);
  }
  
  /**
   * Modification du nom de l'événement : sans avoir à donner un LocalDateTime.
   */
  @Test
  public void testSetNom2() {
    String nom = "Berlinginer";
    this.evenement2.setNom(nom);
    assertEquals(this.evenement2.getNom(), nom);
  }
  
  /**
   * Modification du lieu de l'événement : avec tous les paramètres.
   */
  @Test
  public void testSetLieu1() {
    String lieu = "8 Route De Douarnenez, 29000 Quimper";
    this.evenement1.setLieu(lieu);
    assertEquals(this.evenement1.getLieu(), lieu);
  }
  
  /**
   * Modification du lieu de l'événement : sans avoir à donner un LocalDateTime.
   */
  @Test
  public void testSetLieu2() {
    String lieu = "49 Rue De Siam, 29200 Brest";
    this.evenement2.setLieu(lieu);
    assertEquals(this.evenement2.getLieu(), lieu);
  }
  
  /**
   * Modification de la date de l'événement : avec tous les paramètres.
   */
  @Test
  public void testSetDate1() {
    LocalDateTime date = LocalDateTime.of(2022, Month.OCTOBER, 25, 16, 00);
    this.evenement1.setDate(date);
    assertTrue(this.evenement1.getDate().equals(date));
  }
  
  /**
   * Modification de la date de l'événement : sans avoir à donner un
   * LocalDateTime.
   */
  @Test
  public void testSetDate2() {
    LocalDateTime date = LocalDateTime.of(2022, Month.OCTOBER, 24, 20, 30);
    this.evenement2.setDate(date);
    assertTrue(this.evenement2.getDate().equals(date));
  }
  
  /**
   * Modification de la durée de l'événement : avec tous les paramètres.
   */
  @Test
  public void testSetDuree1() {
    int duree = 8;
    this.evenement1.setDuree(duree);
    assertTrue(this.evenement1.getDuree() == duree);
  }
  
  /**
   * Modification de la durée de l'événement : sans avoir à donner un
   * LocalDateTime.
   */
  @Test
  public void testSetDuree2() {
    int duree = 6;
    this.evenement2.setDuree(duree);
    assertTrue(this.evenement2.getDuree() == duree);
  }
  
  /**
   * Modification du nombre de participants maximum de l'événement : avec tous
   * les paramètres.
   */
  @Test
  public void testSetNbParticipantsMax1() {
    int nbParticipantsMax = 8;
    this.evenement1.setNbParticipantsMax(nbParticipantsMax);
    assertTrue(this.evenement1.getNbParticipantsMax() == nbParticipantsMax);
  }
  
  /**
   * Modification du nombre de participants maximum de l'événement : sans avoir
   * à donner un LocalDateTime.
   */
  @Test
  public void testSetNbParticipantsMax2() {
    int nbParticipantsMax = 8;
    this.evenement2.setNbParticipantsMax(nbParticipantsMax);
    assertTrue(this.evenement2.getNbParticipantsMax() == nbParticipantsMax);
  }
  
  /**
   * Modification de la liste des participants de l'événement : avec tous les
   * paramètres.
   */
  @Test
  public void testSetParticipants1() {
    Set<InterMembre> participants = new HashSet<>();
    InterMembre mbr = new Membre();
    mbr.definirInformationPersonnnelle(
        new InformationPersonnelle("jean", "jacques"));
    participants.add(mbr);
    
    this.evenement1.setParticipants(participants);
    assertTrue(this.evenement1.getParticipants().size() == 1);
  }
  
  /**
   * Modification de la liste des participants de l'événement : sans avoir à
   * donner un LocalDateTime.
   */
  @Test
  public void testSetParticipants2() {
    Set<InterMembre> participants = new HashSet<>();
    InterMembre mbr = new Membre();
    mbr.definirInformationPersonnnelle(
        new InformationPersonnelle("jean", "jacques"));
    participants.add(mbr);
    
    this.evenement2.setParticipants(participants);
    assertTrue(this.evenement2.getParticipants().size() == 1);
  }
  
  /**
   * Test que deux événements ne se déroulent pas au même endroit.
   */
  @Test
  public void testPasDeChevauchementLieuPositif() {
    assertTrue(this.evenement1.pasDeChevauchementLieu(this.evenement2));
  }
  
  /**
   * Test que deux événements se déroulent au même endroit.
   */
  @Test
  public void testPasDeChevauchementLieuNegatif() {
    this.evenement1.setLieu(this.evenement2.getLieu());
    assertFalse(this.evenement1.pasDeChevauchementLieu(this.evenement2));
  }
  
  /**
   * Test que deux événements ne se déroulent pas au même moment.
   */
  @Test
  public void testPasDeChevauchementTempsPositif() {
    assertTrue(this.evenement1.pasDeChevauchementTemps(this.evenement2));
  }
  
  /**
   * Test que deux événements se déroulent au même moment.
   */
  @Test
  public void testPasDeChevauchementTempsNegatif() {
    this.evenement1.setDate(this.evenement2.getDate());
    assertFalse(this.evenement1.pasDeChevauchementTemps(this.evenement2));
  }
  
  /**
   * Test que deux événements ne se déroulent pas au même endroit ni ou au même
   * moment.
   */
  @Test
  public void testPasDeChevauchementPositif() {
    assertTrue(this.evenement1.pasDeChevauchement(this.evenement2));
  }
  
  /**
   * Test que deux événements se déroulent au même endroit ou au même moment.
   */
  @Test
  public void testPasDeChevauchementNegatif() {
    this.evenement1.setLieu(this.evenement2.getLieu());
    this.evenement1.setDate(this.evenement2.getDate());
    assertFalse(this.evenement1.pasDeChevauchement(this.evenement2));
  }
  
  /**
   * Test que deux événements sont les mêmes.
   */
  @Test
  public void testEquals() {
    this.evenement1.setNom(this.evenement2.getNom());
    this.evenement1.setLieu(this.evenement2.getLieu());
    this.evenement1.setDate(this.evenement2.getDate());
    this.evenement1.setDuree(this.evenement2.getDuree());
    this.evenement1
        .setNbParticipantsMax(this.evenement2.getNbParticipantsMax());
    this.evenement1.setParticipants(this.evenement2.getParticipants());
    assertTrue(this.evenement1.equals(this.evenement2));
  }
}
