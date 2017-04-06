import java.awt.*;
import java.util.ArrayList;

/**
 * Created by ghost on 4/6/2017.
 */
public class Screen {
    public int[][] map;
    public int mapWidth, mapHeight, width, height;
    public ArrayList<Texture> textures;

    public Screen(int[][] map, ArrayList<Texture> textures, int width, int height){
        this.map = map;
        this.textures = textures;
        this.width = width;
        this.height = height;
    }

    //turns the floor and cieling grey figure out what to render int view
    public int[] update(Camera camera, int[] pixels){
        for (int n = 0; n < pixels.length/2; n++){
            if (pixels[n] != Color.DARK_GRAY.getRGB()){
                pixels[n] = Color.DARK_GRAY.getRGB();
            }
        }
        for (int i = pixels.length/2; i < pixels.length; i++){
            if (pixels[i] != Color.gray.getRGB()){
                pixels[i] = Color.gray.getRGB();
            }
        }
        for (int x = 0; x < width; x = x+1){
            double cameraX = 2 * x / (double)(width) -1;
            double rayDirX = camera.xDir + camera.xPlane * cameraX;
            double rayDirY = camera.yDir + camera.yPlane * cameraX;

            int mapX = (int)camera.xPos;
            int mapY = (int)camera.yPos;

            double sideDistX;
            double sideDistY;

            double deltaDistX = Math.sqrt(1 + (rayDirY * rayDirY) / (rayDirX * rayDirX));
            double deltaDistY = Math.sqrt(1 + (rayDirX * rayDirX) / (rayDirY * rayDirY));
            double perpWallDist;

            int stepX, stepY;
            boolean hit = false;
            int side = 0;

            if (rayDirX < 0){
                stepX = -1;
                sideDistX = (camera.xPos - mapX) * deltaDistX;
            }else {
                stepX = 1;
                sideDistX = (mapX + 1.0 - camera.xPos) * deltaDistX;
            }
            if (rayDirY < 0){
                stepY = -1;
                sideDistY = (camera.yPos - mapY) * deltaDistY;
            }else {
                stepY = 1;
                sideDistY = (mapY + 1.0 - camera.yPos) * deltaDistY;
            }

            while (!hit){
                if (sideDistX < sideDistY){
                    sideDistX += deltaDistX;
                    mapX += stepX;
                    side = 0;
                }else {
                    sideDistY += deltaDistY;
                    mapY += stepY;
                    side = 1;
                }

                if (map[mapX][mapY] > 0){
                    hit = true;
                }
            }

            if (side == 0){
                perpWallDist = Math.abs((mapX - camera.xPos + (1 - stepX) / 2) / rayDirX);
            }else {
                perpWallDist = Math.abs((mapY - camera.yPos + (1 - stepY) / 2) / rayDirY);
            }

            int lineHeight = height;
            if (perpWallDist > 0){
                lineHeight = Math.abs((int)(height / perpWallDist));
            }else {
                lineHeight = height;
            }

            int drawStart = -lineHeight / 2 + height / 2;
            if (drawStart < 0){
                drawStart = 0;
            }

            int drawEnd = lineHeight / 2 + height / 2;
            if (drawEnd >= height){
                drawEnd = height - 1;
            }

            int textureNum = map[mapX][mapY] - 1;
            double wallX;
            if (side == 1){
                wallX = (camera.yPos + ((mapX - camera.xPos + (1 - stepY) / 2) / rayDirY) * rayDirX);
            }else{
                wallX = (camera.yPos + ((mapX - camera.xPos + (1 - stepX) / 2) / rayDirX) * rayDirY);
            }

            wallX -= Math.floor(wallX);

            int textureX = (int)(wallX * (textures.get(textureNum).size));
            if (side == 0 && rayDirX > 0){
                textureX = textures.get(textureNum).size - textureX -1;
            }
            if (side == 1 && rayDirY < 0){
                textureX = textures.get(textureNum).size - textureX -1;
            }

            for (int y = drawStart; y < drawEnd; y++){
                int textureY = (((y * 2 - height + lineHeight) << 6) / lineHeight) / 2;
                int color;
                if (side == 0){
                    color = textures.get(textureNum).pixels[textureX + (textureY * textures.get(textureNum).size)];
                }else {
                    color = (textures.get(textureNum).pixels[textureX + (textureY * textures.get(textureNum).size)] >> 1)
                            & 8355711;
                }
                pixels[x + y * (width)] = color;
            }
        }
        return pixels;
    }
}
