package de.stach.christoph.twitter.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.stach.christoph.twitter.R;
import de.stach.christoph.twitter.model.Contact;
import de.stach.christoph.twitter.model.User;

public class MapActivity extends AppCompatActivity {

    private String userJson;
    private MapFragment fragmentMap;
    private List<Contact> contacts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SharedPreferences sharedPreferences = this.getSharedPreferences("de.stach.christoph.twitter", Context.MODE_PRIVATE);
        this.userJson = sharedPreferences.getString("user", "");

        this.fragmentMap = (MapFragment) getFragmentManager().findFragmentById(R.id.fragmentMap);
        this.fragmentMap.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap googleMap) {
                RequestQueue requestQueue = Volley.newRequestQueue(MapActivity.this);
                User user = new Gson().fromJson(userJson, User.class);
                String url = "http://esecorporativo.com.mx/uninter/contactos/read/usuario_telefono=" + user.getTelephone();

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                        Request.Method.GET,
                        url,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    for (int i = 0; i < response.getJSONArray("data").length(); i++) {
                                        Contact contact = new Gson().fromJson(response.getJSONArray("data").get(i).toString(), Contact.class);

                                        contacts.add(contact);
                                        drawMarkers(googleMap);
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(MapActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(MapActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                );

                requestQueue.add(jsonObjectRequest);
            }
        });
    }

    private void drawMarkers(GoogleMap googleMap) {

        for (int i = 0; i < contacts.size(); i++) {
            Contact contact = contacts.get(i);

            if (contact.getLatitude() != 0 && contact.getLongitude() != 0) {
                LatLng cuernavaca = new LatLng(contact.getLatitude(), contact.getLongitude());
                googleMap.addMarker(new MarkerOptions().position(cuernavaca).title(contact.getFirstName() + " " + contact.getLastName()));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cuernavaca, 3));
            }
        }
    }
}
