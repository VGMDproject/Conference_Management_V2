//This class is a child for User class
public class Author extends User implements java.io.Serializable {
	//myUser is and instance of the User class
	private User myUser;

	//myUsername is the username for this User class passed in as constructor's parameters
	private String myUsername;

	private final String[] mySubmitOption = {"Submit a Paper"};
	//	private final String[] myUnsubmitOption = {"Unubmit a Paper"};
	private final String[] myModifyOption = {"Modify the Paper", 
			"Unsubmit the Paper", 
	"View the Review for the Paper"};

	//constructor 
	public Author(User theUser) {
		super(theUser.getUserName());
		myUser = theUser;
		myUsername = myUser.getUserName();
	}

	public void promptAuthor() {
		int menuIndex = 0;
		boolean paperFound = false;
		for(Paper tempPaper : myUser.myPaperArrayList) {
			if(tempPaper.getUsername().equals(myUser.getUserName()) && 
					tempPaper.getConferenceID() == myUser.getSelectedConferenceID() &&
					tempPaper.getStatusPaper() == 1) {
				paperFound = true;
				//view submitted paper
				for(String menuOption : myModifyOption) {
					System.out.println(++menuIndex + ") " + menuOption);
				}

				for(String menuOption : myUser.myBackOutOption) {
					System.out.println(++menuIndex + ") " + menuOption);
				}

				int selectedOption = Integer.valueOf(myUser.readConsole());

				switch(selectedOption) {
				case 1:
					System.out.println("Eneter the new name: ");
					myUser.getPaperList().get(tempPaper.getIndexPaper(tempPaper.getID())).setName(myUser.readConsole());
					myUser.myUpdateSerFilePaper.makeSerialize(myUser.myPaperArrayList);
					promptAuthor();
					break;
				case 2:
					myUser.getPaperList().get(tempPaper.getIndexPaper(tempPaper.getID())).setStatusPaper(0);
					myUser.myUpdateSerFilePaper.makeSerialize(myUser.myPaperArrayList);
					System.out.println("Paper " + tempPaper.getName() + " was unsubmitted.");
					promptAuthor();
					break;
				case 3:
					if(tempPaper.getReview() != null && !tempPaper.getReviewer().equals("")) {
						System.out.println(tempPaper.getReviewer());
					} else {
						System.out.println("No review has been submitted yet.");
					}
					promptAuthor();
					break;
				case 4:
					myUser.selectRole();
					myUser.prompt();
					break;
				case 5:
					myUser.logout();
					break;
				}
				break;
			}
		}

		if(!paperFound) {
			menuIndex = 0;
			System.out.println(myUser.mySelectPrompt);
			for(String menuOption : mySubmitOption) {
				System.out.println(++menuIndex + ") " + menuOption);
			}
			for(String menuOption : myUser.myBackOutOption) {
				System.out.println(++menuIndex + ") " + menuOption);
			}
			int selectedOption = Integer.valueOf(myUser.readConsole());
			switch(selectedOption) {
			case 1:
				Paper newPaper = new Paper(myUser, myUser.getPaperList().size() + 1, myUser.getSelectedConferenceID());
				System.out.println("Enter the name for the new paper: ");
//				System.out.println(this.getUsername());
//				System.out.println(this.getUserName());
				newPaper.setAuthor(this);
				newPaper.setName(myUser.readConsole());
				newPaper.setStatusPaper(1);
				System.out.println("Select the file for the new paper: ");
				newPaper.setFile(myUser.chooseFile(myUser.PAPER_FILE));
				myUser.myPaperArrayList.add(newPaper);
				myUser.myUpdateSerFilePaper.makeSerialize(myUser.myPaperArrayList);
				System.out.println("New paper was submitted.");
				myUser.selectRole();
				myUser.prompt();
				break;
			case 2:
				myUser.selectRole();
				myUser.prompt();
				break;

			case 3:
				myUser.logout();
				break;
			}
		}
	}

	public void submitPaper() {

	}

	//when author submits a paper, this status should be set to 1
	//we can change this status to 0 when we want to decline the paper
	private void setStatusPaper() {

	}

	public String getUsername() {
		return myUsername;
	}
	//author can call the editPaper method in Paper class. I don't know how.
	private void editPaper() {

	}

	//	public Author getAuthor() {
	//		for(Author localAuthor : myUser.get) {
	//			
	//		}
	//	}

}
