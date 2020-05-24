package com.zhaojy.medium

/*
两数相加
{@link https://leetcode-cn.com/problems/add-two-numbers/}
 */
class AddTwoNumbers {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val addTwoNumbers = AddTwoNumbers()
            val value1 = ListNode(2)
            value1.next = ListNode(4)
            value1.next?.next = ListNode(3)
            val value2 = ListNode(5)
            value2.next = ListNode(6)
            value2.next?.next = ListNode(4)
            val result = addTwoNumbers.addTwoNumbers(value1, value2)
            addTwoNumbers.traverseListNode(result)
        }
    }

    fun addTwoNumbers(l1: ListNode?, l2: ListNode?): ListNode? {
        var node1 = l1
        var node2 = l2
        val result = ListNode(0)
        var tempResult = result
        while (node1 != null || node2 != null) {
            var value1 = 0
            var value2 = 0
            if (node1 != null) {
                value1 = node1.`val`
                node1 = node1.next
            }
            if (node2 != null) {
                value2 = node2.`val`
                node2 = node2.next
            }
            var add = value1 + value2 + tempResult.`val`
            if (add >= 10) {
                tempResult.`val` = add % 10
                tempResult.next = ListNode(1)
            } else {
                tempResult.`val` = add
                tempResult.next = ListNode(0)
            }
            if (node1 == null && node2 == null && tempResult.next!!.`val` == 0) {
                tempResult.next = null
            } else {
                tempResult = tempResult.next!!
            }
        }
        return result
    }

    fun traverseListNode(node: ListNode?) {
        if (node?.next != null) {
            traverseListNode(node.next)
        }
        print(node?.`val`)
    }

    class ListNode(var `val`: Int) {
        var next: ListNode? = null
    }
}
