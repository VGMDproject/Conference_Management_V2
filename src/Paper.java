public class Paper implements java.io.Serializable {

		//we need an ID for paper 'cause two papers can have the same names\
		//this is how you identify your paper
		private int myPaperID;

		//the PC for this paper
		private ProgramChair myProgramChair;

		//the SPC for this paper
		private SubprogramChair mySubprogramChair; 

		//the reviewer for this paper
		private Reviewer myReviewer;

		//the author for this paper
		private Author myAuthor;

		//the name of the paper same as title
		private String myName;

		//the conference that this paper belongs to
		private int myConferenceID;

		//0 means new paper not touched yet by anyone
		//1 means approved to be in conference
		//2 means suspended by the author
		private int myStatus; 

		//review made by reviewer
		private String myReview; 

		//recommendation made by SPC
		private String myRecommendation; 

		//the file name of the paper
		//different than name. name is title
		private String myFile;  
		
		private int theID;
		private User myUser;
		private String myUsername;
		
		private final String myFileName = "paper.ser";

		public Paper() {
		}
		
		//constructor. takes an ID as parameter. 
		//id is unique but not random. something like (total articles + 1)
		public Paper(User theUser, int thePaperID, int theConferenceID) {
			myUser = theUser;
			myUsername = myUser.getUserName();
			myPaperID = thePaperID;
			myConferenceID = theConferenceID;
		}
		
		public String getUsername() {
			return myUsername;
		}
		
		public int getIndexPaper(int theID) {
			int index = -1;
			for(int i = 0; i < myUser.myPaperArrayList.size(); i++) {
				if(myUser.myPaperArrayList.get(i).getID() == myPaperID) {
					index = i;
					break;
				}
			}
			return index;
		}

		
		public ProgramChair getProgramchair(){
			return myProgramChair;
		}
		
		public void prompt() {
			
		}

		public SubprogramChair getSubprogramChair(){
			return mySubprogramChair;
		}

		public Reviewer getReviewer(){
			return myReviewer;
		}

		public Author getAuthor() {
			return myAuthor;
		}

		public String getName() {
			return myName;
		}

		public int getConferenceID(){
			return myConferenceID;
		}
		public int getStatusPaper(){
			return myStatus;
		}
		
		public String getReview(){
			return myReview;
		}
		
		public String getRecommendation(){
			return myRecommendation;
		}
		
		public String getFile(){
			return myFile;
		}

		public void setProgramchair(ProgramChair theProgramChair){
			myProgramChair = theProgramChair;
		}

		public void setSubprogramChair(SubprogramChair theSubprogramChair){
			mySubprogramChair = theSubprogramChair;
		}

		public void setReviewer(Reviewer theReviewer){
			myReviewer = theReviewer;
		}

		public void setAuthor(Author theAuthor) {
			myAuthor = theAuthor;
		}

		public void setName(String theName) {
			myName = theName;
		}

		public void setConference(int theConferenceID){
			myConferenceID = theConferenceID;
		}
		public void setStatusPaper(int theStatus){
			myStatus = theStatus;
		}
		
		public void setFile(String theFile){
			myFile = theFile;
		}
		
		public void setID(int theID){
			myPaperID = theID;
		}

		public int getID() {
			return myPaperID;
		}
		
		public void submitReview(String theReview) {
			myReview = theReview;
		}

		public void submitRecommendation(String theRecommendation) {
			myRecommendation = theRecommendation;
		}

		public void editPaper(String theName) {
			myName = theName;
		}
}
