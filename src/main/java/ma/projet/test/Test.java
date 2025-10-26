package ma.projet.test;

import ma.projet.entities.*;
import ma.projet.service.*;
import ma.projet.util.HibernateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        // Initialisation des services
        EmployeService es = new EmployeService();
        ProjetService ps = new ProjetService();
        TacheService ts = new TacheService();
        EmployeTacheService ets = new EmployeTacheService();

        // Format de date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            // --- Création des données de test ---

            // 1. Créer un employé (chef de projet)
            Employe chef = new Employe("IDRISSI", "Mohammed", "0666666666");
            es.create(chef);

            // 2. Créer un projet
            Projet projet = new Projet("Gestion de stock", sdf.parse("2013-01-14"), chef);
            ps.create(projet);

            // 3. Créer les tâches
            Tache t1 = new Tache("Analyse", sdf.parse("2013-02-10"), sdf.parse("2013-02-20"), 1000.0, projet);
            Tache t2 = new Tache("Conception", sdf.parse("2013-03-10"), sdf.parse("2013-03-15"), 2000.0, projet);
            Tache t3 = new Tache("Développement", sdf.parse("2013-04-10"), sdf.parse("2013-04-25"), 3000.0, projet);
            ts.create(t1);
            ts.create(t2);
            ts.create(t3);
            
            // 4. Assigner les tâches à l'employé avec des dates réelles
            EmployeTachePK pk1 = new EmployeTachePK(chef.getId(), t1.getId());
            EmployeTache et1 = new EmployeTache(pk1, sdf.parse("2013-02-10"), sdf.parse("2013-02-20"));
            et1.setEmploye(chef);
            et1.setTache(t1);
            ets.create(et1);

            EmployeTachePK pk2 = new EmployeTachePK(chef.getId(), t2.getId());
            EmployeTache et2 = new EmployeTache(pk2, sdf.parse("2013-03-10"), sdf.parse("2013-03-15"));
            et2.setEmploye(chef);
            et2.setTache(t2);
            ets.create(et2);

            EmployeTachePK pk3 = new EmployeTachePK(chef.getId(), t3.getId());
            EmployeTache et3 = new EmployeTache(pk3, sdf.parse("2013-04-10"), sdf.parse("2013-04-25"));
            et3.setEmploye(chef);
            et3.setTache(t3);
            ets.create(et3);


            // --- Affichage du projet et des tâches ---
            System.out.println("\n--- Affichage du projet et des tâches ---");
            ps.afficherTachesRealisees(projet.getId());


        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Fermer l'EntityManagerFactory
        HibernateUtil.closeEmf();
    }
}
