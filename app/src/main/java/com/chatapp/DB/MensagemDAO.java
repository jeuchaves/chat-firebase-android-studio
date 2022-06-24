package com.chatapp.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MensagemDAO extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "mensagem.db";
    private static final String TABLE_NAME = "mensagem";
    private static final String COL_ID = "id";
    private static final String COL_IDREM = "remetente";
    private static final String COL_IDDEST = "destinatario";
    SQLiteDatabase db;
    private static final String TABLE_CREATE="create table "+TABLE_NAME+
            "("+COL_ID+" integer primary key autoincrement, "+
            COL_IDREM+" integer not null, "+COL_IDDEST+" integer not null, constraint fk_remetente foreign key (remetente) REFERENCES contato (id)," +
            "constraint fk_destinatario foreign key (destinatario) references contato (id)" +
            "  );";

    public MensagemDAO(@Nullable Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }
    @Override
        public void onCreate (SQLiteDatabase db){
            db.execSQL(TABLE_CREATE);
            this.db = db;
        }
        @Override
        public void onUpgrade (SQLiteDatabase db,int oldVersion, int newVersion){
            String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
            db.execSQL(query);
            this.onCreate(db);
        }


    }

