package ru.liga.prerevolutionarytinderimagecreator.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.docx4j.org.capaxit.imagegenerator.Margin;
import org.docx4j.org.capaxit.imagegenerator.TextImage;
import org.docx4j.org.capaxit.imagegenerator.TextWrapper;
import org.docx4j.org.capaxit.imagegenerator.impl.TextImageImpl;
import org.docx4j.org.capaxit.imagegenerator.textalign.GreedyTextWrapper;
import org.springframework.stereotype.Service;
import ru.liga.prerevolutionarytinderimagecreator.exception.FontRegistrationException;
import ru.liga.prerevolutionarytinderimagecreator.service.ResponsiveFontService;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ResponsiveFontServiceImpl implements ResponsiveFontService {
    /**
     * Коэффициенты увеличения шрифтов
     */
    private static final int PLAIN_COEFFICIENT = 1;
    private static final double BOLD_COEFFICIENT = 1.8;

    @Override
    public void registerFont(GraphicsEnvironment ge, String filePath, boolean isBold) {
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

    @Override
    public Map<Integer, Integer> getFontSize(TextImage textImage, Margin margin, String header, String description, Font bold, Font plain) {
        TextImageImpl image = new TextImageImpl(textImage.getWidth(), textImage.getHeight(), margin);
        int maxTextHeight = textImage.getHeight() - margin.getTop() - margin.getBottom();
        int maxLineWidth = image.getWidth() - margin.getLeft() - margin.getRight();
        int boldTextHeight = 0;
        int plainTextHeight = 0;
        int headerLineWidth = 0;
        Font currentBold = bold;
        Font currentPlain = plain;

        while (boldTextHeight + plainTextHeight < maxTextHeight && headerLineWidth < maxLineWidth - 1) {
            int currentPlainSize = currentPlain.getSize() + PLAIN_COEFFICIENT;
            int currentBoldSize = (int) (currentPlainSize * BOLD_COEFFICIENT);

            currentBold = new Font(currentBold.getFontName(), currentBold.getStyle(), currentBoldSize);
            currentPlain = new Font(currentBold.getFontName(), currentBold.getStyle(), currentPlainSize);
            Canvas canvas = new Canvas();
            FontMetrics fmBold = canvas.getFontMetrics(currentBold);
            FontMetrics fmPlain = canvas.getFontMetrics(currentPlain);
            TextWrapper textWrapper = new GreedyTextWrapper();
            java.util.List<String> wrappedBoldText = textWrapper.doWrap(header, maxLineWidth, fmBold);
            List<String> wrappedPlainText = textWrapper.doWrap(description, maxLineWidth, fmPlain);

            headerLineWidth = fmBold.stringWidth(header);
            boldTextHeight = wrappedBoldText.size() * fmBold.getHeight();
            plainTextHeight = wrappedPlainText.size() * fmPlain.getHeight();
        }
        Map<Integer, Integer> fontSizes = new HashMap<>();

        log.debug("Header font size: {}", currentBold.getSize());
        log.debug("Description font size {}", currentPlain.getSize());
        fontSizes.put(Font.BOLD, currentBold.getSize());
        fontSizes.put(Font.PLAIN, currentPlain.getSize());
        return fontSizes;
    }
}
