package IO;

import Components.Component;

/**
 * Created by danielkim802 on 1/30/17.
 */
public interface SerializableComponent {

    // stores relevant information of the component that is serializable
    void serialize(Component component);

    // recreates component based on stored information
    Component make();
}
