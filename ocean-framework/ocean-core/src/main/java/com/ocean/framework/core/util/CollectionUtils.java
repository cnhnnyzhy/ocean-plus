package com.ocean.framework.core.util;

import java.util.Collection;

/**
 * 集合工具类
 *
 * @author ocean
 * @date 2024/5/1
 */
public class CollectionUtils {

    public static boolean isEmpty(Collection coll) {
        return (coll == null || coll.isEmpty());
    }

    public static boolean isNotEmpty(Collection coll) {
        return !isEmpty(coll);
    }
}
