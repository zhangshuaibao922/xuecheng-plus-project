package com.xuecheng.media;

import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import io.minio.*;
import io.minio.errors.*;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author cardo
 * @Version 1.0
 * @Description 测试minio的sdk
 * @date 2023/5/23 15:44
 */

public class MinioTest {
    MinioClient minioClient =
            MinioClient.builder()
                    .endpoint("http://192.168.101.65:9000")
                    .credentials("minioadmin", "minioadmin")
                    .build();
    @Test
    public void test1() throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        //通过扩展名得到媒体资源的类型mimeType
        ContentInfo extensionMatch = ContentInfoUtil.findExtensionMatch(".png");
        String mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        if(extensionMatch!=null){
            mimeType=extensionMatch.getMimeType();
        }

        minioClient.uploadObject(
                UploadObjectArgs.builder()
                        .bucket("testbucket")
                        .filename("C:\\Users\\shuaibaozhang\\OneDrive\\桌面\\Snipaste_2023-03-23_11-18-51.png")//本地文件路径
                        .object("test/1.png")//存储到对象
                        .contentType(mimeType)//设置媒体文件类型
                        .build());
    }
    @Test
    public void test2() throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder()
                .bucket("testbucket")
                .object("1.png")
                .build();
        minioClient.removeObject(removeObjectArgs);
    }
    @Test
    public void test3() throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        GetObjectArgs getObjectArgs=GetObjectArgs.builder()
                .bucket("testbucket")
                .object("test/1.png")
                .build();
        GetObjectResponse object = minioClient.getObject(getObjectArgs);
        FileOutputStream fileOutputStream = new FileOutputStream(new File("D:\\upload\\1.png"));
        IOUtils.copy(object,fileOutputStream);
    }
}

