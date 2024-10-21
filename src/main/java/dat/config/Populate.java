package dat.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.jetbrains.annotations.NotNull;
import java.util.Set;

public class Populate {

    public static void main(String[] args) {
        // Opret en instans af Populate-klassen og kald populateDatabase()
        Populate populate = new Populate();
        populate.populateDatabase();
    }

    // Metode til at populere databasen
    public void populateDatabase() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

        Set<Plant> hansenPlants = getHansenPlants();
        Set<Plant> jensenPlants = getJensenPlants();

        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Reseller hansen = new Reseller("Hansens Gartneri", "Kongevejen 230", "78639121");
            Reseller jensen = new Reseller("Jensens Træer og Buske", "Gammel Køge Landevej 210", "34726354");

            hansen.setPlants(hansenPlants);
            jensen.setPlants(jensenPlants);

            // Persist begge Resellers
            em.persist(hansen);
            em.persist(jensen);

            em.getTransaction().commit();
        }
    }

    @NotNull
    private static Set<Plant> getHansenPlants() {
        Plant plant1 = new Plant("ROSE", "Albertine", 400, 199.50);
        Plant plant2 = new Plant("BUSH", "Lavatera", 150, 99.99);
        Plant plant3 = new Plant("TREE", "Acer Palmatum", 500, 350.75);
        Plant plant4 = new Plant("FLOWER", "Dahlia Bishop of Llandaff", 120, 79.00);
        Plant plant5 = new Plant("SHRUB", "Hydrangea Macrophylla", 200, 149.99);

        Plant[] plantArray = {plant1, plant2, plant3, plant4, plant5};
        return Set.of(plantArray);
    }

    @NotNull
    private static Set<Plant> getJensenPlants() {
        Plant plant6 = new Plant("ROSE", "Peace", 300, 220.00);
        Plant plant7 = new Plant("BUSH", "Forsythia", 180, 89.50);
        Plant plant8 = new Plant("TREE", "Quercus Robur", 600, 400.00);
        Plant plant9 = new Plant("FLOWER", "Tulipa Queen of Night", 50, 45.75);
        Plant plant10 = new Plant("SHRUB", "Rhododendron", 250, 175.25);

        Plant[] plantArray = {plant6, plant7, plant8, plant9, plant10};
        return Set.of(plantArray);
    }
}
