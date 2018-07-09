package com.yicheng.test;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * 测试
 *
 * @author luo.yicheng
 */
/*@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)*/
public class DemoTest {


    public static void main(String[] args) {

        String str ="Mozilla/5.0 (iPad; CPU OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1";




        System.out.print(isPcMacOs(str));

    }

    /**
     *
     * 判断是否为 PC 中的 MAC
     * @param ua UA
     * @return TRUE FALSE
     */
    public static boolean isPcMacOs(String ua){

        ua = ua.toLowerCase();
        boolean falg = false;

        try {
            ua = ua.substring(ua.indexOf("like"),ua.indexOf("x")+1);

            System.out.println(ua);

            if (ua.equals("like mac os x")){
                falg = false;
            }


        }catch (Exception e){
            falg = true;
        }

        return falg;

    }

}
