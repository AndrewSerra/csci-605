package homework_10;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * This is a thread that processes data in a queue if there is data. Data
 * processed is written to a shared buffered image.
 *
 * @author Andrew Serra
 * @author Anindhya Kushagra
 */
public class WriterWorker extends Thread {

    static final int red = Color.RED.getRGB();
    static final int blue = Color.BLUE.getRGB();
    int sideLength;
    int squareSize;
    final BufferedImage bImage;
    final ProcessQueue queue;

    public WriterWorker(ProcessQueue q,
                        BufferedImage image,
                        Integer imageSideSize,
                        Integer sqSize) {
        queue = q;
        bImage = image;
        sideLength = imageSideSize;
        squareSize = sqSize;
    }

    @Override
    public void run() {
        synchronized (queue) {
            synchronized (bImage) {
                try {
                    while (!queue.isEmpty()) {
                        Integer[] data = queue.dequeue();

                        int xOrig = (data[0] % sideLength) * squareSize;
                        int yOrig = (data[0] / sideLength) * squareSize;
                        int color = data[1] % 2 == 0 ? red : blue;

                        for (int x = 0; x < squareSize; x++)
                            for (int y = 0; y < squareSize; y++)
                                bImage.setRGB(xOrig + x, yOrig + y, color);
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
