package com.it.banksystem;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class text {
    public static void main(String[] args) throws IOException {
        int choice=0;
        File file = new File("Information.txt");
        File file1 = new File("manegerinfor.txt");
        if(!file.exists()){
            file.createNewFile();
        }
        if(!file1.exists()){
            file1.createNewFile();
        }
        Scanner s=new Scanner(System.in);
        while(true) {
            new InitMenu();//初始化总界面
            choice=s.nextInt();
            switch (choice){
                //管理员登录
                case 1:{
                    new ManagerOperation();
                    break;
                }
                //用户登录
                case 2:{
                    new CustomersLogin();
                    break;
                }
                //申请银行卡
                case 3:{
                    new AddCustomer();
                    break;

                }
                case 4:{
                    System.out.println("成功退出");
                    System.exit(0);
                }
            }
        }
    }
}

