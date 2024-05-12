package dev.chat.service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class EmptyMultipartFile implements MultipartFile {

    @Override
    public String getName() {
        return "empty";
    }

    @Override
    public String getOriginalFilename() {
        return "empty";
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public long getSize() {
        return 0;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return new byte[0];
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(new byte[0]);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        // Пустой файл не может быть передан
    }
}