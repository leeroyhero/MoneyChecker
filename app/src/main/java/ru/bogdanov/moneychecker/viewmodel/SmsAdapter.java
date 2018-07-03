package ru.bogdanov.moneychecker.viewmodel;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import ru.bogdanov.moneychecker.databinding.ListItemSmsBinding;
import ru.bogdanov.moneychecker.model.items.SmsItem;

public class SmsAdapter extends RecyclerView.Adapter<SmsAdapter.ViewHolder> {
    private ArrayList<SmsItem> mDataset;

    public void setList(ArrayList<SmsItem> list) {
        mDataset=new ArrayList<>(list);
        notifyDataSetChanged();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        private ListItemSmsBinding binding;

        public ViewHolder(ListItemSmsBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }

        public void bind(SmsItem smsItem){
            binding.setSms(smsItem);
            binding.executePendingBindings();
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public SmsAdapter() {
        mDataset = new ArrayList<>();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SmsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                    int viewType) {
        // create a new view
        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());
        ListItemSmsBinding binding=ListItemSmsBinding.inflate(layoutInflater, parent,false);

        SmsAdapter.ViewHolder vh = new SmsAdapter.ViewHolder(binding);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(SmsAdapter.ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.bind(mDataset.get(position));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
