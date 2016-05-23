public class Reviewer extends User implements java.io.Serializable {

	private User myUser;
	private Conference myConference;
	private int conferenceIndex;
	private final String[] myReviewerOptions1 = {"Submit Review"};

	public Reviewer(User theUser, int theConferenceID) {
		super(theUser.getUserName());
		myUser = theUser;
		if(myUser.myConferenceArrayList.size() > 0) {
			promptReviewer();
		} else {
			System.out.println("No conference has beet created yet.");
			myUser.prompt();
		}
	}
	
	public void promptReviewer() {
		int optionIndex = 0;
		System.out.println("\nSelect a paper to add review: ");
		for(Paper localPaper : myUser.myPaperArrayList) {
			if(localPaper.getReviewer() != null && localPaper.getReviewer().getUserName().equals(myUser.getUserName())) {
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
			for(String tempString : myReviewerOptions1) {
				System.out.println(++optionIndex + ") " + tempString);
			}
			
			for(String tempString : myUser.myBackOutOption) {
				System.out.println(++optionIndex + ") " + tempString);
			}

			int selectedOption = Integer.valueOf(myUser.readConsole());
			
			switch(selectedOption) {
			case 1:			//Assign a Reviewer
				System.out.println("Add your review for the paper " + myUser.myPaperArrayList.get(myUser.getPaperIndex(selectedPaperID)).getName() + ": ");
				myUser.myPaperArrayList.get(myUser.getPaperIndex(selectedPaperID)).submitReview(myUser.readConsole());
				myUser.myUpdateSerFilePaper.makeSerialize(myUser.myPaperArrayList);
				System.out.println("The review was submitted.");
				myUser.prompt();
				break;
			case 2:			//back
				myUser.prompt();
				break;
			case 3:			//log out
				myUser.logout();
				break;
			}
		} else {
			System.out.println("No paper has been assigned to you in this conference.");
			myUser.selectRole();
			myUser.prompt();
		}
	}
	
	private void submitReview() {
		
	}
}
