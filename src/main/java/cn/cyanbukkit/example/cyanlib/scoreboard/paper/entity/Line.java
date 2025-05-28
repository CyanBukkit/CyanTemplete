package cn.cyanbukkit.example.cyanlib.scoreboard.paper.entity;

import cn.cyanbukkit.example.cyanlib.scoreboard.paper.animation.core.Animation;
import net.kyori.adventure.text.Component;

/**
 * @Author <a href="https://github.com/Cobeine">Cobeine</a>
 */

public interface Line<T> {
    T getContent();

    void setContent(T content);

    int getIndex();

    void setIndex(int index);

    Animation<T> getAnimation();

    void setAnimation(Animation<T> animation);

    default T fetchContent() {
        return getAnimation() == null ? getContent() : getAnimation().fetchNextChange();
    }

    static LineImplementation.LegacyLine legacy(String content, int index) {
        return new LineImplementation.LegacyLine(content, index);
    }

    static LineImplementation.AdventureLine adventure(Component content, int index) {
        return new LineImplementation.AdventureLine(content, index);
    }

    class LineImplementation<T> implements Line<T> {
        private T content;

        public T getContent() {
            return content;
        }

        public void setContent(T content) {
            this.content = content;
        }

        private int index;

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        private Animation<T> animation;

        public Animation<T> getAnimation() {
            return animation;
        }

        public void setAnimation(Animation<T> animation) {
            this.animation = animation;
        }

        public LineImplementation(T content, int index) {
            this.content = content;
            this.index = index;
            this.animation = null;
        }

        public static class LegacyLine extends LineImplementation<String> {
            public LegacyLine(String content, int index) {
                super(content, index);
            }
        }

        public static class AdventureLine extends LineImplementation<Component> {
            public AdventureLine(Component content, int index) {
                super(content, index);
            }
        }
    }
}
