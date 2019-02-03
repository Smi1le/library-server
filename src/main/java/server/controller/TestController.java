package server.controller;

import com.blade.mvc.annotation.GetRoute;
import com.blade.mvc.annotation.JSON;
import com.blade.mvc.annotation.Path;

@Path("/test")
public class TestController {

    @GetRoute("/getName")
    @JSON
    public String getName() {
        return "Some small server for docker example";
    }

    @GetRoute("/hello")
    @JSON
    public String helloWord() {
        return "Hello World";
    }
}
