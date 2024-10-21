package dat.daos.impl;

import dat.daos.IDAO;
import dat.dtos.FrameDTO;
import dat.entities.Bicycle;
import dat.entities.Frame;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class FrameDAO implements IDAO<FrameDTO> {

    private static FrameDAO instance;
    private static EntityManagerFactory emf;

    public static FrameDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new FrameDAO();
        }
        return instance;
    }

    @Override
    public FrameDTO getById(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            Frame plant = em.find(Frame.class, id);
            //return new PlantDTO(plant);
            return plant != null ? new FrameDTO(plant) : null;
        }
    }


    @Override
    public List<FrameDTO> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<FrameDTO> query = em.createQuery("SELECT new dat.dtos.FrameDTO(p) FROM Frame p", FrameDTO.class);
            return query.getResultList();
        }
    }

    @Override
    public FrameDTO add(FrameDTO frameDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Frame frame = new Frame(frameDTO);
            em.persist(frame);
            em.getTransaction().commit();
            return new FrameDTO(frame);
        }
    }

    public FrameDTO update(int id, FrameDTO frameDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Frame f = em.find(Frame.class, id);
            f.setId(frameDTO.getId());
            f.setBrand(frameDTO.getBrand());
            f.setType(frameDTO.getType());
            f.setMaterial(frameDTO.getMaterial());
            f.setWeight(frameDTO.getWeight());
            Frame mergedFrame = em.merge(f);
            em.getTransaction().commit();
            return mergedFrame != null ? new FrameDTO(mergedFrame) : null;
        }
    }

    public Frame delete(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Frame frame = em.find(Frame.class, id);
            if (frame != null) {
                // Fjern relationer til resellers
                for (Bicycle bicycle : frame.getBicycles()) {
                    if(bicycle.getFrame().contains(frame)) {
                        bicycle.getFrame().remove(frame);  // Fjern planten fra reseller
                    }
                frame.getBicycles().clear();  // Ryd resellers sættet

                // Nu kan du slette planten
                em.remove(frame);
            }
            em.getTransaction().commit();
            return frame;
        }
    }

    // Ekstra metode: Tilføj plante til forhandler
    public Reseller addPlantToReseller(int resellerId, int plantId) {

        try (EntityManager em = emf.createEntityManager()) {
            Reseller reseller = em.find(Reseller.class, resellerId);
            Plant plant = em.find(Plant.class, plantId);
            if (reseller != null && plant != null) {
                em.getTransaction().begin();
                reseller.addPlant(plant);
                em.merge(reseller);
                em.getTransaction().commit();
                return reseller;
            }
            return null;
        }
    }

    // Ekstra metode: Hent planter fra en bestemt forhandler
    public List<Plant> getPlantsByReseller(int resellerId) {
        try (EntityManager em = emf.createEntityManager()) {
            Reseller reseller = em.find(Reseller.class, resellerId);
            if (reseller != null) {
                return List.copyOf(reseller.getPlants());
            }
            return null;
        }
    }

    public boolean validatePrimaryKey(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            Plant plant = em.find(Plant.class, integer);
            return plant != null;
        }
    }
}
