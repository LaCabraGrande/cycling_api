package dat.utils;

import dat.daos.impl.BicycleDAO;
import dat.dtos.FilterCountDTO;
import dat.dtos.BicycleDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataExporter {
    private static final Logger logger = LoggerFactory.getLogger(DataExporter.class);

    public static void exportInitialData(BicycleDAO bicycleDAO) {
        try {
            logger.info("🚀 Starter eksport af initiale JSON-filer...");
            logger.info("📂 FilterCounts og FilterBicycles bliver gemt i src/main/resources/static/");

            //Her bruger jeg tomt filter for at få alle bicycles + counts
            Map<String, List<String>> emptyFilters = new HashMap<>();

            // Hent filtercounts
            FilterCountDTO filterCount = bicycleDAO.getFilteredCounts(emptyFilters);

            // Her henter jeg movie summaries med forskellige pagesizes
            List<BicycleDTO> bicyclesDTOS = bicycleDAO.getBicyclesByFilters(emptyFilters, 0, 20000);

            // Gem filer
            JsonUtil.toJsonFile("src/main/resources/static/initial_filtercounts.json", filterCount);
            JsonUtil.toJsonFile("src/main/resources/static/initial_bicyclesummaries.json", bicyclesDTOS);


            logger.info("✅ Initiale JSON-filer gemt med succes.");

        } catch (Exception e) {
            System.err.println("❌ Fejl ved generering af initiale JSON-filer: " + e.getMessage());
            logger.error("❌ Fejl i exportInitialData", e);
        }
    }
}
