package com.zhaojy.medium

/*
LRU缓存机制
{@link https://leetcode-cn.com/problems/lru-cache/}
 */
class LRUCache(capacity: Int) {
    private val mCapacity = capacity

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            var cache = LRUCache(2)
            cache.put(2, 1)
            cache.put(2, 2)
            println(cache.get(2))
            cache.put(1, 1)
            cache.put(4, 1)
            println(cache.get(2))
        }
    }

    private fun printInfo() {
        println(mMap)
        val sb = StringBuilder()
        var node: Node? = mHeadNode.nextNode
        sb.append("length=" + mMap.size + "  ")
        while (node != null && node.nextNode != null) {
            sb.append("[" + node.key + ",")
            sb.append("" + node.value + "],")
            node = node.nextNode
        }
        println(sb.toString() + "\n")
    }

    private var mMap = LinkedHashMap<Int, Node>(capacity)
    private var mHeadNode = Node(0, 0)
    private var mTailNode = Node(0, 0)

    init {
        mHeadNode.nextNode = mTailNode
        mTailNode.preNode = mHeadNode
    }

    fun get(key: Int): Int {
        if (mMap.containsKey(key)) {
            val node = mMap.get(key) ?: return -1
            if (mMap.size > 1) {
                val preNode = node.preNode
                val nextNode = node.nextNode
                preNode?.nextNode = nextNode
                nextNode?.preNode = preNode
                node.preNode = mTailNode.preNode
                mTailNode.preNode?.nextNode = node
                node.nextNode = mTailNode
                mTailNode.preNode = node
            }
            return node.value
        }
        return -1
    }

    fun put(key: Int, value: Int) {
        if (mMap.containsKey(key)) {
            //如果存在对应的key，则覆盖原值，并将对应node移动到链表尾部
            val node = mMap.get(key) ?: return
            node.value = value
            val preNode = node.preNode
            val nextNode = node.nextNode
            preNode?.nextNode = nextNode
            nextNode?.preNode = preNode
            node.preNode = mTailNode.preNode
            mTailNode.preNode?.nextNode = node
            node.nextNode = mTailNode
            mTailNode.preNode = node
        } else {
            if (mMap.size >= mCapacity) {
                //如果超出容量，则删除第一个元素
                val firstNode = mHeadNode.nextNode ?: return
                mHeadNode.nextNode = firstNode?.nextNode
                firstNode.nextNode?.preNode = mHeadNode
                mMap.remove(firstNode.key)
            }
            //插入尾部
            val node = Node(key, value)
            val lastNode = mTailNode.preNode
            lastNode!!.nextNode = node
            node.preNode = lastNode
            node.nextNode = mTailNode
            mTailNode.preNode = node
            mMap.put(key, node)
        }
    }

    inner class Node(key: Int, value: Int) {
        var preNode: Node? = null
        var nextNode: Node? = null
        val key = key
        var value = value
    }

}