package kinsleykjv.animated;


import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import kinsleykjv.animated.property.ObjectPropertyWrapper;

/**
 * Node that animates its child's color.
 * @author Giorgio Garofalo
 */
public class AnimatedColor extends Animated<Paint> {

    public AnimatedColor(Shape child) {
        super(child, new ObjectPropertyWrapper<>(child.fillProperty()));
    }
}
