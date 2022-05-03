package kinsleykjv.animated.property;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;

/**
 * Wraps a {@link Double} property.
 * @author Giorgio Garofalo
 */
public class DoublePropertyWrapper extends PropertyWrapper<Double> {

    private final DoubleProperty property;

    /**
     * Instantiates a wrapper of a {@link Double} property
     * @param property property to wrap
     */
    public DoublePropertyWrapper(DoubleProperty property) {
        this.property = property;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Property<Double> getProperty() {
        return property.asObject();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double getValue() {
        return property.doubleValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void set(Double value) {
        property.set(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PropertyWrapper<Double> createParallelProperty() {
        return new DoublePropertyWrapper(new SimpleDoubleProperty());
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public void addListener(ChangeListener<? super Double> listener) {
        property.addListener((ChangeListener<? super Number>) listener);
    }
}
