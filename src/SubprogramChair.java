public class SubprogramChair extends User implements java.io.Serializable {

	private User myUser;
	private Conference myConference;
	private int conferenceIndex;
	private final String[] mySPCOptions1 = {"Assign a Reviewer",
													"Submit Recommendation"};
	
	public SubprogramChair(User theUser, int theConferenceID) {
		super(theUser.getUserName());
		myUser = theUser;
		if(myUser.myConferenceArrayList.size() > 0) {
			promptSPC();
		} else {
			System.out.println("No conference has beet created yet.");
			myUser.prompt();
		}
	}
	
	public void promptSPC() {
		int optionIndex = 0;
		System.out.println("\nSelect a paper to go to Paper Management.");
		for(Paper localPaper : myUser.myPaperArrayList) {
			if(localPaper.getSubprogramChair() != null && localPaper.getSubprogramChair().getUserName().equals(myUser.getUserName())) {
				System.out.println(++optionIndex + ") " + localPaper.getName() + " by " + localPaper.getAuthor().getUserName());
			}
		}
		
		if(optionIndex > 0) {
			int selectedPaperIndex = Integer.valueOf(myUser.readConsole());
			int selectedPaperID = -1;
			for(Paper localPaper : myUser.myPaperArrayList) {
				if(localPaper.getSubprogramChair() != null && localPaper.getSubprogramChair().getUserName().equals(myUser.getUserName())) {
					selectedPaperIndex--;
					if(selectedPaperIndex == 0) {
						selectedPaperID = localPaper.getID();
					}
				}
			}
			
			System.out.println(myUser.mySelectPrompt);
			optionIndex = 0;
			for(String tempString : mySPCOptions1) {
				System.out.println(++optionIndex + ") " + tempString);
			}
			
			for(String tempString : myUser.myBackOutOption) {
				System.out.println(++optionIndex + ") " + tempString);
			}

			int selectedOption = Integer.valueOf(myUser.readConsole());
			
			switch(selectedOption) {
			case 1:			//Assign a Reviewer
				
				System.out.println("\nChoose a user as the Reviewer for this paper: ");
				optionIndex = 0;
				for(User localUser : myUser.getUserList()) {
					System.out.println(++optionIndex + ") " + localUser.getUserName());
				}
				
				int selectedUserIndex = Integer.valueOf(myUser.readConsole()) - 1;
				
					// check to see if author is the same as the reviewer
					if(myUser.myPaperArrayList.get(selectedPaperIndex).getAuthor().getUsername().equals(myUser.myUserArrayList.get(selectedUserIndex).getUserName())) {
						System.out.println("A reviewer cannot be the same as the Author.");
						promptSPC();
					} else {
						int totalReviewForSelectedUser = 0;
						for(Paper localPaper : myUser.myPaperArrayList) {
							if(localPaper.getReviewer() != null && localPaper.getReviewer().getUserName().equals(myUser.myUserArrayList.get(selectedUserIndex).getUserName())) {
								totalReviewForSelectedUser++;
							}
						}
				
						// check to see if the maximum number of review has been assigned to the selected user
						if(totalReviewForSelectedUser > myUser.MAX_REVIEW) {
							System.out.println("This user has already been assigned the maximum (" + myUser.MAX_REVIEW + ") possible papers.");
						} else {
							Reviewer localReviewer = new Reviewer(myUser.myUserArrayList.get(selectedUserIndex), myUser.getSelectedConferenceID());
							myUser.myPaperArrayList.get(myUser.getPaperIndex(selectedPaperID)).setReviewer(localReviewer);
							myUser.myUserArrayList.get(myUser.getUserIndex(myUser.getUserName())).setReviewer();
							myUser.myUpdateSerFilePaper.makeSerialize(myUser.myPaperArrayList);
							myUser.myUpdateSerFileUser.makeSerialize(myUser.myUserArrayList);
							System.out.println("The user " + myUser.myUserArrayList.get(selectedUserIndex).getUserName()  + " has been assigned to the paper \"" +  myUser.myPaperArrayList.get(myUser.getPaperIndex(selectedPaperID)).getName() + "\" by \"" + myUser.myPaperArrayList.get(myUser.getPaperIndex(selectedPaperID)).getAuthor().getUsername() + "\" as the Reviewer.");
							promptSPC();
						}
					} 
				break;
			case 2:			//Submit Recommendation
				System.out.println("Add your recommendation for the paper " + myUser.myPaperArrayList.get(myUser.getPaperIndex(selectedPaperID)).getName() + ": ");
				myUser.myPaperArrayList.get(myUser.getPaperIndex(selectedPaperID)).submitRecommendation(myUser.readConsole());
				myUser.myUpdateSerFilePaper.makeSerialize(myUser.myPaperArrayList);
				System.out.println("The recommendation was submitted.");
				promptSPC();
				break;		
			case 3:			//back
				promptSPC();
				break;
			case 4:			//log out
				myUser.logout();
				break;
			}
		} else {
			System.out.println("No paper has been assigned to you in this conference.");
			myUser.prompt();
		}
	}

	public void setReviewer() {

	}
	
	public void submitRecommendation(){

	}

}

//if(selectedPaper.getAuthor().getUserName().equals(myUser.getUserName())) {
//	System.out.println("You cannot add recommendation for your own paper.");
//} else {
//	System.out.println("\nAdd your recommendation: ");
//	myUser.myPaperArrayList.get(myUser.getPaperIndex(selectedPaper.getID())).submitRecommendation(myUser.readConsole());
//	myUser.myUpdateSerFilePaper.makeSerialize(myUser.myPaperArrayList);
//	System.out.println("Your recommendation for the paper \"" + selectedPaper.getName() + "\" has been submitted.");



//case 1:
//	System.out.println("\nChoose a user as the Subprogram Chair for this paper: ");
//	optionIndex = 0;
//	for(User localUser : myUser.getUserList()) {
//		System.out.println(++optionIndex + ") " + localUser.getUserName());
//	}
//
//	int selectedUserIndex = Integer.valueOf(myUser.readConsole()) - 1;
//
//	// check to see if author is the same as the SPC
//	if(selectedPaper.getAuthor().getUserName().equals(myUser.myUserArrayList.get(selectedUserIndex).getUserName())) {
//		System.out.println("Subprogram Chair cannot be the same as the Author.");
//		promptPC();
//	} else {
//		int totalReviewForSelectedUser = 0;
//		for(Paper localPaper : myUser.myPaperArrayList) {
//			if(localPaper.getReviewer() != null && localPaper.getReviewer().getUserName().equals(myUser.myUserArrayList.get(selectedUserIndex).getUserName())) {
//				totalReviewForSelectedUser++;
//			}
//		}
//
//		// check to see if the maximum number of review has been assigned to the selected user
//		if(totalReviewForSelectedUser > myUser.MAX_REVIEW) {
//			System.out.println("This user has already been assigned the maximum (" + myUser.MAX_REVIEW + ") possible papers.");
//		} else {
//			Reviewer localReviewer = new Reviewer(myUser.myUserArrayList.get(selectedUserIndex));
//			selectedPaper.setReviewer(localReviewer);
//			
//			System.out.println("The user " + myUser.myUserArrayList.get(selectedUserIndex).getUserName()  + " has been assigned to the paper " +  selectedPaper.getName() + " by " + selectedPaper.getAuthor().getUsername() + " as the Subprogram Chair.");
//			promptPC();
//		}
//	} 
//
//	break;