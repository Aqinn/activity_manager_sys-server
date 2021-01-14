package com.aqinn.actmanagersysserver.utils;

import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.util.Date;

/**
 * @author Aqinn
 * @date 2019/10/26 10:56 上午
 */
public class FileUtils {

    public String saveFileWithRelativePath(MultipartFile mFile, String newFilePath) {
        OutputStream os = null;
        InputStream inputStream = null;
        String fileName = null;
        String returnPath = null;
        try {
            inputStream = mFile.getInputStream();
            fileName = mFile.getOriginalFilename();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String path = newFilePath;
            byte[] bs = new byte[1024];
            int len;
            File tempFile = new File(path);
            if (!tempFile.exists()) {
                tempFile.mkdirs();
            }
            returnPath = tempFile.getPath() + File.separator + new Date().getTime() + "_" + fileName;
            os = new FileOutputStream(returnPath);
            while ((len = inputStream.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                os.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return returnPath;
    }

    //MultipartFile转File
    public static File multipartFileToFile(MultipartFile mfile) throws IOException {
        File f = null;
        File newFile = null;
        if (mfile.equals("") || mfile.getSize() <= 0) {
            System.out.println("mFile为空！！！！！！！！");
            mfile = null;
        } else {
            InputStream ins = mfile.getInputStream();
//            f = new File(mfile.getOriginalFilename());
//            inputStreamToFile(ins, f);
            newFile = new File(PathUtil.tempFileFolder + PathUtil.tempFilePreName + String.valueOf(new Date().getTime()) + "_" + mfile.getOriginalFilename());
            File newFolder = new File(PathUtil.tempFileFolder);
            if (!newFolder.exists())
                newFolder.mkdirs();
            inputStreamToFile(ins, newFile);
        }
//        File del = new File(f.toURI());
//        del.delete();
        return newFile;
    }

    //InputStream转File
    private static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //File转String
    public static String fileConvertToString(File file) {
        byte[] data = null;
        try {
            File folder = new File(PathUtil.tempFileFolder);
            if (!folder.exists())
                folder.mkdirs();
//            System.out.println("fileConvertToString === 》 file。path " + file.getPath());
            if (!file.exists())
                file.createNewFile();
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len;
            byte[] buffer = new byte[1024];
            while ((len = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            data = baos.toByteArray();
            fis.close();
            baos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        BASE64Encoder encoder = new BASE64Encoder();
        String str = encoder.encode(data).trim();
        return str;
    }

    //String转File
    public static String stringConvertToFile(String oldFileString, String newFileName) {
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] imgByte = new byte[0];
        try {
            imgByte = decoder.decodeBuffer(oldFileString);
            File folder = new File(PathUtil.tempFileFolder);
            if (!folder.exists())
                folder.mkdirs();
            String filePath = PathUtil.tempFileFolder + PathUtil.tempFilePreName + String.valueOf(new Date().getTime()) + "_" + newFileName;
            File file = new File(filePath);
            if (!file.exists())
                file.createNewFile();
            System.out.println("取出图片的存储路径 => " + file.getPath());
            OutputStream os = new FileOutputStream(file);
            os.write(imgByte, 0, imgByte.length);
            os.flush();
            os.close();
            return filePath;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}