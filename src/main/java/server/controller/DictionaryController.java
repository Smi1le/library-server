package server.controller;

import com.blade.mvc.annotation.*;
import com.blade.mvc.ui.RestResponse;
import org.bson.types.ObjectId;
import server.model.item.Item;
import server.service.impl.DictionaryService;
import server.service.IDictionaryService;

import java.util.Collection;

@Path("/item")
public class DictionaryController {

    @GetRoute("/getAll")
    @JSON
    public RestResponse getAll(@Param(name = "search") String search) {
        IDictionaryService service = new DictionaryService();
        Collection<Item> items = service.getAll(search);
        return RestResponse.ok(com.alibaba.fastjson.JSON.toJSONString(items));
    }

    @GetRoute("/:id")
    @JSON
    public String getById(@PathParam String id) {
        IDictionaryService service = new DictionaryService();
        return com.alibaba.fastjson.JSON.toJSONString(service.getById(new ObjectId(id)));
    }


    @PostRoute
    @JSON
    public String addItem(@BodyParam Item item) {
        IDictionaryService service = new DictionaryService();
        return com.alibaba.fastjson.JSON.toJSONString(service.create(item));
    }

    @PutRoute("/:id")
    @JSON
    public RestResponse updateItem(@PathParam(name = "id") String id, @BodyParam Item item) {
        IDictionaryService service = new DictionaryService();
        item.setId(new ObjectId(id));
        service.update(item);
        return RestResponse.ok();
    }

    @DeleteRoute("/:id")
    public RestResponse deleteItem(@PathParam(name = "id") String id) {
        IDictionaryService service = new DictionaryService();
        service.deleteById(new ObjectId(id));
        return RestResponse.ok();
    }
}
