package association;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Description d'un événement géré par {@link GestionAssociation}.
 *
 * @author Gwendolyn Mandin
 * @author Nicolas Le Bars
 * @author Josselin Scouarnec
 * @see GestionEvenements
 */
public class Evenement implements java.io.Serializable, Cloneable {
  
  /**
   * Version de la classe.
   */
  private static final long serialVersionUID = 1019861634709625909L;
  
  /**
   * Le nom de l'événement.
   */
  private String nom;
  
  /**
   * Le lieu où l'événement a ou aura lieu.
   */
  private String lieu;
  
  /**
   * La date et l'heure à laquelle l'événement a eu ou aura lieu.
   */
  private LocalDateTime date;
  
  /**
   * La durée de l'événement.
   */
  private int duree;
  
  /**
   * Le nombre maximum de participants.
   */
  private int nbParticipantsMax;
  
  /**
   * Les participants.
   */
  private Set<InterMembre> participants;
  
  /**
   * Crée un événement avec tous les paramètres.
   *
   * @param nom le nom de l'événement
   * @param lieu le lieu de l'événement
   * @param date la date et l'heure de l'événement
   * @param duree la duree de l'événement
   * @param nbParticipantsMax le nombre maximum de participants de l'événement
   * @param participants l'ensemble des participants à l'événement
   */
  public Evenement(String nom, String lieu, LocalDateTime date, int duree,
      int nbParticipantsMax, Set<InterMembre> participants) {
    this.setNom(nom);
    this.setLieu(lieu);
    this.setDate(date);
    this.setDuree(duree);
    this.setNbParticipantsMax(nbParticipantsMax);
    this.setParticipants(participants);
  }
  
  /**
   * Crée un événement sans avoir à donner un LocalDateTime.
   *
   * @param nom le nom de l'événement
   * @param lieu le lieu de l'événement
   * @param jour le jour de la date de l'événement
   * @param mois le mois de la date de l'événement
   * @param annee l'année de la date de l'événement
   * @param heure l'heure de la date de l'événement
   * @param minutes la minute de la date de l'événement
   * @param duree la duree de l'événement
   * @param nbParticipants le nombre maximum de participants de l'événement
   */
  public Evenement(String nom, String lieu, int jour, Month mois, int annee,
      int heure, int minutes, int duree, int nbParticipants) {
    this(nom, lieu, LocalDateTime.of(annee, mois, jour, heure, minutes), duree,
        nbParticipants, new HashSet<>());
  }
  
  /**
   * Reformate une chaîne pour que la première lettre de chaque mot soit
   * capitalisée mais que le reste soit en minuscules.
   *
   * @param str la chaîne à reformater
   * @return la chaîne formatée
   */
  protected String strReformat(String str) {
    if (str == null || str.trim().isEmpty()) {
      return str;
    }
    
    return Arrays.stream(str.split("\\s+"))
        .map(word -> word.substring(0, 1).toUpperCase()
            + word.substring(1).toLowerCase())
        .collect(Collectors.joining(" "));
  }
  
  /**
   * Renvoie le nom de l'événement.
   *
   * @return le nom de l'événement
   */
  public String getNom() {
    return this.nom;
  }
  
  /**
   * Modifie le nom de l'événement.
   *
   * @param nom le nouveau nom
   */
  public void setNom(String nom) {
    this.nom = strReformat(nom);
  }
  
  /**
   * Renvoie le lieu de l'événement.
   *
   * @return le lieu de l'événement
   */
  public String getLieu() {
    return this.lieu;
  }
  
  /**
   * Modifie le lieu de l'événement.
   *
   * @param lieu le nouveau lieu
   */
  public void setLieu(String lieu) {
    this.lieu = strReformat(lieu);
  }
  
  /**
   * Renvoie la date de l'événement.
   *
   * @return la date de l'événement
   */
  public LocalDateTime getDate() {
    return this.date;
  }
  
