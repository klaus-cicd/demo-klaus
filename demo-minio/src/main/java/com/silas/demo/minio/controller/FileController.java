package com.silas.demo.minio.controller;

import com.fd.web.response.Result;
import com.silas.demo.minio.config.MinioConfig;
import com.silas.demo.minio.utils.MinioUtil;
import io.minio.messages.Bucket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "file")
public class FileController {


    @Autowired
    private MinioUtil minioUtil;
    @Autowired
    private MinioConfig prop;

    /**
     * 查看存储bucket是否存在
     *
     * @param bucketName
     * @return
     */
    @GetMapping("/bucketExists")
    public Result<Boolean> bucketExists(@RequestParam("bucketName") String bucketName) {
        return Result.ok(minioUtil.bucketExists(bucketName));
    }

    @GetMapping("/makeBucket")
    public Result makeBucket(String bucketName) {
        return Result.ok(minioUtil.makeBucket(bucketName));
    }

    /**
     * 删除存储bucket
     *
     * @param bucketName
     * @return
     */
    @GetMapping("/removeBucket")
    public Result removeBucket(String bucketName) {
        return Result.ok(minioUtil.removeBucket(bucketName));
    }

    /**
     * 获取全部bucket
     *
     * @return
     */
    @GetMapping("/getAllBuckets")
    public Result getAllBuckets() {
        List<Bucket> allBuckets = minioUtil.getAllBuckets();
        return Result.ok(allBuckets);
    }

    /**
     * 文件上传返回url
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public Result upload(@RequestParam("file") MultipartFile file) {
        String objectName = minioUtil.upload(file);
        if (null != objectName) {
            return Result.ok(prop.getEndpoint() + "/" + prop.getBucketName() + "/" + objectName);
        }
        return Result.fail();
    }

    /**
     * 图片/视频预览
     *
     * @param fileName
     * @return
     */
    @GetMapping("/preview")
    public Result preview(@RequestParam("fileName") String fileName) {
        return Result.ok(minioUtil.preview(fileName));
    }

    /**
     * 文件下载
     *
     * @param fileName
     * @param res
     * @return
     */
    @GetMapping("/download")
    public Result<Void> download(@RequestParam("fileName") String fileName, HttpServletResponse res) {
        minioUtil.download(fileName, res);
        return Result.ok();
    }

    /**
     * "删除文件", notes = "根据url地址删除文件"
     *
     * @param url
     * @return
     */
    @PostMapping("/delete")
    public Result remove(String url) {
        String objName = url.substring(url.lastIndexOf(prop.getBucketName() + "/") + prop.getBucketName().length() + 1);
        minioUtil.remove(objName);
        return Result.ok(objName);
    }

}
