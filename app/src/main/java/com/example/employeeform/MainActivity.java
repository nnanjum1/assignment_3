package com.example.employeeform;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.Objects;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private EditText edName, edEmail, edPhone, edPassword, edAge;
    private RadioGroup radioGroup;
    private Button btnSubmit,btnReset;
    String departments;
    LinearLayout inputLayout;
    private Pattern namePattern, emailPattern, phonePattern, passwordPattern;
    private Spinner spinner;
    CardView cardView;
    private TextView tvShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edName = findViewById(R.id.ed_name);
        edAge = findViewById(R.id.ed_age);
        edEmail = findViewById(R.id.ed_email);
        edPhone = findViewById(R.id.ed_phone);
        edPassword = findViewById(R.id.ed_password);
        radioGroup = findViewById(R.id.radio_group);
        btnSubmit = findViewById(R.id.btn_submit);
       btnReset=findViewById(R.id.btn_reset);
        namePattern = Pattern.compile("[a-zA-Z. ]+");
        emailPattern = Pattern.compile("[a-zA-Z0-9.]+@(gmail|yahoo|outlook)\\.com");
        phonePattern = Pattern.compile("^(\\+88)?01[3-9][0-9]{8}$");
        passwordPattern = Pattern.compile("^[a-zA-Z0-9]{6}$");

        spinner = findViewById(R.id.spinner);
        inputLayout = findViewById(R.id.input_layout);
        cardView = findViewById(R.id.cardview);
        tvShow = findViewById(R.id.tv_show);

        String[] dept = new String[]{"Select", "Human Resources (HR)", "Finance", "Marketing", "Sales",
                "Information Technology (IT)", "Operations", "Customer Service", "Product Management",
                "Administration", "Quality Assurance (QA)", "Supply Chain Management"};
        spinner.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, dept));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                departments = spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSubmit.setOnClickListener(v -> {
            String name = edName.getText().toString().trim();
            String email = edEmail.getText().toString().trim();
            String phone = edPhone.getText().toString().trim();
            String password = edPassword.getText().toString().trim();
            String age = edAge.getText().toString().trim();

            int selectedGenderId = radioGroup.getCheckedRadioButtonId();


            if (name.isEmpty() || age.isEmpty() || password.isEmpty() || email.isEmpty() || phone.isEmpty() ) {
                Toast.makeText(this, "Please fill up all fields!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (selectedGenderId == -1) {
                Toast.makeText(this, "Please select a gender!", Toast.LENGTH_SHORT).show();
                return;
            }


             if (!namePattern.matcher(name).matches()) {
                edName.setError("Name can only contain letters, dots, and spaces");
                edName.requestFocus();
                return;
            }

         if (!passwordPattern.matcher(password).matches()) {
                edPassword.setError("Password must contain 6 characters (letters and digits only)");
                edPassword.requestFocus();
                return;
            }

           if (!emailPattern.matcher(email).matches()) {
                edEmail.setError("Please enter a valid email (gmail, yahoo, outlook only)");
                edEmail.requestFocus();
                return;
            }

          if (!phonePattern.matcher(phone).matches()) {
                edPhone.setError("Only Bangladeshi phone numbers are allowed");
                edPhone.requestFocus();
                return;
            }

           if (Objects.equals(departments, "Select")) {
                Toast.makeText(this, "Please select your department", Toast.LENGTH_SHORT).show();
                return;
            }

            RadioButton radioValue=findViewById(selectedGenderId);
            String gender = radioValue.getText().toString();

            inputLayout.setVisibility(View.GONE);
            cardView.setVisibility(View.VISIBLE);
            String output = "Name: " + name + "\nGender: " + gender + "\nAge: " + age +
                    "\nEmail: " + email + "\nPhone no: " + phone + "\nDepartment: " + departments;
            tvShow.setText(output);
            btnReset.setVisibility(View.VISIBLE);

        });

        btnReset.setOnClickListener(v->{
            cardView.setVisibility(View.GONE);
            inputLayout.setVisibility(View.VISIBLE);
            edName.setText("");
            edAge.setText("");
            edEmail.setText("");
            edPhone.setText("");
            edPassword.setText("");
            radioGroup.clearCheck();
            spinner.setSelection(0);

        });

    }
}
