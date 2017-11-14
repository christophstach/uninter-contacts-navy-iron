package de.stach.christoph.twitter.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import de.stach.christoph.twitter.R;
import de.stach.christoph.twitter.model.User;

public class LoginActivity extends AppCompatActivity {
    private Button buttonLogin;
    private ImageView imageViewLogo;
    private EditText editTextTelephoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buttonLogin = (Button) findViewById(R.id.buttonLogIn);
        imageViewLogo = (ImageView) findViewById(R.id.imageViewLogo);
        editTextTelephoneNumber = (EditText) findViewById(R.id.editTextTelephoneNumber);
    }


    public void openMap(View view) {
        Intent map = new Intent(Intent.ACTION_VIEW);

        map.setData(Uri.parse("geo:52.4606615,13.5256051"));
        if (map.resolveActivity(getPackageManager()) != null) {
            startActivity(map);
        }
    }

    public void send(View view) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Hola Mundo");
        sendIntent.setType("text/plain");

        if (sendIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(sendIntent);
        }
    }

    public void logIn(View view) {
        if (!this.editTextTelephoneNumber.getText().equals("")) {
            final Intent intentContacts = new Intent(this, ContactsActivity.class);
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String url = "http://esecorporativo.com.mx/uninter/usuarios/read/telefono=" + this.editTextTelephoneNumber.getText();

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    url,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                User user = new Gson().fromJson(response.getJSONArray("data").getJSONObject(0).toString(), User.class);
                                startActivity(intentContacts);
                            } catch (Exception e) {
                                Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
            );

            requestQueue.add(jsonObjectRequest);
        }
    }

    public void register(View view) {
        Intent intentContacts = new Intent(this, RegisterFormActivity.class);
        startActivity(intentContacts);
    }

    public void swapImage(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 123);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 123 && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            imageViewLogo.setImageURI(uri);
        }
    }
}
