package dat.daos.impl;

import dat.dtos.BicycleDTO;
import dat.entities.Bicycle;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class BicycleDAO {

    private static BicycleDAO instance;
    private static EntityManagerFactory emf;

    public static BicycleDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new BicycleDAO();
        }
        return instance;
    }

    public BicycleDTO add(BicycleDTO bicycleDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Bicycle bicycle = new Bicycle(bicycleDTO);
            em.persist(bicycle);
            em.getTransaction().commit();
            return new BicycleDTO(bicycle);  // Returner DTO
        }
    }

    public ResellerDTO getResellerById(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            Reseller reseller = em.find(Reseller.class, id);
            return reseller != null ? new ResellerDTO(reseller) : null;  // Returner DTO
        }
    }

    public List<ResellerDTO> getAllResellers() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<ResellerDTO> query = em.createQuery("SELECT new dat.dtos.ResellerDTO(r) FROM Reseller r", ResellerDTO.class);
            return query.getResultList();
        }
    }

    public Reseller addPlantToReseller(int resellerId, int plantId) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Reseller reseller = em.find(Reseller.class, resellerId);
            Plant plant = em.find(Plant.class, plantId);

            if (reseller != null && plant != null) {
                reseller.getPlants().add(plant);
                plant.getResellers().add(reseller);
                em.merge(reseller);
                em.merge(plant);

                em.getTransaction().commit();
                return reseller;
            } else {
                em.getTransaction().rollback();
                return null;  // Hvis enten reseller eller plant ikke findes returnerer jeg null
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Plant> getPlantsByReseller(int resellerId) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Plant> query = em.createQuery(
                    "SELECT p FROM Plant p JOIN p.resellers r WHERE r.id = :resellerId", Plant.class);
            query.setParameter("resellerId", resellerId);
            return query.getResultList();
        }
    }

    public ResellerDTO updateReseller(int id, ResellerDTO resellerDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Reseller reseller = em.find(Reseller.class, id);
            reseller.setName(resellerDTO.getName());
            reseller.setAddress(resellerDTO.getAddress());
            reseller.setPhone(resellerDTO.getPhone());
            Reseller mergedReseller = em.merge(reseller);
            em.getTransaction().commit();
            return mergedReseller != null ? new ResellerDTO(mergedReseller) : null;
        }
    }

    public Reseller deleteReseller(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Reseller reseller = em.find(Reseller.class, id);
            if (reseller != null) {
                // Her fjerner jeg relationer til planter hvis Reselller har nogen tilknyttet
                for (Plant plant : reseller.getPlants()) {
                    plant.getResellers().remove(reseller);
                }
                // Her clearer jeg sættet af planter
                reseller.getPlants().clear();
                em.flush();  // Sørg for, at ændringerne bliver vedvarende i databasen før fjernelse
                // Og til sidst sletter jeg reseller. En anden måde at bruge CascadeType.REMOVE i Reseller
                // klassen på sættet af planter
                em.remove(reseller);
            }
            em.getTransaction().commit();
            return reseller;
        }
    }
}
