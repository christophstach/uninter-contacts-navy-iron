package de.stach.christoph.twitter.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.stach.christoph.twitter.R;
import de.stach.christoph.twitter.adapter.ContactsAdapter;
import de.stach.christoph.twitter.model.Contact;
import de.stach.christoph.twitter.model.User;

public class ContactsActivity extends AppCompatActivity {
    private Toolbar toolbarContacts;
    private ListView listViewContacts;
    private ProgressBar progressBarContacts;
    private List<Contact> contacts = new ArrayList<>();
    private ContactsAdapter contactsAdapter;
    private String userJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        SharedPreferences sharedPreferences = this.getSharedPreferences("de.stach.christoph.twitter", Context.MODE_PRIVATE);
        this.userJson = sharedPreferences.getString("user", "");

        getLocation();

        listViewContacts = (ListView) findViewById(R.id.listViewContacts);
        contactsAdapter = new ContactsAdapter(contacts, this);
        listViewContacts.setAdapter(contactsAdapter);

        listViewContacts.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact contact = (Contact) parent.getAdapter().getItem(position);
                LinearLayout layout = (LinearLayout) view;
                ColorDrawable background = (ColorDrawable) layout.getBackground();

                if (background != null && background.getColor() == Color.LTGRAY) {
                    layout.setBackgroundColor(Color.TRANSPARENT);
                }
            }
        });

        listViewContacts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Contact contact = (Contact) parent.getAdapter().getItem(position);
                LinearLayout layout = (LinearLayout) view;

                layout.setBackgroundColor(Color.LTGRAY);
                return true;
            }


        });


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        User user = new Gson().fromJson(this.userJson, User.class);
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
                            }

                            contactsAdapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            Toast.makeText(ContactsActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ContactsActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contact_menu, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_contact:
                Intent intentContactForm = new Intent(this, ContactFormActivity.class);
                startActivityForResult(intentContactForm, 1);

                return true;

            case R.id.menu_remove_contact:
                Toast.makeText(this, "Remove Contact", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.menu_open_map:
                Intent mapActivity = new Intent(this, MapActivity.class);
                startActivity(mapActivity);

                return true;


            default:
                Toast.makeText(this, "Invalid Action", Toast.LENGTH_SHORT).show();
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == Activity.RESULT_OK) {
                    final Contact contact = (Contact) data.getSerializableExtra("contact");
                    JSONObject json = new JSONObject();

                    try {
                        User user = new Gson().fromJson(this.userJson, User.class);

                        json.put("nombre", contact.getFirstName());
                        json.put("apellido", contact.getLastName());
                        json.put("telefono", contact.getTelephoneNumber());
                        json.put("usuario_telefono", user.getTelephone());


                        RequestQueue requestQueue = Volley.newRequestQueue(this);
                        String url = "http://esecorporativo.com.mx/uninter/contactos/create";

                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                                Request.Method.POST,
                                url,
                                json,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        contacts.add(contact);
                                        contactsAdapter.notifyDataSetChanged();

                                        Toast.makeText(ContactsActivity.this, "Contact saved", Toast.LENGTH_SHORT).show();
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(ContactsActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                        );

                        requestQueue.add(jsonObjectRequest);
                    } catch (JSONException e) {

                    }
                }
                break;
        }
    }

    protected void getLocation() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Toast.makeText(ContactsActivity.this, "Location: " + location.getLongitude() + "," + location.getLatitude(), Toast.LENGTH_SHORT).show();


                try {
                    User user = new Gson().fromJson(ContactsActivity.this.userJson, User.class);
                    JSONObject json = new JSONObject();

                    json.put("nombre", user.getFirstName());
                    json.put("apellido", user.getLastName());

                    json.put("latitud", location.getLatitude());
                    json.put("longitud", location.getLongitude());

                    RequestQueue requestQueue = Volley.newRequestQueue(ContactsActivity.this);
                    String url = "http://esecorporativo.com.mx/uninter/usuarios/update/" + user.getTelephone();

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                            Request.Method.POST,
                            url,
                            json,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(ContactsActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                    );

                    requestQueue.add(jsonObjectRequest);
                } catch (JSONException e) {

                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the userJson grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }
}
