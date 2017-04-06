import javax.imageio.ImageIO;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by ghost on 4/5/2017.
 */
public class Texture {
    public static Texture wood = new Texture("res/woodbrick.jpg",64);
    public static Texture brick = new Texture("res/solidbrick.jpg", 64);
    public static Texture bluestone = new Texture("res/bluebrick.jpg",64);
    public static Texture stone = new Texture("res/multibrick.jpg",64);
    public int[] pixels;
    private String loc;
    public final int size;

    public Texture(String location, int size){
        this.loc = location;
        this.size = size;
        pixels = new int[size*size];
        load();
    }

    private void load(){
        try {
            BufferedImage image = ImageIO.read(new File(loc));
            int w = image.getWidth();
            int h = image.getHeight();
            image.getRGB(0,0,w,h,pixels,0,w);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
