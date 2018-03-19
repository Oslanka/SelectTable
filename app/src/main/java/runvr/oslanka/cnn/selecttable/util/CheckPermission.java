package runvr.oslanka.cnn.selecttable.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

/**
 * Created by LaiYingtang on 2016/5/18.
 * <p/>
 * 检查权限的工具类
 */
public class CheckPermission {

    private Context context;

    //构造器
    private CheckPermission(Context context) {
        this.context = context.getApplicationContext();
    }

    private static CheckPermission permission;

    public static CheckPermission getInstance(Context context) {
        if (permission == null) {
            permission = new CheckPermission(context);
        }
        return permission;
    }

    //检查权限时，判断系统的权限集合
    public boolean permissionSet(String... permissions) {
        for (String permission : permissions) {
            if (isLackPermission(permission)) {//是否添加完全部权限集合
                return true;
            }
        }
        return false;
    }

    //检查系统权限是，判断当前是否缺少权限(PERMISSION_DENIED:权限是否足够)
    private boolean isLackPermission(String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED;
    }

}
