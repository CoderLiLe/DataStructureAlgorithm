//
//  main.c
//  线性表
//
//  Created by LiLe on 2020/2/11.
//  Copyright © 2020 lile. All rights reserved.
//

#include <stdio.h>
#include "List.h"
#include "LinkList.h"
#include "StaticLinkList.h"

void testList() {
    SqList L;
    SqList Lb;
    
    ElemType e;
    Status i;
    int j, k;
    
    i = initList(&L);
    printf("初始化L后：L.length = %d\n", L.length);
    for (j = 1; j <= 5; j++) {
        listInsert(&L, 1, j);
    }
    printf("在L的表头插入1～5后：L.data = ");
    listTraverse(L);
    
    i = listClear(&L);
    printf("清空L后：L.length = %d\n", L.length);
    i = listEmpty(&L);
    printf("L 是否为空：i = %d(1:是 0:否)\n", i);
    
    for (j = 1; j <= 10; j++) {
        listInsert(&L, j, j);
    }
    printf("在L的表尾依次插入1～10后：L.data = ");
    listTraverse(L);
    printf("L.length = %d\n", L.length);
    
    listInsert(&L, 1, 0);
    printf("在L的表头插入0后：L.data = ");
    listTraverse(L);
    printf("L.length = %d\n", L.length);
    
    getElem(L, 5, &e);
    printf("第5个元素的值为：%d\n", e);
    for (j = 3; j <= 4; j++) {
        k = localElem(L, j);
        if (k) {
            printf("第 %d 个元素的值为 %d\n", k, j);
        } else {
            printf("没有值为 %d 的元素\n", j);
        }
    }
    
    k = listLength(&L);
    for (j = k + 1; j >= k; j--) {
        i = listDelete(&L, j, &e);
        if (i == ERROR) {
            printf("删除第 %d 个数据失败\n", j);
        } else {
            printf("删除第 %d 个元素的值为 %d\n", j, e);
        }
    }
    
    printf("依次输出L的元素：");
    listTraverse(L);
    
    i = initList(&Lb);
    for (j = 6; j <= 15; j++) {
        i = listInsert(&Lb, 1, j);
    }
    printf("在Lb的表头插入6～15后：Lb.data = ");
    listTraverse(Lb);
    
    unionLists(&L, Lb);
    
    printf("依次输出合并了 Lb 的 L 的元素");
    listTraverse(L);
}

void testLinkList() {
    LinkList L;
    ElemType e;
    Status i;
    int j, k;
    
    i = initLinkList(&L);
    printf("初始化L后：ListLength(L) = %d\n", linkListLength(&L));
    
    for(j = 1; j <= 5; j++)
        i = linkListInsert(&L, 1, j);
    printf("在L的表头依次插入1～5后：L.data = ");
    linkListTraverse(L);
    
    printf("ListLength(L) = %d \n", linkListLength(&L));
    i = linkListEmpty(L);
    printf("L是否空：i = %d(1:是 0:否)\n",i);
    
    i = linkListClear(&L);
    printf("清空L后：ListLength(L) = %d\n", linkListLength(&L));
    i = linkListEmpty(L);
    printf("L是否空：i=%d(1:是 0:否)\n",i);
    
    for(j = 1; j <= 10; j++)
        linkListInsert(&L,j,j);
    printf("在L的表尾依次插入1～10后：L.data = ");
    linkListTraverse(L);
    
    printf("ListLength(L) = %d \n", linkListLength(&L));
    
    linkListInsert(&L, 1, 0);
    printf("在L的表头插入0后：L.data = ");
    linkListTraverse(L);
    printf("ListLength(L) = %d \n", linkListLength(&L));
    
    getLinkListElem(L, 5, &e);
    printf("第5个元素的值为：%d\n", e);
    
    for(j = 3; j <= 4; j++)
    {
        k = localLinkListElem(L, j);
        if(k)
            printf("第%d个元素的值为%d\n", k, j);
        else
            printf("没有值为%d的元素\n", j);
    }
    
    k = linkListLength(&L); /* k为表长 */
    for(j = k + 1; j >= k; j--)
    {
        i = linkListDelete(&L, j, &e); /* 删除第j个数据 */
        if(i == ERROR)
            printf("删除第%d个数据失败\n", j);
        else
            printf("删除第%d个的元素值为：%d\n", j, e);
    }
    printf("依次输出L的元素：");
    linkListTraverse(L);
    
    j = 5;
    linkListDelete(&L, j, &e); /* 删除第5个数据 */
    printf("删除第%d个的元素值为：%d\n", j, e);
    
    printf("依次输出L的元素：");
    linkListTraverse(L);
    
    i = linkListClear(&L);
    printf("\n清空L后：ListLength(L) = %d\n", linkListLength(&L));
    createLinkListHead(&L, 20);
    printf("整体创建L的元素(头插法)：");
    linkListTraverse(L);
        
    i = linkListClear(&L);
    printf("\n删除L后：ListLength(L)=%d\n", linkListLength(&L));
    createLinkListTail(&L,20);
    printf("整体创建L的元素(尾插法)：");
    linkListTraverse(L);
}

void testStaticLinkList()
{
    StaticLinkList SLL;
    Status i;
    i = initStaticLinkList(SLL);
    printf("初始化 static link list 后：SLL.length = %d\n", staticLinkListLength(SLL));
    
    i = staticLinkListInsert(SLL, 1, 'F');
    i = staticLinkListInsert(SLL, 1, 'E');
    i = staticLinkListInsert(SLL, 1, 'D');
    i = staticLinkListInsert(SLL, 1, 'B');
    i = staticLinkListInsert(SLL, 1, 'A');
    printf("\n在 SLL 的头依次插入 FEDBA 后，SLL.data = ");
    staticLinkListTraverse(SLL);
    
    i = staticLinkListInsert(SLL, 3, 'C');
    printf("\n在 SLL 的 B 和 D 插入 C 后，SLL.data = ");
    staticLinkListTraverse(SLL);

    i = staticLinkListDelete(SLL, 1);
    printf("\nSLL 中删除 A 后，SLL.data = ");
    staticLinkListTraverse(SLL);

    printf("\n");
}

int main(int argc, const char * argv[]) {
//    testList();
//    testLinkList();
    testStaticLinkList();
    
    return 0;
}

