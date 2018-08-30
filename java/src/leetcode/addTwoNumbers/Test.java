package leetcode.addTwoNumbers;

public class Test {
    public static void main(String[] args) {
        ListNode node1 = new ListNode(2);
        ListNode node2 = new ListNode(4);
        node1.next = node2;
        ListNode node3 = new ListNode(3);
        node2.next = node3;

        ListNode node4 = new ListNode(5);
        ListNode node5 = new ListNode(6);
        node4.next = node5;
        ListNode node6 = new ListNode(4);
        node5.next = node6;
        
        ListNode result = addTwoNumbers(node1, node4);
        System.out.println("");
    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode head = null;
        ListNode current = null;
        int carry = 0;
        while (l1!=null || l2!=null || carry!=0){
            //相加
            int newVal = (l1!=null?l1.val:0) + (l2!=null?l2.val:0);
            //进位
            if(carry == 1) newVal += 1;
            carry = newVal >= 10 ? 1 : 0;
            //生成节点
            if(current == null){
                current = new ListNode(newVal%10);
                head = current;
            }else{
                current.next = new ListNode(newVal%10);
                current = current.next;
            }

            l1 = l1 != null ? l1.next : null;
            l2 = l2 != null ? l2.next : null;
        }
        return head;
    }

    static class ListNode{
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }
}
