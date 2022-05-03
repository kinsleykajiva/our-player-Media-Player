package kinsleykjv.animated;

import javafx.scene.Node;
import kinsleykjv.animated.property.DoublePropertyWrapper;

/**
 * Node that animates its child's scale X and Y.
 * @author Giorgio Garofalo
 */
public class AnimatedScale extends AnimatedMulti {

    public AnimatedScale(Node child) {
        super(child, new DoublePropertyWrapper(child.scaleXProperty()), new DoublePropertyWrapper(child.scaleYProperty()));
    }
}
