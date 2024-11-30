package cn.cyanbukkit.example.cyanlib.scorebroad;

public class TextLine implements Line {
    private final String text;

    private TextLine(String text) {
        this.text = text;
    }

    public static Line of(String text) {
        return new TextLine(text);
    }

    public String getText() {
        return this.text;
    }

    public String toString() {
        return this.text;
    }
}
