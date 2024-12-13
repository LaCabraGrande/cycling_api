package dat.daos.impl;

import dat.dtos.*;
import dat.entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.hibernate.Hibernate;

import java.util.*;
import java.util.stream.Collectors;

public class BicycleDAO {

    private static BicycleDAO instance;
    private static EntityManagerFactory emf;
    private final GearDAO gearDAO = GearDAO.getInstance(emf);
    private final SaddleDAO saddleDAO = SaddleDAO.getInstance(emf);
    private final WheelDAO wheelDAO = WheelDAO.getInstance(emf);

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


    public BicycleDTO createWithComponents(BicycleDTO bicycleDTO) {

        try (EntityManager em = emf.createEntityManager()) {
            System.out.println("Starting addWithComponents...");
            em.getTransaction().begin();

            // Fetch and print details for Frame
            System.out.println("Fetching Frame from DTO...");
            Frame frame = bicycleDTO.getFrame() != null ? em.find(Frame.class, bicycleDTO.getFrame().getId()) : null;
            System.out.println("Frame fetched: " + (frame != null ? frame.getId() : "null"));

            // Fetch and print details for Gear
            System.out.println("Fetching Gear from DTO...");
            Gear gear = bicycleDTO.getGear() != null ? em.find(Gear.class, bicycleDTO.getGear().getId()) : null;
            System.out.println("Gear fetched: " + (gear != null ? gear.getId() : "null"));

            // Fetch and print details for Wheel
            System.out.println("Fetching Wheel from DTO...");
            Wheel wheel = bicycleDTO.getWheel() != null ? em.find(Wheel.class, bicycleDTO.getWheel().getId()) : null;
            System.out.println("Wheel fetched: " + (wheel != null ? wheel.getId() : "null"));

            // Fetch and print details for Saddle
            System.out.println("Fetching Saddle from DTO...");
            Saddle saddle = bicycleDTO.getSaddle() != null ? em.find(Saddle.class, bicycleDTO.getSaddle().getId()) : null;
            System.out.println("Saddle fetched: " + (saddle != null ? saddle.getId() : "null"));

            // Create and persist the Bicycle entity
            System.out.println("Creating Bicycle entity...");
            Bicycle bicycle = new Bicycle(
                    bicycleDTO.getBrand(),
                    bicycleDTO.getModel(),
                    bicycleDTO.getSize(),
                    bicycleDTO.getPrice(),
                    bicycleDTO.getWeight(),
                    bicycleDTO.getDescription(),
                    frame, gear, wheel, saddle
            );
            em.persist(bicycle);
            em.getTransaction().commit();

            System.out.println("Bicycle created with ID: " + bicycle.getId());
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
        } catch (Exception e) {
            throw new RuntimeException("Error fetching bicycles", e);
        }
    }

    public List<BicycleDTO> filterByComponents(int saddleId, int frameId, int wheelId, int gearId) {
        try {
            List<BicycleDTO> allBicycles = getAll();
            return allBicycles.stream()
                    .filter(bicycle -> saddleId == 0 || bicycle.getSaddle().getId() == saddleId) // Filtrer på saddleId
                    .filter(bicycle -> frameId == 0 || bicycle.getFrame().getId() == frameId) // Filtrer på frameId
                    .filter(bicycle -> wheelId == 0 || bicycle.getWheel().getId() == wheelId) // Filtrer på wheelId
                    .filter(bicycle -> gearId == 0 || bicycle.getGear().getId() == gearId) // Filtrer på gearId
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error during filtering by components", e);
        }
    }

