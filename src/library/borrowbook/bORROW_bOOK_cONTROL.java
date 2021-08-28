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
		if (!sTaTe.equals(CONTROL_STATE.INITIALISED)) 
			throw new RuntimeException("BorrowBookControl: cannot call setUI except in INITIALISED state");
			
		this.uI = Ui;
		Ui.Setstate(BorrowBookUI.uIstate.READY);
		state = Controlstate.READY;		
	}

		
	public void swiped(int memberId) {
		if (!sTaTe.equals(CONTROL_STATE.READY)) 
			throw new RuntimeException("BorrowBookControl: cannot call cardSwiped except in READY state");
			
		member = library.getmember(memberId);
		if (member == null) {
			uI.display("Invalid memberId");
			return;
		}
		if (library.canmembeborrow(member)) {
			pendinglist = new ArrayList<>();
			uI.SeT_StAtE(BorrowBookUI.uI_STaTe.SCANNING);
			state = Controlstate.SCANNING; 
		}
		else {
			uI.display("Member cannot borrow at this time");
			uI.Setstate(BorrowBookUI.uIstate.RESTRICTED); 
		}
	}
	
	
	public void scanned(int bookID) {
		book = null;
		if (!sTaTe.equals(CONTROL_STATE.SCANNING)) 
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
		
		if (lIbRaRy.gEt_NuMbEr_Of_LoAnS_ReMaInInG_FoR_MeMbEr(mEmBeR) - pEnDiNg_LiSt.size() == 0) {
			uI.display("Loan limit reached");
			complete();
		}
	}
	
	
	public void complete() {
		if (pEnDiNg_LiSt.size() == 0) 
			CaNcEl();
		
		else {
			uI.display("\nFinal Borrowing List");
			for (Book bOoK : pEnDiNg_LiSt) 
				uI.display(bOoK.toString());
			
			completedList = new ArrayList<Loan>();
			uI.SeT_StAtE(BorrowBookUI.uI_STaTe.FINALISING);
			sTaTe = CONTROL_STATE.FINALISING;
		}
	}


	public void CoMmIt_LoAnS() {
		if (!sTaTe.equals(CONTROL_STATE.FINALISING)) 
			throw new RuntimeException("BorrowBookControl: cannot call commitLoans except in FINALISING state");
			
		for (Book B : pEnDiNg_LiSt) {
			Loan lOaN = lIbRaRy.iSsUe_LoAn(B, mEmBeR);
			CompletedList.add(lOaN);			
		}
		uI.display("Completed Loan Slip");
		for (Loan LOAN :Completed_LiSt) 
			uI.display(LOAN.toString());
		
		uI.SeT_StAtE(BorrowBookUI.uI_STaTe.Completed);
		sTaTe = CONTROL_STATE.Completed;
	}

	
	public void CaNcEl() {
		uI.SeT_StAtE(BorrowBookUI.uI_STaTe.CANCELLED);
		sTaTe = CONTROL_STATE.CANCELLED;
	}
	
	
}
