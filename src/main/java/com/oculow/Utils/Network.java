package com.oculow.Utils;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClientBuilder;

import javax.swing.text.html.parser.Entity;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;


public class Network {

    private static HttpResponse _postRequest(String url, HttpEntity entity) {
        HttpPost request = new HttpPost(url);
        request.setEntity(entity);
        HttpResponse response = null;
        HttpClient client = HttpClientBuilder.create().build();
        try {
            response = client.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    public static HttpResponse postRequest(String url, File binaryFile, Map<String,String> data){
        HttpEntity entity = generateFileEntity(binaryFile, data);
        return _postRequest(url, entity);
    }
    public static HttpResponse postRequest(String url, List<NameValuePair> data){
        HttpEntity entity = generateEntity(data);
        return  _postRequest(url, entity);
    }
    private static HttpEntity generateFileEntity(File binaryFile, Map<String,String> data) {
        MultipartEntityBuilder temp_entity = MultipartEntityBuilder.create().addPart("file", new FileBody(binaryFile));

        for(String key : data.keySet()){
            temp_entity.addTextBody(key, data.get(key));
        }

        return temp_entity.build();
    }
    private static HttpEntity generateEntity(List<NameValuePair> data) {
        UrlEncodedFormEntity temp_entity = null;
        try {
            temp_entity = new UrlEncodedFormEntity(data);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return temp_entity;
    }

}
