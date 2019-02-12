package server.controller;

import com.blade.mvc.annotation.*;
import com.blade.mvc.ui.RestResponse;
import org.bson.types.ObjectId;
import server.model.item.Item;
import server.service.IDictionaryService;
import server.service.impl.DictionaryService;

import java.util.*;

@Path("/testItem")
public class DictionaryTestController {

    private final String testWord = "word";
    private final String testDesc = "desc";
    private final String testId = "id";

    @GetRoute("/getAll")
    @JSON
    public RestResponse getAll(@Param(name = "search") String search) {
        Collection<Item> items = Arrays.asList(
                getTestItem(testWord, testDesc, testId, "1"),
                getTestItem(testWord, testDesc, testId, "2"));
        return RestResponse.ok(items);
    }

    @GetRoute("/:id")
    @JSON
    public Item getById(@PathParam String id) {
        Item item = getTestItem(testWord, testDesc, testId, "_getted");
        item.setItemId(id);
        return item;
    }


    @PostRoute
    @JSON
    public RestResponse addItem(@BodyParam Item item) {
        return RestResponse.ok();
    }

    @PutRoute("/:id")
    @JSON
    public RestResponse updateItem(@PathParam(name = "id") String id, @BodyParam Item item) {
        return RestResponse.ok();
    }

    @DeleteRoute("/:id")
    public RestResponse deleteItem(@PathParam(name = "id") String id) {
        return RestResponse.ok();
    }

    static private Item getTestItem(String word, String desc, String id, String suf) {
        Item ret = new Item();
        ret.setItemId(id + suf);
        ret.setWord(word + suf);
        ret.setTranslate(desc + suf);
        return ret;
    }
}
