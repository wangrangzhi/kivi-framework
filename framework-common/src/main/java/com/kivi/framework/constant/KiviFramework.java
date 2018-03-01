package com.kivi.framework.constant;

public interface KiviFramework {
    /**
     * Jyt框架属性文件
     */
    public static final String FRAMWORK_PLACEHOLDER = "${framework.config-file}";

    /**
     * dubbo属性文件
     */
    public static final String DUBBO_PLACEHOLDER    = "${framework.dubbo-file}";

    /** redisson 配置文件 */
    public static final String REDISSON_CONFIG      = "${framework.redisson.config-file}";

    public interface BasePackages {
        /** Component基础扫描包 */
        public static final String COMPONENT_SCAN = "${framework.common.component-scan}";
    }
}
