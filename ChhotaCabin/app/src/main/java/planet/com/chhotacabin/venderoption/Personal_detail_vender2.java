package planet.com.chhotacabin.venderoption;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import planet.com.chhotacabin.R;


public class Personal_detail_vender2 extends AppCompatActivity {
Spinner spinrState,spinrCity,spinrStatus;
    String[] stateA = {"Select State", "UP", "Delhi", "Rajasthan"};
    String[] cityA = {"Select City", "Muzaffarnagar", "Noida", "Shamli"};
    String[] statusA = {"Select Status", "Rent", "Lease"};
    Button btnNext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_detail_vender2);
        spinrState=findViewById(R.id.spinrState);
        spinrCity=findViewById(R.id.spinrCity);
        spinrStatus=findViewById(R.id.spinrStatus);
        btnNext=findViewById(R.id.btnNext);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Personal_detail_vender2.this, Personal_detail_vender3.class);
                startActivity(in);
            }
        });

        ArrayAdapter aa = new ArrayAdapter(Personal_detail_vender2.this,android.R.layout.simple_spinner_item,stateA);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinrState.setAdapter(aa);
        ArrayAdapter aa1 = new ArrayAdapter(Personal_detail_vender2.this, android.R.layout.simple_spinner_item, cityA);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinrCity.setAdapter(aa1);
        ArrayAdapter aa2 = new ArrayAdapter(Personal_detail_vender2.this, android.R.layout.simple_spinner_item, statusA);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinrStatus.setAdapter(aa2);

    }
}
