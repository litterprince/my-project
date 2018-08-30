package leetcode.lengthOfLongestSubstring;

import java.util.HashSet;
import java.util.Set;

public class Test {
    public static void main(String[] args) {
        String s = "a";
        System.out.println("length="+lengthOfLongestSubstring(s));
        System.out.println("len="+longestSubstring(s));
    }

    //暴力破解
    public static int lengthOfLongestSubstring(String s) {
        int max = 0;
        for(int i=0;i<s.length();i++){
            for(int j=i+1;j<s.length()+1;j++){
                if(allUnique(s,i,j)) max = Math.max(max, j-i);
            }
        }
        return max;
    }
    public static boolean allUnique(String s,int start, int end){
        Set<Character> set = new HashSet<>();
        for(int i=start;i<end;i++){
            if(set.contains(s.charAt(i))){
                return false;
            }else{
                set.add(s.charAt(i));
            }
        }
        return true;
    }

    //滑动窗口
    public static int longestSubstring(String s){
        int max = 0;
        int n = s.length();
        int i=0,j=0;
        Set<Character> set = new HashSet<>();
        while (i <n && j< n){
            Character c = s.charAt(j);
            if(!set.contains(c)){
                set.add(c);
                j++;
                max = Math.max(max, j-i);
            }else{
                c = s.charAt(i);
                set.remove(c);
                i++;
            }
        }
        return max;
    }
}
