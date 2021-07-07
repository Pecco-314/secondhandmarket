package com.zerone.secondhandmarket.module;

import com.zerone.secondhandmarket.enums.Status;
import com.zerone.secondhandmarket.tools.PathGenerator;
import com.zerone.secondhandmarket.viewobject.Result;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UploadModule {
    public static Result upload(String itemOrHead, MultipartFile[] multipartfiles) throws IOException {
        List<String> fileNames=new ArrayList<>();
        // 参数为空
        File directory = new File("");
        //获取项目根路径
        String Path = directory.getCanonicalPath();

        //遍历文件
        if (multipartfiles != null && multipartfiles.length > 0) {
            int i=0;
            for (MultipartFile item : multipartfiles) {
                int begin = item.getOriginalFilename().lastIndexOf(".");
                //获取文件类型
                String type = item.getOriginalFilename().substring(begin);
                String fileName = PathGenerator.generateItemImagePath()  + type;
                //自定义上传路径
                String filePath = Path + "/uploadFiles/"+itemOrHead;
                File file = new File(filePath, fileName);
                //判断文件夹是否存在，如果不存在则创建
                if (!file.exists()) {
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                    file.createNewFile();
                }
                //上传文件
                item.transferTo(file);

                fileNames.add(fileName);
            }
        }
        return new Result(Status.UPLOAD_SUCCESSFUL,"图片上传成功",fileNames);
    }
}
