package hr.fer.zemris.io;

import lombok.Getter;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Filip Gulan
 */
public class ImageFinder extends SimpleFileVisitor<Path> {

    private List<Path> images;

    public ImageFinder() {
        images = new ArrayList<>();
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        Path name = file.getFileName();
        if (name != null && (name.toString().endsWith(".png") || name.toString().endsWith(".jpg"))) {
            images.add(file);
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        System.err.println(exc);
        return FileVisitResult.CONTINUE;
    }

    public List<Path> getImages() {
        return images;
    }
}
