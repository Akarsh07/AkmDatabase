package com.akm.database.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.akm.database.model.AccountModel;
import com.akm.database.model.ContactModel;
import com.akm.database.model.ExtensionModel;
import com.akm.database.model.OutputModel;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "contactsManager";

    private static final String TABLE_CONTACT = "contact";
    private static final String TABLE_EXTENSION = "extension";
    private static final String TABLE_ACCOUNTS = "account";

    private static final String KEY_ID = "_id";
    private static final String CONTACT_ID = "contact_id";
    private static final String STAGING_ID = "staging_id";

    private static final String EXTENSION_CONTEXT = "context";
    private static final String PHONE_CONTACT_ID = "phone_contact_id";

    private static final String STATUS = "status";
    private static final String USER_ID = "user_id";
    private static final String ACCOUNT_CONTEXT = "context";


    private static final String CREATE_TABLE_CONTACTS  = "CREATE TABLE " + TABLE_CONTACT   + "(" + KEY_ID + " INTEGER," + CONTACT_ID + " TEXT," + STAGING_ID + " TEXT " + ")";
    private static final String CREATE_TABLE_EXTENSION = "CREATE TABLE " + TABLE_EXTENSION + "(" + PHONE_CONTACT_ID + " INTEGER," + EXTENSION_CONTEXT + " TEXT" + ")";
    private static final String CREATE_TABLE_ACCOUNTS  = "CREATE TABLE " + TABLE_ACCOUNTS  + "(" + STATUS + " INTEGER," + USER_ID + " TEXT," + ACCOUNT_CONTEXT + " TEXT " + ")";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CONTACTS);
        db.execSQL(CREATE_TABLE_EXTENSION);
        db.execSQL(CREATE_TABLE_ACCOUNTS);
    }


    public void createContact(ContactModel contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, contact.getId());
        values.put(CONTACT_ID, contact.getContactId());
        values.put(STAGING_ID, contact.getStagingId());

        db.insert(TABLE_CONTACT, null, values);
    }

    public void createExtension(ExtensionModel extension) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PHONE_CONTACT_ID, extension.getPhoneContactId());
        values.put(EXTENSION_CONTEXT, extension.getContext());

        db.insert(TABLE_EXTENSION, null, values);
    }

    public void createAccount(AccountModel account) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(STATUS, account.getStatus());
        values.put(USER_ID, account.getUserID());
        values.put(ACCOUNT_CONTEXT, account.getContext());

        db.insert(TABLE_ACCOUNTS, null, values);
    }

    public List<ContactModel> getAllContacts() {
        List<ContactModel> contacts = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_CONTACT;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                ContactModel contact = new ContactModel();
                contact.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                contact.setContactId((c.getString(c.getColumnIndex(CONTACT_ID))));
                contact.setStagingId(c.getString(c.getColumnIndex(STAGING_ID)));
                contacts.add(contact);
            } while (c.moveToNext());
        }

        return contacts;
    }

    public OutputModel getData(String contactId) {
        OutputModel model = new OutputModel();
        String selectQuery = "Select " + STAGING_ID + ", e." + EXTENSION_CONTEXT + ", " + STATUS + ", " + USER_ID +
            " from " + TABLE_CONTACT +
            " LEFT JOIN " + TABLE_EXTENSION + " as e ON " + TABLE_CONTACT + "._id = "  + "e." +  PHONE_CONTACT_ID +
            " LEFT JOIN " + TABLE_ACCOUNTS + " as a ON " + "e." + EXTENSION_CONTEXT + " = "  + "a." +  ACCOUNT_CONTEXT +
            " where contact_id = '" + contactId + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                model.setStagingId(c.getString(0));
                model.setContext(c.getString(1));
                model.setStatus(c.getString(2));
                model.setUserId(c.getString(3));

            } while (c.moveToNext());
        }
        return model;
    }

    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXTENSION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNTS);

        onCreate(db);
    }

}
