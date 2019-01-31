package server.controller;

import com.blade.mvc.annotation.GetRoute;
import com.blade.mvc.annotation.JSON;
import com.blade.mvc.annotation.Path;

@Path
public class DictionaryController {

    @GetRoute("/hello")
    @JSON
    public String helloWord() {
        return "Hello World";
    }
}
