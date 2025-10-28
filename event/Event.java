package event;
import character.*;
import java.awt.Rectangle;
public class Event {
    public static boolean checkHit(Cat cat, Wave wave) {
    Rectangle catRect = new Rectangle(cat.x, cat.y, cat.catSize, cat.catSize);
    Rectangle waveRect = new Rectangle(wave.x, wave.y, wave.width, wave.height);
    return catRect.intersects(waveRect);
}

public static boolean checkHit(Cat cat, LowObstacle obs) {
    Rectangle catRect = new Rectangle(cat.x, cat.y, cat.catSize, cat.catSize);
    Rectangle obsRect = new Rectangle(obs.x, obs.y, obs.width, obs.height);
    return catRect.intersects(obsRect);
}


}
