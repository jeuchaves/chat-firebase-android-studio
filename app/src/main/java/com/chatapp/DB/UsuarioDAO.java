package com.chatapp.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;


public class UsuarioDAO extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "contato.db";
    private static final String TABLE_NAME = "contato";
    private static final String COL_ID = "id";
    private static final String COL_USER = "usuario";
    private static final String COL_PASS = "senha";
    SQLiteDatabase db;
    private static final String TABLE_CREATE="create table "+TABLE_NAME+
            "("+COL_ID+" integer primary key autoincrement, "+
            COL_USER+" text not null, "+COL_PASS+" text not null);";

    public UsuarioDAO(@Nullable Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(TABLE_CREATE);
        this.db = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        String query = "DROP TABLE IF EXISTS "+TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);
    }



}
