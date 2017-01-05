package com.s.z.j.ui.videoplay;

import com.s.z.j.utils.L;

import java.io.File;
import java.util.ArrayList;


/**
 * Created by Administrator on 2016-12-21.
 */

public class FileUtil {

    public static ArrayList<Resource> GetVideoFileName(String fileAbsolutePath) {
        L.i("要获取数据的地方："+fileAbsolutePath);
        ArrayList<Resource> vecFile = new ArrayList<Resource>();
        File file = new File(fileAbsolutePath);
        File[] subFile = file.listFiles();
        if(subFile != null){
            L.i("该文件夹下的文件个数：" + subFile.length);
            for (int iFileLength = 0; iFileLength < subFile.length; iFileLength++) {
                // 判断是否为文件夹
                if (!subFile[iFileLength].isDirectory()) {
                    String filename = subFile[iFileLength].getName();

                    // 判断是否为MP4结尾
                    if (filename.trim().toLowerCase().endsWith(".jpg") || filename.trim().toLowerCase().endsWith(".png")) {
//                    L.i(""+filename+"是一张图片文件，添加到列表");
                        vecFile.add(new Resource(subFile[iFileLength].getPath(),"image",5));
                    } else if (filename.trim().toLowerCase().endsWith(".mp4") || filename.trim().toLowerCase().endsWith(".mov")|| filename.trim().toLowerCase().endsWith(".m4v")) {
                        L.i(""+filename+"是一个视频文件，添加到列表");
                        vecFile.add(new Resource( subFile[iFileLength].getPath(),"video", 30));
                    } else {

                    }
                }
            }
        }else {

        }

        L.i("一共获取的文件个数："+vecFile.size());
        return vecFile;
    }
}
