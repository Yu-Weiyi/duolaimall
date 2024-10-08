package pers.wayease.duolaimall.product.controller;

import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pers.wayease.duolaimall.common.result.Result;
import pers.wayease.duolaimall.product.service.FileService;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.controller
 * @name AdminFileUploadController
 * @description Admin file upload controller class.
 * @since 2024-10-08 15:31
 */
@RestController
@RequestMapping("/admin/product")
public class AdminFileUploadController {

    @Autowired
    private FileService fileService;

    @PostMapping("/fileUpload")
    public Result<String> fileUpload(MultipartFile file) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String url = fileService.uploadFile(file);
        return Result.ok(url);
    }
}
