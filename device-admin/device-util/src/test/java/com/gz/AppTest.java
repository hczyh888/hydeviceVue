package com.gz;

import static org.junit.Assert.assertTrue;

import com.gz.utils.MD5;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }
    @Test
    public void saltPwd(){
        String password = "123456";
        String salt = "58akb";//MD5.getRandomSalt(5);
        System.out.println("PASSWORD:"+password);
        System.out.println("salt:"+salt);
        try{
           String pwd = MD5.getEncryptedPwd(password,salt);
           System.out.println();
           System.out.println("pwd:"+pwd);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @Test
    public void validPassword(){
        try{
           if(MD5.validPassword("123456","3538616b62edd142d198cc4b9cc15da891bb2b7fc4")){
               System.out.println("通过");
           }else{
               System.out.println("失败");
           }

        }catch (Exception ex){
            ex.printStackTrace();
        }
}
}
