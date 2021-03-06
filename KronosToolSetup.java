import java.io.*;
import java.util.*;
public class KronosToolSetup 
{ 

    public static void main(String...args)
    {
    	try{
	        BufferedReader reader = new BufferedReader(new FileReader(new File("C:\\a.txt")));
	        String[] strArr=reader.readLine().split(",");
	        String[]tempArr=strArr[0].split("#");
	        int J=Integer.parseInt(tempArr[0]);
	        int R=Integer.parseInt(tempArr[1]);
	        int[][] dataArr=new int[J+1][J+1];
	        ArrayList<String> routeNumber=new ArrayList<>();
	        routeNumber.add(".");
	        for(int i=1;i<=R;i++){
	            tempArr=strArr[i].split("#");
	            int start=Integer.parseInt(tempArr[0]);
	            int end=Integer.parseInt(tempArr[1]);
	            int cost=Integer.parseInt(tempArr[2]);
	            dataArr[start][end]=cost;
	            routeNumber.add(start+"#"+end);
	        }
	        solution(dataArr, J+1, J+1,R,routeNumber);
	        
    	}catch(Exception e){
    		e.printStackTrace();
    		}
    }
    
    static void solution(int[][] dataArr,int M,int N,int R,ArrayList<String> routeNumber){
    	ArrayList<ArrayList<Integer>> list=new ArrayList<>();
    	for(int a=1;a<N;a++){
    		if(dataArr[1][a]!=0){
    			ArrayList<Integer> route=new ArrayList<>();
    			route.add(1);
    			route.add(a);
    			list.add(route);
    			getPaths(dataArr,M,N,a,route,list);
    		}
    	}
    	int MAX=0;
    	for(ArrayList<Integer> a:list){
    		int cost=getPathCost(a, dataArr);
    		if(MAX<cost)MAX=cost;
    	}
    	int m=0;
    	boolean[] visited=new boolean[R];
    	String[] solutionArr=new String[R];
    	for(ArrayList routeToCheck:list){
    		checkToll(dataArr,list,MAX,routeToCheck,routeToCheck.size(),visited,routeNumber,solutionArr);
    	}
    	int sizeofarray=0;
    	for(String s:solutionArr)
    		if(s!=null)sizeofarray++;
    	String[] finalArrar=new String[sizeofarray+1];
    	finalArrar[0]=sizeofarray+"#"+MAX;
    	int j=1;
    	for(int i=0;i<solutionArr.length;i++){
    		if(solutionArr[i]!=null)finalArrar[j++]=i+"#"+solutionArr[i];
    	}
/*    	for(String s:finalArrar)
    		System.out.println(s);
    	System.out.println("Hello");
*/    }
    static void getPaths(int[][] dataArr,int M,int N,int j,ArrayList<Integer> list,ArrayList<ArrayList<Integer>> array){
    	int local=0;
    		for(int b=1;b<N;b++){
    			if(dataArr[j][b]!=0){
    				if(local++!=0){
    					ArrayList<Integer> copiedList = new ArrayList<>();
    					for(int kk=0;kk<list.size()-2;kk++)
    						copiedList.add(list.get(kk));
    					copiedList.add(b);
    					array.add(copiedList);
	    				getPaths(dataArr, M, N, b, copiedList,array);
    				}else{
	    				list.add(b);
	    				getPaths(dataArr, M, N, b, list,array);
    				}
    			}
    		}
    }
    
    static void checkToll(int[][] dataArr,ArrayList<ArrayList<Integer>> routes,int MAX,ArrayList<Integer> routetocheck,int i,boolean[] routeDone,ArrayList<String> routeNumber,String[] solutionArr){
    	while(!routeDone[routes.indexOf(routetocheck)] &&i>1){
    		int start=routetocheck.get(i-2);
    		int end=routetocheck.get(i-1);
    		int previousValue=getPathCost(routetocheck, dataArr);
    		int diff=MAX-previousValue;
    		dataArr[start][end]+=diff;
    		if(!checkValid(dataArr,routes,MAX,start,end)){
    			dataArr[start][end]-=diff;
    			checkToll(dataArr,routes,MAX,routetocheck,i-1,routeDone,routeNumber,solutionArr);
    		}
    		else{
    			routeDone[routes.indexOf(routetocheck)]=true;
    			if(diff>0)
    			solutionArr[routeNumber.indexOf(start+"#"+end)]=diff+"";
//    			System.out.println(start+","+end+","+diff);
    		}
    	}
    	
    }
    
    static boolean checkValid(int[][] dataArray,ArrayList<ArrayList<Integer>> routes,int MAX,int start,int end){
       	for(ArrayList<Integer> a:routes){
       		if(a.contains(start) && a.contains(end)){
	    		int cost=getPathCost(a, dataArray);
	    		if(cost!=MAX) return false;
       		}
       	}
       	return true;
 
    }
    static int getPathCost(ArrayList<Integer> path,int[][] dataArray){
    	int cost=0;
    	for(int i=0;i<path.size()-1;i++){
			cost+=dataArray[path.get(i)][path.get(i+1)];
		}
    	return cost;
    }
    static int getIndex(ArrayList<Integer>[] array){
    	for(int i=0;i<array.length;i++){
    		if(array[i]==null)return i;
    	}
    	return 0;
    }
}
