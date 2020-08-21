package PaooGame.Graphics;

import java.awt.image.BufferedImage;

public class Assets
{

    public static BufferedImage full_heart;
    public static BufferedImage half_heart;
    public static BufferedImage empty_heart;

    public static BufferedImage game_over;


    public static BufferedImage hero1;
    public static BufferedImage hero2;
    public static BufferedImage hero3;
    public static BufferedImage hero4;

    public static BufferedImage blood0;
    public static BufferedImage blood1;
    public static BufferedImage blood2;
    public static BufferedImage blood3;
    public static BufferedImage blood4;
    public static BufferedImage blood5;
    public static BufferedImage blood6;
    public static BufferedImage blood7;
    public static BufferedImage blood8;
    public static BufferedImage blood9;
    public static BufferedImage blood10;
    public static BufferedImage blood11;

    public static BufferedImage bigDemon1;
    public static BufferedImage bigDemon2;
    public static BufferedImage bigDemon3;
    public static BufferedImage bigDemon4;

    public static BufferedImage bigZombie1;
    public static BufferedImage bigZombie2;
    public static BufferedImage bigZombie3;
    public static BufferedImage bigZombie4;

    public static BufferedImage littleSkeleton1;
    public static BufferedImage littleSkeleton2;
    public static BufferedImage littleSkeleton3;
    public static BufferedImage littleSkeleton4;

    public static BufferedImage littleWizard1;
    public static BufferedImage littleWizard2;
    public static BufferedImage littleWizard3;
    public static BufferedImage littleWizard4;

    public static BufferedImage wall;
    public static BufferedImage wallTop;
    public static BufferedImage wallTopReversed;
    public static BufferedImage wallRightSide;
    public static BufferedImage wallLeftSide;
    public static BufferedImage wallTopRightCross;
    public static BufferedImage wallTopRightCrossReversed;
    public static BufferedImage wallBottomRightCross;
    public static BufferedImage wallBottomRightCrossReversed;
    public static BufferedImage wallRightTopCorner;
    public static BufferedImage wallRightTopReversedCorner;
    public static BufferedImage wallLeftTopCorner;
    public static BufferedImage wallLeftTopReversedCorner;
    public static BufferedImage floorWallTop;
    public static BufferedImage wallWithBrickTop;
    public static BufferedImage wallWithHole;

    public static BufferedImage wallSideRight;
    public static BufferedImage wallSideLeft;


    public static BufferedImage wallWithCross;
    public static BufferedImage wallWithCrossReversed;


    public static BufferedImage fountainTop;
    public static BufferedImage lavaFountainMid1;
    public static BufferedImage lavaFountainFloor1;

    public static BufferedImage waterFountainMid1;
    public static BufferedImage waterFountainFloor1;

    public static BufferedImage pillarTop;
    public static BufferedImage pillarMid;
    public static BufferedImage pillarFloor;

    public static BufferedImage slimeMid;
    public static BufferedImage slimeFloor;

    public static BufferedImage floor;
    public static BufferedImage floorLittleCrack;
    public static BufferedImage floorBigCrack1;
    public static BufferedImage floorBigCrack2;
    public static BufferedImage floorBigCrack3;
    public static BufferedImage floorBigCrack4;



    public static BufferedImage basic_sword;
    public static BufferedImage golden_sword;
    public static BufferedImage mighty_sword;
    public static BufferedImage big_hammer;
    public static BufferedImage rusty_sword;


    public static BufferedImage lifePotion;
    public static BufferedImage speedPotion;


    public static BufferedImage spikeAsset1;
    public static BufferedImage spikeAsset2;
    public static BufferedImage spikeAsset3;
    public static BufferedImage spikeAsset4;

    public static BufferedImage floorHole;

    public static BufferedImage mainMenuWallpaper;
    public static BufferedImage mainMenuTitle;

    public static BufferedImage settingsWallpaper;


    public static BufferedImage hero;

