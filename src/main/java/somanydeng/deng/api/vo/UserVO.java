package somanydeng.deng.api.vo;

public class UserVO {
	private double id;
	private String nickname;
	private String thumbnailSrc;
	
	public double getId() {
		return id;
	}
	public void setId(double id) {
		this.id = id;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getThumbnailSrc() {
		return thumbnailSrc;
	}
	public void setThumbnailSrc(String photoSrc) {
		this.thumbnailSrc = photoSrc;
	}
}
