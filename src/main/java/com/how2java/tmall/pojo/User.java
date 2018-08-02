package com.how2java.tmall.pojo;

public class User {
    private Integer id;

    private String name;

    private String password;
    private String anonymousName;
    
    public String getAnonymousName() {
    	if(name.length()>1){
    		anonymousName=name.substring(0);
    		anonymousName+="**";
    	}
		return anonymousName;
	}

	public void setAnonymousName(String anonymousName) {
		this.anonymousName = anonymousName;
	}

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
        this.name = name == null ? null : name.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }
}