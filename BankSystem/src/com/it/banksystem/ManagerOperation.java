package com.it.banksystem;

import java.io.*;
import java.util.Scanner;

public class ManagerOperation {
    static {
        try {
            ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream("manegerinfor.txt"));
            Manager m = new Manager("张三", "123456");
            oos.writeObject(m);
            oos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    Scanner s=new Scanner(System.in);

    public ManagerOperation(){
        if (!ManagerLogin()) return;
        while(true){
            menu();
            int choice=s.nextInt();
            switch (choice){
                case 0:return;
                case 1: {
                    LookCustomer();
                    break;
                }
                case 2:{
                    LookRank();
                    break;
                }
                default:{
                    System.out.println("输入错误,请重新选择");
                    break;
                }
            }
        }

    }
    public void menu(){
        System.out.println("欢迎管理员张三,请输入您要执行的操作");
        System.out.println("0:退出登录");
        System.out.println("1:查看指定用户信息");
        System.out.println("2:查看所有用户资产排名");
    }
    public void LookCustomer(){
        Customers cus=null;
        System.out.println("请输入您要查看的用户的卡号");
        String card=s.next();
        boolean flag=false;
        try (
                FileInputStream fileInput = new FileInputStream("Information.txt");
                ObjectInputStream ois = new ObjectInputStream(fileInput)
        ) {
            while (true) {
                try {
                    cus =(Customers) ois.readObject();
                    if (cus == null) {
                        break; // 到达文件末尾，退出循环
                    }
                    if(cus.getCardNumber().equals(card)){
                        flag=true;
                        break;
                    }
                } catch (EOFException e) {
                    break; // 捕获 EOFException，退出循环
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


        if(!flag){
            System.out.println("该用户不存在");
            return;
        }
        System.out.println("姓名: "+cus.getName());
        System.out.println("银行卡号: "+cus.getCardNumber());
        System.out.println("总资产: "+cus.getAllMoney());
        System.out.println("资产排名: "+cus.getRank());
    }
    public void LookRank(){
        try (
                FileInputStream fileInput = new FileInputStream("Information.txt");
                ObjectInputStream ois = new ObjectInputStream(fileInput)
        ) {
            Object obj;
            while (true) {
                try {
                    obj = ois.readObject();
                    if (obj == null) {
                        ois.close();
                        break; // 到达文件末尾，退出循环
                    }
                    Customers cus = (Customers) obj;
                    System.out.println("姓名: "+cus.getName()+"  "+"卡号: "+cus.getCardNumber()+"  "+"资产排名: "+cus.getRank());
                } catch (EOFException e) {
                    break; // 捕获 EOFException，退出循环
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    public boolean ManagerLogin(){
        int num=1;
        while (num<=3) {
            System.out.println("请输入管理员姓名和账号ID");
            if (s.next().equals("张三") && s.next().equals("123456")) {
                System.out.println("输入正确");
                return true;
            }
            else if (num==3){
                System.out.println("三次输入均错误,登录失败");
            }
            else {
                System.out.println("输入错误,还有"+(3-num)+"次机会");
            }
             num++;
        }
        return false;
    }
}
