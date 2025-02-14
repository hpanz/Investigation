/*
 * Copyright (c) 2025 Wincom Consulting S.L.
 * All Rights Reserved.
 * This source is subject to the terms of a software license agreement.
 * You shall not disclose such confidential information and shall use it only in accordance with the terms and conditions of the license agreement.
 */

package com.wincomplm.wex.delivery.files.impl.commands;
import com.wincomplm.wex.kernel.impl.annotations.WexComponent;
import com.wincomplm.wex.kernel.impl.annotations.WexMethod;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 *
 * @author HaoPan hpan@wincom-plm.com
 */
@WexComponent(uid="docker-check", description="check if docker is installed in the computer")
public class WexDockerInstallCheck {
    @WexMethod(name = "check", description = "return boolean if docker is installed")
    public boolean isDockerInstalled() throws Exception {
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
}


