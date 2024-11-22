package heaps;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

class Twitter {
	
	/*
	 * 355. Design Twitter
	 * 
	 * Design a simplified version of Twitter where users can post tweets, follow/unfollow another user, 
	 * and is able to see the 10 most recent tweets in the user's news feed.
	 * 
	 * Analysis : 
	 * 1. there two entities : tweet and users.
	 * 2. user relates to users with 'follows' relationship so either we can store user1 -> follower1, follower2..
	 * or we can store user1 -> followee1, followee2...
	 * 
	 * 3. to decide the structure we need to look at operation needed to be performed on users.
	 * 		a. follow(u1, u2) : user1 follows user2
	 * 		b. unfollow(u1, u2) : user1 unfollows user2
	 * 		c. postTweet(u1, t1) : user1 posts a tweet1
	 * 		d. getNewsFeed(u1) : get 10 most recent tweets done by him or users that he follows.
	 * 
	 * Since the getNewsFeed is the main operation : and it requires to get all users that a target user follows.
	 * hence we must store user to followees relation in structure.
	 * 
	 * To be able to keep track of order of tweets, we need to also attach some order when a tweet is posted.
	 * Solution : maintaining a global count, increment count for each tweet posted.
	 * 
	 * to get new feed for a userid, we need to get all users that userid follows, which is simple retrieval.
	 * for each followee user, we have lists of sorted tweets.
	 * 
	 * now creating final list of size 10. using minHeap, we can keep most recent (higher values) tweets in the bottom 
	 * of heap, and keep removing top onces when heap size reaches 10.
	 * 
	 * Since it is also possible that a user has deleted all his tweets, and only one of the followee has all the
	 * relavant tweets, we need to keep putting the tweets in heap, when the size reaches 10, we must pop and remove it.
	 * because, only more possibilities are there : next coming tweets in our iteration have higher value or lower.
	 * we should just put them in heap and poll the top after 10 items. in this way we have made sure that largest is
	 * put deep in the heap due to heapify and lower are sailing at top ready to be removed.
	 * 
	 * */
	
    //follower user - followees.
    private Map<Integer, Set<Integer>> followees;
    //tweets : usrid-pairTweets
    private Map<Integer, Deque<Pair<Integer, Integer>>> tweets;
    private int nextTweetNumber;
    public Twitter() {
        nextTweetNumber = 1;
        followees = new HashMap<>();
        tweets = new HashMap<>();
    }
    
    public void postTweet(int userId, int tweetId) {
        //for each tweet id, store an array of size 2 : [tweetNo, tweetId]
        Pair<Integer, Integer> tuple = new Pair(nextTweetNumber++, tweetId);
        Deque queOfTweets = tweets.getOrDefault(userId, new LinkedList<>());
        queOfTweets.addFirst(tuple);
        tweets.put(userId, queOfTweets);
    }
    
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Pair<Integer, Integer>> minHeap = new PriorityQueue<>(
            (a ,b) ->Integer.compare(a.getKey(), b.getKey())
        );
        //1. get all followees of uesrId
        if(followees.containsKey(userId)){
            Set<Integer> users = followees.get(userId);
            for(Integer user : users){
                Deque<Pair<Integer, Integer>> userTweets = tweets.get(user);
                if(null != userTweets){
                    for(Pair<Integer, Integer> tweetPair : userTweets){
                            minHeap.add(tweetPair);
                        if(minHeap.size() > 10)
                            minHeap.poll();
                    }
                }
            }
        }

        if(tweets.containsKey(userId)){
            Deque<Pair<Integer, Integer>> userTweets = tweets.get(userId);
            if(null != userTweets){
                for(Pair<Integer, Integer> tweetPair : userTweets){
                    minHeap.add(tweetPair);
                    if(minHeap.size() > 10)
                        minHeap.poll();
                }
            }
        }

        //2. create result list from heap.
        Deque<Integer> feedResult = new LinkedList<>();
        while(minHeap.size() > 0){
            feedResult.addFirst(minHeap.poll().getValue());
        }
        return new ArrayList<>(feedResult);
    }
    
    public void follow(int followerId, int followeeId) {
        Set<Integer> updatedFollowees = followees.getOrDefault(followerId, new HashSet<>());
		updatedFollowees.add(followeeId);
        followees.put(followerId, updatedFollowees);
    }
    
    public void unfollow(int followerId, int followeeId) {
        if(followees.containsKey(followerId)) {
        	followees.get(followerId).remove(followeeId);
        }
    }

    private class Pair<K, V> {
        private K first;
        private V second;

        public Pair(K key, V value){
            this.first = key;
            this.second = value;
        }

        public K getKey() {
            return first;
        }

        public V getValue() {
            return second;
        }
    }
}
