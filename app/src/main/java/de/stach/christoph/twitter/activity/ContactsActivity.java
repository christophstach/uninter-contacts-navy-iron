package de.stach.christoph.twitter.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import de.stach.christoph.twitter.R;
import de.stach.christoph.twitter.adapter.ContactsAdapter;
import de.stach.christoph.twitter.model.Contact;

public class ContactsActivity extends AppCompatActivity {
    private ListView listViewContacts;
    private ProgressBar progressBarContacts;
    private Contact[] contacts = new Contact[]{
            new Contact("Christoph", "Stach", "+52 55 123 444"),
            new Contact("Annegret", "Stach", "+49 5923 6807"),
            new Contact("Rüdiger", "Stach", "+32 42334 121"),
            new Contact("Laila", "Westphal", "+39 123 4454")
    };
    private ContactsAdapter contactsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        listViewContacts = (ListView) findViewById(R.id.listViewContacts);
        progressBarContacts = (ProgressBar) findViewById(R.id.progressBarContacts);
        contactsAdapter = new ContactsAdapter(contacts, this);
        listViewContacts.setAdapter(contactsAdapter);


        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBarContacts.setVisibility(View.GONE);
                listViewContacts.setVisibility(View.VISIBLE);
            }
        }, 5000);*/

        this.listViewContacts.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact contact = (Contact) parent.getAdapter().getItem(position);

                Toast.makeText(ContactsActivity.this, contact.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
