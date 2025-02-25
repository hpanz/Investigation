/*
 * Copyright (c) 2025 Wincom Consulting S.L.
 * All Rights Reserved.
 * This source is subject to the terms of a software license agreement.
 * You shall not disclose such confidential information and shall use it only in accordance with the terms and conditions of the license agreement.
 */
package com.wincomplm.wex.delivery.files.impl.methods;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wincomplm.wex.delivery.files.impl.helpers.HelperMethods;
import com.wincomplm.wex.kernel.impl.annotations.WexComponent;
import com.wincomplm.wex.kernel.impl.annotations.WexMethod;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import wt.httpgw.URLFactory;

/**
 *
 * @author HaoPan hpan@wincom-plm.com
 */
@WexComponent(uid = "methods", description = "Invokable methods")
public class WexMethods {

    public static boolean first_build = true;

    private class HttpServletRequestUtils {

        private final ObjectMapper objectMapper = new ObjectMapper();

        public <T> T getBodyAsObject(HttpServletRequest request, Class<T> clazz) throws IOException {
            return objectMapper.readValue(request.getReader(), clazz);
        }

        private String getBody(HttpServletRequest request) throws IOException {
            StringBuilder body = new StringBuilder();
            String line;

            // Get a BufferedReader from the request
            try (BufferedReader reader = request.getReader()) {
                while ((line = reader.readLine()) != null) {
                    body.append(line).append("\n"); // Append each line
                }
            }

            return body.toString().trim(); // Trim to remove extra newline
        }
    }

    private class HttpServletResponseUtils {

        public HttpServletResponse writeJsonResponse(HttpServletResponse response, JSONObject jsonResponse) throws IOException {
            response.setContentType("application/json"); // Set content type to JSON
            response.setCharacterEncoding("UTF-8"); // Ensure proper encoding
            response.getWriter().write(jsonResponse.toString()); // Write JSON to response
            response.getWriter().flush(); // Flush to send immediately
            return response;
        }

        public JSONObject getJSONResponseFromMap(Map<String, Object> data) throws JSONException {
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("status", "success");
            jsonResponse.put("message", "Hello from the server!");
            JSONObject dataForResponse = new JSONObject();
            for (String key : data.keySet()) {
                dataForResponse.put(key, data.get(key));
            }
            jsonResponse.put("data", dataForResponse);
            jsonResponse.put("responseTimestamp", System.currentTimeMillis());
            return jsonResponse;
        }
    }

    @WexMethod(name = "docker-caller", description = "Call Docker (up or down)")
    public HttpServletResponse callDocker(HttpServletRequest request, HttpServletResponse response) throws IOException, JSONException {
        HttpServletRequestUtils reqUtils = new HttpServletRequestUtils();
        HttpServletResponseUtils respUtil = new HttpServletResponseUtils();
        String requestBody = reqUtils.getBody(request);
        JSONObject jsonBody = new JSONObject(requestBody);
        JSONObject jsonResponse = new JSONObject();
        String action = null;
        String assignedPort = null;
        Map<String, Object> result = new HashMap();
        Map<String, Object> data = new HashMap();
        if (jsonBody.has("action")) {
            action = jsonBody.getString("action");
            try {
                int myExitCode = 0;
                data.put("received", action);
                String command = action;
                if (command.equalsIgnoreCase("up")) {
                    if (HelperMethods.isDockerInstalled() && HelperMethods.isDockerRunning()) {
                        if (first_build) {
                            int buildExitCode = HelperMethods.executeDockerCompose("build", "--no-cache");
                            if (buildExitCode != 0) {
                                // If the build fails, return an error message
                                result.put("error", "Docker build failed. Exit code: " + buildExitCode);
                                jsonResponse = respUtil.getJSONResponseFromMap(result);
                                return respUtil.writeJsonResponse(response, jsonResponse);
                            }
                            first_build = false;
                        }
                        myExitCode = HelperMethods.executeDockerCompose(command, "-d");
                        assignedPort = HelperMethods.getAssignedPort("coder-server", "8080");
                        data.put("assignedPort", assignedPort);
                    } else {
                        System.out.println("Not running or not installed");
                        data.put("error", "Please install docker and run it");
                    }
                } else {//assuming "down"
                    myExitCode = HelperMethods.executeDockerCompose(command);
                }
                if (command.equalsIgnoreCase("up") && myExitCode == 0) {
                    String url = new URLFactory().getBaseURL().getHost();
                    String port = (assignedPort == null) ?"8080":assignedPort;
                    String urlNPort =  url+ ":" +port+"/";
                    data.put("nextAction", "Navigate to URL: " +urlNPort);
                } else if (command.equalsIgnoreCase("down") && myExitCode == 0) {
                    data.put("nextAction", "Docker is down successfully");
                }
                data.put("comandExitCode", myExitCode);
                result.putAll(data);
                jsonResponse = respUtil.getJSONResponseFromMap(result);
            } catch (Exception e) {
                jsonResponse.append("Exception: ", e.getMessage());
            }
        } else {
            jsonResponse.append("data", "Hola Hao");
        }

        return respUtil.writeJsonResponse(response, jsonResponse);
    }
}
