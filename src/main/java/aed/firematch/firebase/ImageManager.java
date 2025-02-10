package aed.firematch.firebase;

import java.util.HashMap;
import java.util.Map;

public class ImageManager {
    private Map<Integer, String> imagePaths;

    public ImageManager() {
        imagePaths = new HashMap<>();
        // Populate the map with user IDs and image paths
        imagePaths.put(1, "images/usuario1.jpg");
        imagePaths.put(2, "images/usuario2.jpg");
        imagePaths.put(3, "images/usuario3.jpg");
        imagePaths.put(4, "images/usuario4.jpg");
        imagePaths.put(5, "images/usuario5.jpg");
        imagePaths.put(6, "images/usuario6.jpg");
        imagePaths.put(7, "images/usuario7.jpg");
        imagePaths.put(8, "images/usuario8.jpg");
        imagePaths.put(9, "images/usuario9.jpg");
        imagePaths.put(10, "images/usuario10.jpg");
        imagePaths.put(11, "images/usuario11.jpg");
        imagePaths.put(12, "images/usuario12.jpg");
        imagePaths.put(13, "images/usuario13.jpg");
        imagePaths.put(14, "images/usuario14.jpg");
        imagePaths.put(15, "images/usuario15.jpg");
        imagePaths.put(16, "images/usuario16.jpg");
    }

    public String getImagePath(int userId) {
        return imagePaths.get(userId);
    }
}