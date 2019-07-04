package com.akm.database.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.akm.database.R;
import com.akm.database.databinding.ActivityMainBinding;
import com.akm.database.helper.DatabaseHelper;
import com.akm.database.model.AccountModel;
import com.akm.database.model.ContactModel;
import com.akm.database.model.ExtensionModel;
import com.akm.database.model.OutputModel;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private DatabaseHelper db;
    private ArrayList<String> contactIdList = new ArrayList<String>();
    private ArrayAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        init();
    }

    private void init() {
        db = new DatabaseHelper(getApplicationContext());

        if (db.getAllContacts().size() == 0)
            initValues();
        
        contactIdList.add("Select Contact Id");
        for (int i = 0; i < db.getAllContacts().size(); i++)
            contactIdList.add(db.getAllContacts().get(i).getContactId());

        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, contactIdList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinner.setAdapter(adapter);
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                OutputModel outputModel = db.getData(contactIdList.get(position));

                binding.tvStagingId.setText(outputModel.getStagingId());
                binding.tvContext.setText(outputModel.getContext());
                binding.tvStatus.setText(outputModel.getStatus());
                binding.tvUserID.setText(outputModel.getUserId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });

    }

    private void initValues() {
        List<ContactModel> contactList = new ArrayList<>();
        contactList.add(new ContactModel(2, "48f3", "1196"));
        contactList.add(new ContactModel(3, "3e47", "f1fe"));
        contactList.add(new ContactModel(4, "2cac", "036e"));

        for (ContactModel model: contactList) {
            db.createContact(model);
        }

        List<ExtensionModel> extensionList = new ArrayList<>();
        extensionList.add(new ExtensionModel("Gmail", 2));
        extensionList.add(new ExtensionModel("Gmail", 3));
        extensionList.add(new ExtensionModel("Gmail1", 4));

        for (ExtensionModel model: extensionList) {
            db.createExtension(model);
        }

        List<AccountModel> accountList = new ArrayList<>();
        accountList.add(new AccountModel(1, "test_one@gmail.com", "Gmail"));
        accountList.add(new AccountModel(0, "test_two@gmail.com", "Gmail1"));

        for (AccountModel model: accountList) {
            db.createAccount(model);
        }
    }

}
