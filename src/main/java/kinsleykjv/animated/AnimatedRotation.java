package kinsleykjv.animated;


import javafx.scene.Node;
import kinsleykjv.animated.property.DoublePropertyWrapper;

/**
 * Node that animates its child's rotation.
 * @author Giorgio Garofalo
 */
public class AnimatedRotation extends Animated<Double> {

    public AnimatedRotation(Node child) {
        super(child, new DoublePropertyWrapper(child.rotateProperty()));
    }
}
