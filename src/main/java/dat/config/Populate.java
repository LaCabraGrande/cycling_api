package dat.config;

import dat.entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class Populate {

    public static void main(String[] args) {
        // Opret en instans af Populate-klassen og kald populateDatabase()
        Populate populate = new Populate();
        populate.populateDatabase();
    }

    // Metode til at populere databasen med cykler og komponenter
    public void populateDatabase() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

        // Opret rammer, saddel og hjul til cyklerne
        Frame frame1 = new Frame("Canyon", "Carbon", "Disc",  1400);
        Frame frame2 = new Frame("Olmo", "Aluminium", "Rim",  1700);
        Frame frame3 = new Frame("Pinarello", "Carbon", "Disc",  1200);
        Frame frame4 = new Frame("Specialized", "Titanium", "Rim",  1600);
        Frame frame5 = new Frame("Trek", "Carbon", "Disc",  1450);

        Saddle saddle1 = new Saddle("Fizik", "Carbon", "Arione", 200);
        Saddle saddle2 = new Saddle("Brooks", "Leather", "Classic", 350);

        Wheel wheel1 = new Wheel("Zipp", "Carbon", "Disc", 700);
        Wheel wheel2 = new Wheel("Mavic", "Aluminium", "Rim", 650);

        Gear gear1 = new Gear("Shimano", "Ultegra", "Aluminium", "Electronic",  1422);
        Gear gear2 = new Gear("SRAM", "Red", "Aluminium", "Electronic", 1622);


        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            // Persistér alle komponenter først (rammer, saddel og hjul)
            em.persist(frame1);
            em.persist(frame2);
            em.persist(frame3);
            em.persist(frame4);
            em.persist(frame5);

            em.persist(saddle1);
            em.persist(saddle2);

            em.persist(wheel1);
            em.persist(wheel2);

            // Opret fem cykler med tilhørende komponenter
            Bicycle bicycle1 = new Bicycle("Trek", "Domane SL6", "56cm", 3500, "High-performance road bike", frame1, gear1, wheel1, saddle1);
            Bicycle bicycle2 = new Bicycle("Canyon", "Ultimate CF SLX", "54cm", 4000, "Lightweight racing bike", frame2, gear1, wheel2, saddle1);
            Bicycle bicycle3 = new Bicycle("Pinarello", "Dogma F12", "58cm", 5000, "Aero road bike", frame3, gear1, wheel1, saddle1);
            Bicycle bicycle4 = new Bicycle("Giant", "TCR Advanced", "55cm", 3200, "Endurance race bike", frame4, gear1, wheel2, saddle2);
            Bicycle bicycle5 = new Bicycle("Bianchi", "Oltre XR4", "57cm", 4800, "High-end performance bike", frame5, gear2, wheel1, saddle1);

            // Persistér alle cykler
            em.persist(bicycle1);
            em.persist(bicycle2);
            em.persist(bicycle3);
            em.persist(bicycle4);
            em.persist(bicycle5);

            em.getTransaction().commit();
        }
    }
}
