package homework_10;

// original from: http://rosettacode.org/wiki/Pi_set#Java
// modified for color

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import java.io.*;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;

/**
 * This is a class generating a blue and red image based on incoming data from
 * a file given as an argument or as an input stream.
 *
 * @author Andrew Serra
 * @author Anindhya Kushagra
 */
public class Pi extends JFrame {

    private static final long serialVersionUID = 1001L;
    private final int LENGTH_OF_SQUARE = 3;
    private final int LENGTH = 330;
    private final int LENGTH_OF_WINDOW = LENGTH * LENGTH_OF_SQUARE;
    private BufferedImage theImage;
    private final ProcessQueue queue;

    public Pi(String filename) {
        super("Pi");

        setBounds(100, 100, LENGTH_OF_WINDOW, LENGTH_OF_WINDOW);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        queue = new ProcessQueue(LENGTH * LENGTH);
        int count = 0;

        try ( Reader input = getReader(filename) ) {
            int data;

            while((data = input.read()) != -1) {
                if (!queue.isFull() && isDigit((char) data)) {
                    int digit = Integer.parseInt(String.valueOf((char) data));
                    queue.enqueue(count++, digit);
                } else if(queue.isFull()) {
                    break;
                }
            }

            if(!queue.isFull()) {
                throw new InsufficientDataException("Not enough digits " +
                        "provided.");
            }
        } catch (InsufficientDataException e) {
            System.out.println(e.getMessage());
            System.exit(2);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Assigns a Reader object depending on the filename argument passed to the
     * program. If the string is empty, InputStreamReader, if not, FileReader
     * is returned.
     *
     * @param filename - Empty string or filename of the data file
     * @return Reader object which is either InputStreamReader or FileReader
     * @throws FileNotFoundException
     */
    private Reader getReader(String filename) throws FileNotFoundException {
        return filename.isEmpty() ? (
                    new InputStreamReader(System.in)
                ) : (
                    new FileReader(filename)
                );
    }

    /**
     * Checks if a given character is a digit
     *
     * @param data Any character
     * @return True if it is a digit, false if not
     */
    private boolean isDigit(char data) {
        return Pattern.matches("^\\d$", String.valueOf(data));
    }

    /**
     * Writes the BufferedImage object to a file.
     * @param theImage A BufferedImage object
     */
    public void saveImage(BufferedImage theImage) {
        try {
            String suffix = "png";
            File outputfile = new File("output." + suffix);
            ImageIO.write(theImage, suffix, outputfile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates threads to run the data processing and image writing. As soon
     * as the threads are spawned, they are waited to end and then resumes
     * the program.
     */
    public void createImage() {
        theImage = new BufferedImage(getWidth(), getHeight(),
                BufferedImage.TYPE_INT_RGB);
        WriterWorker[] workers = new WriterWorker[] {
                new WriterWorker(queue, theImage, LENGTH, LENGTH_OF_SQUARE),
                new WriterWorker(queue, theImage, LENGTH, LENGTH_OF_SQUARE),
                new WriterWorker(queue, theImage, LENGTH, LENGTH_OF_SQUARE),
        };

        // Start all threads
        for(WriterWorker worker : workers) {
            worker.start();
        }

        try {
            // Join all threads
            for (WriterWorker worker : workers) {
                worker.join();
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        repaint();
        saveImage(theImage);
        System.exit(0);
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(theImage, 0, 0, this);
    }

    public static void main(String[] args) {
        if(args.length > 1) {
            throw new IllegalArgumentException(
                    "Only one argument or none allowed.");
        }

        Pi aPi = new Pi(args.length == 1 ? args[0] : "");
        aPi.setVisible(true);
        aPi.createImage();
    }
}
