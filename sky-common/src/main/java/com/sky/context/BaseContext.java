package com.sky.context;

public class BaseContext {
    // 存储当前登录用户id
    public static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    // 设置为当前登录用户id
    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }
    // 获取当前登录用户id
    public static Long getCurrentId() {
        return threadLocal.get();
    }
    // 清除对象
    public static void removeCurrentId() {
        threadLocal.remove();
    }

}
