import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import server.dao.impl.Database;
import server.model.database.DatabaseParameters;
import server.model.item.Item;
import server.service.IDictionaryService;
import server.service.impl.DictionaryService;

public class TestClass {

    private IDictionaryService service;

    @Before
    public void initDatabase() {
        Database.getInstance().setParameters(new DatabaseParameters()
                .setHost("localhost")
                .setPort("27017")
                .setDatabaseName("library_test"));

        service = new DictionaryService();
    }

    @Test
    public void testForCreateItem() {
        Item item = service.create(new Item()
            .setTranslate("Translate Example")
            .setWord("Example"));
        Assert.assertNotNull(item.getId());
    }

    @Test
    public void testForCreateItemAndGetAllBySearchString() {
        Item item = service.create(new Item()
                .setTranslate("Translate Example")
                .setWord("Example Some New"));
        Assert.assertTrue(service.getAll("Example Some New").size() >= 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testForCreateItemWithoutWord() {
        service.create(new Item()
                .setTranslate("Example"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testForCreateItemWithoutTranslates() {
        service.create(new Item()
                .setWord("Translate Example"));
    }

    @Test
    public void testForGetItemById() {
        Item item = service.create(new Item()
                .setTranslate("Translate Example")
                .setWord("Example"));

        Item newItem = service.getById(item.getId());
        Assert.assertNotNull(newItem);
    }

    @Test
    public void testForUpdateItemTranslate() {
        Item item = service.create(new Item()
                .setTranslate("Translate Example")
                .setWord("Example"));
        String newTemplate = "Translate Example New";
        item.setTranslate(newTemplate);
        service.update(item);
        Item newItem = service.getById(item.getId());
        Assert.assertEquals(newItem.getTranslate(), newTemplate);
        Assert.assertEquals(newItem.getWord(), item.getWord());
    }

    @Test
    public void testForUpdateItemWord() {
        Item item = service.create(new Item()
                .setTranslate("Translate Example")
                .setWord("Example"));
        String newTemplate = "Word Example New";
        item.setWord(newTemplate);
        service.update(item);
        Item newItem = service.getById(item.getId());
        Assert.assertEquals(newItem.getWord(), newTemplate);
        Assert.assertEquals(newItem.getTranslate(), item.getTranslate());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testForUpdateItemWithoutWord() {
        service.update(new Item()
                .setTranslate("Example"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testForUpdateItemWithoutTranslates() {
        service.update(new Item()
                .setWord("Translate Example"));
    }

    @Test
    public void testForDeleteItem() {
        Item item = service.create(new Item()
                .setTranslate("Translate Example")
                .setWord("Example"));
        service.deleteById(item.getId());
        Item newItem = service.getById(item.getId());
        Assert.assertNull(newItem);
    }

    @Test
    public void testForGetAllItemsInThree() {
        service.create(new Item()
                .setTranslate("Translate Example 1")
                .setWord("Example 1"));

        service.create(new Item()
                .setTranslate("Translate Example 2")
                .setWord("Example 2"));

        service.create(new Item()
                .setTranslate("Translate Example 3")
                .setWord("Example 3"));

        Assert.assertTrue(service.getAll(null).size() >= 3);
    }

}
