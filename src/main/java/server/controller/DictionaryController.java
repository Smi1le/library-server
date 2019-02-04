package server.controller;

import com.blade.mvc.annotation.*;
import com.blade.mvc.ui.RestResponse;
import org.bson.types.ObjectId;
import server.dao.DictionaryDao;
import server.model.item.CreateItem;
import server.model.item.Item;

import java.util.Collection;
import java.util.stream.Collectors;

@Path("/item")
public class DictionaryController {

    @GetRoute("/getAll")
    @JSON
    public RestResponse getAll(@Param(name = "search") String search) {
        Collection<Item> items = new DictionaryDao().getAll(search);
        for (Item item :items) {
            item.setItemId(item.getId().toString());
            item.setId(null);
        }
        return RestResponse.ok(com.alibaba.fastjson.JSON.toJSONString(items));
    }

    @GetRoute("/:id")
    @JSON
    public Item getById(@PathParam String id) {
        return new DictionaryDao().getById(new ObjectId(id));
    }


    @PostRoute
    @JSON
    public RestResponse addItem(@BodyParam Item item) {
        new DictionaryDao().create(item);
        return RestResponse.ok();
    }

    @PutRoute("/:id")
    @JSON
    public RestResponse updateItem(@PathParam(name = "id") String id, @BodyParam Item item) {
        item.setId(new ObjectId(id));
        new DictionaryDao().update(item);
        return RestResponse.ok();
    }

    @DeleteRoute("/:id")
    public RestResponse deleteItem(@PathParam(name = "id") String id) {
        new DictionaryDao().deleteById(new ObjectId(id));
        return RestResponse.ok();
    }
}
