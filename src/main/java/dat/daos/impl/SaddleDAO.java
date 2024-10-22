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
public class SaddleDAO implements IDAO<FrameDTO> {

    private static SaddleDAO instance;
    private static EntityManagerFactory emf;

    public static SaddleDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new SaddleDAO();
        }
        return instance;
    }

    @Override
    public FrameDTO getById(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            Frame frame = em.find(Frame.class, id);
            //
            return frame != null ? new FrameDTO(frame) : null;
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
            f.setBrand(frameDTO.getBrand());
            f.setType(frameDTO.getType());
            f.setMaterial(frameDTO.getMaterial());
            f.setWeight(frameDTO.getWeight());
            f.setSize(frameDTO.getSize());
            Frame mergedFrame = em.merge(f);
            em.getTransaction().commit();
            return mergedFrame != null ? new FrameDTO(mergedFrame) : null;
        }
    }

    public FrameDTO delete(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Frame frame = em.find(Frame.class, id);
            Frame deletedFrame = em.find(Frame.class, id);
            if (frame != null) {
                // Fjern relationer til resellers
                for (Bicycle b : frame.getBicycles()) {
                    b.setFrame(null);
                }
                frame.getBicycles().clear();  // Ryd resellers sættet
                em.merge(frame);  // Opdater frame i databasen

                // Nu kan du slette framen
                em.remove(frame);

            }
            em.getTransaction().commit();
            return new FrameDTO(deletedFrame);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Ekstra metode: Tilføj plante til forhandler
    public Bicycle add(int bicycleId, int frameId) {

        try (EntityManager em = emf.createEntityManager()) {
            Bicycle bicycle = em.find(Bicycle.class, bicycleId);
            Frame frame = em.find(Frame.class, frameId);
            if (bicycle != null && frame != null) {
                em.getTransaction().begin();
                bicycle.addFrame(frame);
                em.merge(bicycle);
                em.getTransaction().commit();
                return bicycle;
            }
            return null;
        }
    }

    public boolean validatePrimaryKey(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            Frame frame = em.find(Frame.class, integer);
            return frame != null;
        }
    }
}
