/*
package com.bootdo.testDemo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController()
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestDemo {


    @Autowired
    UsersService u;
    @Autowired
    PayPolicyService p;
    JTextArea jta = null;
    JMenuBar jmb = null;
    JMenu jm = null;
    JMenuItem jmi1 = null;
    JMenuItem jmi2 = null;

    @Test
    public void tst(){

        JFileChooser fileChooser =new JFileChooser();
        fileChooser.showOpenDialog(null);

        File fl = fileChooser.getSelectedFile();
        byte b[] =new byte[(int)fl.length()];
        try {
            FileInputStream fileinput =new FileInputStream(fl);
            fileinput.read(b);
            String string=new String(b);

            System.out.println(fl.getAbsolutePath());
            System.out.print(string);
            fileChooser.showSaveDialog(null);
            File fout =fileChooser.getSelectedFile();
            FileOutputStream fileout =new FileOutputStream(fout);
            fileout.write(b);
            fileout.close();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
    public static boolean isIDNumber(String IDNumber) {
        if (IDNumber == null || "".equals(IDNumber)) {
            return false;
        }
        // 定义判别用户身份证号的正则表达式（15位或者18位，最后一位可以为字母）
        String regularExpression = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" +
                "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
        //假设18位身份证号码:41000119910101123X  410001 19910101 123X
        //^开头
        //[1-9] 第一位1-9中的一个      4
        //\\d{5} 五位数字           10001（前六位省市县地区）
        //(18|19|20)                19（现阶段可能取值范围18xx-20xx年）
        //\\d{2}                    91（年份）
        //((0[1-9])|(10|11|12))     01（月份）
        //(([0-2][1-9])|10|20|30|31)01（日期）
        //\\d{3} 三位数字            123（第十七位奇数代表男，偶数代表女）
        //[0-9Xx] 0123456789Xx其中的一个 X（第十八位为校验值）
        //$结尾

        //假设15位身份证号码:410001910101123  410001 910101 123
        //^开头
        //[1-9] 第一位1-9中的一个      4
        //\\d{5} 五位数字           10001（前六位省市县地区）
        //\\d{2}                    91（年份）
        //((0[1-9])|(10|11|12))     01（月份）
        //(([0-2][1-9])|10|20|30|31)01（日期）
        //\\d{3} 三位数字            123（第十五位奇数代表男，偶数代表女），15位身份证不含X
        //$结尾


        boolean matches = IDNumber.matches(regularExpression);

        //判断第18位校验值
        if (matches) {

            if (IDNumber.length() == 18) {
                try {
                    char[] charArray = IDNumber.toCharArray();
                    //前十七位加权因子
                    int[] idCardWi = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
                    //这是除以11后，可能产生的11位余数对应的验证码
                    String[] idCardY = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
                    int sum = 0;
                    for (int i = 0; i < idCardWi.length; i++) {
                        int current = Integer.parseInt(String.valueOf(charArray[i]));
                        int count = current * idCardWi[i];
                        sum += count;
                    }
                    char idCardLast = charArray[17];
                    int idCardMod = sum % 11;
                    if (idCardY[idCardMod].toUpperCase().equals(String.valueOf(idCardLast).toUpperCase())) {
                        return true;
                    } else {

                        return false;
                    }

                } catch (Exception e) {
                    e.printStackTrace();

                    return false;
                }
            }

        }
        return matches;
    }

    @Test
    public void test33()  {

            String a="/file/ad/bb.txt";
            createFile("C:\\Users\\admin\\Desktop\\wltxcsgj"+a);


    }



    public static boolean createFile(String destFileName) {
        File file = new File(destFileName);
        if (file.exists()) {
            System.out.println("创建单个文件" + destFileName + "失败，目标文件已存在！");
            return false;
        }
        if (destFileName.endsWith(File.separator)) {
            System.out.println("创建单个文件" + destFileName + "失败，目标文件不能为目录！");
            return false;
        }
        //判断目标文件所在的目录是否存在
        if (!file.getParentFile().exists()) {
            //如果目标文件所在的目录不存在，则创建父目录
            System.out.println("目标文件所在目录不存在，准备创建它！");
            if (!file.getParentFile().mkdirs()) {
                System.out.println("创建目标文件所在目录失败！");
                return false;
            }
        }
        //创建目标文件
        try {
            if (file.createNewFile()) {
                System.out.println("创建单个文件" + destFileName + "成功！");
                return true;
            } else {
                System.out.println("创建单个文件" + destFileName + "失败！");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("创建单个文件" + destFileName + "失败！" + e.getMessage());
            return false;
        }

    }


    
    @Test
    public void test(){

        Map<String, Object> map = new HashMap<>();


        String str = ProvinceUtil.sendGets();

        JSONObject json = JSON.parseObject(str);
        String obj = json.get("result").toString();

        List<JSONArray> list = JSON.parseArray(obj, JSONArray.class);
        String fullname;
        StringBuffer st = new StringBuffer();
        st.append("\"area\":");
        st.append("[");

        for (int i = 0; i < list.size(); i++) {
            JSONArray jsonarray = list.get(i);

            for (int j = 0; j < jsonarray.size(); j++) {
                if (j == 1) {
                    System.out.println(jsonarray.size());
                }

                String oj = jsonarray.get(j).toString();
                JSONObject jn = JSON.parseObject(oj);

                String id = jn.get("id").toString();
                fullname = jn.get("fullname").toString();
                //找出省
                int a= Integer.parseInt(id);
                if (a%10000==0){

                    System.out.println("\""+id+"\""+":"+"\""+fullname+"\""+"----------------省");

                }
                if(a%10000!=0&&a%100==0){
                    //市

                    System.out.println("\""+id+"\""+":"+"\""+fullname+"\""+"----------------市----所属省id"+a/10000*10000);
                }
                if(a%100!=0){
                    //区

                    System.out.println("\""+id+"\""+":"+"\""+fullname+"\""+"----------------区----所属市id"+a/100*100);
                }

                st.append(id+"\"" + fullname + "\"" + ",");

            }

        }
        st.append("]");

        //  System.out.println(st);



    }

    */
