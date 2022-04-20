package resources;

import java.awt.Image;
import java.awt.Toolkit;

public class ResourceManager {

    private static final String Images_DIR = "src/resources/Images/";

    private static final ResourceManager instance = new ResourceManager();

    private final ResourcePathFinder finder;
    private final ResourceCache cache;

    private ResourceManager() {
        finder = new ResourcePathFinder();
        cache = new ResourceCache();
    }

    public static Image get(ImageResource type) {
        return getInstance().getImage(type);
    }

    private static ResourceManager getInstance() {
        return instance;
    }

    private Image getImage(ImageResource type) {
        return cache.tryGet(type,
                t -> Toolkit.getDefaultToolkit().getImage(getResourcePath(t))
        );
    }

    private String getResourcePath(ImageResource type) {
        return Images_DIR + finder.getName(type);
    }
}
