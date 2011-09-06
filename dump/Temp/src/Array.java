
public class Array {
	public static void main(String args[]){
		int [] a= new int[]{1,2,3};
		int []b=a.clone();
		b[0]=3;
		System.out.println(a[0]+" "+b[0]+" "+b[1]);
	}
}
