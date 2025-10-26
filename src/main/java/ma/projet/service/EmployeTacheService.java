package ma.projet.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import ma.projet.entities.EmployeTache;
import ma.projet.util.HibernateUtil;

import java.util.List;

public class EmployeTacheService implements IDao<EmployeTache> {

    @Override
    public boolean create(EmployeTache o) {
        EntityManager em = HibernateUtil.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(o); // Utiliser merge pour gérer les entités détachées
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
    public boolean delete(EmployeTache o) {
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
    public boolean update(EmployeTache o) {
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
    public EmployeTache findById(int id) {
        // Non applicable pour une clé composée, mais la méthode est requise par l'interface.
        // On pourrait implémenter une recherche par clé composée si nécessaire.
        throw new UnsupportedOperationException("FindById n'est pas supporté pour EmployeTache. Utilisez une méthode de recherche par clé composée.");
    }

    @Override
    public List<EmployeTache> findAll() {
        EntityManager em = HibernateUtil.getEmf().createEntityManager();
        return em.createQuery("from EmployeTache", EmployeTache.class).getResultList();
    }
}
