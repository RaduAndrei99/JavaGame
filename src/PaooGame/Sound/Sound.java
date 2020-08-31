package PaooGame.Sound;

import PaooGame.ErrorHandler.ErrorScreenPrinter;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.rmi.server.ExportException;
import java.util.Arrays;

public class Sound {

    static float currentVolume;
    static final float DEFAULT_VOLUME_SCALE = (float) 0.1;
    static final float DEFAULT_VOLUME = (float) 0.5;

    public static final float MIN_VOLUME = (float) 0.1;
    public static final float MAX_VOLUME = (float) 1.0;


    public static File stab ;

    public static File death_big_demon;
    public static File big_demon_sound1;

    public static File death_big_orc;
    public static File orc_sound1;

    public static File death_little_skeleton;

    public static File death_little_wizard;

    public static File fireBall;
    public static File fireBallExplosion;

    public static File blueSpell;
    public static File blueSpellExplosion;


    public static File sword_dash;
    public static File spike_trap;

    public static File mouse_over;

    public static File coin_sound;

    public static File chest_sound;

    public static File little_orc1;

    static Clip clip;


    public static void Init(){
        stab = new File("res/Sounds/stab.wav");
        death_big_demon = new File("res/Sounds/death_big_demon.wav");
        big_demon_sound1 = new File("res/Sounds/big_demon_sound1.wav");


        death_big_orc = new File("res/Sounds/death_big_orc.wav");
        orc_sound1 = new File("res/Sounds/orc_sound1.wav");

        death_little_skeleton = new File("res/Sounds/death_little_skeleton.wav");
        death_little_wizard = new File("res/Sounds/dying_wizard.wav");

        mouse_over = new File("res/Sounds/mouse_over.wav");
        sword_dash = new File("res/Sounds/sword_dash.wav");
        spike_trap = new File("res/Sounds/spike.wav");

        fireBall = new File("res/Sounds/fireball.wav");
        fireBallExplosion = new File("res/Sounds/fireball_explode.wav");

        blueSpell =  new File("res/Sounds/blue_spell.wav");
        blueSpellExplosion =  new File("res/Sounds/blue_spell_explode.wav");

        coin_sound = new File("res/Sounds/coin.wav");

        chest_sound = new File("res/Sounds/chest.wav");

        little_orc1 =  new File("res/Sounds/little_orc1.wav");

        currentVolume = DEFAULT_VOLUME;

        try {
            clip = AudioSystem.getClip();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    public static void playSound(File sound)  {

        try {

            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(sound));

            FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

            if(currentVolume > MIN_VOLUME) {
                float dB = (float) (Math.log(currentVolume) / Math.log(10) * 20);
                volume.setValue(dB);
            }else
                volume.setValue(-80);

            clip.start();

        }
        catch (Exception e){
            ErrorScreenPrinter.addErrorMessage("Error trying to play the music! - " + e.getMessage());
        }
    }


    public static void getVolumeDown() {
        if (currentVolume > MIN_VOLUME) {
            currentVolume -= DEFAULT_VOLUME_SCALE;

        }
    }

    public static void getVolumeUp(){
        if(currentVolume < MAX_VOLUME) {
            currentVolume += DEFAULT_VOLUME_SCALE;
        }
    }

    public static float getCurrentVolume(){
        return currentVolume;
    }

    public static void setCurrentVolume(float value){
        currentVolume = value;
    }
}