    public static BufferedImage playButton_static;
    public static BufferedImage playButton_mouseOver;

    public static BufferedImage settingsButton_static;
    public static BufferedImage settingsButton_mouseOver;

    public static BufferedImage quitButton_static;
    public static BufferedImage quitButton_mouseOver;

    public static BufferedImage plusButton_static;
    public static BufferedImage plusButton_mouseOver;

    public static BufferedImage minusButton_static;
    public static BufferedImage minusButton_mouseOver;

    public static BufferedImage backButton_static;
    public static BufferedImage backButton_mouseOver;

    public static BufferedImage volumeButton_static;

    public static BufferedImage fullscreenOff_static;
    public static BufferedImage fullscreenOff_mouseOver;

    public static BufferedImage fullscreenOn_static;
    public static BufferedImage fullscreenOn_mouseOver;

    public static BufferedImage chest_closed;
    public static BufferedImage chest_nearly_opened;
    public static BufferedImage chest_opened;

    public static BufferedImage inventory_cell;
    public static BufferedImage inventory_cell_selected;

    public static BufferedImage fireBall1;
    public static BufferedImage fireBall2;
    public static BufferedImage fireBall3;
    public static BufferedImage fireBall4;
    public static BufferedImage fireBall5;

    /*! \fn public static void Init()
        \brief Functia initializaza referintele catre elementele grafice utilizate.

        Aceasta functie poate fi rescrisa astfel incat elementele grafice incarcate/utilizate
        sa fie parametrizate. Din acest motiv referintele nu sunt finale.
     */
    public static void Init()
    {
            /// Se creaza temporar un obiect SpriteSheet initializat prin intermediul clasei ImageLoader
        SpriteSheet sheet = new SpriteSheet(ImageLoader.LoadImage("/Textures/textures.png"));
        SpriteSheet ui_sheet = new SpriteSheet(ImageLoader.LoadImage("/Textures/ui_big_pieces.png"));

            /// Se obtin subimaginile corespunzatoare elementelor necesare.
        wall = sheet.crop(32, 160, 16, 16);
        wallTop = sheet.crop(32,0,16,16);
        wallTopReversed = ImageLoader.LoadImage("/Textures/wall_reversed.png");
        floor = sheet.crop(16, 64, 16, 16);
        floorLittleCrack = sheet.crop(32, 64, 16, 16);

        floorBigCrack1 = sheet.crop(48, 80, 16, 16);
        floorBigCrack2 = sheet.crop(16, 80, 16, 16);
        floorBigCrack3 = sheet.crop(16, 96, 16, 16);
        floorBigCrack4 = sheet.crop(32, 96, 16, 16);


        wallRightSide = sheet.crop(0, 128 ,16, 16);
        wallLeftSide = sheet.crop(16,128,16,16);
        wallTopRightCross = ImageLoader.LoadImage("/Textures/wall_top_right_cross.png");
        wallTopRightCrossReversed =  sheet.crop(64, 128 ,16, 16);

        wallBottomRightCross =  sheet.crop(80, 128 ,16, 16);
        wallBottomRightCrossReversed =  ImageLoader.LoadImage("/Textures/wall_bottom_cross_reversed.png");

        wallRightTopCorner = sheet.crop(0, 112 ,16, 16);
        wallRightTopReversedCorner = ImageLoader.LoadImage("/Textures/wall_side_top_right.png");
        wallLeftTopCorner =  ImageLoader.LoadImage("/Textures/wall_side_ceva.png");
        wallLeftTopReversedCorner =  ImageLoader.LoadImage("/Textures/wall_side_ceva_reversed.png");
        floorWallTop =  ImageLoader.LoadImage("/Textures/floor_3.png");

        wallWithBrickTop = ImageLoader.LoadImage("/Textures/ajut.png");

        wallWithHole = sheet.crop(48, 48 ,16, 16);

        hero1 = sheet.crop(128, 100 ,16, 28);
        hero2 = sheet.crop(128 + 16, 100 ,16, 28);
        hero3 = sheet.crop(128 + 2 * 16, 100 ,16, 28);
        hero4 = sheet.crop(128 + 3 * 16, 100 ,16, 28);

        basic_sword = sheet.crop(323, 26, 10, 21);
        golden_sword = sheet.crop(291, 153, 10, 22);
        mighty_sword = sheet.crop(307 ,145, 10, 30);
        big_hammer = sheet.crop(291, 42, 10, 37);
        rusty_sword = sheet.crop(307, 26, 10, 21);

        lifePotion = sheet.crop(288, 224, 16, 16);
        speedPotion = sheet.crop(320, 224, 16, 16);

        bigDemon1 = sheet.crop(16, 364, 32, 36);
        bigDemon2 = sheet.crop(16 + 32, 364, 32, 36);
        bigDemon3 = sheet.crop(16 + 2 * 32, 364, 32, 36);
        bigDemon4 = sheet.crop(16 + 3 * 32, 364, 32, 36);

        bigZombie1 = sheet.crop(16, 270 ,32, 34);
        bigZombie2 = sheet.crop(16 + 32, 270 ,32, 34);
        bigZombie3 = sheet.crop(16 + 2 * 32, 270 ,32, 34);
        bigZombie4 = sheet.crop(16 + 3 * 32, 270 ,32, 34);

        littleSkeleton1 = sheet.crop(368, 80, 16, 16);
        littleSkeleton2 = sheet.crop(368 + 16, 80, 16, 16);
        littleSkeleton3 = sheet.crop(368 + 16 * 2, 80, 16, 16);
        littleSkeleton4 = sheet.crop(368 + 16 * 3, 80, 16, 16);

        littleWizard1 = sheet.crop(368, 268 ,16, 20);
        littleWizard2 = sheet.crop(368 + 16, 268, 16, 20);
        littleWizard3 = sheet.crop(368 + 16 * 2, 268, 16, 20);
        littleWizard4 = sheet.crop(368 + 16 * 3, 268, 16, 20);

        mainMenuWallpaper = ImageLoader.LoadImage("/Textures/wallpaper.png");
        mainMenuTitle = ImageLoader.LoadImage("/Textures/title1.png");

        settingsWallpaper = ImageLoader.LoadImage("/Textures/wallpaper_settings.png");

        playButton_static = ImageLoader.LoadImage("/Textures/play_static.png");
        playButton_mouseOver = ImageLoader.LoadImage("/Textures/play_mouse_over.png");

        settingsButton_static = ImageLoader.LoadImage("/Textures/settings_static.png");
        settingsButton_mouseOver = ImageLoader.LoadImage("/Textures/settings_mouse_over.png");

        quitButton_static = ImageLoader.LoadImage("/Textures/quit_static.png");
        quitButton_mouseOver = ImageLoader.LoadImage("/Textures/quit_mouse_over.png");

        backButton_static = ImageLoader.LoadImage("/Textures/back_static.png");
        backButton_mouseOver = ImageLoader.LoadImage("/Textures/back_mouse_over.png");

        blood0 = ImageLoader.LoadImage("/Textures/1/1_0.png");
        blood1 = ImageLoader.LoadImage("/Textures/1/1_1.png");
        blood2 = ImageLoader.LoadImage("/Textures/1/1_2.png");
        blood3 = ImageLoader.LoadImage("/Textures/1/1_3.png");
        blood4 = ImageLoader.LoadImage("/Textures/1/1_4.png");
        blood5 = ImageLoader.LoadImage("/Textures/1/1_5.png");
        blood6 = ImageLoader.LoadImage("/Textures/1/1_6.png");
        blood7 = ImageLoader.LoadImage("/Textures/1/1_7.png");
        blood8 = ImageLoader.LoadImage("/Textures/1/1_8.png");
        blood9 = ImageLoader.LoadImage("/Textures/1/1_9.png");
        blood10 = ImageLoader.LoadImage("/Textures/1/1_10.png");
        blood11 = ImageLoader.LoadImage("/Textures/1/1_11.png");

        full_heart = sheet.crop( 288, 256, 16, 16);
        half_heart = sheet.crop( 304 ,256 ,16 ,16);
        empty_heart = sheet.crop( 320, 256, 16, 16);


        chest_closed = sheet.crop( 304,288,16,16);
        chest_nearly_opened = sheet.crop(304 + 16,288,16,16);
        chest_opened = sheet.crop(304 + 2*16,288,16,16);


        inventory_cell = ui_sheet.crop(189,291,40,40);
        inventory_cell_selected  = ui_sheet.crop(480,292,38,39);

        game_over = ImageLoader.LoadImage("/Textures/game_over.png");

        fountainTop = sheet.crop( 64,0,16,16);
        lavaFountainMid1 = sheet.crop( 64, 16, 16, 16);
        lavaFountainFloor1 = sheet.crop( 64, 32, 16, 16);

        waterFountainMid1 = sheet.crop( 64, 48, 16, 16);
        waterFountainFloor1 = sheet.crop( 64, 64, 16, 16);

        wallSideRight = ImageLoader.LoadImage("/Textures/wall_side.png");
        wallSideLeft = ImageLoader.LoadImage("/Textures/wall_side_left.png");

        wallWithCross = ImageLoader.LoadImage("/Textures/wall_with_cross.png");
        wallWithCrossReversed = ImageLoader.LoadImage("/Textures/wall_with_cross_reversed.png");

        pillarTop = sheet.crop( 96, 80, 16 ,16);
        pillarMid = sheet.crop( 96, 96, 16 ,16);
        pillarFloor = sheet.crop( 96, 112, 16 ,16);

        slimeMid = sheet.crop( 64, 80, 16, 16);
        slimeFloor = sheet.crop( 64, 96, 16, 16);

        plusButton_static = ImageLoader.LoadImage("/Textures/plus.png");
        plusButton_mouseOver = ImageLoader.LoadImage("/Textures/plus_mouse_over.png");

        minusButton_static = ImageLoader.LoadImage("/Textures/minus.png");
        minusButton_mouseOver = ImageLoader.LoadImage("/Textures/minus_mouse_over.png");

        volumeButton_static =  ImageLoader.LoadImage("/Textures/volume_static.png");

        fullscreenOff_static =  ImageLoader.LoadImage("/Textures/fullscreen_off_static.png");
        fullscreenOff_mouseOver =  ImageLoader.LoadImage("/Textures/fullscreen_off_mouse_over.png");

        fullscreenOn_static =  ImageLoader.LoadImage("/Textures/fullscreen_on_static.png");
        fullscreenOn_mouseOver =  ImageLoader.LoadImage("/Textures/fullscreen_on_mouse_over.png");

        spikeAsset1 = sheet.crop( 16, 176 ,16 ,16 );
        spikeAsset2 = sheet.crop( 16 + 16, 176 ,16 ,16 );
        spikeAsset3 = sheet.crop( 16 + 2*16, 176 ,16 ,16 );
        spikeAsset4 = sheet.crop( 16 + 3*16, 176 ,16 ,16 );

        floorHole = sheet.crop( 96, 144, 16, 16);

        fireBall1 =  ImageLoader.LoadImage("/Textures/Fireball/FB001.png");
        fireBall2 =  ImageLoader.LoadImage("/Textures/Fireball/FB002.png");
        fireBall3 =  ImageLoader.LoadImage("/Textures/Fireball/FB003.png");
        fireBall4 =  ImageLoader.LoadImage("/Textures/Fireball/FB004.png");
        fireBall5 =  ImageLoader.LoadImage("/Textures/Fireball/FB005.png");

    }
}
