package pers.wayease.duolaimall.product.service.impl;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pers.wayease.duolaimall.product.configuration.MinioConfiguration;
import pers.wayease.duolaimall.product.service.FileService;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.service.impl
 * @name FileServiceImpl
 * @description File service implement class.
 * @since 2024-10-08 15:36
 */
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private MinioConfiguration minioConfiguration;

    @Autowired
    private MinioClient minioClient;

    private static final long PART_SIZE = -1;

    @Override
    public String uploadFile(MultipartFile file) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String objectName = minioConfiguration.getFilePrefix() + UUID.randomUUID().toString().replaceAll("-", "");
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(minioConfiguration.getBucketName())
                        .contentType(file.getContentType())
                        .stream(file.getInputStream(), file.getSize(), PART_SIZE)
                        .object(objectName)
                        .build()
        );
        String url = minioConfiguration.getEndpointUrl() + "/" + minioConfiguration.getBucketName() + "/" + objectName;
        return url;
    }
}
