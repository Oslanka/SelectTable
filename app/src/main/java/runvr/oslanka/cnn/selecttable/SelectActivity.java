package runvr.oslanka.cnn.selecttable;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class SelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        findViewById(R.id.user_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 18-3-15 用户设置
                startActivity(new Intent(SelectActivity.this,ChangePswActivity.class));
            }
        });
        findViewById(R.id.user_select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 18-3-15 统计查询
                startActivity(new Intent(SelectActivity.this,HistorySelectActivity.class));
            }
        });
        findViewById(R.id.image_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 18-3-15 签收单上传
                startActivity(new Intent(SelectActivity.this,ImageUploadActivity.class));

            }
        });
    }
}
