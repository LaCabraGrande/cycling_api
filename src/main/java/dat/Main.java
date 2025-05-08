package dat;

import dat.config.ApplicationConfig;
import dat.config.HibernateConfig;
import dat.daos.impl.BicycleDAO;
import dat.utils.DataExporter;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    // Her starter vi serveren på port 7070
    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        BicycleDAO bicycleDAO = BicycleDAO.getInstance(emf);

        try {
            System.out.println("📥 Eksporterer initiale JSON-filer...");
            DataExporter.exportInitialData(bicycleDAO);
            System.out.println("✅ Initiale JSON-filer genereret og gemt med succes.");

            System.out.println("🚀 Starter Javalin-server...");
            logger.info("Starter Javalin-server.");
            ApplicationConfig.startServer();

        } catch (Exception e) {
            System.err.println("❌ Uventet fejl: " + e.getMessage());
            logger.error("Uventet fejl i Main", e);
        }
    }
}