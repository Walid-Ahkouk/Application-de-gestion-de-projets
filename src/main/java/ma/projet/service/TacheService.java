package ma.projet.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import ma.projet.entities.Tache;
import ma.projet.util.HibernateUtil;

import java.util.Date;
import java.util.List;

public class TacheService implements IDao<Tache> {

    @Override
    public boolean create(Tache o) {
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
    public boolean delete(Tache o) {
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
    public boolean update(Tache o) {
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
    public Tache findById(int id) {
        EntityManager em = HibernateUtil.getEmf().createEntityManager();
        return em.find(Tache.class, id);
    }

    @Override
    public List<Tache> findAll() {
        EntityManager em = HibernateUtil.getEmf().createEntityManager();
        return em.createQuery("from Tache", Tache.class).getResultList();
    }

    // Requêtes spécifiques

    public List<Tache> findTachesByPrixSuperieur(double prix) {
        EntityManager em = HibernateUtil.getEmf().createEntityManager();
        TypedQuery<Tache> query = em.createNamedQuery("findTachesByPrixSuperieur", Tache.class);
        query.setParameter("prix", prix);
        return query.getResultList();
    }

    public List<Tache> findTachesRealiseesBetweenDates(Date date1, Date date2) {
        EntityManager em = HibernateUtil.getEmf().createEntityManager();
        String jpql = "SELECT et.tache FROM EmployeTache et WHERE et.dateFinReelle BETWEEN :date1 AND :date2";
        TypedQuery<Tache> query = em.createQuery(jpql, Tache.class);
        query.setParameter("date1", date1);
        query.setParameter("date2", date2);
        return query.getResultList();
    }
}
