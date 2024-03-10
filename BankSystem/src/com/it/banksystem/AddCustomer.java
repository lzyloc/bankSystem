package com.it.banksystem;

import java.io.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddCustomer {
    long time= Instant.now().toEpochMilli();
    Random random = new Random(time); // 使用种子构造随机数生成器
    Scanner s=new Scanner(System.in);
    public AddCustomer() throws IOException {
        ArrayList<Customers> arr=new ArrayList<>();
        File file=new File("Information.txt");
        if(file.length()!=0) {
            try (
                    FileInputStream fileInput = new FileInputStream("Information.txt");
                    ObjectInputStream ois = new ObjectInputStream(fileInput)
            ) {
                Object obj;
                while (true) {
                    try {
                        Customers o = (Customers) ois.readObject();
                        if (o== null) {
                            break; // 到达文件末尾，退出循环
                        }
                        arr.add(o);
                    } catch (EOFException e) {
                        break; // 捕获 EOFException，退出循环
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
        int psd;
        System.out.println("请输入您的姓名");
        String name=s.next();
        System.out.println("已为您生成卡号,请记住您的卡号");
        String card =getcardnum(arr);
        System.out.println(card);
        while(true) {
            System.out.println("请输入您的密码,密码为六位数数字");
            String temp = s.next();
            Pattern pt = Pattern.compile("[0-9]{6}");
            Matcher mc = pt.matcher(temp);
            if (!mc.matches()) {
                System.out.println("密码输入有误,请重新输入");
                continue;
            }
            psd = Integer.parseInt(temp);
            System.out.println("请再次确认您的密码");
            if (s.nextInt() == psd) {
                System.out.println("银行卡创建成功");
                break;
            } else {
                System.out.println("两次密码不一致,请重新输入");
            }
        }
        Customers customer;
        if(arr.size()==0){
            customer = new Customers(name, 0, 1, psd, card);
        }
        else{
            if(arr.get(arr.size()-1).getAllMoney()>0){
                customer = new Customers(name,0,arr.get(arr.size()-1).getRank()+1,psd,card);
            }
            else {
                customer = new Customers(name,0,arr.get(arr.size()-1).getRank(),psd,card);
            }
        }
        arr.add(customer);
        try (
                FileOutputStream fileOutput = new FileOutputStream("Information.txt");
                ObjectOutputStream ois = new ObjectOutputStream(fileOutput)
        ) {
            // 将对象序列化并写入文件
            for (Customers customers : arr) {
                ois.writeObject(customers);
                ois.flush(); // 确保所有数据都被写入
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getcardnum( ArrayList<Customers> arr){
        StringBuilder sb=new StringBuilder();
        boolean flag=true;
        String string;
        while(true) {
            for (int i = 0; i < 15; i++) {
                int randomNumber = random.nextInt(10); // 生成 [0, 10) 内的随机整数
                sb.append(randomNumber);
            }
            string = sb.toString();
            for (Customers customers : arr) {
                if(customers.getCardNumber().equals(string)){
                    flag=false;
                    break;
                }
            }
            if(flag==true){
                break;
            }
            flag=true;
        }
        return string;
    }
}

