package net.netii.phi.andromeda;

import android.content.res.Resources;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

public class TextUtils {

    public static boolean isPunctuation(char character) {
        return character == ',' || character == '.' ||character == ':' ||character == ';' ||character == '·' ||character == '᾽';
    }

    public static String removePunctuation(String word) {
        word = word.replace(",", "");
        word = word.replace("\\.", "");
        word = word.replace(":", "");
        word = word.replace(";", "");
        word = word.replace("·", "");
        word = word.replace("᾽", "");
        return word;
    }

    public static String greekToBeta(String word, boolean diacritics) {
        word = word.toLowerCase();
        if (diacritics) {
            word = word.replaceAll("α", "a");
            word = word.replace("ά", "a/");
            word = word.replace("ὰ", "a\\");
            word = word.replace("ᾶ", "a=");
            word = word.replace("ἀ", "a)");
            word = word.replace("ἄ", "a)/");
            word = word.replace("ἂ", "a)\\");
            word = word.replace("ἆ", "a)=");
            word = word.replace("ἁ", "a(");
            word = word.replace("ἅ", "a(/");
            word = word.replace("ἃ", "a(\\");
            word = word.replace("ἇ", "a(=");
            word = word.replace("ᾳ", "a|");
            word = word.replace("ᾴ", "a/|");
            word = word.replace("ᾲ", "a\\|");
            word = word.replace("ᾷ", "a=|");
            word = word.replace("ᾀ", "a)|");
            word = word.replace("ᾄ", "a)/|");
            word = word.replace("ᾂ", "a)\\|");
            word = word.replace("ᾆ", "a)=|");
            word = word.replace("ᾁ", "a(|");
            word = word.replace("ᾅ", "a(/|");
            word = word.replace("ᾃ", "a(\\|");
            word = word.replace("ᾇ", "a(=|");
            word = word.replace("β", "b");
            word = word.replace("γ", "g");
            word = word.replace("δ", "d");
            word = word.replace("ε", "e");
            word = word.replace("έ", "e/");
            word = word.replace("ὲ", "e\\");
            word = word.replace("ἐ", "e)");
            word = word.replace("ἔ", "e)/");
            word = word.replace("ἒ", "e)\\");
            word = word.replace("ἑ", "e(");
            word = word.replace("ἕ", "e(/");
            word = word.replace("ἓ", "e(\\");
            word = word.replace("ζ", "z");
            word = word.replace("η", "h");
            word = word.replace("ή", "h/");
            word = word.replace("ὴ", "h\\");
            word = word.replace("ῆ", "h=");
            word = word.replace("ἠ", "h)");
            word = word.replace("ἤ", "h)/");
            word = word.replace("ἢ", "h)\\");
            word = word.replace("ἦ", "h)=");
            word = word.replace("ἡ", "h(");
            word = word.replace("ἥ", "h(/");
            word = word.replace("ἣ", "h(\\");
            word = word.replace("ἧ", "h(=");
            word = word.replace("ῃ", "h|");
            word = word.replace("ῄ", "h/|");
            word = word.replace("ῂ", "h\\|");
            word = word.replace("ῇ", "h=|");
            word = word.replace("ᾐ", "h)|");
            word = word.replace("ᾔ", "h)/|");
            word = word.replace("ᾒ", "h)\\|");
            word = word.replace("ᾖ", "h)=|");
            word = word.replace("ᾑ", "h(|");
            word = word.replace("ᾕ", "h(/|");
            word = word.replace("ᾓ", "h(\\|");
            word = word.replace("ᾗ", "h(=|");
            word = word.replace("θ", "q");
            word = word.replace("ι", "i");
            word = word.replace("ί", "i/");
            word = word.replace("ὶ", "i\\");
            word = word.replace("ῖ", "i=");
            word = word.replace("ἰ", "i)");
            word = word.replace("ἴ", "i)/");
            word = word.replace("ἲ", "i)\\");
            word = word.replace("ἶ", "i)=");
            word = word.replace("ἱ", "i(");
            word = word.replace("ἵ", "i(/");
            word = word.replace("ἳ", "i(\\");
            word = word.replace("ἷ", "i(=");
            word = word.replace("ϊ", "i+");
            word = word.replace("ΐ", "i+/");
            word = word.replace("ῒ", "i+\\");
            word = word.replace("ῗ", "i+=");
            word = word.replace("κ", "k");
            word = word.replace("λ", "l");
            word = word.replace("μ", "m");
            word = word.replace("ν", "n");
            word = word.replace("ξ", "c");
            word = word.replace("ο", "o");
            word = word.replace("ό", "o/");
            word = word.replace("ὸ", "o\\");
            word = word.replace("ὀ", "o)");
            word = word.replace("ὄ", "o)/");
            word = word.replace("ὂ", "o)\\");
            word = word.replace("ὁ", "o(");
            word = word.replace("ὅ", "o(/");
            word = word.replace("ὃ", "o(\\");
            word = word.replace("π", "p");
            word = word.replace("ρ", "r");
            word = word.replace("ῤ", "r");
            word = word.replace("ῥ", "r");
            word = word.replace("σ", "s");
            word = word.replace("ς", "s");
            word = word.replace("τ", "t");
            word = word.replace("υ", "u");
            word = word.replace("ύ", "u/");
            word = word.replace("ὺ", "u\\");
            word = word.replace("ῦ", "u=");
            word = word.replace("ὐ", "u)");
            word = word.replace("ὔ", "u)/");
            word = word.replace("ὒ", "u)\\");
            word = word.replace("ὖ", "u)=");
            word = word.replace("ὑ", "u(");
            word = word.replace("ὕ", "u(/");
            word = word.replace("ὓ", "u(\\");
            word = word.replace("ὗ", "u(=");
            word = word.replace("ϋ", "u+");
            word = word.replace("ΰ", "u+/");
            word = word.replace("ῢ", "u+\\");
            word = word.replace("ῧ", "u+=");
            word = word.replace("φ", "f");
            word = word.replace("χ", "x");
            word = word.replace("ψ", "y");
            word = word.replace("ω", "w");
            word = word.replace("ώ", "w/");
            word = word.replace("ὼ", "w\\");
            word = word.replace("ῶ", "w=");
            word = word.replace("ὠ", "w)");
            word = word.replace("ὤ", "w)/");
            word = word.replace("ὢ", "w)\\");
            word = word.replace("ὦ", "w)=");
            word = word.replace("ὡ", "w(");
            word = word.replace("ὥ", "w(/");
            word = word.replace("ὣ", "w(\\");
            word = word.replace("ὧ", "w(=");
            word = word.replace("ῳ", "w|");
            word = word.replace("ῴ", "w/|");
            word = word.replace("ῲ", "w\\|");
            word = word.replace("ῷ", "w=|");
            word = word.replace("ᾠ", "w)|");
            word = word.replace("ᾤ", "w)/|");
            word = word.replace("ᾢ", "w)\\|");
            word = word.replace("ᾦ", "w)=|");
            word = word.replace("ᾡ", "w(|");
            word = word.replace("ᾥ", "w(/|");
            word = word.replace("ᾣ", "w(\\|");
            word = word.replace("ᾧ", "w(=|");
        } else {
            word = word.replace("α", "a");
            word = word.replace("ά", "a");
            word = word.replace("ὰ", "a");
            word = word.replace("ᾶ", "a");
            word = word.replace("ἀ", "a");
            word = word.replace("ἄ", "a");
            word = word.replace("ἂ", "a");
            word = word.replace("ἆ", "a");
            word = word.replace("ἁ", "a");
            word = word.replace("ἅ", "a");
            word = word.replace("ἃ", "a");
            word = word.replace("ἇ", "a");
            word = word.replace("ᾳ", "a");
            word = word.replace("ᾴ", "a");
            word = word.replace("ᾲ", "a");
            word = word.replace("ᾷ", "a");
            word = word.replace("ᾀ", "a");
            word = word.replace("ᾄ", "a");
            word = word.replace("ᾂ", "a");
            word = word.replace("ᾆ", "a");
            word = word.replace("ᾁ", "a");
            word = word.replace("ᾅ", "a");
            word = word.replace("ᾃ", "a");
            word = word.replace("ᾇ", "a");
            word = word.replace("β", "b");
            word = word.replace("γ", "g");
            word = word.replace("δ", "d");
            word = word.replace("ε", "e");
            word = word.replace("έ", "e");
            word = word.replace("ὲ", "e");
            word = word.replace("ἐ", "e");
            word = word.replace("ἔ", "e");
            word = word.replace("ἒ", "e");
            word = word.replace("ἑ", "e");
            word = word.replace("ἕ", "e");
            word = word.replace("ἓ", "e");
            word = word.replace("ζ", "z");
            word = word.replace("η", "h");
            word = word.replace("ή", "h");
            word = word.replace("ὴ", "h");
            word = word.replace("ῆ", "h");
            word = word.replace("ἠ", "h");
            word = word.replace("ἤ", "h");
            word = word.replace("ἢ", "h");
            word = word.replace("ἦ", "h");
            word = word.replace("ἡ", "h");
            word = word.replace("ἥ", "h");
            word = word.replace("ἣ", "h");
            word = word.replace("ἧ", "h");
            word = word.replace("ῃ", "h");
            word = word.replace("ῄ", "h");
            word = word.replace("ῂ", "h");
            word = word.replace("ῇ", "h");
            word = word.replace("ᾐ", "h");
            word = word.replace("ᾔ", "h");
            word = word.replace("ᾒ", "h");
            word = word.replace("ᾖ", "h");
            word = word.replace("ᾑ", "h");
            word = word.replace("ᾕ", "h");
            word = word.replace("ᾓ", "h");
            word = word.replace("ᾗ", "h");
            word = word.replace("θ", "q");
            word = word.replace("ι", "i");
            word = word.replace("ί", "i");
            word = word.replace("ὶ", "i");
            word = word.replace("ῖ", "i");
            word = word.replace("ἰ", "i");
            word = word.replace("ἴ", "i");
            word = word.replace("ἲ", "i");
            word = word.replace("ἶ", "i");
            word = word.replace("ἱ", "i");
            word = word.replace("ἵ", "i");
            word = word.replace("ἳ", "i");
            word = word.replace("ἷ", "i");
            word = word.replace("ϊ", "i");
            word = word.replace("ΐ", "i");
            word = word.replace("ῒ", "i");
            word = word.replace("ῗ", "i");
            word = word.replace("κ", "k");
            word = word.replace("λ", "l");
            word = word.replace("μ", "m");
            word = word.replace("ν", "n");
            word = word.replace("ξ", "c");
            word = word.replace("ο", "o");
            word = word.replace("ό", "o");
            word = word.replace("ὸ", "o");
            word = word.replace("ὀ", "o");
            word = word.replace("ὄ", "o");
            word = word.replace("ὂ", "o");
            word = word.replace("ὁ", "o");
            word = word.replace("ὅ", "o");
            word = word.replace("ὃ", "o");
            word = word.replace("π", "p");
            word = word.replace("ρ", "r");
            word = word.replace("ῤ", "r");
            word = word.replace("ῥ", "r");
            word = word.replace("σ", "s");
            word = word.replace("ς", "s");
            word = word.replace("τ", "t");
            word = word.replace("υ", "u");
            word = word.replace("ύ", "u");
            word = word.replace("ὺ", "u");
            word = word.replace("ῦ", "u");
            word = word.replace("ὐ", "u");
            word = word.replace("ὔ", "u");
            word = word.replace("ὒ", "u");
            word = word.replace("ὖ", "u");
            word = word.replace("ὑ", "u");
            word = word.replace("ὕ", "u");
            word = word.replace("ὓ", "u");
            word = word.replace("ὗ", "u");
            word = word.replace("ϋ", "u");
            word = word.replace("ΰ", "u");
            word = word.replace("ῢ", "u");
            word = word.replace("ῧ", "u");
            word = word.replace("φ", "f");
            word = word.replace("χ", "x");
            word = word.replace("ψ", "y");
            word = word.replace("ω", "w");
            word = word.replace("ώ", "w");
            word = word.replace("ὼ", "w");
            word = word.replace("ῶ", "w");
            word = word.replace("ὠ", "w");
            word = word.replace("ὤ", "w");
            word = word.replace("ὢ", "w");
            word = word.replace("ὦ", "w");
            word = word.replace("ὡ", "w");
            word = word.replace("ὥ", "w");
            word = word.replace("ὣ", "w");
            word = word.replace("ὧ", "w");
            word = word.replace("ῳ", "w");
            word = word.replace("ῴ", "w");
            word = word.replace("ῲ", "w");
            word = word.replace("ῷ", "w");
            word = word.replace("ᾠ", "w");
            word = word.replace("ᾤ", "w");
            word = word.replace("ᾢ", "w");
            word = word.replace("ᾦ", "w");
            word = word.replace("ᾡ", "w");
            word = word.replace("ᾥ", "w");
            word = word.replace("ᾣ", "w");
            word = word.replace("ᾧ", "w");
        }
        return word;
    }

    public interface WordClickedCallback {

        void onWordClicked(String word, WordSpan span);

    }

    public static class WordSpan extends ClickableSpan {

        private String word;
        private WordClickedCallback onWordClicked;
        private boolean highlight;

        public WordSpan(String word, WordClickedCallback onWordClicked) {
            super();
            this.word = word;
            this.onWordClicked = onWordClicked;
        }

        public void setHighlight(boolean value) {
            highlight = value;
        }

        @Override
        public void onClick(View widget) {
            onWordClicked.onWordClicked(word, this);
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(false);
            if (highlight) {
                ds.setColor(TextActivity.highlightColor);
            }
        }
    }

}
