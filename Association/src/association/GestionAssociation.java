package association;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Objects;

/**
 * Gère l'enssembles des composantes d'une association.
 *
 * @author Nicolas Le Bars
 * @see InterGestionAssociation
 * @see GestionEvenements
 * @see GestionMembres
 */
public class GestionAssociation implements InterGestionAssociation {
  
  private InterGestionEvenements gestionEvenements;
  private InterGestionMembres gestionMembres;
  
  /**
   * Renvoie le gestionnaire d'événements de l'association. L'objet retourné est
   * unique. Au premier appel de la méthode, il est créé et aux appels suivants,
   * c'est la référence sur cet objet qui est retournée.
   *
   * @return le gestionnaire d'événements de l'association
   */
  @Override
  public InterGestionEvenements gestionnaireEvenements() {
    if (this.gestionEvenements == null) {
      this.gestionEvenements = new GestionEvenements();
    }
    
    return this.gestionEvenements;
  }
  
  /**
   * Renvoie le gestionnaire de membres de l'association. L'objet retourné est
   * unique. Au premier appel de la méthode, il est créé et aux appels suivants,
   * c'est la référence sur cet objet qui est retournée.
   *
   * @return le gestionnaire de membres de l'association
   */
  @Override
  public InterGestionMembres gestionnaireMembre() {
    if (this.gestionMembres == null) {
      this.gestionMembres = new GestionMembres();
    }
    
    return this.gestionMembres;
  }
  
  /**
   * Enregistre dans un fichier toutes les données de l'association,
   * c'est-à-dire l'ensemble des membres et des événéments.
   *
   * @param nomFichier le fichier dans lequel enregistrer les données
   * @throws IOException en cas de problème d'écriture dans le fichier
   */
  @Override
  public void sauvegarderDonnees(String nomFichier) throws IOException {
    ObjectOutputStream oos = new ObjectOutputStream(
        new BufferedOutputStream(new FileOutputStream(nomFichier)));
    oos.writeObject(this.gestionnaireEvenements());
    oos.writeObject(this.gestionnaireMembre());
    oos.close();
  }
  
  /**
   * Charge à partir d'un fichier toutes les données de l'association,
   * c'est-à-dire un ensemble de membres et d'événements. Si des membres et des
   * événéments avaient déjà été définis, ils sont écrasés par le contenu trouvé
   * dans le fichier.
   *
   * @param nomFichier le fichier à partir duquel charger les données
   * @throws IOException en cas de problème de lecture dans le fichier
   */
  @Override
  public void chargerDonnees(String nomFichier) throws IOException {
    ObjectInputStream ois = new ObjectInputStream(
        new BufferedInputStream(new FileInputStream(nomFichier)));
    try {
      this.gestionEvenements = (GestionEvenements) ois.readObject();
      this.gestionMembres = (GestionMembres) ois.readObject();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      ois.close();
    }
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(this.gestionnaireEvenements(),
        this.gestionnaireMembre());
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
    
    GestionAssociation other = (GestionAssociation) obj;
    return Objects.equals(this.gestionnaireEvenements(),
        other.gestionnaireEvenements())
        && Objects.equals(this.gestionnaireMembre(),
            other.gestionnaireMembre());
  }
  
  @Override
  public String toString() {
    return "GestionAssociation [gestionEvenements="
        + this.gestionnaireEvenements() + ", gestionMembres="
        + this.gestionnaireMembre() + "]";
  }
}
