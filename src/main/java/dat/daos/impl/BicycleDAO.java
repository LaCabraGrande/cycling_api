package dat.daos.impl;

import dat.dtos.BicycleDTO;
import dat.entities.Bicycle;
import dat.entities.Frame;
import dat.entities.Gear;
import dat.entities.Wheel;
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
            List<BicycleDTO> results = query.getResultList();
            System.out.println("Results: " + results);
            return results;
        } catch (Exception e) {
            e.printStackTrace();  // Add logging here for debugging
            throw new RuntimeException("Error fetching bicycles", e);
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

            if (bicycle == null) {
                // Håndter, hvis cyklen ikke findes (f.eks. kast en undtagelse eller returnér null)
                em.getTransaction().rollback();
                return null;
            }

            // Opdater kun de felter, der er angivet i bicycleDTO
            if (bicycleDTO.getBrand() != null) {
                bicycle.setBrand(bicycleDTO.getBrand());
            }
            if (bicycleDTO.getModel() != null) {
                bicycle.setModel(bicycleDTO.getModel());
            }
            if (bicycleDTO.getSize() > 0) { // Antag at 0 ikke er en gyldig størrelse
                bicycle.setSize(bicycleDTO.getSize());
            }
            if (bicycleDTO.getPrice() > 0) { // Antag at 0 ikke er en gyldig pris
                bicycle.setPrice(bicycleDTO.getPrice());
            }
            if (bicycleDTO.getDescription() != null) {
                bicycle.setDescription(bicycleDTO.getDescription());
            }

            // Merge opdateringerne og commit transaktionen
            Bicycle mergedBicycle = em.merge(bicycle);
            em.getTransaction().commit();
            return new BicycleDTO(mergedBicycle);
        } catch (Exception e) {
            e.printStackTrace(); // Log fejl (juster efter behov)
            return null;
        }
    }


    public Bicycle delete(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Bicycle bicycle = em.find(Bicycle.class, id);
            if (bicycle != null) {
                // Fjern relation til frame
                Frame frame = bicycle.getFrame();
                if (frame != null) {
                frame.getBicycles().remove(bicycle);  // Fjerner her bicycle fra frame
                }
                // Fjern relation til Gear
                Gear gear = bicycle.getGear();
                if (gear != null) {
                    gear.getBicycles().remove(bicycle);  // Fjerner her bicycle fra gear
                }
                // Fjern relation til Wheel
                Wheel wheel = bicycle.getWheel();
                if (wheel != null) {
                    wheel.getBicycles().remove(bicycle);  // Fjerner her bicycle fra wheel
                }
                // Fjern relation til Saddle
                dat.entities.Saddle saddle = bicycle.getSaddle();
                if (saddle != null) {
                    saddle.getBicycles().remove(bicycle);  // Fjerner her bicycle fra saddle
                }


                // Her fjerner jeg den tilknyttede frame fra bicycle
                bicycle.setFrame(null);
                // Her fjerner jeg den tilknyttede gear fra bicycle
                bicycle.setGear(null);
                // Her fjerner jeg den tilknyttede wheel fra bicycle
                bicycle.setWheel(null);
                // Her fjerner jeg den tilknyttede saddle fra bicycle
                bicycle.setSaddle(null);
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
