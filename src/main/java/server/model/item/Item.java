package server.model.item;

import com.blade.kit.json.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import xyz.morphia.annotations.Entity;
import xyz.morphia.annotations.Id;
import xyz.morphia.annotations.Property;

@Data
@NoArgsConstructor
@Entity("item")
public class Item {

    @Id
    @JsonProperty("id")
    private ObjectId id;

    @Property("word")
    @JsonProperty("word")
    private String word;

    @Property("translate")
    @JsonProperty("translate")
    private String translate;
}
