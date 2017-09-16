package in.askdial.askdialsalescrm.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import in.askdial.askdialsalescrm.R;

public class Packages extends AppCompatActivity {
    LinearLayout linearLayout_starter, linearLayout_preimum, linearLayout_corporate;
    TextView textView_starter, textView_premium, textView_corporate;
    Button premium, starter, corporate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packages);
        linearLayout_starter = (LinearLayout) findViewById(R.id.ll_starter);
        linearLayout_preimum = (LinearLayout) findViewById(R.id.ll_premimum);
        linearLayout_corporate = (LinearLayout) findViewById(R.id.ll_corporate);
        textView_starter = (TextView) findViewById(R.id.txt_starter);
        textView_premium = (TextView) findViewById(R.id.txt_premium);
        textView_corporate = (TextView) findViewById(R.id.txt_corporate);
        starter = (Button) findViewById(R.id.button_addplan_starter);
        premium = (Button) findViewById(R.id.button_addplan_premium);
        corporate = (Button) findViewById(R.id.button_addplan_corporate);

        textView_starter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearLayout_starter.setVisibility(View.VISIBLE);
            }
        });
        textView_premium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearLayout_preimum.setVisibility(View.VISIBLE);
            }
        });

        textView_corporate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearLayout_corporate.setVisibility(View.VISIBLE);
            }
        });

        starter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Packages.this, Add_Coustmer.class);
                startActivity(intent);
            }
        });

        premium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Packages.this, Add_Coustmer.class);
                startActivity(intent);
            }
        });

        corporate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Packages.this, Add_Coustmer.class);
                startActivity(intent);
            }
        });


    }
}
