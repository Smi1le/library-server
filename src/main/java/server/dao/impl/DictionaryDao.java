package server.dao;

import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import server.model.item.CreateItem;
import server.model.item.Item;
import xyz.morphia.Key;
import xyz.morphia.query.Query;

import java.util.Collection;

@NoArgsConstructor
public class DictionaryDao {

    public Collection<Item> getAll(String searchString) {
        Database database = Database.getInstance();
        Query<Item> query = database.getDatastore().find(Item.class);
        if (searchString != null) {
            query.field("word").contains(searchString);
        }
        return query.asList();
    }

    public Item getById(ObjectId id) {
        Database database = Database.getInstance();
        return database
                .getDatastore()
                .find(Item.class)
                .field("_id").equal(id)
                .get();
    }

    public Item create(Item item) {
        Database database = Database.getInstance();
        Key<Item> key =  database.getDatastore()
                .save(item);
        item.setId((ObjectId) key.getId());
        return item;
    }

    public void update(Item item) {
        Database database = Database.getInstance();
        Item dbItem = database.getDatastore()
                .find(Item.class)
                .field("_id").equal(item.getId())
                .get();
        dbItem.setWord(item.getWord());
        dbItem.setTranslate(item.getTranslate());
        database.getDatastore().save(dbItem);
    }

    public void deleteById(ObjectId id) {
        Database database = Database.getInstance();
        Item item = database.getDatastore()
                .find(Item.class)
                .field("_id").equal(id)
                .get();
        database.getDatastore().delete(item);
    }
}
