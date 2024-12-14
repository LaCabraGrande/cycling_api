package dat.daos.impl;

import dat.dtos.*;
import dat.entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

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
                    "LEFT JOIN FETCH b.frame f " +
                    // Sørg for at hente 'frame'
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
            if (filters.containsKey("bicycleBrand")) {
                jpql += " AND b.brand IN :bicycleBrand";
            }
            if (filters.containsKey("bicycleType")) {
                jpql += " AND g.type IN :bicycleType";
            }
            if (filters.containsKey("wheelType")) {
                jpql += " AND w.type IN :wheelType";
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
            if (filters.containsKey("bicycleBrand")) {
                query.setParameter("bicycleBrand", filters.get("bicycleBrand"));
            }
            if (filters.containsKey("bicycleType")) {
                query.setParameter("bicycleType", filters.get("bicycleType"));
            }
            if (filters.containsKey("wheelType")) {
                query.setParameter("wheelType", filters.get("wheelType"));
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


    public FilterCountDTO getFilteredCounts(Map<String, List<String>> filters) {
        // Hent alle cykler fra databasen
        List<BicycleDTO> bicycles = this.getAll();

        // Filtrér data dynamisk baseret på de modtagne filtre
        List<BicycleDTO> filteredBicycles = bicycles.stream()
                .filter(bicycle -> filters.entrySet().stream()
                        .allMatch(entry -> matchesFilter(entry.getKey(), entry.getValue(), bicycle)))
                .collect(Collectors.toList());

        // Beregn tællingerne baseret på den filtrerede liste
        return calculateFilterCounts(filteredBicycles, filters);
    }

    private boolean matchesFilter(String key, List<String> values, BicycleDTO bicycle) {
        if (values == null || values.isEmpty()) {
            return true;
        }

        switch (key) {
            case "gearSeries":
                return values.stream().anyMatch(value -> bicycle.getGear().getSeries().equalsIgnoreCase(value));
            case "saddleBrand":
                return values.stream().anyMatch(value -> bicycle.getSaddle().getBrand().equalsIgnoreCase(value));
            case "wheelBrand":
                return values.stream().anyMatch(value -> bicycle.getWheel().getBrand().equalsIgnoreCase(value));
            case "bicycleBrand":
                return values.stream().anyMatch(value -> bicycle.getBrand().equalsIgnoreCase(value));
            case "bicycleType":
                return values.stream().anyMatch(value -> bicycle.getGear().getType().equalsIgnoreCase(value));
            case "wheelType":
                return values.stream().anyMatch(value -> bicycle.getWheel().getType().equalsIgnoreCase(value));
            case "priceInterval":
                int price = bicycle.getPrice();
                return values.stream().anyMatch(value -> matchesPriceInterval(value, price));
            default:
                return false;
        }
    }

    private boolean matchesPriceInterval(String interval, int price) {
        switch (interval) {
            case "0-3000":
                return price >= 0 && price <= 3000;
            case "3000-5000":
                return price > 3000 && price <= 5000;
            case "5000-7000":
                return price > 5000 && price <= 7000;
            case "7000-9000":
                return price > 7000 && price <= 9000;
            case "at least 9000":
                return price >= 9000;
            default:
                return false;
        }
    }

    // Her beregner jeg antallet af komponenter baseret på de filtrede cykler som er bicycles og skaber en FilterCountDTO som jeg returnerer
    private FilterCountDTO calculateFilterCounts(List<BicycleDTO> bicycles, Map<String, List<String>> filters) {
        // Her henter jeg alle mulige gear-serier fra databasen
        List<BicycleDTO> bicycleDTOS = this.getAll();
        Set<String> allGearSeries = bicycleDTOS.stream()
                .map(bicycle -> bicycle.getGear().getSeries())
                .collect(Collectors.toSet());
        Set<String> allSaddleBrands = bicycleDTOS.stream()
                .map(bicycle -> bicycle.getSaddle().getBrand())
                .collect(Collectors.toSet());
        Set<String> allWheelBrands = bicycleDTOS.stream()
                .map(bicycle -> bicycle.getWheel().getBrand())
                .collect(Collectors.toSet());
        Set<String> allBicycleBrands = bicycleDTOS.stream()
                .map(BicycleDTO::getBrand)
                .collect(Collectors.toSet());
        Set<String> allBicycleTypes = bicycleDTOS.stream()
                .map(bicycle -> bicycle.getGear().getType())
                .collect(Collectors.toSet());
        Set<String> allWheelTypes = bicycleDTOS.stream()
                .map(bicycle -> bicycle.getWheel().getType())
                .collect(Collectors.toSet());


        Map<String, Integer> gearSeriesCount = new HashMap<>();
        Map<String, Integer> saddleBrandCount = new HashMap<>();
        Map<String, Integer> wheelBrandCount = new HashMap<>();
        Map<String, Integer> bicycleBrandCount = new HashMap<>();
        Map<String, Integer> bicycleTypeCount = new HashMap<>();
        Map<String, Integer> wheelTypeCount = new HashMap<>();

//        for (String gearSeries : allGearSeries) {
//            int count = 0;
//            for (BicycleDTO bicycle : bicycles) {
//                if (gearSeries.equals(bicycle.getGear().getSeries())) {
//                    count++;
//                }
//            }
//            gearSeriesCount.put(gearSeries, count);
//        }

        List<BicycleDTO> listToUse;

        // Flag for at tjekke, om alle nøgler kun er "gearSeries" og har værdier
        boolean onlyGearSeriesSelected = true;

        // Gennemløb alle entries i filters og udskriv dem
        for (Map.Entry<String, List<String>> entry : filters.entrySet()) {
            String key = entry.getKey();
            List<String> value = entry.getValue();

            // Udskriv nøgle og dens tilhørende værdi
            System.out.println("Filter key: " + key);
            System.out.println("Filter value: " + (value != null ? value : "null"));

            // Hvis værdien ikke er tom, tjek om nøglen er "gearSeries"
            if (value != null && !value.isEmpty() && !"gearSeries".equals(key)) {
                onlyGearSeriesSelected = false; // Der er et filter, der ikke er "gearSeries"
            }
        }

        // Udskriv resultatet af evalueringen
        System.out.println("All keys with non-empty values are 'gearSeries': " + onlyGearSeriesSelected);

        // Brug den passende liste baseret på resultatet
        if (onlyGearSeriesSelected) {
            System.out.println("Using bicycleDTOS (only 'gearSeries' selected).");
            listToUse = bicycleDTOS;
        } else {
            System.out.println("Using bicycles (other filters are present).");
            listToUse = bicycles;
        }
        for (String gearSeries : allGearSeries) {
            int count = 0;
            for (BicycleDTO bicycle : listToUse) {
                if (gearSeries.equals(bicycle.getGear().getSeries())) {
                    count++;
                }
            }
            gearSeriesCount.put(gearSeries, count);
        }

        for (String saddleBrand : allSaddleBrands) {
            int count = 0;
            for (BicycleDTO bicycle : bicycles) {
                if (saddleBrand.equals(bicycle.getSaddle().getBrand())) {
                    count++;
                }
            }
            saddleBrandCount.put(saddleBrand, count);
        }

        for (String wheelBrand : allWheelBrands) {
            int count = 0;
            for (BicycleDTO bicycle : bicycles) {
                if (wheelBrand.equals(bicycle.getWheel().getBrand())) {
                    count++;
                }
            }
            wheelBrandCount.put(wheelBrand, count);
        }

        for (String bicycleBrand : allBicycleBrands) {
            int count = 0;
            for (BicycleDTO bicycle : bicycles) {
                if (bicycleBrand.equals(bicycle.getBrand())) {
                    count++;
                }
            }
            bicycleBrandCount.put(bicycleBrand, count);
        }

        for (String bicycleType : allBicycleTypes) {
            int count = 0;
            for (BicycleDTO bicycle : bicycles) {
                if (bicycleType.equals(bicycle.getGear().getType())) {
                    count++;
                }
            }
            bicycleTypeCount.put(bicycleType, count);
        }

        for (String wheelType : allWheelTypes) {
            int count = 0;
            for (BicycleDTO bicycle : bicycles) {
                if (wheelType.equals(bicycle.getWheel().getType())) {
                    count++;
                }
            }
            wheelTypeCount.put(wheelType, count);
        }


        // Price interval tælling
        Map<String, Integer> priceIntervalCount = initializeCountMap(filters.get("priceInterval"), bicycles, "priceInterval", getAllPriceIntervals());
        for (BicycleDTO bicycle : bicycles) {
            int price = bicycle.getPrice();
            String priceInterval = getPriceInterval(price);
            priceIntervalCount.put(priceInterval, priceIntervalCount.getOrDefault(priceInterval, 0) + 1);
        }

        // Returner DTO'en med antal for de forskellige komponenter
        return new FilterCountDTO(
                gearSeriesCount,
                saddleBrandCount,
                wheelBrandCount,
                bicycleBrandCount,
                bicycleTypeCount,
                wheelTypeCount,
                priceIntervalCount
        );
    }


    // Helper-metode til at initialisere en tællings-map med mulige værdier
    private Map<String, Integer> initializeCountMap(List<String> filterValues, List<BicycleDTO> bicycles, String filterKey, Set<String> allPossibleValues) {
        Map<String, Integer> countMap = new HashMap<>();

        // Hvis der ikke er nogen specifikke filtre, initialiserer vi med alle mulige værdier for det relevante filter
        if (filterValues == null || filterValues.isEmpty()) {
            // Brug alle mulige værdier (uanset om de er i cyklerne eller ej)
            for (String value : allPossibleValues) {
                countMap.put(value, 0);  // Initialiser tællingen som 0
            }
        } else {
            // Hvis der er specifikke filtre, initialiserer vi kun de filtrerede værdier
            for (String value : filterValues) {
                countMap.put(value, 0);  // Initialiser tællingen for hver værdi som 0
            }
        }

        return countMap;
    }

    private Set<String> getAllPriceIntervals() {
        return Set.of("0-3000", "3000-5000", "5000-7000", "7000-9000", "at least 9000");
    }

    private String getPriceInterval(int price) {
        if (price <= 3000) return "0-3000";
        if (price <= 5000) return "3000-5000";
        if (price <= 7000) return "5000-7000";
        if (price <= 9000) return "7000-9000";
        return "at least 9000";
    }


    // Metode til at hente gear, saddle og wheel counts
//    public FilterCountDTO getFilterCounts() {
//        // Hent data fra databasen
//        List<BicycleDTO> bicycles = this.getAll();
//
//        // Gear-serie tælling
//        Map<String, Integer> gearSeriesCount = new HashMap<>();
//        for (BicycleDTO bicycle : bicycles) {
//            String gearSeries = bicycle.getGear().getSeries();
//            gearSeriesCount.put(gearSeries, gearSeriesCount.getOrDefault(gearSeries, 0) + 1);
//        }
//
//        // Saddle-brand tælling
//        Map<String, Integer> saddleBrandCount = new HashMap<>();
//        for (BicycleDTO bicycle : bicycles) {
//            String saddleBrand = bicycle.getSaddle().getBrand();
//            saddleBrandCount.put(saddleBrand, saddleBrandCount.getOrDefault(saddleBrand, 0) + 1);
//        }
//
//        // Wheel-brand tælling
//        Map<String, Integer> wheelBrandCount = new HashMap<>();
//        for (BicycleDTO bicycle : bicycles) {
//            String wheelBrand = bicycle.getWheel().getBrand();
//            wheelBrandCount.put(wheelBrand, wheelBrandCount.getOrDefault(wheelBrand, 0) + 1);
//        }
//
//        // Bicycle-brand tælling
//        Map<String, Integer> bicycleBrandCount = new HashMap<>();
//        for (BicycleDTO bicycle : bicycles) {
//            String bicycleBrand = bicycle.getBrand();
//            bicycleBrandCount.put(bicycleBrand, bicycleBrandCount.getOrDefault(bicycleBrand, 0) + 1);
//        }
//
//        // Tælling på antal Bicycles med Mekanisk eller Elektronisk gear
//        Map<String, Integer> bicycleTypeCount = new HashMap<>();
//        for (BicycleDTO bicycle : bicycles) {
//            String bicycleType = bicycle.getGear().getType();
//            bicycleTypeCount.put(bicycleType, bicycleTypeCount.getOrDefault(bicycleType, 0) + 1);
//        }
//
//        // Wheel-type tælling
//        Map<String, Integer> wheelTypeCount = new HashMap<>();
//        for (BicycleDTO bicycle : bicycles) {
//            String wheelType = bicycle.getWheel().getType();
//            wheelTypeCount.put(wheelType, wheelTypeCount.getOrDefault(wheelType, 0) + 1);
//        }
//
//        // Prisinterval tælling
//        Map<String, Integer> priceRangeCount = new HashMap<>();
//        priceRangeCount.put("0-3000", 0);
//        priceRangeCount.put("3000-5000", 0);
//        priceRangeCount.put("5000-7000", 0);
//        priceRangeCount.put("7000-9000", 0);
//        priceRangeCount.put("at least 9000", 0);
//
//        for (BicycleDTO bicycle : bicycles) {
//            double price = bicycle.getPrice(); // Antager BicycleDTO har et price-felt
//            if (price < 3000) {
//                priceRangeCount.put("0-3000", priceRangeCount.get("0-3000") + 1);
//            } else if (price < 5000) {
//                priceRangeCount.put("3000-5000", priceRangeCount.get("3000-5000") + 1);
//            } else if (price < 7000) {
//                priceRangeCount.put("5000-7000", priceRangeCount.get("5000-7000") + 1);
//            } else if (price < 9000) {
//                priceRangeCount.put("7000-9000", priceRangeCount.get("7000-9000") + 1);
//            } else {
//                priceRangeCount.put("at least 9000", priceRangeCount.get("at least 9000") + 1);
//            }
//        }
//
//        // Returner den opdaterede DTO
//        return new FilterCountDTO(
//                gearSeriesCount,
//                saddleBrandCount,
//                wheelBrandCount,
//                bicycleBrandCount,
//                bicycleTypeCount,
//                wheelTypeCount,
//                priceRangeCount
//        );
//    }


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
        } catch (Exception e) {
            throw new RuntimeException("Error validating frame primary key", e);
        }
    }
}
