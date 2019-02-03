package server.controller;

import com.blade.mvc.annotation.*;
import com.blade.mvc.ui.RestResponse;
import org.bson.types.ObjectId;
import server.dao.DictionaryDao;
import server.model.item.CreateItem;
import server.model.item.Item;

import java.util.Collection;

@Path
public class DictionaryController {



    @GetRoute("/getAll")
    @JSON
    public RestResponse getAll(@Param(name = "search") String search) {
        Collection<Item> items = new DictionaryDao().getAll(search);
        return RestResponse.ok(com.alibaba.fastjson.JSON.toJSONString(items));
    }

    @GetRoute("/item/:id")
    @JSON
    public Item getById(@PathParam String id) {
        return new DictionaryDao().getById(new ObjectId(id));
    }


    @PostRoute("/item")
    @JSON
    public RestResponse addItem(@BodyParam Item item) {
        new DictionaryDao().create(item);
        return RestResponse.ok();
    }
}
