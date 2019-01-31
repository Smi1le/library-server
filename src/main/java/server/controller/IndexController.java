package server.controller;

import com.blade.mvc.annotation.GetRoute;
import com.blade.mvc.annotation.JSON;
import com.blade.mvc.annotation.Path;

@Path
public class IndexController {

    @GetRoute("/getName")
    @JSON
    public String getName() {
        return "Some small server for docker example";
    }
}
