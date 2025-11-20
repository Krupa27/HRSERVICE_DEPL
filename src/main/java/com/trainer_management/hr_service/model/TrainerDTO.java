package com.trainer_management.hr_service.model;

public class TrainerDTO {
   private String id;
   private   String name;
   private  String password;
   private String email;
    private  String mappedType;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
        this.password=id;
        //System.out.println("id");
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
		 
	}
	public void setPassword(String password) {
		this.password = password;
		 //System.out.println("password");
	
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMappedType() {
		return mappedType;
	}
	public void setMappedType(String mappedType) {
		this.mappedType = mappedType;
	}
	public TrainerDTO(String id, String name, String password, String email, String mappedType) {
		super();
		this.id = id;
		this.name = name;
		this.password = id;
		this.email = email;
		this.mappedType = mappedType;
	}
	public TrainerDTO() {
		super();
	}
	@Override
	public String toString() {
		return "TrainerDTO [id=" + id + ", name=" + name + ", password=" + password + ", email=" + email
				+ ", mappedType=" + mappedType + "]";
	}
    
    
}
