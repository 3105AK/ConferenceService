package com.brawlstars.ConferenceService;

import com.brawlstars.ConferenceService.models.Comment;

import java.util.ArrayList;

public class CommentSort {
    private static ArrayList<Comment> arr;
    private static int size;
    public static ArrayList<Comment> quickSort(ArrayList<Comment> array){
        arr = array;
        size = array.size();
        recQuickSort(0, size-1);
        return arr;
    }
    private static void recQuickSort(int left, int right){
        if (right-left <= 2){
            manualSort(left, right);
        }
        else{
            int median =  medianOf3(left, right);//value
            int partition = partition(left, right, median);//index
            recQuickSort(left, partition-1);
            recQuickSort(partition+1, right);
        }
    }
    private static void manualSort(int left, int right){
        if (right<=left) return;
        else if (right-left==1){
            if (arr.get(left).getLikes()<arr.get(right).getLikes()||(arr.get(left).getLikes()==arr.get(right).getLikes()&&arr.get(left).getCreatedTime()<arr.get(right).getCreatedTime()))
                swap(left, right);
        }
        else{
            if(arr.get(left).getLikes()<arr.get(left+1).getLikes()||(arr.get(left).getLikes()==arr.get(right).getLikes()&&arr.get(left).getCreatedTime()<arr.get(right).getCreatedTime()))
                swap(left, left+1);
            if(arr.get(left+1).getLikes()<arr.get(right).getLikes()||(arr.get(left).getLikes()==arr.get(right).getLikes()&&arr.get(left).getCreatedTime()<arr.get(right).getCreatedTime()))
                swap(left+1, right);
            if(arr.get(left).getLikes()<arr.get(left+1).getLikes()||(arr.get(left).getLikes()==arr.get(right).getLikes()&&arr.get(left).getCreatedTime()<arr.get(right).getCreatedTime()))
                swap(left, left+1);
        }
    }
    private static int medianOf3(int left, int right){
        int center = (left+right)/2;
        if(arr.get(left).getLikes()<arr.get(center).getLikes()||(arr.get(left).getLikes()==arr.get(right).getLikes()&&arr.get(left).getCreatedTime()<arr.get(right).getCreatedTime()))
            swap(left, center);
        if(arr.get(center).getLikes()<arr.get(right).getLikes()||(arr.get(left).getLikes()==arr.get(right).getLikes()&&arr.get(left).getCreatedTime()<arr.get(right).getCreatedTime()))
            swap(center, right);
        if(arr.get(left).getLikes()<arr.get(center).getLikes()||(arr.get(left).getLikes()==arr.get(right).getLikes()&&arr.get(left).getCreatedTime()<arr.get(right).getCreatedTime()))
            swap(left, center);
        swap(center, right-1);
        return arr.get(right-1).getLikes();
    }
    private static int partition(int left, int right, int pivot){
        int LeftPtr = left;
        int RightPtr = right-1;
        while (true){
            while (arr.get(++LeftPtr).getLikes()<pivot);
            while (arr.get(--RightPtr).getLikes()>pivot);
            if(LeftPtr>=RightPtr) break;
            swap(LeftPtr, RightPtr);
        }
        swap(LeftPtr, right-1);
        return LeftPtr;
    }
    private static void swap(int ix1, int ix2){
        Comment temp = arr.get(ix1);
        arr.set(ix1, arr.get(ix2));
        arr.set(ix2, temp);
    }
}
