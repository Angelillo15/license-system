package es.angelillo15.license;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.json.JavalinJackson;
import io.javalin.json.JsonMapper;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LicenseServer {
    @Getter
    private static Javalin app;
    @Getter
    private static Logger logger = LoggerFactory.getLogger("LicenseServer");

    public static void main(String[] args) {
        app = Javalin.create(config -> {
            ObjectMapper objectMapper = new ObjectMapper(); // customize as needed
            JsonMapper jsonMapper = new JavalinJackson(objectMapper);
            config.jsonMapper(jsonMapper);

            config.spaRoot.addFile("/", "/client/index.html", Location.CLASSPATH);
            config.staticFiles.add(staticFileConfig -> {
                staticFileConfig.location = Location.CLASSPATH;
                staticFileConfig.directory = "/client";
                staticFileConfig.hostedPath = "/";
            });
        });

        // Prevent unhandled exceptions from taking down the web server
        app.exception(Exception.class, (e, ctx) -> {
            logger.error("Unhandled exception", e);
            ctx.status(500);
        });

        app.get("/api/license", (ctx -> {
            ctx.json("Hello world");
        }));

        app.start(5000);

        logger.info("License server started");
    }


}
