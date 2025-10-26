package ma.projet.util;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class HibernateUtil {

    private static EntityManagerFactory emf;

    public static EntityManagerFactory getEmf() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("UP_PROJECT");
        }
        return emf;
    }

    public static void closeEmf() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
