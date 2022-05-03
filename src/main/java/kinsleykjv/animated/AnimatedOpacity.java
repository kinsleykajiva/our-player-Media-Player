package kinsleykjv.animated;


import javafx.scene.Node;
import kinsleykjv.animated.property.DoublePropertyWrapper;

/**
 * Node that animates its child's opacity.
 * @author Giorgio Garofalo
 */
public class AnimatedOpacity extends Animated<Double> {

    public AnimatedOpacity(Node child) {
        super(child, new DoublePropertyWrapper(child.opacityProperty()));
    }
}
