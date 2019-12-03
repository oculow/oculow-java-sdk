package com.oculow;

import org.json.*;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.apache.commons.lang.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.oculow.Utils.Network.postRequest;

public class Oculow {
    private final String reportBaseUrl = "http://www.oculow.com/dashboard/executions.html";
    private final String url = "https://us-central1-lince-232621.cloudfunctions.net/";
    private final String processFunction = "process_image-dev";  // TODO PARAMETRIZE
    private final String executionStatusFunction = "get_execution_status-dev"; // TODO PARAMETRIZE
    private String moduleAccID = null;

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }
    public void setAccID(String moduleAccID) {
        this.moduleAccID = moduleAccID;
    }
    private String executionId;
    private String moduleApiKey = null;
    private String moduleSecretKey = null;
    private String moduleAppId = null;


    private int moduleComparisonLogic = 0;
    private int moduleBaselineManagement = 1;
    private String viewportWidth ="";
    private String viewportHeight = "";

    private void _captureScreen(WebDriver driver, String title) {
        assert moduleApiKey != null;
        assert moduleSecretKey != null;
        assert moduleAppId != null;
        setResolution(
                String.valueOf(driver.manage().window().getSize().getWidth()),
                String.valueOf(driver.manage().window().getSize().getHeight())
        );
        File id = new File(title + ".png");
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        scrFile.renameTo(id);
        uploadImage(id);
        id.delete();
        scrFile.delete();
    }

    public void captureScreen(WebDriver driver){
        _captureScreen(driver, driver.getTitle());

    }



    public void captureScreen(WebDriver driver, String baselineId){
        _captureScreen(driver, baselineId);

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
        parameters.put("api_key", moduleApiKey+"__"+moduleSecretKey);
        parameters.put("app_id", moduleAppId);
        parameters.put("comparison_logic", String.valueOf(moduleComparisonLogic));
        parameters.put("baseline_management", String.valueOf(moduleBaselineManagement));
        if (this.viewportWidth.equals("") || this.viewportHeight.equals("")){
            try {
                BufferedImage bimg = ImageIO.read(binaryFile);
                this.viewportWidth = String.valueOf(bimg.getWidth());
                this.viewportHeight = String.valueOf(bimg.getHeight());
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error detecting image size, setting 0,0 as default size.");
            }
        }
        parameters.put(
                "viewport",
                "{\"width\": \""+this.viewportWidth+"\", \"height\": \""+this.viewportHeight+"\"}"
        );
        if (executionId != null){
            parameters.put("execution_id", executionId);
        }
        String furl = url+processFunction;
        HttpResponse response = postRequest(furl, binaryFile,parameters);
        assert response.getStatusLine().getStatusCode()==200;
        try {
            JSONObject obj = new JSONObject(StringUtils.chomp(EntityUtils.toString(response.getEntity())).replaceAll("\"", ""));
            if (moduleAccID == null){
                moduleAccID = obj.getString("acc_id");
            }
            executionId= obj.getString("execution_id");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return executionId;
    }

    public void setApiKey(String apiKey, String secretKey) {
        this.moduleApiKey = apiKey;
        this.moduleSecretKey = secretKey;
    }


    public void setAppId(String moduleAppId) {
        this.moduleAppId = moduleAppId;
    }

    public void setResolution(String width, String height) {
        this.viewportWidth = width;
        this.viewportHeight = height;
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
            System.out.println(String.format("Baseline action is required, visit %s?id=%s&app_id=%s&acc_id=%s", reportBaseUrl, executionId, moduleAppId, moduleAccID));
        }
        else if (results.toLowerCase().contains("failed")) {
            System.out.println(String.format("Tests failed, please review at %s?id=%s&app_id=%s&acc_id=%s", reportBaseUrl, executionId, moduleAppId, moduleAccID));
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
