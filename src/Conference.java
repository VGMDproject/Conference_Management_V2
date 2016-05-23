import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 */

/**
 * @author Gabriela
 *
 */
public class Conference implements java.io.Serializable {
	
	private int myConferenceID;

	//a list of all papers with conferenceID == myConferenceID
	private ArrayList<Integer> myPaperList;

	//the name of the conference
	private String myName;

	//will be set to 1. 0 means conference is not active anymore
	private int myStatus; 

	//make sure you use Date datatype
	private Date myDeadline;
	
	private User myUser;
	
	private int myConferenceIndex;
	


	public Conference() {
	}
	
	//constructor 	
	public Conference(User theUser, int theConferenceID) {
		myUser = theUser;
		myConferenceID = theConferenceID;

	}
	
	public int getIndexConference(int theID) {
		int index = -1;
		for(int i = 0; i < myUser.myConferenceArrayList.size(); i++) {
			if(myUser.myConferenceArrayList.get(i).getIDConference() == myConferenceID) {
				index = i;
				break;
			}
		}
		return index;
	}

	public void setConferenceID(int theConferenceID) {
		myConferenceID = theConferenceID;
	}

	//set the name of the conference. name is the same as title
	public void setName(String theName) {
		myName = theName;
	}
	
	public void setStatusConference(int theStatus) {
		myStatus = theStatus;
	}

	public void setDeadline(Date theDeadline) {
		myDeadline = theDeadline;
	}

	public String getName() {
		return myName;
	}
	
	public Date getDeadline() {
		return myDeadline;
	}
	
	public int getStatusConference() {
		return myStatus;
	}
	
	public int getIDConference() {
		return myConferenceID;
	}
	
//	public void setIndexConference() {
//		return myConferenceID;
//	}
//	
//	public void getIndexConference() {
//		return myConferenceIndex;
//	}
	
	

	//this is how you add a conference
	public void submitConference()  {
		
	}

}
