package planet.com.chhotacabin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PropertyUserTypeDetails extends AppCompatActivity {

    RadioButton IndivisualRadio,AgentRadio,OwnerRadio,BuilderRadio;

    String userType="";
    Button btnNext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.property_user_type_details);

        IndivisualRadio=findViewById(R.id.IndivisualRadio);
        AgentRadio=findViewById(R.id.AgentRadio);
        OwnerRadio=findViewById(R.id.OwnerRadio);
        BuilderRadio=findViewById(R.id.BuilderRadio);
        btnNext=findViewById(R.id.btnNext);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userType.equalsIgnoreCase("")){
                    Toast.makeText(PropertyUserTypeDetails.this, "Please Select any one", Toast.LENGTH_SHORT).show();
                }else {
                    Intent in = new Intent(PropertyUserTypeDetails.this, Owner_detail_vender.class);
                    startActivity(in);
                }
            }
        });

        IndivisualRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userType ="Indivisual";
            }
        });
        AgentRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userType ="Agent";
            }
        });
        OwnerRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userType ="Owner";
            }
        });
        BuilderRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userType ="Builder";
            }
        });

    }
}
