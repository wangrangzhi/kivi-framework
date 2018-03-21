package com.kivi.framework.constant;

/**
 * 公共错误码定义类，其他业务模块的错误码需继承此类后，添加各自模块的本身的错误码
 * 
 * @author Eric
 *
 */
public class GlobalErrorConst {

    /** 成功 */
    public static final String SUCCESS                = "S0000000";

    /** 用户未认证通过 */
    public static final String E_USER_NOT_AUTH        = "E0000100";

    /** 记录信息已存在 */
    public static final String E_RECORD_NOT_EXIST     = "E8000008";
    /** 记录信息已存在 */
    public static final String E_RECORD_EXIST         = "E8000009";
    /** 记录信息不唯一 */
    public static final String E_RECORD_NOT_UNIQUE    = "E8000010";
    /** 系统忙，请稍后再提交 */
    public static final String E_TRAN_SYSTEM_BUSY     = "E9000001";
    /** 交易请求时间间隔太短 */
    public static final String E_TRAN_TOO_FREQUENTLY  = "E9000002";
    /** 数据更新影响行数异常 */
    public static final String E_DB_UPDATE_ROW        = "E9000003";
    /** 数据更新异常 */
    public static final String E_DB_UPDATE            = "E9000004";
    /** 数据插入影响行数异常 */
    public static final String E_DB_INSERT_ROW        = "E9000005";
    /** 数据插入异常 */
    public static final String E_DB_INSERT            = "E9000006";
    /** 数据库查询异常 */
    public static final String E_DB_QUERY             = "E9000007";
    /** 数据库异常 */
    public static final String E_DB_ERROR             = "E9000008";
    /** 数据拷贝失败 */
    public static final String E_OBJECT_COPY          = "E9000010";
    /** 数据校验异常 */
    public static final String E_OBJECT_VERIFY        = "E9000011";
    /** 数据转换异常 */
    public static final String E_OBJECT_CONVERT       = "E9000012";
    /** 请求参数为NULL */
    public static final String E_PARAM_IS_NULL        = "E9000013";
    /** 字符串格式不正确 */
    public static final String E_STRING_FORMAT        = "E9000014";
    /** 文件内容格式不正确 */
    public static final String E_FILE_DATA_FORMAT     = "E9000015";
    /** 请求参数无效 */
    public static final String E_PARAM_INVALID        = "E9000016";
    /** 对象实例化异常 */
    public static final String E_OBJECT_INSTANCE      = "E9000017";
    /** RSA密钥加载异常 */
    public static final String E_RSA_KEY_LOAD         = "E9000018";
    /** RSA签名异常 */
    public static final String E_RSA_SIGN             = "E9000019";
    /** RSA验证签名异常 */
    public static final String E_RSA_VERIFY           = "E9000020";
    /** RSA加密异常 */
    public static final String E_RSA_ENCRYPT          = "E9000021";
    /** RSA解密异常 */
    public static final String E_RSA_DECRYPT          = "E9000022";
    /** 签名算法不支持 */
    public static final String E_SIGN_ALG_NOT_SUPPORT = "E9000023";
    /** 字符集转换时异常 */
    public static final String E_CHARSET_CONVERT      = "E9999990";
    /** 未知交易客户端来源 */
    public static final String E_UNKNOWN_CLIENT       = "E9999991";
    /** 未实现 */
    public static final String E_NOT_IMPLEMENT        = "E9999992";
    /** 文件不存在 */
    public static final String E_FILE_NOT_FOUND       = "E9999993";
    /** 文件写入异常 */
    public static final String E_FILE_WRITE           = "E9999994";
    /** 文件读取异常 */
    public static final String E_FILE_READ            = "E9999995";
    /** 发送请求数据网络异常 */
    public static final String E_NET_SEND             = "E9999996";
    /** 服务器连接失败 */
    public static final String E_NET_CONNECT          = "E9999997";
    /** 未知错误 */
    public static final String E_UNDEFINED            = "E9999998";
    /** 系统错误 */
    public static final String E_SYSTEM               = "E9999999";

}
