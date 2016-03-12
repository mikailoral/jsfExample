package spring.dao;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PasswordHasher hasher = new PasswordHasher();
		String hash = hasher.createHash("123456");
//		String hash = hasher.createHash("e10adc3949ba59abbe56e057f20f883e");
		System.out.println(hash);
		boolean bool = hasher.isMatch("123456", "$P$Bju8EqIApVYpkQ3l3W7QezSIDWD2q51");
		System.out.println(bool);
	}

}
