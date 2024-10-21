package dat.daos.impl;

import dat.dtos.BicycleDTO;
import dat.entities.Bicycle;
import dat.entities.Frame;
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
            return new BicycleDTO(bicycle);
        }
    }

    public BicycleDTO getById(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            Bicycle bicycle = em.find(Bicycle.class, id);
            return bicycle != null ? new BicycleDTO(bicycle) : null;  // Returner DTO
        }
    }

    public List<BicycleDTO> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<BicycleDTO> query = em.createQuery("SELECT new dat.dtos.BicycleDTO(r) FROM Bicycle r", BicycleDTO.class);
            return query.getResultList();
        }
    }

    public Bicycle addFrameToBicycle(int bicycleId, int frameId) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Bicycle bicycle = em.find(Bicycle.class, bicycleId);
            Frame frame = em.find(Frame.class, frameId);

            if (bicycle != null && frame != null) {
                bicycle.addFrame(frame);
                frame.getBicycles().add(bicycle);
                em.merge(bicycle);
                em.merge(frame);

                em.getTransaction().commit();
                return bicycle;
            } else {
                em.getTransaction().rollback();
                return null;  // Hvis enten reseller eller plant ikke findes returnerer jeg null
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public BicycleDTO update(int id, BicycleDTO bicycleDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Bicycle bicycle = em.find(Bicycle.class, id);
            bicycle.setId(bicycleDTO.getId());
            bicycle.setBrand(bicycleDTO.getBrand());
            bicycle.setModel(bicycleDTO.getModel());
            bicycle.setSize(bicycleDTO.getSize());
            bicycle.setPrice(bicycleDTO.getPrice());
            bicycle.setDescription(bicycleDTO.getDescription());
            bicycle.setFrame(bicycleDTO.getFrame().toEntity());
            bicycle.setGear(bicycleDTO.getGear().toEntity());
            bicycle.setWheel(bicycleDTO.getWheel().toEntity());
            bicycle.setSaddle(bicycleDTO.getSaddle().toEntity());

            Bicycle mergedBicycle = em.merge(bicycle);
            em.getTransaction().commit();
            return mergedBicycle != null ? new BicycleDTO(mergedBicycle) : null;
        }
    }

    public Bicycle delete(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Bicycle bicycle = em.find(Bicycle.class, id);
            if (bicycle != null) {
                // Fjern relation til frame
                Frame frame = bicycle.getFrame();
                frame.getBicycles().remove(bicycle);  // Fjerner her bicycle fra frame

                // Her fjerner jeg den tilknyttede frame fra bicycle
                bicycle.setFrame(null);

                em.flush();  // Sørg for, at ændringerne bliver vedvarende i databasen før fjernelse
                // Og til sidst sletter jeg reseller. En anden måde at bruge CascadeType.REMOVE i Reseller
                // klassen på sættet af planter
                em.remove(bicycle);
            }
            em.getTransaction().commit();
            return bicycle;
        }
    }
}
