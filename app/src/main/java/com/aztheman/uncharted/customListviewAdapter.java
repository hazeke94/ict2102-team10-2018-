package com.aztheman.uncharted;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.aztheman.uncharted.promo;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


public class customListviewAdapter extends ArrayAdapter {
    Context context;
    List list = new ArrayList<>();
    Dialog confirm;
    static String url = " ";

    public customListviewAdapter(Context context, int resource) {
        super(context, resource);
    }

    public void add(promo object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @NonNull
    public View getView(int position, View convertView, ViewGroup parent){
        View row;
        row = convertView;
        final ViewHolder oHolder;
            if(row == null){
                LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = layoutInflater.inflate(R.layout.layout_promoitem,null,false);
                oHolder = new ViewHolder(row);
                oHolder.img = row.findViewById(R.id.ivItem);
                row.setTag(oHolder);
            }
            else{
                oHolder = (ViewHolder) row.getTag();
            }

            final promo obj = (promo) this.getItem(position);
            Glide.with(row).asBitmap().load(obj.getImage()).into(oHolder.img);
            url = obj.getPromo_url();
            oHolder.titleText.setText(obj.name);
            oHolder.durationText.setText(obj.duration);
            oHolder.priceText.setText(obj.price);

            oHolder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Open dialog that prompts user to leave app to visit url
                    confirm = new Dialog(getContext());
                    confirm.setContentView(R.layout.promo_dialog);
                    confirm.show();
                    Button leave = (Button) confirm.findViewById(R.id.btnNo);
                    Button stay = (Button) confirm.findViewById(R.id.btnYes);

                    leave.setOnClickListener(new View.OnClickListener()  {
                        @Override
                        public void onClick(View view) {
                            //Open url
                            System.out.println("URL IS " + url);
                            Intent browserIntent = new Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse(url));
                            getContext().startActivity(browserIntent);
                            confirm.dismiss();
                        }
                    });

                    stay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //stay and close dialog
                            confirm.dismiss();
                        }
                    });
                }
            });
        return row;
    }

    class ViewHolder{
        ImageButton img;
        TextView titleText;
        TextView durationText;
        TextView priceText;
        ViewHolder(View v){
            img = v.findViewById(R.id.ivItem);
            titleText = v.findViewById(R.id.titleItem);
            durationText = v.findViewById(R.id.durationItem);
            priceText = v.findViewById(R.id.priceItem);
        }
    }
}