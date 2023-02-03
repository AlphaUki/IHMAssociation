package association;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Gestionnaire des événements d'une association {@link GestionAssociation}.
 *
 * @author Josselin Scouarnec
 * @see Evenement
 * @see InterGestionEvenements
 * @see GestionAssociation
 */
public class GestionEvenements
    implements InterGestionEvenements, java.io.Serializable {
  
  /**
   * Version de la classe.
   */
  private static final long serialVersionUID = 1279672301657103879L;
  
  /**
   * L'ensemble des événements de l'association.
   */
  private List<Evenement> evenements;
  
  /**
   * L'ensemble des événements à venir de l'association.
   */
  private List<Evenement> evenementsAvenir;
  
  
  /**
   * Crée un gestionnaire d'événements avec aucun événement.
   */
  public GestionEvenements() {
    this.evenements = new ArrayList<Evenement>();
    this.evenementsAvenir = new ArrayList<Evenement>();
  }
  
  /**
   * Renvoie l'ensemble des événements de l'association.
   *
   * @return l'ensemble des événements
   */
  @Override
  public List<Evenement> ensembleEvenements() {
    return this.evenements;
  }
  
  /**
   * Renvoie l'ensemble des événements à venir de l'association.
   *
   * @return l'ensemble des événements à venir
   */
  @Override
  public List<Evenement> ensembleEvenementsAvenir() {
    return this.evenementsAvenir;
  }
  
  /**
   * Crée un nouvel événement. Plusieurs vérifications sont effectuées : que les
   * dates et heures sont cohérentes et qu'il n'y a pas un chevauchement sur la
   * même période avec un autre événement dans le même lieu.
   *
   * @param nom le nom de l'événement
   * @param lieu le lieu
   * @param jour le jour dans le mois (nombre de 0 à 31)
   * @param mois le mois dans l'année
   * @param annee l'année
   * @param heure l'heure de la journée (nombre entre 0 et 23)
   * @param minutes les minutes de l'heure (nombre entre 0 et 59)
   * @param duree la durée (en minutes)
   * @param nbParticipants le nombre maximum de participants (0 signifie un
   *        nombre quelconque)
   * @return l'événement créé ou <code>null</code> en cas de problème
   *         (paramètres non valides)
   */
  @Override
  public Evenement creerEvenement(String nom, String lieu, int jour, Month mois,
      int annee, int heure, int minutes, int duree, int nbParticipants) {
    
    /* Valeurs invalides */
    if (nom.isEmpty() || lieu.isEmpty() || duree <= 0 || nbParticipants <= 0) {
      return null;
    }
    
    Evenement evt;
    try {
      evt = new Evenement(nom, lieu, jour, mois, annee, heure, minutes, duree,
          nbParticipants);
    } catch (DateTimeException e) { // Date invalide
      return null;
    }
    
    /* Événement dans le passé */
    // if (evt.getDate().isBefore(LocalDateTime.now()))
    // return null;
    
    /* Conflit avec d'autres événements */
    for (Evenement e : this.ensembleEvenements()) {
      if (!evt.pasDeChevauchement(e) || evt.getNom().equals(e.getNom())) {
        return null;
      }
    }
    
    /* Ajout aux ensembles */
    this.ensembleEvenements().add(evt);
    this.ensembleEvenementsAvenir().add(evt);
    return evt;
  }
  
  /**
   * Supprime un événement. Les membres qui étaient inscrits sont
   * automatiquement désinscrits de l'événement supprimé. Si l'événement
   * n'existait pas, la méthode ne fait rien.
   *
   * @param evt l'événement à supprimer.
   */
  @Override
  public void supprimerEvenement(Evenement evt) {
    /* Supprime evt des des membres inscrits */
    for (InterMembre mbr : evt.getParticipants()) {
      mbr.ensembleEvenements().remove(evt);
      mbr.ensembleEvenementsAvenir().remove(evt);
    }
    
    /* Supprime evt */
    this.ensembleEvenements().remove(evt);
    this.ensembleEvenementsAvenir().remove(evt);
  }
  
  /**
   * Un membre est incrit à un événement.
   *
   * @param evt l'événement auquel s'inscrire
   * @param mbr le membre qui s'inscrit
   * @return <code>true</code> s'il n'y a pas eu de problème, <code>false</code>
   *         si l'événement est en conflit de calendrier avec un événement
   *         auquel est déjà inscrit le membre ou si le nombre de participants
   *         maximum est déjà atteint
   */
  @Override
  public boolean inscriptionEvenement(Evenement evt, InterMembre mbr) {
    /* Membre déjà inscrit */
    if (evt.getParticipants().contains(mbr)) {
      return false;
    }
    
    /* Nombre max de participants atteint */
    if (evt.getParticipants().size() >= evt.getNbParticipantsMax()) {
      return false;
    }
    
    /* Conflits avec les inscriptions du membre */
    for (Evenement e : mbr.ensembleEvenements()) {
      if (!evt.pasDeChevauchementTemps(e)) {
        return false;
      }
    }
    
    /* Ajout de l'événement */
    mbr.ensembleEvenements().add(evt);
    
    if (evt.getDate().isAfter(LocalDateTime.now())) {
      mbr.ensembleEvenementsAvenir().add(evt);
    }
    
    evt.getParticipants().add(mbr);
    return true;
  }
  
  /**
   * Désincrit un membre d'un événement.
   *
   * @param evt l'événement auquel se désinscrire
   * @param mbr le membre qui se désincrit
   * @return si le membre était bien inscrit à l'événement, renvoie
   *         <code>true</code> pour préciser que l'annulation est effective,
   *         sinon <code>false</code> si le membre n'était pas inscrit à
   *         l'événement
   */
  @Override
  public boolean annulerEvenement(Evenement evt, InterMembre mbr) {
    if (evt.getParticipants().contains(mbr)) {
      evt.getParticipants().remove(mbr);
      mbr.ensembleEvenements().remove(evt);
      mbr.ensembleEvenementsAvenir().remove(evt);
      return true;
    }
    
    return false;
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(this.ensembleEvenements(),
        this.ensembleEvenementsAvenir());
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
    
    GestionEvenements other = (GestionEvenements) obj;
    return Objects.equals(this.ensembleEvenements(), other.ensembleEvenements())
        && Objects.equals(this.ensembleEvenementsAvenir(),
            other.ensembleEvenementsAvenir());
  }
  
  @Override
  public String toString() {
    return "GestionEvenement [evenements=" + this.ensembleEvenements()
        + ", evenementsAvenir=" + this.ensembleEvenementsAvenir() + "]";
  }
}
