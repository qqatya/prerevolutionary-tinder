package ru.liga.prerevolutionarytindertranslator.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.liga.prerevolutionarytindertranslator.repository.ListRepository;
import ru.liga.prerevolutionarytindertranslator.service.TranslationService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class TranslationServiceImpl implements TranslationService {
    private static final String PUNCTUATION_PATTERN = "[\\s,.:!?]+";
    private static final String CONSONANT_PATTERN = "^.*[бвгджзклмнпрстфхцчшщБВГДЖЗЙКЛМНПРСТФХЦЧШЩ]$";
    private static final String VOWEL_PATTERN = "^.*[аяуюоеёэийыАЯУЮОЕЁЭИЙЫ]$";
    private final ListRepository listRepository;

    @Override
    public String translate(String text) {
        return getEs(getFs(getIs(getErs(text + " ")))).trim();
    }

    private String getErs(String text) {
        log.info("Start translation");
        log.debug("Initial string length = {}", text.length());
        char[] chars = text.toCharArray();
        StringBuilder sb = new StringBuilder();
        Pattern punctuationPattern = Pattern.compile(PUNCTUATION_PATTERN);
        Pattern consonantPattern = Pattern.compile(CONSONANT_PATTERN);
        boolean isConsonant = false;
        boolean isUpperCase = false;

        for (char c : chars) {
            Matcher punctuationMatcher = punctuationPattern.matcher(String.valueOf(c));
            Matcher consonantMatcher = consonantPattern.matcher(String.valueOf(c));
            boolean isPunctuationMatch = punctuationMatcher.find();

            if (isPunctuationMatch && isConsonant && isUpperCase) {
                sb.append("Ъ");
            }
            if (isPunctuationMatch && isConsonant && !isUpperCase) {
                sb.append("ъ");
            }
            isConsonant = consonantMatcher.find();
            isUpperCase = Character.isUpperCase(c);
            sb.append(c);
        }
        log.debug("Finished er translation");
        return sb.toString();
    }

    private String getIs(String text) {
        char[] chars = text.toCharArray();
        StringBuilder sb = new StringBuilder();
        Pattern vowelPattern = Pattern.compile(VOWEL_PATTERN);
        boolean isVowel;
        boolean isUpperCase = false;
        boolean charWasI = false;

        for (char c : chars) {
            Matcher vowelMatcher = vowelPattern.matcher(String.valueOf(c));

            isVowel = vowelMatcher.find();
            int lastLetterIdx = sb.length() - 1;

            if (charWasI && isVowel && isUpperCase) {
                sb.replace(lastLetterIdx, sb.length(), "I");
            }
            if (charWasI && isVowel && !isUpperCase) {
                sb.replace(lastLetterIdx, sb.length(), "i");
            }
            isUpperCase = Character.isUpperCase(c);
            charWasI = (c == 'И' || c == 'и');
            sb.append(c);
        }
        log.debug("Finished i translation");
        return sb.toString();
    }

    private String getFs(String text) {
        Pattern punctuationPattern = Pattern.compile(PUNCTUATION_PATTERN);
        StringBuilder temp = new StringBuilder();
        StringBuilder result = new StringBuilder();
        char[] chars = text.toCharArray();

        for (char c : chars) {
            Matcher punctuationMatcher = punctuationPattern.matcher(String.valueOf(c));
            boolean isMatch = punctuationMatcher.find();

            if (isMatch && listRepository.getAllNames().contains(temp.toString())) {
                result.append(temp.toString()
                        .replaceAll("Ф", "Ѳ")
                        .replaceAll("ф", "ѳ"));
                temp.setLength(0);
            } else if (isMatch) {
                result.append(temp);
                temp.setLength(0);
            }
            if (!isMatch) {
                temp.append(c);
            } else {
                result.append(c);
            }
        }
        log.debug("Finished f translation");
        return result.toString();
    }

    private String getEs(String text) {
        char[] tmp = text.toCharArray();
        boolean[] isLetterUppercase = new boolean[tmp.length];

        for (int i = 0; i < tmp.length; i++) {
            isLetterUppercase[i] = Character.isUpperCase(tmp[i]);
        }
        String lowercaseText = text.toLowerCase();

        for (String root : listRepository.getAllRoots()) {
            lowercaseText = lowercaseText.replaceAll(root, root.replaceAll("е", "ѣ"));
        }
        StringBuilder sb = new StringBuilder();

        char[] chars = lowercaseText.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (isLetterUppercase[i]) {
                sb.append(Character.toUpperCase(chars[i]));
            } else {
                sb.append(chars[i]);
            }
        }
        log.debug("Finished e translation");
        log.debug("Current string length = {}", sb.length());
        log.info("Successfully translated text");
        return sb.toString();
    }
}
