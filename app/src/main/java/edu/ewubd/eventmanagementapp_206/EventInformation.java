package edu.ewubd.eventmanagementapp_206;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class EventInformation extends AppCompatActivity {

    private EditText etName, etPlace, etDateTime, etCapacity, etBudget, etEmail, etPhone, etDescription;
    private RadioButton rdIndoor, rdOutdoor, rdOnline;

    private String errors = "";

    // declare existingKey variable
    private String existingKey = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_information);

        findViewById(R.id.BtnCancel).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.BtnSave).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                save();
            }
        });

        etName = findViewById(R.id.NameInput);
        etPlace = findViewById(R.id.PlaceInput);
        etDateTime = findViewById(R.id.DnT);
        etCapacity = findViewById(R.id.CapacityInput);
        etBudget = findViewById(R.id.BudgetInput);
        etEmail = findViewById(R.id.EmailInput);
        etPhone = findViewById(R.id.PhoneInput);
        etDescription = findViewById(R.id.DescInput);

        rdIndoor = findViewById(R.id.RB1);
        rdOutdoor = findViewById(R.id.RB2);
        rdOnline = findViewById(R.id.RB3);

        // check in intent if there is any key set in putExtra
        Intent i = getIntent();
        existingKey = i.getStringExtra("EventKey");
        if (existingKey != null && !existingKey.isEmpty()) {
            initializeFormWithExistingData(existingKey);
        }

    }

    private void initializeFormWithExistingData(String eventKey) {

        String value = Util.getInstance().getValueByKey(this, eventKey);
        System.out.println("Key: " + eventKey + "; Value: " + value);

        if (value != null) {
            String[] fieldValues = value.split("-::-");
            String name = fieldValues[0];
            String place = fieldValues[1];
            String dateTime = fieldValues[2];
            String capacity = fieldValues[3];
            String budget = fieldValues[4];
            String email = fieldValues[5];
            String phone = fieldValues[6];
            String description = fieldValues[7];
            String eventType = fieldValues[8];

            etName.setText(name);
            etPlace.setText(place);
            etDateTime.setText(dateTime);
            etCapacity.setText(capacity);
            etBudget.setText(budget);
            etEmail.setText(email);
            etPhone.setText(phone);
            etDescription.setText(description);

            if (eventType.equalsIgnoreCase("INDOOR")) {
                rdIndoor.setChecked(true);
            } else if (eventType.equalsIgnoreCase("OUTDOOR")) {
                rdOutdoor.setChecked(true);
            } else if (eventType.equalsIgnoreCase("ONLINE")) {
                rdOnline.setChecked(true);
            }
        }
    }

    private void save() {
        String name = etName.getText().toString().trim();
        String place = etPlace.getText().toString().trim();
        String dateTime = etDateTime.getText().toString().trim();
        int capacity = Integer.parseInt(etCapacity.getText().toString().trim());
        int budget = Integer.parseInt(etBudget.getText().toString().trim());
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String description = etDescription.getText().toString().trim();

        boolean isIndoorChecked = rdIndoor.isChecked();
        boolean isOutdoorChecked = rdOutdoor.isChecked();
        boolean isOnlineChecked = rdOnline.isChecked();

        if (phone.length() < 8 || phone.length() > 12) {
            errors += "Phone Number isn't valid\n";
        }
        if (name.isEmpty()) {
            System.out.println("Enter event name");
        }
        if (place.isEmpty()) {
            System.out.println("Enter place");
        }
        if (dateTime.isEmpty()) {
            System.out.println("Enter date time");
        }
        if (capacity < 0) {
            System.out.println("Enter a valid capacity");
        }
        if (budget < 0) {
            System.out.println("Enter a valid budget");
        }
        if (email.isEmpty()) {
            System.out.println("Enter email");
        }
        if (phone.isEmpty()) {
            System.out.println("Enter phone number");
        }
        if (description.isEmpty()) {
            System.out.println("Enter description");
        }

        if (errors == "") {
            System.out.println(name);
            System.out.println(place);
            System.out.println(dateTime);
            System.out.println(capacity);
            System.out.println(budget);
            System.out.println(email);
            System.out.println(phone);
            System.out.println(description);

            System.out.println(isIndoorChecked);
            System.out.println(isOutdoorChecked);
            System.out.println(isOnlineChecked);

            String eventType = isIndoorChecked ? "Indoor" : isOutdoorChecked ? "Outdoor" : isOnlineChecked ? "Online" : "Undefined";


            //
            String key = name + "-::-" + dateTime + "-::-" + email;
            String value = name + "-::-" + place + "-::-" + dateTime + "-::-" + capacity + "-::-" + budget + "-::-" + email + "-::-" + phone + "-::-" + description + "-::-" + eventType;

            if (existingKey != null) {
                key = existingKey;
            }

            Util.getInstance().setKeyValue(this, key, value);
            System.out.println("Data Saved Successfully");

            // show success message to the user
            Toast.makeText(this, "Event information has been saved successfully", Toast.LENGTH_LONG).show();
            // if data is saved successfully, destroy the current page
            finish();

        } else {
            System.out.println("Error: " + errors);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        System.out.println("@EventInformationActivity-onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("@EventInformationActivity-onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("@EventInformationActivity-onPause()");
    }

    @Override
    public void onRestart() {
        super.onRestart();
        System.out.println("@EventInformationActivity-onRestart()");
    }

    @Override
    public void onStop() {
        super.onStop();
        System.out.println("@EventInformationActivity-onStop()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("@EventInformationActivity-onDestroy()");
    }

}