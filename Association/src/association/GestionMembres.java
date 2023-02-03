package association;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Gestionnaire des membres d'une association {@link GestionAssociation}.
 *
 * @author Gwendolyn Mandin
 * @see Membre
 * @see InterGestionMembres
 * @see GestionAssociation
 */
public class GestionMembres
    implements InterGestionMembres, java.io.Serializable {
  
  /**
   * Version de la classe.
   */
  private static final long serialVersionUID = 7891213782426876524L;
  
  /**
   * Ensemble des membres.
   */
  private Set<InterMembre> membres;
  
  /**
   * Président de l'association, doit faire partie des membres.
   */
  private InterMembre president;
  
  /**
   * Crée un gestionnaire de membres avec aucun membres.
   */
  public GestionMembres() {
    this.membres = new HashSet<InterMembre>();
    this.president = null;
  }
  
  /**
   * Ajoute un membre dans l'association. Ne fait rien si le membre était déjà
   * présent dans l'association.
   *
   * @param membre le membre à rajouter
   * @return <code>true</code> si le membre a bien été ajouté,
   *         <code>false</code> si le membre était déjà présent (dans ce cas il
   *         n'est pas ajouté à nouveau)
   */
  @Override
  public boolean ajouterMembre(InterMembre membre) {
    return this.ensembleMembres().add(membre);
  }
  
  /**
   * Supprime un membre de l'association.
   *
   * @param membre le membre à supprimer
   * @return <code>true</code> si le membre était présent et a été supprimé,
   *         <code>false</code> si le membre n'était pas dans l'association
   */
  @Override
  public boolean supprimerMembre(InterMembre membre) {
    if (this.president() == membre) {
      this.designerPresident(null);
    }
    
    return this.ensembleMembres().remove(membre);
  }
  
  /**
   * Désigne le président de l'association. Il doit être un des membres de
   * l'association.
   *
   * @param membre le membre à désigner comme président.
   * @return <code>false</code> si le membre n'était pas dans l'association (le
   *         président n'est alors pas positionné), <code>true</code> si le
   *         membre a été nommé président
   */
  @Override
  public boolean designerPresident(InterMembre membre) {
    if (membre == null || this.ensembleMembres().contains(membre)) {
      this.president = membre;
      return true;
    }
    
    return false;
  }
  
  /**
   * Renvoie l'ensemble des membres de l'association.
   *
   * @return l'ensemble des membres de l'association.
   */
  @Override
  public Set<InterMembre> ensembleMembres() {
    return this.membres;
  }
  
  /**
   * Renvoie le président de l'association.
   *
   * @return le membre président de l'association s'il avait été désigné sinon
   *         retourne <code>null</code>
   */
  @Override
  public InterMembre president() {
    return this.president;
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(this.ensembleMembres(), this.president());
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
    
    GestionMembres other = (GestionMembres) obj;
    return Objects.equals(this.ensembleMembres(), other.ensembleMembres())
        && Objects.equals(this.president(), other.president());
  }
}
