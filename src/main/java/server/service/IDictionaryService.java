package server.service;

import org.bson.types.ObjectId;
import server.model.item.Item;

import java.util.Collection;

public interface IDictionaryService {

    Collection<Item> getAll(String searchString);

    Item getById(ObjectId id);

    Item create(Item item);

    void update(Item item);

    void deleteById(ObjectId id);

}
