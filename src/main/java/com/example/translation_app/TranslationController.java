package com.example.translation_app;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TranslationController {

    private final Translate translate;

    public TranslationController() {
        this.translate = TranslateOptions.getDefaultInstance().getService();
    }

    @PostMapping("/translate")
    public List<String> translateText(@RequestBody TranslateRequest request) {
        String[] sentences = request.getText().split("\\. ");
        List<String> result = new ArrayList<>();

        for (String sentence : sentences) {
            String trimmedSentence = sentence.trim();
            Translation translation = translate.translate(
                    trimmedSentence,
                    Translate.TranslateOption.targetLanguage(request.getTargetLanguage())
            );
            result.add(translation.getTranslatedText());
            result.add(trimmedSentence);
        }

        return result;
    }
}
