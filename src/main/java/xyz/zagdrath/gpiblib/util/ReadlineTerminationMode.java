/*
 * @(#)ReadlineTerminationMode.java
 * 
 * Copyright (c) 2022 Cody L. Wellman. All rights reserved. This work is
 * licensed under the terms of the MIT license which can be found in the
 * root directory of this project.
 * 
 * Author: Cody L. Wellman <zecoderex@gmail.com>
 * 
 * Created: July 07, 2022
 * Updated: July 07, 2022
 */

package xyz.zagdrath.gpiblib.util;

public enum ReadlineTerminationMode {
    CR,
    LF,
    CR_LF,
    OPTCR_LF,
    LF_CR;
}
