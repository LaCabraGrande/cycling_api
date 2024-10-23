package dat.daos.impl;

import dat.daos.IDAO;
import dat.dtos.GearDTO;
import dat.entities.Bicycle;
import dat.entities.Gear;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class GearDAO implements IDAO<GearDTO> {

    private static GearDAO instance;
    private static EntityManagerFactory emf;

    public static GearDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new GearDAO();
        }
        return instance;
    }

    @Override
    public GearDTO getById(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            Gear gear = em.find(Gear.class, id);
            return gear != null ? new GearDTO(gear) : null;
        }
    }

    @Override
    public List<GearDTO> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<GearDTO> query = em.createQuery("SELECT new dat.dtos.GearDTO(g) FROM Gear g", GearDTO.class);
            return query.getResultList();
        }
    }

    @Override
    public GearDTO add(GearDTO gearDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Gear gear = new Gear(gearDTO);
            em.persist(gear);
            em.getTransaction().commit();
            return new GearDTO(gear);
        }
    }

    public GearDTO update(int id, GearDTO gearDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Gear g = em.find(Gear.class, id);
            g.setBrand(gearDTO.getBrand());
            g.setType(gearDTO.getType());
            g.setMaterial(gearDTO.getMaterial());
            g.setWeight(gearDTO.getWeight());
            g.setModel(gearDTO.getModel());
            Gear mergedGear = em.merge(g);
            em.getTransaction().commit();
            return mergedGear != null ? new GearDTO(mergedGear) : null;
        }
    }

    public GearDTO delete(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Gear gear = em.find(Gear.class, id);
            Gear deletedGear = em.find(Gear.class, id);
            if (gear != null) {
                for (Bicycle b : gear.getBicycles()) {
                    b.setGear(null);
                }
                gear.getBicycles().clear();
                em.merge(gear);
                em.remove(gear);
            }
            em.getTransaction().commit();
            return new GearDTO(deletedGear);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Tilføj gear til bicycle
    public Bicycle add(int bicycleId, int gearId) {

        try (EntityManager em = emf.createEntityManager()) {
            Bicycle bicycle = em.find(Bicycle.class, bicycleId);
            Gear gear = em.find(Gear.class, gearId);
            if (bicycle != null && gear != null) {
                em.getTransaction().begin();
                bicycle.addGear(gear);
                em.merge(bicycle);
                em.getTransaction().commit();
                return bicycle;
            }
            return null;
        }
    }

    public boolean validatePrimaryKey(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            Gear gear = em.find(Gear.class, integer);
            return gear != null;
        }
    }
}
