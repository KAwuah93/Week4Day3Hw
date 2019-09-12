package com.example.week4day3hw.view.adapters;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;

import com.bumptech.glide.request.RequestOptions;
import com.example.week4day3hw.R;
import com.example.week4day3hw.model.FlickerObj.FlickerObj;
import com.example.week4day3hw.model.FlickerObj.ItemsItem;
import com.example.week4day3hw.view.activities.imageActivity.imageActivity;

import java.util.ArrayList;

public class ItemsRVAdapter extends RecyclerView.Adapter<ItemsRVAdapter.ViewHolder>{

    private ArrayList<ItemsItem> itemsArrayList;

    public ItemsRVAdapter(ArrayList<ItemsItem> itemsArrayList) {
        this.itemsArrayList = itemsArrayList;
    }
    //You could make a method to populate but maybe cleaner to do it in the viewHolder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate a view into memory, pul context from the parent and attach it to the parent view
        //set attach to root to false
        View inflatedItem  = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.flickr_list_item_1, parent, false);
        //Return the view within the Viewholder
        return new ViewHolder(inflatedItem);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemsItem currentItem = itemsArrayList.get(position);
        //how you can do it by using a prewritten method
        holder.populateValues(currentItem);
    }

    //set this mad lad up
    @Override
    public int getItemCount() {
        return itemsArrayList.size();
    }

    public void addToList(FlickerObj response) {
        for(ItemsItem result : response.getItems()) {
            itemsArrayList.add(result);
            notifyDataSetChanged();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        TextView tvAuthor;
        TextView tvTitle;
        TextView tvPub;
        ImageView imgThumb;
        ItemsItem item;
        LinearLayout listItem;

        public ViewHolder(View itemView){
            super(itemView);

            //Bind using the the ItemView instead of directly
            tvAuthor = itemView.findViewById(R.id.flickrTitle);
            tvPub = itemView.findViewById(R.id.flickrPub);
            tvTitle = itemView.findViewById(R.id.flickrAuthor);
            imgThumb = itemView.findViewById(R.id.flickrImg);
            listItem = itemView.findViewById(R.id.lstFlickr);

            //Context menu instead of onClick
            itemView.setOnLongClickListener(this);
        }
        public void setItem(ItemsItem item) {
            this.item = item;
        }

        public void populateValues(ItemsItem item){
            tvAuthor.setText(item.getAuthor());
            tvPub.setText(item.getPublished());
            tvTitle.setText(item.getTitle());
            Glide
                    .with(itemView.getContext())
                    .load(item.getMedia().getM())
                    .apply(new RequestOptions().override(600, 200))
                    .into(imgThumb);
            setItem(item);
        }
        @Override
        public void onClick(View view){
        }

        @Override
        public boolean onLongClick(final View view) {


            //item.getMedia().getM();
            DialogInterface.OnClickListener anythingyouwant = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    switch(i){
                        case DialogInterface.BUTTON_NEGATIVE:

                            //passing the URL
                            Intent bigView = new Intent(view.getContext(), imageActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("picture", item.getMedia().getM());
                            bigView.putExtras(bundle);
                            view.getContext().startActivity(bigView);
                            break;
                        case DialogInterface.BUTTON_POSITIVE:
                            //AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                            Dialog settingsDialog = new Dialog(view.getContext());
                            settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                            View balloon = LayoutInflater.from(view.getContext()).inflate(R.layout.image_layout, null);
                            settingsDialog.setContentView(balloon);
                            //Where i hold the info
                            ImageView tiny = balloon.findViewById(R.id.smallImg);

                            Glide.with(balloon).load(item.getMedia().getM()).apply(new RequestOptions().override(150, 150)).into(tiny);

                            settingsDialog.show();
                            break;
                    }
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setMessage("Next Step:").setPositiveButton("Small Image", anythingyouwant).setNegativeButton("Big Image", anythingyouwant).show();
            return true;
        }
    }
}
