package com.zhaojy.medium

import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class Twitter {

    /** Initialize your data structure here. */
    constructor() {
        User.userMap.clear()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val twitter = Twitter()
            twitter.postTweet(1, 1)
            println(twitter.getNewsFeed(1))
            twitter.follow(2, 1)
            println(twitter.getNewsFeed(2))
            twitter.unfollow(2, 1)
            println(twitter.getNewsFeed(2))
        }
    }

    fun newUser(userId: Int): User? {
        if (User.userMap.containsKey(userId)) {
            return User.userMap.get(userId)
        }
        val user = User(userId)
        User.userMap.put(userId, user)
        return user
    }

    /** Compose a new tweet. */
    fun postTweet(userId: Int, tweetId: Int) {
        var user: User? = null
        if (User.userMap.containsKey(userId)) {
            user = User.userMap.get(userId)
        } else {
            user = newUser(userId)
        }
        user?.addTweet(TwitterTime(tweetId))
    }

    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    fun getNewsFeed(userId: Int): List<Int>? {
        if (User.userMap.containsKey(userId)) {
            val user = User.userMap.get(userId)
            return user?.getLastTenTwitter()
        }
        return ArrayList()
    }

    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    fun follow(followerId: Int, followeeId: Int) {
        if (followeeId == followerId) {
            return
        }
        val follower = newUser(followerId)
        val followee = newUser(followeeId)
        follower!!.followMap.put(followeeId, followee)
    }

    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    fun unfollow(followerId: Int, followeeId: Int) {
        if (followeeId == followerId) {
            return
        }
        val follower = newUser(followerId)
        follower!!.followMap.remove(followeeId)
    }

}

class User constructor(userId: Int) {
    val userId = userId
    val followMap = HashMap<Int, User?>()
    val tweetList = LinkedList<TwitterTime>()

    companion object {
        val userMap = HashMap<Int, User?>()
        val MAX_TWITTER = 10
    }

    fun addTweet(twitterTime: TwitterTime) {
        if (tweetList.size == MAX_TWITTER) {
            tweetList.removeLast()
        }
        tweetList.addFirst(twitterTime)
    }

    fun getLastTenTwitter(): List<Int> {
        val twitterList = ArrayList<Int>()
        val queue: PriorityQueue<TwitterTime> = PriorityQueue(MAX_TWITTER, object : Comparator<TwitterTime> {
            override fun compare(p0: TwitterTime?, p1: TwitterTime?): Int {
                if (p0?.time!! > p1?.time!!)
                    return -1
                else if (p0?.time!! < p1?.time!!)
                    return 1
                else
                    return 0
            }
        })
        for (temp in tweetList) {
            queue.add(temp)
        }
        for (follow in followMap.values) {
            for (temp in follow?.tweetList!!) {
                queue.add(temp)
            }
        }
        while (!queue.isEmpty()) {
            twitterList.add(queue.poll().tweetId)
        }
        if (twitterList.size > MAX_TWITTER) {
            return twitterList.subList(0, MAX_TWITTER)
        }
        return twitterList
    }
}

class TwitterTime {
    var tweetId: Int = 0
    var time: Int = 0

    companion object {
        var timeNumber = 0
    }

    constructor(tweetId: Int) {
        this.tweetId = tweetId
        time = timeNumber++
    }
}
