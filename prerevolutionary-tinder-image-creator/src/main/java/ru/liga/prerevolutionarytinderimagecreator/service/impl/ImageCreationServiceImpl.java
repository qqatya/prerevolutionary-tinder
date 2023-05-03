package ru.liga.prerevolutionarytinderimagecreator.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.docx4j.org.capaxit.imagegenerator.Margin;
import org.docx4j.org.capaxit.imagegenerator.TextImage;
import org.docx4j.org.capaxit.imagegenerator.imageexporter.exporters.PngImageWriter;
import org.docx4j.org.capaxit.imagegenerator.impl.TextImageImpl;
import org.springframework.stereotype.Service;
import ru.liga.prerevolutionarytinderimagecreator.exception.BackgroundNotFoundException;
import ru.liga.prerevolutionarytinderimagecreator.exception.ImageCreationException;
import ru.liga.prerevolutionarytinderimagecreator.service.ImageCreationService;
import ru.liga.prerevolutionarytinderimagecreator.service.ResponsiveFontService;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
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
    private static final int BOTTOM = 80;

    /**
     * Шрифты и фон
     */
    private static final String FONT_PLAIN = "/static/fonts/OldStandard-Regular.ttf";
    private static final String FONT_BOLD = "/static/fonts/OldStandard-Bold.ttf";
    private static final String BACKGROUND = "/static/prerev-background.jpg";

    private final ResponsiveFontService responsiveFontService;

    @Override
    public byte[] createPicture(String header, String description) {
        log.info("Start creating image");
        Margin margin = new Margin(LEFT, TOP, RIGHT, BOTTOM);
        TextImage textImage = new TextImageImpl(WIDTH, HEIGHT, margin);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        int initialFontSize = 1;

        responsiveFontService.registerFont(ge, FONT_PLAIN, false);
        log.debug("Registered font: {}", FONT_PLAIN);
        responsiveFontService.registerFont(ge, FONT_BOLD, true);
        log.debug("Registered font: {}", FONT_BOLD);
        Map<Integer, Integer> fontSizes = responsiveFontService.getFontSize(textImage, margin, header, description,
                new Font("Old Standard TT", Font.BOLD, initialFontSize),
                new Font("Old Standard TT", Font.BOLD, initialFontSize));
        Font plain = new Font("Old Standard TT", Font.PLAIN, fontSizes.get(Font.PLAIN));
        Font bold = new Font("Old Standard TT", Font.BOLD, fontSizes.get(Font.BOLD));
        int oX = 0;
        int oY = 0;

        textImage.write(getBackground(), oX, oY);
        log.debug("Applied image background");
        textImage.withFont(bold).wrap(true).write(header);
        log.debug("Applied header text");
        textImage.withFont(plain).wrap(true).write(description);
        log.debug("Applied description text");
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            PngImageWriter pngImageWriter = new PngImageWriter();
            pngImageWriter.writeImageToOutputStream(textImage, os);
            log.info("Successfully created image");
            return os.toByteArray();
        } catch (IOException e) {
            throw new ImageCreationException();
        }

    }

    private Image getBackground() {
        try (InputStream is = ImageCreationServiceImpl.class.getResourceAsStream(ImageCreationServiceImpl.BACKGROUND)) {
            log.debug("Found background resource: {}", BACKGROUND);
            return ImageIO.read(is);
        } catch (IOException e) {
            throw new BackgroundNotFoundException(ImageCreationServiceImpl.BACKGROUND);
        }
    }
}
