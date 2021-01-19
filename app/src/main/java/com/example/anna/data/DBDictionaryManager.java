package com.example.anna.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObservable;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.anna.model.Word;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DBDictionaryManager extends SQLiteOpenHelper {

    private final Context mContext;

    private static String TAG = "DataBaseHelper"; // Tag just for the LogCat window
    private static String DATABASE_PATH = "";
    private static final String DATABASE_NAME = "dictionary_manager.db";
    private SQLiteDatabase mDataBase;

    private static final String TABLE_NAME = "dictionary";
    private static final String ID = "id";
    private static final String ENGLISH = "english";
    private static final String TYPE = "type";
    private static final String VIETNAMESE = "vietnamese";
    private static final String SPECIALIZED_NAME = "specialized_name";
    private static final String IS_STAR = "is_star";
    private static final int VERSION = 1;

    private String CreateTableDictionaryQuery = "CREATE TABLE " + TABLE_NAME + " (" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ENGLISH + " TEXT, " +
            TYPE + " TEXT, " +
            VIETNAMESE + " TEXT, " +
            SPECIALIZED_NAME + " TEXT, " +
            IS_STAR + " INTEGER)";

    public DBDictionaryManager(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        if (android.os.Build.VERSION.SDK_INT >= 17) {
            DATABASE_PATH = context.getApplicationInfo().dataDir + "/databases/";
        } else {
            DATABASE_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
        this.mContext = context;
    }

    public void createDataBase() {
        deleteDB();
        //If the database does not exist, copy it from the assets.
        boolean mDataBaseExist = checkDataBase();
        if (!mDataBaseExist) {
            this.getReadableDatabase();
            this.close();
            try {
                //Copy the database from assests
                copyDataBase();
                Log.e(TAG, "createDatabase database created");
            } catch (IOException mIOException) {
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }

    //Check that the database exists here: /data/data/your package/databases/Da Name
    private boolean checkDataBase() {
        File dbFile = new File(DATABASE_PATH + DATABASE_NAME);
        //Log.v("dbFile", dbFile + "   "+ dbFile.exists());
        return dbFile.exists();
    }

    private void copyDataBase() throws IOException {
        InputStream mInput = mContext.getAssets().open(DATABASE_NAME);
        String outFileName = DATABASE_PATH + DATABASE_NAME;
        OutputStream mOutput = new FileOutputStream(outFileName);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0) {
            mOutput.write(mBuffer, 0, mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    public boolean openDataBase() throws SQLException {
        String mPath = DATABASE_PATH + DATABASE_NAME;
        //Log.v("mPath", mPath);
        mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        //mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
        return mDataBase != null;
    }

    @Override
    public synchronized void close() {
        if (mDataBase != null)
            mDataBase.close();
        super.close();
    }

    public void deleteDB() {
        mContext.deleteDatabase(DATABASE_NAME);
    }

    public List<Word> GetAll(String department_temp) {
        List<Word> words = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String SQL = "SELECT * FROM " + TABLE_NAME + " WHERE " + SPECIALIZED_NAME + " = '" + department_temp + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(SQL, null);

        if (cursor != null) {
            if (cursor.getCount() > 0) {

                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {

                    int id = cursor.getInt(0);
                    String english = cursor.getString(1);
                    String type = cursor.getString(2);
                    String vietnamese = cursor.getString(3);
                    String spec = cursor.getString(4);
                    int star = cursor.getInt(5);

                    Word word = new Word(id, english, type, vietnamese, spec, star);
                    words.add(word);
                    cursor.moveToNext();
                }
                cursor.close();
            }
        }
        return words;
    }

    public List<String> GetAllEnglish(String department_temp) {
        List<String> words = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String SQL = "SELECT * FROM " + TABLE_NAME + " WHERE " + SPECIALIZED_NAME + " = '" + department_temp + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(SQL, null);

        if (cursor != null) {
            if (cursor.getCount() > 0) {

                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {

                    int id = cursor.getInt(0);
                    String english = cursor.getString(1);
                    String type = cursor.getString(2);
                    String vietnamese = cursor.getString(3);
                    String spec = cursor.getString(4);
                    int star = cursor.getInt(5);

                    Log.e("id", id + "");
                    Log.e("english", english);
                    Log.e("type", type);
                    Log.e("vietnamese", vietnamese);
                    Log.e("spec", spec);
                    Log.e("star", star + "");

                    words.add(english);
                    cursor.moveToNext();
                }
                cursor.close();
            }
        }
        return words;
    }

    public Word searchWord(String searchString) {
        Word words = new Word();

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        String SQL = "SELECT * FROM " + TABLE_NAME + " WHERE " + ENGLISH + " = '" + searchString + "'";

        Cursor cursor = sqLiteDatabase.rawQuery(SQL, null);

        if (cursor != null) {
            if (cursor.getCount() > 0) {

                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {

                    Word word = new Word();

                    word.setmID(cursor.getInt(cursor.getColumnIndex(ID)));
                    word.setmEnglish(cursor.getString(cursor.getColumnIndex(ENGLISH)));
                    word.setmType(cursor.getString(cursor.getColumnIndex(TYPE)));
                    word.setmVietnamese(cursor.getString(cursor.getColumnIndex(VIETNAMESE)));
                    word.setIsStar(cursor.getInt(cursor.getColumnIndex(IS_STAR)));

                    words = word;

                    cursor.moveToNext();

                }
                cursor.close();
            }
            else
            {
                Word word = new Word();
                word.setmEnglish("Không có từ này trong từ điển");
                word.setmType("");
                word.setmVietnamese("");
                word.setIsStar(0);
                words = word;
            }
        }

        return words;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void hello() {
        Toast.makeText(mContext, "HELLO", Toast.LENGTH_SHORT).show();
    }

    public void addWord(Word word) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ENGLISH, word.getmEnglish());
        values.put(TYPE, word.getmType());
        values.put(VIETNAMESE, word.getmVietnamese());
        values.put(SPECIALIZED_NAME, word.getmSpecializedName());
        values.put(IS_STAR, word.getIsStar());

        db.insert(TABLE_NAME, null, values);
        db.close();
        Log.d("123", "Add");
    }
}
