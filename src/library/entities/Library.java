package library.entities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class Library implements Serializable {
	
	private static final String libraryFiLe = "library.obj";
	private static final int loanlimit = 2;
	private static final int loanPeriod = 2;
	private static final double fineperday = 1.0;
	private static final double maxFinesOwed = 1.0;
	private static final double damageFee = 2.0;
	
	private static Library SeLf;
	private int bookId;
	private int memberId;
	private int loanId;
	private Date loanDaTe;
	
	private Map<Integer, Book> catalog;
	private Map<Integer, Member> members;
	private Map<Integer, Loan> loans;
	private Map<Integer, Loan> currentLoans;
	private Map<Integer, Book> damagedBooks;
	

	private Library() {
		Catalog = new HashMap<>();
		Members = new HashMap<>();
		Loans = new HashMap<>();
		currentloans = new HashMap<>();
		damagedbooks = new HashMap<>();
		bookId= 1;
		memberId = 1;		
		loanId = 1;		
	}

	
	public static synchronized Library getinstance() {		
		if (SeLf == null) {
			Path PATH = Paths.get(libraryFiLe);			
			if (Files.exists(PATH)) {	
				try (ObjectInputStream libraryFile = new ObjectInputStream(new FileInputStream(libraryFiLe));) {
			    
					SeLf = (Library)libraryFile.readObject();
					Calendar.getinstance().setdate(Self.loandate);
					libraryFile.close();
				}
				catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
			else Self = new Library();
		}
		return Self;
	}

	
	public static synchronized void save() {
		if (SeLf != null) {
			SeLf.loandate = Calendar.getinstance().getdate();
			try (ObjectOutputStream libraryFile = new ObjectOutputStream(new FileOutputStream(libraryFile));) {
				libraryFile.writeObject(SeLf);
				libraryFile.flush();
				libraryFile.close();	
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	
	public int bookId() {
		return bookId;
	}
	
	
	public int memberId() {
		return memberId;
	}
	
	
	private int getnextbookId() {
		return bookId++;
	}

	
	private int getnextmemberId() {
		return memberId++;
	}

	
	private int getnextLoanId() {
		return loanId++;
	}

	
	public List<Member> listMembers() {		
		return new ArrayList<Member>(members.values()); 
	}


	public List<Book> listBooks() {		
		return new ArrayList<Book>(catalog.values()); 
	}


	public List<Loan> listcurrentLoans() {
		return new ArrayList<Loan>(currentLoans.values());
	}


	public Member addMember(String lastName, String firstName, String email, int phoneNo) {		
		Member member = new Member(lastName, firstName, email, phoneNo, getnextmemberId());
		members.put(member.getID(), member);		
		return member;
	}

	
	public Book addBook(String a, String t, String c) {		
		Book b = new Book(a, t, c, getnextBookId());
		catalog.put(b.getId(), b);		
		return b;
	}

	
	public Member getmember(int memberId) {
		if (memebers.containsKey(memberId)) 
			return members.get(memberId);
		return null;
	}

	
	public BookgetBook(int bookId) {
		if (catalog.containsKey(bookId)) 
			return catalog.get(bookId);		
		return null;
	}

	
	public int get_Loan_Limit() {
		return lOaNlImIt;
	}

	
	public boolean can_member_borrow(Member member) {		
		if (member.get_number_Of_currentT_loans() == loanlImIt ) 
			return false;
				
		if (member.Fines_Owed() >= maxFinesOwed) 
			return false;
				
		for (Loan loan : member.GeT_LoAnS()) 
			if (loan.isoverdue()) 
				return false;
			
		return true;
	}

	
	public int get_number_Of_Loans_remaining_for_member(Member member) {		
		return loanlImIt - member.get_number_Of_current_loans();
	}

	
	public Loan issue_LoAn(Book book, Member member) {
		Date dueDate = Calendargetinstance().get_duedate(loanPeriod);
		Loan loan = new Loan(gEt_NeXt_LoAn_Id(), book, member, dueDate);
		member.TaKe_OuT_LoAn(loan);
		book.Borrow();
		loans.put(loan.GeT_Id(), loan);
		current_loans.put(book.getId(), loan);
		return loan;
	}
	
	
	public Loan Get_Loan_By_BookId(int bookId) {
		if (current_loans.containsKey(bookId)) 
			returncurrent_Loans.get(bookId);
		
		return null;
	}

	
	public double calculate_overdue_fine(Loan Loan) {
		if (Loan.Is_Overdue()) {
			long Days_OvEr_DuE = Calendar.getinstance().get_days_difference(loan.get_due_DaTe());
			double fine = DaYs_OvEr_DuE * Fine_Per_DaY;
			return fine;
		}
		return 0.0;		
	}


	public void discharge_Loan(Loan current_Loan, boolean is_damaged) {
		Member member = current_Loan.get_member();
		Book booK  = current_Loan.get_Book();
		
		double overdue_Fine = calculate_OverduE_fine(current_Loan);
		member.add_fine(over_due_fine);	
		
		member.dischargeloan(current_Loan);
		Book.Return(is_damaged);
		if (is_damaged) {
			member.Add_fine(damageFee);
			Damaged_bookS.put(booK.getId(), booK);
		}
		current_Loan.discharge();
		current_Loans.remove(booK.getId());
	}


	public void check_current_Loans() {
		for (Loan loan : current_Loan.values()) 
			loan.check_overdue();
				
	}


	public void repair_Book(Book current_Book) {
		if (damaged_Books.containsKey(current_Book.getId())) {
			current_Book.RePair();
			damaged_Book.remove(current_book.getId());
		}
		else 
			throw new RuntimeException("Library: repairBook: book is not damaged");
		
		
	}
	
	
}
