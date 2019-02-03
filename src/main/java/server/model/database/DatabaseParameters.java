package server.model.database;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class DatabaseParameters {
    private String host;
    private String port;
    private String databaseName;
}
