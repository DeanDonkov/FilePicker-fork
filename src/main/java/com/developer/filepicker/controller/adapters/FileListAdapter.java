package com.developer.filepicker.controller.adapters;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.developer.filepicker.R;
import com.developer.filepicker.controller.NotifyItemChecked;
import com.developer.filepicker.model.DialogConfigs;
import com.developer.filepicker.model.DialogProperties;
import com.developer.filepicker.model.FileListItem;
import com.developer.filepicker.model.MarkedCopyItemList;
import com.developer.filepicker.model.MarkedDecryptItemList;
import com.developer.filepicker.model.MarkedDeleteItemList;
import com.developer.filepicker.model.MarkedItemList;
import com.developer.filepicker.widget.CopyCheckBox;
import com.developer.filepicker.widget.DecryptCheckBox;
import com.developer.filepicker.widget.DeleteCheckBox;
import com.developer.filepicker.widget.MaterialCheckbox;
import com.developer.filepicker.widget.listeners.OnCheckedChangeListener;

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author akshay sunil masram
 */
public class FileListAdapter extends BaseAdapter {

    private ArrayList<FileListItem> listItem;
    private Context context;
    private DialogProperties properties;
    private NotifyItemChecked notifyItemChecked;

    public FileListAdapter(ArrayList<FileListItem> listItem, Context context,
                           DialogProperties properties) {
        this.listItem = listItem;
        this.context = context;
        this.properties = properties;
    }

    @Override
    public int getCount() {
        return listItem.size();
    }

