package controller;

import com.blade.mvc.annotation.GetRoute;
import com.blade.mvc.annotation.Path;

@Path
public class DictionaryController {

    @GetRoute("/hello")
    public String helloWord() {
        return "Hello World";
    }
}
