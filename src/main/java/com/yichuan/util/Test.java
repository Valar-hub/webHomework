package com.yichuan.util;

public class Test
{
    public static void main(String[] args) throws InterruptedException {
        for (int i = 1; i <20 ; i++)
        {
            Thread.sleep(i*10000);
            MailUtil mail=new MailUtil("3224063958@qq.com","在垃圾箱吗？");
            mail.run();
        }
    }
}
