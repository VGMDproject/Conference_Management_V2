import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProgramChair extends User implements java.io.Serializable {

	private User myUser;
	private Conference myConference;
	private int conferenceIndex;
	private final String[] myConferenceOptions1 = {"Create a New Conference"};

	private final String[] myConferenceOptions2 = {"View All Papers",
			"Modify Conference Name",
			"Modify Conference Deadline",
			"Modify Conference Status"
	};

	private final String[] myConferenceOptions3 = {"Assign Paper to Subprogram Chair",
			"View Review",
			"View Recommendation",
			"Accept/Decline Paper"
	};

	public ProgramChair(User theUser, int theConferenceID) {
		super(theUser.getUserName());
		myUser = theUser;
		if(myUser.myConferenceArrayList.size() > 0) {
			conferenceIndex = 0;
			for(Conference localConference : myUser.myConferenceArrayList){
				if(localConference.getIDConference() == theConferenceID) {
					myConference = localConference;
					break;
				}
				conferenceIndex++;
			}
			promptPC();
		} else {
			promptNewConference();
		}
	}

	public void promptPC() {
		System.out.println("\nYou are in Conference: \"" + myConference.getName() + "\"");
		DateFormat deadlineFormat = new SimpleDateFormat ("MM/dd/yyyy 'at' hh:mm:ss");
		System.out.println("Conference deadline: " + deadlineFormat.format(myConference.getDeadline()));
		System.out.println("Conference status: " + (myConference.getStatusConference() == 1 ? "Active" : "Not Active"));
		System.out.println(myUser.mySelectPrompt);
		int optionIndex = 0;
		for(String tempString : myConferenceOptions2) {
			System.out.println(++optionIndex + ") " + tempString);
		}

		for(String tempString : myUser.myBackOutOption) {
			System.out.println(++optionIndex + ") " + tempString);
		}

		int selectedOption = Integer.valueOf(myUser.readConsole());

		switch(selectedOption) {
		case 1:			//View All Papers

			int paperCounter = 0;
			if (myUser.myPaperArrayList.size() > 0) {
				System.out.println("\nList of all papers in conference \"" + myConference.getName() + "\".\nYou can select a paper to go to Paper Management.");
				for(Paper localPaper : myUser.myPaperArrayList) {
					if(localPaper.getConferenceID() == myConference.getIDConference()) {
						System.out.println(++paperCounter + ") " + localPaper.getName() + " by " + localPaper.getUsername());
					}
				}
				
				int optionSelected = paperCounter;
				for(String tempString : myUser.myBackOutOption) {
					System.out.println(++optionSelected + ") " + tempString);
				}

				int selectedPaperIndex = Integer.valueOf(myUser.readConsole());
				int selectedPaperID = -1;
				
				
				//////////////////
				if(selectedPaperIndex <= paperCounter) {
					for(Paper localPaper : myUser.myPaperArrayList) {
						if(localPaper.getConferenceID() == myConference.getIDConference()) {
							selectedPaperIndex--;
							if(selectedPaperIndex == 0) {
								selectedPaperID = localPaper.getID();
							}
						}
					}
					
				Paper selectedPaper = myUser.myPaperArrayList.get(myUser.getPaperIndex(selectedPaperID));
				if(!selectedPaper.getAuthor().getUserName().equals(myUser.getUserName())) {
					System.out.println("You are in Paper: " + selectedPaper.getName());
					System.out.println("Paper Author: " + selectedPaper.getUsername());
					if(selectedPaper.getSubprogramChair() != null && !selectedPaper.getSubprogramChair().getUserName().equals("")) {
						System.out.println("Paper Subprogram Chair: " + selectedPaper.getSubprogramChair().getUserName());			
					} else {
						System.out.println("Paper Subprogram Chair has not been assigned yet.");	
					}
					System.out.println("Paper Status: " + (selectedPaper.getStatusPaper() == 1 ? "Accepted" : "Declined"));

					System.out.println(myUser.mySelectPrompt);
					optionIndex = 0;
					for(String tempString : myConferenceOptions3) {
						System.out.println(++optionIndex + ") " + tempString);
					}

					for(String tempString : myUser.myBackOutOption) {
						System.out.println(++optionIndex + ") " + tempString);
					}

					selectedOption = Integer.valueOf(myUser.readConsole());

					switch(selectedOption) {
					case 1:
						System.out.println("\nChoose a user as the Subprogram Chair for this paper: ");
						optionIndex = 0;
						for(User localUser : myUser.getUserList()) {
							System.out.println(++optionIndex + ") " + localUser.getUserName());
						}

						int selectedUserIndex = Integer.valueOf(myUser.readConsole()) - 1;

						// check to see if author is the same as the SPC
						if(selectedPaper.getAuthor().getUserName().equals(myUser.myUserArrayList.get(selectedUserIndex).getUserName())) {
							System.out.println("Subprogram Chair cannot be the same as the Author.");
							promptPC();
						} else {
							int totalSPCForUser = 0;
							for(Paper localPaper : myUser.myPaperArrayList) {
								if(localPaper.getSubprogramChair() != null && 
										localPaper.getSubprogramChair().getUserName().equals(myUser.myUserArrayList.get(selectedUserIndex).getUserName()) &&
										localPaper.getConferenceID() == myUser.getSelectedConferenceID()) {
									totalSPCForUser++;
								}
							}

							// check to see if the maximum number of SPC has been assigned to the selected user
							if(totalSPCForUser > myUser.MAX_SPC) {
								System.out.println("This user has already been assigned the maximum (" + myUser.MAX_SPC + ") possible papers.");
							} else {
								SubprogramChair localSPC = new SubprogramChair(myUser.myUserArrayList.get(selectedUserIndex), myUser.getSelectedConferenceID());
								myUser.myPaperArrayList.get(myUser.getPaperIndex(selectedPaper.getID())).setSubprogramChair(localSPC);
								myUser.myUserArrayList.get(myUser.getUserIndex(myUser.getUserName())).setSPC();
								myUser.myUpdateSerFilePaper.makeSerialize(myUser.myPaperArrayList);
								myUser.myUpdateSerFileUser.makeSerialize(myUser.myUserArrayList);
								System.out.println("The user " + myUser.myUserArrayList.get(selectedUserIndex).getUserName()  + " has been assigned to the paper \"" +  selectedPaper.getName() + "\" by \"" + selectedPaper.getAuthor().getUsername() + "\" as the Subprogram Chair.");
								promptPC();
							}
						} 
						break;
					case 2:
						if(selectedPaper.getReview() != null && !selectedPaper.getReview().equals("")) {
							System.out.println(selectedPaper.getReview());
						} else {
							System.out.println("No review has been submitted yet.");
						}
						promptPC();	
						break;
					case 3:
						if(selectedPaper.getRecommendation() != null && !selectedPaper.getRecommendation().equals("")) {
							System.out.println("You cannot add recommendation for your own paper.");
							System.out.println("\nRecommendation for the paper \"" + selectedPaper.getName() + "\":");
							System.out.println(selectedPaper.getRecommendation());
						}
						promptPC();
						break;
					case 4:
						if(selectedPaper.getAuthor().getUserName().equals(myUser.getUserName())) {
							System.out.println("You cannot accept/decline your own paper.");
						} else {
							System.out.println("\nChoose the status (0:Decline, 1: Accept) of the paper:");
							int selectedStatus = Integer.valueOf(myUser.readConsole());
							myUser.myPaperArrayList.get(myUser.getPaperIndex(selectedPaper.getID())).setStatusPaper(selectedStatus);
							myUser.myUpdateSerFilePaper.makeSerialize(myUser.myPaperArrayList);
							System.out.println("Paper " + selectedPaper.getName() + " has been " + (selectedStatus == 0 ? "declined" : "accepted") + ".");
						}
						promptPC();
						break;
					case 5:
						promptPC();
						break;
					case 6:
						myUser.logout();
						break;
					}

				} else {
					System.out.println("As a Program Chair, you cannot modify your own paper. Please log in as an Author.");
					promptPC();
				}
				} else {
					switch(selectedPaperIndex - paperCounter) {
					case 1:
						promptPC();
						break;
					case 2:
//						System.out.println("sign out");
						myUser.logout();
					break;
				}
				}
				//////////////////////
				

			} else {
				System.out.println("No paper has been submitted yet.");
			}

			promptPC();
			break;
		case 2:			//Modify Conference Name
			System.out.println("Choose a name for the conference: ");
			String newName = myUser.readConsole();
			myUser.myConferenceArrayList.get(conferenceIndex).setName(newName);
			myUser.myUpdateSerFileConference.makeSerialize(myUser.myConferenceArrayList);
			promptPC();
			break;
		case 3:			//Modify Conference Deadline
			System.out.println("Choose a deadline (MM/DD/YYYY) for the conference: ");
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
			Date selectedDate = new Date();
			String selectedDateString = myUser.readConsole();
			try {
				selectedDate = formatter.parse(selectedDateString);
			} catch (ParseException e) {
				System.out.println("Invalid date format.");
			}
			myUser.myConferenceArrayList.get(conferenceIndex).setDeadline(selectedDate);
			myUser.myUpdateSerFileConference.makeSerialize(myUser.myConferenceArrayList);
			promptPC();
			break;
		case 4:			//Modify Conference Status
			System.out.println("Choose the status (0:Deactive, 1: Active) of the conference:");
			int newStatus = Integer.valueOf(myUser.readConsole());
			myUser.myConferenceArrayList.get(conferenceIndex).setStatusConference(newStatus);
			myUser.myUpdateSerFileConference.makeSerialize(myUser.myConferenceArrayList);
			promptPC();
			break;
		case 5:			//back
			myUser.selectRole();
			myUser.prompt();
			break;
		case 6:			//log out
			myUser.logout();
			break;
		}
	}

	public void promptNewConference() {
		System.out.println(mySelectPrompt);
		if(myUser.myConferenceArrayList.size() == 0) {
			int optionIndex = 0;
			for(String tempString : myConferenceOptions1) {
				System.out.println(++optionIndex + ") " + tempString);
			}

			for(String tempString : myUser.myBackOutOption) {
				System.out.println(++optionIndex + ") " + tempString);
			}

			int selectedIndex = Integer.valueOf(myUser.readConsole());
			switch(selectedIndex) {
			case 1:			//Create a New Conference
				System.out.println("Choose a name for the new conference: ");
				String selectedName = myUser.readConsole();
				System.out.println("Choose a deadline (MM/DD/YYYY) for the new conference: ");
				SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
				Date selectedDate = new Date();
				String selectedDateString = myUser.readConsole();
				try {
					selectedDate = formatter.parse(selectedDateString);
				} catch (ParseException e) {
					System.out.println("Invalid date format.");
				}

				Conference newConference = new Conference();
				newConference.setConferenceID(myUser.myConferenceArrayList.size() + 1);
				newConference.setName(selectedName);
				newConference.setDeadline(selectedDate);
				newConference.setStatusConference(1);
				myUser.myConferenceArrayList.add(newConference);
				myUser.myUpdateSerFileConference.makeSerialize(myUser.myConferenceArrayList);
				System.out.println("Conference '" + newConference.getName() + "' was added.");
				break;
			case 2:			//back
				myUser.prompt();
				break;
			case 3:			//log out
				myUser.logout();
				break;
			}
		} 
	}

	private void makeConference() {
		String tempConferenceName = myUser.readConsole();
		new Conference();
	}

	private void setStatusConference() {

	}
	private void setSubprogramchair() {

	}
	private void setStatusPaper() {

	}
	private void viewPapers() {

	}
}
