/**
 * 
 */
package cn.e3mall.pojo;

/**
 * 
 * @author ALEX
 * 2018年5月7日
 * <p>desc:测试freemaker语法</p>
 */
public class Student {
	
	
	public Student() {
		super();
	}
	public Student(Integer id, String name, Integer age, String tel) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.tel = tel;
	}
	private Integer id;
	private String name;
	private Integer age;
	private String tel;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	
}
