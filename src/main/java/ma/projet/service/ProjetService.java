package ma.projet.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import ma.projet.entities.EmployeTache;
import ma.projet.entities.Projet;
import ma.projet.util.HibernateUtil;

import java.util.List;

public class ProjetService implements IDao<Projet> {

    @Override
    public boolean create(Projet o) {
        EntityManager em = HibernateUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Projet o) {
        EntityManager em = HibernateUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.contains(o) ? o : em.merge(o));
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Projet o) {
        EntityManager em = HibernateUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Projet findById(int id) {
        EntityManager em = HibernateUtil.getEmf().createEntityManager();
        return em.find(Projet.class, id);
    }

    @Override
    public List<Projet> findAll() {
        EntityManager em = HibernateUtil.getEmf().createEntityManager();
        return em.createQuery("from Projet", Projet.class).getResultList();
    }

    // Requête spécifique

    public void afficherTachesRealisees(int projetId) {
        EntityManager em = HibernateUtil.getEmf().createEntityManager();
        Projet projet = em.find(Projet.class, projetId);
        if (projet == null) {
            System.out.println("Projet non trouvé.");
            return;
        }

        // Format de date pour l'affichage
        java.text.SimpleDateFormat sdfProjet = new java.text.SimpleDateFormat("dd MMMM yyyy");
        java.text.SimpleDateFormat sdfTache = new java.text.SimpleDateFormat("dd/MM/yyyy");

        System.out.println("Projet : " + projet.getId() + "      Nom : " + projet.getNom() + "     Date début : " + sdfProjet.format(projet.getDateDebut()));
        System.out.println("Liste des tâches:");
        System.out.printf("%-5s %-15s %-20s %-20s%n", "Num", "Nom", "Date Début Réelle", "Date Fin Réelle");

        String jpql = "SELECT et FROM EmployeTache et WHERE et.tache.projet.id = :projetId";
        TypedQuery<EmployeTache> query = em.createQuery(jpql, EmployeTache.class);
        query.setParameter("projetId", projetId);
        List<EmployeTache> employeTaches = query.getResultList();

        if (employeTaches.isEmpty()) {
            System.out.println("  -> Aucune tâche réalisée pour ce projet.");
        } else {
            for (EmployeTache et : employeTaches) {
                System.out.printf("%-5d %-15s %-20s %-20s%n",
                        et.getTache().getId(),
                        et.getTache().getNom(),
                        sdfTache.format(et.getDateDebutReelle()),
                        sdfTache.format(et.getDateFinReelle()));
            }
        }
    }
}
