package lib;

import app.Database;
import app.MockDatabase;

import java.awt.*;

/**
 * class for static data
 */
public final class Settings {

    public static Settings INSTANCE;
    //both attributes should be set to same values, so it works as intended
    public final Dimension SMALL_WINDOW_PREFERRED_SIZE = new Dimension(500,500);
    public final Dimension SMALL_WINDOW_MIN_SIZE = new Dimension(500,500);
    public final Dimension BIG_WINDOW_PREFERRED_SIZE = new Dimension(800,800);
    public final Dimension BIG_WINDOW_MIN_SIZE = new Dimension(800,800);
    public final Dimension MESSAGE_WINDOW_SIZE = new Dimension(400,300);
    public final int SMALL_WINDOW_LOCATION_X;
    public final int SMALL_WINDOW_LOCATION_Y;
    public final int BIG_WINDOW_LOCATION_X;
    public final int BIG_WINDOW_LOCATION_Y;
    public final int MESSAGE_WINDOW_LOCATION_X;
    public final int MESSAGE_WINDOW_LOCATION_Y;
    public MockDatabase database;
    public Settings() {
        Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
        MESSAGE_WINDOW_LOCATION_X = ((Long)Math.round(windowSize.getWidth() / 2 - MESSAGE_WINDOW_SIZE.getWidth() / 2)).intValue();
        MESSAGE_WINDOW_LOCATION_Y = ((Long)Math.round(windowSize.getHeight() / 2 - MESSAGE_WINDOW_SIZE.getHeight() / 2)).intValue();
        SMALL_WINDOW_LOCATION_X = ((Long)Math.round(windowSize.getWidth()/2 - SMALL_WINDOW_PREFERRED_SIZE.getWidth()/2)).intValue();
        SMALL_WINDOW_LOCATION_Y = ((Long)Math.round(windowSize.getHeight()/2 - SMALL_WINDOW_PREFERRED_SIZE.getHeight()/2)).intValue();
        BIG_WINDOW_LOCATION_X = ((Long)Math.round(windowSize.getWidth() / 2 - BIG_WINDOW_PREFERRED_SIZE.getWidth() / 2)).intValue();
        BIG_WINDOW_LOCATION_Y =((Long)Math.round(windowSize.getHeight()/2 - BIG_WINDOW_PREFERRED_SIZE.getHeight()/2)).intValue();
    }
    public static Settings getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new Settings();
        }
        return INSTANCE;
    }
}
