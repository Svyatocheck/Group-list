package com.example.grouplist;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grouplist.toStore.Person;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.PersonHolder> {

    // List to store all the contact details
    private final ArrayList<Person> personItems;
    private final OnNoteListener mOnNoteListener;

    public MyAdapter(ArrayList<Person> personList, OnNoteListener onNoteListener) {
        this.personItems = personList;
        this.mOnNoteListener = onNoteListener;
    }

    @Override
    public int getItemCount() {
        return personItems == null? 0: personItems.size();
    }

    @NonNull
    @Override
    public PersonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        // Inflate the layout view you have created for the list rows here
        View view = layoutInflater.inflate(R.layout.item_list, parent, false);
        return new PersonHolder(view, mOnNoteListener, personItems);
    }

    // This method is called when binding the data to the views being created in RecyclerView
    @Override
    public void onBindViewHolder(@NonNull PersonHolder holder, final int position) {
        final Person person = personItems.get(position);

        // Set the data to the views here
        holder.setContactName(person.getName());
        holder.setContactSex(person.getSex());
        holder.setContactIde(person.getIde());
        holder.setContactLang(person.getLang());

    }

    public interface OnNoteListener {
        void onNoteClick(int position);
    }

    // This is your ViewHolder class that helps to populate data to the view
    public class PersonHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView personName;
        private final TextView sexPerson;
        private final TextView idePersons;
        private final TextView langPersons;
        private final ImageView avatarPerson;


        OnNoteListener onNoteListener;

        public PersonHolder(View itemView, OnNoteListener onNoteListener, ArrayList<Person> people) {
            super(itemView);

            personName = itemView.findViewById(R.id.personName);
            sexPerson = itemView.findViewById(R.id.sexData);
            idePersons = itemView.findViewById(R.id.ideName);
            langPersons = itemView.findViewById(R.id.langName);

            avatarPerson = itemView.findViewById(R.id.avatarField);

            ImageView deleteItem = itemView.findViewById(R.id.removeItem);
            ImageView editItem = itemView.findViewById(R.id.editItem);

            this.onNoteListener = onNoteListener;
            itemView.setOnClickListener(this);

            deleteItem.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), "Delete button pressed", Toast.LENGTH_SHORT).show();

                    int position = getAdapterPosition();
                    people.remove(position);
                    notifyItemRemoved(position);

                    // remove data from files too
                }});


            editItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), "Edit button pressed", Toast.LENGTH_SHORT).show();
                    onNoteListener.onNoteClick(getAdapterPosition());
                }});
        }


        public void setContactName(String name) {
            personName.setText(name);
        }

        public void setContactSex(String sex) {
            sexPerson.setText(sex);

            try {
                if (sex.equals("Male")) {
                    avatarPerson.setImageResource(R.drawable.male);

                } else if (sex.equals("Female")) {
                    avatarPerson.setImageResource(R.drawable.female);

                } else {
                    avatarPerson.setImageResource(R.drawable.avatar);
                }
            } catch (Exception ignore){}
        }

        public void setContactIde(String ide) {
            idePersons.setText(ide);
        }

        public void setContactLang(String lang) {
            langPersons.setText(lang);
        }

        @Override
        public void onClick(View view) {
            Log.e("Note", "Note clicked!");
        }


    }
}