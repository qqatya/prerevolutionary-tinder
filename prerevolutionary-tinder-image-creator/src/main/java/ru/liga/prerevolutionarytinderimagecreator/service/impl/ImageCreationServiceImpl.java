package ru.liga.prerevolutionarytinderimagecreator.service.impl;

import org.docx4j.org.capaxit.imagegenerator.Margin;
import org.docx4j.org.capaxit.imagegenerator.TextImage;
import org.docx4j.org.capaxit.imagegenerator.TextWrapper;
import org.docx4j.org.capaxit.imagegenerator.imageexporter.exporters.PngImageWriter;
import org.docx4j.org.capaxit.imagegenerator.impl.TextImageImpl;
import org.docx4j.org.capaxit.imagegenerator.textalign.GreedyTextWrapper;
import org.springframework.stereotype.Service;
import ru.liga.prerevolutionarytinderimagecreator.exception.BackgroundNotFoundException;
import ru.liga.prerevolutionarytinderimagecreator.exception.FontRegistrationException;
import ru.liga.prerevolutionarytinderimagecreator.exception.ImageCreationException;
import ru.liga.prerevolutionarytinderimagecreator.service.ImageCreationService;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ImageCreationServiceImpl implements ImageCreationService {
    /**
     * Ширина и высота изображения
     */
    private static final int WIDTH = 626;
    private static final int HEIGHT = 626;

    /**
     * LEFT, RIGHT, TOP, BOTTOM - отступы на изображении
     */
    private static final int LEFT = 50;
    private static final int RIGHT = 50;
    private static final int TOP = 80;
    private static final int BOTTOM = 130;

    /**
     * Шрифты и фон
     */
    private static final String FONT_PLAIN = "/static/fonts/OldStandard-Regular.ttf";
    private static final String FONT_BOLD = "/static/fonts/OldStandard-Bold.ttf";
    private static final String BACKGROUND = "/static/prerev-background.jpg";

    @Override
    public byte[] createPicture(String header, String description) {
        Margin margin = new Margin(LEFT, TOP, RIGHT, BOTTOM);
        TextImage testImage = new TextImageImpl(WIDTH, HEIGHT, margin);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

        registerFont(ge, FONT_PLAIN, false);
        registerFont(ge, FONT_BOLD, true);
        Map<Integer, Integer> fontSizes = getFontSize(testImage, margin, header, description,
                new Font("Old Standard TT", Font.BOLD, 1),
                new Font("Old Standard TT", Font.BOLD, 1));
        Font plain = new Font("Old Standard TT", Font.PLAIN, fontSizes.get(Font.PLAIN));
        Font bold = new Font("Old Standard TT", Font.BOLD, fontSizes.get(Font.BOLD));
        int oX = 0;
        int oY = 0;

        testImage.write(getBackground(), oX, oY);
        testImage.withFont(bold).wrap(true).write(header);
        testImage.withFont(plain).wrap(true).write(description);
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            PngImageWriter pngImageWriter = new PngImageWriter();
            pngImageWriter.writeImageToOutputStream(testImage, os);
            return os.toByteArray();
        } catch (IOException e) {
            throw new ImageCreationException();
        }

    }

    private void registerFont(GraphicsEnvironment ge, String filePath, boolean isBold) {
        try (InputStream stream = ImageCreationServiceImpl.class
                .getResourceAsStream(filePath)) {
            if (isBold) {
                ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(Font.BOLD));
            } else {
                ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(Font.PLAIN));
            }
        } catch (IOException | FontFormatException e) {
            throw new FontRegistrationException("Не удалось создать шрифт");
        }
    }

    private Map<Integer, Integer> getFontSize(TextImage textImage, Margin margin, String header, String description,
                                              Font bold, Font plain) {
        TextImageImpl image = new TextImageImpl(textImage.getWidth(), textImage.getHeight(), margin);
        int maxTextHeight = textImage.getHeight() - margin.getTop() - margin.getBottom();
        int maxLineWidth = image.getWidth() - margin.getLeft() - margin.getRight();
        int boldTextHeight = 0;
        int plainTextHeight = 0;
        Font currentBold = bold;
        Font currentPlain = plain;

        while (boldTextHeight < maxTextHeight && plainTextHeight < maxTextHeight) {
            int currentPlainSize = currentPlain.getSize() + 1;
            int currentBoldSize = (int) (currentPlainSize * 1.6);

            currentBold = new Font(currentBold.getFontName(), currentBold.getStyle(), currentBoldSize);
            currentPlain = new Font(currentBold.getFontName(), currentBold.getStyle(), currentPlainSize);
            Canvas canvas = new Canvas();
            FontMetrics fmBold = canvas.getFontMetrics(currentBold);
            FontMetrics fmPlain = canvas.getFontMetrics(currentPlain);
            TextWrapper textWrapper = new GreedyTextWrapper();
            List<String> wrappedBoldText = textWrapper.doWrap(header, maxLineWidth, fmBold);
            List<String> wrappedPlainText = textWrapper.doWrap(description, maxLineWidth, fmPlain);

            boldTextHeight = wrappedBoldText.size() * fmBold.getHeight();
            plainTextHeight = wrappedPlainText.size() * fmPlain.getHeight();
        }
        Map<Integer, Integer> fontSizes = new HashMap<>();

        fontSizes.put(Font.BOLD, currentBold.getSize());
        fontSizes.put(Font.PLAIN, currentPlain.getSize());
        return fontSizes;
    }

    private Image getBackground() {
        try (InputStream is = ImageCreationServiceImpl.class.getResourceAsStream(ImageCreationServiceImpl.BACKGROUND)) {
            return ImageIO.read(is);
        } catch (IOException e) {
            throw new BackgroundNotFoundException(ImageCreationServiceImpl.BACKGROUND);
        }
    }
}
