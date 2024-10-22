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

        // Opret rammer
        Frame frame1 = new Frame("Canyon", "Carbon", "Disc", 1400, 56);
        Frame frame2 = new Frame("Olmo", "Aluminium", "Rim", 1700, 54);
        Frame frame3 = new Frame("Pinarello", "Carbon", "Disc", 1200, 58);
        Frame frame4 = new Frame("Specialized", "Titanium", "Rim", 1600, 55);
        Frame frame5 = new Frame("Trek", "Carbon", "Disc", 1450, 56);
        Frame frame6 = new Frame("Bianchi", "Carbon", "Disc", 1350, 57);
        Frame frame7 = new Frame("Giant", "Carbon", "Disc", 1300, 54);
        Frame frame8 = new Frame("Scott", "Carbon", "Rim", 1500, 55);
        Frame frame9 = new Frame("Colnago", "Carbon", "Disc", 1550, 58);
        Frame frame10 = new Frame("Felt", "Carbon", "Rim", 1250, 56);
        Frame frame11 = new Frame("Cannondale", "Carbon", "Disc", 1400, 56);
        Frame frame12 = new Frame("Merida", "Aluminium", "Rim", 1650, 54);
        Frame frame13 = new Frame("Salsa", "Steel", "Rim", 1800, 58);
        Frame frame14 = new Frame("BMC", "Carbon", "Disc", 1500, 57);
        Frame frame15 = new Frame("Raleigh", "Aluminium", "Rim", 1750, 54);


        // Opret saddel
        Saddle saddle1 = new Saddle("Fizik", "Carbon", "Arione", 200, 140);
        Saddle saddle2 = new Saddle("Brooks", "Leather", "Classic", 350, 160);
        Saddle saddle3 = new Saddle("Selle Italia", "Leather", "Flite", 300, 155);
        Saddle saddle4 = new Saddle("Specialized", "Carbon", "Power", 250, 143);
        Saddle saddle5 = new Saddle("Prologo", "Carbon", "Dimension", 220, 145);
        Saddle saddle6 = new Saddle("Fabric", "Synthetic", "Scoop", 180, 130);
        Saddle saddle7 = new Saddle("Giant", "Synthetic", "Contact", 160, 145);
        Saddle saddle8 = new Saddle("Trek", "Synthetic", "Bontrager Ajna", 200, 138);
        Saddle saddle9 = new Saddle("ISM", "Foam", "PN 3.0", 250, 130);
        Saddle saddle10 = new Saddle("Selle Royal", "Synthetic", "Lookin", 100, 150);
        Saddle saddle11 = new Saddle("Fi'zi:k", "Leather", "Antares", 230, 145);
        Saddle saddle12 = new Saddle("Ritchey", "Carbon", "WCS", 220, 142);
        Saddle saddle13 = new Saddle("Terry", "Synthetic", "Butterfly", 240, 150);
        Saddle saddle14 = new Saddle("Charge", "Synthetic", "Slice", 190, 140);
        Saddle saddle15 = new Saddle("Selle San Marco", "Leather", "Regale", 260, 145);

        // Opret hjul (vægten repræsenterer et hjulsæt)
        Wheel wheel1 = new Wheel("Zipp", "Carbon", "Disc", 1400, 25);
        Wheel wheel2 = new Wheel("Mavic", "Aluminium", "Rim", 1300, 23);
        Wheel wheel3 = new Wheel("DT Swiss", "Carbon", "Disc", 1600, 28);
        Wheel wheel4 = new Wheel("Fulcrum", "Aluminium", "Rim", 1200, 24);
        Wheel wheel5 = new Wheel("Campagnolo", "Carbon", "Disc", 1800, 30);
        Wheel wheel6 = new Wheel("Shimano", "Aluminium", "Rim", 900, 20);
        Wheel wheel7 = new Wheel("Vision", "Carbon", "Disc", 1500, 26);
        Wheel wheel8 = new Wheel("Easton", "Carbon", "Rim", 1700, 22);
        Wheel wheel9 = new Wheel("Aero", "Aluminium", "Disc", 1400, 23);
        Wheel wheel10 = new Wheel("Nokon", "Carbon", "Disc", 1450, 27);
        Wheel wheel11 = new Wheel("HED", "Carbon", "Disc", 1800, 28);
        Wheel wheel12 = new Wheel("Roval", "Carbon", "Disc", 1600, 25);
        Wheel wheel13 = new Wheel("Rim", "Aluminium", "Rim", 1100, 25);
        Wheel wheel14 = new Wheel("Deda", "Aluminium", "Rim", 1300, 22);
        Wheel wheel15 = new Wheel("Spin", "Carbon", "Disc", 1750, 30);

        // Opret gear
        Gear gear1 = new Gear("Shimano", "Ultegra", "Aluminium", "Electronic", 1422);
        Gear gear2 = new Gear("SRAM", "Red", "Aluminium", "Electronic", 1622);
        Gear gear3 = new Gear("Shimano", "Dura-Ace", "Carbon", "Mechanical", 1600);
        Gear gear4 = new Gear("SRAM", "Force", "Aluminium", "Mechanical", 1400);
        Gear gear5 = new Gear("Campagnolo", "Chorus", "Aluminium", "Mechanical", 1550);
        Gear gear6 = new Gear("Shimano", "105", "Aluminium", "Mechanical", 1200);
        Gear gear7 = new Gear("SRAM", "Apex", "Aluminium", "Mechanical", 1250);
        Gear gear8 = new Gear("Shimano", "Tiagra", "Aluminium", "Mechanical", 1100);
        Gear gear9 = new Gear("Campagnolo", "Record", "Carbon", "Electronic", 1800);
        Gear gear10 = new Gear("Shimano", "GRX", "Aluminium", "Mechanical", 1300);
        Gear gear11 = new Gear("SRAM", "Rival", "Aluminium", "Mechanical", 1500);
        Gear gear12 = new Gear("Shimano", "Zee", "Aluminium", "Mechanical", 1250);
        Gear gear13 = new Gear("Campagnolo", "Super Record", "Carbon", "Electronic", 1900);
        Gear gear14 = new Gear("Shimano", "XTR", "Aluminium", "Mechanical", 1750);
        Gear gear15 = new Gear("SRAM", "NX", "Aluminium", "Mechanical", 1350);

        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            // Persistér alle komponenter først (rammer, saddel og hjul)
            em.persist(frame1);
            em.persist(frame2);
            em.persist(frame3);
            em.persist(frame4);
            em.persist(frame5);
            em.persist(frame6);
            em.persist(frame7);
            em.persist(frame8);
            em.persist(frame9);
            em.persist(frame10);
            em.persist(frame11);
            em.persist(frame12);
            em.persist(frame13);
            em.persist(frame14);
            em.persist(frame15);

            em.persist(saddle1);
            em.persist(saddle2);
            em.persist(saddle3);
            em.persist(saddle4);
            em.persist(saddle5);
            em.persist(saddle6);
            em.persist(saddle7);
            em.persist(saddle8);
            em.persist(saddle9);
            em.persist(saddle10);
            em.persist(saddle11);
            em.persist(saddle12);
            em.persist(saddle13);
            em.persist(saddle14);
            em.persist(saddle15);

            em.persist(wheel1);
            em.persist(wheel2);
            em.persist(wheel3);
            em.persist(wheel4);
            em.persist(wheel5);
            em.persist(wheel6);
            em.persist(wheel7);
            em.persist(wheel8);
            em.persist(wheel9);
            em.persist(wheel10);
            em.persist(wheel11);
            em.persist(wheel12);
            em.persist(wheel13);
            em.persist(wheel14);
            em.persist(wheel15);

            em.persist(gear1);
            em.persist(gear2);
            em.persist(gear3);
            em.persist(gear4);
            em.persist(gear5);
            em.persist(gear6);
            em.persist(gear7);
            em.persist(gear8);
            em.persist(gear9);
            em.persist(gear10);
            em.persist(gear11);
            em.persist(gear12);
            em.persist(gear13);
            em.persist(gear14);
            em.persist(gear15);

            // Opret fem cykler med tilhørende komponenter
            Bicycle bicycle1 = new Bicycle("Trek", "Domane SL6", 56, 3500, "High-performance road bike", frame5, gear1, wheel1, saddle1);
            Bicycle bicycle2 = new Bicycle("Canyon", "Ultimate CF SLX", 54, 4000, "Lightweight racing bike", frame1, gear1, wheel2, saddle2);
            Bicycle bicycle3 = new Bicycle("Pinarello", "Dogma F12", 58, 5000, "Aero road bike", frame3, gear1, wheel1, saddle1);
            Bicycle bicycle4 = new Bicycle("Giant", "TCR Advanced", 55, 3200, "Endurance race bike", frame7, gear1, wheel2, saddle2);
            Bicycle bicycle5 = new Bicycle("Bianchi", "Oltre XR4", 57, 4800, "High-end performance bike", frame6, gear2, wheel1, saddle1);
            Bicycle bicycle6 = new Bicycle("Specialized", "S-Works Tarmac SL7", 56, 8000, "Ultra-lightweight race bike", frame4, gear1, wheel1, saddle1);
            Bicycle bicycle7 = new Bicycle("Colnago", "C64", 58, 7000, "Iconic Italian road bike", frame9, gear3, wheel1, saddle1);
            Bicycle bicycle8 = new Bicycle("Cannondale", "SuperSix EVO", 54, 4000, "Racer with great aerodynamics", frame10, gear1, wheel1, saddle2);
            Bicycle bicycle9 = new Bicycle("Felt", "FR FRD", 55, 6000, "High-performance road bike", frame8, gear2, wheel2, saddle1);
            Bicycle bicycle10 = new Bicycle("Scott", "Foil RC", 56, 5500, "Aerodynamic racing bike", frame8, gear1, wheel1, saddle2);
            Bicycle bicycle11 = new Bicycle("Canyon", "Aeroad CF SLX", 56, 4400, "Aerodynamic road bike", frame1, gear1, wheel1, saddle2);
            Bicycle bicycle12 = new Bicycle("Trek", "Madone SLR 9", 58, 8500, "Lightweight and aerodynamic", frame5, gear3, wheel1, saddle1);
            Bicycle bicycle13 = new Bicycle("Specialized", "Diverge Pro", 54, 3200, "Versatile gravel bike", frame4, gear2, wheel2, saddle3);
            Bicycle bicycle14 = new Bicycle("Giant", "Defy Advanced", 55, 3000, "Comfortable endurance bike", frame7, gear6, wheel3, saddle4);
            Bicycle bicycle15 = new Bicycle("BMC", "Teammachine SLR01", 56, 7000, "Ultimate racing machine", frame14, gear1, wheel1, saddle5);

            em.persist(bicycle1);
            em.persist(bicycle2);
            em.persist(bicycle3);
            em.persist(bicycle4);
            em.persist(bicycle5);
            em.persist(bicycle6);
            em.persist(bicycle7);
            em.persist(bicycle8);
            em.persist(bicycle9);
            em.persist(bicycle10);
            em.persist(bicycle11);
            em.persist(bicycle12);
            em.persist(bicycle13);
            em.persist(bicycle14);
            em.persist(bicycle15);

            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error populating database", e);
        }
    }

}