    @Override
    public FileListItem getItem(int i) {
        return listItem.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.dialog_file_list_item,
                    viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final FileListItem item = listItem.get(i);
        if (MarkedItemList.hasItem(item.getLocation())) {
            Animation animation = AnimationUtils.loadAnimation(context,
                    R.anim.marked_item_animation);
            view.setAnimation(animation);
        } else {
            Animation animation = AnimationUtils.loadAnimation(context,
                    R.anim.unmarked_item_animation);
            view.setAnimation(animation);
        }  if(MarkedDeleteItemList.hasItem(item.getLocation())){
            Animation animation = AnimationUtils.loadAnimation(context,
                    R.anim.marked_item_animation);
            view.setAnimation(animation);
        } else {
            Animation animation = AnimationUtils.loadAnimation(context,
                    R.anim.unmarked_item_animation);
            view.setAnimation(animation);
        } if(MarkedCopyItemList.hasItem(item.getLocation())){
            Animation animation = AnimationUtils.loadAnimation(context,
                    R.anim.marked_item_animation);
            view.setAnimation(animation);
        }else if(MarkedDecryptItemList.hasItem(item.getLocation())){
            Animation animation = AnimationUtils.loadAnimation(context,
                    R.anim.marked_item_animation);
            view.setAnimation(animation);
        }
        else {
            Animation animation = AnimationUtils.loadAnimation(context,
                    R.anim.unmarked_item_animation);
            view.setAnimation(animation);
        }
        if (item.isDirectory()) {
            holder.type_icon.setImageResource(R.mipmap.ic_type_folder);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                holder.type_icon.setColorFilter(context.getResources()
                        .getColor(R.color.colorPrimary, context.getTheme()));
            } else {
                holder.type_icon.setColorFilter(context.getResources()
                        .getColor(R.color.colorPrimary));
            }
            if (properties.selection_type == DialogConfigs.FILE_SELECT) {
                holder.checkbox.setVisibility(View.INVISIBLE);
                holder.btncheckbox2.setVisibility(View.INVISIBLE);
                holder.btncheckbox3.setVisibility(View.INVISIBLE);
                holder.btncheckbox4.setVisibility(View.INVISIBLE);
            } else {
                holder.checkbox.setVisibility(View.VISIBLE);
                holder.btncheckbox2.setVisibility(View.GONE);
                holder.btncheckbox3.setVisibility(View.VISIBLE);
                holder.btncheckbox4.setVisibility(View.GONE);
            }
        } else {
            holder.type_icon.setImageResource(R.mipmap.ic_type_file);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                holder.type_icon.setColorFilter(context.getResources()
                        .getColor(R.color.colorAccent, context.getTheme()));
            } else {
                holder.type_icon.setColorFilter(context.getResources()
                        .getColor(R.color.colorAccent));
            }
            if (properties.selection_type == DialogConfigs.DIR_SELECT) {
                holder.checkbox.setVisibility(View.INVISIBLE);
                holder.btncheckbox2.setVisibility(View.INVISIBLE);
                holder.btncheckbox3.setVisibility(View.INVISIBLE);
                holder.btncheckbox4.setVisibility(View.INVISIBLE);
            } else {
                holder.checkbox.setVisibility(View.VISIBLE);
                holder.btncheckbox2.setVisibility(View.VISIBLE);
                holder.btncheckbox3.setVisibility(View.VISIBLE);
                holder.btncheckbox4.setVisibility(View.GONE);
            }
        }
        holder.type_icon.setContentDescription(item.getFilename());
        holder.name.setText(item.getFilename());
        DateFormat dateFormatter = android.text.format.DateFormat.getMediumDateFormat(context);
        DateFormat timeFormatter = android.text.format.DateFormat.getTimeFormat(context);
        Date date = new Date(item.getTime());
        if (i == 0 && item.getFilename().startsWith(context.getString(R.string.label_parent_dir))) {
            holder.type.setText(R.string.label_parent_directory);
        } else {
            holder.type.setText(String.format(context.getString(R.string.last_edit),
                    dateFormatter.format(date), timeFormatter.format(date)));
        }
        if (holder.checkbox.getVisibility() == View.VISIBLE) {
            if (i == 0 && item.getFilename().startsWith(context.getString(R.string.label_parent_dir))) {
                holder.checkbox.setVisibility(View.INVISIBLE);
                holder.btncheckbox2.setVisibility(View.INVISIBLE);
                holder.btncheckbox3.setVisibility(View.INVISIBLE);
                holder.btncheckbox4.setVisibility(View.INVISIBLE);
            }

            if(item.getFilename().endsWith(".encryptedfolder") || item.getFilename().endsWith("encryptedfile")){
                holder.btncheckbox4.setVisibility(View.VISIBLE);
                holder.checkbox.setVisibility(View.INVISIBLE);
                holder.btncheckbox2.setVisibility(View.INVISIBLE);
                holder.btncheckbox3.setVisibility(View.INVISIBLE);

            }

            if (MarkedItemList.hasItem(item.getLocation())) {
                holder.checkbox.setChecked(true);
            } else {
                holder.checkbox.setChecked(false);
            }
            if (MarkedDeleteItemList.hasItem(item.getLocation())) {
                holder.btncheckbox2.setChecked(true);
            } else {
                holder.btncheckbox2.setChecked(false);
            }
            if (MarkedCopyItemList.hasItem(item.getLocation())) {
                holder.btncheckbox3.setChecked(true);
            }
            if(MarkedDecryptItemList.hasItem(item.getLocation())){
                holder.btncheckbox4.setChecked(true);
            } else {
                holder.btncheckbox4.setChecked(false);
            }
        }


        holder.checkbox.setOnCheckedChangedListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CopyCheckBox copyCheckBox, boolean isChecked) {

            }

            @Override
            public void onCheckedChanged(DecryptCheckBox decryptCheckBox, boolean isChecked) {

            }

            @Override
            public void onCheckedChanged(DeleteCheckBox buttonbox, boolean isChecked) {

            }

            @Override
            public void onCheckedChanged(MaterialCheckbox checkbox, boolean isChecked) {
                item.setMarked(isChecked);
                if (item.isMarked()) {
                    if (properties.selection_mode == DialogConfigs.MULTI_MODE) {
                        MarkedItemList.addSelectedItem(item);
                    } else {
                        MarkedItemList.addSingleFile(item);
                    }
                } else {
                    MarkedItemList.removeSelectedItem(item.getLocation());
                }
                notifyItemChecked.notifyCheckBoxIsClicked();
                if(holder.btncheckbox2.isChecked()){
                    holder.btncheckbox2.setChecked(false);
                    MarkedDeleteItemList.removeSelectedItem(item.getLocation());
                    notifyItemChecked.notifyDeleteIsClicked();
                }
                if(holder.btncheckbox3.isChecked()){
                    holder.btncheckbox3.setChecked(false);
                    MarkedCopyItemList.removeSelectedItem(item.getLocation());
                    notifyItemChecked.notifyCopyIsClicked();
                }
            }
        });

        holder.btncheckbox2.setOnCheckedChangedListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CopyCheckBox copyCheckBox, boolean isChecked) {

            }

            @Override
            public void onCheckedChanged(DecryptCheckBox decryptCheckBox, boolean isChecked) {

            }

            @Override
            public void onCheckedChanged(DeleteCheckBox buttonbox, boolean isChecked) {
                item.setMarked(isChecked);
                if (item.isMarked()) {
                    if (properties.selection_mode == DialogConfigs.MULTI_MODE) {
                        MarkedDeleteItemList.addSelectedItem(item);
                    } else {
                        MarkedDeleteItemList.addSingleFile(item);
                    }
                } else {
                    MarkedDeleteItemList.removeSelectedItem(item.getLocation());
                }
                notifyItemChecked.notifyDeleteIsClicked();
                if(holder.checkbox.isChecked()){
                    MarkedItemList.removeSelectedItem(item.getLocation());
                    holder.checkbox.setChecked(false);
                    notifyItemChecked.notifyCheckBoxIsClicked();
                }
                if(holder.btncheckbox3.isChecked()){
                    MarkedCopyItemList.removeSelectedItem(item.getLocation());
                    holder.btncheckbox3.setChecked(false);
                    notifyItemChecked.notifyCopyIsClicked();
                }
            }

            @Override
            public void onCheckedChanged(MaterialCheckbox checkbox, boolean isChecked) {

            }
        });

        holder.btncheckbox3.setOnCheckedChangedListener(new OnCheckedChangeListener() {


            @Override
            public void onCheckedChanged(CopyCheckBox copyCheckBox, boolean isChecked) {
                item.setMarked(isChecked);
                if (item.isMarked()) {
                    if (properties.selection_mode == DialogConfigs.MULTI_MODE) {
                        MarkedCopyItemList.addSelectedItem(item);
                    } else {
                        MarkedCopyItemList.addSingleFile(item);
                    }
                } else {
                    if(MarkedCopyItemList.hasItem(item.getLocation())){
                        MarkedCopyItemList.removeSelectedItem(item.getLocation());
                    }
                }
                notifyItemChecked.notifyCopyIsClicked();
                if(holder.checkbox.isChecked()){
                    holder.checkbox.setChecked(false);
                    MarkedItemList.removeSelectedItem(item.getLocation());
                    notifyItemChecked.notifyCheckBoxIsClicked();
                }
                if(holder.btncheckbox2.isChecked()){
                    holder.btncheckbox2.setChecked(false);
                    MarkedDeleteItemList.removeSelectedItem(item.getLocation());
                    notifyItemChecked.notifyDeleteIsClicked();
                }
            }

            @Override
            public void onCheckedChanged(DecryptCheckBox decryptCheckBox, boolean isChecked) {

            }

            @Override
            public void onCheckedChanged(DeleteCheckBox buttonbox, boolean isChecked) {

            }

            @Override
            public void onCheckedChanged(MaterialCheckbox checkbox, boolean isChecked) {

            }

        });


        holder.btncheckbox4.setOnCheckedChangedListener(new OnCheckedChangeListener() {


            @Override
            public void onCheckedChanged(CopyCheckBox copyCheckBox, boolean isChecked) {

            }

            @Override
            public void onCheckedChanged(DecryptCheckBox decryptCheckBox, boolean isChecked) {
                item.setMarked(isChecked);
                if (item.isMarked()) {
                    if (properties.selection_mode == DialogConfigs.MULTI_MODE) {
                        MarkedDecryptItemList.addSelectedItem(item);
                    } else {
                        MarkedDecryptItemList.addSingleFile(item);
                    }
                } else {
                    if(MarkedDecryptItemList.hasItem(item.getLocation())){
                        MarkedDecryptItemList.removeSelectedItem(item.getLocation());
                    }
                }
                notifyItemChecked.notifyDecryptIsClicked();
                if(holder.checkbox.isChecked()){
                    holder.checkbox.setChecked(false);
                    MarkedItemList.removeSelectedItem(item.getLocation());
                    notifyItemChecked.notifyCheckBoxIsClicked();
                }
                if(holder.btncheckbox2.isChecked()){
                    holder.btncheckbox2.setChecked(false);
                    MarkedDeleteItemList.removeSelectedItem(item.getLocation());
                    notifyItemChecked.notifyDeleteIsClicked();
                }
                if(holder.btncheckbox3.isChecked()){
                    holder.btncheckbox3.setChecked(false);
                    MarkedCopyItemList.removeSelectedItem(item.getLocation());
                    notifyItemChecked.notifyDeleteIsClicked();
                }
            }

            @Override
            public void onCheckedChanged(DeleteCheckBox buttonbox, boolean isChecked) {

            }

            @Override
            public void onCheckedChanged(MaterialCheckbox checkbox, boolean isChecked) {

            }
        });

        return view;
    }



    private class ViewHolder {
        ImageView type_icon;
        TextView name, type;
        MaterialCheckbox checkbox;
        DeleteCheckBox btncheckbox2;
        CopyCheckBox btncheckbox3;
        DecryptCheckBox btncheckbox4;

        ViewHolder(View itemView) {
            name = itemView.findViewById(R.id.fname);
            type = itemView.findViewById(R.id.ftype);
            type_icon = itemView.findViewById(R.id.image_type);
            checkbox = itemView.findViewById(R.id.file_mark);
            btncheckbox2 = itemView.findViewById(R.id.button2);
            btncheckbox3 = itemView.findViewById(R.id.button3);
            btncheckbox4 = itemView.findViewById(R.id.button4);
        }
    }

    public void setNotifyItemCheckedListener(NotifyItemChecked notifyItemChecked) {
        this.notifyItemChecked = notifyItemChecked;
    }
}
