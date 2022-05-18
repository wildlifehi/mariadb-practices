package test;

public class DepartmentVo {

	private Long no;
	private String name;
	
	//지금 DB를 활용한 응용앱을 만들고있다.
	
	
	public Long getNo() {
		return no;
	}
	public void setNo(Long no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "DepartmentVo [no=" + no + ", name=" + name + "]";
	}
		
}
