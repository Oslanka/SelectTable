package runvr.oslanka.cnn.selecttable;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * Created by cnn on 18-3-17.
 */

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_base);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getMyTitle());
        LayoutInflater.from(this).inflate(getResId(), (ViewGroup) findViewById(R.id.container));
    }

    public abstract String getMyTitle();

    public abstract int getResId();
}
