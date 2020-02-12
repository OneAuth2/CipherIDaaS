package cipher.console.oidc.util;

import cipher.console.oidc.enums.UserImportEnum;
import cipher.console.oidc.service.UUIDService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

public class ImportExcelUtil {
    private static Workbook wb = null;

    private static Sheet sheet;
    private static Row row;

    private UUIDService uuidService=SpringContextUtil.getBean(UUIDService.class);


    public static BufferedReader multipartFileToBufferedReader(MultipartFile multipartFile, String charsetName) {
        if (multipartFile == null || multipartFile.isEmpty()) {
            return null;
        }
        try {
            InputStream inputStream = multipartFile.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, charsetName);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            return bufferedReader;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BufferedReader multipartFileToBufferedReader(MultipartFile multipartFile) {
        return multipartFileToBufferedReader(multipartFile, "UTF-8");
    }


    public ImportExcelUtil(MultipartFile file) throws IOException {
        if (file == null) {
            return;
        }
        String filename = file.getOriginalFilename();
        if (StringUtils.isEmpty(filename)) {
            return;
        }
        String ext = filename.substring(filename.lastIndexOf("."));//获取文件名后缀
        try {
            InputStream inputStream = file.getInputStream();
            if (".xls".equals(ext)) {//2003- 版本的excel
                wb = new HSSFWorkbook(inputStream);
            } else if (".xlsx".equals(ext)) {//2007+ 版本的excel
                wb = new XSSFWorkbook(inputStream);
            } else {
                wb = null;
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            wb.close();
        }
    }

  /*  public ImportExcelUtil(String filepath) {
        if(filepath==null){
            return;
        }
        String ext = filepath.substring(filepath.lastIndexOf("."));//获取文件名后缀
        try {
            InputStream is = new FileInputStream(filepath);
            if(".xls".equals(ext)){//2003- 版本的excel
                wb = new HSSFWorkbook(is);
            }else if(".xlsx".equals(ext)){//2007+ 版本的excel
                wb = new XSSFWorkbook(is);
            }else{
                wb=null;
            }
        } catch ( FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }*/

    /**
     * 读取excel表头的内容
     *
     * @return
     * @throws Exception
     */
    public String[] readExcelTitle() throws Exception {
        if (wb == null) {
            throw new Exception("Workbook对象为空！");
        }
        sheet = wb.getSheetAt(0);
        row = sheet.getRow(0);

        // 标题总列数
        int colNum = row.getPhysicalNumberOfCells();
        System.out.println("colNum:" + colNum);
        String[] title = new String[colNum];
        for (int i = 0; i < colNum; i++) {
            // title[i] = getStringCellValue(row.getCell((short) i));
            row.getCell(i).setCellType(Cell.CELL_TYPE_STRING);
            title[i] = row.getCell(i).getStringCellValue();
        }
        return title;
    }

    /**
     * 读取excel内容
     *
     * @return
     * @throws Exception
     */
    public Map<Integer, Map<String, Object>> readExcelContent() throws Exception {
        Map<Integer, Map<String, Object>> content = new HashMap<Integer, Map<String, Object>>();
        sheet = wb.getSheetAt(0);
        // 得到总行数
        int rowNum = sheet.getLastRowNum();
        row = sheet.getRow(0);
        int colNum = row.getPhysicalNumberOfCells();
        // 正文内容应该从第二行开始,第一行为表头的标题
        for (int i = 1; i <= rowNum; i++) {
            row = sheet.getRow(i);
            int j = 0;
            Map<String, Object> cellValue = new HashMap<String, Object>();
            while (j < colNum) {
                Object obj = getCellFormatValue(row.getCell(j));
                Object objStr = getCellFormatValue(sheet.getRow(1).getCell(j));
                //获取第一列的标题
                cellValue.put((String) objStr, obj);
                j++;
            }
            content.put(i, cellValue);
        }
        return content;

    }


    public Map<Integer, Map<String, String>> readUserExcelContent(String companyId) throws Exception {
        Map<Integer, Map<String, String>> usercontent = new HashMap<Integer, Map<String, String>>();
        sheet = wb.getSheetAt(0);
        // 得到总行数
        int rowNum = sheet.getLastRowNum();
        row = sheet.getRow(0);
        int colNum = row.getPhysicalNumberOfCells();
        // 正文内容应该从第二行开始,第一行为表头的标题
        for (int i = 2; i <= rowNum; i++) {
            row = sheet.getRow(i);
            int j = 0;
            Map<String, String> cellValue = new HashMap<String, String>();
            while (j <= 7) {
                Cell cell = row.getCell(j);
                Object obj = getCellFormatValue(cell);
                Object objStr = getCellFormatValue(sheet.getRow(1).getCell(j));
                String desc = UserImportEnum.getPoliceEnum(String.valueOf(objStr));
                //获取第一列的标题
                cellValue.put(desc, String.valueOf(obj));
                j++;
            }
            String uuid = uuidService.getUUid();
            //添加公司id和用户uuid
            cellValue.put("uuid",uuid);
            cellValue.put("companyId", companyId);
            //net.sf.json.JSONObject 将Map转换为JSON方法
            JSONObject json = JSONObject.fromObject(cellValue);
            usercontent.put(i, json);
        }
        return usercontent;
    }


    /**
     * 将对象转换成json字符串。
     *
     * @param data
     * @return
     */

    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();


    public static String objectToJson(Object data) {
        try {
            String string = MAPPER.writeValueAsString(data);
            return string;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Map<Integer, Map<String, Object>> readGroupExcelContent() throws Exception {
        Map<Integer, Map<String, Object>> groupcontent = new HashMap<Integer, Map<String, Object>>();
        sheet = wb.getSheetAt(0);
        // 得到总行数
        int rowNum = sheet.getLastRowNum();
        row = sheet.getRow(0);
        int colNum = row.getPhysicalNumberOfCells();
        // 正文内容应该从第二行开始,第一行为表头的标题
        for (int i = 2; i <= rowNum; i++) {
            row = sheet.getRow(i);
            int j = 0;
            Map<String, Object> cellValue = new HashMap<String, Object>();
            while (j < colNum) {
                j++;
                if (j == 1) {
                    Object obj = getCellFormatValue(row.getCell(j));
                    Object objStr = getCellFormatValue(sheet.getRow(1).getCell(j));
                    if ("" != objStr) {
                        //获取第一列的标题
                        cellValue.put((String) objStr, obj);
                    }
                }
                if (j > 7) {
                    Object obj = getCellFormatValue(row.getCell(j));
                    Object objStr = getCellFormatValue(sheet.getRow(1).getCell(j));
                    if ("" != objStr) {
                        //获取第一列的标题
                        cellValue.put((String) objStr, obj);
                    }
                }
            }
            groupcontent.put(i, sortMapByKey(cellValue));
        }
        return groupcontent;
    }


    public static Map<String, Object> sortMapByKey(Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        //利用匿名内部类，重写compare to 方法

        Map<String, Object> sortMap = new TreeMap<String, Object>(new MapKeyComparator());

        sortMap.putAll(map);

        return sortMap;
    }


    public static class MapKeyComparator implements Comparator<String> {


        @Override
        public int compare(String str1, String str2) {
            return str1.compareTo(str2);
        }
    }


    public static <T> T convertMap2Bean(Map map, Class T) throws Exception {
        if (map == null || map.size() == 0) {
            return null;
        }
        BeanInfo beanInfo = Introspector.getBeanInfo(T);
        T bean = (T) T.newInstance();
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (int i = 0, n = propertyDescriptors.length; i < n; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            String upperPropertyName = propertyName.toUpperCase();
            if (map.containsKey(upperPropertyName)) {
                Object value = map.get(upperPropertyName);
                //这个方法不会报参数类型不匹配的错误。
                BeanUtils.copyProperty(bean, propertyName, value);
            }
        }
        return bean;
    }

    /**
     * 将 List<Map>对象转化为List<JavaBean> 此方法已通过测试
     *
     * @param
     * @param
     * @return Object对象
     * @author wyply115
     * @version 2016年3月20日 11:03:01
     */
    public static <T> List<T> convertListMap2ListBean(List<Map<String, Object>> listMap, Class T) throws Exception {
        List<T> beanList = new ArrayList<T>();
        for (int i = 0, n = listMap.size(); i < n; i++) {
            Map<String, Object> map = listMap.get(i);
            T bean = convertMap2Bean(map, T);
            beanList.add(bean);
        }
        return beanList;
    }


    /**
     * 根据cell类型设置数据参数
     *
     * @param cell
     * @return
     */
    private static Object getCellFormatValue(Cell cell) {
        Object cellvalue = "";
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC:// 如果当前Cell的Type为NUMERIC
                    DecimalFormat df = new DecimalFormat("0");
                    cellvalue = df.format(cell.getNumericCellValue());
                    break;
                case Cell.CELL_TYPE_FORMULA: {
                    // 判断当前的cell是否为Date
                    if (DateUtil.isCellDateFormatted(cell)) {
                        // 如果是Date类型则，转化为Data格式
                        // data格式是带时分秒的：2013-7-10 0:00:00
                        // cellvalue = cell.getDateCellValue().toLocaleString();
                        // data格式是不带带时分秒的：2013-7-10
                        Date date = cell.getDateCellValue();
                        cellvalue = date;
                    } else {// 如果是纯数字
                        // 取得当前Cell的数值
                        cellvalue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                }
                case Cell.CELL_TYPE_STRING:// 如果当前Cell的Type为STRING
                    // 取得当前的Cell字符串
                    cellvalue = cell.getRichStringCellValue().getString();
                    break;
                default:// 默认的Cell值
                    cellvalue = "";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;

    }


    public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
        try {
            T t = MAPPER.readValue(jsonData, beanType);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }




   /* public static void main(String[] args) {
        String filepath = "D:\\data\\数据样例1.xlsx";
        ImportExcelUtil eu=new ImportExcelUtil(filepath);
        String[] title;
        try {
        *//*    Map<Integer, Map<String,Object>> map = eu.readExcelContent();
            System.out.println("获得Excel表格的内容:");
            for (int i = 0; i <= map.size(); i++) {
                System.out.println(map.get(i));
            }
*//*
           Map<Integer, Map<String,String>> usermap = eu.readUserExcelContent();
            System.out.println("获得人员表格的内容:");
            for (Map.Entry<Integer, Map<String,String>> entry : usermap.entrySet()) {
               // System.out.println(entry.getKey() + ": " + entry.getValue());
                NewUserInfoExcle newUserInfoExcle= jsonToPojo(entry.getValue().toString(), NewUserInfoExcle.class);
                System.out.println(newUserInfoExcle.toString());
               // JSONObject jsonobject = JSONObject.fromObject(String.valueOf(entry.getValue()));
               // NewUserInfoExcle newUserInfoExcle= (NewUserInfoExcle)JSONObject.toBean(jsonobject,NewUserInfoExcle.class);
               // System.out.println(newUserInfoExcle.toString());
            }

           *//* Map<Integer, Map<String,Object>> groupmap = eu.readGroupExcelContent();
            System.out.println("获得部门表格的内容:");
            for (Map.Entry<Integer, Map<String,Object>> entry : groupmap.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());

                for (Map.Entry<String, Object> newentry : entry.getValue().entrySet()) {
                    System.out.println(newentry.getKey() + ": " + newentry.getValue());

                }
            }*//*
        } catch (FileNotFoundException e) {
            System.out.println("未找到指定路径的文件!");
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }



    }
*/


}
