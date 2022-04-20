package resources;

import java.awt.Image;
import java.util.EnumMap;

public class ResourceCache {
    private final EnumMap<ImageResource, Image> imageCache;

    public ResourceCache() {
        imageCache = new EnumMap<>(ImageResource.class);
    }

    public Image tryGet(ImageResource type, IImageLoader loader) {
        return imageCache.computeIfAbsent(type, loader::getImage);
    }
}