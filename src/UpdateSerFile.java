import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class UpdateSerFile implements java.io.Serializable {
	private String fileName = null;
	private Object myObject = null;
	private Object myReturnObject = null;
	
	
	
	public UpdateSerFile(Object theObject) {
		myObject = theObject;
		if(myObject instanceof Paper) {
			fileName = "paper.ser";
		} else if(myObject instanceof User) {
			fileName = "user.ser";
		} else if(myObject instanceof Conference) {
			fileName = "conference.ser";
		} 
//		makeSerialize();
//		deserialize();
	}

	/**
	 * you know this
	 */
	public void makeSerialize(Object theObject) {
		//		System.out.println("size=" + mm.size());
		//		for(User ss : mm) {
		//			System.out.println(ss.getName()  + " " + ss.getUserName());
		//		}

//		User localUser = (User)theObject;
		
//		ArrayList<User> ll = new ArrayList<User>();
//		if((ArrayList<User>) theObject != null) {
//			ll = (ArrayList<User>) theObject;
//		}
//		System.out.println("size=" + ll.size());
//
//		for(User s : ll) {
//			System.out.println(s.getName());
//		}

		try {
			//			 System.out.println("ff  " + mm.size());
			FileOutputStream fileOut = new FileOutputStream(fileName);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(theObject);
			out.close();
			fileOut.close();
		} catch(IOException i) {
			i.printStackTrace();
		}
		//		  return myObject;
	}
	
	/**
	 * you better know this
	 */
	public Object deserialize() throws ClassNotFoundException {
//		ArrayList<User> returnArrayList = new ArrayList<User>();
		try
	      {
	         FileInputStream fileIn = new FileInputStream(fileName);
//	         try{
	        	 ObjectInputStream in = new ObjectInputStream(fileIn);
	        	 myReturnObject = in.readObject();
//			System.out.println(myReturnObject.getClass());
	        	 in.close();
//	         } catch(StreamCorruptedException sc) {
////	        	 System.out.println("Invalid data!");
//	         } catch(EOFException sc) {
////	        	 System.out.println("No data available!");
//	         } catch(IOException i) {
	        	 
//	         } 
	         fileIn.close();
	      }catch(IOException i) {
//	         i.printStackTrace();
//	    	  System.out.println("FAIL1");
	      }catch(ClassNotFoundException c)
	      {
//	    	  System.out.println("FAIL2");
//	    	  c.printStackTrace();
	      }

//		ArrayList<User> mm = (ArrayList<User>) myReturnObject;
//		if(mm!=null){
//		for(User ss : mm) {
//			System.out.println(ss.getName()  + " ,  " + ss.getUserName());
//		}
//		}
		return myReturnObject;
		
//		System.out.println("Deserialized User...");
//		System.out.println("Name: " + myObject);
//		System.out.println("User name: " + what.myUserName);
//		System.out.println("Role: " + what.myRole);
	}
	
}
