package com.example.learn_german;


import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {
    private int mcolorResourceId;

    public WordAdapter(@NonNull Context context, ArrayList<Word>words, int colorResourceId) {
        super(context, 0, words);
        mcolorResourceId = colorResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_tem, parent, false);
        }

        Word currentWord = getItem(position);

        TextView germanTextView = (TextView) listItemView.findViewById(R.id.germanTextView);

        germanTextView.setText(currentWord.getmGermanTranslation());

        TextView defaultTextView = (TextView) listItemView.findViewById(R.id.defaultTextView);

        defaultTextView.setText(currentWord.getmDefaultTranslation());

        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image);
        if(currentWord.hasImage()){
            imageView.setImageResource(currentWord.getmImgResourceId());
            imageView.setVisibility(View.VISIBLE);
        }
        else
            imageView.setVisibility(View.GONE);

        View textContainer = listItemView.findViewById(R.id.text_container);
        int color = ContextCompat.getColor(getContext(),mcolorResourceId);
        textContainer.setBackgroundColor(color);

        return listItemView;
    }
}
