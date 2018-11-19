package com.example.weatherapp.weatherapp.Database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class NoteManager {

        private DatabaseManager dbmanager;
        private SQLiteDatabase database;


        private String[] notesAllColumn = {
                DatabaseManager.COLUMN_ID,
                DatabaseManager.COLUMN_NOTE,
                DatabaseManager.COLUMN_TITLE
        };

        public NoteManager(Context context) {
            dbmanager = new DatabaseManager(context);
          
        }

        public void open() throws SQLException {
            database = dbmanager.getWritableDatabase();
        }

        public void close() {
            dbmanager.close();
        }

     public Note addNote(String title, String note) {

         ContentValues values = new ContentValues();
         Note newNote = new Note();
         long insertId = database.insert(DatabaseManager.TABLE_NOTES, null,
                 values);

         if (checkTitle(title) == false) {
             editNote(insertId, note, title);
            return null;

         }
         else {
             newNote.setNote(note);
             newNote.setTitle(title);
             newNote.setId(insertId);

             values.put(DatabaseManager.COLUMN_NOTE, note);
             values.put(DatabaseManager.COLUMN_TITLE, title);



         }
         return newNote;
     }

        public void editNote(long id, String note, String title) {
            ContentValues editedNote = new ContentValues();
            editedNote.put(dbmanager.COLUMN_ID, id);
            editedNote.put(dbmanager.COLUMN_NOTE, note);
            editedNote.put(dbmanager.COLUMN_TITLE, title);

            database.update(dbmanager.TABLE_NOTES,
                    editedNote,
                    dbmanager.COLUMN_ID + "=" + id,
                    null);
        }

        public void deleteNote(Note note) {
            long id = note.getId();
            database.delete(DatabaseManager.TABLE_NOTES, DatabaseManager.COLUMN_ID
                    + " = " + id, null);
        }

        public void deleteAll() {
            database.delete(DatabaseManager.TABLE_NOTES, null, null);
        }

       public List<Note> getAllNotes() {
            List<Note> notes = new ArrayList<Note>();

            Cursor cursor = database.query(DatabaseManager.TABLE_NOTES,
                    notesAllColumn, null, null, null, null, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Note note = cursorToNote(cursor);
                notes.add(note);
                cursor.moveToNext();
            }
            // make sure to close the cursor
            cursor.close();
            return notes;
        }


    public String getNote(String title)
        {
            try {
                boolean flag = false;
                Note note;
                Cursor cursor = database.query(DatabaseManager.TABLE_NOTES,
                        notesAllColumn, null, null, null, null, null);
                cursor.moveToFirst();
                while (flag == false) {
                    note = cursorToNote(cursor);
                    if (note.getTitle() == title) {
                        flag = true;
                        return note.getNote();

                    }
                    cursor.moveToNext();
                    if (cursor.isAfterLast()) {
                        break;
                    }

                }
            }
            catch (Exception e) {
                return null;
            }
            return null;
        }
        private Note cursorToNote(Cursor cursor) {
            Note note = new Note();
            note.setId(cursor.getLong(0));
            note.setNote(cursor.getString(1));
            note.setTitle(cursor.getString(2));
            return note;
        }
        private boolean checkTitle(String title)
        {
            Note note;
            Cursor cursor = database.query(DatabaseManager.TABLE_NOTES,
                    notesAllColumn, null, null, null, null, null);
            cursor.moveToFirst();
            note=cursorToNote(cursor);
            while (!cursor.isAfterLast())
            {
                if (note.getTitle()==title)
                    return true;
                cursor.moveToNext();
            }
            return false;

        }

    }


