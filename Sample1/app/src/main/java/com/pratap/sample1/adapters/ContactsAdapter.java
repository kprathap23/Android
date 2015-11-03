package com.pratap.sample1.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.pratap.sample1.ContactDetailActivity;
import com.pratap.sample1.R;
import com.pratap.sample1.models.Contact;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by sulei on 8/12/2015.
 */
public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactViewHolder> implements
        Filterable {
    List<Object> dataList;
    List<Object> filteredDataList;
    String letter;
    Context context;
    ValueFilter valueFilter;


    public ContactsAdapter(Context context, List<Object> dataList) {
        this.context = context;
        this.dataList = dataList;
        this.filteredDataList = dataList;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contact_list_item1, viewGroup, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
        final Contact contact = (Contact) dataList.get(i);

        contactViewHolder.title.setText(contact.getFullName());
//        Get the first letter of list item
        letter = String.valueOf(contact.getFullName().charAt(0));

//        Create a new TextDrawable for our image's background
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(letter, contact.getTextDrawbleColor());

        contactViewHolder.letter.setImageDrawable(drawable);

        contactViewHolder.rowId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, ContactDetailActivity.class);
                intent.putExtra("CONTACT", contact);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }


    class ContactViewHolder extends RecyclerView.ViewHolder {
        LinearLayout rowId;
        TextView title;
        ImageView letter;

        public ContactViewHolder(View itemView) {
            super(itemView);
            rowId = (LinearLayout) itemView.findViewById(R.id.rowId);
            letter = (ImageView) itemView.findViewById(R.id.iconLetterView);
            title = (TextView) itemView.findViewById(R.id.contactName);
        }
    }


    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                List<Contact> filterList = new ArrayList<Contact>();
                for (int i = 0; i < filteredDataList.size(); i++) {

                    Contact singleContact = (Contact) filteredDataList.get(i);

                    if ((singleContact.getFullName().toUpperCase(Locale.getDefault()))
                            .contains(constraint.toString().toUpperCase(
                                    Locale.getDefault()))) {
                        filterList.add((Contact) filteredDataList.get(i));
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = filteredDataList.size();
                results.values = filteredDataList;
            }
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            dataList = (ArrayList<Object>) results.values;
            notifyDataSetChanged();
        }

    }

}
