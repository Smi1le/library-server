package server;

import com.blade.Blade;
import server.controller.DictionaryController;

public class Main {
    public static void main(String[] args) {
        Blade.of()
//             .   .get("/hello", (request, response) -> DictionaryController::helloWord)
//                .listen(8080)
                .start(Main.class, args);
    }
}
