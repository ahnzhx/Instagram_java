package ig;

public class InstaDTO {
	private String no;
	private String id="";
	private String pw="";
	private String birth;
	private String gender;
	private String nation;
	private int total;
	
public InstaDTO(){}
public InstaDTO(String id, String pw) {
	this.id = id;
	this.pw = pw;
}
public InstaDTO(String no,String id, String pw,String birth,String gender,String nation) {
	this.no=no;
	this.id = id;
	this.pw = pw;
	this.birth=birth;
	this.gender=gender;
	this.nation=nation;
	
}

public int getTotal() {
	return total;
}
public void setTotal(int total) {
	this.total = total;
}
public String getNo() {
	return no;
}
public void setNo(String no) {
	this.no = no;
}
public String getBirth() {
	return birth;
}
public void setBirth(String birth) {
	this.birth = birth;
}
public String getGender() {
	return gender;
}
public void setGender(String gender) {
	this.gender = gender;
}
public String getNation() {
	return nation;
}
public void setNation(String nation) {
	this.nation = nation;
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getPw() {
	return pw;
}
public void setPw(String pw) {
	this.pw = pw;
}


}
