//
//  main.cpp
//  Leetcode
//
//  Created by LiLe on 2021/7/20.
//

#include <iostream>
#include <assert.h>

#include "_0146_LRU缓存机制.hpp"
#include "排序数组测试.hpp"
#include "栈队列测试.hpp"
#include "StringTest.hpp"
#include "DpTest.hpp"
#include "BinaryTreeTest.hpp"
#include "DFSTest.hpp"
#include "MathTest.hpp"

void test0146() {
    LRUCache *lRUCache = new LRUCache(2);
    lRUCache->put(1, 1); // 缓存是 {1=1}
    lRUCache->put(2, 2); // 缓存是 {1=1, 2=2}
    assert(1 == lRUCache->get(1)); // 返回 1
    lRUCache->put(3, 3); // 该操作会使得关键字 2 作废，缓存是 {1=1, 3=3}
    assert(-1 == lRUCache->get(2)); // 返回 -1 (未找到)
    lRUCache->put(4, 4); // 该操作会使得关键字 1 作废，缓存是 {4=4, 3=3}
    assert(-1 == lRUCache->get(1));    // 返回 -1 (未找到)
    assert(3 == lRUCache->get(3));    // 返回 3
    assert(4 == lRUCache->get(4));    // 返回 4
}

int main(int argc, const char * argv[]) {
//    test0146();
    
//    sortArraytest();
//    stackQueueTest();
//    stringTest();
    
    dpTest();
    
//    binaryTreeTest();
    
//    dfsTest();
    
//    mathTest();
    
    cout << "执行完毕！！！" << endl;
    return 0;
}


