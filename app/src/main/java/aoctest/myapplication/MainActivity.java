package aoctest.myapplication;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.czg.soft.SoftRadioButton;
import com.czg.soft.SoftRadioGroup;

public class MainActivity extends AppCompatActivity implements SoftRadioGroup.OnCheckedChangeListener {
    SoftRadioGroup radiogroup;
    TextView text_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        radiogroup = (SoftRadioGroup) findViewById(R.id.radiogroup);
        text_content = (TextView) findViewById(R.id.text_content);
        radiogroup.setOnCheckedChangeListener(this);

    }

    @Override
    public void onCheckedChanged(SoftRadioGroup group, @IdRes int checkedId, boolean orientation) {
        SoftRadioButton softradiogroup = (SoftRadioButton) radiogroup.findViewById(checkedId);
        text_content.setText(softradiogroup.getText() + (orientation ? "上" : "下"));
    }
}
