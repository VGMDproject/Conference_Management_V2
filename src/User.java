import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.JFileChooser;

public class User implements java.io.Serializable {

	private static final long serialVersionUID = 518192311986499400L;
	public final String PAPER_FILE = "paper";
	public final String REVIEW_FILE = "review";
	public final String RECOMMENDATION_FILE = "recommendation";
	public final String USER = "User";
	public final String AUTHOR = "Author";
	public final String REVIEWER = "Reviewer";
	public final String SPC = "Subprogram Chair";
	public final String PC = "Program Chair";
	public final String DEFAULT_ROLE = PC;
	public final String[] myOutOption = {"Log Out"};
	public final String[] mySubmitBackOutOption = {"Submit a Paper", "Back", "Log Out"};
	public final String[] myBackOutOption = {"Back", "Log Out"};
	public final String mySelectPrompt = "\nSelect from the following options:";
	public final int MAX_REVIEW = 4;
	public final int MAX_SPC = 4;
	
	public ArrayList<Object> myObjectArrayList; 
	private ArrayList<String> roleArrayList;
	public ArrayList<Conference> myConferenceArrayList; 
	public ArrayList<User> myUserArrayList; 
	public ArrayList<Paper> myPaperArrayList; 

	public UpdateSerFile myUpdateSerFileUser;
	public UpdateSerFile myUpdateSerFilePaper;
	public UpdateSerFile myUpdateSerFileConference;
	
	private Paper myPaper;
	private Conference myConference;
	private String myName;
	private String myRole;
	
	private boolean myIsPC;
	private boolean myIsSPC;
	private boolean myIsReviewer;
	private boolean myIsAuthor;
	private boolean myIsUser;
	
	private transient Scanner input;
	private String myUserName;
	private int selectedConferenceID;

	public User(String theUserName) {
		input = new Scanner(System.in);
		if(theUserName != null && !theUserName.equals("")) {
			myUserName = theUserName;
		}
		init();
	}

	public void start() {
		login();
		selectRole();
		prompt();
	}

