import handler.Health;
import handler.Hello;
import io.muserver.ContentTypes;
import io.muserver.Method;
import io.muserver.MuServer;
import io.muserver.openapi.OpenAPIObjectBuilder;
import io.muserver.rest.RestHandlerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static io.muserver.ContextHandlerBuilder.context;
import static io.muserver.MuServerBuilder.muServer;
import static io.muserver.handlers.ResourceHandlerBuilder.fileOrClasspath;
import static io.muserver.openapi.InfoObjectBuilder.infoObject;

public class App {
    private static final Logger log = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws Exception {
        Map<String, String> settings = System.getenv();

        int port = Integer.parseInt(settings.getOrDefault("APP_PORT", "8081"));
        String appName = settings.getOrDefault("APP_NAME", "my-app");
        log.info("Starting " + appName + " on port " + port);

        MuServer server = muServer()
            .withHttpPort(port)
            .addHandler(context(appName)
                    .addHandler(fileOrClasspath("src/main/resources/web", "/web"))
                    .addHandler(RestHandlerBuilder.restHandler(new Health(), new Hello())
                            .withOpenApiJsonUrl("swagger.json")
                            .withOpenApiHtmlUrl("api.html")
                            .withOpenApiDocument(OpenAPIObjectBuilder.openAPIObject()
                                    .withInfo(infoObject()
                                            .withTitle("API Teamplate")
                                            .withDescription("The REST API for MuServer Template App")
                                            .withVersion("1.0")
                                            .build())
                            )
                    )

            )
            .start();

        Runtime.getRuntime().addShutdownHook(new Thread(server::stop));
        log.info("Started app at " + server.uri().resolve("/" + appName));
    }

}