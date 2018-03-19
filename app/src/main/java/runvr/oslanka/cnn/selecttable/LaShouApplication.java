package runvr.oslanka.cnn.selecttable;

import android.app.Application;
import android.content.Context;

/**
 * Created by cnn on 18-3-13.
 */

public class LaShouApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext() {
        return context;
    }
}
