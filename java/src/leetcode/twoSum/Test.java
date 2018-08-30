package leetcode.twoSum;

import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) throws Exception {
        int[] nums = new int[]{2, 7, 11, 15};
        int target = 9;
        int[] result = twoSum(nums, target);
        System.out.println("");
    }

    //两数之和
    public static int[] twoSum(int[] nums, int target) throws Exception {
        Map<Integer,Integer> map = new HashMap<>();
        for(int i=0;i<nums.length;i++){
            int temp = target - nums[i];
            if(map.containsKey(temp)){
                return new int[]{map.get(temp), i};
            }
            map.put(nums[i], i);
        }
        return new int[]{};
    }

}
