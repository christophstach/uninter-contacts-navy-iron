package de.stach.christoph.twitter.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.stach.christoph.twitter.R;
import de.stach.christoph.twitter.model.Contact;

/**
 * Created by christoph on 07.09.17.
 * <p>
 * ContactsAdapter
 */
public class ContactsAdapter extends BaseAdapter {
    private Contact[] contacts;
    private Activity activity;

    public ContactsAdapter(Contact[] contacts, Activity activity) {
        this.contacts = contacts;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return contacts.length;
    }

    @Override
    public Object getItem(int i) {
        return contacts[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = activity.getLayoutInflater();
        LinearLayout itemContact = (LinearLayout) inflater.inflate(R.layout.item_contact, null);

        Contact contact = (Contact) getItem(i);


        ((TextView) itemContact.findViewById(R.id.textViewName))
                .setText(String.format("%s %s", contact.getFirstName(), contact.getLastName()));
        ((TextView) itemContact.findViewById(R.id.textViewTelephoneNumber))
                .setText(String.format("%s", contact.getTelephoneNumber()));

        return itemContact;
    }
}
