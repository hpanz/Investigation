/*
 * Copyright (c) 2025 Wincom Consulting S.L.
 * All Rights Reserved.
 * This source is subject to the terms of a software license agreement.
 * You shall not disclose such confidential information and shall use it only in accordance with the terms and conditions of the license agreement.
 */
package com.wincomplm.wex.delivery.files.impl.helpers;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import com.wincomplm.wex.wt.framework.commons.system.WTConstants;

/**
 *
 * @author HaoPan hpan@wincom-plm.com
 */
public class HelperMethods {
    
    public static boolean isDockerInstalled() throws Exception {
        Process process = Runtime.getRuntime().exec("docker --version");
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = reader.readLine();
        
        if (line != null) {
            System.out.println("Docker Version: " + line);
            System.out.println("Docker is installed.");
            return true;
        } else {
            System.out.println("Docker is not installed.");
            return false;
        }
    }
    
    public static boolean isDockerRunning() throws Exception {
        Process process = Runtime.getRuntime().exec("docker info");
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        boolean isRunning = false;

        while ((line = reader.readLine()) != null) {
            if (line.contains("Server Version")) { // Ensures the daemon is responding
                isRunning = true;
                break;
            }
        }

        if (isRunning) {
            System.out.println("Docker daemon is running.");
        } else {
            System.out.println("Docker daemon is NOT running.");
        }

        return isRunning;
    }

    public static int executeDockerCompose(String... commands) throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder();

        // Build the command: docker compose <commands>
        processBuilder.command("docker");
        processBuilder.command().add("compose");
        for (String command : commands) {
            processBuilder.command().add(command);
        }
      //  processBuilder.command().addAll(Arrays.asList(commands));

        // Set the working directory to the location of docker-compose.yml
        processBuilder.directory(new File(WTConstants.WTHOME + "/wex/packages/com.wincomplm/wex-delivery-files/windchill/wex/com.wincomplm/wex-delivery-files/docker-rt/yml"));

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
}
