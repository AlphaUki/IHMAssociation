package association;

import java.util.Set;

/**
 * Gestion des membres de l'association.
 *
 * @author Eric Cariou
 */
public interface InterGestionMembres {
  /**
   * Ajoute un membre dans l'association. Ne fait rien si le membre était déjà
   * présent dans l'association.
   *
   * @param membre le membre à rajouter
   * @return <code>true</code> si le membre a bien été ajouté,
   *         <code>false</code> si le membre était déjà présent (dans ce cas il
   *         n'est pas ajouté à nouveau)
   */
  boolean ajouterMembre(InterMembre membre);
  
  /**
   * Supprime un membre de l'association.
   *
   * @param membre le membre à supprimer
   * @return <code>true</code> si le membre était présent et a été supprimé,
   *         <code>false</code> si le membre n'était pas dans l'association
   */
  boolean supprimerMembre(InterMembre membre);
  
  /**
   * Désigne le président de l'association. Il doit être un des membres de
   * l'association.
   *
   * @param membre le membre à désigner comme président.
   * @return <code>false</code> si le membre n'était pas dans l'association (le
   *         président n'est alors pas positionné), <code>true</code> si le
   *         membre a été nommé président
   */
  boolean designerPresident(InterMembre membre);
  
  /**
   * Renvoie l'ensemble des membres de l'association.
   *
   * @return l'ensemble des membres de l'association.
   */
  Set<InterMembre> ensembleMembres();
  
  /**
   * Renvoie le président de l'association.
   *
   * @return le membre président de l'association s'il avait été désigné sinon
   *         retourne <code>null</code>
   */
  InterMembre president();
}
