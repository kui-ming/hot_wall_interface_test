package com.example.csust_hot_wall.tools;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class MyMultipartFile implements MultipartFile {

    private final byte[] context;
    private final String originalFileName;
    private final String type;
    private String name;

    public MyMultipartFile(byte[] context,String originalFileName,String FileType){
        this.context = context;
        this.originalFileName = originalFileName;
        this.type = FileType;
    }

    @Override
    public String getName() {
        if (name == null){
            name = Utils.md5(context);
            name += ".".concat(originalFileName.split(".")[1]);
        }
        return name;
    }

    @Override
    public String getOriginalFilename() {
        return originalFileName;
    }

    @Override
    public String getContentType() {
        return type;
    }

    @Override
    public boolean isEmpty() {
        return context.length < 1;
    }

    @Override
    public long getSize() {
        return context.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return context;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(context);
    }

    @Override
    public void transferTo(File file) throws IOException, IllegalStateException {
        new FileOutputStream(file).write(context);
    }
}
