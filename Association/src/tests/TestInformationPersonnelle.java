package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import association.InformationPersonnelle;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests JUnit de la classe {@link association.InformationPersonnelle
 * InformationPersonnelle}.
 *
 * @author Eric Cariou
 * @see association.InformationPersonnelle
 */
public class TestInformationPersonnelle {
  
  /**
   * Une information basique : prénom et nom.
   */
  private InformationPersonnelle infoBasique;
  /**
   * Une information complète : prénom, nom, adresse et age.
   */
  private InformationPersonnelle infoComplete;
  
  /**
   * Instancie une information basique et une complète pour les tests.
   *
   * @throws Exception ne peut pas être levée ici
   */
  @BeforeEach
  public void setUp() throws Exception {
    infoBasique = new InformationPersonnelle("Skywalker", "Luke");
    infoComplete =
        new InformationPersonnelle("Skywalker", "Luke", "Planète Tatooine", 20);
  }
  
  /**
   * Ne fait rien après les tests : à modifier au besoin.
   *
   * @throws Exception ne peut pas être levée ici
   */
  @AfterEach
  public void tearDown() throws Exception {}
  
  /**
   * Vérifie que l'on peut positionner un age de 25 ans.
   */
  @Test
  public void testAge25Basique() {
    infoBasique.setAge(25);
    assertEquals(infoBasique.getAge(), 25);
  }
  
  /**
   * Vérifie qu'on ne peut pas positionner un age négatif sur une information
   * basique.
   */
  @Test
  public void testAgeNegatifBasique() {
    infoBasique.setAge(-20);
    assertTrue(infoBasique.getAge() != -20);
  }
  
  /**
   * Vérifie qu'on ne peut pas positionner un age négatif sur une information
   * complète.
   */
  @Test
  public void testAgeNegatifComplet() {
    int age = infoComplete.getAge();
    infoComplete.setAge(-20);
    assertEquals(infoComplete.getAge(), age);
  }
  
  /**
   * Vérifie qu'une adresse n'est pas null quand on crée une information
   * personnelle.
   */
  @Test
  public void testAdresseNonNull() {
    assertTrue(infoBasique.getAdresse() != null);
    assertTrue(infoComplete.getAdresse() != null);
  }
  
  /**
   * Vérifie qu'on ne peut pas positionner une adresse null sur une information
   * existante.
   */
  @Test
  public void testSetterAdresseNull() {
    infoComplete.setAdresse(null);
    assertTrue(infoComplete.getAdresse() != null);
  }
  
  /**
   * Vérifie que les paramètres des constructeurs sont correctement gérés.
   */
  @Test
  public void testConstructeur() {
    InformationPersonnelle inf =
        new InformationPersonnelle("Vador", "Dark", "etoile de la mort", 30);
    assertEquals(inf.getNom(), "Vador");
    assertEquals(inf.getPrenom(), "Dark");
    assertTrue(inf.getAdresse() != null);
    assertTrue(inf.getAge() >= 0);
  }
}
