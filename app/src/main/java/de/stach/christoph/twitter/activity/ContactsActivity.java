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

        contacts.addAll(Arrays.asList(new Contact[]{
                new Contact("Christoph", "Stach", "+52 55 123 444"),
                new Contact("Annegret", "Stach", "+49 5923 6807"),
                new Contact("Rüdiger", "Stach", "+32 42334 121"),
                new Contact("Laila", "Westphal", "+39 123 4454")
        }));

        listViewContacts = (ListView) findViewById(R.id.listViewContacts);
        contactsAdapter = new ContactsAdapter(contacts, this);
        listViewContacts.setAdapter(contactsAdapter);

        listViewContacts.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact contact = (Contact) parent.getAdapter().getItem(position);
                LinearLayout layout = (LinearLayout) view;
                ColorDrawable background = (ColorDrawable) layout.getBackground();

                if(background != null && background.getColor() == Color.LTGRAY) {
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
                    Contact contact = (Contact) data.getSerializableExtra("contact");
                    this.contacts.add(contact);
                    this.contactsAdapter.notifyDataSetChanged();
                }
                break;
        }
    }
}
