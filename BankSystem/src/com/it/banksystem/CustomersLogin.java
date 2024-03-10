package com.it.banksystem;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomersLogin {
    Customers ct=new Customers();
    TreeSet<Customers> cusrank=new TreeSet<>((o1, o2) ->{
        if(o1.getAllMoney()>o2.getAllMoney()){
            return -1;
        }
        else if(o1.getAllMoney()< o2.getAllMoney()){
            return 1;
        }
        return 0;
    });
    Scanner s=new Scanner(System.in);
    public CustomersLogin() throws IOException {
        try (
                FileInputStream fileInput = new FileInputStream("Information.txt");
                ObjectInputStream ois = new ObjectInputStream(fileInput)
        ) {
            Object obj;
            while (true) {
                try {
                    obj = ois.readObject();
                    if (obj == null) {
                        break; // 到达文件末尾，退出循环
                    }
                    Customers cus = (Customers) obj;
                    cusrank.add(cus);
                } catch (EOFException e) {
                    break; // 捕获 EOFException，退出循环
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }


        int num=1;
        while(true){
            if(checknameAndbankWord(num)) {
                break;
            }
            if(num==3){
                return;
            }
            num++;
        }
        num=1;
        while(true){
            if(checkPassWord(num)){
                break;
            }
            if(num==3){
                return;
            }
            num++;
        }
        while(true) {
            CostomersMenu();
            int choice = s.nextInt();
            switch (choice) {
                //退出登录
                case 0:
                    return;
                //存款
                case 1: {
                    AddMoney();
                    break;
                }
                //取款
                case 2: {
                    GetMoney();
                    break;
                }
                //转账
                case 3: {
                    TransformMoney();
                    break;
                }
                //修改密码
                case 4: {
                    ChangePassword();
                    break;
                }
                case 5:{
                    System.out.println("您的余额为"+ct.getAllMoney()+"元");
                    break;
                }
                default:{
                    System.out.println("输入错误,请重新输入");
                }
            }
        }
    }

    public boolean checknameAndbankWord(int num){
        System.out.println("请输入您的姓名和卡号");
        String name=s.next();
        String id=s.next();
        for (Customers customers : cusrank) {
            if(customers.getName().equals(name)&&customers.getCardNumber().equals(id)){
                System.out.println("输入信息正确");
                ct=customers;
                return true;
            }
        }
        if(num<3){
            System.out.println("输入错误,请重新输入,您还有"+(3-num)+"次机会");}
        else{
            System.out.println("三次输入均错误,登录失败");
        }
        return false;
    }
    public boolean checkPassWord(int num){
        System.out.println("请输入您的银行密码");
        int psd=s.nextInt();
        for (Customers customers : cusrank) {
            if(customers.getPassWord()==psd) {
                System.out.println("登录成功!");
                return true;
            }
        }
        if(num<3){
            System.out.println("输入错误,请重新输入,您还有"+(3-num)+"次机会");}
        else{
            System.out.println("三次输入均错误,登录失败");
        }
        return false;
    }
    public void CostomersMenu(){
        System.out.println("---------------------------------------------------------------");
        System.out.println("欢迎"+ct.getName()+"登录,请选择您所需要的服务");
        System.out.println("0:退出登录");
        System.out.println("1:存款");
        System.out.println("2:取款");
        System.out.println("3:转账");
        System.out.println("4:修改密码");
        System.out.println("5:查看余额");
        System.out.println("---------------------------------------------------------------");
    }
    public void AddMoney(){
        System.out.println("请输入您要存人的金额");
        double money=s.nextDouble();
        ct.setAllMoney(ct.getAllMoney()+money);
        for (Customers customers : cusrank) {
            if(customers.getName().equals(ct.getName())&&customers.getCardNumber().equals(ct.getCardNumber())){
                customers.setAllMoney(ct.getAllMoney());
                break;
            }
        }
        Updatavector(cusrank);
        changeinfor(cusrank);
        System.out.println("存款成功");

    }
    public void Updatavector(TreeSet<Customers> cusrank) {
        TreeSet<Customers> temp = new TreeSet<>((o1, o2) -> {
           if(o1.getAllMoney()>o2.getAllMoney()){
               return -1;
           }
           else if(o1.getAllMoney()< o2.getAllMoney()){
               return 1;
           }
           return 0;
        });
        temp.addAll(cusrank);
        cusrank.clear();
        cusrank.addAll(temp);
        int num = 1;
        ArrayList<Double> arr = new ArrayList<Double>();
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (Customers customers : cusrank) {
            arr.add(customers.getAllMoney());
        }
        for (int i = 0; i < arr.size() - 1; i++) {
            if (arr.get(i) > arr.get(i + 1)) {
                list.add(num);
                num++;
            } else if (arr.get(i) == arr.get(i + 1)) {
                list.add(num);
            }
        }
        list.add(num);
        num = 0;
        for (Customers customers : cusrank) {
            customers.setRank(list.get(num++));
        }
    }
    public void GetMoney(){
        System.out.println("请输入您要取走的金额");
        double money=s.nextDouble();
        if(ct.getAllMoney()<money){
            System.out.println("余额不足,剩余金额为"+ct.getAllMoney()+"元");
            return;
        }
        ct.setAllMoney(ct.getAllMoney()-money);
        for (Customers customers : cusrank) {
            if(customers.getName().equals(ct.getName())&&customers.getCardNumber().equals(ct.getCardNumber())){
                customers.setAllMoney(ct.getAllMoney());
                break;
            }
        }
        Updatavector(cusrank);
        changeinfor(cusrank);
        System.out.println("取款成功");
    }
    public void TransformMoney(){
        System.out.println("请输入您要转入金额的卡号");
        String id=s.next();
        for (Customers customers : cusrank) {
            if(customers.getCardNumber().equals(id)){
                System.out.println("请输入您要转让的金额");
                double money=s.nextDouble();
                if(money> ct.getAllMoney()){
                    System.out.println("您的余额不足,剩余金额为"+ct.getAllMoney()+"元");
                    return;
                }
                customers.setAllMoney(customers.getAllMoney()+money);
                ct.setAllMoney(ct.getAllMoney()-money);
                for (Customers customer : cusrank) {
                    if(customer.getName().equals(ct.getName())&&customer.getCardNumber().equals(ct.getCardNumber())){
                        customer.setAllMoney(ct.getAllMoney());
                        break;
                    }
                }
                System.out.println("转账成功");
                Updatavector(cusrank);
                changeinfor(cusrank);
                return;
            }
        }
        System.out.println("您输入的卡号不存在");
    }
    public void ChangePassword(){
        System.out.println("请输入您的原密码");
        int psd=s.nextInt();
        if(ct.getPassWord()==psd){
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
                    System.out.println("密码修改成功");
                    break;
                } else {
                    System.out.println("两次密码不一致,请重新输入");
                }
            }
        }
    }
    public void changeinfor(TreeSet<Customers> cusrank){
        try {
            FileWriter fw=new FileWriter("Information.txt");
            fw.write("");
            fw.flush();
            fw.close();
            ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream("Information.txt"));
            for (Customers customers : cusrank) {
                oos.writeObject(customers);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


