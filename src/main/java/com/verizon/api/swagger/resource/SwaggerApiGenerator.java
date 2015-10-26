package com.verizon.api.swagger.resource;

import io.swagger.config.Scanner;
import io.swagger.config.ScannerFactory;
import io.swagger.config.SwaggerConfig;
import io.swagger.jaxrs.Reader;
import io.swagger.jaxrs.config.DefaultJaxrsScanner;
import io.swagger.jaxrs.config.JaxrsScanner;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import io.swagger.models.Swagger;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SwaggerApiGenerator {
    static Logger LOGGER = LoggerFactory.getLogger(ApiListingResource.class);
    private static Swagger swagger = null;
    static Scanner scanner = new DefaultJaxrsScanner();

    public static void main(String[] args) throws IOException {
        run();
    }

    private static void run() throws FileNotFoundException, IOException {
        Portal portal = new Portal();
        Swagger swaggerJson = process(portal);
        SwaggerSerializers ss= new SwaggerSerializers();
        FileOutputStream fos= new FileOutputStream("swagger-json-file.txt");
        ss.writeTo(swaggerJson, null, null, null, MediaType.APPLICATION_JSON_TYPE, null, fos);
        fos.flush();
        fos.close();
    }

    private static Swagger process(Application app) {
        Swagger swagger = new Swagger();
        ScannerFactory.setScanner(scanner);
        swagger = scan(app);
        return swagger;
    }

    protected synchronized static Swagger scan(Application app) {

        scanner = ScannerFactory.getScanner();
        LOGGER.debug("using scanner " + scanner);

        if (scanner != null) {
            SwaggerSerializers.setPrettyPrint(scanner.getPrettyPrint());

            Set<Class<?>> classes = new HashSet<Class<?>>();
            if (scanner instanceof JaxrsScanner) {
                JaxrsScanner jaxrsScanner = (JaxrsScanner) scanner;
                classes = jaxrsScanner.classesFromContext(app, null);
            } else {
                classes = scanner.classes();
            }
            if (classes != null) {
                Reader reader = new Reader(swagger, null);
                swagger = reader.read(classes);
                if (scanner instanceof SwaggerConfig) {
                    swagger = ((SwaggerConfig) scanner).configure(swagger);
                }
            }
        }
        return swagger;
    }
}
