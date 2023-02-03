package association;

import java.io.IOException;

/**
 * Définition des actions de gestion de l'association.
 *
 * @author Eric Cariou
 */
public interface InterGestionAssociation {
  
  /**
   * Renvoie le gestionnaire d'événements de l'association. L'objet retourné est
   * unique. Au premier appel de la méthode, il est créé et aux appels suivants,
   * c'est la référence sur cet objet qui est retournée.
   *
   * @return le gestionnaire d'événements de l'association
   */
  InterGestionEvenements gestionnaireEvenements();
  
  /**
   * Renvoie le gestionnaire de membres de l'association. L'objet retourné est
   * unique. Au premier appel de la méthode, il est créé et aux appels suivants,
   * c'est la référence sur cet objet qui est retournée.
   *
   * @return le gestionnaire de membres de l'association
   */
  InterGestionMembres gestionnaireMembre();
  
  /**
   * Enregistre dans un fichier toutes les données de l'association,
   * c'est-à-dire l'ensemble des membres et des événéments.
   *
   * @param nomFichier le fichier dans lequel enregistrer les données
   * @throws IOException en cas de problème d'écriture dans le fichier
   */
  void sauvegarderDonnees(String nomFichier) throws IOException;
  
  /**
   * Charge à partir d'un fichier toutes les données de l'association,
   * c'est-à-dire un ensemble de membres et d'événements. Si des membres et des
   * événéments avaient déjà été définis, ils sont écrasés par le contenu trouvé
   * dans le fichier.
   *
   * @param nomFichier le fichier à partir duquel charger les données
   * @throws IOException en cas de problème de lecture dans le fichier
   */
  void chargerDonnees(String nomFichier) throws IOException;
}
