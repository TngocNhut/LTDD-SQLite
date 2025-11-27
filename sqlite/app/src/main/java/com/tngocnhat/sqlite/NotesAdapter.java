package com.tngocnhat.sqlite;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class NotesAdapter extends BaseAdapter {

    private MainActivity context;
    private ArrayList<Notes> list;

    public NotesAdapter(MainActivity context, ArrayList<Notes> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() { return list.size(); }

    @Override
    public Object getItem(int i) { return list.get(i); }

    @Override
    public long getItemId(int i) { return list.get(i).getId(); }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {

        if (convertView == null)
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.item_notes, parent, false);

        Notes notes = list.get(i);

        TextView txtTitle = convertView.findViewById(R.id.txtTitle);
        TextView txtContent = convertView.findViewById(R.id.txtContent);
        Button btnEdit = convertView.findViewById(R.id.btnEdit);
        Button btnDelete = convertView.findViewById(R.id.btnDelete);

        txtTitle.setText(notes.getTitle());
        txtContent.setText(notes.getContent());

        btnEdit.setOnClickListener(v -> context.DialogCapNhatNotes(notes));
        btnDelete.setOnClickListener(v -> context.DialogDelete(notes));

        return convertView;
    }
}
