package net.amoc.util;

import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


/**
 * @author Atiqur Rahman
 * @since Jun 1, 2013
 */
public class ImageUtils {
    @SuppressWarnings({"UnusedDeclaration"})
    private static final Logger log = Logger.getLogger(ImageUtils.class);
    private static final int IMG_MAX_DIM = 192;
    public static final int THUMB_IMAGE_DIM = 96;

    public static byte[] getScaledImage(byte[] imageData)
            throws IOException {
        return getScaledImage(imageData, IMG_MAX_DIM);
    }

    public static byte[] getScaledImage(byte[] imageData,
                                        final int imgDimension) throws IOException {

        return getThumbImage(imageData, imgDimension);
    }

    public static byte[] getThumbImage(byte[] imageData, int imgDimension)
            throws IOException {
        Image origImg = Toolkit.getDefaultToolkit().createImage(imageData);
        origImg = new ImageIcon(origImg).getImage();

        int origHeight = origImg.getHeight(null);
        int origWidth = origImg.getWidth(null);
        double scalingFactor = (double) imgDimension / Math.max(origHeight, origWidth);
        int scaledHeight = (int) (origHeight * scalingFactor + 0.5);
        int scaledWidth = (int) (origWidth * scalingFactor + 0.5);


        origImg = origImg.getScaledInstance(scaledWidth, scaledHeight,
                Image.SCALE_REPLICATE);
        origImg = new ImageIcon(origImg).getImage();

        BufferedImage bimage = new BufferedImage(origImg.getWidth(null), origImg.getHeight(null),
                BufferedImage.TYPE_INT_RGB);

        Graphics g = bimage.createGraphics();
        g.drawImage(origImg, 0, 0, null);
        g.dispose();

        ByteArrayOutputStream os = new ByteArrayOutputStream(10);
        ImageIO.write(bimage, "jpg", os);

        return os.toByteArray();
    }
}
