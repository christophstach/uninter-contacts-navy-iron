package de.stach.christoph.twitter.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import de.stach.christoph.twitter.R;

public class LoginActivity extends AppCompatActivity {
    private Button buttonLogin;
    private ImageView imageViewLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buttonLogin = (Button) findViewById(R.id.buttonLogIn);
        imageViewLogo = (ImageView) findViewById(R.id.imageViewLogo);
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

    public void showMsg(View view) {
        Toast.makeText(this, "Mi mesage", Toast.LENGTH_SHORT).show();
    }

    public void logIn(View view) {
        Intent intentContacts = new Intent(this, ContactsActivity.class);
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
