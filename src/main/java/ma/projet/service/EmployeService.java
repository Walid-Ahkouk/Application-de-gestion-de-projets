package ma.projet.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import ma.projet.entities.Employe;
import ma.projet.entities.Projet;
import ma.projet.entities.Tache;
import ma.projet.util.HibernateUtil;

import java.util.List;

public class EmployeService implements IDao<Employe> {

    @Override
    public boolean create(Employe o) {
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
    public boolean delete(Employe o) {
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
    public boolean update(Employe o) {
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
    public Employe findById(int id) {
        EntityManager em = HibernateUtil.getEmf().createEntityManager();
        return em.find(Employe.class, id);
    }

    @Override
    public List<Employe> findAll() {
        EntityManager em = HibernateUtil.getEmf().createEntityManager();
        return em.createQuery("from Employe", Employe.class).getResultList();
    }

    // Requêtes spécifiques

    public List<Tache> findTachesByEmployeId(int employeId) {
        EntityManager em = HibernateUtil.getEmf().createEntityManager();
        String jpql = "SELECT et.tache FROM EmployeTache et WHERE et.employe.id = :employeId";
        TypedQuery<Tache> query = em.createQuery(jpql, Tache.class);
        query.setParameter("employeId", employeId);
        return query.getResultList();
    }

    public List<Projet> findProjetsGeresByEmployeId(int employeId) {
        EntityManager em = HibernateUtil.getEmf().createEntityManager();
        String jpql = "SELECT p FROM Projet p WHERE p.employe.id = :employeId";
        TypedQuery<Projet> query = em.createQuery(jpql, Projet.class);
        query.setParameter("employeId", employeId);
        return query.getResultList();
    }
}
