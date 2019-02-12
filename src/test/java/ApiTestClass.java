import com.blade.Blade;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.handler.codec.json.JsonObjectDecoder;
import lombok.SneakyThrows;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import server.Main;
import server.dao.impl.Database;
import server.model.database.DatabaseParameters;
import server.model.item.Item;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiTestClass {

    private static String idItemForUpdate;

    @BeforeClass
    public static void upBlade() {
        String[] args = {};
        Blade.of()
                .start(Main.class, args);
        Database.getInstance().setParameters(new DatabaseParameters()
                .setHost("localhost")
                .setPort("27017")
                .setDatabaseName("library"));
    }

    @Test
    public void givenRequestForCreateNewElementWithStatusCode_200() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost http = new HttpPost("http://localhost:9000/item");

        String json = "{\"word\":\"example from test\",\"translate\":\"example test api controllers and other\"}";
        StringEntity entity = new StringEntity(json);

        http.setEntity(entity);
        http.setHeader("Accept", "application/json");
        http.setHeader("Content-Type", "application/json");

        HttpResponse response = client.execute(http);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
        Object obj = com.alibaba.fastjson.JSON.parse(toJsonString(response.getEntity().getContent()));
        ObjectMapper objectMapper = new ObjectMapper();

        Item item = objectMapper.readValue(obj.toString(), Item.class);
        idItemForUpdate = item.getItemId();
        client.close();
    }

    @Test
    public void givenRequestForGettingItemByIdNewElementWithStatusCode_200() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpUriRequest http = new HttpGet("http://localhost:9000/item/" + idItemForUpdate);

        HttpResponse response = client.execute(http);
        Object obj = com.alibaba.fastjson.JSON.parse(toJsonString(response.getEntity().getContent()));
        ObjectMapper objectMapper = new ObjectMapper();

        Item item = objectMapper.readValue(obj.toString(), Item.class);
        Assert.assertNotNull(item);
        Assert.assertEquals(item.getWord(), "example from test some new word");
        Assert.assertEquals(item.getTranslate(), "example test api controllers and other some new word");
        client.close();
    }

    @Test
    public void updateWordAndTranslateItemById() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPut http = new HttpPut("http://localhost:9000/item/" + idItemForUpdate);

        String json = "{\"word\":\"example from test some new word\",\"translate\":\"example test api controllers and other some new word\"}";
        StringEntity entity = new StringEntity(json);

        http.setEntity(entity);
        http.setHeader("Accept", "application/json");
        http.setHeader("Content-Type", "application/json");

        HttpResponse response = client.execute(http);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_OK);

        HttpGet httpPost = new HttpGet("http://localhost:9000/item/" + idItemForUpdate);
        HttpResponse httpPostResponse = client.execute(httpPost);

        Object obj = com.alibaba.fastjson.JSON.parse(toJsonString(httpPostResponse.getEntity().getContent()));
        ObjectMapper objectMapper = new ObjectMapper();

        Item item = objectMapper.readValue(obj.toString(), Item.class);

        Assert.assertEquals(item.getWord(), "example from test some new word");
        Assert.assertEquals(item.getTranslate(), "example test api controllers and other some new word");

        client.close();
    }

    @Test
    public void givenRequestForGettingAllElementsWith_200_responseCode() throws ClientProtocolException, IOException {
        HttpUriRequest request = new HttpGet("http://localhost:9000/item/getAll");
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
    }

    @Test
    public void givenRequestForGettingAllElementsWithItems() throws ClientProtocolException, IOException {
        HttpUriRequest request = new HttpGet("http://localhost:9000/item/getAll");
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        JSONArray array = getPayload(response.getEntity().getContent());
        Assert.assertTrue(array.length() > 0);
    }

    @Test
    public void givenRequestForDeleteItemById() throws ClientProtocolException, IOException {
        HttpUriRequest request = new HttpGet("http://localhost:9000/item/" + idItemForUpdate);
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

//        JSONArray array = getPayload(response.getEntity().getContent());
        Assert.assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
    }

    private JSONArray getPayload(InputStream inputStream) {
        JSONObject jsonObject = toJsonObject(inputStream);
        Map<String, Object> map = jsonObject.toMap();
        JSONArray payloadJson = toJsonArray(new ByteArrayInputStream(map.get("payload").toString().getBytes(StandardCharsets.UTF_8)));
        return payloadJson;
    }

    private JSONObject toJsonObject(InputStream in) {
        return new JSONObject(toJsonString(in));
    }

    private JSONArray toJsonArray(InputStream in) {
        return new JSONArray(toJsonString(in));
    }

    @SneakyThrows
    private String toJsonString(InputStream in) {
        BufferedReader streamReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        StringBuilder responseStrBuilder = new StringBuilder();

        String inputStr;
        while ((inputStr = streamReader.readLine()) != null)
            responseStrBuilder.append(inputStr);
        String outputStr = responseStrBuilder.toString();
        return outputStr;
    }


}
