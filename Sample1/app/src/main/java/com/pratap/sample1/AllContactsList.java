package com.pratap.sample1;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.pratap.sample1.adapters.ContactsAdapter;
import com.pratap.sample1.database.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;

public class AllContactsList extends Fragment {
    RecyclerView recyclerView;
    ContactsAdapter adapter;
    DatabaseHandler dbHandler;
    ImageButton btnFabAddContact;

    public AllContactsList() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.contactlist, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();


        dbHandler = DatabaseHandler.getDbInstance(getActivity());
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.contactList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        btnFabAddContact = (ImageButton) getActivity().findViewById(R.id.btnFabAddContact);


        btnFabAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent newContactIntent = new Intent(v.getContext(), AddNewContactActivity.class);
                newContactIntent.putExtra("ISNEWCONTACT",true);
                startActivity(newContactIntent);


            }
        });
        List<Object> listData = new ArrayList<Object>();

        listData.addAll(dbHandler.getAllContacts());


        adapter = new ContactsAdapter(getContext(), listData);
        recyclerView.setAdapter(adapter);

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.allcontacts_menu, menu);

        SearchManager searchManager = (SearchManager) ((Context) getActivity())
                .getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu
                .findItem(R.id.action_search));
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getActivity().getComponentName()));


        searchView.setIconifiedByDefault(true);

        //   searchView.setBackgroundColor(Color.WHITE);


        SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                // this is your adapter that will be filtered
                adapter.getFilter().filter(newText);
                //  System.out.println("on text chnge text: " + newText);
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                // this is your adapter that will be filtered
                adapter.getFilter().filter(query);
                //   System.out.println("on query submit: " + query);
                return true;
            }
        };
        searchView.setOnQueryTextListener(textChangeListener);
        super.onCreateOptionsMenu(menu, inflater);
    }


}
