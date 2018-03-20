package com.cloud.lashou.utils;

import android.text.TextUtils;
import android.util.Log;

/**
 * Log工具，类似android.util.Log。
 * tag自动产生，格式: AppTagPrefix:className.methodName(L:lineNumber),
 * customTagPrefix为空时只输出：className.methodName(L:lineNumber)。
 * <p/>
 */
public class LogUtils {

    public static String appTagPrefix = "";

    private LogUtils() {
    }

    /**
     * 全局调试信息开关，true打开调试，false关闭调试
     */
    public static boolean allow = true; // TODO: 2017/12/4 全局调试信息开关，打线上包的时候注意关闭，true打开调试，false关闭调试

    /**
     * 生成Tag日志，到方法的调用栈信息
     *
     * @param caller
     * @return
     */
    private static String generateTag(StackTraceElement caller) {
        String tag = "%s.%s(L:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
        tag = TextUtils.isEmpty(appTagPrefix) ? tag : appTagPrefix + ":" + tag;
        return tag;
    }

    public static AppLogger appLogger;

    public interface AppLogger {
        void d(String tag, String content);

        void d(String tag, String content, Throwable tr);

        void e(String tag, String content);

        void e(String tag, String content, Throwable tr);

        void i(String tag, String content);

        void i(String tag, String content, Throwable tr);

        void v(String tag, String content);

        void v(String tag, String content, Throwable tr);

        void w(String tag, String content);

        void w(String tag, String content, Throwable tr);

        void w(String tag, Throwable tr);

        void wtf(String tag, String content);

        void wtf(String tag, String content, Throwable tr);

        void wtf(String tag, Throwable tr);
    }

    public static void d(String content) {
        if (!allow) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (appLogger != null) {
            appLogger.d(tag, content);
        } else {
            if (null != content) {
                Log.d(tag, content);
            }
        }
    }

    public static void d(String content, Throwable tr) {
        if (!allow) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (appLogger != null) {
            appLogger.d(tag, content, tr);
        } else {
            if (null != content) {
                Log.d(tag, content, tr);
            }
        }
    }

    public static void e(String content) {
        if (!allow) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (appLogger != null) {
            appLogger.e(tag, content);
        } else {
            if (null != content) {
                Log.e(tag, content);
            }
        }
    }

    public static void e(String content, Throwable tr) {
        //  if (!allow) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (appLogger != null) {
            appLogger.e(tag, content, tr);
        } else {
            if (null != content) {
                Log.e(tag, content, tr);
            }
        }
    }

    public static void i(String content) {
        if (!allow) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (appLogger != null) {
            appLogger.i(tag, content);
        } else {
            if (null != content) {
                Log.i(tag, content);
            }
        }
    }

    public static void i(String content, Throwable tr) {
        if (!allow) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (appLogger != null) {
            appLogger.i(tag, content, tr);
        } else {
            if (null != content) {
                Log.i(tag, content, tr);
            }
        }
    }

    public static void v(String content) {
        if (!allow) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (appLogger != null) {
            appLogger.v(tag, content);
        } else {
            if (null != content) {
                Log.v(tag, content);
            }
        }
    }

    public static void v(String content, Throwable tr) {
        if (!allow) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (appLogger != null) {
            appLogger.v(tag, content, tr);
        } else {
            if (null != content) {
                Log.v(tag, content, tr);
            }
        }
    }

    public static void w(String content) {
        if (!allow) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (appLogger != null) {
            appLogger.w(tag, content);
        } else {
            if (null != content) {
                Log.w(tag, content);
            }
        }
    }

    public static void w(String content, Throwable tr) {
        if (!allow) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (appLogger != null) {
            appLogger.w(tag, content, tr);
        } else {
            if (null != content) {
                Log.w(tag, content, tr);
            }
        }
    }

    public static void w(Throwable tr) {
        if (!allow) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (appLogger != null) {
            appLogger.w(tag, tr);
        } else {
            if (null != tr) {
                Log.w(tag, tr);
            }
        }
    }


    public static void wtf(String content) {
        if (!allow) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (appLogger != null) {
            appLogger.wtf(tag, content);
        } else {
            if (null != content) {
                Log.wtf(tag, content);
            }
        }
    }

    public static void wtf(String content, Throwable tr) {
        if (!allow) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (appLogger != null) {
            appLogger.wtf(tag, content, tr);
        } else {
            if (null != content && null != tr) {
                Log.wtf(tag, content, tr);
            }
        }
    }

    public static void wtf(Throwable tr) {
        if (!allow) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (appLogger != null) {
            appLogger.wtf(tag, tr);
        } else {
            if (null != tr) {
                Log.wtf(tag, tr);
            }
        }
    }

    public static StackTraceElement getCurrentStackTraceElement() {
        return Thread.currentThread().getStackTrace()[3];
    }

    public static StackTraceElement getCallerStackTraceElement() {
        return Thread.currentThread().getStackTrace()[4];
    }
}
