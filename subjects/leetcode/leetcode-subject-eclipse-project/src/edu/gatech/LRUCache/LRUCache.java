package edu.gatech.LRUCache;

import java.util.HashMap;
import java.util.Map;

public class LRUCache {
    int capacity, has;
    Map<Integer, DataNode> map;
    DataNode first, last;
    
    public LRUCache(int capacity) {
        this.capacity = capacity;
        has = 0;
        map = new HashMap<Integer, DataNode>();
        first = null;
        last = null;
    }
    
    private void moveNodeToFirst(DataNode node) {
        if(node == last && last.pre != null) { // bug, also need to update last pointer
            last.pre.next = null;
            last = last.pre;
        }
        if(node != first) {
            if(node.pre != null) node.pre.next = node.next;
            if(node.next != null) node.next.pre = node.pre;
            node.pre = null;
            node.next = first;
            first.pre = node;
            first = node;
        }
    }
    
    public int get(int key) {
        if(map.containsKey(key)) {
            DataNode node = map.get(key);
            moveNodeToFirst(node);
            return node.val;
        } else 
            return -1;
    }
    
    public void set(int key, int value) {
        // change value for existing key
        if(map.containsKey(key)) {
            DataNode node = map.get(key);
            node.val = value;
            // maintain node order in the list
            moveNodeToFirst(node);
        } else {
            DataNode node = new DataNode(key, value);
            map.put(key, node);
            // need to throw old value
            if(has == capacity) {
                map.remove(last.key);
                if(last.pre != null) { // last != null cause capacity > 0
                    last.pre.next = null;
                    last = last.pre;
                } else {
                    last = node;
                }
            } else has++;
            
            if(first != null) { // bug, forgot this = =|||
                node.next = first;
                first.pre = node;
            }
            first = node;
            if(last == null) last = node;
        }
    }
    
    public class DataNode {
        public int key, val;
        public DataNode pre, next;
        
        public DataNode(int key, int val) {
            this.key = key;
            this.val = val;
            pre = null;
            next = null;
        }
    }
}
