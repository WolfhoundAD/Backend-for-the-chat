package dev.chat.util;

public class MinioUrlGenerator {
    private static final String MINIO_SERVER_URL = "http://localhost:9000"; // ваш URL MinIO
    private static final String BUCKET_NAME = "chat-photos"; // имя вашего бакета

    public static String generateMinioUrl(String fileName) {
        return String.format("%s/%s/%s", MINIO_SERVER_URL, BUCKET_NAME, fileName);
    }
}