	public void init() {
		input = new Scanner(System.in);
		myPaper = new Paper();
		myConference = new Conference();

		myUpdateSerFileUser = new UpdateSerFile(this);
		myUpdateSerFilePaper = new UpdateSerFile(myPaper);
		myUpdateSerFileConference = new UpdateSerFile(myConference);

		myUserArrayList = new ArrayList<User>();
		myPaperArrayList = new ArrayList<Paper>();
		myConferenceArrayList = new ArrayList<Conference>();
		roleArrayList = new ArrayList<String>();
		
		selectedConferenceID = -1;

		try {
			if((ArrayList<User>) myUpdateSerFileUser.deserialize() != null) {
				myUserArrayList = (ArrayList<User>) myUpdateSerFileUser.deserialize();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			if((ArrayList<Paper>) myUpdateSerFilePaper.deserialize() != null) {
				myPaperArrayList = (ArrayList<Paper>) myUpdateSerFilePaper.deserialize();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			if((ArrayList<Conference>) myUpdateSerFileConference.deserialize() != null) {
				myConferenceArrayList = (ArrayList<Conference>) myUpdateSerFileConference.deserialize();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		myIsPC = false;
		myIsSPC = false;
		myIsReviewer = false;
		myIsAuthor = false;
		myIsUser = true;
	}

	public void login() {
		User matchedUser = null;
		System.out.print("\nEnter your username to login or choose a username to register: ");
		setUserName(readConsole());

		for(User localUser : myUserArrayList) {
			if(localUser.myUserName != null && localUser.myUserName.equals(getUserName())){
				matchedUser = localUser;
				break;
			}
		}

		if(matchedUser != null) {
			setUserName(matchedUser.getUserName());
			setName(matchedUser.getName());
			setRole(USER);
			System.out.println("Logged in as " + matchedUser.getUserName() + " [User]");
			selectedConferenceID = selectConference();
			if(matchedUser.isUser()) {
				setUser();
			}

			if(matchedUser.isPC()) {
				setPC();
			}

			if(matchedUser.isSPC()) {
				setSPC();
			}

			if(matchedUser.isReviewer()) {
				setReviewer();
			}

			setAuthor();

		} else {
			register(DEFAULT_ROLE);
		}
	}

	public void logout() {
		start();
	}

	public void prompt() {
		System.out.println("Logged in as " + getUserName() + " [" + getRole() + "]");

		if(selectedConferenceID > 0) {
			if(getRole().equals(USER)) {
				selectConference();
				selectRole();
				prompt();
			} else if(getRole().equals(AUTHOR)) {
				Author author = new Author(this);
				author.promptAuthor();
			} else if(getRole().equals(REVIEWER)) {
				new Reviewer(this, selectedConferenceID);
			} else if(getRole().equals(SPC)) {
				new SubprogramChair(this, selectedConferenceID);
			} else if(getRole().equals(PC)) {
				new ProgramChair(this, selectedConferenceID);
			} 
		} 
	}

	public void selectRole() {
		roleArrayList = new ArrayList<String>();
		
		if(isUser()) {
			roleArrayList.add(USER);
		}

		if(isPC()) {
			roleArrayList.add(PC);
		}

		if(isSPC()) {
			roleArrayList.add(SPC);
		}

		if(isReviewer()) {
			roleArrayList.add(REVIEWER);
		}

		if(isAuthor()) {
			roleArrayList.add(AUTHOR);
		}
		
		
		System.out.println(mySelectPrompt);	
		int roleCounter = 0;
		for(String tempString : roleArrayList) {
			if(!tempString.equals(USER)) {
				System.out.println(++roleCounter + ") Log in as " + tempString);
			}
		}

		for(String tempString : myBackOutOption) {
			System.out.println(++roleCounter + ") " + tempString);
		}

		int selectedOption = Integer.valueOf(readConsole());
		if(selectedOption < roleArrayList.size()) {
			int j = 0;
			for(int i = 0; i < selectedOption; i++) {
				if(roleArrayList.get(j).equals(USER)) {
					i--;
				}
				setRole(roleArrayList.get(j));
				j++;
			}
			

		} else {
//			System.out.println("is less than");
//			for(int i = 1; i <= myBackOutOption.length; i++) {
				//				if(selectedOption == myConferenceArrayList.size() + i) {
				//
				//					if(selectedConferenceID > 0) {
				//						new Paper(this, myPaperArrayList.size(), selectedConferenceID);
				//					}
				//					break;
				//				}

//				System.out.println(roleArrayList.size() + " " + (roleArrayList.size()-1+i));

				switch(selectedOption - (roleArrayList.size() - 1)) {
				case 1:
//					System.out.println("back");
					selectedConferenceID = selectConference();
					selectRole();
//					System.out.println("fuck2");
					break;
				case 2:
//					System.out.println("sign out");
					logout();
				break;
				
				}
				
//			}
		}
	}

	public int selectConference() {
		int selectedOption = -1;
		if(myConferenceArrayList.size() > 0) {
			int conferenceCounter = 0;
			System.out.println(mySelectPrompt);
			for(Conference tempConference : myConferenceArrayList) {
				System.out.println(++conferenceCounter + ") View Conference \"" + tempConference.getName() + "\"");
			}

			for(String tempString : myOutOption) {
				System.out.println(++conferenceCounter + ") " + tempString);
			}

			selectedOption = Integer.valueOf(readConsole());

			for(int i = 0; i < myOutOption.length; i++) {
				//				if(selectedOption == myConferenceArrayList.size() + i) {
				//					prompt();
				//					break;
				//				}

				if(selectedOption == myConferenceArrayList.size() + i + 1) {
					logout();
					break;
				}
			}
//			System.out.println("asked for index" + selectedOption);
			selectedOption = myConferenceArrayList.get(selectedOption - 1).getIDConference();
		} else {
			//			System.out.println(getRole());
			if(getRole().equals(PC)) {
				System.out.println("No conference has been made yet.");
				new ProgramChair(this, selectedConferenceID);
			} else {
				System.out.println("No conference has been made yet. Please come back later.");
				logout();
			}
		}

		return selectedOption;
	}
	
	

	public void register(final String theRole) {
		System.out.print("Enter your name: ");
		//		input = new Scanner(System.in);
		//		while(!input.hasNext()) {
		//		}
		setName(readConsole());
		setRole(theRole);

		if(theRole.equals(PC)) {
			setPC();
		} else if(theRole.equals(SPC)) {
			setSPC();
		} else if(theRole.equals(REVIEWER)) {
			setReviewer();
		} else if(theRole.equals(AUTHOR)) {
			setAuthor();
		} 

		myUserArrayList.add(this);
		myUpdateSerFileUser = new UpdateSerFile(this);

		/////////////////////////////////
		myUpdateSerFileUser.makeSerialize(myUserArrayList);
		////////////////////////////////
	}

//	public void submitPaper() {
//		int paperID = 1;
//		System.out.println("Enter the title of the paper: ");
//		myPaper.setName(readConsole());
//		System.out.println("Enter the text of the paper: ");
////		myPaper.setText(readConsole());
//		setAuthor();
//		Author myAuthor = new Author(this);
//
//		myPaper.setID(myPaperArrayList.size());
//		myPaper.setAuthor(myAuthor);
//		myPaper.setStatusPaper(0);
//		//		myPaper.setConference(myConference.getID());
//	}

public String chooseFile(String thePath) {
//	JFileChooser chooser = new JFileChooser();
//	int retval = JFileChooser.CANCEL_OPTION;
//	chooser.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY);
//	retval = chooser.showDialog(null, "Select the file");
	File file = null;
	JFileChooser fileChooser = new JFileChooser();
	if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
	   file = fileChooser.getSelectedFile();
	}
	return file.getName();
}

public int getPaperIndex(int theID) {
	int index = -1;
	for(int i = 0; i < myPaperArrayList.size(); i++) {
		if(myPaperArrayList.get(i).getID() == theID) {
			index = i;
			break;
		}
	}
	return index;
}

public int getUserIndex(String theUserName) {
	int index = -1;
	for(int i = 0; i < myUserArrayList.size(); i++) {
		if(myUserArrayList.get(i).getUserName().equals(theUserName)) {
			index = i;
			break;
		}
	}
	return index;
}

	public void makeConferenceArrayList(){
	}

	public ArrayList<Conference> getConferenceList() {
		return myConferenceArrayList;
	}

	public void makePaperArrayList(){
	}

	public ArrayList<Paper> getPaperList() {
		return myPaperArrayList;
	}

	public void makeUsersArrayList(){
	}

	public ArrayList<User> getUserList() {
		return myUserArrayList;
	}

	public String getUserName() {
		return myUserName;
	}

	public String getName() {
		return myName;
	}

	public String getRole() {
		return myRole;
	}

	public void setRole(String theRole) {
		myRole = theRole;
	}

	public void setUserName(String theUserName) {
		myUserName = theUserName;
	}

	public String readConsole() {
		//		input = new Scanner(System.in);
		while(!input.hasNext()) {
		}

		String returnDate = input.next();
		//		input.close();
		return returnDate;
	}

	public void setUser() {
		myIsUser = true;
	}

	public void setPC() {
		myIsPC = true;
	}

	public void setSPC() {
		myIsSPC = true;
	}

	public void setReviewer() {
		myIsReviewer = true;
	}

	public void setAuthor() {
		myIsAuthor = true;
	}

	public boolean isUser() {
		return myIsUser;
	}

	public boolean isPC() {
		return myIsPC;
	}

	public boolean isSPC() {
		return myIsSPC;
	}

	public boolean isReviewer() {
		return myIsReviewer;
	}

	public boolean isAuthor() {
		return myIsAuthor;
	}

	public void setName(String theName) {
		myName = theName;
	}

	public int getSelectedConferenceID() {
		return selectedConferenceID;
	}
}
