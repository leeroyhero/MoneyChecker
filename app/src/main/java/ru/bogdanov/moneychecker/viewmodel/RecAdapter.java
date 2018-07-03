package ru.bogdanov.moneychecker.viewmodel;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import ru.bogdanov.moneychecker.databinding.ListItemBinding;
import ru.bogdanov.moneychecker.model.items.DayItem;

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.ViewHolder> {
    private ArrayList<DayItem> mDataset;

    public void setList(ArrayList<DayItem> list) {
        mDataset=new ArrayList<>(list);
        notifyDataSetChanged();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private ListItemBinding binding;

        public ViewHolder(ListItemBinding binding) {
            super(binding.getRoot());
           this.binding=binding;
        }

        public void bind(DayItem dayItem){
            binding.setData(dayItem);
            binding.executePendingBindings();
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecAdapter() {
        mDataset = new ArrayList<>();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());
        ListItemBinding binding=ListItemBinding.inflate(layoutInflater, parent,false);

        ViewHolder vh = new ViewHolder(binding);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
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
