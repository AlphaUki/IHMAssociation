package tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import association.GestionMembres;
import association.InformationPersonnelle;
import association.InterMembre;
import association.Membre;
import org.junit.jupiter.api.Test;

/**
 * Tests JUnit de la classe {@link association.GestionMembres}.
 *
 * @author Gwendolyn Mandin
 * @see association.GestionMembres
 */
public class TestGestionMembres {
  
  /**
   * Test du constructeur.
   */
  @Test
  public void testConstructeur() {
    GestionMembres groupe = new GestionMembres();
    assertTrue(groupe.ensembleMembres().size() == 0);
    assertTrue(groupe.president() == null);
  }
  
  /**
   * Ajout d'un membre.
   */
  @Test
  public void testAjouterMembreOk() {
    GestionMembres groupe = new GestionMembres();
    InterMembre membre = new Membre();
    membre.definirInformationPersonnnelle(
        new InformationPersonnelle("jean", "jacques"));
    assertTrue(groupe.ajouterMembre(membre));
    assertTrue(groupe.ensembleMembres().size() == 1);
  }
  
  /**
   * Ajout d'un membre qui est déjà présent mais avec la capitalisation
   * différente.
   */
  @Test
  public void testAjouterMembreDejaPresent() {
    GestionMembres groupe = new GestionMembres();
    InterMembre membre = new Membre();
    membre.definirInformationPersonnnelle(
        new InformationPersonnelle("jean", "jacques"));
    assertTrue(groupe.ajouterMembre(membre));
    assertTrue(groupe.ensembleMembres().size() == 1);
    assertFalse(groupe.ajouterMembre(membre));
    assertTrue(groupe.ensembleMembres().size() == 1);
  }
  
  /**
   * Supprimer un membre.
   */
  @Test
  public void testSupprimerMembreOk() {
    GestionMembres groupe = new GestionMembres();
    InterMembre membre = new Membre();
    membre.definirInformationPersonnnelle(
        new InformationPersonnelle("jean", "jacques"));
    groupe.ajouterMembre(membre);
    assertTrue(groupe.supprimerMembre(membre));
    assertTrue(groupe.ensembleMembres().size() == 0);
  }
  
  /**
   * Supprimer un membre qui ne fait pas partie de l'association.
   */
  @Test
  public void testSupprimerMembreNonInscrit() {
    GestionMembres groupe = new GestionMembres();
    InterMembre membre = new Membre();
    membre.definirInformationPersonnnelle(
        new InformationPersonnelle("jean", "jacques"));
    assertFalse(groupe.supprimerMembre(membre));
    assertTrue(groupe.ensembleMembres().size() == 0);
  }
  
  /**
   * Supprimer le président.
   */
  @Test
  public void testSupprimerPresident() {
    GestionMembres groupe = new GestionMembres();
    InterMembre membre = new Membre();
    membre.definirInformationPersonnnelle(
        new InformationPersonnelle("jean", "jacques"));
    assertTrue(groupe.ajouterMembre(membre));
    assertTrue(groupe.designerPresident(membre));
    assertTrue(groupe.supprimerMembre(membre));
    assertTrue(groupe.president() == null);
  }
  
  /**
   * Désigner un membre comme président.
   */
  @Test
  public void testdesignerPresidentEstMembre() {
    GestionMembres groupe = new GestionMembres();
    InterMembre membre = new Membre();
    membre.definirInformationPersonnnelle(
        new InformationPersonnelle("jean", "jacques"));
    groupe.ajouterMembre(membre);
    assertTrue(groupe.designerPresident(membre));
    assertTrue(groupe.president() == membre);
  }
  
  /**
   * Désigner comme président un membre qui ne fait pas partie de l'association.
   */
  @Test
  public void testdesignerPresidentPasMembre() {
    GestionMembres groupe = new GestionMembres();
    InterMembre membre = new Membre();
    membre.definirInformationPersonnnelle(
        new InformationPersonnelle("jean", "jacques"));
    assertFalse(groupe.designerPresident(membre));
    assertFalse(groupe.president() == membre);
  }
}
