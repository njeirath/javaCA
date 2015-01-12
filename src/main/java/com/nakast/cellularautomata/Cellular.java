package com.nakast.cellularautomata;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Cellular {
    private int iteration = 0;
    private Random rand;

    public Cellular (BufferedImage startImage, int iterations) {
        rand = new Random();

        try {
            writeFile(startImage);
            BufferedImage last = startImage;

            for (iteration = 1; iteration <= iterations; iteration++) {
                if ((iteration % 100) == 0) {
                    System.out.println(iteration + " of " + iterations);
                }
                BufferedImage newImg = nextFrame(last);
                writeFile(newImg);
                last = newImg;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeFile(BufferedImage img) throws IOException {
        File out = new File(String.format("output/%06d.png", iteration));
        ImageIO.write(img, "png", out);
    }

    private BufferedImage nextFrame(BufferedImage img) {
        BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());

        for (int x = 1; x < img.getWidth()-1; x++) {
            for (int y = 1; y < img.getHeight()-1; y++) {
                int r = 0;
                int g = 0;
                int b = 0;

                for (int i = x - 1; i <= x + 1; i++) {
                    for (int j = y - 1; j <= y+1; j++) {
                        int rgb = img.getRGB(i,j);
                        r += getRed(rgb);
                        g += getGrn(rgb);
                        b += getBlu(rgb);
                    }
                }

                if ((r > g) && (r > b)) {
                    out.setRGB(x, y, getRGB(r/7, rand.nextInt(204), rand.nextInt(255)));
                } else if ((g > r) && (g > b)) {
                    out.setRGB(x, y, getRGB(rand.nextInt(255), g/7, rand.nextInt(204)));
                } else if ((b > r) && (b > g)) {
                    out.setRGB(x, y, getRGB(rand.nextInt(204), rand.nextInt(255), b/7));
                } else {
                    out.setRGB(x, y, img.getRGB(x, y));
                }
            }
        }

        return out;
    }

    private int getRed(int rgb) {
        return (rgb & 0x00FF0000) >> 16;
    }

    private int getGrn(int rgb) {
        return (rgb & 0x0000FF00) >> 8;
    }

    private int getBlu(int rgb) {
        return (rgb & 0x000000FF);
    }

    private int getRGB(int r, int g, int b) {
        return ((Math.min(r, 255) & 0x000000FF) << 16) | ((Math.min(g, 255) & 0x000000FF) << 8) | (Math.min(b, 255) & 0x000000FF);
    }

    public static void main(String[] args) {
        Random rand = new Random();
        BufferedImage img = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                if ((i == 0) || (j == 0) || (i == img.getWidth()-1) || (j == img.getHeight() -1)) {
                    img.setRGB(i, j, 0);
                } else {
                    img.setRGB(i, j, rand.nextInt());
                }
            }
        }

        long start = System.nanoTime();
        new Cellular(img, 5000);
        long end = System.nanoTime();

        System.out.println("Elapsed: " + ((end - start) / 1000 / 1000 / 1000.0) + "s");
    }
}
