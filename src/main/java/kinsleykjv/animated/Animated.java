package kinsleykjv.animated;


import javafx.scene.Node;
import kinsleykjv.animated.property.PropertyWrapper;

/**
 * A node that automatically animates a property of its child.
 * @param <T> type of the target property
 * @author Giorgio Garofalo
 */
public class Animated<T> extends SingleChildParent implements CustomizableAnimation<Animated<T>> {

    private final  AnimationProperty<T> property;

    /**
     * Instantiates an {@link Animated} node
     * @param child initial child
     * @param property target property
     * @param settings animation settings
     */
    public Animated(Node child,  AnimationProperty<T> property,  AnimationSettings settings) {
        super(child);
        this.property = property.withSettings(settings);
        this.property.register(child);
    }

    /**
     * Instantiates an {@link Animated} node
     * @param child initial child
     * @param property target property
     * @param settings animation settings
     */
    public Animated(Node child, PropertyWrapper<T> property, AnimationSettings settings) {
        this(child, new  AnimationProperty<>(property), settings);
    }

    /**
     * Instantiates an {@link Animated} node
     * @param child initial child
     * @param property target property
     */
    public Animated(Node child, PropertyWrapper<T> property) {
        this(child, property, property.getSettings());
    }

    /**
     * Instantiates an {@link Animated} node
     * @param property target property
     */
    public Animated(PropertyWrapper<T> property) {
        this(null, property);
    }

    /**
     * @return target property
     */
    public AnimationProperty<T> getProperty() {
        return property;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public  AnimationSettings getSettings() {
        return property.getSettings();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public <A extends Animated<T>> A withSettings(AnimationSettings settings) {
        this.property.withSettings(settings);
        return (A) this;
    }
}
