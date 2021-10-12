package TwoPoints;

import common.ListNode;

public class _083_删除排序链表中的重复元素 {
    public ListNode deleteDuplicates(ListNode head) {
        if (head == null) return null;

        ListNode slow = head, fast = head;
        while (fast != null) {
            if (fast.val != slow.val) {
                // nums[slow] = nums[fast]
                slow.next = fast;
                // slow++
                slow = slow.next;
            }
            // fast++
            fast = fast.next;
        }

        // 断开与后面重复元素的连接
        slow.next = null;
        return head;
    }
}
