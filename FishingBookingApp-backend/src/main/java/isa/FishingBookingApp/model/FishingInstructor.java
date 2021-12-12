package isa.FishingBookingApp.model;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "fishing_instructor_user")
public class FishingInstructor extends User{
	
    @OneToMany(mappedBy = "fishingInstructor", fetch = FetchType.LAZY)
	private Set<FishingClass> classes;
	
	private String explanationOfReg;
	
	public FishingInstructor() {
		setClasses(new HashSet<FishingClass>());
	}
	public FishingInstructor(User user) {
		super(user);
		setClasses(new HashSet<FishingClass>());
	}
	public String getExplanationOfReg() {
		return explanationOfReg;
	}
	public void setExplanationOfReg(String explanationOfReg) {
		this.explanationOfReg = explanationOfReg;
	}
	public Set<FishingClass> getClasses() {
		return classes;
	}
	public void setClasses(Set<FishingClass> classes) {
		this.classes = classes;
	}
    public FishingInstructor(String mailAddress, String password, String name, String surname, String mobileNumber, Address address, UserRole role, boolean enabled, boolean verified, String explanationOfReg) {
        super(mailAddress, password, name, surname, mobileNumber, address, role, enabled, verified);
        this.explanationOfReg = explanationOfReg;
    }
	
}