  /**
   * Modifie la date de l'événement.
   *
   * @param date la nouvelle date
   */
  public void setDate(LocalDateTime date) {
    this.date = date;
  }
  
  /**
   * Renvoie la durée de l'événement.
   *
   * @return la durée de l'événement
   */
  public int getDuree() {
    return this.duree;
  }
  
  /**
   * Modifie la durée de l'événement.
   *
   * @param duree la nouvelle durée
   */
  public void setDuree(int duree) {
    this.duree = duree;
  }
  
  /**
   * Renvoie le nombre maximum de participants à l'événement.
   *
   * @return le nombre maximum de participants à l'événement
   */
  public int getNbParticipantsMax() {
    return this.nbParticipantsMax;
  }
  
  /**
   * Modifie nombre maximum de participants à l'événement.
   *
   * @param nbParticipantsMax le nouveau nombre maximum de participants
   */
  public void setNbParticipantsMax(int nbParticipantsMax) {
    this.nbParticipantsMax = nbParticipantsMax;
  }
  
  /**
   * Renvoie l'ensemble des participants à l'événement.
   *
   * @return l'ensemble des participants à l'événement
   */
  public Set<InterMembre> getParticipants() {
    return this.participants;
  }
  
  /**
   * Modifie la liste des participants à l'événement.
   *
   * @param participants la nouvelle liste des participants
   */
  public void setParticipants(Set<InterMembre> participants) {
    this.participants = participants;
  }
  
  /**
   * Teste si deux événement on lieu sur le même lieu.
   *
   * @param evt l'événement à comparer
   * @return <code>faux</code> si evt a le même lieu
   */
  public boolean pasDeChevauchementLieu(Evenement evt) {
    return !this.getLieu().equals(evt.getLieu());
  }
  
  /**
   * Teste si deux événement on lieu en même temps.
   *
   * @param evt l'événement à comparer
   * @return <code>faux</code> si evt se déroule au même moment
   */
  public boolean pasDeChevauchementTemps(Evenement evt) {
    /*
     * Vrai si le début de this est après la fin de evt OU la fin de this est
     * avant le début de evt
     */
    return this.getDate().isAfter(evt.getDate().plusHours(evt.getDuree()))
        || this.getDate().plusHours(this.getDuree()).isBefore(evt.getDate());
  }
  
  /**
   * Teste si deux événement on lieu en même temps et au même endroit.
   *
   * @param evt l'événement à comparer
   * @return <code>faux</code> si evt se déroule au même moment et au même
   *         endroit
   */
  public boolean pasDeChevauchement(Evenement evt) {
    /* vrai ssi ce n'est pas au même endroit OU pas au même moment */
    return this.pasDeChevauchementTemps(evt)
        || this.pasDeChevauchementLieu(evt);
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(this.getDate(), this.getDuree(), this.getLieu(),
        this.getNbParticipantsMax(), this.getNom());
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    
    if (obj == null) {
      return false;
    }
    
    if (this.getClass() != obj.getClass()) {
      return false;
    }
    
    Evenement other = (Evenement) obj;
    return this.getDate().equals(other.getDate())
        && this.getDuree() == other.getDuree()
        && this.getLieu().equals(other.getLieu())
        && this.getNbParticipantsMax() == other.getNbParticipantsMax()
        && this.getNom().equals(other.getNom());
  }
  
  @Override
  public Evenement clone() {
    Evenement evenement =
        new Evenement(this.getNom(), this.getLieu(), this.getDate(),
            this.getDuree(), this.getNbParticipantsMax(), new HashSet<>());
    
    if (this.getParticipants() != null) {
      evenement.getParticipants().addAll(this.getParticipants());
    }
    return evenement;
  }
  
  @Override
  public String toString() {
    return "Evenement [nom=" + this.getNom() + ", lieu=" + this.getLieu()
        + ", date=" + this.getDate() + ", duree=" + this.getDuree()
        + ", nbParticipantsMax=" + this.getNbParticipantsMax()
        + ", participants=" + this.getNbParticipantsMax() + "]";
  }
}
