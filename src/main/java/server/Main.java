package server;

import com.blade.Blade;
import server.dao.Database;
import server.model.database.DatabaseParameters;

public class Main {
    public static void main(String[] args) {
        Blade.of()
                .start(Main.class, args);
        Database.getInstance().setParameters(new DatabaseParameters()
            .setHost("localhost")
            .setPort("27017")
            .setDatabaseName("library"));
    }
}
