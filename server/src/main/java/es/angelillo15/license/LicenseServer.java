package es.angelillo15.license;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.angelillo15.license.config.Config;
import es.angelillo15.license.config.ConfigLoader;
import io.javalin.Javalin;
import io.javalin.config.JettyConfig;
import io.javalin.http.staticfiles.Location;
import io.javalin.jetty.JettyUtil;
import io.javalin.json.JavalinJackson;
import io.javalin.json.JsonMapper;
import io.javalin.util.JavalinLogger;
import lombok.Getter;
import org.tinylog.Logger;

public class LicenseServer {
    @Getter
    private static Javalin app;
    public static void main(String[] args) {
        JavalinLogger.enabled = false;
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
            Logger.error("Unhandled exception", e);
            ctx.status(500);
        });

        app.get("/api/license", (ctx -> {
            ctx.json("Hello world");
        }));
        ConfigLoader.loadConfig();

        app.start(Config.port());

        Logger.info("Server started on port " + Config.port());
    }
}
