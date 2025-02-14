/*
 * Copyright (c) 2025 Wincom Consulting S.L.
 * All Rights Reserved.
 * This source is subject to the terms of a software license agreement.
 * You shall not disclose such confidential information and shall use it only in accordance with the terms and conditions of the license agreement.
 */
package com.wincomplm.wex.delivery.files.impl.commands;

import com.wincomplm.wex.kernel.impl.annotations.WexComponent;
import com.wincomplm.wex.kernel.impl.annotations.WexMethod;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 *
 * @author HaoPan hpan@wincom-plm.com
 */
@WexComponent(uid = "docker-caller", description = "Methods for calling docker compose up or down")
public class WexCodeServerCommands {

    @WexMethod(name = "compose", description = "Entry point for up or down (in body of request)")
    public static void DockerCompose(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        try {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("data"); // Get 'data' parameter
        int myExitCode = 4000; // Default exit code

        // Log the received action
        System.out.println("Action received: " + action);

        // Validate input
        if (action == null || action.isEmpty()) {
            response.getWriter().write("{\"text\": \"Error: No action provided\"}");
            return;
        }
        // Check if action is valid and execute
        if (isUpOrDown(action)) {
            if (isUp(action)) {
                myExitCode = executeDockerCompose("up", "-d");
            } else if (isDown(action)) {
                myExitCode = executeDockerCompose("down");
            }

            // Send JSON response
            response.getWriter().write("{\"text\": \"Action received: " + action + ", Docker exited with code: " + myExitCode + "\"}");
        } else {
            response.getWriter().write("{\"text\": \"Error: Invalid action '" + action + "'\"}");
        }
        } catch (Exception e) {
            throw e;
        }
    }


    public static int executeDockerCompose(String... commands) throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder();

        // Build the command: docker compose <commands>
        processBuilder.command("docker", "compose");
        processBuilder.command().addAll(Arrays.asList(commands));

        // Set the working directory to the location of docker-compose.yml
        processBuilder.directory(new java.io.File("src/main/wex/windchill/wex/com.wincomplm/wex-delivery-files/docker-rt/yml"));

        Process process = processBuilder.start();
        printProcessOutput(process);
        int exitCode = process.waitFor();
        return exitCode;
    }

    private static void printProcessOutput(Process process) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream())); BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {

            reader.lines().forEach(System.out::println);
            errorReader.lines().forEach(System.err::println);
        }
    }

    private static boolean isUpOrDown(String action) {
        return isUp(action) || isDown(action);
    }

    private static boolean isUp(String action) {
        return action.equalsIgnoreCase("UP");
    }

    private static boolean isDown(String action) {
        return action.equalsIgnoreCase("DOWN");
    }
}
