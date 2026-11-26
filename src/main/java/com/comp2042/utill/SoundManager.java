package com.comp2042.utill;

import javafx.scene.media.AudioClip;

public class SoundManager {
    private final String coinEatenSoundPath = "resources/views/res/coinSound.wav";
    private AudioClip coinSound;
    private boolean muted = false;


    public SoundManager() {
        try {
            String soundUrl = getClass().getResource("/" + coinEatenSoundPath).toExternalForm();
            coinSound = new AudioClip(soundUrl);
        } catch (Exception e) {
            System.out.println("Error loading sound: " + e.getMessage());
        }
    }

    public void playCoinEatenSound() {
        if (coinSound!= null){
            coinSound.play();
        }
    }
}
