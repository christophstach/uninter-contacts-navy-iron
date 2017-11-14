package de.stach.christoph.twitter.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import java.util.Arrays;
import java.util.List;

import de.stach.christoph.twitter.R;
import de.stach.christoph.twitter.adapter.ContactsAdapter;
import de.stach.christoph.twitter.model.Contact;

public class ContactsActivity extends AppCompatActivity {
    private Toolbar toolbarContacts;
    private ListView listViewContacts;
    private ProgressBar progressBarContacts;
    private List<Contact> contacts = new ArrayList<>();
    private ContactsAdapter contactsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        /* contacts.addAll(Arrays.asList(new Contact[]{
                new Contact("Christoph", "Stach", "+52 55 123 444"),
                new Contact("Annegret", "Stach", "+49 5923 6807"),
                new Contact("Rüdiger", "Stach", "+32 42334 121"),
                new Contact("Laila", "Westphal", "+39 123 4454")
        }));*/

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
        String url = "http://esecorporativo.com.mx/uninter/contactos/read/usuario_telefono=55555";

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

                        json.put("nombre", contact.getFirstName());
                        json.put("apellido", contact.getLastName());
                        json.put("telefono", contact.getTelephoneNumber());
                        json.put("usuario_telefono", "55555");


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
}
