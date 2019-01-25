package main.java.exam.wordcount;

import java.io.*;
import java.util.Scanner;

import static java.lang.System.exit;
/**
 * 题目三：
 JAVA中对文件读取的效率会受到文件大小本身的影响，本题目要求能够对于文本文件进行读取，在保证读取效率的同时，要求内存不能溢出。
 要求：
 输入一个本地文本文件地址，文本文件大小为2G,文本编码类型为utf-8。
 读取该文本文件中出现特定单词的数量
 把文本部分文件读取到内存中后，即可释放内存，并统计特定单词出现次数和总时间耗时
 尽量减低字符统计耗时。
 * */
public class WordCount {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);

        //获取用户输入的字符串
        String path=null,word=null;
        System.out.print("请输入文件路径:");
        path=sc.nextLine();
        File file = new File(path);
        if(!file.exists()) {
            System.out.println("输入路径有误!path="+path);
            exit(0);
        }
        System.out.print("请输入要统计的单词:");
        word =sc.nextLine();
        int count = wordCount(path, word);
        System.out.println("单词"+word+"在文中出现的次数为:"+count);
    }

    //Scanner允许对每一行进行处理，而不保持对它的引用
    public static int wordCount(String path, String word){
        int count = 0;
        FileInputStream inputStream = null;
        Scanner sc = null;
        try {
            inputStream = new FileInputStream(path);
            sc = new Scanner(inputStream, "UTF-8");
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] words = line.split(" ");
                for(int i=0;i<words.length;i++){
                    if(word.equals(words[i]))
                        count ++;
                }
            }
            // note that Scanner suppresses exceptions
            if (sc.ioException() != null) {
                throw sc.ioException();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (sc != null) {
                sc.close();
            }
            return count;
        }
    }
}
