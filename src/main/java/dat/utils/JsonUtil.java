package dat.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class JsonUtil {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private JsonUtil() {
        // Privat constructor – skal ikke instantieres
    }

    public static void toJsonFile(String path, Object obj) throws IOException {
        String json = gson.toJson(obj);
        Files.writeString(Path.of(path), json);
    }

    public static <T> T fromJsonFile(String path, Class<T> clazz) throws IOException {
        String json = Files.readString(Path.of(path));
        return gson.fromJson(json, clazz);
    }

    public static String loadStaticJson(String resourcePath) throws IOException {
        try (InputStream is = JsonUtil.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new IOException("Resource not found: " + resourcePath);
            }
            return new String(is.readAllBytes());
        }
    }
}

