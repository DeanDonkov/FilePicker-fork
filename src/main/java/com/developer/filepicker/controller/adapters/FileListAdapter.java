package com.developer.filepicker.controller.adapters;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.MimeTypeMap;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.filepicker.R;
import com.developer.filepicker.controller.NotifyItemChecked;
import com.developer.filepicker.model.DialogConfigs;
import com.developer.filepicker.model.DialogProperties;
import com.developer.filepicker.model.FileListItem;
import com.developer.filepicker.model.MarkedCopyItemList;
import com.developer.filepicker.model.MarkedDecryptItemList;
import com.developer.filepicker.model.MarkedEncryptItemList;
import com.developer.filepicker.model.MarkedDeleteItemList;
import com.developer.filepicker.model.MarkedExtractItemList;
import com.developer.filepicker.model.MarkedItemList;
import com.developer.filepicker.widget.CopyCheckBox;
import com.developer.filepicker.widget.DecryptCheckBox;
import com.developer.filepicker.widget.EncryptCheckBox;
import com.developer.filepicker.widget.DeleteCheckBox;
import com.developer.filepicker.widget.ExtractCheckBox;
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
        }else if(MarkedEncryptItemList.hasItem(item.getLocation())){
            Animation animation = AnimationUtils.loadAnimation(context,
                    R.anim.marked_item_animation);
            view.setAnimation(animation);
        } else if(MarkedDecryptItemList.hasItem(item.getLocation())){
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.marked_item_animation);
            view.setAnimation(animation);
        } else if(MarkedExtractItemList.hasItem(item.getLocation())){
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.marked_item_animation);
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
                holder.deleteCheckBox.setVisibility(View.GONE);
                holder.copyCheckBox.setVisibility(View.INVISIBLE);
                holder.encryptCheckBox.setVisibility(View.GONE);
                holder.decryptCheckBox.setVisibility(View.GONE);
                holder.extractCheckBox.setVisibility(View.GONE);
            } else {
                holder.checkbox.setVisibility(View.VISIBLE);
                holder.deleteCheckBox.setVisibility(View.GONE);
                holder.copyCheckBox.setVisibility(View.VISIBLE);
                holder.encryptCheckBox.setVisibility(View.GONE);
                holder.decryptCheckBox.setVisibility(View.GONE);
                holder.extractCheckBox.setVisibility(View.GONE);
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
                holder.deleteCheckBox.setVisibility(View.INVISIBLE);
                holder.copyCheckBox.setVisibility(View.INVISIBLE);
                holder.encryptCheckBox.setVisibility(View.INVISIBLE);
                holder.extractCheckBox.setVisibility(View.INVISIBLE);
            } else {
                holder.checkbox.setVisibility(View.VISIBLE);
                holder.deleteCheckBox.setVisibility(View.INVISIBLE);
                holder.copyCheckBox.setVisibility(View.VISIBLE);
                holder.encryptCheckBox.setVisibility(View.GONE);
                holder.decryptCheckBox.setVisibility(View.GONE);
                holder.extractCheckBox.setVisibility(View.GONE);
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
                holder.deleteCheckBox.setVisibility(View.INVISIBLE);
                holder.copyCheckBox.setVisibility(View.INVISIBLE);
                holder.encryptCheckBox.setVisibility(View.INVISIBLE);
                holder.extractCheckBox.setVisibility(View.INVISIBLE);

            }

            File file = new File(item.getLocation());
            if(file.getParent().equals("/mnt/sdcard/.ScaWorld")){
                if(file.getName().contains(".pattern")){
                    holder.decryptCheckBox.setVisibility(View.VISIBLE);
                    holder.deleteCheckBox.setVisibility(View.VISIBLE);
                    holder.encryptCheckBox.setVisibility(View.GONE);
                } else {
                    holder.decryptCheckBox.setVisibility(View.GONE);
                    holder.encryptCheckBox.setVisibility(View.VISIBLE);
                }
                holder.extractCheckBox.setVisibility(View.VISIBLE);
                holder.checkbox.setVisibility(View.GONE);
                holder.deleteCheckBox.setVisibility(View.VISIBLE);
                holder.copyCheckBox.setVisibility(View.GONE);
            }

            if (MarkedItemList.hasItem(item.getLocation())) {
                holder.checkbox.setChecked(true);
            } else {
                holder.checkbox.setChecked(false);
            }
            if (MarkedDeleteItemList.hasItem(item.getLocation())) {
                holder.deleteCheckBox.setChecked(true);
            } else {
                holder.deleteCheckBox.setChecked(false);
            }
            if (MarkedCopyItemList.hasItem(item.getLocation())) {
                holder.copyCheckBox.setChecked(true);
            }
            if(MarkedEncryptItemList.hasItem(item.getLocation())){
                holder.encryptCheckBox.setChecked(true);
            } else {
                holder.encryptCheckBox.setChecked(false);
            }
            if(MarkedDecryptItemList.hasItem(item.getLocation())){
                holder.decryptCheckBox.setChecked(true);
            } else {
                holder.decryptCheckBox.setChecked(false);
            }
            if(MarkedExtractItemList.hasItem(item.getLocation())){
                holder.extractCheckBox.setChecked(true);
            } else{
                holder.extractCheckBox.setChecked(false);
            }
        }



        holder.checkbox.setOnCheckedChangedListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CopyCheckBox copyCheckBox, boolean isChecked) {

            }

            @Override
            public void onCheckedChanged(EncryptCheckBox encryptCheckBox, boolean isChecked) {

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
                if(holder.deleteCheckBox.isChecked()){
                    holder.deleteCheckBox.setChecked(false);
                    MarkedDeleteItemList.removeSelectedItem(item.getLocation());
                    notifyItemChecked.notifyDeleteIsClicked();
                }
                if(holder.copyCheckBox.isChecked()){
                    holder.copyCheckBox.setChecked(false);
                    MarkedCopyItemList.removeSelectedItem(item.getLocation());
                    notifyItemChecked.notifyCopyIsClicked();
                }
            }

            @Override
            public void onCheckedChanged(DecryptCheckBox decryptCheckBox, boolean isChecked) {

            }

            @Override
            public void onCheckedChanged(ExtractCheckBox extractCheckBox, boolean isChecked) {

            }
        });

        holder.deleteCheckBox.setOnCheckedChangedListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CopyCheckBox copyCheckBox, boolean isChecked) {

            }

            @Override
            public void onCheckedChanged(EncryptCheckBox encryptCheckBox, boolean isChecked) {

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
                if(holder.copyCheckBox.isChecked()){
                    MarkedCopyItemList.removeSelectedItem(item.getLocation());
                    holder.copyCheckBox.setChecked(false);
                    notifyItemChecked.notifyCopyIsClicked();
                }
            }

            @Override
            public void onCheckedChanged(MaterialCheckbox checkbox, boolean isChecked) {

            }

            @Override
            public void onCheckedChanged(DecryptCheckBox decryptCheckBox, boolean isChecked) {

            }

            @Override
            public void onCheckedChanged(ExtractCheckBox extractCheckBox, boolean isChecked) {

            }
        });

        holder.decryptCheckBox.setOnCheckedChangedListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CopyCheckBox copyCheckBox, boolean isChecked) {

            }

            @Override
            public void onCheckedChanged(EncryptCheckBox encryptCheckBox, boolean isChecked) {
            }

            @Override
            public void onCheckedChanged(DeleteCheckBox buttonbox, boolean isChecked) {

            }

            @Override
            public void onCheckedChanged(MaterialCheckbox checkbox, boolean isChecked) {

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
            }

            @Override
            public void onCheckedChanged(ExtractCheckBox extractCheckBox, boolean isChecked) {

            }
        });

        holder.copyCheckBox.setOnCheckedChangedListener(new OnCheckedChangeListener() {


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
                if(holder.deleteCheckBox.isChecked()){
                    holder.deleteCheckBox.setChecked(false);
                    MarkedDeleteItemList.removeSelectedItem(item.getLocation());
                    notifyItemChecked.notifyDeleteIsClicked();
                }
            }

            @Override
            public void onCheckedChanged(EncryptCheckBox encryptCheckBox, boolean isChecked) {

            }

            @Override
            public void onCheckedChanged(DeleteCheckBox buttonbox, boolean isChecked) {

            }

            @Override
            public void onCheckedChanged(MaterialCheckbox checkbox, boolean isChecked) {

            }

            @Override
            public void onCheckedChanged(DecryptCheckBox decryptCheckBox, boolean isChecked) {

            }

            @Override
            public void onCheckedChanged(ExtractCheckBox extractCheckBox, boolean isChecked) {

            }

        });


        holder.encryptCheckBox.setOnCheckedChangedListener(new OnCheckedChangeListener() {


            @Override
            public void onCheckedChanged(CopyCheckBox copyCheckBox, boolean isChecked) {

            }

            @Override
            public void onCheckedChanged(EncryptCheckBox encryptCheckBox, boolean isChecked) {
                item.setMarked(isChecked);
                if (item.isMarked()) {
                    if (properties.selection_mode == DialogConfigs.MULTI_MODE) {
                        MarkedEncryptItemList.addSelectedItem(item);
                    } else {
                        MarkedEncryptItemList.addSingleFile(item);
                    }
                } else {
                    if(MarkedEncryptItemList.hasItem(item.getLocation())){
                        MarkedEncryptItemList.removeSelectedItem(item.getLocation());
                    }
                }
                notifyItemChecked.notifyEncryptIsClicked();
                if(holder.checkbox.isChecked()){
                    holder.checkbox.setChecked(false);

                    MarkedItemList.removeSelectedItem(item.getLocation());
                    notifyItemChecked.notifyCheckBoxIsClicked();
                }
                if(holder.deleteCheckBox.isChecked()){
                    holder.deleteCheckBox.setChecked(false);
                    MarkedDeleteItemList.removeSelectedItem(item.getLocation());
                    notifyItemChecked.notifyDeleteIsClicked();
                }
                if(holder.copyCheckBox.isChecked()){
                    holder.copyCheckBox.setChecked(false);
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

            @Override
            public void onCheckedChanged(DecryptCheckBox decryptCheckBox, boolean isChecked) {

            }

            @Override
            public void onCheckedChanged(ExtractCheckBox extractCheckBox, boolean isChecked) {

            }
        });

        holder.extractCheckBox.setOnCheckedChangedListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CopyCheckBox copyCheckBox, boolean isChecked) {

            }

            @Override
            public void onCheckedChanged(EncryptCheckBox encryptCheckBox, boolean isChecked) {

            }

            @Override
            public void onCheckedChanged(DeleteCheckBox buttonbox, boolean isChecked) {

            }

            @Override
            public void onCheckedChanged(MaterialCheckbox checkbox, boolean isChecked) {

            }

            @Override
            public void onCheckedChanged(DecryptCheckBox decryptCheckBox, boolean isChecked) {

            }

            @Override
            public void onCheckedChanged(ExtractCheckBox extractCheckBox, boolean isChecked) {
                item.setMarked(isChecked);
                if (item.isMarked()) {
                    if (properties.selection_mode == DialogConfigs.MULTI_MODE) {
                        MarkedExtractItemList.addSelectedItem(item);
                    } else {
                        MarkedExtractItemList.addSingleFile(item);
                    }
                } else {
                    if(MarkedExtractItemList.hasItem(item.getLocation())){
                        MarkedExtractItemList.removeSelectedItem(item.getLocation());
                    }
                }
                notifyItemChecked.notifyExtractIsClicked();
            }
        });

        return view;
    }




    private class ViewHolder {
        ImageView type_icon;
        TextView name, type;
        MaterialCheckbox checkbox;
        DeleteCheckBox deleteCheckBox;
        CopyCheckBox copyCheckBox;
        EncryptCheckBox encryptCheckBox;
        DecryptCheckBox decryptCheckBox;
        ExtractCheckBox extractCheckBox;

        ViewHolder(final View itemView) {
            name = itemView.findViewById(R.id.fname);
            type = itemView.findViewById(R.id.ftype);
            type_icon = itemView.findViewById(R.id.image_type);
            checkbox = itemView.findViewById(R.id.file_mark);
            deleteCheckBox = itemView.findViewById(R.id.button2);
            copyCheckBox = itemView.findViewById(R.id.button3);
            encryptCheckBox = itemView.findViewById(R.id.button4);
            decryptCheckBox = itemView.findViewById(R.id.button5);
            extractCheckBox = itemView.findViewById(R.id.button6);

            type_icon.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    File f = new File(name.getText().toString());
                    if(f.getName().contains("pattern")){
                        Toast.makeText(context, "Please extract the file first.", Toast.LENGTH_LONG).show();
                    } else {
                        try {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setDataAndType(Uri.fromFile(f), getMimeType(f.getPath()));
                            context.startActivity(intent);
                        } catch (ActivityNotFoundException e) {
                            // no Activity to handle this kind of files
                        }
                    }
                }

                public String getMimeType(String url) {
                    String type = null;
                    String extension = MimeTypeMap.getFileExtensionFromUrl(url);
                    if (extension != null) {
                        type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
                    }
                    return type;
                }


            });
        }
    }



    public void setNotifyItemCheckedListener(NotifyItemChecked notifyItemChecked) {
        this.notifyItemChecked = notifyItemChecked;
    }

    private String fileExt(String url) {
        if (url.indexOf("?") > -1) {
            url = url.substring(0, url.indexOf("?"));
        }
        if (url.lastIndexOf(".") == -1) {
            return null;
        } else {
            String ext = url.substring(url.lastIndexOf(".") + 1);
            if (ext.indexOf("%") > -1) {
                ext = ext.substring(0, ext.indexOf("%"));
            }
            if (ext.indexOf("/") > -1) {
                ext = ext.substring(0, ext.indexOf("/"));
            }
            return ext.toLowerCase();

        }
    }




}
