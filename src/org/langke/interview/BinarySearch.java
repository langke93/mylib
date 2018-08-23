package org.langke.interview;

import java.util.ArrayList;
import java.util.List;
/**
*名称:BinarySearch
*功能:实现了折半查找(二分查找)的递归和非递归算法.
*说明:
*     1、要求所查找的数组已有序,并且其中元素已实现Comparable<T>接口,如Integer、String等.
*    2、非递归查找使用search();,递归查找使用searchRecursively();
*/

public class BinarySearch <T extends Comparable<T>>{
	/**
	 * 二分查找算法
	 *  二分查找算法就是不断将数组进行对半分割，每次拿中间元素和goal进行比较
	 *  适用于不经常变动而查找频繁的有序列表
	 * 
	 * @param srcArray
	 *            有序数组
	 * @param des
	 *            查找元素
	 * @return des的数组下标，没找到返回-1
	 */
	
	  private T[]  data;//要排序的数据
	  
	  public BinarySearch() {
    	}
	  public BinarySearch(T[] data){
	        this.data = data;
	    }
	  
	  /**
	   * 循环查找
	   * @param key
	   * @return
	   */
	    public int binarySearch(T key){
	        int low;
	        int high;
	        int mid;

	        if(data == null)
	            return -1;

	        low = 0;
	        high = data.length - 1;

	        while(low <= high){
	            mid = (low + high) / 2;
	            System.out.println("mid " + mid + " mid value:" + data[mid]);///
	            
	            if(key.compareTo(data[mid]) < 0){
	                high = mid - 1;
	            }else if(key.compareTo(data[mid]) > 0){
	                low = mid + 1;
	            }else if(key.compareTo(data[mid]) == 0){
	                return mid;
	            }
	        }

	        return -1;
	    }

	    /**
	     * @param low
	     * @param high
	     * @param key
	     * @return
	     */
	    private int binarySearch(int low , int high , T key){
	        int mid;
	        int result;

	        if(low <= high){
	            mid = (low + high) / 2;
	            result = key.compareTo(data[mid]);
	            System.out.println("mid " + mid + " mid value:" + data[mid]);///
	            
	            if(result < 0){
	                return binarySearch(low , mid - 1 , key);
	            }else if(result > 0){
	                return binarySearch(mid + 1 , high , key);
	            }else if(result == 0){
	                return mid;
	            }
	        }
	        
	        return -1;
	    }

	    /// 递归查找
	    public int searchRecursively(T key){
	        if(data ==null)
	        	return -1;
	        return binarySearch(0 , data.length - 1 , key);
	    }

	    
	public static int binarySearch(int[] srcArray, int des) {
		int low = 0;
		int high = srcArray.length - 1;
		while (low <= high) {
			int middle = (low + high) / 2;
			if (des == srcArray[middle]) {
				return middle;
			} else if (des < srcArray[middle]) {
				high = middle - 1;
			} else {
				low = middle + 1;
			}
		}
		return -1;
	}


	int deep = 0;
	
	public Integer binarySearch(List<Integer> list,Integer target){
		if(list.size() == 1 && list.get(0) != target)
			return null;
		int size = list.size();
		Integer middle = list.get(size/2);
		if(target < middle){
			list = list.subList(0, size/2);
			return binarySearch(list, target);
		}else if(target > middle){
			list = list.subList(size/2,list.size());
			return binarySearch(list, target);
		}else{
			return middle;
		}
	}
	
	public static void main(String[] args) {
		int[] src = new int[] { 1, 3, 5, 7, 8, 9 };
		System.out.println(binarySearch(src, 3));
		List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(3);
		list.add(5);
		list.add(7);
		list.add(9);
		list.add(9);
		System.out.println(new BinarySearch().binarySearch(list, 9));
	}
	
	
}
