package dat.daos.impl;

import dat.config.HibernateConfig;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PlantCenterDAOTest {

    private static BicycleDAO plantCenterDAO;
    private static FrameDAO plantDAO;
    private static EntityManagerFactory emf;
    private ResellerDTO resellerDTO;
    private Reseller reseller;
    private Plant plant;
    private PlantDTO plantDTO;

    @BeforeAll
    public static void setUp() {
        emf = HibernateConfig.getEntityManagerFactoryForTest();
        plantCenterDAO = BicycleDAO.getInstance(emf);
        plantDAO = FrameDAO.getInstance(emf);
    }

    @AfterAll
    public static void tearDown() {
        if (emf != null) {
            emf.close();
        }
    }

    @BeforeEach
    public void init() {
        // Her opretter jeg en Reseller
        reseller = new Reseller();
        reseller.setName("Hansens Gartneri");
        reseller.setAddress("Strandvejen 154");
        reseller.setPhone("83746354");
        resellerDTO = plantCenterDAO.addReseller(new ResellerDTO(reseller));

        // Her opretter jeg en Plant
        plant = new Plant();
        plant.setPlantName("Aronia");
        plant.setPlantType("BUSH");
        plant.setPlantHeight(250);
        plant.setPlantPrice(132.0);
        plantDTO = plantDAO.add(new PlantDTO(plant)); // Tilføj plant til databasen
    }

    @AfterEach
    public void cleanup() {
        // Ryd op, f.eks. slet reseller og plant fra databasen
        if (resellerDTO != null) {
            try {
                plantCenterDAO.deleteReseller(resellerDTO.getId());
            } catch (Exception e) {
                System.err.println("Fejl ved sletning af reseller: " + e.getMessage());
            }
        }
        if (plantDTO != null) {
            try {
                plantDAO.delete(plantDTO.getId());
            } catch (Exception e) {
                System.err.println("Fejl ved sletning af plant: " + e.getMessage());
            }
        }
    }

    @Test
    public void testAddReseller() {
        ResellerDTO newReseller = new ResellerDTO();
        newReseller.setName("Mortensens Haveudsalg");
        newReseller.setAddress("Jyderupvej 345");
        newReseller.setPhone("87654321");

        ResellerDTO createdReseller = plantCenterDAO.addReseller(newReseller);
        assertNotNull(createdReseller);
        assertEquals(newReseller.getName(), createdReseller.getName());
    }

    @Test
    public void testGetResellerById() {
        ResellerDTO fetchedReseller = plantCenterDAO.getResellerById(resellerDTO.getId());
        assertNotNull(fetchedReseller);
        assertEquals(resellerDTO.getName(), fetchedReseller.getName());
    }

    @Test
    public void testGetAllResellers() {
        List<ResellerDTO> resellers = plantCenterDAO.getAllResellers();
        assertFalse(resellers.isEmpty());
    }

    @Test
    public void testAddPlantToReseller() {
        // Tilføj plant til reseller
        plantCenterDAO.addPlantToReseller(resellerDTO.getId(), plantDTO.getId());

        ResellerDTO updatedReseller = plantCenterDAO.getResellerById(resellerDTO.getId());
        assertNotNull(updatedReseller);
        assertTrue(updatedReseller.getPlants().contains(plantDTO));
    }

    @Test
    public void testGetPlantsByReseller() {
        // Først tilføj plant til reseller
        plantCenterDAO.addPlantToReseller(resellerDTO.getId(), plantDTO.getId());

        List<Plant> plants = plantCenterDAO.getPlantsByReseller(resellerDTO.getId());
        assertFalse(plants.isEmpty());
        assertEquals(1, plants.size());
        assertEquals(plantDTO.getPlantName(), plants.get(0).getPlantName());
    }

    @Test
    public void testUpdateReseller() {
        ResellerDTO updatedResellerDTO = new ResellerDTO();
        updatedResellerDTO.setName("Viebjergs Roser");
        updatedResellerDTO.setAddress("Hellerupvej 291");
        updatedResellerDTO.setPhone("31439897");

        ResellerDTO updatedReseller = plantCenterDAO.updateReseller(resellerDTO.getId(), updatedResellerDTO);
        assertNotNull(updatedReseller);
        assertEquals(updatedResellerDTO.getName(), updatedReseller.getName());
    }

    @Test
    public void testDeleteReseller() {
        // Først opret en ny reseller til test
        ResellerDTO newReseller = new ResellerDTO();
        newReseller.setName("Henriks Træer og Buske");
        newReseller.setAddress("Amager Landevej 321");
        newReseller.setPhone("27291912");

        ResellerDTO createdReseller = plantCenterDAO.addReseller(newReseller);
        assertNotNull(createdReseller);

        // Slet reselleren
        plantCenterDAO.deleteReseller(createdReseller.getId());

        // Bekræft at reselleren ikke længere kan findes
        assertNull(plantCenterDAO.getResellerById(createdReseller.getId()));
    }

    @Test
    public void testDeletePlant() {
        // Først opret en ny plant til test
        Plant newPlant = new Plant();
        newPlant.setPlantName("Aronia");
        newPlant.setPlantType("BUSH");
        newPlant.setPlantHeight(230);
        newPlant.setPlantPrice(150.0);

        PlantDTO newPlantDTO = new PlantDTO();
        newPlantDTO = plantDAO.add(new PlantDTO(newPlant));

        assertNotNull(newPlantDTO);

        // Slet planten
        plantDAO.delete(newPlantDTO.getId());

        // Bekræft at planten ikke længere kan findes
        assertNull(plantDAO.getById(newPlantDTO.getId()));
    }
}
