package com.tngocnhat.sqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<Notes> list;
    NotesAdapter adapter;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listViewNotes);
        db = new DatabaseHandler(this);

        loadNotes();
    }

    private void loadNotes() {
        list = db.getAllNotes();
        adapter = new NotesAdapter(this, list);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuAddNotes) {
            DialogThem();
        }
        return true;
    }

    public void DialogThem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_add_notes, null);
        builder.setView(view);

        EditText edtTitle = view.findViewById(R.id.edtTitleAdd);
        EditText edtContent = view.findViewById(R.id.edtContentAdd);
        Button btnAdd = view.findViewById(R.id.btnAdd);

        AlertDialog dialog = builder.create();
        dialog.show();

        btnAdd.setOnClickListener(v -> {
            db.addNotes(new Notes(
                    edtTitle.getText().toString(),
                    edtContent.getText().toString()
            ));
            loadNotes();
            dialog.dismiss();
        });
    }

    public void DialogCapNhatNotes(Notes notes) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_update_notes, null);
        builder.setView(view);

        EditText edtTitle = view.findViewById(R.id.edtTitleUpdate);
        EditText edtContent = view.findViewById(R.id.edtContentUpdate);
        Button btnUpdate = view.findViewById(R.id.btnUpdate);

        edtTitle.setText(notes.getTitle());
        edtContent.setText(notes.getContent());

        AlertDialog dialog = builder.create();
        dialog.show();

        btnUpdate.setOnClickListener(v -> {
            notes.setTitle(edtTitle.getText().toString());
            notes.setContent(edtContent.getText().toString());
            db.updateNotes(notes);
            loadNotes();
            dialog.dismiss();
        });
    }

    public void DialogDelete(Notes notes) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xóa ghi chú");
        builder.setMessage("Bạn muốn xóa ghi chú này?");

        builder.setPositiveButton("Có", (d, w) -> {
            db.deleteNotes(notes.getId());
            loadNotes();
        });

        builder.setNegativeButton("Không", null);
        builder.show();
    }
}