    /**
     * Hent cykler baseret på flere filtre.
     */
    public List<BicycleDTO> getBicyclesByFilters(Map<String, List<String>> filters, int minPrice, int maxPrice) {
        List<BicycleDTO> bicyclesDTOS = new ArrayList<>();
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            // JPQL: Hent Bicycle entiteter, ikke DTO'er direkte
            String jpql = "SELECT b FROM Bicycle b " +
                    "LEFT JOIN FETCH b.gear g " +
                    "LEFT JOIN FETCH b.saddle s " +
                    "LEFT JOIN FETCH b.wheel w " +
                    "LEFT JOIN FETCH b.frame f " +  // Sørg for at hente 'frame'
                    "WHERE b.price BETWEEN :minPrice AND :maxPrice";

            if (filters.containsKey("gearSeries")) {
                jpql += " AND g.series IN :gearSeries";
            }
            if (filters.containsKey("saddleBrand")) {
                jpql += " AND s.brand IN :saddleBrand";
            }
            if (filters.containsKey("wheelBrand")) {
                jpql += " AND w.brand IN :wheelBrand";
            }

            TypedQuery<Bicycle> query = em.createQuery(jpql, Bicycle.class);
            query.setParameter("minPrice", minPrice);
            query.setParameter("maxPrice", maxPrice);

            if (filters.containsKey("gearSeries")) {
                query.setParameter("gearSeries", filters.get("gearSeries"));
            }
            if (filters.containsKey("saddleBrand")) {
                query.setParameter("saddleBrand", filters.get("saddleBrand"));
            }
            if (filters.containsKey("wheelBrand")) {
                query.setParameter("wheelBrand", filters.get("wheelBrand"));
            }

            List<Bicycle> bicycles = query.getResultList();

            // Map Bicycle entiteter til BicycleDTO
            for (Bicycle bicycle : bicycles) {
                BicycleDTO dto = new BicycleDTO();
                dto.setId(bicycle.getId());
                dto.setBrand(bicycle.getBrand());
                dto.setModel(bicycle.getModel());
                dto.setSize(bicycle.getSize());
                dto.setPrice(bicycle.getPrice());
                dto.setWeight(bicycle.getWeight());
                dto.setDescription(bicycle.getDescription());
                dto.setFrame(new FrameDTO(bicycle.getFrame()));
                dto.setGear(new GearDTO(bicycle.getGear()));
                dto.setSaddle(new SaddleDTO(bicycle.getSaddle()));
                dto.setWheel(new WheelDTO(bicycle.getWheel()));
                bicyclesDTOS.add(dto);
            }

            em.getTransaction().commit();
        } catch (Exception e) {
           throw new RuntimeException("Error fetching bicycles by filters: " + e.getMessage());
        }
        return bicyclesDTOS;
    }

    // Metode til at hente gear, saddle og wheel counts
    public FilterCountDTO getFilterCounts() {
        // Hent saddles og wheels data fra databasen
        List<SaddleDTO> saddles = saddleDAO.getAll(); // Hent saddles fra databasen
        List<WheelDTO> wheels = wheelDAO.getAll();  // Hent hjul fra databasen

        // Hent alle cykler fra databasen
        List<BicycleDTO> bicycles = this.getAll(); // Brug this.getAll() i BicycleDAO, da metoden er i BicycleDAO

        // Tælling af gear efter gear-serie baseret på cyklerne
        Map<String, Integer> gearSeriesCount = new HashMap<>();
        for (BicycleDTO bicycle : bicycles) {
            // Få gear-serien for den aktuelle cykel
            String gearSeries = bicycle.getGear().getSeries(); // Antager at BicycleDTO har et GearDTO som en egenskab

            // Tæl cykler med denne gear-serie
            gearSeriesCount.put(gearSeries, gearSeriesCount.getOrDefault(gearSeries, 0) + 1);
        }

        // Tælling af saddel-brands baseret på cyklerne
        Map<String, Integer> saddleBrandCount = new HashMap<>();
        for (BicycleDTO bicycle : bicycles) {
            // Få sadel-brand for den aktuelle cykel
            String saddleBrand = bicycle.getSaddle().getBrand(); // Antager at BicycleDTO har et SaddleDTO som en egenskab

            // Tæl cykler med dette saddle-brand
            saddleBrandCount.put(saddleBrand, saddleBrandCount.getOrDefault(saddleBrand, 0) + 1);
        }

        // Tælling af wheel-brands baseret på cyklerne
        Map<String, Integer> wheelBrandCount = new HashMap<>();
        for (BicycleDTO bicycle : bicycles) {
            // Få wheel-brand for den aktuelle cykel
            String wheelBrand = bicycle.getWheel().getBrand(); // Antager at BicycleDTO har et WheelDTO som en egenskab

            // Tæl cykler med dette wheel-brand
            wheelBrandCount.put(wheelBrand, wheelBrandCount.getOrDefault(wheelBrand, 0) + 1);
        }

        // Returner FilterCountDTO med de indsamlede data
        return new FilterCountDTO(gearSeriesCount, saddleBrandCount, wheelBrandCount);
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
            throw new RuntimeException("Error adding frame to bicycle", e);
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
            throw new RuntimeException("Error adding gear to bicycle", e);
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
            throw new RuntimeException("Error adding saddle to bicycle", e);
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
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException("Error adding wheel to bicycle", e);
        }
    }

    public Bicycle addAllComponentsToBicycle(int bicycleId, int frameId, int gearId, int wheelId, int saddleId) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Bicycle bicycle = em.find(Bicycle.class, bicycleId);
            Frame frame = em.find(Frame.class, frameId);
            Gear gear = em.find(Gear.class, gearId);
            Wheel wheel = em.find(Wheel.class, wheelId);
            Saddle saddle = em.find(Saddle.class, saddleId);

            if (bicycle != null && frame != null && gear != null && wheel != null && saddle != null) {
                bicycle.addFrame(frame);
                frame.getBicycles().add(bicycle);
                bicycle.addGear(gear);
                gear.getBicycles().add(bicycle);
                bicycle.addWheel(wheel);
                wheel.getBicycles().add(bicycle);
                bicycle.addSaddle(saddle);
                saddle.getBicycles().add(bicycle);
                em.merge(bicycle);
                em.merge(frame);
                em.merge(gear);
                em.merge(wheel);
                em.merge(saddle);

                em.getTransaction().commit();
                return bicycle;
            } else {
                em.getTransaction().rollback();
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException("Error adding components to bicycle", e);
        }
    }

    public BicycleDTO update(int id, BicycleDTO bicycleDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Bicycle bicycle = em.find(Bicycle.class, id);

            if (bicycle == null) {
                em.getTransaction().rollback();
                return null;
            }

            // Jeg opdaterer kun de felter, der er angivet i bicycleDTO
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
            throw new RuntimeException("Error updating bicycle", e);
        }
    }

    public BicycleDTO updateAllDetails(int id, BicycleDTO bicycleDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Bicycle bicycle = em.find(Bicycle.class, id);

            if (bicycle == null) {
                em.getTransaction().rollback();
                return null;
            }

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
            if (bicycleDTO.getFrame() != null) {
                bicycle.setFrame(new Frame(bicycleDTO.getFrame()));
            }
            if (bicycleDTO.getGear() != null) {
                bicycle.setGear(new Gear(bicycleDTO.getGear()));
            }
            if (bicycleDTO.getWheel() != null) {
                bicycle.setWheel(new Wheel(bicycleDTO.getWheel()));
            }
            if (bicycleDTO.getSaddle() != null) {
                bicycle.setSaddle(new Saddle(bicycleDTO.getSaddle()));
            }

            // Her merge vi opdateringerne og committer transaktionen
            Bicycle mergedBicycle = em.merge(bicycle);
            em.getTransaction().commit();
            return new BicycleDTO(mergedBicycle);
        } catch (Exception e) {
            throw new RuntimeException("Error updating bicycle", e);
        }
    }

    public Bicycle delete(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Bicycle bicycle = em.find(Bicycle.class, id);
            // TODO Håndter, hvis cyklen ikke findes (f.eks. kast en undtagelse eller returnér null)
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

                // Her fjerner jeg de tilknyttede frame, gear, wheel og saddle fra bicycle
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
