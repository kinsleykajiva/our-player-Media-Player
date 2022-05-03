package kinsleykjv.animated;


import javafx.scene.Node;
import kinsleykjv.animated.property.DoublePropertyWrapper;

/**
 * Node that animates its child's translate X and Y.
 * @author Giorgio Garofalo
 */
public class AnimatedPosition extends AnimatedMulti {

    public AnimatedPosition(Node child) {
        super(child, new DoublePropertyWrapper(child.translateXProperty()), new DoublePropertyWrapper(child.translateYProperty()));
    }
}
