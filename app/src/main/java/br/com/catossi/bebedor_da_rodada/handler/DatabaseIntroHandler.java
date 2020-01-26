package br.com.catossi.bebedor_da_rodada.handler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseIntroHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "dbIntro";
    private static final String TABLE_INTRO = "intro";

    private static final String KEY_ID = "id";
    private static final String KEY_VIEW = "view";

    public DatabaseIntroHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_INTRO + "("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_VIEW + " TEXT )";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INTRO);
        onCreate(db);
    }

    public void addViewIntro(String view) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_VIEW, view);


        db.insert(TABLE_INTRO, null, values);
        db.close();
    }

    public String getViewIntro() {
        String selectQuery = "SELECT  * FROM " + TABLE_INTRO;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        String view = "";
        if (cursor.moveToFirst()) {
            do {
                view = cursor.getString(1);
            } while (cursor.moveToNext());
        }

        return view;
    }


}