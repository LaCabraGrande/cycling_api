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

//@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
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
            Frame frame = em.find(Frame.class, id);
            //
            return frame != null ? new FrameDTO(frame) : null;
        }
        catch (Exception e) {
            throw new RuntimeException("Error fetching frame", e);
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
        catch (Exception e) {
            throw new RuntimeException("Error adding frame", e);
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
        catch (Exception e) {
            throw new RuntimeException("Error updating frame", e);
        }
    }

    public FrameDTO delete(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Frame frame = em.find(Frame.class, id);
            Frame deletedFrame = em.find(Frame.class, id);
            if (frame != null) {
                for (Bicycle b : frame.getBicycles()) {
                    b.setFrame(null);
                }
                frame.getBicycles().clear();
                em.merge(frame);
                em.remove(frame);
            }
            em.getTransaction().commit();
            return new FrameDTO(deletedFrame);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting frame", e);
        }
    }

    // Tilføj frame til bicycle
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
        catch (Exception e) {
            throw new RuntimeException("Error adding frame to bicycle", e);
        }
    }

    public boolean validatePrimaryKey(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            Frame frame = em.find(Frame.class, integer);
            return frame != null;
        }
        catch (Exception e) {
            throw new RuntimeException("Error validating frame primary key", e);
        }
    }
}
