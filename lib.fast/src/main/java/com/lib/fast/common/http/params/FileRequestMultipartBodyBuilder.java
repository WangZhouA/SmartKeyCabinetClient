package com.lib.fast.common.http.params;

import java.io.File;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;

/**
 * created by siwei on 2018/12/3
 */
public class FileRequestMultipartBodyBuilder {

    private MultipartBody.Builder mBuilder;

    private FileRequestMultipartBodyBuilder() {
        mBuilder = new MultipartBody.Builder();
        mBuilder.setType(MultipartBody.FORM);
    }

    public static FileRequestMultipartBodyBuilder create() {
        return new FileRequestMultipartBodyBuilder();
    }

    public FileRequestMultipartBodyBuilder addFormMediaPart(File file){
        return addPart(MultipartBody.FORM, file);
    }

    public FileRequestMultipartBodyBuilder addPart(MediaType mediaType, File file){
        mBuilder.addPart(RequestBody.create(mediaType, file));
        return this;
    }
}
