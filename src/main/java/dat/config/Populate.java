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
        Frame frame1 = new Frame("Allied", "Echo", "Carbon", "Disc", 1350, 57);
        Frame frame2 = new Frame("Argon 18", "Nitrogen Pro", "Carbon", "Rim", 1400, 52);
        Frame frame3 = new Frame("Argon 18", "Gallium", "Aluminium", "Disc", 1700, 56);
        Frame frame4 = new Frame("Argon 18", "Gallium Pro", "Carbon", "Rim", 1350, 55);
        Frame frame5 = new Frame("Argon 18", "Krypton X", "Carbon", "Rim", 1550, 56);
        Frame frame6 = new Frame("BH", "G7 Disc", "Carbon", "Disc", 1350, 54);
        Frame frame7 = new Frame("BH", "G8 Pro", "Carbon", "Disc", 1300, 57);
        Frame frame8 = new Frame("Bianchi", "Intenso Disc", "Aluminium", "Disc", 1600, 57);
        Frame frame9 = new Frame("Bianchi", "Oltre XR4", "Carbon", "Disc", 1350, 57);
        Frame frame10 = new Frame("BMC", "Teammachine SLR", "Carbon", "Disc", 1300, 57);
        Frame frame11 = new Frame("BMC", "Roadmachine 01", "Carbon", "Disc", 1350, 56);
        Frame frame12 = new Frame("Cannondale", "Synapse Carbon", "Carbon", "Disc", 1450, 57);
        Frame frame13 = new Frame("Cannondale", "F-Si Carbon", "Carbon", "Disc", 1250, 54);
        Frame frame14 = new Frame("Cannondale", "Bad Boy", "Aluminium", "Disc", 1300, 52);
        Frame frame15 = new Frame("Cannondale", "Jekyll", "Aluminium", "Disc", 1600, 58);
        Frame frame16 = new Frame("Cannondale", "Supersix EVO", "Carbon", "Disc", 1300, 56);
        Frame frame17 = new Frame("Canyon", "Endurace AL Disc", "Aluminium", "Disc", 1200, 56);
        Frame frame18 = new Frame("Canyon", "Endurace CF SLX", "Carbon", "Rim", 1400, 58);
        Frame frame19 = new Frame("Canyon", "Aeroad CF SLX", "Carbon", "Disc", 1450, 52);
        Frame frame20 = new Frame("Canyon", "Ultimate AL Disc", "Aluminium", "Disc", 1300, 58);
        Frame frame21 = new Frame("Canyon", "Endurace AL SL", "Aluminium", "Rim", 1250, 54);
        Frame frame22 = new Frame("Canyon", "Aeroad AL Disc", "Aluminium", "Disc", 1400, 56);
        Frame frame23 = new Frame("Canyon", "Endurace CF SL", "Carbon", "Disc", 1400, 56);
        Frame frame24 = new Frame("Canyon", "Ultimate CF SLX", "Carbon", "Disc", 1355, 56);
        Frame frame25 = new Frame("Cervelo", "R5", "Carbon", "Disc", 1500, 56);
        Frame frame26 = new Frame("Cervelo", "R3 Disc", "Carbon", "Disc", 1450, 58);
        Frame frame27 = new Frame("Cervelo", "Caledonia", "Carbon", "Disc", 1500, 57);
        Frame frame28 = new Frame("Colnago", "C64", "Carbon", "Disc", 1400, 58);
        Frame frame29 = new Frame("Colnago", "C64", "Carbon", "Rim", 1350, 58);
        Frame frame30 = new Frame("De Rosa", "Merak", "Steel", "Rim", 1700, 57);
        Frame frame31 = new Frame("Felt", "FR2", "Carbon", "Rim", 1250, 56);
        Frame frame32 = new Frame("Felt", "VR3", "Carbon", "Disc", 1250, 54);
        Frame frame33 = new Frame("Focus", "Izalco Max", "Carbon", "Disc", 1300, 56);
        Frame frame34 = new Frame("Focus", "Paralane", "Carbon", "Disc", 1300, 55);
        Frame frame35 = new Frame("Fuji", "SL 1.1", "Carbon", "Rim", 1450, 56);
        Frame frame36 = new Frame("Giant", "TCR Advanced Pro", "Carbon", "Rim", 1250, 56);
        Frame frame37 = new Frame("Giant", "Defy Advanced Pro", "Carbon", "Disc", 1300, 56);
        Frame frame38 = new Frame("Kona", "Rove DL", "Steel", "Disc", 1700, 57);
        Frame frame39 = new Frame("Look", "795 Aerolight", "Carbon", "Disc", 1400, 58);
        Frame frame40 = new Frame("Look", "785 Huez", "Carbon", "Disc", 1400, 55);
        Frame frame41 = new Frame("Marin", "Gestalt X10", "Aluminium", "Disc", 1500, 55);
        Frame frame42 = new Frame("Merida", "Scultura", "Aluminium", "Rim", 1600, 54);
        Frame frame43 = new Frame("Merida", "Reacto", "Carbon", "Rim", 1500, 58);
        Frame frame44 = new Frame("Olmo", "Racing Aluminium", "Aluminium", "Rim", 1600, 54);
        Frame frame45 = new Frame("Orbea", "Orca M21e", "Carbon", "Disc", 1300, 58);
        Frame frame46 = new Frame("Orbea", "Avant M20", "Aluminium", "Rim", 1600, 57);
        Frame frame47 = new Frame("Pinarello", "Dogma F12", "Carbon", "Disc", 1200, 58);
        Frame frame48 = new Frame("Pinarello", "Prince", "Carbon", "Rim", 1550, 54);
        Frame frame49 = new Frame("Raleigh", "Aero 700", "Aluminium", "Rim", 1650, 54);
        Frame frame50 = new Frame("Ridley", "Fenix SL", "Carbon", "Disc", 1400, 57);
        Frame frame51 = new Frame("Ridley", "Helium SLX", "Carbon", "Rim", 1450, 58);
        Frame frame52 = new Frame("Ritchey", "SwissCross", "Steel", "Rim", 1700, 58);
        Frame frame53 = new Frame("Ritchey", "Road Logic", "Steel", "Rim", 1700, 54);
        Frame frame54 = new Frame("Scott", "Addict RC", "Carbon", "Rim", 1300, 55);
        Frame frame55 = new Frame("Scott", "Foil RC", "Carbon", "Rim", 1400, 56);
        Frame frame56 = new Frame("Specialized", "S-Works Tarmac", "Titanium", "Rim", 1600, 55);
        Frame frame57 = new Frame("Specialized", "Roubaix Pro", "Carbon", "Disc", 1450, 56);
        Frame frame58 = new Frame("Specialized", "S-Works Venge", "Carbon", "Disc", 1400, 54);
        Frame frame59 = new Frame("Specialized", "Allez Sprint", "Aluminium", "Rim", 1200, 56);
        Frame frame60 = new Frame("Specialized", "Diverge Pro", "Carbon", "Disc", 1350, 58);
        Frame frame61 = new Frame("Specialized", "Tarmac SL7", "Carbon", "Rim", 1500, 52);
        Frame frame62 = new Frame("Specialized", "Roubaix Sport", "Carbon", "Disc", 1300, 58);
        Frame frame63 = new Frame("Specialized", "Turbo Levo SL", "Aluminium", "Disc", 1600, 54);
        Frame frame64 = new Frame("Time", "Alpe D'Huez 01", "Carbon", "Rim", 1350, 55);
        Frame frame65 = new Frame("Trek", "500 Series OCLV Carbon","Carbon", "Disc", 1500, 56);
        Frame frame66 = new Frame("Trek", "Emonda SLR 9", "Carbon", "Disc", 1300, 56);
        Frame frame67 = new Frame("Trek", "Madone SL 6", "Carbon", "Disc", 1550, 56);
        Frame frame68 = new Frame("Wilier", "Cento 10 NDR", "Carbon", "Rim", 1450, 58);
        Frame frame69 = new Frame("Wilier", "Zero SLR", "Carbon", "Disc", 1350, 56);
        Frame frame70 = new Frame("Wilier", "Centos 10", "Carbon", "Rim", 1500, 58);
        Frame frame71 = new Frame("Giant", "ALUXX-Grade", "Aluminum", "Rim", 1250, 56);
        Frame frame72 = new Frame("Cervelo", "All-Carbon Tapered", "Carbon", "Disc", 1500, 56);
        Frame frame73 = new Frame("Pinarello", "Dogma F10", "Carbon", "Disc", 1200, 58);
        Frame frame74 = new Frame("Colnago", "V3Rs", "Carbon", "Disc", 1400, 58);






        // Opret saddel
        Saddle saddle1 = new Saddle("Fi'zi:k", "Carbon", "Arione", 200, 140);
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
        Saddle saddle14 = new Saddle("Selle Italia", "Foam", "X3", 150, 145);
        Saddle saddle15 = new Saddle("Selle San Marco", "Leather", "Regale", 260, 145);
        Saddle saddle16 = new Saddle("SDG", "Synthetic", "Fly", 220, 145);
        Saddle saddle17 = new Saddle("Bontrager", "Synthetic", "Aeolus", 200, 142);
        Saddle saddle18 = new Saddle("WTB", "Synthetic", "Volt", 180, 140);
        Saddle saddle19 = new Saddle("Ergon", "Synthetic", "SME3", 160, 145);
        Saddle saddle20 = new Saddle("SQlab", "Synthetic", "611", 190, 140);
        Saddle saddle21 = new Saddle("Fabric", "Synthetic", "Line", 170, 145);
        Saddle saddle22 = new Saddle("Prologo", "Synthetic", "Nago", 150, 140);
        Saddle saddle23 = new Saddle("Selle Italia", "Synthetic", "SLR", 200, 145);
        Saddle saddle24 = new Saddle("Fi'zi:k", "Synthetic", "Antares", 180, 140);
        Saddle saddle25 = new Saddle("Brooks", "Leather", "Cambium", 250, 160);
        Saddle saddle26 = new Saddle("Selle Royal", "Foam", "Freeway", 120, 150);
        Saddle saddle27 = new Saddle("Giant", "Leather", "Fleet", 230, 145);
        Saddle saddle28 = new Saddle("Specialized", "Synthetic", "Henge", 200, 150);
        Saddle saddle29 = new Saddle("Fi'zi:k", "Carbon", "Tundra", 270, 145);
        Saddle saddle30 = new Saddle("Selle Italia", "Carbon", "SLR Tekno", 400, 130);
        Saddle saddle31 = new Saddle("Bontrager", "Synthetic", "H1", 180, 138);
        Saddle saddle32 = new Saddle("Prologo", "Leather", "Dimension", 300, 145);
        Saddle saddle33 = new Saddle("Ritchey", "Carbon", "Comp", 240, 140);
        Saddle saddle34 = new Saddle("Fi'zi:k", "Carbon", "Arione 00", 350, 140);
        Saddle saddle35 = new Saddle("Brooks", "Leather", "Swift", 300, 160);
        Saddle saddle36 = new Saddle("Colnago", "Carbon", "Prologo X10", 230, 145);
        Saddle saddle37 = new Saddle("Fabric", "Synthetic", "Shallow", 180, 130);
        Saddle saddle38 = new Saddle("SQlab", "Synthetic", "612", 200, 140);
        Saddle saddle39 = new Saddle("Selle San Marco", "Synthetic", "Concor", 250, 145);
        Saddle saddle40 = new Saddle("ISM", "Leather", "PL 1.1", 300, 130);
        Saddle saddle41 = new Saddle("Bontrager", "Foam", "Verse Comp", 307, 155);
        Saddle saddle42 = new Saddle("Selle Italia", "Synthetic", "SLR Boost Superflow Kit", 120, 130);


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
        Wheel wheel15 = new Wheel("Spinergy", "Carbon", "Disc", "FCC 32", 1597, 18);
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
        Wheel wheel26 = new Wheel("Fulcrum", "Aluminium", "Rim", "Racing Zero", 1490, 23);
        Wheel wheel27 = new Wheel("Easton", "Carbon", "Disc", "EA90", 1400, 26);
        Wheel wheel28 = new Wheel("Syncros", "Carbon", "Rim", "Aero 60", 1650, 25);
        Wheel wheel29 = new Wheel("Shimano", "Carbon", "Disc", "Dura-Ace R9270", 1350, 21);
        Wheel wheel30 = new Wheel("HED", "Aluminium", "Rim", "HED Jet", 1300, 24);
        Wheel wheel31 = new Wheel("Roval", "Aluminium", "Disc", "Alpinist", 1200, 23);
        Wheel wheel32 = new Wheel("Campagnolo", "Carbon", "Rim", "Bullet", 1750, 30);
        Wheel wheel33 = new Wheel("Reynolds", "Aluminium", "Rim", "Attaque", 1400, 25);
        Wheel wheel34 = new Wheel("Bora", "Aluminium", "Disc", "Bora WTO", 1300, 23);
        Wheel wheel35 = new Wheel("Spinergy", "Carbon", "Disc", "FCC 47", 1794, 23);
        Wheel wheel36 = new Wheel("Fulcrum", "Carbon", "Rim", "Racing 6", 1550, 27);
        Wheel wheel37 = new Wheel("Shimano", "Carbon", "Rim", "Dura-Ace C50", 1500, 24);
        Wheel wheel38 = new Wheel("Mavic", "Carbon", "Rim", "Cosmic SL", 1600, 26);
        Wheel wheel39 = new Wheel("DT Swiss", "Carbon", "Disc", "EX 1501", 1750, 30);
        Wheel wheel40 = new Wheel("Zipp", "Aluminium", "Rim", "302", 1400, 22);
        Wheel wheel41 = new Wheel("Bontrager", "Aluminium", "Rim", "Bontrager Paradigm Comp 25", 1400, 25);


        // Opret gear
        Gear gear1 = new Gear("Shimano", "Ultegra Di2 RD-R8170", "Aluminium", "Electronic", 2520);
        Gear gear2 = new Gear("SRAM", "Red", "Aluminium", "Electronic", 1622);
        Gear gear3 = new Gear("Shimano", "Dura-Ace Di2 RD-R9270", "Carbon", "Electronic", 2320);
        Gear gear4 = new Gear("SRAM", "Force", "Aluminium", "Mechanical", 1400);
        Gear gear5 = new Gear("Campagnolo", "Chorus", "Aluminium", "Mechanical", 1550);
        Gear gear6 = new Gear("Shimano", "Dura-Ace Di2 RD-R9250", "Carbon", "Electronic", 2340);
        Gear gear7 = new Gear("SRAM", "Apex", "Aluminium", "Mechanical", 1250);
        Gear gear8 = new Gear("Shimano", "Tiagra", "Aluminium", "Mechanical", 1100);
        Gear gear9 = new Gear("Campagnolo", "Record", "Carbon", "Electronic", 1800);
        Gear gear10 = new Gear("Campagnolo", "Athena", "Aluminium", "Mechanical", 1600);
        Gear gear11 = new Gear("SRAM", "Rival", "Aluminium", "Mechanical", 1500);
        Gear gear12 = new Gear("Campagnolo", "Super Record", "Carbon", "Electronic", 1900);
        Gear gear13 = new Gear("Shimano", "105 R7000", "Aluminium", "Mechanical", 1250);
        Gear gear14 = new Gear("Shimano", "Ultegra R8100", "Aluminium", "Mechanical", 2520);
        Gear gear15 = new Gear("Shimano", "105 Di2 RD-R7170", "Aluminium", "Electronic", 2820);
        Gear gear16 = new Gear("Shimano", "105 R7100", "Aluminium", "Mechanical", 2620);
        Gear gear17 = new Gear("Campagnolo", "Veloce", "Aluminium", "Mechanical", 1400);
        Gear gear18 = new Gear("Campagnolo", "Centaur", "Aluminium", "Mechanical", 1500);
        Gear gear19 = new Gear("Campagnolo", "Potenza", "Aluminium", "Mechanical", 1550);
        Gear gear20 = new Gear("Campagnolo", "Chorus", "Carbon", "Electronic", 1700);
        Gear gear21 = new Gear("Campagnolo", "Super Record EPS", "Carbon", "Electronic", 2000);
        Gear gear22 = new Gear("Shimano", "Tiagra 4700", "Aluminium", "Mechanical", 1150);
        Gear gear23 = new Gear("Campagnolo", "Centaur EPS", "Carbon", "Electronic", 1650);
        Gear gear24 = new Gear("Shimano", "Dura-Ace 9100", "Carbon", "Mechanical", 1750);
        Gear gear25 = new Gear("Campagnolo", "Athena EPS", "Carbon", "Electronic", 1800);
        Gear gear26 = new Gear("Campagnolo", "Record EPS", "Carbon", "Electronic", 1900);



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
            em.persist(frame41);
            em.persist(frame42);
            em.persist(frame43);
            em.persist(frame44);
            em.persist(frame45);
            em.persist(frame46);
            em.persist(frame47);
            em.persist(frame48);
            em.persist(frame49);
            em.persist(frame50);
            em.persist(frame51);
            em.persist(frame52);
            em.persist(frame53);
            em.persist(frame54);
            em.persist(frame55);
            em.persist(frame56);
            em.persist(frame57);
            em.persist(frame58);
            em.persist(frame59);
            em.persist(frame60);
            em.persist(frame61);
            em.persist(frame62);
            em.persist(frame63);
            em.persist(frame64);
            em.persist(frame65);
            em.persist(frame66);
            em.persist(frame67);
            em.persist(frame68);
            em.persist(frame69);
            em.persist(frame70);
            em.persist(frame71);
            em.persist(frame72);
            em.persist(frame73);
            em.persist(frame74);



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
            em.persist(wheel41);


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


            // Opretter cykler og tilføjer komponenter
            Bicycle bicycle1 = new Bicycle("Trek", "Domane SL6", 56, 3500, 9.3, "Endurance road bike", frame66, gear1, wheel41, saddle41);
            Bicycle bicycle2 = new Bicycle("Canyon", "Ultimate CF SLX 9 Di2", 54, 9000, 6.8, "Lightweight racing bike", frame18, gear6, wheel28, saddle42);
            Bicycle bicycle3 = new Bicycle("Pinarello", "Dogma F12", 58, 5000, 7.6, "Aero road bike", frame47, gear1, wheel1, saddle1);
            Bicycle bicycle4 = new Bicycle("Giant", "TCR Advanced", 55, 3200, 7.8, "Endurance race bike", frame36, gear1, wheel2, saddle2);
            Bicycle bicycle5 = new Bicycle("Bianchi", "Oltre XR4", 57, 4800, 7.4, "High-end performance bike", frame9, gear2, wheel1, saddle1);
            Bicycle bicycle6 = new Bicycle("Specialized", "S-Works Tarmac SL7", 56, 8000, 6.7, "Ultra-lightweight race bike", frame61, gear1, wheel1, saddle1);
            Bicycle bicycle7 = new Bicycle("Colnago", "C64", 58, 7000, 7.2, "Iconic Italian road bike", frame9, gear3, wheel1, saddle1);
            Bicycle bicycle8 = new Bicycle("Cannondale", "SuperSix EVO", 54, 4000, 7.5, "Aero road bike", frame16, gear1, wheel1, saddle2);
            Bicycle bicycle9 = new Bicycle("Felt", "FR FRD", 55, 6000, 6.9, "Road bike", frame31, gear2, wheel2, saddle1);
            Bicycle bicycle10 = new Bicycle("Scott", "Foil RC", 56, 5500, 7.6, "Aero Road bike", frame55, gear1, wheel1, saddle2);
            Bicycle bicycle11 = new Bicycle("Canyon", "Aeroad CF SLX", 56, 4400, 7.3, "Aerodynamic road bike", frame19, gear1, wheel1, saddle2);
            Bicycle bicycle12 = new Bicycle("Trek", "Madone SLR 9", 58, 8500, 7.7, "Lightweight and aerodynamic", frame66, gear3, wheel1, saddle1);
            Bicycle bicycle13 = new Bicycle("Specialized", "Diverge Pro", 54, 3200, 8.2, "Versatile gravel bike", frame60, gear2, wheel2, saddle3);
            Bicycle bicycle14 = new Bicycle("Giant", "Defy Advanced", 55, 3000, 7.9, "Endurance bike", frame37, gear6, wheel3, saddle4);
            Bicycle bicycle15 = new Bicycle("BMC", "Teammachine SLR01", 56, 7000, 6.5, "Aero road bike", frame10, gear1, wheel1, saddle5);
            Bicycle bicycle16 = new Bicycle("Cervélo", "R5", 58, 6500, 7.1, "Lightweight", frame25, gear1, wheel1, saddle6);
            Bicycle bicycle17 = new Bicycle("Canyon", "Ultimate CF SL", 54, 2500, 8.5, "Lightweight", frame23, gear1, wheel1, saddle7);
            Bicycle bicycle18 = new Bicycle("Trek", "Emonda SL", 55, 2800, 7.8, "Lightweight", frame67, gear1, wheel1, saddle8);
            Bicycle bicycle19 = new Bicycle("Specialized", "Allez Sprint", 56, 2000, 8.2, "Lightweight", frame59, gear1, wheel1, saddle9);
            Bicycle bicycle20 = new Bicycle("Giant", "Contend", 58, 1500, 8.7, "Lightweight", frame71, gear1, wheel1, saddle10);
            Bicycle bicycle21 = new Bicycle("BMC", "Roadmachine", 56, 3500, 7.5, "Lightweight", frame11, gear1, wheel1, saddle11);
            Bicycle bicycle22 = new Bicycle("Cervélo", "R3", 56, 4800, 7.0, "Versatile racing bike", frame26, gear2, wheel1, saddle6);
            Bicycle bicycle23 = new Bicycle("Colnago", "V3Rs Disc", 56, 8000, 6.9, "Aero road bike", frame74, gear2, wheel1, saddle6);
            Bicycle bicycle24 = new Bicycle("Cervélo", "S3", 56, 5500, 6.4, "Aero road bike", frame15, gear2, wheel1, saddle5);
            Bicycle bicycle25 = new Bicycle("Colnago", "C59", 58, 6400, 6.5, "High-performance road bike", frame28, gear1, wheel1, saddle1);
            Bicycle bicycle26 = new Bicycle("Colnago", "C64 Disc", 56, 7900, 7.0, "Premium road bike", frame29, gear2, wheel1, saddle5);
            Bicycle bicycle27 = new Bicycle("Cervélo", "S5", 54, 6500, 6.6, "Aerodynamic race bike", frame72, gear2, wheel1, saddle1);
            Bicycle bicycle28 = new Bicycle("Pinarello", "F10", 54, 7500, 6.7, "High-performance racing bike", frame73, gear2, wheel1, saddle5);
            Bicycle bicycle29 = new Bicycle("Pinarello", "Paris", 56, 5900, 7.3, "Touring bike", frame48, gear1, wheel1, saddle9);
            Bicycle bicycle30 = new Bicycle("Scott", "Addict RC", 56, 6200, 7.0, "Performance road bike", frame54, gear1, wheel1, saddle2);
            Bicycle bicycle31 = new Bicycle("Canyon", "Endurace", 55, 3200, 7.8, "Endurance road bike", frame18, gear3, wheel2, saddle4);
            Bicycle bicycle32 = new Bicycle("Giant", "Defy Advanced Pro", 55, 4200, 7.1, "Endurance bike", frame37, gear3, wheel1, saddle6);
            Bicycle bicycle33 = new Bicycle("Felt", "F5", 56, 2900, 8.2, "Lightweight racing bike", frame32, gear1, wheel2, saddle6);
            Bicycle bicycle34 = new Bicycle("Giant", "Defy", 55, 3300, 7.5, "Endurance bike", frame37, gear3, wheel2, saddle4);
            Bicycle bicycle35 = new Bicycle("Bianchi", "Impulso", 56, 2900, 7.9, "Versatile road bike", frame8, gear3, wheel1, saddle6);
            Bicycle bicycle36 = new Bicycle("Specialized", "Roubaix", 58, 5000, 6.9, "Endurance road bike", frame57, gear1, wheel1, saddle2);
            Bicycle bicycle37 = new Bicycle("Felt", "VR FRD", 55, 4200, 7.2, "Endurance road bike", frame31, gear2, wheel2, saddle3);




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



            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error populating database", e);
        }
    }

}
