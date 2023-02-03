package association;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Description d'un membre géré par {@link GestionMembres}.
 *
 * @author Nicolas Le Bars
 * @see InterMembre
 * @see GestionMembres
 */
public class Membre implements InterMembre, java.io.Serializable, Cloneable {
  
  
  
  /**
   * Version de la classe.
   */
  private static final long serialVersionUID = 4167389720104587479L;
  
  /**
   * Les informations personnelles du membre.
   */
  private InformationPersonnelle informationPersonnelle;
  
  /**
   * La liste des événements auquel le membre est inscrit ou a participé.
   */
  private List<Evenement> evenements;
  
  /**
   * La liste des événements auquel le membre est inscrit et qui n'ont pas
   * encore eu lieu.
   */
  private List<Evenement> evenementsAvenir;
  
  /**
   * Crée un membre sans événements.
   */
  public Membre() {
    this.evenements = new ArrayList<>();
    this.evenementsAvenir = new ArrayList<>();
  }
  
  /**
   * La liste des événements auquel le membre est inscrit ou a participé.
   *
   * @return la liste des événements du membre
   */
  @Override
  public List<Evenement> ensembleEvenements() {
    return this.evenements;
  }
  
  /**
   * La liste des événements auquel le membre est inscrit et qui n'ont pas
   * encore eu lieu.
   *
   * @return la liste des événements à venir du memmbre
   */
  @Override
  public List<Evenement> ensembleEvenementsAvenir() {
    return this.evenementsAvenir;
  }
  
  /**
   * Définit les informations personnelles du membre.
   *
   * @param info les informations personnelles du membre
   */
  @Override
  public void definirInformationPersonnnelle(InformationPersonnelle info) {
    this.informationPersonnelle = info;
  }
  
  /**
   * Renvoie les informations personnelles du membre.
   *
   * @return l'objet contenant les informations personnelles du membre ou
   *         <code>null</code> si elles n'ont pas été définies
   */
  @Override
  public InformationPersonnelle getInformationPersonnelle() {
    return this.informationPersonnelle;
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(this.getInformationPersonnelle().getNom().toLowerCase(),
        this.getInformationPersonnelle().getPrenom().toLowerCase());
  }
  
  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    
    if (obj == null) {
      return false;
    }
    
    if (this.getClass() != obj.getClass()) {
      return false;
    }
    
    Membre other = (Membre) obj;
    return this.getInformationPersonnelle().getNom()
        .equalsIgnoreCase(other.getInformationPersonnelle().getNom())
        && this.getInformationPersonnelle().getPrenom()
            .equalsIgnoreCase(other.getInformationPersonnelle().getPrenom());
  }
  
  @Override
  public Membre clone() {
    Membre membre = new Membre();
    
    membre.definirInformationPersonnnelle(this.getInformationPersonnelle());
    membre.ensembleEvenements().addAll(this.ensembleEvenements());
    membre.ensembleEvenementsAvenir().addAll(this.ensembleEvenementsAvenir());
    
    return membre;
  }
  
  @Override
  public String toString() {
    return "Membre [informationPersonnelle=" + this.getInformationPersonnelle()
        + ", evenements=" + this.ensembleEvenements() + ", evenementsAvenir="
        + this.ensembleEvenementsAvenir() + "]";
  }
}
