package project.monsterBox.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 全アバター画像のパスの定義
 * @author k.minamoto
 * @author morikawahiroki
 */
public class AllAvatar implements Serializable{
	/**全アバターのパスのリスト*/
	private ArrayList<Abator> passList=new ArrayList<Abator>();
	/**
	 *アバター画像のパス設定
	 */
	public AllAvatar() {
		for(int i=0;i<=28;i++){
			passList.add(new Abator("avatar_0"+i+".png"));
		}
	}
	/**
	 * 全アバターのパスのリストを返す
	 * @return
	 */
	public List<String> getAbatorList(){
		return passList.stream().map(e -> e.getAbatorFilePath()).collect(Collectors.toList());
	}

	public Abator getAbator(int abatorNum) {
		if(0 <= abatorNum && abatorNum <= 28) {
			return passList.get(abatorNum);
		} else {
			return null;
		}
	}
}
