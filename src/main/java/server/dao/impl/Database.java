package server.dao.impl;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import lombok.NoArgsConstructor;
import server.model.database.DatabaseParameters;
import xyz.morphia.Datastore;
import xyz.morphia.Morphia;

@NoArgsConstructor
public class Database {

    private static volatile Database instance;
    private DatabaseParameters parameters;
    private Datastore datastore;

//    private Database() {
//
//    }

    public static Database getInstance() {
        Database localInstance = instance;
        if (localInstance == null) {
            synchronized (Database.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new Database();
                }
            }
        }
        return localInstance;
    }

    public void setParameters(DatabaseParameters dbParameters) {
        parameters = dbParameters;
        MongoClientURI connectionString = new MongoClientURI(String.format(
                "mongodb://%s:%s",
                parameters.getHost(),
                parameters.getPort()));
        final Morphia morphia = new Morphia();
        morphia.mapPackage("server");
        datastore = morphia.createDatastore(new MongoClient(connectionString), parameters.getDatabaseName());
    }

    public Datastore getDatastore() {
        return datastore;
    }

}
