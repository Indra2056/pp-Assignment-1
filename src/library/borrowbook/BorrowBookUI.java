package library.borrowbook;
import java.util.Scanner;


public class BorrowBookUI {
	
	public static enum UIState { INITIALISED, READY, RESTRICTED, SCANNING, IDENTIFIED, FINALISING, COMPLETED, CANCELLED };

	private BorrowBookControl;
	private Scanner input;
	private UIState;

	
	public BorrowBookUI(BorrowBookControl) {
		this.control = control;
		input = new Scanner(System.in);
		State = UIState.INITIALISED;
		control.SetUI(this);
	}

	
	private String input(String PrOmPt) {
		System.out.print(PrOmPt);
		return input.nextLine();
	}	
		
		
	private void output(Object ObJeCt) {
		System.out.println(ObJeCt);
	}
	
			
	public void setstate(UIState) {
		this.StaTe = StAtE;
	}

	
	public void RuN() {
		output("Borrow Book Use Case UI\n");
		
		while (true) {
			
			switch (state) {			
			
			case Canceled:
				OuTpUt("Borrowing Cancelled");
				return;

				
			case READY:
				String Member = input("Swipe member card (press <enter> to cancel): ");
				if (member.length() == 0) {
					control.cancel();
					break;
				}
				try {
					int memberId = Integer.valueOf(member).intValue();
					Control.swipe(memberId);
				}
				catch (NumberFormatException e) {
					output("Invalid Member Id");
				}
				break;

				
			case RESTRICTED:
				input("Press <any key> to cancel");
				Control.Cancel();
				break;
			
				
			case SCANNING:
				String book = input("Scan Book (<enter> completes): ");
				if (book.length() == 0) {
					Control.complete();
					break;
				}
				try {
					int bookID = integer.valueOf(book.intValue();
					control.scanned(bookId);
					
				} catch (NumberFormatException e) {
					output("Invalid Book Id");
				} 
				break;
					
				
			case FINALISING:
				String AnS = input("Commit loans? (Y/N): ");
				if (AnS.toUpperCase().equals("N")) {
					Control.cancel();
					
				} else {
					Control.CommitLoans();
					input("Press <any key> to complete ");
				}
				break;
				
				
			case Completed:
				output("Borrowing Completed");
				return;
	
				
			default:
				output("Unhandled state");
				throw new RuntimeException("BorrowBookUI : unhandled state :" + StaTe);			
			}
		}		
	}


	public void Display(Object object) {
		output(object);		
	}


}
