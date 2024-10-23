package dat.daos.impl;

import dat.daos.IDAO;
import dat.dtos.WheelDTO;
import dat.entities.Bicycle;
import dat.entities.Wheel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class WheelDAO implements IDAO<WheelDTO> {

    private static WheelDAO instance;
    private static EntityManagerFactory emf;

    public static WheelDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new WheelDAO();
        }
        return instance;
    }

    @Override
    public WheelDTO getById(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            Wheel wheel = em.find(Wheel.class, id);
            //
            return wheel != null ? new WheelDTO(wheel) : null;
        }
    }

    @Override
    public List<WheelDTO> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<WheelDTO> query = em.createQuery("SELECT new dat.dtos.WheelDTO(w) FROM Wheel w", WheelDTO.class);
            return query.getResultList();
        }
    }

    @Override
    public WheelDTO add(WheelDTO wheelDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Wheel wheel = new Wheel(wheelDTO);
            em.persist(wheel);
            em.getTransaction().commit();
            return new WheelDTO(wheel);
        }
    }

    public WheelDTO update(int id, WheelDTO wheelDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Wheel w = em.find(Wheel.class, id);
            w.setBrand(wheelDTO.getBrand());
            w.setType(wheelDTO.getType());
            w.setMaterial(wheelDTO.getMaterial());
            w.setWeight(wheelDTO.getWeight());
            w.setSize(wheelDTO.getSize());
            Wheel mergedWheel = em.merge(w);
            em.getTransaction().commit();
            return mergedWheel != null ? new WheelDTO(mergedWheel) : null;
        }
    }

    public WheelDTO delete(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Wheel wheel = em.find(Wheel.class, id);
            Wheel deletedWheel = em.find(Wheel.class, id);
            if (wheel != null) {
                for (Bicycle b : wheel.getBicycles()) {
                    b.setWheel(null);
                }
                wheel.getBicycles().clear();
                em.merge(wheel);

                em.remove(wheel);

            }
            em.getTransaction().commit();
            return new WheelDTO(deletedWheel);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Ekstra metode: Tilføj wheel til forhandler
    public Bicycle add(int bicycleId, int wheelId) {

        try (EntityManager em = emf.createEntityManager()) {
            Bicycle bicycle = em.find(Bicycle.class, bicycleId);
            Wheel wheel = em.find(Wheel.class, wheelId);
            if (bicycle != null && wheel != null) {
                em.getTransaction().begin();
                bicycle.addWheel(wheel);
                em.merge(bicycle);
                em.getTransaction().commit();
                return bicycle;
            }
            return null;
        }
    }

    public boolean validatePrimaryKey(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            Wheel wheel = em.find(Wheel.class, integer);
            return wheel != null;
        }
    }
}