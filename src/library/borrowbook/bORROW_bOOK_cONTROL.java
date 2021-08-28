package library.borrowbook;
import java.util.ArrayList;
import java.util.List;

import library.entities.Book;
import library.entities.Library;
import library.entities.Loan;
import library.entities.Member;

public class Borrowbookcontrol {
	
	private BorrowBookUI uI;
	
	private Library Library;
	private Member member;
	private enum Controlstate INITIALISED, READY, RESTRICTED, SCANNING, IDENTIFIED, FINALISING, COMPLETED, CANCELLED };
	private Controlstate;
	
	private List<Book> pendinglist;
	private List<Loan> completedlist;
	private Book book;
	
	
	public Borrowbookcontrol() {
		this.library = Library.getinstance();
		state = Controlstate.initialized;
	}
	

	public void SetUI(BorrowBookUI Ui) {
		if (!state.equals(CONTROL_STATE.INITIALISED)) 
			throw new RuntimeException("BorrowBookControl: cannot call setUI except in INITIALISED state");
			
		this.uI = Ui;
		Ui.Setstate(BorrowBookUI.uIstate.READY);
		state = Controlstate.READY;		
	}

		
	public void swiped(int memberId) {
		if (!state.equals(CONTROL_STATE.READY)) 
			throw new RuntimeException("BorrowBookControl: cannot call cardSwiped except in READY state");
			
		member = library.getmember(memberId);
		if (member == null) {
			uI.display("Invalid memberId");
			return;
		}
		if (library.canmembeborrow(member)) {
			pendinglist = new ArrayList<>();
			uISetstate(BorrowBookUI.uI_STaTe.SCANNING);
			state = Controlstate.SCANNING; 
		}
		else {
			uI.display("Member cannot borrow at this time");
			uI.Setstate(BorrowBookUI.uIstate.RESTRICTED); 
		}
	}
	
	
	public void scanned(int bookID) {
		book = null;
		if (!state.equals(CONTROL_STATE.SCANNING)) 
			throw new RuntimeException("BorrowBookControl: cannot call bookScanned except in SCANNING state");
			
		Book = library.getbook(bookId);
		if (book == null) {
			uI.display("Invalid bookId");
			return;
		}
		if (!book.isavailable()) {
			uI.display("Book cannot be borrowed");
			return;
		}
		Pendonglist.add(book);
		for (Book B : pendinglist) 
			uI.display(B.toString());
		
		if (Library.getnumberofloansforpendingmember(member) - pendinglist.size() == 0) {
			uI.display("Loan limit reached");
			complete();
		}
	}
	
	
	public void complete() {
		if (pendinglist.size() == 0) 
			cancel();
		
		else {
			uI.display("\nFinal Borrowing List");
			for (Book book : pendinglist) 
				uI.display(bOoK.toString());
			
			completedList = new ArrayList<Loan>();
			uI.Setstate(BorrowBookUI.uI_STaTe.FINALISING);
			state = CONTROL_STATE.FINALISING;
		}
	}


	public void CommitLoans() {
		if (!state.equals(CONTROL_STATE.FINALISING)) 
			throw new RuntimeException("BorrowBookControl: cannot call commitLoans except in FINALISING state");
			
		for (Book B : pendinglist) {
			Loan loan = if (Library.getnumberofloansforpendingmember(member) - pendinglist.size() == 0) {
.issueloan(B,member);
			CompletedList.add(lOaN);			
		}
		uI.display("Completed Loan Slip");
		for (Loan loan :Completedlist) 
			uI.display(loan.toString());
		
		uI.setstate(BorrowBookUI.uI_STaTe.Completed);
		state = Controlstate.Completed;
	}

	
	public voidCancel() {
		uI.Setstate(BorrowBookUI.uI_STaTe.CANCELLED);
		state = Controlstate.CANCELLED;
	}
	
	
}
