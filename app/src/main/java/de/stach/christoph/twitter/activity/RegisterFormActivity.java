package de.stach.christoph.twitter.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import de.stach.christoph.twitter.R;

public class RegisterFormActivity extends AppCompatActivity {
    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextTelephoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_form);

        editTextFirstName = (EditText) findViewById(R.id.editTextFirstName);
        editTextLastName = (EditText) findViewById(R.id.editTextLastName);
        editTextTelephoneNumber = (EditText) findViewById(R.id.editTextTelephoneNumber);
    }


    public void register(View view) {
        JSONObject json = new JSONObject();

        try {
            json.put("telefono", this.editTextTelephoneNumber.getText());
            json.put("nombre", this.editTextFirstName.getText());
            json.put("apellido", this.editTextLastName.getText());


            String url = "http://esecorporativo.com.mx/uninter/usuarios/create";

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    json,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(RegisterFormActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(RegisterFormActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
            );

            requestQueue.add(jsonObjectRequest);
        } catch (JSONException e) {

        }
    }

}
