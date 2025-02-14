/*
 * Copyright (c) 2025 Wincom Consulting S.L.
 * All Rights Reserved.
 * This source is subject to the terms of a software license agreement.
 * You shall not disclose such confidential information and shall use it only in accordance with the terms and conditions of the license agreement.
 */

package com.wincomplm.wex.delivery.files.impl.resources;

import com.wincomplm.wex.kernel.impl.annotations.WexComponent;
import wt.util.resource.RBEntry;
import wt.util.resource.RBNameException;
import wt.util.resource.RBUUID;
import wt.util.resource.WTListResourceBundle;

/**
 *
 * @author HaoPan hpan@wincom-plm.com
 */

@WexComponent(uid = "com.wincomplm.wex.delivery.files.resources.WexResources", description = "Resource bundle")
@RBUUID("com.wincomplm.wex.delivery.files.resources.WexResources")
@RBNameException
public class WexResources extends WTListResourceBundle {

    @RBEntry("Delivery Files")
    public static final String PRIVATE_CONSTANT_0 = "com.wincomplm.wex.delivery.files.edkDeliveryNonPop.description";
    @RBEntry("Delivery Files")
    public static final String PRIVATE_CONSTANT_1 = "com.wincomplm.wex.delivery.files.edkDeliveryNonPop.tooltip";
    @RBEntry("Delivery Files")
    public static final String PRIVATE_CONSTANT_2 = "com.wincomplm.wex.delivery.files.edkDeliveryNonPop.activetooltip";

    @RBEntry("Delivery Files2")
    public static final String PRIVATE_CONSTANT_3 = "com.wincomplm.wex.delivery.files.dockerCommandCalls.description";
    @RBEntry("Delivery Files2")
    public static final String PRIVATE_CONSTANT_4 = "com.wincomplm.wex.delivery.files.dockerCommandCalls.tooltip";
    @RBEntry("Delivery Files2")
    public static final String PRIVATE_CONSTANT_5 = "com.wincomplm.wex.delivery.files.dockerCommandCalls.activetooltip";
}
