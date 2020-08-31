package PaooGame.Sound;

import PaooGame.ErrorHandler.ErrorScreenPrinter;

import javax.sound.sampled.*;
import java.io.File;
import java.util.Arrays;

public class Music {

    public static File menu_music_file;
    public static File level1_song_file;

    public static Clip menu_music_clip;
    public static Clip level1_clip;


    static float currentVolume;
    static final float DEFAULT_VOLUME_SCALE = (float) 0.1;
    static final float DEFAULT_VOLUME = (float) 0.4;

    public static final float MIN_VOLUME = (float) 0.1;
    public static final float MAX_VOLUME = (float) 1.0;


    static Clip lastPlayedMusic;

    public static void Init(){
        try {
            menu_music_file = new File("res/Sounds/menu_music.wav");
            level1_song_file = new File("res/Sounds/dungeon_cave.wav");

            currentVolume = DEFAULT_VOLUME;

            menu_music_clip = AudioSystem.getClip();
            menu_music_clip.open(AudioSystem.getAudioInputStream(menu_music_file));


            level1_clip = AudioSystem.getClip();
            level1_clip.open(AudioSystem.getAudioInputStream(level1_song_file));


        }catch (Exception e){
            ErrorScreenPrinter.addErrorMessage("Error loading the music! Please check the files. - " + e.getMessage());
        }
    }

    public static void playSound(Clip sound)  {
        try {

            FloatControl volume = (FloatControl) sound.getControl(FloatControl.Type.MASTER_GAIN);

            if(currentVolume > MIN_VOLUME) {
                float dB = (float) (Math.log(currentVolume) / Math.log(10) * 20);
                volume.setValue(dB);
            }else
                volume.setValue(-80);


            lastPlayedMusic = sound;

            sound.start();

        }
        catch (Exception e){
            ErrorScreenPrinter.addErrorMessage("Error trying to play the music! - " + e.getMessage());
        }
    }

    public static void stopLastSong(){
        lastPlayedMusic.stop();
    }

    public static void getVolumeDown(){
        if(lastPlayedMusic != null) {

            if(currentVolume > MIN_VOLUME) {
                currentVolume -= DEFAULT_VOLUME_SCALE;

                FloatControl volume = (FloatControl) lastPlayedMusic.getControl(FloatControl.Type.MASTER_GAIN);
                float dB = (float) (Math.log(currentVolume) / Math.log(10) * 20);
                try {
                    if (dB >= -80)
                        volume.setValue(dB);
                    else
                        volume.setValue(-80);
                }catch (IllegalArgumentException e){
                    volume.setValue(0);
                }

                System.out.println("Music down with " + DEFAULT_VOLUME_SCALE + " - current " + currentVolume);
            }

        }
    }

    public static void getVolumeUp(){
        if(lastPlayedMusic != null) {

            if(currentVolume < MAX_VOLUME) {
                currentVolume += DEFAULT_VOLUME_SCALE;

                FloatControl volume = (FloatControl) lastPlayedMusic.getControl(FloatControl.Type.MASTER_GAIN);
                float dB = (float) (Math.log(currentVolume) / Math.log(10) * 20);
                volume.setValue(dB);

                System.out.println("Music up " + DEFAULT_VOLUME_SCALE + " - current " + currentVolume);
            }

        }
    }

    public static float getCurrentVolume(){
        return currentVolume;
    }
    public static void setCurrentVolume(float value){
        currentVolume = value;
    }
}
