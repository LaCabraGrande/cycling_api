package dat.config;

import dat.entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class Populate {

    public static void main(String[] args) {
        // Opret en instans af Populate-klassen og kald populateDatabase()
//        Populate populate = new Populate();
        populateDatabase();
    }

    // Metode til at populere databasen med cykler og komponenter
    public static void populateDatabase() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

        // Opret rammer
        Frame frame1 = new Frame("Canyon", "Carbon", "Disc", 1200, 56);
        Frame frame2 = new Frame("Olmo", "Aluminium", "Rim", 1600, 54);
        Frame frame3 = new Frame("Pinarello", "Carbon", "Disc", 1200, 58);
        Frame frame4 = new Frame("Specialized", "Titanium", "Rim", 1600, 55);
        Frame frame5 = new Frame("Trek", "Carbon", "Disc", 1300, 56);  //
        Frame frame6 = new Frame("Bianchi", "Carbon", "Disc", 1350, 57);
        Frame frame7 = new Frame("Giant", "Carbon", "Disc", 1250, 54);
        Frame frame8 = new Frame("Scott", "Carbon", "Rim", 1300, 55);
        Frame frame9 = new Frame("Colnago", "Carbon", "Disc", 1400, 58);
        Frame frame10 = new Frame("Felt", "Carbon", "Rim", 1250, 56);
        Frame frame11 = new Frame("Cannondale", "Carbon", "Disc", 1300, 56);
        Frame frame12 = new Frame("Merida", "Aluminium", "Rim", 1600, 54);
        Frame frame13 = new Frame("Salsa", "Steel", "Rim", 1800, 58);
        Frame frame14 = new Frame("BMC", "Carbon", "Disc", 1300, 57);
        Frame frame15 = new Frame("Raleigh", "Aluminium", "Rim", 1650, 54);
        Frame frame16 = new Frame("Marin", "Aluminium", "Disc", 1500, 55);
        Frame frame17 = new Frame("Fuji", "Carbon", "Rim", 1450, 56);
        Frame frame18 = new Frame("Niner", "Aluminium", "Rim", 1600, 54);
        Frame frame19 = new Frame("Kona", "Steel", "Disc", 1700, 57);
        Frame frame20 = new Frame("Orbea", "Carbon", "Disc", 1300, 58);
        Frame frame21 = new Frame("Ridley", "Carbon", "Disc", 1400, 57);
        Frame frame22 = new Frame("Cervelo", "Carbon", "Disc", 1500, 56);
        Frame frame23 = new Frame("Argon 18", "Carbon", "Rim", 1350, 55);
        Frame frame24 = new Frame("Focus", "Carbon", "Disc", 1300, 56);
        Frame frame25 = new Frame("Wilier", "Carbon", "Rim", 1450, 58);
        Frame frame26 = new Frame("Canyon", "Aluminium", "Rim", 1600, 54);
        Frame frame27 = new Frame("De Rosa", "Steel", "Rim", 1700, 57);
        Frame frame28 = new Frame("S-Works", "Carbon", "Disc", 1200, 56);
        Frame frame29 = new Frame("Look", "Carbon", "Disc", 1400, 58);
        Frame frame30 = new Frame("Time", "Carbon", "Rim", 1350, 55);
        Frame frame31 = new Frame("BH", "Carbon", "Disc", 1300, 57);
        Frame frame32 = new Frame("Santa Cruz", "Aluminium", "Disc", 1500, 56);
        Frame frame33 = new Frame("Yeti", "Carbon", "Disc", 1600, 57);
        Frame frame34 = new Frame("3T", "Carbon", "Rim", 1450, 56);
        Frame frame35 = new Frame("Pivot", "Carbon", "Disc", 1550, 55);
        Frame frame36 = new Frame("Cotic", "Steel", "Disc", 1700, 58);
        Frame frame37 = new Frame("Allied", "Carbon", "Disc", 1350, 56);
        Frame frame38 = new Frame("Diamondback", "Aluminium", "Rim", 1500, 54);
        Frame frame39 = new Frame("Surly", "Steel", "Disc", 1800, 56);
        Frame frame40 = new Frame("Ritchey", "Steel", "Rim", 1700, 58);


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
        Saddle saddle16 = new Saddle("SDG", "Synthetic", "Fly", 220, 145);
        Saddle saddle17 = new Saddle("Bontrager", "Synthetic", "Aeolus", 200, 142);
        Saddle saddle18 = new Saddle("WTB", "Synthetic", "Volt", 180, 140);
        Saddle saddle19 = new Saddle("Ergon", "Synthetic", "SME3", 160, 145);
        Saddle saddle20 = new Saddle("SQlab", "Synthetic", "611", 190, 140);
        Saddle saddle21 = new Saddle("Fabric", "Synthetic", "Line", 170, 145);
        Saddle saddle22 = new Saddle("Prologo", "Synthetic", "Nago", 150, 140);
        Saddle saddle23 = new Saddle("Selle Italia", "Synthetic", "SLR", 200, 145);
        Saddle saddle24 = new Saddle("Fizik", "Synthetic", "Antares", 180, 140);
        Saddle saddle25 = new Saddle("Brooks", "Leather", "Cambium", 250, 160);
        Saddle saddle26 = new Saddle("Selle Royal", "Foam", "Freeway", 120, 150);
        Saddle saddle27 = new Saddle("Giant", "Leather", "Fleet", 230, 145);
        Saddle saddle28 = new Saddle("Specialized", "Synthetic", "Henge", 200, 150);
        Saddle saddle29 = new Saddle("Fizik", "Carbon", "Tundra", 270, 145);
        Saddle saddle30 = new Saddle("Selle Italia", "Carbon", "SLR Tekno", 400, 130);
        Saddle saddle31 = new Saddle("Bontrager", "Synthetic", "H1", 180, 138);
        Saddle saddle32 = new Saddle("Prologo", "Leather", "Dimension", 300, 145);
        Saddle saddle33 = new Saddle("Ritchey", "Carbon", "Comp", 240, 140);
        Saddle saddle34 = new Saddle("Charge", "Foam", "Velo", 160, 140);
        Saddle saddle35 = new Saddle("WTB", "Leather", "Deva", 260, 155);
        Saddle saddle36 = new Saddle("SDG", "Synthetic", "Radar", 190, 145);
        Saddle saddle37 = new Saddle("Terry", "Foam", "Fly", 220, 150);
        Saddle saddle38 = new Saddle("SQlab", "Synthetic", "612", 200, 140);
        Saddle saddle39 = new Saddle("Selle San Marco", "Synthetic", "Concor", 250, 145);
        Saddle saddle40 = new Saddle("ISM", "Leather", "PL 1.1", 300, 130);
        Saddle saddle41 = new Saddle("Fizik", "Carbon", "Arione 00", 350, 140);
        Saddle saddle42 = new Saddle("Brooks", "Leather", "Swift", 300, 160);
        Saddle saddle43 = new Saddle("Prologo", "Carbon", "X10", 230, 145);
        Saddle saddle44 = new Saddle("Fabric", "Synthetic", "Shallow", 180, 130);
        Saddle saddle45 = new Saddle("Selle Italia", "Foam", "X3", 150, 145);



        // Opret hjul med modeller
        Wheel wheel1 = new Wheel("Zipp", "Carbon", "Disc", "404 Firecrest", 1400, 25);
        Wheel wheel2 = new Wheel("Mavic", "Aluminium", "Rim", "Ksyrium Pro", 1300, 23);
        Wheel wheel3 = new Wheel("DT Swiss", "Carbon", "Disc", "PRC 1400", 1600, 28);
        Wheel wheel4 = new Wheel("Fulcrum", "Aluminium", "Rim", "Racing 3", 1200, 24);
        Wheel wheel5 = new Wheel("Campagnolo", "Carbon", "Disc", "Bora Ultra", 1800, 30);
        Wheel wheel6 = new Wheel("Shimano", "Aluminium", "Rim", "WH-RS100", 900, 20);
        Wheel wheel7 = new Wheel("Vision", "Carbon", "Disc", "Metron 40", 1500, 26);
        Wheel wheel8 = new Wheel("Easton", "Carbon", "Rim", "EA90", 1700, 22);
        Wheel wheel9 = new Wheel("Aero", "Aluminium", "Disc", "Aero 50", 1400, 23);
        Wheel wheel10 = new Wheel("Nokon", "Carbon", "Disc", "F5", 1450, 27);
        Wheel wheel11 = new Wheel("HED", "Carbon", "Disc", "Belgium Plus", 1800, 28);
        Wheel wheel12 = new Wheel("Roval", "Carbon", "Disc", "CLX 32", 1600, 25);
        Wheel wheel13 = new Wheel("Rim", "Aluminium", "Rim", "Rim 400", 1100, 25);
        Wheel wheel14 = new Wheel("Deda", "Aluminium", "Rim", "Zero 100", 1300, 22);
        Wheel wheel15 = new Wheel("Spin", "Carbon", "Disc", "Lightweight", 1750, 30);
        Wheel wheel16 = new Wheel("Reynolds", "Carbon", "Disc", "ASSAULT", 1600, 28);
        Wheel wheel17 = new Wheel("Bora", "Carbon", "Rim", "One", 1500, 25);
        Wheel wheel18 = new Wheel("Fulcrum", "Aluminium", "Rim", "Racing 5", 1200, 24);
        Wheel wheel19 = new Wheel("Shimano", "Carbon", "Disc", "Dura-Ace R9170", 1600, 26);
        Wheel wheel20 = new Wheel("Campagnolo", "Aluminium", "Rim", "Zonda", 1300, 23);
        Wheel wheel21 = new Wheel("Mavic", "Carbon", "Disc", "Cosmic Pro", 1550, 26);
        Wheel wheel22 = new Wheel("DT Swiss", "Aluminium", "Rim", "M 1900", 1350, 24);
        Wheel wheel23 = new Wheel("Zipp", "Carbon", "Rim", "303 Firecrest", 1700, 28);
        Wheel wheel24 = new Wheel("Fulcrum", "Carbon", "Disc", "Racing Quattro", 1800, 29);
        Wheel wheel25 = new Wheel("Shimano", "Aluminium", "Disc", "WH-RS500", 950, 22);
        Wheel wheel26 = new Wheel("Vision", "Aluminium", "Rim", "Team 30", 1250, 23);
        Wheel wheel27 = new Wheel("Easton", "Carbon", "Disc", "EA90", 1400, 26);
        Wheel wheel28 = new Wheel("Aero", "Carbon", "Rim", "Aero 60", 1650, 25);
        Wheel wheel29 = new Wheel("Nokon", "Aluminium", "Disc", "Nokon 1", 1100, 21);
        Wheel wheel30 = new Wheel("HED", "Aluminium", "Rim", "HED Jet", 1300, 24);
        Wheel wheel31 = new Wheel("Roval", "Aluminium", "Disc", "Alpinist", 1200, 23);
        Wheel wheel32 = new Wheel("Campagnolo", "Carbon", "Rim", "Bullet", 1750, 30);
        Wheel wheel33 = new Wheel("Reynolds", "Aluminium", "Rim", "Attaque", 1400, 25);
        Wheel wheel34 = new Wheel("Bora", "Aluminium", "Disc", "Bora WTO", 1300, 23);
        Wheel wheel35 = new Wheel("Spin", "Aluminium", "Rim", "Spin 4", 1250, 22);
        Wheel wheel36 = new Wheel("Fulcrum", "Carbon", "Rim", "Racing 6", 1550, 27);
        Wheel wheel37 = new Wheel("Shimano", "Carbon", "Rim", "Dura-Ace C50", 1500, 24);
        Wheel wheel38 = new Wheel("Mavic", "Carbon", "Rim", "Cosmic SL", 1600, 26);
        Wheel wheel39 = new Wheel("DT Swiss", "Carbon", "Disc", "EX 1501", 1750, 30);
        Wheel wheel40 = new Wheel("Zipp", "Aluminium", "Rim", "302", 1400, 22);



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
        Gear gear16 = new Gear("Shimano", "SLX", "Aluminium", "Mechanical", 1500);
        Gear gear17 = new Gear("SRAM", "GX", "Aluminium", "Mechanical", 1400);
        Gear gear18 = new Gear("Campagnolo", "Athena", "Aluminium", "Mechanical", 1600);
        Gear gear19 = new Gear("Shimano", "Deore", "Aluminium", "Mechanical", 1300);
        Gear gear20 = new Gear("SRAM", "XX1", "Carbon", "Electronic", 1850);
        Gear gear21 = new Gear("Shimano", "Alivio", "Aluminium", "Mechanical", 1200);
        Gear gear22 = new Gear("Campagnolo", "Veloce", "Aluminium", "Mechanical", 1400);
        Gear gear23 = new Gear("SRAM", "X01", "Carbon", "Electronic", 1800);
        Gear gear24 = new Gear("Shimano", "Acera", "Aluminium", "Mechanical", 1250);
        Gear gear25 = new Gear("Campagnolo", "Centaur", "Aluminium", "Mechanical", 1500);
        Gear gear26 = new Gear("SRAM", "X1", "Aluminium", "Mechanical", 1450);
        Gear gear27 = new Gear("Shimano", "Altus", "Aluminium", "Mechanical", 1200);
        Gear gear28 = new Gear("Campagnolo", "Potenza", "Aluminium", "Mechanical", 1550);
        Gear gear29 = new Gear("SRAM", "X01 Eagle", "Carbon", "Electronic", 1850);
        Gear gear30 = new Gear("Shimano", "Tourney", "Aluminium", "Mechanical", 1100);
        Gear gear31 = new Gear("Campagnolo", "Chorus", "Carbon", "Electronic", 1700);
        Gear gear32 = new Gear("SRAM", "X01 Eagle AXS", "Carbon", "Electronic", 1900);
        Gear gear33 = new Gear("Shimano", "Deore XT", "Aluminium", "Mechanical", 1400);
        Gear gear34 = new Gear("SRAM", "X1 Eagle", "Carbon", "Electronic", 1850);
        Gear gear35 = new Gear("Shimano", "GRX 800", "Aluminium", "Mechanical", 1350);
        Gear gear36 = new Gear("Campagnolo", "Super Record EPS", "Carbon", "Electronic", 2000);
        Gear gear37 = new Gear("Shimano", "Tiagra 4700", "Aluminium", "Mechanical", 1150);
        Gear gear38 = new Gear("SRAM", "Rival 22", "Aluminium", "Mechanical", 1600);
        Gear gear39 = new Gear("Shimano", "Dura-Ace 9100", "Carbon", "Mechanical", 1750);
        Gear gear40 = new Gear("Campagnolo", "Athena EPS", "Carbon", "Electronic", 1800);
        Gear gear41 = new Gear("Shimano", "SLX M7100", "Aluminium", "Mechanical", 1400);
        Gear gear42 = new Gear("SRAM", "XX1 Eagle AXS", "Carbon", "Electronic", 1950);
        Gear gear43 = new Gear("Shimano", "XTR M9100", "Carbon", "Mechanical", 1850);
        Gear gear44 = new Gear("Campagnolo", "Record EPS", "Carbon", "Electronic", 1900);
        Gear gear45 = new Gear("Shimano", "105 R7000", "Aluminium", "Mechanical", 1250);
        Gear gear46 = new Gear("SRAM", "NX Eagle", "Aluminium", "Mechanical", 1300);
        Gear gear47 = new Gear("Shimano", "Zee M645", "Aluminium", "Mechanical", 1350);
        Gear gear48 = new Gear("Campagnolo", "Centaur EPS", "Carbon", "Electronic", 1650);
        Gear gear49 = new Gear("Shimano", "Acera M310", "Aluminium", "Mechanical", 1200);
        Gear gear50 = new Gear("SRAM", "GX Eagle", "Aluminium", "Mechanical", 1450);



        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            // Gemmer rammer
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
            em.persist(frame16);
            em.persist(frame17);
            em.persist(frame18);
            em.persist(frame19);
            em.persist(frame20);
            em.persist(frame21);
            em.persist(frame22);
            em.persist(frame23);
            em.persist(frame24);
            em.persist(frame25);
            em.persist(frame26);
            em.persist(frame27);
            em.persist(frame28);
            em.persist(frame29);
            em.persist(frame30);
            em.persist(frame31);
            em.persist(frame32);
            em.persist(frame33);
            em.persist(frame34);
            em.persist(frame35);
            em.persist(frame36);
            em.persist(frame37);
            em.persist(frame38);
            em.persist(frame39);
            em.persist(frame40);

            // Gemmer saddel
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
            em.persist(saddle16);
            em.persist(saddle17);
            em.persist(saddle18);
            em.persist(saddle19);
            em.persist(saddle20);
            em.persist(saddle21);
            em.persist(saddle22);
            em.persist(saddle23);
            em.persist(saddle24);
            em.persist(saddle25);
            em.persist(saddle26);
            em.persist(saddle27);
            em.persist(saddle28);
            em.persist(saddle29);
            em.persist(saddle30);
            em.persist(saddle31);
            em.persist(saddle32);
            em.persist(saddle33);
            em.persist(saddle34);
            em.persist(saddle35);
            em.persist(saddle36);
            em.persist(saddle37);
            em.persist(saddle38);
            em.persist(saddle39);
            em.persist(saddle40);
            em.persist(saddle41);
            em.persist(saddle42);
            em.persist(saddle43);
            em.persist(saddle44);
            em.persist(saddle45);

            // Gemmer hjul
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
            em.persist(wheel16);
            em.persist(wheel17);
            em.persist(wheel18);
            em.persist(wheel19);
            em.persist(wheel20);
            em.persist(wheel21);
            em.persist(wheel22);
            em.persist(wheel23);
            em.persist(wheel24);
            em.persist(wheel25);
            em.persist(wheel26);
            em.persist(wheel27);
            em.persist(wheel28);
            em.persist(wheel29);
            em.persist(wheel30);
            em.persist(wheel31);
            em.persist(wheel32);
            em.persist(wheel33);
            em.persist(wheel34);
            em.persist(wheel35);
            em.persist(wheel36);
            em.persist(wheel37);
            em.persist(wheel38);
            em.persist(wheel39);
            em.persist(wheel40);

            // Gemmer gear
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
            em.persist(gear16);
            em.persist(gear17);
            em.persist(gear18);
            em.persist(gear19);
            em.persist(gear20);
            em.persist(gear21);
            em.persist(gear22);
            em.persist(gear23);
            em.persist(gear24);
            em.persist(gear25);
            em.persist(gear26);
            em.persist(gear27);
            em.persist(gear28);
            em.persist(gear29);
            em.persist(gear30);
            em.persist(gear31);
            em.persist(gear32);
            em.persist(gear33);
            em.persist(gear34);
            em.persist(gear35);
            em.persist(gear36);
            em.persist(gear37);
            em.persist(gear38);
            em.persist(gear39);
            em.persist(gear40);
            em.persist(gear41);
            em.persist(gear42);
            em.persist(gear43);
            em.persist(gear44);
            em.persist(gear45);
            em.persist(gear46);
            em.persist(gear47);
            em.persist(gear48);
            em.persist(gear49);
            em.persist(gear50);


            // Opretter cykler og tilføjer komponenter
            Bicycle bicycle1 = new Bicycle("Trek", "Domane SL6", 56, 3500, 8.7, "High-performance road bike", frame5, gear1, wheel1, saddle1);
            Bicycle bicycle2 = new Bicycle("Canyon", "Ultimate CF SLX", 54, 4000, 6.8, "Lightweight racing bike", frame1, gear1, wheel2, saddle2);
            Bicycle bicycle3 = new Bicycle("Pinarello", "Dogma F12", 58, 5000, 7.6, "Aero road bike", frame3, gear1, wheel1, saddle1);
            Bicycle bicycle4 = new Bicycle("Giant", "TCR Advanced", 55, 3200, 7.8, "Endurance race bike", frame7, gear1, wheel2, saddle2);
            Bicycle bicycle5 = new Bicycle("Bianchi", "Oltre XR4", 57, 4800, 7.4, "High-end performance bike", frame6, gear2, wheel1, saddle1);
            Bicycle bicycle6 = new Bicycle("Specialized", "S-Works Tarmac SL7", 56, 8000, 6.7, "Ultra-lightweight race bike", frame4, gear1, wheel1, saddle1);
            Bicycle bicycle7 = new Bicycle("Colnago", "C64", 58, 7000, 7.2, "Iconic Italian road bike", frame9, gear3, wheel1, saddle1);
            Bicycle bicycle8 = new Bicycle("Cannondale", "SuperSix EVO", 54, 4000, 7.5, "Racer with great aerodynamics", frame10, gear1, wheel1, saddle2);
            Bicycle bicycle9 = new Bicycle("Felt", "FR FRD", 55, 6000, 6.9, "High-performance road bike", frame8, gear2, wheel2, saddle1);
            Bicycle bicycle10 = new Bicycle("Scott", "Foil RC", 56, 5500, 7.6, "Aerodynamic racing bike", frame8, gear1, wheel1, saddle2);
            Bicycle bicycle11 = new Bicycle("Canyon", "Aeroad CF SLX", 56, 4400, 7.3, "Aerodynamic road bike", frame1, gear1, wheel1, saddle2);
            Bicycle bicycle12 = new Bicycle("Trek", "Madone SLR 9", 58, 8500, 7.7, "Lightweight and aerodynamic", frame5, gear3, wheel1, saddle1);
            Bicycle bicycle13 = new Bicycle("Specialized", "Diverge Pro", 54, 3200, 8.2, "Versatile gravel bike", frame4, gear2, wheel2, saddle3);
            Bicycle bicycle14 = new Bicycle("Giant", "Defy Advanced", 55, 3000, 7.9, "Comfortable endurance bike", frame7, gear6, wheel3, saddle4);
            Bicycle bicycle15 = new Bicycle("BMC", "Teammachine SLR01", 56, 7000, 6.5, "Ultimate racing machine", frame14, gear1, wheel1, saddle5);
            Bicycle bicycle16 = new Bicycle("Cervélo", "R5", 58, 6500, 7.1, "Lightweight", frame15, gear1, wheel1, saddle6);
            Bicycle bicycle17 = new Bicycle("Canyon", "Ultimate CF SL", 54, 2500, 8.5, "Lightweight", frame16, gear1, wheel1, saddle7);
            Bicycle bicycle18 = new Bicycle("Trek", "Emonda SL", 55, 2800, 7.8, "Lightweight", frame17, gear1, wheel1, saddle8);
            Bicycle bicycle19 = new Bicycle("Specialized", "Allez Sprint", 56, 2000, 8.2, "Lightweight", frame18, gear1, wheel1, saddle9);
            Bicycle bicycle20 = new Bicycle("Giant", "Contend", 58, 1500, 8.7, "Lightweight", frame19, gear1, wheel1, saddle10);
            Bicycle bicycle21 = new Bicycle("BMC", "Roadmachine", 56, 3500, 7.5, "Lightweight", frame20, gear1, wheel1, saddle11);
            Bicycle bicycle22 = new Bicycle("Cervélo", "R3", 56, 4800, 7.0, "Versatile racing bike", frame15, gear2, wheel1, saddle6);
            Bicycle bicycle23 = new Bicycle("Trek", "Checkpoint ALR 5", 54, 2200, 8.1, "All-road bike", frame17, gear4, wheel3, saddle3);
            Bicycle bicycle24 = new Bicycle("Specialized", "Turbo Vado SL", 56, 3000, 9.0, "Electric hybrid bike", frame4, gear2, wheel2, saddle2);
            Bicycle bicycle25 = new Bicycle("Giant", "Trance X 29", 55, 3400, 8.4, "Trail bike", frame7, gear5, wheel1, saddle4);
            Bicycle bicycle26 = new Bicycle("Canyon", "Neuro CC", 54, 2800, 7.9, "Trail mountain bike", frame16, gear6, wheel3, saddle7);
            Bicycle bicycle27 = new Bicycle("Bianchi", "Pista", 58, 1700, 9.5, "Track bike", frame6, gear1, wheel2, saddle8);
            Bicycle bicycle28 = new Bicycle("Pinarello", "Paris", 56, 5900, 7.3, "Touring bike", frame3, gear1, wheel1, saddle9);
            Bicycle bicycle29 = new Bicycle("Cannondale", "CAAD13", 55, 2700, 7.6, "Lightweight alloy bike", frame10, gear2, wheel2, saddle10);
            Bicycle bicycle30 = new Bicycle("Colnago", "V3Rs", 54, 4200, 6.8, "Aerodynamic road bike", frame9, gear3, wheel1, saddle5);
            Bicycle bicycle31 = new Bicycle("Scott", "Addict RC", 56, 6200, 7.0, "Performance road bike", frame8, gear1, wheel1, saddle2);
            Bicycle bicycle32 = new Bicycle("Giant", "Reign", 58, 3000, 8.9, "All-mountain bike", frame7, gear4, wheel1, saddle3);
            Bicycle bicycle33 = new Bicycle("Trek", "FX 3 Disc", 55, 800, 9.5, "Hybrid fitness bike", frame17, gear6, wheel3, saddle4);
            Bicycle bicycle34 = new Bicycle("Cervélo", "S5", 54, 6500, 6.6, "Aerodynamic race bike", frame15, gear2, wheel1, saddle1);
            Bicycle bicycle35 = new Bicycle("BMC", "Fourstroke", 56, 5200, 7.4, "Cross-country mountain bike", frame20, gear1, wheel2, saddle5);
            Bicycle bicycle36 = new Bicycle("Specialized", "Stumpjumper", 56, 4500, 7.2, "Trail mountain bike", frame4, gear4, wheel3, saddle3);
            Bicycle bicycle37 = new Bicycle("Giant", "Defy Advanced Pro", 55, 4200, 7.1, "Endurance bike", frame7, gear3, wheel1, saddle6);
            Bicycle bicycle38 = new Bicycle("Pinarello", "Bolide", 58, 9000, 6.5, "Aero time trial bike", frame3, gear1, wheel1, saddle1);
            Bicycle bicycle39 = new Bicycle("Canyon", "Spectral", 54, 3400, 8.3, "All-mountain bike", frame16, gear5, wheel2, saddle4);
            Bicycle bicycle40 = new Bicycle("Colnago", "C64 Disc", 56, 7900, 7.0, "Premium road bike", frame9, gear2, wheel1, saddle5);
            Bicycle bicycle41 = new Bicycle("Felt", "VR FRD", 55, 4200, 7.2, "Endurance road bike", frame8, gear2, wheel2, saddle3);
            Bicycle bicycle42 = new Bicycle("Giant", "Talon", 54, 900, 8.5, "Entry-level mountain bike", frame7, gear6, wheel3, saddle4);
            Bicycle bicycle43 = new Bicycle("Bianchi", "Impulso", 56, 2900, 7.9, "Versatile road bike", frame6, gear3, wheel1, saddle6);
            Bicycle bicycle44 = new Bicycle("Specialized", "Roubaix", 58, 5000, 6.9, "Comfort road bike", frame4, gear1, wheel1, saddle2);
            Bicycle bicycle45 = new Bicycle("Cervélo", "S3", 56, 5500, 6.4, "Aerodynamic racing bike", frame15, gear2, wheel1, saddle5);
            Bicycle bicycle46 = new Bicycle("Cannondale", "Bad Boy", 55, 1000, 8.1, "Urban commuter bike", frame10, gear4, wheel2, saddle3);
            Bicycle bicycle47 = new Bicycle("Scott", "Scale", 56, 3700, 8.8, "Cross-country mountain bike", frame8, gear5, wheel1, saddle7);
            Bicycle bicycle48 = new Bicycle("Trek", "Fuel EX", 54, 4300, 8.4, "Trail bike", frame17, gear3, wheel3, saddle2);
            Bicycle bicycle49 = new Bicycle("Colnago", "C59", 58, 6400, 6.5, "High-performance road bike", frame9, gear1, wheel1, saddle1);
            Bicycle bicycle50 = new Bicycle("BMC", "Teammachine", 56, 7200, 6.8, "Racing bike", frame20, gear2, wheel1, saddle8);
            Bicycle bicycle51 = new Bicycle("Giant", "Explore", 55, 1500, 8.6, "Adventure bike", frame7, gear5, wheel3, saddle4);
            Bicycle bicycle52 = new Bicycle("Felt", "F5", 56, 2900, 8.2, "Lightweight racing bike", frame8, gear1, wheel2, saddle6);
            Bicycle bicycle53 = new Bicycle("Cervélo", "P5", 58, 9500, 6.3, "Time trial bike", frame15, gear1, wheel1, saddle3);
            Bicycle bicycle54 = new Bicycle("Specialized", "Crossroads", 56, 750, 9.0, "Hybrid bike for commuting", frame4, gear6, wheel2, saddle7);
            Bicycle bicycle55 = new Bicycle("Pinarello", "F10", 54, 7500, 6.7, "High-performance racing bike", frame3, gear2, wheel1, saddle5);
            Bicycle bicycle56 = new Bicycle("Trek", "Speed Concept", 56, 8500, 6.2, "Triathlon bike", frame17, gear1, wheel1, saddle8);
            Bicycle bicycle57 = new Bicycle("Giant", "Defy", 55, 3300, 7.5, "Endurance bike", frame7, gear3, wheel2, saddle4);
            Bicycle bicycle58 = new Bicycle("Canyon", "Strive", 54, 4200, 8.6, "All-mountain bike", frame16, gear5, wheel1, saddle3);
            Bicycle bicycle59 = new Bicycle("Colnago", "V3Rs Disc", 56, 8000, 6.9, "Aero road bike", frame9, gear2, wheel1, saddle6);
            Bicycle bicycle60 = new Bicycle("Bianchi", "Cameleonte", 54, 1100, 8.8, "Versatile city bike", frame6, gear4, wheel3, saddle7);

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
            em.persist(bicycle16);
            em.persist(bicycle17);
            em.persist(bicycle18);
            em.persist(bicycle19);
            em.persist(bicycle20);
            em.persist(bicycle21);
            em.persist(bicycle22);
            em.persist(bicycle23);
            em.persist(bicycle24);
            em.persist(bicycle25);
            em.persist(bicycle26);
            em.persist(bicycle27);
            em.persist(bicycle28);
            em.persist(bicycle29);
            em.persist(bicycle30);
            em.persist(bicycle31);
            em.persist(bicycle32);
            em.persist(bicycle33);
            em.persist(bicycle34);
            em.persist(bicycle35);
            em.persist(bicycle36);
            em.persist(bicycle37);
            em.persist(bicycle38);
            em.persist(bicycle39);
            em.persist(bicycle40);
            em.persist(bicycle41);
            em.persist(bicycle42);
            em.persist(bicycle43);
            em.persist(bicycle44);
            em.persist(bicycle45);
            em.persist(bicycle46);
            em.persist(bicycle47);
            em.persist(bicycle48);
            em.persist(bicycle49);
            em.persist(bicycle50);
            em.persist(bicycle51);
            em.persist(bicycle52);
            em.persist(bicycle53);
            em.persist(bicycle54);
            em.persist(bicycle55);
            em.persist(bicycle56);
            em.persist(bicycle57);
            em.persist(bicycle58);
            em.persist(bicycle59);
            em.persist(bicycle60);

            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error populating database", e);
        }
    }

}
