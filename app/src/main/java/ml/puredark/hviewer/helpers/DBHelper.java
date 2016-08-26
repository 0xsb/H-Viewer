package ml.puredark.hviewer.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper {
    private final static String dbName = "hviewer.db";
    private SQLiteHelper mSqliteHelper = null;

    public DBHelper() {
    }

    public synchronized void open(Context context) {
        close();
        mSqliteHelper = new SQLiteHelper(context, dbName, null, 1);
    }

    public synchronized void insert(String sql) {
        if (mSqliteHelper == null) {
            return;
        }
        mSqliteHelper.getWritableDatabase().execSQL(sql);
    }

    public synchronized long insert(String table, ContentValues values) {
        if (mSqliteHelper == null) {
            return -1;
        }
        return mSqliteHelper.getWritableDatabase().insert(table, null, values);
    }

    public synchronized int update(String table, ContentValues values,
                                   String whereClause, String[] whereArgs) {
        if (mSqliteHelper == null) {
            return -1;
        }
        return mSqliteHelper.getWritableDatabase().update(table, values,
                whereClause, whereArgs);
    }

    public synchronized Cursor query(String table, String[] columns,
                                     String selection, String[] selectionArgs, String groupBy,
                                     String having, String orderBy) {
        if (mSqliteHelper == null) {
            return null;
        }
        return mSqliteHelper.getReadableDatabase().query(table, columns,
                selection, selectionArgs, groupBy, having, orderBy);
    }

    public synchronized Cursor query(String sql) {
        if (mSqliteHelper == null) {
            return null;
        }
        return mSqliteHelper.getReadableDatabase().rawQuery(sql, null);
    }

    public synchronized Cursor query(String sql, String[] args) {
        if (mSqliteHelper == null) {
            return null;
        }
        return mSqliteHelper.getReadableDatabase().rawQuery(sql, args);
    }

    public synchronized void nonQuery(String sql) {
        if (mSqliteHelper == null) {
            return;
        }
        mSqliteHelper.getReadableDatabase().execSQL(sql);
    }

    public synchronized void nonQuery(String sql, String[] args) {
        if (mSqliteHelper == null) {
            return;
        }
        mSqliteHelper.getReadableDatabase().execSQL(sql, args);
    }

    public synchronized int delete(String table, String whereClause,
                                   String[] whereArgs) {
        if (mSqliteHelper == null) {
            return -1;
        }
        return mSqliteHelper.getReadableDatabase().delete(table, whereClause,
                whereArgs);
    }

    public synchronized void close() {
        if (mSqliteHelper != null) {
            mSqliteHelper.close();
            mSqliteHelper = null;
        }
    }

    public class SQLiteHelper extends SQLiteOpenHelper {

        public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                              int version) {
            super(context, name, factory, version);
        }

        //创建SQLiteOpenHelper的子类，并重写onCreate及onUpgrade方法。
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE `sites`(`sid` integer primary key autoincrement, `title`, `indexUrl`, `galleryUrl`, `json` text)");
            db.execSQL("CREATE TABLE `histories`(`hid` integer primary key autoincrement, `idCode`, `title`, `referer`, `json` text)");
            db.execSQL("CREATE TABLE `favourites`(`fid` integer primary key autoincrement, `idCode`, `title`, `referer`, `json` text)");
            db.execSQL("CREATE TABLE `downloads`(`fid` integer primary key autoincrement, `idCode`, `title`, `referer`, `json` text)");
            db.execSQL("CREATE TABLE `searchSuggestions`(`title` text primary key)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

    }

}