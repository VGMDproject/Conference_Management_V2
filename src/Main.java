import java.awt.EventQueue;

public final class Main
{
	/**
	 * Private constructor, to prevent instantiation of this class.
	 */
	private Main() {
	}

	/**
	 * The main method, invokes the PowerPaint GUI. Command line arguments are
	 * ignored.
	 * 
	 * @param theArgs Command line arguments.
	 */
	public static void main(final String[] theArgs) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
					new User("").start();
//						User myUser = new User();
//						myUser = myUser.init();
//						myUser.login(myUser);
//						myUser.prompt();
			}
		});
	}
}
