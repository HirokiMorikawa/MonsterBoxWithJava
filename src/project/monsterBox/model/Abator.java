package project.monsterBox.model;

import java.io.Serializable;

/**
 * アバタークラス
 *
 * @author morikawahiroki
 *
 */
public class Abator implements Serializable {
	private String abatorFilePath;

	public Abator() {
		this.abatorFilePath = "avatar_0" + 0 + ".png";
	}

	public Abator(String abatorName) {
		this.abatorFilePath = abatorName;
	}

	public String getAbatorFilePath() {
		return this.abatorFilePath;
	}
}
