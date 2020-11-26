package com.giovanny.study.utils;

/**
 * @packageName: com.giovanny.study.utils
 * @className: SortUtils
 * @description: 排序方法
 * @author: YangJun
 * @date: 2020/8/13 10:10
 * @version: v1.0
 **/
public class SortUtils {

    /**
     * @param arr  需要排序的数组
     * @param low  开始时最左边的索引=0
     * @param high 开始时最右边的索引=arr.length-1
     */
    public static void quickSort(int[] arr, int low, int high) {
        int i, j, temp, t;
        if (low > high) {
            return;
        }
        //左边哨兵的索引
        i = low;
        //右边哨兵的索引
        j = high;
        //temp就是基准位
        //以最左边为  基准位
        temp = arr[low];

        while (i < j) {
            //先看右边，依次往左递减
            //先从右往左找一个小于 基准位的数
            //当右边的哨兵位置所在的数>基准位的数 时
            //继续从右往左找（同时 j 索引-1）
            //找到后会跳出 while循环
            while (temp <= arr[j] && i < j) {
                j--;
            }

            //再看左边，依次往右递增
            //步骤和上面类似
            while (temp >= arr[i] && i < j) {
                i++;
            }

            //如果满足条件则交换
            if (i < j) {
                arr[i] = arr[i] ^ arr[j];
                arr[j] = arr[i] ^ arr[j];
                arr[i] = arr[i] ^ arr[j];
            }
        }

        //这时 跳出了 “while (i<j) {}” 循环
        //说明 i=j 左右在同一位置
        //最后将基准为与i和j相等位置的数字交换
        //或 arr[low] = arr[j];
        arr[low] = arr[i];
        //或 arr[j] = temp;
        arr[i] = temp;


        //i=j
        //这时  左半数组<(i或j所在索引的数)<右半数组
        //也就是说(i或j所在索引的数)已经确定排序位置， 所以就不用再排序了，
        // 只要用相同的方法 分别处理  左右数组就可以了

        //递归调用左半数组
        quickSort(arr, low, j - 1);
        //递归调用右半数组
        quickSort(arr, j + 1, high);
    }


    public static void main(String[] args) {
        int[] arr = {10, 7, 2, 4, 7, 62, 3, 4, 2, 1, 8, 9, 19};
        quickSort(arr, 0, arr.length - 1);
        for (int value : arr) {
            System.out.print(value+" ");
        }
    }
}
