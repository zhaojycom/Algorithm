package com.zhaojy.easy

/*
最小高度树
{@link https://leetcode-cn.com/problems/minimum-height-tree-lcci/}
 */
class SortedArrayToBST {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val nums = intArrayOf(-10, -3, 0, 5, 9)
            val bst = SortedArrayToBST()
            val treeNode = bst.sortedArrayToBST(nums)
            bst.travsal(treeNode)
        }
    }

    fun travsal(treeNode: TreeNode?) {
        if (null != treeNode) {
            travsal(treeNode.left)
            println(treeNode?.`val`)
            travsal(treeNode.right)
        }
    }

    fun sortedArrayToBST(nums: IntArray): TreeNode? {
        val size = nums.size
        if (size <= 0) {
            return null
        }
        val m = size / 2
        val rootNode = TreeNode(nums[m])
        if (m > 0) {
            rootNode.left = bst(nums, 0, m)
        }
        if (m + 1 < size) {
            rootNode.right = bst(nums, m + 1, size)
        }
        return rootNode
    }

    fun bst(nums: IntArray, s: Int, e: Int): TreeNode? {
        val m = (s + e) / 2
        val rootNode = TreeNode(nums[m])
        if (m > s) {
            rootNode.left = bst(nums, s, m)
        }
        if (m + 1 < e) {
            rootNode.right = bst(nums, m + 1, e)
        }
        return rootNode
    }

    class TreeNode(var `val`: Int) {
        var left: TreeNode? = null
        var right: TreeNode? = null
    }
}