/*@Test
    public void t()  {
        System.out.println(usersDao.countNum(15));
    }}*//*



  */
/*  @Test
    public void test2() throws Exception {
 Map<String, Object> map = new HashMap<>();
        List<CarDO> list = carDao.list(map);
        for (CarDO carDO : list) {
            System.out.println(carDO.getContact());
        }

        UsersDTO uc = new UsersDTO();
        uc.setMobile("18989898989");
        uc.setPassword("123");
        uc.setType(2);
        uc.setContact("王爱国");
        uc.setProvince("山东省");
        uc.setCity("青岛市");
        uc.setDistrict("市南区");
        uc.setAddress("真心家园99号301");
        u.saveUserDTO(uc);
    }
*//*


  */
/*  public StringBuffer test3()  {
        //省
        StringBuffer sb=new StringBuffer();
        sb.append("{");
        List<MapDTO> pmap = p.get();
        for (MapDTO mapDTO : pmap) {

            String a=mapDTO.getProvinceid()+":"+"\""+mapDTO.getFullname()+"\",";
            sb.append(a);
        }
             sb.append("},");
        System.out.println(sb.toString());
        return sb;


    }

    public StringBuffer test4()  {
        //市
        StringBuffer sb=new StringBuffer();

        List<MapDTO> g = p.getid();
        //所有的省的id
        for (int i = 0; i < g.size(); i++) {
            //其中一个省
            StringBuffer t=new StringBuffer();
            t.append(g.get(i).getProvinceid()+":{");
            //省内所有的市
            int num=1;
            List<MapDTO> mapDTOS = c.get(g.get(i).getProvinceid());
            for (MapDTO mapDTO : mapDTOS) {
                String a=mapDTO.getCityid()+":"+"\""+mapDTO.getFullname()+"";
                t.append(a);
                if (num==mapDTOS.size()){
                    t.append("");
                }else{
                    num++;
                    t.append(",");
                }
            }
            t.append("},");

           sb.append(t);

        }
        System.out.println(sb.toString());
        return sb;


    }

    public StringBuffer test5()  {
      //区
        StringBuffer sb=new StringBuffer();
        List<MapDTO> g = c.getid();
        int temp=1;
        for (int i = 0; i < g.size(); i++) {
            //其中一个市
            StringBuffer t=new StringBuffer();
            t.append(g.get(i).getCityid()+":{");
            //市内所有的区
            int num=1;
            List<MapDTO> mapDTOS = d.get(g.get(i).getCityid());
            for (MapDTO mapDTO : mapDTOS) {
                String a=mapDTO.getDid()+":"+"\""+mapDTO.getFullname()+"\"";
                t.append(a);
                if (num==mapDTOS.size()){
                    t.append("");
                }else{
                    num++;
                    t.append(",");
                }

            }

            t.append("},");

            sb.append(t);

        }
        System.out.println(sb.toString());
        return sb;


    }

    @Test
    public void test6()  {
        StringBuffer s = new StringBuffer();
        s.append(test3().toString());
        s.append(test4().toString());
        s.append(test5().toString());
        System.out.println(s.toString());

    }
    @Test
    public void test7()  {
        //格式---所有省显示，省内所有市显示----某市所有区显示
        StringBuffer sheng=test3();//所有省
        //省内所有市
        StringBuffer sb=new StringBuffer();

        List<MapDTO> g = p.getid();
        //所有的省的id
        for (int i = 0; i < g.size(); i++) {
            //其中一个省
            StringBuffer t=new StringBuffer();
            t.append(g.get(i).getProvinceid()+":{\n");
            //省内所有的市
            int num=1;
            List<MapDTO> mapDTOS = c.get(g.get(i).getProvinceid());
            for (MapDTO mapDTO : mapDTOS) {
                //省内所有市
                String a=mapDTO.getCityid()+":"+"\""+mapDTO.getFullname()+"\"";
                t.append(a);
                if (num==mapDTOS.size()){
                    t.append("\n");
                }else{
                    num++;
                    t.append(",\n");

                    //市内所有区
                    for (MapDTO m : mapDTOS) {
                        m.getCityid();//某一个市的id

                        t.append(g.get(i).getCityid()+":{\n");
                        //市内所有的区
                        int num1=1;
                        List<MapDTO> mm = d.get(g.get(i).getCityid());
                        for (MapDTO d : mm) {
                            String z=d.getDid()+":"+"\""+mapDTO.getFullname()+"\"";
                            t.append(z);
                            if (num1==mm.size()){
                                t.append("\n");
                            }else{
                                num++;
                                t.append(",\n");
                            }

                        }

                        t.append("},\n");

                    }
                }



            }
            t.append("},\n");

            sb.append(t);

        }
        System.out.println(sb);



    }*//*

}
*/
