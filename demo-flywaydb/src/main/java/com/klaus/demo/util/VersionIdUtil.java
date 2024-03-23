package com.klaus.demo.util;

import cn.hutool.core.util.IdUtil;

/**
 * @author Klaus
 */
public class VersionIdUtil {


    public static String generateLatestId() {



        return String.valueOf(IdUtil.getSnowflakeNextId());
    }
}
