package de.stach.christoph.twitter.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import de.stach.christoph.twitter.R;
import de.stach.christoph.twitter.model.Contact;

public class ContactFormActivity extends AppCompatActivity {
    private EditText editTextFirstName, editTextLastName, editTextTelephoneNumber, editTextLongitude, editTextLatitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_form);

        editTextFirstName = (EditText) findViewById(R.id.editTextFirstName);
        editTextLastName = (EditText) findViewById(R.id.editTextLastName);
        editTextTelephoneNumber = (EditText) findViewById(R.id.editTextTelephoneNumber);
        editTextLongitude = (EditText) findViewById(R.id.editTextLongitude);
        editTextLatitude = (EditText) findViewById(R.id.editTextLatitude);
    }

    public void save(View view) {
        Intent response = new Intent();
        Contact contact = new Contact(
                editTextFirstName.getText().toString(),
                editTextLastName.getText().toString(),
                editTextTelephoneNumber.getText().toString(),
                editTextLongitude.getText().toString(),
                editTextLatitude.getText().toString()
        );

        response.putExtra("contact", contact);

        this.setResult(Activity.RESULT_OK, response);
        this.finish();
    }

    public void cancel(View view) {
        this.setResult(Activity.RESULT_CANCELED);
        this.finish();
    }
}
