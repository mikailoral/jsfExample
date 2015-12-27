package spring.dao;


public class test {

	public static void main(String[] args) {		
		
		// verilen string deger
		String str = "abbcccaaeeeeb bfffffca ccab";
		char[] dizi = str.toCharArray();   // karakterleri degistirmek icin tanimlanmis dizi
		System.out.println(dizi);
		int tekrar = 4;    // tekrar parametresi
		for(int i = 0 ; i < str.length() - 1 ; i++ ){
			int c = control(str, i);
			if((c - i) >= (tekrar - 1 )){
				System.out.println("tekrar var : "  + str.charAt(i)  + (c - i));
				
				// tekrar eden karakter yerine * karakteri koymak icin yazildi.
				for(int j = 0 ; j < (c- i + 1) ; j++){
					dizi[i+ j] = '*';
				}
				i = c;  // bos yere for donmesin diye bir atama yapilir.
			}
		}
		
		// sonuc yazdirilir
		for (int i = 0; i < dizi.length; i++) {
			System.out.print(dizi[i]);
		}
		
		
	}
	/**
	 * recursive fonksiyon kendisine verilen stringdeki indexden 
	 * sonrasi kacinci indexe kadar ayni harf oldugunu dondurur.
	 * @param str
	 * @param index
	 * @return
	 */
	public static int  control(String str , int index){
		if( str.charAt(index) == str.charAt(index + 1 )){
			return control(str  , index + 1);
		}else{
			return index;
		}
	}
}
