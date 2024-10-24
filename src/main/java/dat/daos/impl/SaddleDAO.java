package dat.daos.impl;

import dat.daos.IDAO;
import dat.dtos.SaddleDTO;
import dat.entities.Bicycle;
import dat.entities.Saddle;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class SaddleDAO implements IDAO<SaddleDTO> {

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
    public SaddleDTO getById(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            Saddle saddle = em.find(Saddle.class, id);
            return saddle != null ? new SaddleDTO(saddle) : null;
        }
        catch (Exception e) {
            throw new RuntimeException("Error fetching saddle", e);
        }
    }

    @Override
    public List<SaddleDTO> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<SaddleDTO> query = em.createQuery("SELECT new dat.dtos.SaddleDTO(s) FROM Saddle s", SaddleDTO.class);
            return query.getResultList();
        }
        catch (Exception e) {
            throw new RuntimeException("Error fetching saddles", e);
        }
    }

    @Override
    public SaddleDTO add(SaddleDTO saddleDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Saddle saddle = new Saddle(saddleDTO);
            em.persist(saddle);
            em.getTransaction().commit();
            return new SaddleDTO(saddle);
        }
        catch (Exception e) {
            throw new RuntimeException("Error adding saddle", e);
        }
    }

    public SaddleDTO update(int id, SaddleDTO saddleDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Saddle s = em.find(Saddle.class, id);
            s.setBrand(saddleDTO.getBrand());
            s.setModel(saddleDTO.getModel());
            s.setMaterial(saddleDTO.getMaterial());
            s.setWeight(saddleDTO.getWeight());
            s.setWidth(saddleDTO.getWidth());
            Saddle mergedSaddle = em.merge(s);
            em.getTransaction().commit();
            return mergedSaddle != null ? new SaddleDTO(mergedSaddle) : null;
        }
        catch (Exception e) {
            throw new RuntimeException("Error updating saddle", e);
        }
    }

    public SaddleDTO delete(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Saddle saddle = em.find(Saddle.class, id);
            Saddle deletedSaddle = em.find(Saddle.class, id);
            if (saddle != null) {
                for (Bicycle b : saddle.getBicycles()) {
                    b.setSaddle(null);
                }
                saddle.getBicycles().clear();
                em.merge(saddle);

                em.remove(saddle);

            }
            em.getTransaction().commit();
            return new SaddleDTO(deletedSaddle);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting saddle", e);
        }
    }

    // Tilføj saddle til bicycle
    public Bicycle add(int bicycleId, int saddleId) {

        try (EntityManager em = emf.createEntityManager()) {
            Bicycle bicycle = em.find(Bicycle.class, bicycleId);
            Saddle saddle = em.find(Saddle.class, saddleId);
            if (bicycle != null && saddle != null) {
                em.getTransaction().begin();
                bicycle.addSaddle(saddle);
                em.merge(bicycle);
                em.getTransaction().commit();
                return bicycle;
            }
            return null;
        }
        catch (Exception e) {
            throw new RuntimeException("Error adding saddle to bicycle", e);
        }
    }

    public boolean validatePrimaryKey(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            Saddle saddle = em.find(Saddle.class, integer);
            return saddle != null;
        }
        catch (Exception e) {
            throw new RuntimeException("Error validating saddle primary key", e);
        }
    }
}
