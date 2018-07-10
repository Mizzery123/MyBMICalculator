package sg.edu.rp.c346.mybmicalculator;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etweight, etheight;
    TextView tvdate, tvbmi, tvOut;
    Button btncal, btnreset;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etheight = findViewById(R.id.editTextHeight);
        etweight = findViewById(R.id.editTextWeight);
        tvbmi = findViewById(R.id.textViewBMI);
        tvdate = findViewById(R.id.textViewDate);
        tvOut = findViewById(R.id.textViewOutcome);
        btncal = findViewById(R.id.buttonCal);
        btnreset = findViewById(R.id.buttonReset);






        btncal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
                String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                        (now.get(Calendar.MONTH)+1) + "/" +
                        now.get(Calendar.YEAR) + " " +
                        now.get(Calendar.HOUR_OF_DAY) + ":" +
                        now.get(Calendar.MINUTE);

                float weight = Float.parseFloat(etweight.getText().toString());
                float height = Float.parseFloat(etheight.getText().toString());
                float bmi = (weight / (height * height));
                tvdate.setText("Last calculated Date: "+ datetime);
                tvbmi.setText(String.format("%s %.3f ", "Last Calculated BMI:",bmi));
                tvOut.setText("You are "+interpretBMI(bmi));

            }
        });

        btnreset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor prefEdit = prefs.edit();
                prefEdit.clear();
                prefEdit.commit();


                etheight.setText("");
                etweight.setText("");
                tvdate.setText("Last Calculated Date: ");
                tvbmi.setText("Last Calculated BMI: ");




            }
        });



    }

    @Override
    protected void onPause() {
        super.onPause();
        String strDate = tvdate.getText().toString();
        String strBMI = tvbmi.getText().toString();
        float strWeight = Float.parseFloat(etweight.getText().toString());
        float strHeight = Float.parseFloat(etheight.getText().toString());
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefEdit = prefs.edit();
        prefEdit.putString("date", strDate);
        prefEdit.putString("bmi", strBMI);
        prefEdit.putFloat("weight",strWeight);
        prefEdit.putFloat("height", strHeight);
        prefEdit.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String date = prefs.getString("date", "");
        String bmi = prefs.getString("bmi", "");
        float msg = prefs.getFloat("weight", 0);
        float msg1 = prefs.getFloat("height", 0);


        tvdate.setText(date);
        tvbmi.setText(bmi);

    }

    // Interpret what BMI means
    private String interpretBMI(float bmiValue) {
        if (bmiValue < 18.5) {

            return "Underweight";
        } else if (bmiValue < 25) {

            return "Normal";
        } else if (bmiValue < 30) {

            return "Overweight";
        } else {
            return "Obese";
        }
    }
}
