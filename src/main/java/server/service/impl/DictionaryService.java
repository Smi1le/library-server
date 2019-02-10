package server.service;

import org.bson.types.ObjectId;
import server.dao.DictionaryDao;
import server.model.item.Item;
import xyz.morphia.Key;

import java.util.Collection;
import java.util.Objects;

public class DictionaryService implements IDictionaryService {

    private DictionaryDao dictionaryDao;

    public DictionaryService() {
        this.dictionaryDao = new DictionaryDao();
    }

    public Collection<Item> getAll(String searchString) {
        return dictionaryDao.getAll(searchString);
    }

    public Item getById(ObjectId id) {
        return dictionaryDao.getById(id);
    }

    public Item create(Item item) {
        checkForCorrect(item);
        return dictionaryDao.create(item);
    }

    public void update(Item item) {
        checkForCorrect(item);
        dictionaryDao.update(item);
    }

    public void deleteById(ObjectId id) {
        dictionaryDao.deleteById(id);
    }

    private void checkForCorrect(Item item) {
        if (Objects.isNull(item.getTranslate()) ||
                Objects.isNull(item.getWord()) ||
                item.getTranslate().equals("") ||
                item.getTranslate().equals("")) {
            throw new IllegalArgumentException("Not correct arguments");
        }
    }
}
