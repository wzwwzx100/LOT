//package com.mogu.LOT.util;
//
//import com.aliyun.oss.OSSClient;
//import com.mogu.LOT.model.params.ExportResultDo;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.xssf.usermodel.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.*;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.text.DecimalFormat;
//import java.util.*;
//
//public class FileUtil {
//
//
//    public static final String separator = File.separator;
//
//    public static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
//
////    public static void writeFile(String filePath, String context) {
////        FileOutputStream outSTr = null;
////        BufferedOutputStream Buff = null;
////        try {
////            File downloadFile = new File(filePath);
////            downloadFile.getParentFile().mkdirs();
////            if (!downloadFile.exists()) {
////                downloadFile.createNewFile();
////            }
////            outSTr = new FileOutputStream(downloadFile);
////            Buff = new BufferedOutputStream(outSTr);
////            long begin0 = System.currentTimeMillis();
////            Buff.write(context.getBytes());
////            Buff.flush();
////            Buff.close();
////            long end0 = System.currentTimeMillis();
////            System.out.println("BufferedOutputStream执行耗时:" + (end0 - begin0) + " 豪秒");
////        } catch (Exception e) {
////            e.printStackTrace();
////        } finally {
////            try {
////                Buff.close();
////                outSTr.close();
////            } catch (Exception e) {
////                e.printStackTrace();
////            }
////        }
////    }
//
//
////    @SuppressWarnings("resource")
////    public static String readFile(String filePath) {
////        try {
////            InputStreamReader read = new InputStreamReader(new FileInputStream(new File(filePath)), "UTF-8");//考虑到编码格式
////            BufferedReader bufferedReader = new BufferedReader(read);
////            String lineTxt = null;
////            StringBuffer sb = new StringBuffer("");
////            while ((lineTxt = bufferedReader.readLine()) != null) {
////                sb.append(lineTxt);
////            }
////            return sb.toString();
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////        return "";
////    }
//
////    /**
////     * @param inputStream
////     * @param rootDir
////     * @param originalFilename
////     * @param overWriter
////     * @return
////     */
////    public static String saveFile(FileInputStream inputStream, String rootDir, String originalFilename, boolean overWriter) {
////        //拿到输出流，同时重命名上传的文件
////        if (StringUtils.isBlank(originalFilename) || inputStream == null) {
////            throw new NullPointerException("save file and file name is not blank...");
////        }
////        String _prefix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
////        String _fileName = overWriter ? StringUtils.join(UUID.randomUUID().toString().replaceAll("-", ""), ".", _prefix) : originalFilename;
////        String _relativePath = StringUtils.join(FileUtil.separator, DateUtil.formatSimple(new Date(), DateUtil.FORMAT_YYYYMMDD), _fileName);
////        String _savePath = StringUtils.join(rootDir, _relativePath);
////        try {
////            File writeFile = new File(_savePath);
////            if (!writeFile.getParentFile().exists()) {
////                writeFile.mkdirs();
////            }
////            FileOutputStream os = new FileOutputStream(writeFile);
////            //拿到上传文件的输入流
////            //以写字节的方式写文件
////            int b = 0;
////            while ((b = inputStream.read()) != -1) {
////                os.write(b);
////            }
////            os.flush();
////            os.close();
////            inputStream.close();
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////        return _relativePath;
////    }
////
////    /**
////     * @param multipartFile
////     * @param rootDir
////     * @param originalFilename
////     * @param overWriter
////     * @return
////     */
////    public static String saveFile(MultipartFile multipartFile, String originalFilename, String rootDir, String parentDir, boolean overWriter) {
////        //拿到输出流，同时重命名上传的文件
////        if (StringUtils.isBlank(originalFilename) || multipartFile == null) {
////            throw new NullPointerException("save file and file name is not blank...");
////        }
////        String _prefix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
////        String _fileName = overWriter ? StringUtils.join(UUID.randomUUID().toString().replaceAll("-", ""), ".", _prefix) : originalFilename;
//////        String originalFileName = multipartFile.getOriginalFilename();
//////        String _fileName = originalFilename ;
////        String _relativePath = StringUtils.join(FileUtil.separator,DateUtil.formatSimple(new Date(), DateUtil.FORMAT_YYYYMMDD),FileUtil.separator, _fileName);
////        if (StringUtils.isNotBlank(parentDir)) {
////            _relativePath = StringUtils.join(FileUtil.separator, parentDir, _relativePath);
////        }
////        String _savePath = StringUtils.join(rootDir, _relativePath);
////        try {
////            File writeFile = new File(_savePath);
////            if (!writeFile.getParentFile().exists()) {
////                writeFile.mkdirs();
////            }
////            multipartFile.transferTo(writeFile);
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////        return _relativePath;
////    }
//
//
//    public static final String bucketName = "moguceshi";
//    public static final String access_key_id = "LTAIRa9FNX7qS3dQ";
//    public static final String access_key_secret = "UX1BdSQIcuYtkNxvEcV6MQ5SRS5kH7";
//    public static final String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
//
//    public static void saveFileToaliyun(MultipartFile multipartFile,String dir){
//
//        OSSClient ossClient = new OSSClient(endpoint, access_key_id, access_key_secret);
//        if(ossClient == null){
//            logger.error("OSS服务初始化失败！文件上传失败！");
//        }
//
//        InputStream inputStream = null;
//        try{
//            inputStream = multipartFile.getInputStream();
//            ossClient.putObject(bucketName, dir, inputStream);
//            ossClient.shutdown();
//        }catch (Exception e){
//            logger.error(e.getMessage(),e);
//        }finally {
//            if(inputStream != null){
//                try {
//                    inputStream.close();
//                }catch (Exception e1){
//                    logger.error(e1.getMessage(),e1);
//                }
//            }
//        }
//    }
//
//    public static void saveFileToaliyun(File file,String dir){
//
//        OSSClient ossClient = new OSSClient(endpoint, access_key_id, access_key_secret);
//        if(ossClient == null){
//            logger.error("OSS服务初始化失败！文件上传失败！");
//        }
//        try{
//            ossClient.putObject(bucketName, dir.substring(1), file);
//            ossClient.shutdown();
//        }catch (Exception e){
//            logger.error(e.getMessage(),e);
//        }
//    }
//
//    public static void saveFileToaliyun(InputStream inputStream,String originalFilename,String dir){
//        OSSClient ossClient = new OSSClient(endpoint, access_key_id, access_key_secret);
//        if(ossClient == null){
//            logger.error("OSS服务初始化失败！文件上传失败！");
//        }
//        dir = dir.substring(1);
//        String file_name = dir+originalFilename;
//        ossClient.putObject(bucketName, file_name, inputStream);
//        ossClient.shutdown();
//        try {
//            inputStream.close();
//        }catch (Exception e){
//            logger.error(e.getMessage(),e);
//        }
//    }
//
//
//    public static String saveFile(InputStream inputStream,String originalFilename,String rootDir){
//        if (StringUtils.isBlank(originalFilename) || inputStream == null) {
//            throw new NullPointerException("save file and file name is not blank...");
//        }
//
//        String file = originalFilename+".png";
//        String _relativePath = StringUtils.join(FileUtil.separator,DateUtil.formatSimple(new Date(), DateUtil.FORMAT_YYYYMMDD),FileUtil.separator, file);
//        try {
//            File writeFile = new File(rootDir+ File.separator +_relativePath);
//            if (!writeFile.getParentFile().exists()) {
//                writeFile.getParentFile().mkdirs();
//            }
//            FileOutputStream os = new FileOutputStream(writeFile);
//            //拿到上传文件的输入流
//            //以写字节的方式写文件
//            int b = 0;
//            while ((b = inputStream.read()) != -1) {
//                os.write(b);
//            }
//            os.flush();
//            os.close();
//            inputStream.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return _relativePath;
//    }
//
////    //图片转化成base64字符串
////    public static String GetImageStr(InputStream in) {//将图片文件转化为字节数组字符串，并对其进行Base64编码处理
////        byte[] data = null;
////        //读取图片字节数组
////        try
////        {
////            data = new byte[in.available()];
////            in.read(data);
////            in.close();
////        }
////        catch (IOException e)
////        {
////            e.printStackTrace();
////        }
////        //对字节数组Base64编码
////        BASE64Encoder encoder = new BASE64Encoder();
////        return encoder.encode(data);//返回Base64编码过的字节数组字符串
////    }
//
//    public static List<ArrayList<String>> readXls(MultipartFile file){
//        List<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
//        // IO流读取文件
//        InputStream input = null;
//        XSSFWorkbook wb = null;
//        ArrayList<String> rowList = null;
//        try {
//            input = file.getInputStream();
//            // 创建文档
//            wb = new XSSFWorkbook(input);
//            //读取sheet(页)
//            for(int numSheet=0;numSheet<wb.getNumberOfSheets();numSheet++){
//                XSSFSheet xssfSheet = wb.getSheetAt(numSheet);
//                if(xssfSheet == null){
//                    continue;
//                }
//                int totalRows = xssfSheet.getLastRowNum();
//                //读取Row,从第二行开始
//                for(int rowNum = 1;rowNum <= totalRows;rowNum++){
//                    XSSFRow xssfRow = xssfSheet.getRow(rowNum);
//                    if(xssfRow!=null){
//                        rowList = new ArrayList<String>();
//                        int totalCells = xssfRow.getLastCellNum();
//                        //读取列，从第一列开始
//                        for(int c=0;c<=totalCells+1;c++){
//                            XSSFCell cell = xssfRow.getCell(c);
//                            if(cell==null){
//                                rowList.add("");
//                                continue;
//                            }
//                            rowList.add(getXValue(cell).trim());
//                        }
//                        list.add(rowList);
//                    }
//                }
//            }
//            return list;
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally{
//            try {
//                input.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
//
//    /**
//     * 单元格格式
//     * @param xssfCell
//     * @return
//     */
//    public static String getXValue(XSSFCell xssfCell){
//        if (xssfCell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
//            return String.valueOf(xssfCell.getBooleanCellValue());
//        } else if (xssfCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
//            String cellValue = "";
//            DecimalFormat df = new DecimalFormat("#.##");
//            cellValue = df.format(xssfCell.getNumericCellValue());
//            String strArr = cellValue.substring(cellValue.lastIndexOf(".")+1,cellValue.length());
//            if(strArr.equals("00")){
//                cellValue = cellValue.substring(0, cellValue.lastIndexOf("."));
//            }
//            return cellValue;
//        } else {
//            return String.valueOf(xssfCell.getStringCellValue());
//        }
//    }
//
//
//    public static void exportExcel(String title, String[] headers,
//                                   List dataset, OutputStream out){
//        XSSFWorkbook workbook = new XSSFWorkbook();
//        XSSFSheet sheet = workbook.createSheet(title);
//        XSSFRow row = sheet.createRow(0);
//        //head
//        for (short i = 0; i < headers.length; i++)
//        {
//            XSSFCell cell = row.createCell(i);
//            XSSFRichTextString text = new XSSFRichTextString(headers[i]);
//            cell.setCellValue(text);
//        }
//        // 遍历集合数据，产生数据行
//        Iterator it = dataset.iterator();
//        int index = 0;
//        while (it.hasNext())
//        {
//            index++;
//            row = sheet.createRow(index);
//            Object obj = it.next();
//            //tel
//            XSSFCell tel = row.createCell(0);
//            tel.setCellValue(((ExportResultDo)obj).getTel());
//            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
//            for (short i = 1; i < 105; i++)
//            {
//                XSSFCell cell = row.createCell(i);
//                String getMethodName = "getM"+(i-1);
//                try
//                {
//                    Class tCls = obj.getClass();
//                    Method getMethod = tCls.getMethod(getMethodName,
//                            new Class[]
//                                    {});
//                    Object value = getMethod.invoke(obj);
//                    // 判断值的类型后进行强制类型转换
//                    String textValue = value.toString();
//                    cell.setCellValue(textValue);
//                }
//                catch (SecurityException e)
//                {
//                    e.printStackTrace();
//                }
//                catch (NoSuchMethodException e)
//                {
//                    e.printStackTrace();
//                }
//                catch (IllegalArgumentException e)
//                {
//                    e.printStackTrace();
//                }
//                catch (IllegalAccessException e)
//                {
//                    e.printStackTrace();
//                }
//                catch (InvocationTargetException e)
//                {
//                    e.printStackTrace();
//                } finally
//                {
//                    // 清理资源
//                }
//            }
//            //pbi
//            XSSFCell sex = row.createCell(105);
//            sex.setCellValue(((ExportResultDo)obj).getSex());
//            XSSFCell age = row.createCell(106);
//            age.setCellValue(((ExportResultDo)obj).getAge());
//            XSSFCell department = row.createCell(107);
//            department.setCellValue(((ExportResultDo)obj).getDepartment());
//            XSSFCell gangwei = row.createCell(108);
//            gangwei.setCellValue(((ExportResultDo)obj).getGangwei());
//            XSSFCell zhiwei = row.createCell(109);
//            zhiwei.setCellValue(((ExportResultDo)obj).getZhiwei());
//            XSSFCell edu = row.createCell(110);
//            edu.setCellValue(((ExportResultDo)obj).getEdu());
//            XSSFCell titl = row.createCell(111);
//            titl.setCellValue(((ExportResultDo)obj).getTitle());
//            XSSFCell political = row.createCell(112);
//            political.setCellValue(((ExportResultDo)obj).getPolitical());
//            XSSFCell origin = row.createCell(113);
//            origin.setCellValue(((ExportResultDo)obj).getOrigin());
//            XSSFCell seniority = row.createCell(114);
//            seniority.setCellValue(((ExportResultDo)obj).getSeniority());
//            XSSFCell level = row.createCell(115);
//            level.setCellValue(((ExportResultDo)obj).getLevel());
//            XSSFCell salary = row.createCell(116);
//            salary.setCellValue(((ExportResultDo)obj).getSalary());
//            XSSFCell health = row.createCell(117);
//            health.setCellValue(((ExportResultDo)obj).getHealth());
//            XSSFCell blood = row.createCell(118);
//            blood.setCellValue(((ExportResultDo)obj).getBlood());
//            XSSFCell home = row.createCell(119);
//            home.setCellValue(((ExportResultDo)obj).getHomeplace());
//
//        }
//        try
//        {
//            workbook.write(out);
//        }
//        catch (IOException e)
//        {
//            e.printStackTrace();
//        }
//    }
//}
