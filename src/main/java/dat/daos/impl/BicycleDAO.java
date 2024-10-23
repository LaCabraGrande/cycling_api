package dat.daos.impl;

import dat.dtos.BicycleDTO;
import dat.entities.*;
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
            e.printStackTrace();
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
                return null;  // Hvis enten bicycle eller frame ikke findes returnerer jeg null
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Bicycle addGearToBicycle(int bicycleId, int gearId) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Bicycle bicycle = em.find(Bicycle.class, bicycleId);
            Gear gear = em.find(Gear.class, gearId);

            if (bicycle != null && gear != null) {
                bicycle.addGear(gear);
                gear.getBicycles().add(bicycle);
                em.merge(bicycle);
                em.merge(gear);

                em.getTransaction().commit();
                return bicycle;
            } else {
                em.getTransaction().rollback();
                return null;  // Hvis enten bicycle eller gear ikke findes returnerer jeg null
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Bicycle addSaddleToBicycle(int bicycleId, int saddleId) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Bicycle bicycle = em.find(Bicycle.class, bicycleId);
            Saddle saddle = em.find(Saddle.class, saddleId);

            if (bicycle != null && saddle != null) {
                bicycle.addSaddle(saddle);
                saddle.getBicycles().add(bicycle);
                em.merge(bicycle);
                em.merge(saddle);

                em.getTransaction().commit();
                return bicycle;
            } else {
                em.getTransaction().rollback();
                return null;  // Hvis enten bicycle eller saddle ikke findes returnerer jeg null
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Bicycle addWheelToBicycle(int bicycleId, int wheelId) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Bicycle bicycle = em.find(Bicycle.class, bicycleId);
            Wheel wheel = em.find(Wheel.class, wheelId);

            if (bicycle != null && wheel != null) {
                bicycle.addWheel(wheel);
                wheel.getBicycles().add(bicycle);
                em.merge(bicycle);
                em.merge(wheel);

                em.getTransaction().commit();
                return bicycle;
            } else {
                em.getTransaction().rollback();
                return null;  // Hvis enten bicycle eller wheel ikke findes returnerer jeg null
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
            if (bicycleDTO.getSize() > 0) {
                bicycle.setSize(bicycleDTO.getSize());
            }
            if (bicycleDTO.getPrice() > 0) {
                bicycle.setPrice(bicycleDTO.getPrice());
            }
            if (bicycleDTO.getWeight() > 0) {
                bicycle.setWeight(bicycleDTO.getWeight());
            }
            if (bicycleDTO.getDescription() != null) {
                bicycle.setDescription(bicycleDTO.getDescription());
            }

            // Merge opdateringerne og commit transaktionen
            Bicycle mergedBicycle = em.merge(bicycle);
            em.getTransaction().commit();
            return new BicycleDTO(mergedBicycle);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Bicycle delete(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Bicycle bicycle = em.find(Bicycle.class, id);
            if (bicycle != null) {
                Frame frame = bicycle.getFrame();
                if (frame != null) {
                frame.getBicycles().remove(bicycle);
                }
                Gear gear = bicycle.getGear();
                if (gear != null) {
                    gear.getBicycles().remove(bicycle);
                }
                Wheel wheel = bicycle.getWheel();
                if (wheel != null) {
                    wheel.getBicycles().remove(bicycle);
                }
                Saddle saddle = bicycle.getSaddle();
                if (saddle != null) {
                    saddle.getBicycles().remove(bicycle);
                }

                // Her fjerner jeg de tilknyttede frame,gear, wheel og saddle fra bicycle
                bicycle.setFrame(null);
                bicycle.setGear(null);
                bicycle.setWheel(null);
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
