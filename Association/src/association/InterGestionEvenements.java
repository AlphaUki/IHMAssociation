package association;

import java.time.Month;
import java.util.List;

/**
 * Gestion des événements d'une association.
 *
 * @author Eric Cariou
 *
 */
public interface InterGestionEvenements {
  
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
  Evenement creerEvenement(String nom, String lieu, int jour, Month mois,
      int annee, int heure, int minutes, int duree, int nbParticipants);
  
  /**
   * Supprime un événement. Les membres qui étaient inscrits sont
   * automatiquement désinscrits de l'événement supprimé. Si l'événement
   * n'existait pas, la méthode ne fait rien.
   *
   * @param evt l'événement à supprimer.
   */
  void supprimerEvenement(Evenement evt);
  
  /**
   * Renvoie l'ensemble des événements de l'association.
   *
   * @return l'ensemble des événements
   */
  List<Evenement> ensembleEvenements();
  
  /**
   * Renvoie l'ensemble des événements à venir de l'association.
   *
   * @return l'ensemble des événements à venir
   */
  List<Evenement> ensembleEvenementsAvenir();
  
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
  boolean inscriptionEvenement(Evenement evt, InterMembre mbr);
  
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
  boolean annulerEvenement(Evenement evt, InterMembre mbr);
}
