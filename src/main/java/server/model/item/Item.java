package server.model.item;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.bson.types.ObjectId;
import xyz.morphia.annotations.Entity;
import xyz.morphia.annotations.Id;
import xyz.morphia.annotations.Property;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Entity("item")
public class Item {

    @Id
    private ObjectId id;

    @Property("word")
    @JsonProperty("word")
    private String word;

    @Property("translate")
    @JsonProperty("translate")
    private String translate;

    @JsonProperty("itemId")
    private String itemId;

}
