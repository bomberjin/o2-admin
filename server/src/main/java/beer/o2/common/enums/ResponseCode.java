package beer.o2.common.enums;

/**
 * @Author:bomber
 * @Date:Created in 5:06 下午 2020/5/18
 * @Description:
 * @Modified By:
 */
public enum ResponseCode {

    /**
     * 通用-操作返回成功
     */
    SUCCESS(0, "操作成功"),
    INVALID_TOKEN(401, "无效token"),

    RESOURCE_DOES_NOT_EXIST(404, "路径不存在，请检查路径是否正确"),
    /**
     * 系统繁忙,请稍后再试
     */
    ERROR(99999, "系统开了个小差，请稍后再试");

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
