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

        File directory = new File("");// 参数为空
        //获取项目根路径
        String Path = directory.getCanonicalPath();

        //遍历文件
        if (multipartfiles != null && multipartfiles.length > 0) {
            int i=0;
            for (MultipartFile item : multipartfiles) {
                int begin = item.getOriginalFilename().lastIndexOf(".");
                String type = item.getOriginalFilename().substring(begin);//获取文件类型
                String fileName = PathGenerator.generateItemImagePath()  + type;
                String filePath = Path + "/uploadFiles/"+itemOrHead;//自定义上传路径
                File file = new File(filePath, fileName);
                if (!file.exists()) {//判断文件夹是否存在，如果不存在则创建
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                    file.createNewFile();
                }
                item.transferTo(file);//上传文件

                fileNames.add(fileName);
                //imageService.insertImage(fileName);
            }
        }
        return new Result(Status.UPLOAD_SUCCESSFUL,"图片上传成功",fileNames);
    }
}
