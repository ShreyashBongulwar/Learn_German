package com.example.learn_german;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class family_members_fragment extends Fragment {

    private MediaPlayer mediaPlayer;
    private AudioManager maudioManager;

    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                @Override
                public void onAudioFocusChange(int focusChange) {
                    if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                            focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                        mediaPlayer.pause();
                        mediaPlayer.seekTo(0);
                    }
                    else if(focusChange == AudioManager.AUDIOFOCUS_GAIN){
                        mediaPlayer.start();
                    }
                    else if(focusChange == AudioManager.AUDIOFOCUS_LOSS){
                        releaseMediaplayer();
                    }
                }
            };
    private MediaPlayer.OnCompletionListener mOnCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaplayer();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.word_list, container, false);
        maudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("father","der Vater",R.drawable.family_father, R.raw.der_vater));
        words.add(new Word("mother","die Mutter",R.drawable.family_mother,R.raw.die_mutter));
        words.add(new Word("parents","die Eltern",R.drawable.parents,R.raw.die_eltern));
        words.add(new Word("child","das Kind",R.drawable.family_younger_sister,R.raw.das_kind));
        words.add(new Word("son","der Sohn",R.drawable.family_son,R.raw.der_sohn));
        words.add(new Word("daughter","die Tochter",R.drawable.family_daughter,R.raw.die_tochter));
        words.add(new Word("brother","der Bruder",R.drawable.family_older_brother,R.raw.der_bruder));
        words.add(new Word("sister","die Schwester",R.drawable.family_older_sister,R.raw.die_schwester));
        words.add(new Word("grandmother","die Großmutter",R.drawable.family_grandmother,R.raw.die_grossmutter));
        words.add(new Word("grandfather","der Großvater",R.drawable.family_grandfather,R.raw.der_grossvater));
        words.add(new Word("uncle","der Onkel",R.drawable.family_father,R.raw.der_onkel));
        words.add(new Word("aunt","die Tante",R.drawable.family_mother,R.raw.die_tante));
        words.add(new Word("cousin","der Cousin",R.drawable.family_younger_brother,R.raw.der_cousin));
        words.add(new Word("cousin (female)","die Cousine",R.drawable.family_younger_sister,R.raw.die_cousine));
        words.add(new Word("wife","der Ehefrau",R.drawable.family_mother,R.raw.der_ehefrau));
        words.add(new Word("husband","die Ehemann",R.drawable.family_father,R.raw.die_eheman));
        words.add(new Word("family","die Familie",R.drawable.family,R.raw.die_familie));

        WordAdapter adapter = new WordAdapter(getActivity(),words,R.color.family_green);
        ListView listView = rootView.findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = words.get(position);

                releaseMediaplayer();

                int result = maudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mediaPlayer = MediaPlayer.create(getActivity(), word.getmAudioResourceId());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(mOnCompletionListener);
                }
            }
        });
        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaplayer();
    }
    private void releaseMediaplayer(){
        if(mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer = null;
            maudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}