package controllers;

import com.google.inject.Inject;
import ninja.Result;
import ninja.Results;
import ninja.utils.NinjaProperties;

public class CorsHeadersController {
    @Inject
    NinjaProperties ninjaProperties;

    public Result routeForOptions() {
        return addHeaders(Results.ok().json().render("key", "value"));
    }
    public Result addHeaders(Result result) {
        return result.addHeader("Access-Control-Allow-Origin", "http://localhost:4200")
                .addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS")
                .addHeader("Access-Control-Request-Method", "GET, POST, DELETE, PUT, OPTIONS")
                .addHeader("Access-Control-Allow-Headers", "apikey, hrEmail, Content-Type, Content-Range, Content-Disposition, Content-Description, Authorization, X-PING, Set-Cookie")
                .addHeader("Access-Control-Request-Headers", "Cookie")
                .addHeader("Access-Control-Max-Age", "85000")
                .addHeader("Access-Control-Allow-Credentials", "true");
    }
}
