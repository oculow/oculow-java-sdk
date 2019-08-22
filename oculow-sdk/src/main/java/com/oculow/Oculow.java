package com.oculow;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.apache.commons.lang.StringUtils;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.oculow.Utils.Network.postRequest;

public class Oculow {
    enum Color
    {
        RED, GREEN, BLUE;
    }
    private final String reportBaseUrl = "http://127.0.0.1:5502/dashboard/executions.html";
    private final String url = "https://us-central1-lince-232621.cloudfunctions.net/";
    private final String processFunction = "process_image-dev";  // TODO PARAMETRIZE
    private final String executionStatusFunction = "get_execution_status-dev"; // TODO PARAMETRIZE

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    private String executionId;
    private String moduleApiKey = "0de8ef6f-7837-4deb-81ed-6837ab67da23";  // TODO PARAMETRIZE
    private String moduleAppId = "oculow";  // TODO PARAMETRIZE


    private int moduleComparisonLogic = 0;
    private int moduleBaselineManagement = 1;
    private String viewportWidth ="";
    private String viewportHeight = "";

    public void captureScreen(WebDriver driver){
        viewportWidth=String.valueOf(driver.manage().window().getSize().getWidth());
        viewportHeight= String.valueOf(driver.manage().window().getSize().getHeight());
        File id= new File(driver.getTitle()+".png");
        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        scrFile.renameTo(id);
        uploadImage(id);
        id.delete();
        scrFile.delete();

    }
    public void captureScreen(WebDriver driver, String baselineId){
        viewportWidth=String.valueOf(driver.manage().window().getSize().getWidth());
        viewportHeight= String.valueOf(driver.manage().window().getSize().getHeight());
        File id= new File(baselineId+".png");
        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        scrFile.renameTo(id);
        uploadImage(id);
        id.delete();
        scrFile.delete();

    }
    public String uploadImage(File image){
        return _uploadImage(image);
    }

    public String uploadImage(String imagePath){
        File image = new File(imagePath);
        return _uploadImage(image);
    }

    private String _uploadImage(File binaryFile){
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("api_key", moduleApiKey);
        parameters.put("app_id", moduleAppId);
        parameters.put("comparison_logic", String.valueOf(moduleComparisonLogic));
        parameters.put("baseline_management", String.valueOf(moduleBaselineManagement));
        parameters.put("viewport", "{\"width\": \""+viewportWidth+"\", \"height\": \""+viewportHeight+"\"}");
        if (executionId != null){
            parameters.put("execution_id", executionId);
        }
        String furl = url+processFunction;
        HttpResponse response = postRequest(furl, binaryFile,parameters);
        assert response.getStatusLine().getStatusCode()==200;
        try {
            executionId= StringUtils.chomp(EntityUtils.toString(response.getEntity())).replaceAll("^\"|\"$", "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return executionId;
    }

    public void setApiKey(String moduleApiKey) {
        this.moduleApiKey = moduleApiKey;
    }


    public void setAppId(String moduleAppId) {
        this.moduleAppId = moduleAppId;
    }

    public void setComparison(COMPARISON moduleComparisonLogic) {
        this.moduleComparisonLogic = moduleComparisonLogic.ordinal();
    }

    public void setBaselineManagement(MANAGEMENT moduleBaselineManagement) {
        this.moduleBaselineManagement = moduleBaselineManagement.ordinal();
    }

    public String getExecutionID() {
        return executionId;
    }

    public void dispose() {
        System.out.println(executionId);
        HttpResponse response = getResult();
        String results = null;
        try {
            results = EntityUtils.toString(response.getEntity());
            System.out.println(results);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert results != null;

        if (results.toLowerCase().contains("action required")) {
            System.out.println(String.format("Baseline action is required, visit %s?id=%s&app_id=%s&acc_id=%s", reportBaseUrl, executionId, moduleAppId, moduleApiKey));
        }
        else if (results.toLowerCase().contains("failed")) {
            System.out.println(String.format("Tests failed, please review at %s?id=%s", reportBaseUrl, executionId));
        }
        assert results.toLowerCase().contains("passed");

        System.out.println(String.format("To view a detailed report of the execution please navigate to %s?id=%s", reportBaseUrl, executionId));


    }
    public HttpResponse getResult() {
        String _url = url+ executionStatusFunction;
        List<NameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("api_key", moduleApiKey));
        parameters.add(new BasicNameValuePair("app_id", moduleAppId));
        parameters.add(new BasicNameValuePair("execution_id", executionId));
        return postRequest(_url, parameters);
    }

        }
