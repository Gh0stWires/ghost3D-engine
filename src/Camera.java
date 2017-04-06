import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by ghost on 4/5/2017.
 */
public class Camera implements KeyListener {

    public double xPos, yPos, xDir, yDir, xPlane, yPlane;
    public boolean left, right, forward, back;
    public final double moveSpeed = .08;
    public final double rotSpeed = .045;

    public Camera(double x, double y, double xd, double yd, double xp, double yp){
        this.xPos = x;
        this.yPos = y;
        this.xDir = xd;
        this.yDir = yd;
        this.xPlane = xp;
        this.yPlane = yp;

    }

    @Override
    public void keyTyped(KeyEvent e) {


    }

    @Override
    public void keyPressed(KeyEvent e) {
        if ((e.getKeyCode() == KeyEvent.VK_LEFT))
            left = true;
        if ((e.getKeyCode() == KeyEvent.VK_RIGHT))
            right = true;
        if ((e.getKeyCode() == KeyEvent.VK_UP))
            forward = true;
        if ((e.getKeyCode() == KeyEvent.VK_DOWN))
            back = true;

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if ((e.getKeyCode() == KeyEvent.VK_LEFT))
            left = false;
        if ((e.getKeyCode() == KeyEvent.VK_RIGHT))
            right = false;
        if ((e.getKeyCode() == KeyEvent.VK_UP))
            forward = false;
        if ((e.getKeyCode() == KeyEvent.VK_DOWN))
            back = false;

    }

    public void update(int[][] map){
        if (forward){
            if(map[(int)(xPos + xDir * moveSpeed)][(int)yPos] == 0){
                xPos += xDir * moveSpeed;
            }
            if (map[(int)xPos][(int)(yPos + yDir * moveSpeed)] == 0){
                yPos += yDir * moveSpeed;
            }
        }
        if (back){
            if (map[(int)(xPos - xDir * moveSpeed)][(int)yPos] == 0){
                xPos -= xDir * moveSpeed;
            }
            if (map[(int)xPos][(int)(yPos - yDir * moveSpeed)] == 0){
                yPos -= yDir * moveSpeed;
            }
        }
        if (right){
            double oldxDir = xDir;
            xDir = xDir * Math.cos(-rotSpeed) - yDir * Math.sin(-rotSpeed);
            yDir = oldxDir * Math.sin(-rotSpeed) + yDir * Math.cos(-rotSpeed);

            double oldxPlane = xPlane;
            xPlane = xPlane * Math.cos(-rotSpeed) - yPlane * Math.sin(-rotSpeed);
            yPlane = oldxPlane * Math.sin(-rotSpeed) + yPlane * Math.cos(-rotSpeed);

        }
        if (left){
            double oldxDir = xDir;
            xDir = xDir * Math.cos(rotSpeed) - yDir * Math.sin(rotSpeed);
            yDir = oldxDir * Math.sin(rotSpeed) + yDir * Math.cos(rotSpeed);

            double oldxPlane = xPlane;
            xPlane = xPlane * Math.cos(rotSpeed) - yPlane * Math.sin(rotSpeed);
            yPlane = oldxPlane * Math.sin(rotSpeed) + yPlane * Math.cos(rotSpeed);
        }
    }
}
